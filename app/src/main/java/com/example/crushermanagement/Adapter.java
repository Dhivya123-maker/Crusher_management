package com.example.crushermanagement;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.crushermanagement.utils.PreferenceUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {



    List<User_model> userModelList;
    private Context context;
    String id;


    public Adapter(Context context, List<User_model>userModelList) {
        this.context = context;
        this.userModelList = userModelList;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler,parent,false);


        return new Adapter.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(userModelList.get(position).getName());
        holder.textView1.setText(userModelList.get(position).getEmail());


    }





    // total number of rows
    @Override
    public int getItemCount() {

        return userModelList.size();

    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView, textView1;
        ImageView imageView;
        String Id;
        Adapter adapter;

        ViewHolder(View itemView) {
            super(itemView);


            textView = itemView.findViewById(R.id.name_txt);
            textView1 = itemView.findViewById(R.id.mail_txt);
            imageView = itemView.findViewById(R.id.image);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    new AlertDialog.Builder(view.getContext()).setIcon(R.drawable.ic_baseline_warning_24)
                            .setMessage("Are you sure want to delete user")
                            .setNegativeButton(android.R.string.no,null)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {



                                    int actualPosition =  getAdapterPosition();

                                    Id = userModelList.get(actualPosition).getId();

                                    userModelList.remove(actualPosition);
                                    notifyItemRemoved(actualPosition);
                                    notifyItemRangeChanged(actualPosition, userModelList.size());



                                    String JSON_URL = "http://sbm.spksystems.in/public/api/user/delete/"+Id;
                                    Log.i("swkhdfei2ufheo",JSON_URL);
//
                                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, JSON_URL, null, new Response.Listener<JSONObject>() {
                                        @SuppressLint("CheckResult")
                                        @Override
                                        public void onResponse(JSONObject response) {

                                            try {



                                                String Success = response.getString("success");
                                                String msg = response.getString("message");
                                                if(Success.equals("true")){

                                                    Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                                                }else{

                                                }




                                            } catch (Exception e) {
                                                e.printStackTrace();


                                            }


                                        }


                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {


                                        }
                                    }){
                                        @Override
                                        protected Map<String,String> getParams(){
                                            Map<String,String> params = new HashMap<String, String>();



                                            return params;
                                        }

                                        @Override
                                        public Map<String, String> getHeaders() throws AuthFailureError {
                                            Map<String,String> params = new HashMap<String, String>();
                                            params.put("Accept","application/json");
                                            params.put("Authorization","Bearer "+ PreferenceUtils.getToken(context.getApplicationContext()));


                                            return params;
                                        }
                                    };

                                    RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
                                    requestQueue.add(jsonObjectRequest);


                                }
                            }).create().show();




                }
            });




        }



    }




}
