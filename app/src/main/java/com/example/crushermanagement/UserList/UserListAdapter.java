package com.example.crushermanagement.UserList;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.crushermanagement.R;
import com.example.crushermanagement.utils.PreferenceUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {



    List<UserListModel> userModelList;
    private Context context;
    String id;


    public UserListAdapter(Context context, List<UserListModel>userModelList) {
        this.context = context;
        this.userModelList = userModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_recycle,parent,false);


        return new ViewHolder(view);


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
        UserListAdapter adapter;

        ViewHolder(View itemView) {
            super(itemView);


            textView = itemView.findViewById(R.id.name_txt);
            textView1 = itemView.findViewById(R.id.mail_txt);
            imageView = itemView.findViewById(R.id.image);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Dialog dialog;

                    dialog=new Dialog(view.getContext());

                    // set custom dialog
                    dialog.setContentView(R.layout.alert_dailogbox);

                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                    // set transparent background
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    // show dialog
                    dialog.show();

                    // Initialize and assign variable
                    TextView title=dialog.findViewById(R.id.title);
                    RelativeLayout no=dialog.findViewById(R.id.no);
                    RelativeLayout yes=dialog.findViewById(R.id.yes);

                    title.setText("Are you sure want to delete");


                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.dismiss();
                        }
                    });

                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            int actualPosition =  getAdapterPosition();

                            Id = userModelList.get(actualPosition).getId();

                            userModelList.remove(actualPosition);
                            notifyItemRemoved(actualPosition);
                            notifyItemRangeChanged(actualPosition, userModelList.size());



                            String JSON_URL = "http://sbm.spksystems.in/public/api/user/delete/"+Id;
                            Log.i("swkhdfei2ufheo",JSON_URL);

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
                    });



                }
            });




        }



    }




}
