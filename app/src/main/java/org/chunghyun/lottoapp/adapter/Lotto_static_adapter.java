package org.chunghyun.lottoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.chunghyun.lottoapp.R;

import java.util.ArrayList;

public class Lotto_static_adapter extends RecyclerView.Adapter<Lotto_static_adapter.ViewHolder> {

    private ArrayList<String> mNumber = null;
    private ArrayList<String> mNum = null;
    private Context context;

    public Lotto_static_adapter(ArrayList<String> num, ArrayList<String> list, Context context){
        mNum = num;
        mNumber = list;
        this.context = context;
    }
    @NonNull
    @Override
    public Lotto_static_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_lotto_static, parent, false);
        Lotto_static_adapter.ViewHolder viewHolder = new Lotto_static_adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Lotto_static_adapter.ViewHolder holder, int position) {
        holder.number.setBackgroundResource(returnResource(position+1 + ""));
        holder.number.setText(position+1 + "");
        holder.num.setText(mNum.get(position));
        holder.graph.setProgress(Integer.parseInt(mNumber.get(position)));
    }

    public int returnResource(String number){
        int num = Integer.parseInt(number);
        if(num >=1 && num <= 10){
            return R.drawable.common_ball1_10;
        }else if(num >=11 && num <= 20){
            return R.drawable.common_ball11_20;
        }else if(num >=21 && num <= 30){
            return R.drawable.common_ball21_30;
        }else if(num >=31 && num <= 40){
            return R.drawable.common_ball31_40;
        }else{
            return R.drawable.common_ball41_45;
        }
    }

    @Override
    public int getItemCount() {
        return mNumber.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        TextView number;
        ProgressBar graph;
        TextView num;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.static_ball_image);
            graph = itemView.findViewById(R.id.static_progressBar);
            num = itemView.findViewById(R.id.static_textView);
            graph.setMax(100);
        }
    }
}
