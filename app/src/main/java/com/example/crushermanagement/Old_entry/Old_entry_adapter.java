package com.example.crushermanagement.Old_entry;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.crushermanagement.FrontPage;
import com.example.crushermanagement.R;
import com.example.crushermanagement.Show_Entries.ShowList;
import com.example.crushermanagement.Show_Entries.Show_Model;
import com.example.crushermanagement.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Old_entry_adapter extends RecyclerView.Adapter<Old_entry_adapter.ViewHolder> {



   List<Old_Model> oldModelList;
   private Context context;



    public static Old_entry_adapter.OnItemClickListener mListener,mListener1;




    public interface OnItemClickListener{
        void onItemClick(int position);
        void onItemClick1(int position);
    }


    public void setOnItemClickListener(Old_entry_adapter.OnItemClickListener listener){

        mListener = listener;
        mListener1 = listener;

    }

    public Old_entry_adapter(Context context,List<Old_Model> oldModelList) {
        this.context = context;
        this.oldModelList = oldModelList;

    }



    @NonNull
    @Override
    public Old_entry_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler1,parent,false);


        return new Old_entry_adapter.ViewHolder(view);


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
            holder.seven.setVisibility(View.VISIBLE);
        }


        else{
            holder.one.setVisibility(View.GONE);
            holder.two.setVisibility(View.GONE);
            holder.three.setVisibility(View.GONE);
            holder.four.setVisibility(View.GONE);
            holder.five.setVisibility(View.GONE);
            holder.six.setVisibility(View.GONE);
            holder.seven.setVisibility(View.GONE);

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
        LinearLayout txt7;
        TextView one, two, three, four, five, six,seven;

        ImageView delete;



        ViewHolder(View itemView) {
            super(itemView);

            txt1 = itemView.findViewById(R.id.txt1);
            txt2 = itemView.findViewById(R.id.txt2);
            txt3 = itemView.findViewById(R.id.txt3);
            txt4 = itemView.findViewById(R.id.txt4);
            txt5 = itemView.findViewById(R.id.txt5);
            txt6 = itemView.findViewById(R.id.txt6);
            txt7 = itemView.findViewById(R.id.txt7);
            delete = itemView.findViewById(R.id.image1);


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



            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    if (mListener1 != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener1.onItemClick1(position);


                        }
                    }


                }
            });
        }


    }




}