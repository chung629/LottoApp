package org.chunghyun.lottoapp.occur;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.chunghyun.lottoapp.R;
import org.chunghyun.lottoapp.confirm.Input_confirm;
import org.chunghyun.lottoapp.database.Lotto_Input_MyEntity;
import org.chunghyun.lottoapp.database.MyDatabase;
import org.chunghyun.lottoapp.dialog.Dialog_Lotto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

public class Lotto_input_number extends AppCompatActivity {

    private Button saveButton;
    private LinearLayout linearLayout_A;
    private LinearLayout linearLayout_B;
    private LinearLayout linearLayout_C;
    private  LinearLayout linearLayout_D;
    private LinearLayout linearLayout_E;
    private ArrayList<String> select_number_A;
    private ArrayList<String> select_number_B;
    private ArrayList<String> select_number_C;
    private  ArrayList<String> select_number_D;
    private ArrayList<String> select_number_E;
    private TextView round;
    private  String title;
    // 데이터 베이스 관련
    private MyDatabase db;
    private AdView mAdView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lotto_input_number);
        getSupportActionBar().setTitle("수동번호 선택");
        adFunction();
        init();
    }
    // 애드몹 관련
    public void adFunction(){
        // 광고 관련
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        mAdView = findViewById(R.id.input_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        // 광고가 제대로 로드 되는지 테스트 하기 위한 코드입니다.
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                // 광고가 문제 없이 로드시 출력됩니다.
                Log.d("@@@", "onAdLoaded");
            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                // 광고 로드에 문제가 있을시 출력됩니다.
                Log.d("@@@", "onAdFailedToLoad " + errorCode);
            }
            @Override
            public void onAdOpened() { }
            @Override
            public void onAdClicked() { }
            @Override
            public void onAdLeftApplication() { }
            @Override
            public void onAdClosed() { }
        });
        // 광고 끝
    }

    public void init(){
        saveButton = findViewById(R.id.save_input_num);
        select_number_A = new ArrayList<>();
        select_number_B = new ArrayList<>();
        select_number_C = new ArrayList<>();
        select_number_D = new ArrayList<>();
        select_number_E = new ArrayList<>();
        // 데이터베이스 관련
        title = "";
        db = MyDatabase.getDatabase(getApplicationContext());
        // 이전 수동 번호 발생 확인
        //회차 정보 최신화
        updateRoundInfo();
        //라인 클릭
        lineClick();
        // 번호 저장 데이터 베이스
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(() ->{
                    if(!select_number_A.isEmpty()){
                        db.inputDao().insert(new Lotto_Input_MyEntity((Integer.parseInt(title) + 1) + "", select_number_A.get(0),select_number_A.get(1)
                                ,select_number_A.get(2),select_number_A.get(3),select_number_A.get(4),select_number_A.get(5),"미추첨"));
                    }
                    if(!select_number_B.isEmpty()){
                        db.inputDao().insert(new Lotto_Input_MyEntity((Integer.parseInt(title) + 1) + "", select_number_B.get(0),select_number_B.get(1)
                                ,select_number_B.get(2),select_number_B.get(3),select_number_B.get(4),select_number_B.get(5),"미추첨"));
                    }
                    if(!select_number_C.isEmpty()){
                        db.inputDao().insert(new Lotto_Input_MyEntity((Integer.parseInt(title) + 1) + "", select_number_C.get(0),select_number_C.get(1)
                                ,select_number_C.get(2),select_number_C.get(3),select_number_C.get(4),select_number_C.get(5),"미추첨"));
                    }
                    if(!select_number_D.isEmpty()){
                        db.inputDao().insert(new Lotto_Input_MyEntity((Integer.parseInt(title) + 1) + "", select_number_D.get(0),select_number_D.get(1)
                                ,select_number_D.get(2),select_number_D.get(3),select_number_D.get(4),select_number_D.get(5),"미추첨"));
                    }
                    if(!select_number_E.isEmpty()){
                        db.inputDao().insert(new Lotto_Input_MyEntity((Integer.parseInt(title) + 1) + "", select_number_E.get(0),select_number_E.get(1)
                                ,select_number_E.get(2),select_number_E.get(3),select_number_E.get(4),select_number_E.get(5),"미추첨"));
                    }
                }).start();
                Toast.makeText(getApplicationContext(), "저장되었습니다", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public int returnResource(String number){
        int num = Integer.parseInt(number);
        if(num >=1 && num <= 10){
            return R.drawable.common_ball1_10;
        }else if(num >=11 && num <= 20){
            return R.drawable.common_ball11_20;
        }else if(num >=21 && num <= 30){
            return R.drawable.common_ball21_30;
        }else if(num >=31 && num <= 40){
            return R.drawable.common_ball31_40;
        }else{
            return R.drawable.common_ball41_45;
        }
    }
    class add_view implements  View.OnClickListener{

        private String str;

        add_view(String str){
            this.str = str;
        }

        @Override
        public void onClick(View v) {
            Dialog_Lotto dialog_lotto = new Dialog_Lotto(Lotto_input_number.this);
            dialog_lotto.setDialogListener(new Dialog_Lotto.DialogClickListener() {
                @Override
                public void onPositiveClick(ArrayList<String> selectNumber) {
                    switch (str){
                        case "A":
                            select_number_A = new ArrayList<String>(selectNumber);
                            break;
                        case "B":
                            select_number_B = new ArrayList<String>(selectNumber);
                            break;
                        case "C":
                            select_number_C = new ArrayList<String>(selectNumber);
                            break;
                        case "D":
                            select_number_D = new ArrayList<String>(selectNumber);
                            break;
                        case "E":
                            select_number_E = new ArrayList<String>(selectNumber);
                            break;
                    }
                    TextView textViews[] = new TextView[6];
                    for(int i=0; i<6; i++){
                        textViews[i] = findViewById(getResources().getIdentifier(str + "_" + (i+1), "id", getPackageName()));
                        textViews[i].setBackgroundResource(returnResource(selectNumber.get(i)));
                        textViews[i].setText(selectNumber.get(i) + "");
                    }
                }
            });
            dialog_lotto.show();
        }
    }
    class delete_view implements View.OnClickListener{

        private String str;
        delete_view(String str){
            this.str = str;
        }
        @Override
        public void onClick(View v) {
            switch (str){
                case "A":
                    select_number_A.clear();
                    break;
                case "B":
                    select_number_B.clear();
                    break;
                case "C":
                    select_number_C.clear();
                    break;
                case "D":
                    select_number_D.clear();
                    break;
                case "E":
                    select_number_E.clear();
                    break;
            }
            TextView imageView[] = new TextView[6];
            for(int i=0; i<6; i++){
                imageView[i] = findViewById(getResources().getIdentifier(str + "_" + (i+1), "id", getPackageName()));
                imageView[i].setBackgroundResource(R.drawable.container_circle);
            }
        }
    }

    public void lineClick(){
        linearLayout_A = findViewById(R.id.input_image_A);
        linearLayout_B = findViewById(R.id.input_image_B);
        linearLayout_C = findViewById(R.id.input_image_C);
        linearLayout_D = findViewById(R.id.input_image_D);
        linearLayout_E = findViewById(R.id.input_image_E);
        linearLayout_A.setOnClickListener(new add_view("A"));
        linearLayout_B.setOnClickListener(new add_view("B"));
        linearLayout_C.setOnClickListener(new add_view("C"));
        linearLayout_D.setOnClickListener(new add_view("D"));
        linearLayout_E.setOnClickListener(new add_view("E"));
        TextView delete_A = findViewById(R.id.input_delete_A);
        TextView delete_B = findViewById(R.id.input_delete_B);
        TextView delete_C = findViewById(R.id.input_delete_C);
        TextView delete_D = findViewById(R.id.input_delete_D);
        TextView delete_E = findViewById(R.id.input_delete_E);
        delete_A.setOnClickListener(new delete_view("A"));
        delete_B.setOnClickListener(new delete_view("B"));
        delete_C.setOnClickListener(new delete_view("C"));
        delete_D.setOnClickListener(new delete_view("D"));
        delete_E.setOnClickListener(new delete_view("E"));
    }
    public void updateRoundInfo(){
        round = findViewById(R.id.input_round);
        Intent intent = getIntent();
        title = intent.getExtras().get("round").toString();
        round.setText(Integer.parseInt(title) + 1 + " 회차");
    }
}
