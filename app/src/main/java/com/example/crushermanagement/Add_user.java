package com.example.crushermanagement;

import static android.view.View.GONE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.crushermanagement.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class Add_user extends AppCompatActivity {
    Button add_user,view_user;
    EditText name,email,password;
    String Email,Password,Name;
    JSONObject data;
    String id,nam,emai;
    TextView error1,error2,error3;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        add_user = findViewById(R.id.add_btn);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        error1 = findViewById(R.id.error1);
        error2 = findViewById(R.id.error2);
        error3 = findViewById(R.id.error3);
        view_user = findViewById(R.id.view_btn);
        view_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Add_user.this,User_List.class);
                startActivity(intent);
            }
        });

        add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                error1.setVisibility(GONE);
                error2.setVisibility(GONE);
                error3.setVisibility(GONE);
                add_user();
            }
        });

    }

    public void add_user(){


        Name = name.getText().toString();
        Email = email.getText().toString();
        Password = password.getText().toString();

        String URL = "http://sbm.spksystems.in/public/api/add-user";

        JSONObject jsonBody = new JSONObject();


        try {
            jsonBody.put("name",Name);
            jsonBody.put("email",Email);
            jsonBody.put("password",Password);



            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {


                    Log.i("000000000",response.toString());

                    try {
                        String Success = response.getString("success");
                        String msg = response.getString("message");

                        data = response.getJSONObject("data");
                        id = data.getString("id");
                        nam = data.getString("name");
                        emai = data.getString("email");


                        if (Success.equals("true")) {

                            Toast.makeText(Add_user.this, msg, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Add_user.this, User_List.class);
                            startActivity(intent);



                        }else{
                            Toast.makeText(Add_user.this, msg, Toast.LENGTH_SHORT).show();
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



                    try {

                        if(Name.isEmpty()){


                        JSONObject jsonObject = new JSONObject(str);
                        JSONObject data = jsonObject.getJSONObject("data");

                        JSONArray jsonArray = data.getJSONArray("name");
                        error1.setText(jsonArray.getString(0));
                        error1.setVisibility(View.VISIBLE);
                        }else{
                            error1.setVisibility(GONE);
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {

                        if(Email.isEmpty()){

                        JSONObject jsonObject = new JSONObject(str);
                        JSONObject data = jsonObject.getJSONObject("data");

                        JSONArray jsonArray = data.getJSONArray("email");
                        error2.setText(jsonArray.getString(0));
                        error2.setVisibility(View.VISIBLE);


                        }else if(!Email.matches(emailPattern)){
                            JSONObject jsonObject = new JSONObject(str);
                            JSONObject data = jsonObject.getJSONObject("data");

                            JSONArray jsonArray = data.getJSONArray("email");
                            error2.setText(jsonArray.getString(0));
                            error2.setVisibility(View.VISIBLE);

                        }else{
                            JSONObject jsonObject = new JSONObject(str);
                            JSONObject data = jsonObject.getJSONObject("data");

                            JSONArray jsonArray = data.getJSONArray("email");
                            error2.setText(jsonArray.getString(0));
                            error2.setVisibility(View.VISIBLE);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                    try {
                        if(Password.isEmpty()) {


                            JSONObject jsonObject = new JSONObject(str);
                            JSONObject data = jsonObject.getJSONObject("data");

                            JSONArray jsonArray = data.getJSONArray("password");
                            error3.setText(jsonArray.getString(0));
                            error3.setVisibility(View.VISIBLE);

                        }
                        else{
                            error3.setVisibility(GONE);

                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }





                }
            })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("Accept","application/json");
                    params.put("Authorization", "Bearer " + PreferenceUtils.getToken(Add_user.this));
                    return params;
                }

            };

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            RequestQueue requestQueue = Volley.newRequestQueue(Add_user.this);
            requestQueue.add(jsonObjectRequest);
            requestQueue.getCache().remove(jsonObjectRequest.getCacheKey());




        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
}