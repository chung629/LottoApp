package org.chunghyun.lottoapp.confirm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.chunghyun.lottoapp.R;
import org.chunghyun.lottoapp.adapter.Input_confirm_adapter;
import org.chunghyun.lottoapp.database.Lotto_Input_MyEntity;
import org.chunghyun.lottoapp.database.MyDatabase;
import java.util.ArrayList;
import java.util.List;

public class Input_confirm extends AppCompatActivity {

    private MyDatabase db;
    private RecyclerView recyclerView;
    private Input_confirm_adapter adapter;
    private String round;
    private ArrayList<String> numbers;
    private String bonus;
    private AdView mAdView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_confirm);
        getSupportActionBar().setTitle("수동번호 선택 확인");
        adFunction();
        init();
    }
    // 애드몹 관련
    public void adFunction(){
        // 광고 관련
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        mAdView = findViewById(R.id.confirm_adView);
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
        round = "";
        numbers = new ArrayList<>();
        bonus = "";
        Intent intent = getIntent();
        round = intent.getExtras().getString("round");
        numbers.add(intent.getExtras().getString("num1"));
        numbers.add(intent.getExtras().getString("num2"));
        numbers.add(intent.getExtras().getString("num3"));
        numbers.add(intent.getExtras().getString("num4"));
        numbers.add(intent.getExtras().getString("num5"));
        numbers.add(intent.getExtras().getString("num6"));
        bonus = intent.getExtras().getString("bonus");
        recyclerView = findViewById(R.id.input_confirm_recyclerview);
        db = MyDatabase.getDatabase(getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Input_confirm_adapter(db, Input_confirm.this, round, numbers, bonus);
        recyclerView.setAdapter(adapter);
        db.inputDao().getAll().observe(this, new Observer<List<Lotto_Input_MyEntity>>() {
            @Override
            public void onChanged(List<Lotto_Input_MyEntity> lotto_input_myEntities) {
                adapter.setItem(lotto_input_myEntities);
            }
        });
    }
}
