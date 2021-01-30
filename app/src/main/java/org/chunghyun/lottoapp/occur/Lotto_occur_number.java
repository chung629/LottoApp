package org.chunghyun.lottoapp.occur;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.LinearLayout;
import android.widget.Spinner;
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
import org.chunghyun.lottoapp.database.Lotto_Occur_MyEntity;
import org.chunghyun.lottoapp.database.MyDatabase;
import org.chunghyun.lottoapp.dialog.Exclude_Dialog;
import org.chunghyun.lottoapp.dialog.Include_Dialog;
import org.chunghyun.lottoapp.dialog.Prev_Lotto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Lotto_occur_number extends AppCompatActivity {

    private LinearLayout MainLayout;
    private LinearLayout prevLinearLayout;
    private LinearLayout includeLinearLayout;
    private GridLayout excludeLinearLayout;
    private TextView round;
    private ArrayList<String> finalNumbers;
    private ArrayList<String> numberList;
    private ArrayList<String> prevList;
    private Spinner spinner;
    private int curRound;
    private MyDatabase db;
    private Random random;
    private ArrayList<String> returnPrev;
    private ArrayList<String> returnInclude;
    private ArrayList<String> returnExclude;
    private AdView mAdView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lotto_number_occur);
        getSupportActionBar().setTitle("랜덤번호 발생");
        adFunction();
        init();
    }
    // 애드몹 관련
    public void adFunction(){
        // 광고 관련
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        mAdView = findViewById(R.id.occur_adView);
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
    void init(){
        MainLayout = findViewById(R.id.occur_numbers);
        prevLinearLayout = findViewById(R.id.occur_prev_numbers);
        includeLinearLayout = findViewById(R.id.occur_include_container);
        excludeLinearLayout = findViewById(R.id.occur_exclude_container);
        round = findViewById(R.id.occur_round);
        Intent intent = getIntent();
        curRound = Integer.parseInt(intent.getExtras().get("round").toString()) + 1;
        round.setText( curRound + " 회차");
        initButton();
        db = MyDatabase.getDatabase(getApplicationContext());

        finalNumbers = new ArrayList<>();
        numberList = new ArrayList<>();
        prevList = new ArrayList<>();
        random = new Random();
        returnPrev = new ArrayList<>();
        returnExclude = new ArrayList<>();
        returnInclude = new ArrayList<>();

        for(int i=0; i<6; i++){
            createInitTextView(MainLayout);
        }
        for(int i=0; i<5; i++){
            createInitTextView(prevLinearLayout);
            createInitTextView(includeLinearLayout);
            createInitTextView_Grid(excludeLinearLayout);
        }
        linearLayoutClick();
        spinnerSetting();
        occurSaveButton();

        prevList.add(intent.getExtras().getString("num1"));
        prevList.add(intent.getExtras().getString("num2"));
        prevList.add(intent.getExtras().getString("num3"));
        prevList.add(intent.getExtras().getString("num4"));
        prevList.add(intent.getExtras().getString("num5"));
        prevList.add(intent.getExtras().getString("num6"));
        prevList.add(intent.getExtras().getString("bonus"));
    }
    // 초기화 버튼 - finish
    void initButton(){
        Button prevInit = findViewById(R.id.occur_prev_initButton);
        Button includeInit = findViewById(R.id.occur_include_initButton);
        Button excludeInit = findViewById(R.id.occur_exclude_initButton);
        prevInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevLinearLayout.removeAllViews();
                for(int i=0; i<5; i++)
                    createInitTextView(prevLinearLayout);
                returnPrev.clear();
            }
        });
        includeInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                includeLinearLayout.removeAllViews();
                for(int i=0; i<5; i++)
                    createInitTextView(includeLinearLayout);
                returnInclude.clear();
            }
        });
        excludeInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excludeLinearLayout.removeAllViews();
                for(int i=0; i<5; i++)
                    createInitTextView_Grid(excludeLinearLayout);
                returnExclude.clear();
            }
        });
    }
    // 일반 텍스트뷰 생성 - finish
    public void createInitTextView(LinearLayout linearLayout){
        TextView textView = new TextView(this);
        textView.setBackgroundResource(R.drawable.container_circle);
        LinearLayout.LayoutParams tmp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tmp.setMargins(10, 10, 10, 10);
        textView.setLayoutParams(tmp);
        linearLayout.addView(textView);
    }
    // 일반 텍스트뷰 생성 - finish
    public void createInitTextView_Grid(GridLayout linearLayout){
        TextView textView = new TextView(this);
        textView.setBackgroundResource(R.drawable.container_circle);
        LinearLayout.LayoutParams tmp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tmp.setMargins(10, 10, 10, 10);
        textView.setLayoutParams(tmp);
        linearLayout.addView(textView);
    }
    // 클릭시 다이얼로그 창 띄우기
    public void linearLayoutClick(){
        prevLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prev_Lotto prev_lotto = new Prev_Lotto(Lotto_occur_number.this, prevList, returnInclude, returnExclude);
                prev_lotto.setDialogListener(new Prev_Lotto.DialogClickListener() {
                    @Override
                    public void onPositiveClick(ArrayList<String> selectNumber) {
                        returnPrev = new ArrayList<>(selectNumber);
                        prevLinearLayout.removeAllViews();
                        for(int i=0; i<returnPrev.size(); i++)
                            createMainView(prevLinearLayout, returnPrev.get(i));
                    }
                });
                prev_lotto.show();
            }
        });
        includeLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Include_Dialog include_dialog = new Include_Dialog(Lotto_occur_number.this, returnPrev, returnExclude);
                include_dialog.setDialogListener(new Include_Dialog.DialogClickListener() {
                    @Override
                    public void onPositiveClick(ArrayList<String> selectNumber) {
                        returnInclude = new ArrayList<>(selectNumber);
                        includeLinearLayout.removeAllViews();
                        for(int i=0; i<returnInclude.size(); i++)
                            createMainView(includeLinearLayout, returnInclude.get(i));
                    }
                });
                include_dialog.show();
            }
        });
        excludeLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Exclude_Dialog exclude_dialog = new Exclude_Dialog(Lotto_occur_number.this, returnPrev, returnInclude);
                exclude_dialog.setDialogListener(new Exclude_Dialog.DialogClickListener() {
                    @Override
                    public void onPositiveClick(ArrayList<String> selectNumber) {
                        returnExclude = new ArrayList<>(selectNumber);
                        excludeLinearLayout.removeAllViews();
                        for(int i=0; i<returnExclude.size(); i++)
                            createMainView_Grid(excludeLinearLayout, returnExclude.get(i));
                    }
                });
                exclude_dialog.show();

            }
        });
    }
    // 스피너 세팅 - finish
    void spinnerSetting(){
        spinner = findViewById(R.id.occur_spinner);
        String[] items = {"선택", "높은순", "낮은순"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
    }

    void occurSaveButton(){
        Button save = findViewById(R.id.occur_save);
        Button occur = findViewById(R.id.occur_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!finalNumbers.isEmpty()){
                    new Thread(() ->{
                        Lotto_Occur_MyEntity lotto_occur_myEntity = new Lotto_Occur_MyEntity(curRound + "", finalNumbers.get(0)
                                , finalNumbers.get(1), finalNumbers.get(2), finalNumbers.get(3), finalNumbers.get(4), finalNumbers.get(5), "미추첨");
                        db.occurDao().insert(lotto_occur_myEntity);
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
                finalNumbers.clear();
                numberList.clear();
                for(int i=1; i<=45; i++){
                    numberList.add(i + "");
                }
                for(int i=0; i<returnExclude.size(); i++)
                    numberList.remove(returnExclude.get(i));
                for(int i=0; i<returnPrev.size(); i++)
                    finalNumbers.add(returnPrev.get(i));
                for(int i=0; i<returnInclude.size(); i++)
                    finalNumbers.add(returnInclude.get(i));
                while(finalNumbers.size() < 6){
                    String num = numberList.get(random.nextInt(numberList.size()));
                    while(finalNumbers.contains(num)){
                        num = numberList.get(random.nextInt(numberList.size()));
                    }
                    finalNumbers.add(num);
                }
                MainLayout.removeAllViews();
                Collections.sort(finalNumbers, (a,b)-> Integer.compare(Integer.parseInt(a), Integer.parseInt(b)));
                for(int i=0; i<finalNumbers.size(); i++)
                    createMainView(MainLayout, finalNumbers.get(i));
            }
        });
    }

    public void createMainView(LinearLayout linearLayout, String number){
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
        tmp.setMargins(10, 10, 10, 10);
        textView.setLayoutParams(tmp);
        linearLayout.addView(textView);
    }
    public void createMainView_Grid(GridLayout linearLayout, String number){
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
        tmp.setMargins(10, 10, 10, 10);
        textView.setLayoutParams(tmp);
        linearLayout.addView(textView);
    }
}