package org.chunghyun.lottoapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.chunghyun.lottoapp.adapter.Occur_confirm_adapter;
import org.chunghyun.lottoapp.adapter.Select_confirm_adapter;
import org.chunghyun.lottoapp.confirm.Select_number_confirm;
import org.chunghyun.lottoapp.database.Lotto_Occur_MyEntity;
import org.chunghyun.lottoapp.database.Lotto_Select_MyEntity;
import org.chunghyun.lottoapp.database.MyDatabase;

import java.util.ArrayList;
import java.util.List;

public class Lotto_occur_number extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lotto_number_occur);

    }
}