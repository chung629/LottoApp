package org.chunghyun.lottoapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.chunghyun.lottoapp.R;

import java.util.ArrayList;
import java.util.Collections;

public class Select_Dialog_Lotto extends Dialog {

    private Context context;
    private DialogClickListener dialogClickListener;
    private Button yes, no;
    private TextView[] textView;
    private ArrayList<String> selectNumber;
    private boolean isClick[];

    public interface DialogClickListener {
        void onPositiveClick(ArrayList<String> selectNumber);
    }
    public Select_Dialog_Lotto(@NonNull Context context) {
        super(context);
        this.context = context;
    }
    //리스너 초기화
    public void setDialogListener(Select_Dialog_Lotto.DialogClickListener dialogListener){
        this.dialogClickListener = dialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_lotto);
        init();
    }

    public void init(){
        isClick = new boolean[45];
        selectNumber = new ArrayList<String>();
        textView = new TextView[45];
        for(int i=1; i<=45; i++){
            int resId = context.getResources().getIdentifier("dialog_" + i, "id", context.getPackageName());
            textView[i-1] = findViewById(resId);
            textView[i-1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int temp = 0;
                    for(int i=1; i<=45; i++){
                        if(v.getId() == context.getResources().getIdentifier("dialog_" + i, "id", context.getPackageName())){
                            if(isClick[i-1])
                                isClick[i-1] = false;
                            else
                                isClick[i-1] = true;
                            temp = i-1;
                            break;
                        }
                    }
                    if(isClick[temp]) {
                        if(temp < 10)
                            v.setBackground(context.getDrawable(R.drawable.dialog_ball1_10));
                        else if(temp < 20 && temp >= 10)
                            v.setBackground(context.getDrawable(R.drawable.dialog_ball11_20));
                        else if(temp < 30 && temp >= 20)
                            v.setBackground(context.getDrawable(R.drawable.dialog_ball21_30));
                        else if(temp < 40 && temp >= 30)
                            v.setBackground(context.getDrawable(R.drawable.dialog_ball31_40));
                        else
                            v.setBackground(context.getDrawable(R.drawable.dialog_ball41_45));
                        selectNumber.add(temp+1 + "");
                    } else{
                        selectNumber.remove(temp+1 + "");
                        v.setBackground(context.getDrawable(R.drawable.dialog_ball));
                    }
                }
            });
        }
        yes = findViewById(R.id.lotto_yes);
        no = findViewById(R.id.lotto_no);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectNumber.size() < 6){
                    Toast.makeText(getContext(), "6개 이상의 숫자를 선택해주세요", Toast.LENGTH_SHORT).show();
                }else{
                    Collections.sort(selectNumber, (a,b)-> Integer.compare(Integer.parseInt(a), Integer.parseInt(b)));
                    dialogClickListener.onPositiveClick(selectNumber);
                    dismiss();
                }
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
