package com.example.crushermanagement.AllEntries;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crushermanagement.R;

import java.util.List;

public class AllEntryAdapter extends RecyclerView.Adapter<AllEntryAdapter.ViewHolder> {



   List<AllEntryModel> oldModelList;
   private Context context;


    public static AllEntryAdapter.OnItemClickListener mListener1, mListener2;

    public interface OnItemClickListener{

        void onItemClick1(int position1);

        void onItemClick2(int position2);




    }


    public void setOnItemClickListener(AllEntryAdapter.OnItemClickListener listener){


        mListener1 = listener;
        mListener2 = listener;




    }



    public AllEntryAdapter(Context context, List<AllEntryModel> oldModelList) {
        this.context = context;
        this.oldModelList = oldModelList;

    }



    @NonNull
    @Override
    public AllEntryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allentry_recycle,parent,false);


        return new AllEntryAdapter.ViewHolder(view);


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
        holder.date_text.setText(oldModelList.get(position).getDate());


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
            holder.eleven.setVisibility(View.VISIBLE);

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
            holder.eleven.setVisibility(View.GONE);


        }



    }





    // total number of rows
    @Override
    public int getItemCount() {


        return oldModelList.size();

    }




    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView s_no,p_name,place,m_name,units,price,v_no,total,date_text;


        TextView one, two, three, four, five, six,seven,eight,nine,ten,eleven;
        LinearLayout delete_lnr,edit_lnr;
        ImageView delete,edit_img;



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
            ten = itemView.findViewById(R.id.ten);
            date_text = itemView.findViewById(R.id.date_text);




            delete = itemView.findViewById(R.id.image1);
            edit_img = itemView.findViewById(R.id.image2);
            delete_lnr = itemView.findViewById(R.id.txt9);
            edit_lnr = itemView.findViewById(R.id.txt10);


            one = itemView.findViewById(R.id.one);
            two = itemView.findViewById(R.id.two);
            three = itemView.findViewById(R.id.three);
            four = itemView.findViewById(R.id.four);
            five = itemView.findViewById(R.id.five);
            six = itemView.findViewById(R.id.six);
            seven = itemView.findViewById(R.id.seven);
            eight = itemView.findViewById(R.id.eight);
            nine = itemView.findViewById(R.id.nine);
            eleven = itemView.findViewById(R.id.eleven);







            delete_lnr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (mListener1 != null) {
                        int position1= getAdapterPosition();
                        if (position1 != RecyclerView.NO_POSITION) {
                            mListener1.onItemClick1(position1);


                        }
                    }


                }
            });
            edit_lnr.setOnClickListener(new View.OnClickListener() {

                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onClick(View view) {

                    if (mListener2 != null) {
                        int position2 = getAdapterPosition();
                        if (position2 != RecyclerView.NO_POSITION) {
                            mListener2.onItemClick2(position2);


                        }
                    }


                }

            });

        }


    }




}