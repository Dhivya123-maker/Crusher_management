package com.example.crushermanagement.ViewEntry;

import static android.text.TextUtils.split;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.crushermanagement.Dashboard.DashboardClass;
import com.example.crushermanagement.Edit;
import com.example.crushermanagement.AllEntries.AllEntryClass;
import com.example.crushermanagement.R;
import com.example.crushermanagement.utils.PreferenceUtils;
import com.github.ybq.android.spinkit.SpinKitView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewEntryClass extends AppCompatActivity {

    RecyclerView recyclerView;
    List<ViewEntryModel> show_models;
    ViewEntryAdapter show_adapter;
    private Date oneWayTripDate;
    Button old_entry;
    AlertDialog alertDialog;
    EditText editText;
    String order_no, party_name,vehicle_no,total_amount,id,date;
    String place_id;
    String place_name;
    String party_id;
    String material_id;
    String material_name;
    String unit;
    String price;
    SpinKitView spinKitView;

    ArrayList<String> store_mat =  new ArrayList<>();
    ArrayList<String> store_unit = new ArrayList<>();
    ArrayList<String> store_price =  new ArrayList<>();

   ImageView edit;

    JSONObject material;
    String m_material_arraylist = "" ,u_unit_arraylist = "" ,p_price_arraylist = "";
    String material_arraylist = "" ;
    String unit_arraylist ;
    String price_arraylist;
    String store_material_array;
    String store_unit_array;
    String store_price_array;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewentry);


        Intent intent = getIntent();
        id = intent.getStringExtra("id");



        spinKitView = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recycle_show);
        old_entry = findViewById(R.id.button);
        edit = findViewById(R.id.edit_menu);




        spinKitView.setVisibility(View.VISIBLE);
        show_list();

        old_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ViewEntryClass.this, AllEntryClass.class);
                intent.putExtra("order_no",order_no);
                intent.putExtra("party_name",party_name);
                intent.putExtra("place_name",place_name);
                intent.putExtra("vehicle_no",vehicle_no);
                intent.putExtra("material_name",store_material_array);
                intent.putExtra("unit",store_unit_array);
                intent.putExtra("price",store_price_array);
                intent.putExtra("date",date);
                intent.putExtra("total_amount",total_amount);
                intent.putExtra("id",id);

                startActivity(intent);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewEntryClass.this, Edit.class);
                intent.putExtra("order_no",order_no);
                intent.putExtra("party_name",party_name);
                intent.putExtra("place_name",place_name);
                intent.putExtra("vehicle_no",vehicle_no);
                intent.putExtra("material_name",store_material_array);
                intent.putExtra("unit",store_unit_array);
                intent.putExtra("price",store_price_array);
                intent.putExtra("date",date);
                intent.putExtra("total_amount",total_amount);
                intent.putExtra("id",id);



                startActivity(intent);
            }
        });




    }


    public  void show_list(){
        spinKitView.setVisibility(View.VISIBLE);

        String url = "http://sbm.spksystems.in/public/api/view-delivery/"+id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint({"CheckResult", "ResourceAsColor", "NewApi"})
            @Override
            public void onResponse(JSONObject response) {
                Log.i("kwqjdorueqyr",response.toString());
                spinKitView.setVisibility(View.GONE);
                try {

                    show_models = new ArrayList<>();


                 JSONObject jsonObject = response.getJSONObject("data");



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


                    for(int i=0;i<jsonArray1.length();i++){

                            JSONObject jObj = jsonArray1.getJSONObject(i);
                            unit = jObj.getString("unit");
                            price = jObj.getString("price");

                            material = jObj.getJSONObject("material");

                            material_id = material.getString("id");
                            material_name = material.getString("name");

                        store_mat.add(material_name);
                        store_unit.add(unit);
                        store_price.add(price);


                      store_material_array= store_mat.get(i);
                        store_unit_array= store_unit.get(i);
                        store_price_array= store_price.get(i);



                        }




                        if(store_mat.size()<=1){
                            material_arraylist = store_mat.get(0);


                        }
                        else  {

                            material_arraylist=  store_mat.toString().replace("[", "").replace("]", "");


                        }

                    if(store_unit.size()<=1){
                        unit_arraylist = store_unit.get(0);

                    }
                    else  {

                        unit_arraylist =  store_unit.toString().replace("[", "").replace("]", "");

                    }
                    if(store_price.size()<=1){
                        price_arraylist = store_price.get(0);

                    }
                    else  {

                        price_arraylist =  store_price.toString().replace("[", "").replace("]", "");


                    }


                    m_material_arraylist = m_material_arraylist+material_arraylist;
                    u_unit_arraylist= u_unit_arraylist+unit_arraylist;
                    p_price_arraylist = p_price_arraylist+price_arraylist;



                    ViewEntryModel model = new ViewEntryModel();
                    model.setS_no(order_no);
                    model.setP_name(party_name);
                    model.setPlace(place_name);
                    model.setV_no( vehicle_no);
                    model.setUnits(u_unit_arraylist);
                    model.setPrice(p_price_arraylist);
                    model.setM_name(m_material_arraylist);
                    model.setId(id);
                    model.setDate(date);
                    model.setRate(total_amount);




                    show_models.add(model);



                    show_adapter = new ViewEntryAdapter(ViewEntryClass.this, show_models);
                    recyclerView.setAdapter(show_adapter);


                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewEntryClass.this) {
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
                params.put("Authorization","Bearer "+ PreferenceUtils.getToken(ViewEntryClass.this));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ViewEntryClass.this);
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);


    }


    public void onBackPressed() {

     Intent intent = new Intent(ViewEntryClass.this, DashboardClass.class);
     startActivity(intent);
    }




}