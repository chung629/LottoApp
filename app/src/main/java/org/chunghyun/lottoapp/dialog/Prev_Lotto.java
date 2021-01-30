package org.chunghyun.lottoapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.chunghyun.lottoapp.R;

import java.util.ArrayList;
import java.util.Collections;

public class Prev_Lotto extends Dialog {

    private Context context;
    private DialogClickListener dialogClickListener;
    private Button yes, no;
    private ArrayList<String> selectNumber;
    private boolean isClick[];
    private ArrayList<String> setNum;
    private ArrayList<String> include;
    private ArrayList<String> exclude;
    private TextView[] num;


    public interface DialogClickListener {
        void onPositiveClick(ArrayList<String> selectNumber);
    }
    public Prev_Lotto(@NonNull Context context, ArrayList<String> setNum, ArrayList<String> include, ArrayList<String> exclude) {
        super(context);
        this.context = context;
        this.setNum = setNum;
        this.include = include;
        this.exclude = exclude;
    }
    //리스너 초기화
    public void setDialogListener(Prev_Lotto.DialogClickListener dialogListener){
        this.dialogClickListener = dialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_prev);
        init();
    }

    public void init(){
        isClick = new boolean[7];
        num = new TextView[7];
        selectNumber = new ArrayList<String>();
        setting_number();
        yes = findViewById(R.id.prev_lotto_yes);
        no = findViewById(R.id.prev_lotto_no);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectNumber.size() == 0){
                    Toast.makeText(getContext(), "1개 이상의 번호를 선택 해주세요", Toast.LENGTH_SHORT).show();
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
    public void setting_number(){
        num[0] = findViewById(R.id.prev_dialog_1);
        num[1] = findViewById(R.id.prev_dialog_2);
        num[2] = findViewById(R.id.prev_dialog_3);
        num[3] = findViewById(R.id.prev_dialog_4);
        num[4] = findViewById(R.id.prev_dialog_5);
        num[5] = findViewById(R.id.prev_dialog_6);
        num[6] = findViewById(R.id.prev_dialog_7);
        for(int i=0; i<setNum.size(); i++)
            num[i].setText(setNum.get(i));
        num[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               clickListener(v, 0);
            }
        });
        num[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener(v, 1);
            }
        });
        num[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener(v, 2);
            }
        });
        num[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener(v, 3);
            }
        });
        num[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener(v, 4);
            }
        });
        num[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener(v, 5);
            }
        });
        num[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener(v, 6);
            }
        });
    }
    public void clickListener(View v, int index){
        String tmp = num[index].getText().toString();
        if(!isClick[index]){
            if(include.contains(tmp)){
                Toast.makeText(context, "포함수에 선택된 번호입니다.", Toast.LENGTH_SHORT).show();
            }else if(exclude.contains(tmp)){
                Toast.makeText(context, "제외수에 선택된 번호입니다", Toast.LENGTH_SHORT).show();
            }else{
                if (selectNumber.size() + include.size() < 5) {
                    int temp = Integer.parseInt(tmp) - 1;
                    if (temp < 10)
                        v.setBackground(context.getDrawable(R.drawable.dialog_ball1_10));
                    else if (temp < 20 && temp >= 10)
                        v.setBackground(context.getDrawable(R.drawable.dialog_ball11_20));
                    else if (temp < 30 && temp >= 20)
                        v.setBackground(context.getDrawable(R.drawable.dialog_ball21_30));
                    else if (temp < 40 && temp >= 30)
                        v.setBackground(context.getDrawable(R.drawable.dialog_ball31_40));
                    else
                        v.setBackground(context.getDrawable(R.drawable.dialog_ball41_45));
                    selectNumber.add(temp + 1 + "");
                    isClick[index] = true;
                }else {
                    int a = 5 - include.size() - selectNumber.size();
                    if(selectNumber.size() < 5)
                        Toast.makeText(getContext(), "현재 선택 가능한 번호의 수는 " + a + "입니다", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getContext(), "최대 5개의 숫자만 선택 가능 합니다", Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            selectNumber.remove(tmp);
            v.setBackground(context.getDrawable(R.drawable.dialog_ball));
            isClick[index] = false;
        }
    }
}
