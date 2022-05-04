package com.example.crushermanagement.Old_entry;

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

import com.example.crushermanagement.FrontPage;
import com.example.crushermanagement.R;

import java.util.List;

public class View_Adapter extends RecyclerView.Adapter<View_Adapter.ViewHolder> {



   List<Old_Model> oldModelList;
   private Context context;



    public static View_Adapter.OnItemClickListener mListener;



    public interface OnItemClickListener{
        void onItemClick(int position);

    }


    public void setOnItemClickListener(View_Adapter.OnItemClickListener listener){

        mListener = listener;


    }

    public View_Adapter(Context context, List<Old_Model> oldModelList) {
        this.context = context;
        this.oldModelList = oldModelList;

    }



    @NonNull
    @Override
    public View_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler2,parent,false);


        return new View_Adapter.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt1.setText(oldModelList.get(position).getS_no());
        holder.txt2.setText(oldModelList.get(position).getP_name());
        holder.txt3.setText(oldModelList.get(position).getPlace());
        holder.txt4.setText(oldModelList.get(position).getM_name());
        holder.txt5.setText(oldModelList.get(position).getV_no());
        holder.txt6.setText(oldModelList.get(position).getRate());



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


        return oldModelList.size();

    }



    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt1, txt2, txt3, txt4, txt5, txt6;

        TextView one, two, three, four, five, six,seven;




        ViewHolder(View itemView) {
            super(itemView);

            txt1 = itemView.findViewById(R.id.txt1);
            txt2 = itemView.findViewById(R.id.txt2);
            txt3 = itemView.findViewById(R.id.txt3);
            txt4 = itemView.findViewById(R.id.txt4);
            txt5 = itemView.findViewById(R.id.txt5);
            txt6 = itemView.findViewById(R.id.txt6);


            one = itemView.findViewById(R.id.one);
            two = itemView.findViewById(R.id.two);
            three = itemView.findViewById(R.id.three);
            four = itemView.findViewById(R.id.four);
            five = itemView.findViewById(R.id.five);
            six = itemView.findViewById(R.id.six);
            seven = itemView.findViewById(R.id.seven);

            txt5.setOnClickListener(new View.OnClickListener() {

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