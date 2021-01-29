package org.chunghyun.lottoapp.confirm;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_confirm);

        init();
    }
    void init(){
        recyclerView = findViewById(R.id.input_confirm_recyclerview);
        db = MyDatabase.getDatabase(getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Input_confirm_adapter(db, Input_confirm.this, round, numbers, bonus);
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

        db.myDao().getAll().observe(this, new Observer<List<Lotto_Input_MyEntity>>() {
            @Override
            public void onChanged(List<Lotto_Input_MyEntity> lotto_input_myEntities) {
                adapter.setItem(lotto_input_myEntities);
            }
        });
    }
}
