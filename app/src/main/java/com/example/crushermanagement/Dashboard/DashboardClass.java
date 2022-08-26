package com.example.crushermanagement.Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.example.crushermanagement.Add_user;
import com.example.crushermanagement.AllEntries.AllEntryModel;
import com.example.crushermanagement.AllEntries.AllEntryClass;
import com.example.crushermanagement.Edit;
import com.example.crushermanagement.Login;
import com.example.crushermanagement.NewEntry.NewEntryClass;
import com.example.crushermanagement.R;
import com.example.crushermanagement.utils.PreferenceUtils;
import com.github.ybq.android.spinkit.SpinKitView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardClass extends AppCompatActivity implements DashboardAdapter.OnItemClickListener {

    LinearLayout menu,add_menu;
    Button entry,add_user;
    private long pressedTime;
    RecyclerView recyclerView;
    List<AllEntryModel> oldModelList;
    DashboardAdapter view_adapter;

    TextView view;
    String email;
    String id,date,order_no, party_name,vehicle_no,total_amount,m_unit,m_price;
    String place_id,place_name,party_id,material_id,material_name;
    SpinKitView spinKitView;

    String S_no,Id,S_party,S_place,S_vehicle,S_date,S_amount;
    ArrayList<String> store_mat =  new ArrayList<>();
    ArrayList<String> store_unit = new ArrayList<>();
    ArrayList<String> store_price =  new ArrayList<>();




    String m = "" ,u = "" ,p = "";
    String material_arraylist = "" ;
    String unit_arraylist ;
    String price_arraylist;
    String store_material_array;
    String store_unit_array;
    String store_price_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

                add_user = findViewById(R.id.add_user);
                menu = findViewById(R.id.menu_lnr);
                entry = findViewById(R.id.new_entry);
                recyclerView = findViewById(R.id.recycle_front);
                view = findViewById(R.id.view);
                spinKitView = findViewById(R.id.progressBar);

                spinKitView.setVisibility(View.VISIBLE);





                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(DashboardClass.this, AllEntryClass.class);
                        startActivity(intent);
                    }
                });
                entry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(DashboardClass.this, NewEntryClass.class);
                        startActivity(intent);

                    }
                });

        add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardClass.this, Add_user.class);
                startActivity(intent);

            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog dialog;

                dialog=new Dialog(DashboardClass.this);

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

                title.setText("Are you sure want to logout");


                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        logout();
                    }
                });



            }
        });
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(DashboardClass.this);
        email = preferences.getString("mail", null);


        if(email.equals("ns@spksystems.in")) {
            add_user.setVisibility(View.VISIBLE);


        }else{

            add_user.setVisibility(View.GONE);


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

                        Intent intent = new Intent(DashboardClass.this, Login.class);
                        startActivity(intent);
                        PreferenceUtils.saveid(null, DashboardClass.this);
                        PreferenceUtils.saveToken(null, DashboardClass.this);



                    }else{

                        Toast.makeText(DashboardClass.this, msg, Toast.LENGTH_SHORT).show();
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
                params.put("Authorization","Bearer "+ PreferenceUtils.getToken(DashboardClass.this));


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(DashboardClass.this);
        requestQueue.add(jsonObjectRequest);

    }
    public void onBackPressed() {


            Dialog dialog;

            dialog=new Dialog(DashboardClass.this);

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

            title.setText("Are you sure want to exit");


            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                }
            });

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                 finishAffinity();
                }
            });

    }



    public void show(){

        String url = "http://sbm.spksystems.in/public/api/delivery";
        spinKitView.setVisibility(View.VISIBLE);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @SuppressLint({"CheckResult", "NotifyDataSetChanged"})
            @Override
            public void onResponse(JSONObject response) {


                Log.i("dkjbfiwadw",response.toString());

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


                        JSONArray materials_jsonarray = jsonObject.getJSONArray("materials");

                        for(int i1=0;i1<materials_jsonarray.length();i1++){

                            JSONObject materials_jsonobject = materials_jsonarray.getJSONObject(i1);
                            JSONObject material = materials_jsonobject.getJSONObject("material");

                            m_unit = materials_jsonobject.getString("unit");
                            m_price = materials_jsonobject.getString("price");

                            material_id = material.getString("id");
                            material_name = material.getString("name");


                            store_mat.add(material_name);
                            store_unit.add(m_unit);
                            store_price.add(m_price);

                            store_material_array= store_mat.get(i1);
                            store_unit_array= store_unit.get(i1);
                            store_price_array= store_price.get(i1);


                        }


                        if(store_mat.size()<=1){
                            material_arraylist = store_mat.get(0);
                            store_mat.clear();



                        }
                        else {
                            material_arraylist = store_mat.toString().replace("[","").replace("]", "");
                          store_mat.clear();




                        }

                        if(store_unit.size()<=1){
                            unit_arraylist = store_unit.get(0);
                            store_mat.clear();

                        }
                        else  {

                            unit_arraylist =  store_unit.toString().replace("[","").replace("]", "");
                            store_unit.clear();

                        }


                        if(store_price.size()<=1){
                          price_arraylist  = store_price.get(0);
                            store_mat.clear();
                        }
                        else  {

                            price_arraylist =  store_price.toString().replace("[","").replace("]", "");

                            store_price.clear();
                        }



                        AllEntryModel model = new AllEntryModel();
                        model.setS_no(order_no);
                        model.setP_name(party_name);
                        model.setPlace(place_name);
                        model.setM_name(material_arraylist);
                        model.setUnit(unit_arraylist);
                        model.setPrice(price_arraylist);
                        model.setV_no(vehicle_no);
                        model.setRate(total_amount);
                        model.setId(id);
                        model.setDate(date);


                        oldModelList.add(model);


                    }


                    view_adapter = new DashboardAdapter(DashboardClass.this, oldModelList);
                    recyclerView.setAdapter(view_adapter);
                    view_adapter.setOnItemClickListener(DashboardClass.this);




                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DashboardClass.this) {
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
                params.put("Authorization", "Bearer " + PreferenceUtils.getToken(DashboardClass.this));


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(DashboardClass.this);
        // requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onItemClick(int position) {
        AllEntryModel old_model = oldModelList.get(position);
        Id = old_model.getId();
        S_no = old_model.getS_no();
        S_party = old_model.getP_name();
        S_place = old_model.getPlace();
        S_vehicle = old_model.getV_no();
        S_date = old_model.getDate();
        S_amount = old_model.getRate();


        Intent intent = new Intent(DashboardClass.this, Edit.class);
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



}