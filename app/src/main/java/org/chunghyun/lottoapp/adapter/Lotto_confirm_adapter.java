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
import org.w3c.dom.Text;

import java.util.ArrayList;

public class Lotto_confirm_adapter extends RecyclerView.Adapter<Lotto_confirm_adapter.ViewHolder> {

    ArrayList<Integer> round;
    ArrayList<String> date;
    ArrayList<String> money;
    ArrayList<int[]> number;

    public Lotto_confirm_adapter(ArrayList<Integer> round, ArrayList<String> date, ArrayList<String> money, ArrayList<int[]> number){
        this.round =round;
        this.date = date;
        this.money = money;
        this.number = number;
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
        holder.text_round.setText(round.get(position) + "회차");
        holder.text_date.setText(date.get(position));
        holder.text_money.setText(money.get(position));
    }

    @Override
    public int getItemCount() {
        return round.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView text_round;
        TextView text_date;
        TextView text_money;
        LinearLayout linearLayout;

        ViewHolder(View item){
            super(item);
            text_round = item.findViewById(R.id.textview_adapter1);
            text_date = item.findViewById(R.id.textview_adapter2);
            text_money = item.findViewById(R.id.textview_adapter3);
            linearLayout = item.findViewById(R.id.numberContainer_adapter);
        }
    }
}
