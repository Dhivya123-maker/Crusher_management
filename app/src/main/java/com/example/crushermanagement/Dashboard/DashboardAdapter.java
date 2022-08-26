package com.example.crushermanagement.Dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crushermanagement.AllEntries.AllEntryModel;
import com.example.crushermanagement.R;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {



   List<AllEntryModel> oldModelList;
   private Context context;



    public static OnItemClickListener mListener;



    public interface OnItemClickListener{
        void onItemClick(int position);



    }


    public void setOnItemClickListener(OnItemClickListener listener){

        mListener = listener;



    }





    public DashboardAdapter(Context context, List<AllEntryModel> oldModelList) {
        this.context = context;
        this.oldModelList = oldModelList;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboardentry_recycle,parent,false);


        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.s_no.setText(oldModelList.get(position).getS_no());
        holder.p_name.setText(oldModelList.get(position).getP_name());
        holder.place.setText(oldModelList.get(position).getPlace());
        holder.m_name.setText(oldModelList.get(position).getM_name());
        holder.units.setText(oldModelList.get(position).getUnit());
        holder.price.setText(oldModelList.get(position).getPrice());
        holder.v_no.setText(oldModelList.get(position).getV_no());
        holder.total.setText(oldModelList.get(position).getRate());
        holder.date_txt.setText(oldModelList.get(position).getDate());



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
            holder.ten.setVisibility(View.VISIBLE);

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
            holder.ten.setVisibility(View.GONE);


        }






    }





    // total number of rows
    @Override
    public int getItemCount() {

      return oldModelList.size();

    }



    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView s_no,p_name,place,m_name,units,price,v_no,total,date_txt;

        TextView one, two, three, four, five, six,seven,eight,nine,ten;
        LinearLayout lnr;

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
            date_txt = itemView.findViewById(R.id.date_txt);
            lnr = itemView.findViewById(R.id.txt9);


            one = itemView.findViewById(R.id.one);
            two = itemView.findViewById(R.id.two);
            three = itemView.findViewById(R.id.three);
            four = itemView.findViewById(R.id.four);
            five = itemView.findViewById(R.id.five);
            six = itemView.findViewById(R.id.six);
            seven = itemView.findViewById(R.id.seven);
            eight = itemView.findViewById(R.id.eight);
            nine = itemView.findViewById(R.id.nine);
            ten = itemView.findViewById(R.id.ten);

            lnr.setOnClickListener(new View.OnClickListener() {

                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View view) {


                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);


                        }
                    }




                }

            });

        }


    }





}