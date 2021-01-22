package org.chunghyun.lottoapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

public class Lotto_static extends AppCompatActivity {

    // 로또 API
    JsonObject jsonObject;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lotto_static);

        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
    }

    public void requestLottoNumber() {
        String lotto_No = "946";   // 이번 회차 번호로 되도록 고치기
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
                    resId = getResources().getIdentifier(temp, "drawable", getPackageName());
                    ImageView image = new ImageView(getApplicationContext());
                    image.setImageResource(resId);
                    linearLayout.addView(image);
                }
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

}
