package com.example.crushermanagement.entries;

import static android.view.View.GONE;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.crushermanagement.Add_user;
import com.example.crushermanagement.Old_entry.Old_entry;
import com.example.crushermanagement.R;
import com.example.crushermanagement.Show_Entries.ShowList;
import com.example.crushermanagement.VolleyMultipartRequest;
import com.example.crushermanagement.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EntryActivity extends AppCompatActivity implements View.OnClickListener{
    EditText s_no,p_name,place,m_name,units,price,v_no;
    EditText rate;
    EditText date_txt;
    String S_no,P_name,Place,M_name,Units,Price,V_no,Rate,Date;

    Button submit,calculate;
    ImageView calender_img,img;
    Calendar calendar;
    DatePickerDialog dd;

    ImageView imageview;
    TextView unit_err,price_err;

    Button add;
    EditText editText,editText1,editText2;
    EditText editTextName,editTextName1,editTextName2;


    ArrayList<String> material = new ArrayList<>();
    ArrayList<Integer> unit = new ArrayList<>();
    ArrayList<Integer> pricee = new ArrayList<>();



    TextView date_error,error1,error2,error3,error4,error5,error6;


    private int count;
    private LinearLayout ll;
    private final int numClass = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);




        s_no = findViewById(R.id.s_no);
        p_name = findViewById(R.id.part_name);
        place = findViewById(R.id.place);
        v_no = findViewById(R.id.v_no);
        rate = findViewById(R.id.rate);
        date_txt = findViewById(R.id.date_txt);
        calender_img = findViewById(R.id.calender_img);
        ll = (LinearLayout) findViewById(R.id.lnr);
        add = findViewById(R.id.add1);
        calculate = findViewById(R.id.btn);
        date_error = findViewById(R.id.date_error);
        error1 = findViewById(R.id.error1);
        error2 = findViewById(R.id.error2);
        error3 = findViewById(R.id.error3);
        error4 = findViewById(R.id.error4);
        error5 = findViewById(R.id.error5);
        error6 = findViewById(R.id.error6);
        imageview = findViewById(R.id.menu);
        submit = findViewById(R.id.sumbit_btn);
        unit_err = findViewById(R.id.unit_error);
        price_err = findViewById(R.id.price_error);



     add.setOnClickListener(this);
      submit.setOnClickListener(this);




        date_txt.setInputType(InputType.TYPE_NULL);
        rate.setInputType(InputType.TYPE_NULL);





      calculate.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              if (checkIfValidAndRead()) {
                  unit_err.setVisibility(GONE);
                  price_err.setVisibility(GONE);

                  int mul = 0;

                  for (int i = 0; i <ll.getChildCount(); i++) {


                          calculate.setVisibility(View.VISIBLE);
                          int units = unit.get(i);
                          int price = pricee.get(i);


                          mul += units * price;
                          Log.i("ghvihkj", String.valueOf(units));






                  }


                  rate.setText(Integer.toString(mul));
                  rate.setVisibility(View.VISIBLE);
                  Log.i("jdhsfgisd", Integer.toString(mul));
                  Log.i("jdhsfgisd", String.valueOf(unit));




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
                        // TODO Auto-generated method stub
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                            date_txt.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);


                    }
                };

                new DatePickerDialog(EntryActivity.this, dateA, calendar.get(Calendar.YEAR),
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

            entry_two();

            date_error.setVisibility(GONE);
            error1.setVisibility(GONE);
            error2.setVisibility(GONE);
            error3.setVisibility(GONE);
            error4.setVisibility(GONE);
            error5.setVisibility(GONE);
            error6.setVisibility(GONE);


            checkIfValidAndRead();

            break;



    }



}

    public boolean checkIfValidAndRead() {

        material.clear();
        unit.clear();
        pricee.clear();

        boolean result = true;


        for(int i=0;i<ll.getChildCount();i++){

            View entry = ll.getChildAt(i);

       editTextName = (EditText)entry.findViewById(R.id.m_name);
       editTextName1= (EditText)entry.findViewById(R.id.units);
       editTextName2= (EditText)entry.findViewById(R.id.price);

            error5.setVisibility(GONE);


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

        editText = (EditText)entry.findViewById(R.id.m_name);
        editText1 = (EditText)entry.findViewById(R.id.units);
         editText2 = (EditText)entry.findViewById(R.id.price);


        ImageView imageClose = (ImageView)entry.findViewById(R.id.image1);



        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(entry);
                if(ll.getChildCount()>0){
                    calculate.setVisibility(View.VISIBLE);
                    rate.setVisibility(View.VISIBLE);

                }else{
                    calculate.setVisibility(GONE);
                    rate.setText("0");

                }


            }
        });

        ll.addView(entry);

    }

    public void removeView(View view){

        ll.removeView(view);

    }





    public void entry_two() {

        Date = date_txt.getText().toString();
        S_no = s_no.getText().toString();
        P_name = p_name.getText().toString();
        Place = place.getText().toString();
        V_no = v_no.getText().toString();
        Rate = rate.getText().toString();


            if (checkIfValidAndRead()) {




                String URL = "http://sbm.spksystems.in/public/api/delivery/store";


                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("000000000",response);


                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            String id = jsonObject1.getString("id");

                            String Success = jsonObject.getString("success");
                            String msg = jsonObject.getString("message");


                            if(Success.equals("true")){
                                Toast.makeText(EntryActivity.this, msg, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(EntryActivity.this, ShowList.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                intent.putExtra("id",id);
                                startActivity(intent);

                            }else{
                                Toast.makeText(EntryActivity.this, msg, Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }






                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Charset charset = Charset.defaultCharset();
                        String str = new String(error.networkResponse.data, charset);


                        try {


                                JSONObject jsonObject = new JSONObject(str);
                                JSONObject data = jsonObject.getJSONObject("errors");

                                JSONArray jsonArray = data.getJSONArray("date");
                                date_error.setText(jsonArray.getString(0));
                                date_error.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {


                                JSONObject jsonObject1 = new JSONObject(str);
                                JSONObject data1 = jsonObject1.getJSONObject("errors");

                                JSONArray jsonArray1 = data1.getJSONArray("order_no");
                                error1.setText(jsonArray1.getString(0));
                                error1.setVisibility(View.VISIBLE);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {



                                JSONObject jsonObject2 = new JSONObject(str);
                                JSONObject data2 = jsonObject2.getJSONObject("errors");

                                JSONArray jsonArray2 = data2.getJSONArray("party_name");
                                error2.setText(jsonArray2.getString(0));
                                error2.setVisibility(View.VISIBLE);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {




                                JSONObject jsonObject3 = new JSONObject(str);
                                JSONObject data3 = jsonObject3.getJSONObject("errors");

                                JSONArray jsonArray3 = data3.getJSONArray("place");
                                error3.setText(jsonArray3.getString(0));
                                error3.setVisibility(View.VISIBLE);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                        try {


                                JSONObject jsonObject5 = new JSONObject(str);
                                JSONObject data5 = jsonObject5.getJSONObject("errors");

                                JSONArray jsonArray5 = data5.getJSONArray("vehicle_no");
                                error4.setText(jsonArray5.getString(0));
                                error4.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        try {
                            JSONObject jsonObject4 = new JSONObject(str);
                            JSONObject data4 = jsonObject4.getJSONObject("errors");

                            JSONArray jsonArray4 = data4.getJSONArray("materials");
                            error5.setText(jsonArray4.getString(0));
                            error5.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {


                                JSONObject jsonObject6 = new JSONObject(str);
                                JSONObject data6 = jsonObject6.getJSONObject("errors");

                                JSONArray jsonArray6 = data6.getJSONArray("total_amount");
                                error6.setText(jsonArray6.getString(0));
                                error6.setVisibility(View.VISIBLE);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }






                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("date", Date);
                        params.put("order_no", S_no);
                        params.put("party_name", P_name);
                        params.put("place", Place);
                        params.put("vehicle_no", V_no);
                        params.put("total_amount", Rate);

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
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Accept", "application/json");
                        params.put("Authorization", "Bearer " + PreferenceUtils.getToken(EntryActivity.this));


                        return params;
                    }

                };

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                RequestQueue requestQueue = Volley.newRequestQueue(EntryActivity.this);
                requestQueue.getCache().clear();
                requestQueue.add(stringRequest);

            }



    }
}

