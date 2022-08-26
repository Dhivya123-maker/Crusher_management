package com.example.crushermanagement.NewEntry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crushermanagement.R;

import java.util.List;

public class NewEntryAdapter extends RecyclerView.Adapter<NewEntryAdapter.ViewHolder> {



    List<NewEntryModel> entryModelList;

    private Context context;


    public NewEntryAdapter(Context context, List<NewEntryModel> entryModelList ) {
        this.context = context;
        this.entryModelList = entryModelList;
    }

    @NonNull
    @Override
    public NewEntryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_recycle,parent,false);


        return new NewEntryAdapter.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textView.setText(entryModelList.get(position).getMaterial());
        holder.textView1.setText(entryModelList.get(position).getUnit());
        holder.txt2.setText(entryModelList.get(position).getPrice());


        if(position ==0){
            holder.one.setVisibility(View.VISIBLE);
            holder.two.setVisibility(View.VISIBLE);
            holder.three.setVisibility(View.VISIBLE);
            holder.four.setVisibility(View.VISIBLE);
            holder.five.setVisibility(View.VISIBLE);
            holder.six.setVisibility(View.VISIBLE);

        }


        else{
            holder.one.setVisibility(View.GONE);
            holder.two.setVisibility(View.GONE);
            holder.three.setVisibility(View.GONE);
            holder.four.setVisibility(View.GONE);
            holder.five.setVisibility(View.GONE);
            holder.six.setVisibility(View.GONE);


        }


    }




    // total number of rows
    @Override
    public int getItemCount() {

        return entryModelList.size();

    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView, textView1,txt2;
        LinearLayout lnr;
        TextView one, two, three, four, five, six,seven;



        ViewHolder(View itemView) {
            super(itemView);


            textView = itemView.findViewById(R.id.m_name);
            textView1 = itemView.findViewById(R.id.units);
            txt2 = itemView.findViewById(R.id.price);
            lnr = itemView.findViewById(R.id.lnr);



            one = itemView.findViewById(R.id.one);
            two = itemView.findViewById(R.id.two);
            three = itemView.findViewById(R.id.three);
            four = itemView.findViewById(R.id.four);
            five = itemView.findViewById(R.id.five);
            six = itemView.findViewById(R.id.six);





        }



    }


}