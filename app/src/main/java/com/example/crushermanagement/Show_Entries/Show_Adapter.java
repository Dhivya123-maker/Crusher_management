package com.example.crushermanagement.Show_Entries;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.example.crushermanagement.MainActivity;
import com.example.crushermanagement.R;
import com.example.crushermanagement.entries.EntryActivity;
import com.example.crushermanagement.entries.Entry_Model;
import com.example.crushermanagement.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Show_Adapter extends RecyclerView.Adapter<Show_Adapter.ViewHolder> {



   List<Show_Model> show_models;
  Context context;




    public static Show_Adapter.OnItemClickListener mListener;

    public Show_Adapter(Context context, List<Show_Model> show_models) {
        this.context = context;
        this.show_models = show_models;

    }


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(Show_Adapter.OnItemClickListener listener){

        mListener = listener;

    }




    @NonNull
    @Override
    public Show_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_rercycle,parent,false);

        return new Show_Adapter.ViewHolder(view);


    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Show_Model model = show_models.get(position);
        holder.s_no.setText(model.getS_no());
        holder.p_name.setText(model.getP_name());
        holder.place.setText(model.getPlace());
        holder.m_name.setText(model.getM_name());
        holder.v_no.setText(model.getV_no());
        holder.total.setText(model.getRate());



    }




    // total number of rows
    @Override
    public int getItemCount() {

        return show_models.size();

    }



    // stores and recycles views as they are scrolled off screen
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView s_no,p_name,place,m_name,v_no,total;
        TextView one,two,three,four,five,six;





        ViewHolder(View itemView) {
            super(itemView);


            s_no = itemView.findViewById(R.id.txt1);
            p_name = itemView.findViewById(R.id.txt2);
            place = itemView.findViewById(R.id.txt3);
            m_name = itemView.findViewById(R.id.txt4);
            v_no = itemView.findViewById(R.id.txt5);
            total = itemView.findViewById(R.id.txt6);

            one = itemView.findViewById(R.id.one);
            two = itemView.findViewById(R.id.two);
            three = itemView.findViewById(R.id.three);
            four = itemView.findViewById(R.id.four);
            five = itemView.findViewById(R.id.five);
            six = itemView.findViewById(R.id.six);



            v_no.setOnClickListener(new View.OnClickListener() {


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