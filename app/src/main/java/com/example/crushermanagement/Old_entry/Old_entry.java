package com.example.crushermanagement.Old_entry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.example.crushermanagement.FrontPage;
import com.example.crushermanagement.LoadingDialog;
import com.example.crushermanagement.R;

import com.example.crushermanagement.Show_Entries.Show_Model;
import com.example.crushermanagement.entries.EntryActivity;
import com.example.crushermanagement.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Old_entry extends AppCompatActivity implements Old_entry_adapter.OnItemClickListener{

    RecyclerView recyclerView;
    List<Old_Model> oldModelList;
    Old_entry_adapter old_entry_adapter;
    View_Adapter old_entry_adapter1;
    String id,date,order_no, party_name,vehicle_no,place,total_amount,material;
    AlertDialog alertDialog;
    EditText editText;
    ImageView calender_img;
    Calendar calendar;
    DatePickerDialog dd;
    TextView date_txt;
    private ProgressBar pgsBar;
    String Date;
    private int i1 = 0;
    private Handler hdlr = new Handler();
    LoadingDialog loadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_old_entry);

        recyclerView = findViewById(R.id.recycle_old);
        loadingDialog = new LoadingDialog(this);

        calender_img = findViewById(R.id.calender_img);
        date_txt = findViewById(R.id.date_txt);





     edit();


        calender_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener dateA = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                        date_txt.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                        edit();


                    }
                };

                new DatePickerDialog(Old_entry.this, dateA, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });




    }


    @Override
    public void onItemClick(int position) {

        Old_Model old_model = oldModelList.get(position);
        String Id = old_model.getId();

        Log.i("kfnwijfhwhgfreijfqr",Id);

        alertDialog = new AlertDialog.Builder(Old_entry.this).create();
        editText = new EditText(Old_entry.this);
        editText.setTextSize(15);
        editText.setPadding(30, 50, 0, 20);
        editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        alertDialog.setTitle("Edit vehicle number");
        alertDialog.setView(editText);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "save", new DialogInterface.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {




           final ProgressDialog ringProgressDialog = ProgressDialog.show(Old_entry.this, "Loading...", "Please Wait",
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
                                    old_entry_adapter.notifyDataSetChanged();
                                    edit();




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



                            Toast.makeText(Old_entry.this, "Vehicle Number is required!", Toast.LENGTH_SHORT).show();




                        }
                    })
                    {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("Accept","application/json");
                            params.put("Authorization","Bearer "+ PreferenceUtils.getToken(Old_entry.this));
                            return params;
                        }

                    };

                    jsonObjectRequest1.setRetryPolicy(new DefaultRetryPolicy(
                            10000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    RequestQueue requestQueue1 = Volley.newRequestQueue(Old_entry.this);
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

    @Override
    public void onItemClick1(int position) {


        Old_Model old_model1 = oldModelList.get(position);
        String Id = old_model1.getId();

        new androidx.appcompat.app.AlertDialog.Builder(Old_entry.this).setIcon(R.drawable.ic_baseline_warning_24)
                .setMessage("Are you sure want to delete delivery")
                .setNegativeButton(android.R.string.no,null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        final ProgressDialog ringProgressDialog = ProgressDialog.show(Old_entry.this, "Loading...", "Please Wait",
                                true, false);
                        ringProgressDialog.setIndeterminate(true);



                        new Thread(new Runnable() {
                            public void run() {
                                try {

                                    Thread.sleep(3000);



                                    if (ringProgressDialog != null && ringProgressDialog.isShowing()) {
                                        ringProgressDialog.dismiss();

                                    }



                                } catch (Exception e) {

                                }

                            }

                        }).start();




                        String JSON_URL = "http://sbm.spksystems.in/public/api/delivery/delete/"+Id;
                        Log.i("swkhdfei2ufheo",JSON_URL);

                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, JSON_URL, null, new Response.Listener<JSONObject>() {
                            @SuppressLint("CheckResult")
                            @Override
                            public void onResponse(JSONObject response) {

                                try {



                                    String Success = response.getString("success");
                                    String msg = response.getString("message");
                                    if(Success.equals("true")){

                                        oldModelList.remove(position);
                                        old_entry_adapter.notifyItemRemoved(position);
                                        old_entry_adapter. notifyItemRangeChanged(position, oldModelList.size());


                                        Toast.makeText(Old_entry.this, msg, Toast.LENGTH_SHORT).show();

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

                                params.put("Authorization","Bearer "+ PreferenceUtils.getToken(Old_entry.this));


                                return params;
                            }
                        };

                        RequestQueue requestQueue = Volley.newRequestQueue(Old_entry.this);
                        requestQueue.add(jsonObjectRequest);









                    }
                }).create().show();
    }

    public void edit(){
        if(date_txt.getText().equals("Choose date")) {

            final ProgressDialog ringProgressDialog = ProgressDialog.show(Old_entry.this, "Loading...", "Please Wait",
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



            String url = "http://sbm.spksystems.in/public/api/delivery";


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @SuppressLint("CheckResult")
                @Override
                public void onResponse(JSONObject response) {



                    try {


                        oldModelList = new ArrayList<>();


                        JSONArray jsonArray = response.getJSONArray("data");

                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            JSONArray jsonArray1 = jsonObject.getJSONArray("materials");
                            id = jsonObject.getString("id");
                            date = jsonObject.getString("date");
                            order_no = jsonObject.getString("order_no");
                            party_name = jsonObject.getString("party_name");
                            vehicle_no = jsonObject.getString("vehicle_no");
                            place = jsonObject.getString("place");
                            total_amount = jsonObject.getString("total_amount");





                            for(int i1=0;i1<jsonArray1.length();i1++){
                                JSONObject jsonObject1 = jsonArray1.getJSONObject(i1);
                                material = jsonObject1.getString("material");

                                Log.i("materiallllllllllllllll",material);


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


//                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Old_entry.this);
//                        String email = preferences.getString("mail", null);
//
//
//                        if(email.equals("ns@spksystems.in")) {

                            old_entry_adapter = new Old_entry_adapter(Old_entry.this, oldModelList);
                            recyclerView.setAdapter(old_entry_adapter);
                            old_entry_adapter.setOnItemClickListener(Old_entry.this);

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Old_entry.this) {
                                @Override
                                public boolean canScrollVertically() {
                                    return false;
                                }
                            };
                            recyclerView.setLayoutManager(linearLayoutManager);

//                        }else{
//                            old_entry_adapter1 = new View_Adapter(Old_entry.this, oldModelList);
//                            recyclerView.setAdapter(old_entry_adapter1);
////                            old_entry_adapter1.setOnItemClickListener(Old_entry.this);
//
//                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Old_entry.this) {
//                                @Override
//                                public boolean canScrollVertically() {
//                                    return false;
//                                }
//                            };
//                            recyclerView.setLayoutManager(linearLayoutManager);
//
//
//                        }



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
//                params.put("date",date_txt.getText().toString());


                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Accept", "application/json");
                    params.put("Authorization", "Bearer " + PreferenceUtils.getToken(Old_entry.this));


                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(Old_entry.this);
            requestQueue.getCache().clear();
            requestQueue.add(jsonObjectRequest);
//            requestQueue.getCache().remove(jsonObjectRequest.getCacheKey());
        }else {


            final ProgressDialog ringProgressDialog = ProgressDialog.show(Old_entry.this, "Loading...", "Please Wait",
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



            String url = "http://sbm.spksystems.in/public/api/delivery?date="+date_txt.getText().toString();


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @SuppressLint("CheckResult")
                @Override
                public void onResponse(JSONObject response) {



                    try {


                        oldModelList = new ArrayList<>();


                        JSONArray jsonArray = response.getJSONArray("data");

                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            id = jsonObject1.getString("id");
                            date = jsonObject1.getString("date");
                            order_no = jsonObject1.getString("order_no");
                            party_name = jsonObject1.getString("party_name");
                            vehicle_no = jsonObject1.getString("vehicle_no");
                            place = jsonObject1.getString("place");
                            total_amount = jsonObject1.getString("total_amount");



                            JSONArray jsonArray1 = jsonObject1.getJSONArray("materials");

                            for(int i1=0;i1<jsonArray1.length();i1++){
                                JSONObject jsonObject2 = jsonArray1.getJSONObject(i1);
                                material = jsonObject2.getString("material");

                                Log.i("materiallllllllllllllll",material);


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




                            old_entry_adapter = new Old_entry_adapter(Old_entry.this, oldModelList);
                            recyclerView.setAdapter(old_entry_adapter);
                            old_entry_adapter.setOnItemClickListener(Old_entry.this);

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Old_entry.this) {
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
                    params.put("Authorization", "Bearer " + PreferenceUtils.getToken(Old_entry.this));


                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(Old_entry.this);
            requestQueue.getCache().clear();
            requestQueue.add(jsonObjectRequest);

        }

    }





}