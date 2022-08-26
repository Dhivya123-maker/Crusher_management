package com.example.crushermanagement.ViewEntry;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.crushermanagement.R;

import java.util.List;

public class ViewEntryAdapter extends RecyclerView.Adapter<ViewEntryAdapter.ViewHolder> {



   List<ViewEntryModel> show_models;
  Context context;




    public ViewEntryAdapter(Context context, List<ViewEntryModel> show_models) {
        this.context = context;
        this.show_models = show_models;

    }





    @NonNull
    @Override
    public ViewEntryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_entry_recycle,parent,false);

        return new ViewEntryAdapter.ViewHolder(view);


    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewEntryModel model = show_models.get(position);
        holder.s_no.setText(model.getS_no());
        holder.p_name.setText(model.getP_name());
        holder.place.setText(model.getPlace());
        holder.m_name.setText(model.getM_name());
        holder.units.setText(model.getUnits());
        holder.price.setText(model.getPrice());
        holder.v_no.setText(model.getV_no());
        holder.total.setText(model.getRate());
        holder.date.setText(model.getDate());


        if (holder.v_no.getText().toString().equals("null")) {
            holder. v_no.setBackgroundResource(R.drawable.red_bg);
            holder.v_no.setText("-");


        }else{
            holder.v_no.setBackgroundResource(R.drawable.bg);


        }

        if(position ==0){
            holder.one.setVisibility(View.VISIBLE);
            holder.two.setVisibility(View.VISIBLE);
            holder.three.setVisibility(View.VISIBLE);
            holder.four.setVisibility(View.VISIBLE);
            holder.five.setVisibility(View.VISIBLE);
            holder.six.setVisibility(View.VISIBLE);
            holder.seven.setVisibility(View.VISIBLE);
            holder.eight.setVisibility(View.VISIBLE);
            holder.nine.setVisibility(View.VISIBLE);

        }


        else{
            holder.one.setVisibility(View.GONE);
            holder.two.setVisibility(View.GONE);
            holder.three.setVisibility(View.GONE);
            holder.four.setVisibility(View.GONE);
            holder.five.setVisibility(View.GONE);
            holder.six.setVisibility(View.GONE);
            holder.seven.setVisibility(View.GONE);
            holder.eight.setVisibility(View.GONE);
            holder.nine.setVisibility(View.GONE);


        }




    }




    // total number of rows
    @Override
    public int getItemCount() {

        return show_models.size();

    }



    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView s_no,p_name,place,m_name,units,price,v_no,total,date;
        TextView one,two,three,four,five,six,seven,eight,nine;





        ViewHolder(View itemView) {
            super(itemView);



            s_no = itemView.findViewById(R.id.txt1);
            p_name = itemView.findViewById(R.id.txt2);
            place = itemView.findViewById(R.id.txt3);
            m_name = itemView.findViewById(R.id.txt4);
            units = itemView.findViewById(R.id.txt5);
            price = itemView.findViewById(R.id.txt6);
            v_no = itemView.findViewById(R.id.txt7);
            total = itemView.findViewById(R.id.txt8);
            date = itemView.findViewById(R.id.txt9);

            one = itemView.findViewById(R.id.one);
            two = itemView.findViewById(R.id.two);
            three = itemView.findViewById(R.id.three);
            four = itemView.findViewById(R.id.four);
            five = itemView.findViewById(R.id.five);
            six = itemView.findViewById(R.id.six);
            seven = itemView.findViewById(R.id.seven);
            eight = itemView.findViewById(R.id.eight);
            nine =itemView.findViewById(R.id.nine);






        }



    }





}