package com.example.crushermanagement;

import static android.view.View.GONE;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.crushermanagement.Dashboard.DashboardClass;
import com.example.crushermanagement.utils.PreferenceUtils;
import com.github.ybq.android.spinkit.SpinKitView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Date;
import java.util.Map;

public class Edit extends AppCompatActivity implements View.OnClickListener{
    EditText s_no,v_no;
    AutoCompleteTextView p_name,place;
    EditText rate;
    EditText date_txt;
    String S_no,P_name,Place,V_no,Rate,Date;

    Button submit,calculate;
    ImageView calender_img;
    Calendar calendar;
    ImageView imageview;
    TextView unit_err,price_err;
    private long pressedTime;
    Button add;
    String order_no, party_name,units,vehicle_no,total_amount,id,date;

    String store_material_array;
    String store_unit_array;
    String store_price_array;

    String dd,dd1,dd2;

    AutoCompleteTextView editText,editText1,editText2;
    AutoCompleteTextView editTextName,editTextName1,editTextName2;


    ArrayList<String> material = new ArrayList<>();
    ArrayList<Integer> unit = new ArrayList<>();
    ArrayList<Integer> pricee = new ArrayList<>();
    ArrayList<String> s_mat = new ArrayList<String>();
    ArrayList<String> s_uni = new ArrayList<String>();
    ArrayList<String> s_pri = new ArrayList<String>();
    ArrayList<String> place_n = new ArrayList<String>();
    ArrayList<String> materials = new ArrayList<String>();
    ArrayList<String> store_mat =  new ArrayList<>();
    ArrayList<String> store_unit = new ArrayList<>();
    ArrayList<String> store_price =  new ArrayList<>();

    TextView error1,error2,error3,error5,error6;
    private LinearLayout linearLayout;
    String material_name;
    SpinKitView spinKitView;
    String s_order,s_party,s_place,s_vehicle,s_unit,s_price,s_rate,s_date,s_material,s_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newentry);



        s_no = findViewById(R.id.s_no);
        p_name = findViewById(R.id.party_name);
        place = findViewById(R.id.place);
        v_no = findViewById(R.id.v_no);
        rate = findViewById(R.id.rate);
        date_txt = findViewById(R.id.date_txt);
        calender_img = findViewById(R.id.calender_img);
        linearLayout = (LinearLayout) findViewById(R.id.lnr);
        add = findViewById(R.id.add1);
        calculate = findViewById(R.id.btn);
        error1 = findViewById(R.id.error1);
        error2 = findViewById(R.id.error2);
        error3 = findViewById(R.id.error3);
        error5 = findViewById(R.id.error5);
        error6 = findViewById(R.id.error6);
        imageview = findViewById(R.id.menu);
        submit = findViewById(R.id.sumbit_btn);
        unit_err = findViewById(R.id.unit_error);
        price_err = findViewById(R.id.price_error);

        spinKitView = findViewById(R.id.progressBar);

        Intent intent = getIntent();
        s_order = intent.getStringExtra("order_no");
        s_party = intent.getStringExtra("party_name");
        s_place = intent.getStringExtra("place_name");
        s_vehicle = intent.getStringExtra("vehicle_no");
        s_material = intent.getStringExtra("material_name");
        s_unit = intent.getStringExtra("unit");
        s_price = intent.getStringExtra("price");
        s_rate = intent.getStringExtra("total_amount");
        s_date = intent.getStringExtra("date");
        s_id = intent.getStringExtra("id");




        s_mat.add(s_material);
        s_uni.add(s_unit);
        s_pri.add(s_price);

        s_no.setText(s_order);
        p_name.setText(s_party);
        p_name.setEnabled(false);
        p_name.setClickable(false);

        place.setEnabled(false);
        place.setClickable(false);
        place.setText(s_place);

        if(s_vehicle.equals("null")){
            v_no.setText(" ");
        }else{
            v_no.setText(s_vehicle);
        }

        rate.setText(s_rate);
        date_txt.setText(s_date);

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        date_txt.setText(date);

     add.setOnClickListener(this);

     submit.setOnClickListener(this);

     calender_img.setVisibility(GONE);

        calculate.setVisibility(View.VISIBLE);

        spinKitView.setVisibility(View.VISIBLE);
        date_txt.setInputType(InputType.TYPE_NULL);
        rate.setInputType(InputType.TYPE_NULL);


        calculate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {

                if (checkIfValidAndRead()) {
                    unit_err.setVisibility(GONE);
                    price_err.setVisibility(GONE);

                    int mul = 0;
                    int units ;
                    int price;
                    for (int i = 0; i <linearLayout.getChildCount(); i++) {


                        calculate.setVisibility(View.VISIBLE);
                         units = unit.get(i);
                       price = pricee.get(i);


                        mul += units * price;





                    }

                    rate.setText(String.valueOf(mul));
                    rate.setVisibility(View.VISIBLE);


                }

            }
        });


        String url = "http://sbm.spksystems.in/public/api/view-delivery/"+s_id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint({"CheckResult", "ResourceAsColor", "NewApi"})
            @Override
            public void onResponse(JSONObject response) {
                Log.i("kwqjdorueqyr",response.toString());

                spinKitView.setVisibility(GONE);
                try {




                    JSONObject jsonObject = response.getJSONObject("data");



                    id = jsonObject.getString("id");
                    order_no = jsonObject.getString("order_no");
                    vehicle_no = jsonObject.getString("vehicle_no");
                    total_amount = jsonObject.getString("total_amount");




                    JSONArray materials_jsonarray = jsonObject.getJSONArray("materials");


                    for(int i=0;i<materials_jsonarray.length();i++){

                        JSONObject materials_jsonobject = materials_jsonarray.getJSONObject(i);
                        units = materials_jsonobject.getString("unit");
                       String pricee = materials_jsonobject.getString("price");

                     JSONObject  material = materials_jsonobject.getJSONObject("material");

                        material_name = material.getString("name");

                        store_mat.add(material_name);
                        store_unit.add(units);
                        store_price.add(pricee);
                        Log.i("qjhfiqruwegfu8wdig",store_mat.toString());


                        store_material_array= store_mat.get(i);
                        store_unit_array= store_unit.get(i);
                        store_price_array= store_price.get(i);


                    }
                    for (int i1 = 0; i1 < store_mat.size(); i1++) {



                        View entry = getLayoutInflater().inflate(R.layout.entry_recycle,null,false);

                        editText =entry.findViewById(R.id.m_name);
                        editText1 = entry.findViewById(R.id.units);
                        editText2 =entry.findViewById(R.id.price);

                        editText.setSingleLine(true);
                        editText1.setSingleLine(true);
                        editText2.setSingleLine(true);

                        editText.setText(store_mat.get(i1));
                        editText1.setText(store_unit.get(i1));
                        editText2.setText(store_price.get(i1));


                        materials.add(material_name);

                        editText.setAdapter(new ArrayAdapter<String>(Edit.this,android.R.layout.simple_list_item_1,materials));
                        editText.setThreshold(1);

                        ImageView imageClose = (ImageView)entry.findViewById(R.id.image1);



                        imageClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                removeView(entry);
                                if(linearLayout.getChildCount()>0){
                                    calculate.setVisibility(View.VISIBLE);
                                    rate.setVisibility(View.VISIBLE);

                                }else{
                                    calculate.setVisibility(GONE);
                                    rate.setText("0");

                                }


                            }
                        });



                        linearLayout.addView(entry);





                    }




                } catch (Exception e) {
                    e.printStackTrace();


                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                spinKitView.setVisibility(GONE);
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
                params.put("Authorization","Bearer "+ PreferenceUtils.getToken(Edit.this));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Edit.this);
        requestQueue.getCache().clear();
        requestQueue.add(jsonObjectRequest);



    }




@Override
public void onClick(View v) {

    switch (v.getId()){

        case R.id.add1:

            addView();

            calculate.setVisibility(View.VISIBLE);


            break;

        case R.id.sumbit_btn:



            spinKitView.setVisibility(View.VISIBLE);

                entry();

                error1.setVisibility(GONE);
                error2.setVisibility(GONE);
                error3.setVisibility(GONE);
                error5.setVisibility(GONE);
                error6.setVisibility(GONE);
                unit_err.setVisibility(GONE);
                price_err.setVisibility(GONE);

                checkIfValidAndRead();





            break;



    }



}

    public boolean checkIfValidAndRead() {


        material.clear();
        unit.clear();
        pricee.clear();
        materials.clear();


        boolean result = true;


        for(int i=0;i<linearLayout.getChildCount();i++){

            View entry = linearLayout.getChildAt(i);

       editTextName = entry.findViewById(R.id.m_name);
       editTextName1= entry.findViewById(R.id.units);
       editTextName2= entry.findViewById(R.id.price);

            error5.setVisibility(GONE);



            if(!editTextName.getText().toString().equals("")){
                material.add(editTextName.getText().toString());


                Log.i("dhqgyuiq",material.toString());

            }else {
                result = false;

                error5.setVisibility(View.VISIBLE);
                error5.setText("The materials field is required.");


                break;
            }
            if(!editTextName1.getText().toString().equals("")){

                String ddd = editTextName1.getText().toString();
                unit.add(Integer.valueOf(ddd));

            }else {
                result = false;
                error5.setVisibility(View.VISIBLE);
                error5.setText("The materials field is required.");
                break;
            }
            if(!editTextName2.getText().toString().equals("")){
                String ddd1 = editTextName2.getText().toString();
                pricee.add(Integer.valueOf(ddd1));
            }else {
                result = false;
                error5.setVisibility(View.VISIBLE);
                error5.setText("The materials field is required.");
                break;
            }



        }




        return result;


    }



    public void addView() {


      View entry = getLayoutInflater().inflate(R.layout.entry_recycle,null,false);

        editText =entry.findViewById(R.id.m_name);
        editText1 = entry.findViewById(R.id.units);
         editText2 =entry.findViewById(R.id.price);

         editText.setSingleLine(true);
        editText1.setSingleLine(true);
        editText2.setSingleLine(true);


        materials.add(material_name);

        editText.setAdapter(new ArrayAdapter<String>(Edit.this,android.R.layout.simple_list_item_1,materials));
        editText.setThreshold(1);

        ImageView imageClose = (ImageView)entry.findViewById(R.id.image1);



        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(entry);
                if(linearLayout.getChildCount()>0){
                    calculate.setVisibility(View.VISIBLE);
                    rate.setVisibility(View.VISIBLE);

                }else{
                    calculate.setVisibility(GONE);
                    rate.setText("0");

                }


            }
        });



        linearLayout.addView(entry);

    }

    public void removeView(View view){

        linearLayout.removeView(view);

    }


    public void entry() {



            if (checkIfValidAndRead()) {


                String URL =  "http://sbm.spksystems.in/public/api/delivery/update/"+s_id;
                unit_err.setVisibility(GONE);
                price_err.setVisibility(GONE);
              int  mul = 0;
               int units ;
                int price;
                for (int i = 0; i <linearLayout.getChildCount(); i++) {


                    calculate.setVisibility(View.VISIBLE);
                    units = unit.get(i);
                    price = pricee.get(i);


                    mul += units * price;


                }


                rate.setText(String.valueOf(mul));
                rate.setVisibility(View.VISIBLE);


                Date = date_txt.getText().toString();
                S_no = s_no.getText().toString();
                P_name = p_name.getText().toString();
                Place = place.getText().toString();
                V_no = v_no.getText().toString();
                Rate = rate.getText().toString();


                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("000000000",response);

                        spinKitView.setVisibility(GONE);

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String Success = jsonObject.getString("success");
                            String msg = jsonObject.getString("message");


                            if (Success.equals("true")) {

                                Intent intent = new Intent(Edit.this, DashboardClass.class);
                                intent.putExtra("id",s_id);
                                startActivity(intent);



                            }else{

                            }
                        } catch (Exception e) {
                            e.printStackTrace();

                        }



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        spinKitView.setVisibility(GONE);
                        Charset charset = Charset.defaultCharset();
                        String str = new String(error.networkResponse.data,charset);
                        try {
                            JSONObject jsonObject = new JSONObject(str);
                            String err = jsonObject.getString("data");
//                            Toast.makeText(Edit.this, "Total amount ", Toast.LENGTH_SHORT).show();
                            error6.setVisibility(View.VISIBLE);
                            error6.setText("Total amount should not be greater than 8 digits");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }





                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("vehicle_no",V_no);
                        params.put("total_amount",Rate);

                        for (int i = 0; i < linearLayout.getChildCount(); i++) {


                                params.put("materials[" + i + "][material]", material.get(i));
                                params.put("materials[" + i + "][unit]", String.valueOf(unit.get(i)));
                                params.put("materials[" + i + "][price]", String.valueOf(pricee.get(i)));

                        }

                        Log.i("arrayyyyy",params.toString());
                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("Accept","application/json");
                        headers.put("Authorization", "Bearer " + PreferenceUtils.getToken( Edit.this));
                        return headers;
                    }
                };

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                RequestQueue requestQueue = Volley.newRequestQueue(Edit.this);
                requestQueue.add(stringRequest);



    }



    }
    public void onBackPressed() {


//        if (pressedTime + 2000 > System.currentTimeMillis()) {

            Dialog dialog;

            dialog=new Dialog(Edit.this);

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

            title.setText("Are you sure want to continue without changes");


            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                }
            });

            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Edit.super.onBackPressed();

                }
            });






    }



}

