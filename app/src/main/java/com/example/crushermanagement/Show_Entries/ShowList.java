package com.example.crushermanagement.Show_Entries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.crushermanagement.Adapter;
import com.example.crushermanagement.FrontPage;
import com.example.crushermanagement.Old_entry.Old_entry;
import com.example.crushermanagement.Old_entry.Old_entry_adapter;
import com.example.crushermanagement.R;
import com.example.crushermanagement.User_List;
import com.example.crushermanagement.User_model;
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

public class ShowList extends AppCompatActivity implements Show_Adapter.OnItemClickListener{

    RecyclerView recyclerView;
      List<Show_Model> show_models;

    Show_Adapter show_adapter;
    Button old_entry;
    AlertDialog alertDialog;
    EditText editText;
    String order_no, party_name,vehicle_no,place,total_amount,material,id,date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);


        Intent intent = getIntent();
        id = intent.getStringExtra("id");



        recyclerView = findViewById(R.id.recycle_show);
        old_entry = findViewById(R.id.button);

        old_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(ShowList.this, Old_entry.class);
                startActivity(intent1);
            }
        });

        show_list();


    }

    public  void show_list(){
        String url = "http://sbm.spksystems.in/public/api/delivery/"+id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @SuppressLint("CheckResult")
            @Override
            public void onResponse(JSONObject response) {

                try {

                    show_models = new ArrayList<>();

                 JSONObject jsonObject = response.getJSONObject("data");



                    JSONArray jsonArray = jsonObject.getJSONArray("materials");

                 id = jsonObject.getString("id");
                 date = jsonObject.getString("date");
                 order_no = jsonObject.getString("order_no");
                party_name = jsonObject.getString("party_name");
                vehicle_no = jsonObject.getString("vehicle_no");
                place = jsonObject.getString("place");
                total_amount = jsonObject.getString("total_amount");




                 for(int i=0;i<jsonArray.length();i++){
                     JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    material = jsonObject1.getString("material");



                     Show_Model model = new Show_Model();
                     model.setS_no(order_no);
                     model.setP_name(party_name);
                     model.setPlace(place);
                     model.setM_name(material);
                     model.setV_no(vehicle_no);
                     model.setRate(total_amount);
                     model.setId(id);

                     show_models.add(model);



                 }





                } catch (Exception e) {
                    e.printStackTrace();


                }


                show_adapter = new Show_Adapter(ShowList.this, show_models);
                recyclerView.setAdapter(show_adapter);
                show_adapter.setOnItemClickListener(ShowList.this);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ShowList.this) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                };
                recyclerView.setLayoutManager(linearLayoutManager);



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
                params.put("Authorization","Bearer "+ PreferenceUtils.getToken(ShowList.this));


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ShowList.this);
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);
//        requestQueue.getCache().remove(jsonObjectRequest.getCacheKey());

    }



    @Override
    public void onItemClick(int position) {


        alertDialog = new AlertDialog.Builder(ShowList.this).create();
        editText = new EditText(ShowList.this);
        editText.setTextSize(15);
        editText.setPadding(30, 50, 0, 20);
        editText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        alertDialog.setTitle("Edit vehicle number");
        alertDialog.setView(editText);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {






                String URL = "http://sbm.spksystems.in/public/api/delivery/update/"+id;

                JSONObject jsonBody = new JSONObject();


                try {

                    jsonBody.put("vehicle_no", editText.getText().toString());



                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {



                            Log.i("wspijferqopijfr",response.toString());
                            try {
                                String Success = response.getString("success");
                                String msg = response.getString("message");

                                show_models = new ArrayList<>();



                                if (Success.equals("true")) {

                                    Toast.makeText(ShowList.this, msg, Toast.LENGTH_SHORT).show();
                                    Show_Model model = new Show_Model();

                                    model.setS_no(order_no);
                                    model.setP_name(party_name);
                                    model.setPlace(place);
                                    model.setM_name(material);
                                    model.setRate(total_amount);
                                    model.setV_no(editText.getText().toString());



                                    show_models.add(model);

                                    recyclerView.setLayoutManager(new LinearLayoutManager(ShowList.this));

                                    show_adapter =  new Show_Adapter(ShowList.this,show_models);
                                    show_adapter.setOnItemClickListener(ShowList.this);
                                    recyclerView.setAdapter(show_adapter);



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

                            Toast.makeText(ShowList.this, "Vehicle Number is required!", Toast.LENGTH_SHORT).show();




                        }
                    })
                    {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("Accept","application/json");
                            params.put("Authorization","Bearer "+ PreferenceUtils.getToken(ShowList.this));
                            return params;
                        }

                    };

                    jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                            10000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    RequestQueue requestQueue = Volley.newRequestQueue(ShowList.this);
                    requestQueue.getCache().clear();
                    requestQueue.add(jsonObjectRequest);



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
    public void onBackPressed() {


        Intent intent = new Intent(ShowList.this,FrontPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);


    }
}