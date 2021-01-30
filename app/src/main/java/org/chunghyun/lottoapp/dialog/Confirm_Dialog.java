package org.chunghyun.lottoapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;

import org.chunghyun.lottoapp.R;
import org.chunghyun.lottoapp.database.Lotto_Input_MyEntity;
import org.chunghyun.lottoapp.database.MyDatabase;

public class Confirm_Dialog extends Dialog {

    Button delete;
    Button deleteAll;
    Lotto_Input_MyEntity lotto_input_myEntity;
    MyDatabase db;

    public Confirm_Dialog(@NonNull Context context, Lotto_Input_MyEntity lotto_input_myEntity) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_confirm);
        this.lotto_input_myEntity = lotto_input_myEntity;
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
                        db.inputDao().delete(lotto_input_myEntity);
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
                        db.inputDao().deleteRoundAll(lotto_input_myEntity.getRound());
                    }
                }).start();
                dismiss();
            }
        });
    }

}
