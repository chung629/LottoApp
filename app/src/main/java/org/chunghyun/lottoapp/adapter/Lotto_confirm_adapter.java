package org.chunghyun.lottoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.chunghyun.lottoapp.R;
import java.util.ArrayList;

public class Lotto_confirm_adapter extends RecyclerView.Adapter<Lotto_confirm_adapter.ViewHolder> {

    ArrayList<String> round;
    ArrayList<String> date;
    ArrayList<String[]> numbers;
    Context context;


    public Lotto_confirm_adapter(ArrayList<String> round, ArrayList<String> date, ArrayList<String[]> numbers, Context context){
        this.round =round;
        this.date = date;
        this.numbers = numbers;
        this.context = context;
    }
    @NonNull
    @Override
    public Lotto_confirm_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_lotto_confirm, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Lotto_confirm_adapter.ViewHolder holder, int position) {
        holder.text_round.setText(round.get(position) + " 회차");
        holder.text_date.setText(date.get(position));
        holder.imageView_1.setImageResource(context.getResources().getIdentifier( "ball_" + numbers.get(position)[0], "drawable", context.getPackageName()));
        holder.imageView_2.setImageResource(context.getResources().getIdentifier( "ball_" + numbers.get(position)[1], "drawable", context.getPackageName()));
        holder.imageView_3.setImageResource(context.getResources().getIdentifier( "ball_" + numbers.get(position)[2], "drawable", context.getPackageName()));
        holder.imageView_4.setImageResource(context.getResources().getIdentifier( "ball_" + numbers.get(position)[3], "drawable", context.getPackageName()));
        holder.imageView_5.setImageResource(context.getResources().getIdentifier( "ball_" + numbers.get(position)[4], "drawable", context.getPackageName()));
        holder.imageView_6.setImageResource(context.getResources().getIdentifier( "ball_" + numbers.get(position)[5], "drawable", context.getPackageName()));
    }

    @Override
    public int getItemCount() {
        return round.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView text_round;
        TextView text_date;
        ImageView imageView_1;
        ImageView imageView_2;
        ImageView imageView_3;
        ImageView imageView_4;
        ImageView imageView_5;
        ImageView imageView_6;


        ViewHolder(View item){
            super(item);
            text_round = item.findViewById(R.id.confirm_adapter1);
            text_date = item.findViewById(R.id.confirm_adapter2);
            imageView_1 = item.findViewById(R.id.confirm_imageView1);
            imageView_2 = item.findViewById(R.id.confirm_imageView2);
            imageView_3 = item.findViewById(R.id.confirm_imageView3);
            imageView_4 = item.findViewById(R.id.confirm_imageView4);
            imageView_5 = item.findViewById(R.id.confirm_imageView5);
            imageView_6 = item.findViewById(R.id.confirm_imageView6);
        }
    }
}
