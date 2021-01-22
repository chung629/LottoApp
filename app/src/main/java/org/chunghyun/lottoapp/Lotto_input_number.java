package org.chunghyun.lottoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Lotto_input_number extends AppCompatActivity {

    Button saveButton;
    Button before_input_confirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lotto_input_number);
        init();
    }

    public void init(){
        saveButton = findViewById(R.id.save_input_num);
        before_input_confirm = findViewById(R.id.before_input_confirm);
        before_input_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Input_confirm.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
    }


}
