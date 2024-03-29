package org.chunghyun.lottoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.chunghyun.lottoapp.R;
import org.chunghyun.lottoapp.database.Lotto_Occur_MyEntity;
import org.chunghyun.lottoapp.database.MyDatabase;
import org.chunghyun.lottoapp.dialog.Occur_Dialog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Occur_confirm_adapter extends RecyclerView.Adapter<Occur_confirm_adapter.ViewHolder>{

    ArrayList<Lotto_Occur_MyEntity> items = new ArrayList<>();
    Context context;
    private String round;
    private ArrayList<String> numbers;
    private String bonus;
    private MyDatabase db;

    public Occur_confirm_adapter(MyDatabase db, Context context, String round, ArrayList<String> numbers, String bonus){
        this.db = db;
        this.context = context;
        this.round = round;
        this.numbers = numbers;
        this.bonus = bonus;
    }

    @NonNull
    @Override
    public Occur_confirm_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_input_confirm, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull Occur_confirm_adapter.ViewHolder holder, int position) {
        holder.round.setText(items.get(position).getRound() + " 회차");
        holder.num1.setText(items.get(position).getNum1());
        holder.num2.setText(items.get(position).getNum2());
        holder.num3.setText(items.get(position).getNum3());
        holder.num4.setText(items.get(position).getNum4());
        holder.num5.setText(items.get(position).getNum5());
        holder.num6.setText(items.get(position).getNum6());
        int count = 0;
        String result = "";
        if(items.get(position).getRound().equals(round) && items.get(position).getResult().equals("미추첨")){
            count = items.get(position).countCollect(numbers);
            if(items.get(position).countBonus(bonus)){
                count++;
                if(count == 6)
                    result = "2등";
                else if(count == 5)
                    result = "3등";
                else if(count == 4)
                    result = "4등";
                else if(count == 3)
                    result = "5등";
                else
                    result = "낙첨";
            }else{
                if(count == 6)
                    result = "1등";
                else if(count == 5)
                    result = "3등";
                else if(count == 4)
                    result = "4등";
                else if(count == 3)
                    result = "5등";
                else
                    result = "낙첨";
            }
            editData(position, result);
        }
        holder.result.setText(items.get(position).getResult());
        holder.num1.setBackgroundResource(returnResId(items.get(position).getNum1()));
        holder.num2.setBackgroundResource(returnResId(items.get(position).getNum2()));
        holder.num3.setBackgroundResource(returnResId(items.get(position).getNum3()));
        holder.num4.setBackgroundResource(returnResId(items.get(position).getNum4()));
        holder.num5.setBackgroundResource(returnResId(items.get(position).getNum5()));
        holder.num6.setBackgroundResource(returnResId(items.get(position).getNum6()));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Occur_Dialog occur_dialog = new Occur_Dialog(context, items.get(position));
                occur_dialog.show();
            }
        });
    }

    public void editData(int index, String result){
        new Thread(()->{
            items.get(index).setResult(result);
            db.occurDao().update(items.get(index));
        }).start();
    }
    public int returnResId(String num){
        int temp = Integer.parseInt(num);
        if(temp >= 1 && temp <= 10)
            return R.drawable.dialog_ball1_10;
        else if(temp >= 11 && temp <= 20)
            return R.drawable.dialog_ball11_20;
        else if(temp >= 21 && temp <= 30)
            return R.drawable.dialog_ball21_30;
        else if(temp >= 31 && temp <= 40)
            return R.drawable.dialog_ball31_40;
        else
            return R.drawable.dialog_ball41_45;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItem(List<Lotto_Occur_MyEntity> data){
        items = new ArrayList<>(data);
        Collections.reverse(items);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView round;
        TextView num1;
        TextView num2;
        TextView num3;
        TextView num4;
        TextView num5;
        TextView num6;
        TextView result;
        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.input_confirm_click);
            round = itemView.findViewById(R.id.input_confirm_round);
            num1 = itemView.findViewById(R.id.input_confirm_num1);
            num2 = itemView.findViewById(R.id.input_confirm_num2);
            num3 = itemView.findViewById(R.id.input_confirm_num3);
            num4 = itemView.findViewById(R.id.input_confirm_num4);
            num5 = itemView.findViewById(R.id.input_confirm_num5);
            num6 = itemView.findViewById(R.id.input_confirm_num6);
            result = itemView.findViewById(R.id.input_confirm_result);
        }
    }
}
