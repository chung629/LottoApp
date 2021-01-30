package org.chunghyun.lottoapp.occur;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.chunghyun.lottoapp.R;
import org.chunghyun.lottoapp.database.Lotto_Select_MyEntity;
import org.chunghyun.lottoapp.database.MyDatabase;
import org.chunghyun.lottoapp.dialog.Select_Dialog_Lotto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Select_number_occur extends AppCompatActivity {

    private Button numberAdd;
    private Button save;
    private Button occur;
    private Button delete;
    private GridLayout gridLayout;
    private ArrayList<String> selectNumberAdd;
    private ArrayList<String> finalNumber;
    private Random random =  new Random();
    private TextView num1;
    private TextView num2;
    private TextView num3;
    private TextView num4;
    private TextView num5;
    private TextView num6;
    private int selectRound;
    private MyDatabase db;
    private AdView mAdView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_number_occur);
        getSupportActionBar().setTitle("선택번호 조합 발생");
        adFunction();
        init();
    }
    void init(){
        db = MyDatabase.getDatabase(getApplicationContext());
        numberAdd = findViewById(R.id.select_add_button);
        save = findViewById(R.id.select_number_occur_save);
        occur = findViewById(R.id.select_number_occur_button);
        delete = findViewById(R.id.select_delete_button);
        gridLayout = findViewById(R.id.select_number_container);
        num1 = findViewById(R.id.select_TextView1);
        num2 = findViewById(R.id.select_TextView2);
        num3 = findViewById(R.id.select_TextView3);
        num4 = findViewById(R.id.select_TextView4);
        num5 = findViewById(R.id.select_TextView5);
        num6 = findViewById(R.id.select_TextView6);
        TextView round = findViewById(R.id.select_number_round);
        Intent intent = getIntent();
        selectRound = Integer.parseInt(intent.getExtras().get("round").toString()) + 1;
        round.setText(selectRound + " 회차");
        selectNumberAdd = new ArrayList<>();
        finalNumber = new ArrayList<>();
        buttonClickEvent();
    }
    void buttonClickEvent(){
        numberAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Select_Dialog_Lotto select_dialog_lotto = new Select_Dialog_Lotto(Select_number_occur.this);
                select_dialog_lotto.setDialogListener(new Select_Dialog_Lotto.DialogClickListener() {
                    @Override
                    public void onPositiveClick(ArrayList<String> selectNumber) {
                        selectNumberAdd = new ArrayList<>(selectNumber);
                        gridLayout.removeAllViews();
                        for(int i=0; i<selectNumberAdd.size(); i++){
                            createTextView(selectNumber.get(i));
                        }
                    }
                });
                select_dialog_lotto.show();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectNumberAdd.clear();
                gridLayout.removeAllViews();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!finalNumber.isEmpty()){
                    new Thread(() ->{
                        Lotto_Select_MyEntity lotto_select_myEntity = new Lotto_Select_MyEntity(selectRound + "", finalNumber.get(0)
                                , finalNumber.get(1), finalNumber.get(2), finalNumber.get(3), finalNumber.get(4), finalNumber.get(5), "미추첨");
                            db.selectDao().insert(lotto_select_myEntity);
                    }).start();
                    Toast.makeText(getApplicationContext(), "저장되었습니다", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "번호 생성을 해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
        occur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalNumber.clear();
                if(selectNumberAdd.isEmpty()){
                    Toast.makeText(getApplicationContext(), "번호를 선택해주세요", Toast.LENGTH_SHORT).show();
                }else{
                    while(finalNumber.size() < 6){
                        String temp = "";
                        do{
                            temp = selectNumberAdd.get(random.nextInt(selectNumberAdd.size()));
                        } while(finalNumber.contains(temp));
                        finalNumber.add(temp);
                    }
                }
                Collections.sort(finalNumber, (a, b)-> Integer.compare(Integer.parseInt(a), Integer.parseInt(b)));
                num1.setText(finalNumber.get(0));
                num2.setText(finalNumber.get(1));
                num3.setText(finalNumber.get(2));
                num4.setText(finalNumber.get(3));
                num5.setText(finalNumber.get(4));
                num6.setText(finalNumber.get(5));
                num1.setBackgroundResource(returnResId(finalNumber.get(0)));
                num2.setBackgroundResource(returnResId(finalNumber.get(1)));
                num3.setBackgroundResource(returnResId(finalNumber.get(2)));
                num4.setBackgroundResource(returnResId(finalNumber.get(3)));
                num5.setBackgroundResource(returnResId(finalNumber.get(4)));
                num6.setBackgroundResource(returnResId(finalNumber.get(5)));
            }
        });
    }
    public int returnResId(String number){
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
    public void createTextView(String number){
        TextView textView = new TextView(this);
        textView.setText(number);
        textView.setTextSize(18);
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER);
        int num = Integer.parseInt(number);
        if(num >=1 && num <= 10){
            textView.setBackgroundResource(R.drawable.common_ball1_10);
        }else if(num >=11 && num <= 20){
            textView.setBackgroundResource(R.drawable.common_ball11_20);
        }else if(num >=21 && num <= 30){
            textView.setBackgroundResource(R.drawable.common_ball21_30);
        }else if(num >=31 && num <= 40){
            textView.setBackgroundResource(R.drawable.common_ball31_40);
        }else{
            textView.setBackgroundResource(R.drawable.common_ball41_45);
        }
        LinearLayout.LayoutParams tmp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tmp.leftMargin = 10;
        tmp.rightMargin = 10;
        tmp.bottomMargin = 10;
        textView.setLayoutParams(tmp);
        gridLayout.addView(textView);
    }
    // 애드몹 관련
    public void adFunction(){
        // 광고 관련
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        mAdView = findViewById(R.id.select_adView);
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
}
