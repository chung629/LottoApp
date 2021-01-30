package org.chunghyun.lottoapp.useInternet;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.chunghyun.lottoapp.R;
import org.chunghyun.lottoapp.adapter.Lotto_confirm_adapter;
import org.chunghyun.lottoapp.adapter.Lotto_static_adapter;
import org.chunghyun.lottoapp.dialog.ProgressDialog;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Lotto_confirm extends AppCompatActivity {

    // 로또 API
    int curRound;
    ArrayList<String> roundNumber;
    ArrayList<String> date;
    ArrayList<String[]> numbers;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lotto_number_confirm);
        getSupportActionBar().setTitle("로또 당첨번호 확인");
        Toast.makeText(this, "당첨 정보를 받아오고 있습니다.", Toast.LENGTH_SHORT).show();
        init();
    }

    void init(){
        curRound = 0;
        roundNumber = new ArrayList<>();
        date = new ArrayList<>();
        numbers = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.show();
        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();
        recyclerView = findViewById(R.id.lotto_confirm_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // 웹크롤링을 통한 현재 회차 정보 가져오기
    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Intent intent = getIntent();
                curRound = Integer.parseInt(intent.getExtras().get("round").toString());
                if(curRound % 10 != 0){
                    curRound = curRound / 10 + 1;
                }
                for(int i=1; i<=curRound; i++){
                    Document document = Jsoup.connect("https://dhlottery.co.kr/gameResult.do?method=statConsNumber&sortOrder=DESC&startPage=1&endPage=10&currentPage=" + i).get();
                    Elements elements = document.select("tbody").select("tr");
                    for(Element e : elements){
                        String[] tmp = e.text().toString().split(" ");
                        roundNumber.add(tmp[0]);
                        date.add(tmp[1]);
                        String[] num = {tmp[2], tmp[3], tmp[4], tmp[5], tmp[6], tmp[7]};
                        numbers.add(num);
                    }
                }
            }catch(IOException e){
                Toast.makeText(Lotto_confirm.this, "데이터를 받아오는데 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Lotto_confirm_adapter adapter = new Lotto_confirm_adapter(roundNumber, date, numbers, getApplicationContext());
            recyclerView.setAdapter(adapter);
            progressDialog.dismiss();
            super.onPostExecute(aVoid);
        }
    }
}
