package com.example.crushermanagement.NewEntry;

import static android.view.View.GONE;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.crushermanagement.R;
import com.example.crushermanagement.ViewEntry.ViewEntryClass;
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

public class NewEntryClass extends AppCompatActivity implements View.OnClickListener{
    EditText s_no,v_no;
    AutoCompleteTextView p_name,place;
    EditText total_amount;
    EditText date_txt;
    String S_no,P_name,Place,V_no,Total_amount,Date;

    Button submit,calculate;
    ImageView calender_img;
    Calendar calendar;
    ImageView imageview;
    TextView unit_err,price_err;
    SpinKitView spinKitView;

    Button add;

    JSONArray jsonArray;

    AutoCompleteTextView editText,editText1,editText2;
    AutoCompleteTextView editTextName,editTextName1,editTextName2;


    ArrayList<String> material = new ArrayList<>();
    ArrayList<Long> unit = new ArrayList<>();
    ArrayList<Long> pricee = new ArrayList<>();
    ArrayList<String> party = new ArrayList<String>();
    ArrayList<String> place_n = new ArrayList<String>();
    ArrayList<String> materials = new ArrayList<String>();

    TextView error1,error2,error3,error4,error5,error6;
    private LinearLayout ll;
    String order_no,place_id,place_name,party_id,party_name,material_id,material_name;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newentry);



        spinKitView = findViewById(R.id.progressBar);

        s_no = findViewById(R.id.s_no);
        p_name = findViewById(R.id.party_name);
        place = findViewById(R.id.place);
        v_no = findViewById(R.id.v_no);
        total_amount = findViewById(R.id.rate);
        date_txt = findViewById(R.id.date_txt);
        calender_img = findViewById(R.id.calender_img);
        ll = (LinearLayout) findViewById(R.id.lnr);
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


        spinKitView.setVisibility(View.VISIBLE);
        p_name.setEnabled(false);
        place.setEnabled(false);
        v_no.setEnabled(false);
        add.setEnabled(false);
        submit.setEnabled(false);
        calender_img.setEnabled(false);


        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        date_txt.setText(date);

     add.setOnClickListener(this);
     submit.setOnClickListener(this);



        String url = "http://sbm.spksystems.in/public/api/delivery/create";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @SuppressLint("CheckResult")
            @Override
            public void onResponse(JSONObject response) {
                spinKitView.setVisibility(GONE);
                p_name.setEnabled(true);
                place.setEnabled(true);
                v_no.setEnabled(true);
                add.setEnabled(true);
                submit.setEnabled(true);
                calender_img.setEnabled(true);
                Log.i("jwgbfiwuegtiq",response.toString());

                try {
                    JSONObject jsonObject = response.getJSONObject("data");
                    String Success = response.getString("success");


                    order_no = jsonObject.getString("orderNo");
                    jsonArray = jsonObject.getJSONArray("places");

                    s_no.setText(order_no);


                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        place_id = jObj.getString("id");
                        place_name = jObj.getString("name");
                        place_n.add(place_name);

                        place.setAdapter(new ArrayAdapter<String>(NewEntryClass.this,R.layout.support_simple_spinner_dropdown_item,place_n));
                        place.setThreshold(1);


                    }

                    JSONArray jsonArray1 = jsonObject.getJSONArray("materials");

                    for(int i=0;i<jsonArray1.length();i++){


                            JSONObject jObj = jsonArray1.getJSONObject(i);
                        material_id = jObj.getString("id");
                        material_name = jObj.getString("name");
                        materials.add(material_name);



                    }


                    JSONArray jsonArray2 = jsonObject.getJSONArray("partyNames");

                    for(int i=0;i<jsonArray2.length();i++){
                        JSONObject jObj = jsonArray2.getJSONObject(i);
                        party_id = jObj.getString("id");
                        party_name = jObj.getString("name");
                        party.add(party_name);

                     p_name.setAdapter(new ArrayAdapter<String>(NewEntryClass.this,R.layout.support_simple_spinner_dropdown_item,party));
                        p_name.setThreshold(1);

                    }



                    if(Success.equals("true")){


                    }else{

                    }
                } catch (JSONException e) {
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
                params.put("Authorization", "Bearer " + PreferenceUtils.getToken(NewEntryClass.this));


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(NewEntryClass.this);
        requestQueue.add(jsonObjectRequest);






        date_txt.setInputType(InputType.TYPE_NULL);
        total_amount.setInputType(InputType.TYPE_NULL);




      calculate.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {



              if (checkIfValidAndRead()) {


                  Long mul = Long.valueOf(0);
                  Long units ;
                  Long price;

                  for (int i = 0; i <ll.getChildCount(); i++) {


                      calculate.setVisibility(View.VISIBLE);

                     units = unit.get(i);
                    price = pricee.get(i);


                          mul += units * price;



                  }


                  total_amount.setText(String.valueOf(mul));
                  total_amount.setVisibility(View.VISIBLE);


              }

          }
      });

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


                    }
                };

                new DatePickerDialog(NewEntryClass.this, dateA, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });




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


        for(int i=0;i<ll.getChildCount();i++){

            View entry = ll.getChildAt(i);

       editTextName = entry.findViewById(R.id.m_name);
       editTextName1= entry.findViewById(R.id.units);
       editTextName2= entry.findViewById(R.id.price);

            error5.setVisibility(GONE);
            unit_err.setVisibility(GONE);
            price_err.setVisibility(GONE);

            if(!editTextName.getText().toString().equals("")){
                material.add(editTextName.getText().toString());


            }else {
                result = false;

                error5.setVisibility(View.VISIBLE);
                error5.setText("The materials field is required.");


                break;
            }
            if(!editTextName1.getText().toString().equals("")){

                String ddd = editTextName1.getText().toString();
               unit.add(Long.valueOf(ddd));



            }else {
                result = false;
                error5.setVisibility(View.VISIBLE);
                error5.setText("The materials field is required.");
                break;
            }
            if(!editTextName2.getText().toString().equals("")){
                String ddd1 = editTextName2.getText().toString();
                pricee.add(Long.valueOf(ddd1));


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


        materials.add(material_name);

        editText.setAdapter(new ArrayAdapter<String>(NewEntryClass.this,android.R.layout.simple_list_item_1,materials));
        editText.setThreshold(1);

        ImageView imageClose = (ImageView)entry.findViewById(R.id.image1);



        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(entry);
                if(ll.getChildCount()>0){
                    calculate.setVisibility(View.VISIBLE);
                    total_amount.setVisibility(View.VISIBLE);

                }else{
                    calculate.setVisibility(GONE);
                    total_amount.setText("0");

                }


            }
        });

        ll.addView(entry);

    }

    public void removeView(View view){

        ll.removeView(view);

    }





    public void entry() {


            if (checkIfValidAndRead()) {

                String URL = "http://sbm.spksystems.in/public/api/delivery/store";


                Long mul = Long.valueOf(0);
                Long units ;
                Long price;
                for (int i = 0; i <ll.getChildCount(); i++) {


                    calculate.setVisibility(View.VISIBLE);
                    units = unit.get(i);
                    price = pricee.get(i);


                    mul += units * price;


                }


                total_amount.setText(String.valueOf(mul));
                total_amount.setVisibility(View.VISIBLE);



                Date = date_txt.getText().toString();
                S_no = s_no.getText().toString();
                P_name = p_name.getText().toString();
                Place = place.getText().toString();
                V_no = v_no.getText().toString();
                Total_amount = total_amount.getText().toString();



                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("000000000",response);

                        spinKitView.setVisibility(GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            String id = jsonObject1.getString("id");
                            String Success = jsonObject.getString("success");
                            String msg = jsonObject.getString("message");


                            if(Success.equals("true")){
                                Toast.makeText(NewEntryClass.this, msg, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(NewEntryClass.this, ViewEntryClass.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                intent.putExtra("id",id);

                                

                                startActivity(intent);

                            }else{
                                Toast.makeText(NewEntryClass.this, msg, Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        spinKitView.setVisibility(GONE);

                        try {

                            Charset charset = Charset.defaultCharset();
                            String str = new String(error.networkResponse.data, charset);

                            JSONObject jsonObject1 = new JSONObject(str);
                            JSONObject data1 = jsonObject1.getJSONObject("errors");
                            JSONArray jsonArray1 = data1.getJSONArray("order_no");

                                error1.setText(jsonArray1.getString(0));
                                error1.setVisibility(View.VISIBLE);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            Charset charset = Charset.defaultCharset();
                            String str = new String(error.networkResponse.data, charset);


                            JSONObject jsonObject2 = new JSONObject(str);
                                JSONObject data2 = jsonObject2.getJSONObject("errors");
                                JSONArray jsonArray2 = data2.getJSONArray("party_name");
                                error2.setText(jsonArray2.getString(0));
                                error2.setVisibility(View.VISIBLE);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {

                            Charset charset = Charset.defaultCharset();
                            String str = new String(error.networkResponse.data, charset);

                            JSONObject jsonObject3 = new JSONObject(str);
                            JSONObject data3 = jsonObject3.getJSONObject("errors");
                                JSONArray jsonArray3 = data3.getJSONArray("place");
                                error3.setText(jsonArray3.getString(0));
                                error3.setVisibility(View.VISIBLE);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                        try {
                            Charset charset = Charset.defaultCharset();
                            String str = new String(error.networkResponse.data, charset);



                            JSONObject jsonObject4 = new JSONObject(str);
                            JSONObject data4 = jsonObject4.getJSONObject("errors");

                            JSONArray jsonArray4 = data4.getJSONArray("materials");
                            error5.setText(jsonArray4.getString(0));
                            error5.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        try {
                            Charset charset = Charset.defaultCharset();
                            String str = new String(error.networkResponse.data, charset);


                            if(total_amount.getText().toString().length()>=8){
                                error6.setVisibility(View.VISIBLE);
                                error6.setText("Total amount should not be greater than 8 digits");
                            }else{
                                JSONObject jsonObject6 = new JSONObject(str);
                                JSONObject data6 = jsonObject6.getJSONObject("errors");

                                JSONArray jsonArray6 = data6.getJSONArray("total_amount");
                                error6.setText(jsonArray6.getString(0));
                                error6.setVisibility(View.VISIBLE);


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }






                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("date",Date);
                        params.put("order_no",S_no);
                        params.put("party_name",P_name);
                        params.put("place",Place);
                        params.put("vehicle_no",V_no);
                        params.put("total_amount",Total_amount);

                        for (int i = 0; i < ll.getChildCount(); i++) {


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
                        headers.put("Authorization", "Bearer " + PreferenceUtils.getToken( NewEntryClass.this));
                        return headers;
                    }
                };

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                RequestQueue requestQueue = Volley.newRequestQueue(NewEntryClass.this);
                requestQueue.add(stringRequest);



    }



    }
    public void onBackPressed() {


        NewEntryClass.super.onBackPressed();
    }



}

