package org.chunghyun.lottoapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;

import org.chunghyun.lottoapp.R;
import org.chunghyun.lottoapp.database.Lotto_Input_MyEntity;
import org.chunghyun.lottoapp.database.Lotto_Occur_MyEntity;
import org.chunghyun.lottoapp.database.MyDatabase;

public class Occur_Dialog extends Dialog {

    Button delete;
    Button deleteAll;
    Lotto_Occur_MyEntity lotto_Occur_myEntity;
    MyDatabase db;

    public Occur_Dialog(@NonNull Context context, Lotto_Occur_MyEntity lotto_Occur_myEntity) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_confirm);
        this.lotto_Occur_myEntity = lotto_Occur_myEntity;
        init();
    }

    void init(){
        delete = findViewById(R.id.delete_button);
        db = MyDatabase.getDatabase(getContext());
        deleteAll = findViewById(R.id.delete_all_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        db.occurDao().delete(lotto_Occur_myEntity);
                    }
                }).start();
                dismiss();
            }
        });
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        db.occurDao().deleteRoundAll(lotto_Occur_myEntity.getRound());
                    }
                }).start();
                dismiss();
            }
        });
    }

}
