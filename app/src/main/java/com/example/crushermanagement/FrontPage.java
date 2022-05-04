package com.example.crushermanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.crushermanagement.Old_entry.Old_Model;
import com.example.crushermanagement.Old_entry.Old_entry;
import com.example.crushermanagement.Old_entry.Old_entry_adapter;
import com.example.crushermanagement.Old_entry.View_Adapter;
import com.example.crushermanagement.entries.EntryActivity;
import com.example.crushermanagement.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrontPage extends AppCompatActivity implements View_Adapter.OnItemClickListener {

    ImageView add_menu,imageView;
    Button entry;
    private long pressedTime;
    RecyclerView recyclerView;
    List<Old_Model> oldModelList;
    View_Adapter view_adapter;
    TextView view;
    EditText editText;
    String id,date,order_no, party_name,vehicle_no,place,total_amount,material;
    android.app.AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

                add_menu = findViewById(R.id.add_menu);
                imageView = findViewById(R.id.menu);
                entry = findViewById(R.id.entry);
                recyclerView = findViewById(R.id.recycle_front);
                view = findViewById(R.id.view);


                Intent intent = getIntent();
                String mail = intent.getStringExtra("mail");



                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(FrontPage.this, Old_entry.class);
                        startActivity(intent);
                    }
                });
                entry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(FrontPage.this, EntryActivity.class);
                        startActivity(intent);

                    }
                });

        add_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FrontPage.this, Add_user.class);
                startActivity(intent);

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(FrontPage.this).setIcon(R.drawable.ic_baseline_warning_24)
                        .setMessage("Are you sure want to logout")
                        .setNegativeButton(android.R.string.no,null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                logout();


                            }
                        }).create().show();


            }
        });
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(FrontPage.this);
        String email = preferences.getString("mail", null);


        if(email.equals("ns@spksystems.in")) {
            add_menu.setVisibility(View.VISIBLE);

        }else{

            add_menu.setVisibility(View.GONE);
        }
        show();


    }
    public void logout(){
        String JSON_URL = "http://sbm.spksystems.in/public/api/logout";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONObject>() {
            @SuppressLint("CheckResult")
            @Override
            public void onResponse(JSONObject response) {

                try {


                    String Success = response.getString("success");
                    String msg = response.getString("message");


                    if(Success.equals("true")){

                        finishAffinity();
                        PreferenceUtils.saveid(null,FrontPage.this);
                        PreferenceUtils.saveToken(null,FrontPage.this);


                    }else{

                        Toast.makeText(FrontPage.this, msg, Toast.LENGTH_SHORT).show();
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
                params.put("Authorization","Bearer "+ PreferenceUtils.getToken(FrontPage.this));


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(FrontPage.this);
        requestQueue.add(jsonObjectRequest);

    }
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            new AlertDialog.Builder(FrontPage.this).setIcon(R.drawable.ic_baseline_warning_24)
                    .setMessage("Are you sure want to exit")
                    .setNegativeButton(android.R.string.no,null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            FrontPage.super.onBackPressed();
                            finishAffinity();
                        }
                    }).create().show();

        }
        else  {

            Toast.makeText(FrontPage.this, "Press back to exit", Toast.LENGTH_SHORT).show();

        }
        pressedTime = System.currentTimeMillis();





    }


    @Override
    public void onItemClick(int position) {
        Old_Model old_model = oldModelList.get(position);
        String Id = old_model.getId();

        Log.i("kfnwijfhwhgfreijfqr",Id);

        alertDialog = new android.app.AlertDialog.Builder(FrontPage.this).create();
        editText = new EditText(FrontPage.this);
        editText.setTextSize(15);
        editText.setPadding(30, 50, 0, 20);
        editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        alertDialog.setTitle("Edit vehicle number");
        alertDialog.setView(editText);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "save", new DialogInterface.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {




                final ProgressDialog ringProgressDialog = ProgressDialog.show(FrontPage.this, "Loading...", "Please Wait",
                        true, false);
                ringProgressDialog.setIndeterminate(true);
                new Thread(new Runnable() {
                    public void run() {
                        try {

                            Thread.sleep(2000);



                            if (ringProgressDialog != null && ringProgressDialog.isShowing()) {
                                ringProgressDialog.dismiss();

                            }



                        } catch (Exception e) {

                        }

                    }

                }).start();





                String URL = "http://sbm.spksystems.in/public/api/delivery/update/"+Id;
                Log.i("SKJDWIJFDHW",URL);

                JSONObject jsonBody = new JSONObject();


                try {

                    jsonBody.put("vehicle_no", editText.getText().toString());



                    JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {



                            Log.i("wspijferqopijfr",response.toString());
                            try {
                                String Success = response.getString("success");
                                String msg = response.getString("message");



                                if (Success.equals("true")) {
                                    oldModelList.clear();
                                    view_adapter.notifyDataSetChanged();


                                    show();



                                }else{

                                }
                            } catch (Exception e) {
                                e.printStackTrace();

                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Charset charset = Charset.defaultCharset();
                            String str = new String(error.networkResponse.data,charset);



                            Toast.makeText(FrontPage.this, "Vehicle Number is required!", Toast.LENGTH_SHORT).show();




                        }
                    })
                    {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("Accept","application/json");
                            params.put("Authorization","Bearer "+ PreferenceUtils.getToken(FrontPage.this));
                            return params;
                        }

                    };

                    jsonObjectRequest1.setRetryPolicy(new DefaultRetryPolicy(
                            10000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    RequestQueue requestQueue1 = Volley.newRequestQueue(FrontPage.this);
                    requestQueue1.add(jsonObjectRequest1);
                    requestQueue1.getCache().remove(jsonObjectRequest1.getCacheKey());


//                    old_entry_adapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


        });


        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog

                        dialog.cancel();
                    }
                });

        alertDialog.show();

        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setAllCaps(false);
        alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setAllCaps(false);



    }


    public void show(){

        String url = "http://sbm.spksystems.in/public/api/delivery";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @SuppressLint("CheckResult")
            @Override
            public void onResponse(JSONObject response) {



                try {


                    oldModelList = new ArrayList<>();



                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i = 0; i < 10; i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);


                        JSONArray jsonArray1 = jsonObject.getJSONArray("materials");
                        id = jsonObject.getString("id");
                        date = jsonObject.getString("date");
                        order_no = jsonObject.getString("order_no");
                        party_name = jsonObject.getString("party_name");
                        vehicle_no = jsonObject.getString("vehicle_no");
                        place = jsonObject.getString("place");
                        total_amount = jsonObject.getString("total_amount");


                        for (int i1 = 0; i1 < jsonArray1.length(); i1++) {
                            JSONObject jsonObject1 = jsonArray1.getJSONObject(i1);
                            material = jsonObject1.getString("material");

                            Log.i("materiallllllllllllllll", material);


                            Old_Model model = new Old_Model();
                            model.setS_no(order_no);
                            model.setP_name(party_name);
                            model.setPlace(place);
                            model.setM_name(material);
                            model.setV_no(vehicle_no);
                            model.setRate(total_amount);
                            model.setId(id);


                            oldModelList.add(model);


                        }
                    }




                    Log.i("kdjhforirwhto3", String.valueOf(jsonArray.length()));

                    view_adapter = new View_Adapter(FrontPage.this, oldModelList);
                    recyclerView.setAdapter(view_adapter);
                    view_adapter.setOnItemClickListener(FrontPage.this);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FrontPage.this) {
                        @Override
                        public boolean canScrollVertically() {
                            return false;
                        }
                    };
                    recyclerView.setLayoutManager(linearLayoutManager);

                } catch (Exception e) {
                    e.printStackTrace();


                }


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();



                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer " + PreferenceUtils.getToken(FrontPage.this));


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(FrontPage.this);
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);
    }


}