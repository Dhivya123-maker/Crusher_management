package com.example.crushermanagement.AllEntries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.crushermanagement.Edit;
import com.example.crushermanagement.NewEntry.NewEntryClass;
import com.example.crushermanagement.R;

import com.example.crushermanagement.utils.PreferenceUtils;
import com.github.ybq.android.spinkit.SpinKitView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AllEntryClass extends AppCompatActivity implements AllEntryAdapter.OnItemClickListener {

    RecyclerView recyclerView;
    List<AllEntryModel> oldModelList;
    AllEntryAdapter old_entry_adapter;

    String id;
    String date;
    String order_no;
    String party_name;
    String vehicle_no;
    String total_amount;
    String unit;
    String price;
    ImageView calender_img;
    Calendar calendar;
    TextView date_txt;
    SpinKitView spinKitView;

    String place_id,place_name,party_id,material_id,material_name;
    ArrayList<String> store_mat =  new ArrayList<>();
    ArrayList<String> store_unit = new ArrayList<>();
    ArrayList<String> store_price =  new ArrayList<>();

    String S_no,Id,S_party,S_place,S_vehicle,S_date,S_amount;

    String material_arraylist = "" ;
    String unit_arraylist ;
    String price_arraylist;
    String store_material_array;
    String store_unit_array;
    String store_price_array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_allentry);

        recyclerView = findViewById(R.id.recycle_old);


        spinKitView = findViewById(R.id.progressBar);

        calender_img = findViewById(R.id.calender_img);
        date_txt = findViewById(R.id.date_txt);

        spinKitView.setVisibility(View.VISIBLE);


      all_data();


        calender_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener dateA = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {

                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                        date_txt.setText(dayOfMonth+"-"+(monthOfYear+1)+"-"+year);
                     all_data();


                    }
                };

                new DatePickerDialog(AllEntryClass.this, dateA, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });





    }


    @Override
    public void onItemClick1(int position1) {


        Dialog dialog;

        dialog=new Dialog(AllEntryClass.this);

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
                spinKitView.setVisibility(View.VISIBLE);


                AllEntryModel old_model1 = oldModelList.get(position1);
                String Id = old_model1.getId();

                String JSON_URL = "http://sbm.spksystems.in/public/api/delivery/delete/"+Id;
                Log.i("swkhdfei2ufheo",JSON_URL);


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, JSON_URL, null, new Response.Listener<JSONObject>() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void onResponse(JSONObject response) {

                        spinKitView.setVisibility(View.GONE);
                        try {



                            String Success = response.getString("success");
                            String msg = response.getString("message");
                            if(Success.equals("true")){
                                Toast.makeText(AllEntryClass.this, msg, Toast.LENGTH_SHORT).show();
                                oldModelList.remove(position1);
                                old_entry_adapter.notifyItemRemoved(position1);



                            }else{

                            }




                        } catch (Exception e) {
                            e.printStackTrace();


                        }


                    }


                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        spinKitView.setVisibility(View.GONE);
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

                        params.put("Authorization","Bearer "+ PreferenceUtils.getToken(AllEntryClass.this));

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(AllEntryClass.this);
                requestQueue.add(jsonObjectRequest);








            }
        });



        }
    @Override
    public void onItemClick2(int position2) {
        AllEntryModel old_model = oldModelList.get(position2);
        Id = old_model.getId();
        S_no = old_model.getS_no();
        S_party = old_model.getP_name();
        S_place = old_model.getPlace();
        S_vehicle = old_model.getV_no();
        S_date = old_model.getDate();
        S_amount = old_model.getRate();


        Intent intent = new Intent(AllEntryClass.this, Edit.class);
        intent.putExtra("order_no",S_no);
        intent.putExtra("party_name",S_party);
        intent.putExtra("place_name",S_place);
        intent.putExtra("vehicle_no",S_vehicle);
        intent.putExtra("material_name",store_material_array);
        intent.putExtra("unit",store_unit_array);
        intent.putExtra("price",store_price_array);
        intent.putExtra("date",S_date);
        intent.putExtra("total_amount",S_amount);
        intent.putExtra("id",Id);
        startActivity(intent);

    }

    public void all_data(){
        if(date_txt.getText().equals("Choose date")) {
            spinKitView.setVisibility(View.VISIBLE);


            String url = "http://sbm.spksystems.in/public/api/delivery?date=All";


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @SuppressLint("CheckResult")
                @Override
                public void onResponse(JSONObject response) {
                    spinKitView.setVisibility(View.GONE);
                    Log.i("dkhgt9iuwrt9q2",response.toString());



                    try {


                        oldModelList = new ArrayList<>();
                        JSONArray jsonArray = response.getJSONArray("data");

                        for (int i = 0; i <jsonArray.length();i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);


                            id = jsonObject.getString("id");
                            date = jsonObject.getString("date");
                            order_no = jsonObject.getString("order_no");
                            vehicle_no = jsonObject.getString("vehicle_no");
                            total_amount = jsonObject.getString("total_amount");


                            JSONObject place = jsonObject.getJSONObject("place");
                            place_id = place.getString("id");
                            place_name = place.getString("name");

                            JSONObject party = jsonObject.getJSONObject("party_name");
                            party_id = party.getString("id");
                            party_name = party.getString("name");


                            JSONArray jsonArray1 = jsonObject.getJSONArray("materials");

                            for(int i1=0;i1<jsonArray1.length();i1++){

                                JSONObject jObj = jsonArray1.getJSONObject(i1);
                                JSONObject material = jObj.getJSONObject("material");

                                unit = jObj.getString("unit");
                                price = jObj.getString("price");

                                material_id = material.getString("id");
                                material_name = material.getString("name");


                                store_mat.add(material_name);
                                store_unit.add(unit);
                                store_price.add(price);

                                store_material_array= store_mat.get(i1);
                                store_unit_array= store_unit.get(i1);
                                store_price_array= store_price.get(i1);


                            }


                            if(store_mat.size()<=1){
                                material_arraylist = store_mat.get(0);

                            }
                            else {
                                material_arraylist = store_mat.toString().replace("[","").replace("]", "");
                                store_mat.clear();

                            }

                            if(store_unit.size()<=1){
                                unit_arraylist = store_unit.get(0);


                            }
                            else  {

                                unit_arraylist =  store_unit.toString().replace("[","").replace("]", "");
                                store_unit.clear();

                            }


                            if(store_price.size()<=1){
                                price_arraylist = store_price.get(0);

                            }
                            else  {

                                price_arraylist =  store_price.toString().replace("[","").replace("]", "");

                                store_price.clear();
                            }



                            AllEntryModel model = new AllEntryModel();
                            model.setS_no(order_no);
                            model.setP_name(party_name);
                            model.setPlace(place_name);
                            model.setUnit(material_arraylist);
                            model.setPrice(price_arraylist);
                            model.setM_name(unit_arraylist);
                            model.setV_no( vehicle_no);
                            model.setRate(total_amount);
                            model.setId(id);
                            model.setDate(date);

                            oldModelList.add(model);

                        }

                        old_entry_adapter = new AllEntryAdapter(AllEntryClass.this, oldModelList);
                        recyclerView.setAdapter(old_entry_adapter);
                        old_entry_adapter.setOnItemClickListener(AllEntryClass.this);



                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AllEntryClass.this) {
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
                    spinKitView.setVisibility(View.GONE);
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
                    params.put("Authorization", "Bearer " + PreferenceUtils.getToken(AllEntryClass.this));


                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(AllEntryClass.this);
            requestQueue.getCache().clear();
            requestQueue.add(jsonObjectRequest);

        }

        else {

            spinKitView.setVisibility(View.VISIBLE);



            String url = "http://sbm.spksystems.in/public/api/delivery?date="+date_txt.getText().toString();


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @SuppressLint({"CheckResult", "SimpleDateFormat"})
                @Override
                public void onResponse(JSONObject response) {


                    spinKitView.setVisibility(View.GONE);


                    try {


                        oldModelList = new ArrayList<>();
                        JSONArray jsonArray = response.getJSONArray("data");

                        for (int i = 0; i <jsonArray.length();i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);


                            id = jsonObject.getString("id");
                            date = jsonObject.getString("date");
                            order_no = jsonObject.getString("order_no");
                            vehicle_no = jsonObject.getString("vehicle_no");
                            total_amount = jsonObject.getString("total_amount");


                            JSONObject place = jsonObject.getJSONObject("place");
                            place_id = place.getString("id");
                            place_name = place.getString("name");

                            JSONObject party = jsonObject.getJSONObject("party_name");
                            party_id = party.getString("id");
                            party_name = party.getString("name");


                            JSONArray jsonArray1 = jsonObject.getJSONArray("materials");

                            for(int i1=0;i1<jsonArray1.length();i1++){

                                JSONObject jObj = jsonArray1.getJSONObject(i1);
                                JSONObject material = jObj.getJSONObject("material");

                                unit = jObj.getString("unit");
                                price = jObj.getString("price");

                                material_id = material.getString("id");
                                material_name = material.getString("name");


                                store_mat.add(material_name);
                                store_unit.add(unit);
                                store_price.add(price);

                                store_material_array= store_mat.get(i1);
                                store_unit_array= store_unit.get(i1);
                                store_price_array= store_price.get(i1);


                            }


                            if(store_mat.size()<=1){
                                material_arraylist = store_mat.get(0);

                            }
                            else {
                                material_arraylist = store_mat.toString().replace("[","").replace("]", "");
                                store_mat.clear();

                            }

                            if(store_unit.size()<=1){
                                unit_arraylist = store_unit.get(0);


                            }
                            else  {

                                unit_arraylist =  store_unit.toString().replace("[","").replace("]", "");
                                store_unit.clear();

                            }


                            if(store_price.size()<=1){
                                price_arraylist = store_price.get(0);

                            }
                            else  {

                                price_arraylist =  store_price.toString().replace("[","").replace("]", "");

                                store_price.clear();
                            }




                                    AllEntryModel model = new AllEntryModel();
                                    model.setS_no(order_no);
                                    model.setP_name(party_name);
                                    model.setPlace(place_name);
                                    model.setUnit(unit_arraylist);
                                    model.setPrice(price_arraylist);
                                    model.setM_name(material_arraylist);
                                    model.setV_no(vehicle_no);
                                    model.setRate(total_amount);
                                    model.setId(id);
                                    model.setDate(date);

                                    oldModelList.add(model);


                            }

                        old_entry_adapter = new AllEntryAdapter(AllEntryClass.this, oldModelList);
                        recyclerView.setAdapter(old_entry_adapter);
                        old_entry_adapter.setOnItemClickListener(AllEntryClass.this);



                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AllEntryClass.this) {
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
                    spinKitView.setVisibility(View.GONE);
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
                    params.put("Authorization", "Bearer " + PreferenceUtils.getToken(AllEntryClass.this));


                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(AllEntryClass.this);
            requestQueue.getCache().clear();
            requestQueue.add(jsonObjectRequest);

        }

    }
    public void onBackPressed() {

        AllEntryClass.super.onBackPressed();

    }



}