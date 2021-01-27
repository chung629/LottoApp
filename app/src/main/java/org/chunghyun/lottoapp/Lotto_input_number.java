package org.chunghyun.lottoapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import org.chunghyun.lottoapp.dialog.Dialog_Lotto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Lotto_input_number extends AppCompatActivity {

    Button saveButton;
    Button before_input_confirm;
    LinearLayout linearLayout_A;
    LinearLayout linearLayout_B;
    LinearLayout linearLayout_C;
    LinearLayout linearLayout_D;
    LinearLayout linearLayout_E;
    ArrayList<String> select_number_A;
    ArrayList<String> select_number_B;
    ArrayList<String> select_number_C;
    ArrayList<String> select_number_D;
    ArrayList<String> select_number_E;
    TextView round;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lotto_input_number);
        init();
    }

    public void init(){

        saveButton = findViewById(R.id.save_input_num);
        before_input_confirm = findViewById(R.id.before_input_confirm);
        // 번호 저장 데이터 베이스 구축 해야함 - 미완료
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
        // 이전 수동 번호 발생 확인
        before_input_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Input_confirm.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
        //회차 정보 최신화
        updateRoundInfo();
        //라인 클릭
        lineClick();
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
                    ImageView imageViews[] = new ImageView[6];
                    for(int i=0; i<6; i++){
                        imageViews[i] = findViewById(getResources().getIdentifier(str + "_" + (i+1), "id", getPackageName()));
                        imageViews[i].setImageDrawable(ContextCompat.getDrawable(Lotto_input_number.this, getResources().getIdentifier("ball_" + selectNumber.get(i), "drawable", getPackageName())));
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
                    select_number_A = new ArrayList<>();
                    break;
                case "B":
                    select_number_B = new ArrayList<>();
                    break;
                case "C":
                    select_number_C = new ArrayList<>();
                    break;
                case "D":
                    select_number_D = new ArrayList<>();
                    break;
                case "E":
                    select_number_E = new ArrayList<>();
                    break;
            }
            ImageView imageView[] = new ImageView[6];
            for(int i=0; i<6; i++){
                imageView[i] = findViewById(getResources().getIdentifier(str + "_" + (i+1), "id", getPackageName()));
                imageView[i].setImageResource(R.drawable.container_circle);
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
        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();
    }
    // 웹크롤링을 통한 현재 회차 정보 가져오기
    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void>{
        String title = "";
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Document doc = Jsoup.connect("https://dhlottery.co.kr/common.do?method=main").get();
                Elements round = doc.select("#lottoDrwNo");
                title = round.text();
            }catch(IOException e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            round.setText(title + " 회차");
        }
    }
}
