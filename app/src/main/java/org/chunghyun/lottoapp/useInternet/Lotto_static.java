package org.chunghyun.lottoapp.useInternet;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
import org.chunghyun.lottoapp.adapter.Lotto_static_adapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lotto_static extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> list;
    ArrayList<String> num;
    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lotto_static);

        init();
    }
    public void init(){
        list = new ArrayList<>();
        num = new ArrayList<>();
        context = getApplicationContext();
        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();
        recyclerView = findViewById(R.id.static_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Document doc = Jsoup.connect("https://dhlottery.co.kr/gameResult.do?method=statByNumber").get();
                Elements round = doc.select(".graph");
                for(Element elem : round){
                    String tmp = elem.select("td").text();
                    list.add(tmp.substring(0, tmp.length()-1));
                }
                Elements e = doc.select("tbody").get(1).select("tr").select("td");
                int count = 0;
                for(Element ee : e){
                    count++;
                    if(count % 3 == 0)
                        num.add(ee.text().toString());
                }
            }catch(IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Lotto_static_adapter adapter = new Lotto_static_adapter(num, list, context);
            recyclerView.setAdapter(adapter);
            super.onPostExecute(aVoid);
        }
    }

}
