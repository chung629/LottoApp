package org.chunghyun.lottoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.chunghyun.lottoapp.confirm.Input_confirm;
import org.chunghyun.lottoapp.confirm.Occur_confirm;
import org.chunghyun.lottoapp.confirm.Select_number_confirm;
import org.chunghyun.lottoapp.occur.Lotto_input_number;
import org.chunghyun.lottoapp.occur.Select_number_occur;
import org.chunghyun.lottoapp.useInternet.Lotto_QR;
import org.chunghyun.lottoapp.useInternet.Lotto_confirm;
import org.chunghyun.lottoapp.useInternet.Lotto_static;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private AdView mAdView;
    private Button lotto_occur;
    private Button lotto_input;
    private Button lotto_static;
    private Button year_occur;
    private Button year_input;
    private Button year_confirm;
    private Button occur_confirm;
    private Button input_confirm;
    private Button lotto_confirm;
    // 로또 API
    JsonObject jsonObject;
    RequestQueue requestQueue;
    String curRound;
    String[] numbers;
    String bonus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adFunction();
        init();

        // 로또 API 관련
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
    }

    // 레이아웃 초기화
    public void init(){
        numbers = new String[6];
        bonus = "";
        lotto_occur = (Button)findViewById(R.id.lotto_occur);
        lotto_input = (Button)findViewById(R.id.lotto_input);
        lotto_static = (Button)findViewById(R.id.lotto_static);
        year_occur = (Button)findViewById(R.id.lotto_qr);
        year_input = (Button)findViewById(R.id.select_number_occur);
        year_confirm = (Button)findViewById(R.id.select_number_confirm);
        occur_confirm = (Button)findViewById(R.id.occur_confirm);
        input_confirm = (Button)findViewById(R.id.input_confirm);
        lotto_confirm = (Button)findViewById(R.id.lotto_confirm);
        Button.OnClickListener onClickListener = new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent;
                switch(v.getId()){
                    case R.id.lotto_occur:
                        intent = new Intent(getApplicationContext(), Lotto_occur_number.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.putExtra("round", curRound);
                        startActivity(intent);
                        break;
                    case R.id.lotto_input:
                        intent = new Intent(getApplicationContext(), Lotto_input_number.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        break;
                    case R.id.lotto_static:
                        intent = new Intent(getApplicationContext(), Lotto_static.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                        break;
                    case R.id.lotto_qr:
                        intent = new Intent(getApplicationContext(), Lotto_QR.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                        break;
                    case R.id.select_number_occur:
                        intent = new Intent(getApplicationContext(), Select_number_occur.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.putExtra("round", curRound);
                        startActivity(intent);
                        break;
                    case R.id.select_number_confirm:
                        intent = new Intent(getApplicationContext(), Select_number_confirm.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.putExtra("round", curRound);
                        intent.putExtra("num1", numbers[0]);
                        intent.putExtra("num2", numbers[1]);
                        intent.putExtra("num3", numbers[2]);
                        intent.putExtra("num4", numbers[3]);
                        intent.putExtra("num5", numbers[4]);
                        intent.putExtra("num6", numbers[5]);
                        intent.putExtra("bonus", bonus);
                        startActivity(intent);
                        break;
                    case R.id.occur_confirm:
                        intent = new Intent(getApplicationContext(), Occur_confirm.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.putExtra("round", curRound);
                        intent.putExtra("num1", numbers[0]);
                        intent.putExtra("num2", numbers[1]);
                        intent.putExtra("num3", numbers[2]);
                        intent.putExtra("num4", numbers[3]);
                        intent.putExtra("num5", numbers[4]);
                        intent.putExtra("num6", numbers[5]);
                        intent.putExtra("bonus", bonus);
                        startActivity(intent);
                        break;
                    case R.id.input_confirm:
                        intent = new Intent(getApplicationContext(), Input_confirm.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.putExtra("round", curRound);
                        intent.putExtra("num1", numbers[0]);
                        intent.putExtra("num2", numbers[1]);
                        intent.putExtra("num3", numbers[2]);
                        intent.putExtra("num4", numbers[3]);
                        intent.putExtra("num5", numbers[4]);
                        intent.putExtra("num6", numbers[5]);
                        intent.putExtra("bonus", bonus);
                        startActivity(intent);
                        break;
                    case R.id.lotto_confirm:
                        intent = new Intent(getApplicationContext(), Lotto_confirm.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        break;
                }
            }
        };
        lotto_occur.setOnClickListener(onClickListener);
        lotto_input.setOnClickListener(onClickListener);
        lotto_static.setOnClickListener(onClickListener);
        year_occur.setOnClickListener(onClickListener);
        year_input.setOnClickListener(onClickListener);
        year_confirm.setOnClickListener(onClickListener);
        occur_confirm.setOnClickListener(onClickListener);
        input_confirm.setOnClickListener(onClickListener);
        lotto_confirm.setOnClickListener(onClickListener);
        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();
    }


    // 애드몹 관련
    public void adFunction(){
        // 광고 관련
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        mAdView = findViewById(R.id.adView);
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

    // 로또 번호 관련
    public void requestLottoNumber(String round) {
        String lotto_No = round;   // 이번 회차 번호로 되도록 고치기
        String[] lotto_number = {"drwtNo1", "drwtNo2", "drwtNo3", "drwtNo4", "drwtNo5", "drwtNo6", "bnusNo"};
        String url = "https://www.dhlottery.co.kr/common.do?method=getLottoNumber&drwNo=" + lotto_No;
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.numberContainer_adapter);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                jsonObject = (JsonObject) JsonParser.parseString(response);
                int resId = 0;
                for (int i = 0; i < lotto_number.length - 1; i++) {
                    String temp = "ball_" + jsonObject.get(lotto_number[i]);
                    numbers[i] = jsonObject.get(lotto_number[i]) + "";
                    resId = getResources().getIdentifier(temp, "drawable", getPackageName());
                    ImageView image = new ImageView(getApplicationContext());
                    image.setImageResource(resId);
                    image.setScaleType(ImageView.ScaleType.CENTER);
                    linearLayout.addView(image);
                }
                TextView textView = new TextView(getApplicationContext());
                textView.setText(" + ");
                textView.setGravity(Gravity.CENTER_VERTICAL);
                textView.setTextSize(40);
                linearLayout.addView(textView);
                ImageView image = new ImageView(getApplicationContext());
                image.setImageResource(getResources().getIdentifier("ball_" + jsonObject.get(lotto_number[lotto_number.length-1]), "drawable", getPackageName()));
                linearLayout.addView(image);
                bonus = jsonObject.get(lotto_number[lotto_number.length-1]) + "";
                //당첨결과, 당첨금, 당첨날짜
                TextView textView1 = findViewById(R.id.temp1);
                TextView textView2 = findViewById(R.id.temp2);
                TextView textView3 = findViewById(R.id.temp3);
                textView1.setText(lotto_No + "회 당첨결과");
                String str =  jsonObject.get("drwNoDate").toString();
                textView2.setText(str.substring(1, str.length()-1));
                String money = jsonObject.get("firstWinamnt").toString();
                textView3.setText("1등 당첨금 : " + Math.round((double)Integer.parseInt(money.substring(0, money.length()-3))/100000) + "억");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            // Header나 요청 parameter를 재정의 할 수 있다.
            // GET방식에서는 url에 parameter가 함께 있어 불필요, POST 방식에서 사용
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        request.setShouldCache(false); // 캐싱하지 말고 매번 받은것은 다시 보여주도록 설정
        requestQueue.add(request);
    }
    // 웹크롤링을 통한 현재 회차 정보 가져오기
    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Document doc = Jsoup.connect("https://dhlottery.co.kr/common.do?method=main").get();
                Elements round = doc.select("#lottoDrwNo");
                curRound = round.text();
                requestLottoNumber(curRound);
            }catch(IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }
}