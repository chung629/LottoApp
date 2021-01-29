package org.chunghyun.lottoapp.confirm;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.chunghyun.lottoapp.Lotto_occur_number;
import org.chunghyun.lottoapp.R;
import org.chunghyun.lottoapp.adapter.Occur_confirm_adapter;
import org.chunghyun.lottoapp.database.Lotto_Occur_MyEntity;
import org.chunghyun.lottoapp.database.MyDatabase;

import java.util.ArrayList;
import java.util.List;

public class Occur_confirm extends AppCompatActivity {

    private MyDatabase db;
    private RecyclerView recyclerView;
    private Occur_confirm_adapter adapter;
    private String round;
    private ArrayList<String> numbers;
    private String bonus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.occur_number_confirm);
        init();
    }
    void init(){
        recyclerView = findViewById(R.id.occur_confirm_recyclerview);
        db = MyDatabase.getDatabase(getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Occur_confirm_adapter(db, Occur_confirm.this, round, numbers, bonus);
        recyclerView.setAdapter(adapter);
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
        db.occurDao().getAll().observe(this, new Observer<List<Lotto_Occur_MyEntity>>() {
            @Override
            public void onChanged(List<Lotto_Occur_MyEntity> lotto_occur_myEntities) {
                adapter.setItem(lotto_occur_myEntities);
            }
        });
    }
}
