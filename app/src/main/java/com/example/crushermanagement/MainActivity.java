package com.example.crushermanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.example.crushermanagement.entries.EntryActivity;
import com.example.crushermanagement.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button login_btn;
    EditText email,password;
    String Email,Password;
    JSONObject data,user;
    String id;
    String token;
    TextView error1,error2;
    String mail;
    private long pressedTime;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_btn = findViewById(R.id.login_btn);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        error1 = findViewById(R.id.error1);
        error2 = findViewById(R.id.error2);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                error1.setVisibility(View.GONE);
                error2.setVisibility(View.GONE);
                login();

            }
        });

    }


    public void login() {


        Email = email.getText().toString();
        Password = password.getText().toString();


        String URL = "http://sbm.spksystems.in/public/api/login";

        JSONObject jsonBody = new JSONObject();


        try {

            jsonBody.put("email", Email);
            jsonBody.put("password", Password);



            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {



                    try {
                        String Success = response.getString("success");
                        String msg = response.getString("message");



                        data = response.getJSONObject("data");
                        token = data.getString("token");


                        user = data.getJSONObject("user");
                        id = user.getString("id");
                        mail = user.getString("email");



                        if (Success.equals("true")) {

                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, FrontPage.class);
                            intent.putExtra("id", id);
                            intent.putExtra("token", token);
                            intent.putExtra("mail",mail);

                            PreferenceUtils.saveid(id, MainActivity.this);
                            PreferenceUtils.saveToken(token, MainActivity.this);

                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("mail",mail);
                            editor.apply();
                            startActivity(intent);



                        }else{
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
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
                        if(Email.isEmpty()){
                            JSONObject jsonObject = new JSONObject(str);
                            JSONObject data = jsonObject.getJSONObject("data");

                            JSONArray jsonArray1 = data.getJSONArray("email");

                            error1.setText(jsonArray1.getString(0));
                            error1.setVisibility(View.VISIBLE);


                        }else if(!Email.matches(emailPattern)){
                            JSONObject jsonObject = new JSONObject(str);
                            JSONObject data = jsonObject.getJSONObject("data");

                            JSONArray jsonArray1 = data.getJSONArray("email");

                            error1.setText(jsonArray1.getString(0));
                            error1.setVisibility(View.VISIBLE);
                        }else{

                            error1.setVisibility(View.GONE);
                        }




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        if(Password.isEmpty()){


                            JSONObject jsonObject = new JSONObject(str);
                            JSONObject data = jsonObject.getJSONObject("data");


                            JSONArray jsonArray2 = data.getJSONArray("password");
                            error2.setText(jsonArray2.getString(0));
                            error2.setVisibility(View.VISIBLE);

                        }else if(Password.length()<8){
                            JSONObject jsonObject = new JSONObject(str);
                            JSONObject data = jsonObject.getJSONObject("data");


                            JSONArray jsonArray2 = data.getJSONArray("password");
                            error2.setText(jsonArray2.getString(0));
                            error2.setVisibility(View.VISIBLE);

                        }else if(Password.length()>20){
                            JSONObject jsonObject = new JSONObject(str);
                            JSONObject data = jsonObject.getJSONObject("data");


                            JSONArray jsonArray2 = data.getJSONArray("password");
                            error2.setText(jsonArray2.getString(0));
                            error2.setVisibility(View.VISIBLE);

                        }else{
                            JSONObject jsonObject = new JSONObject(str);
                            JSONObject data = jsonObject.getJSONObject("data");


                           String er = data.getString("error");
                            error2.setText(er);
                            error2.setVisibility(View.VISIBLE);

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
                    return params;
                }

            };

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            requestQueue.getCache().clear();
            requestQueue.add(jsonObjectRequest);



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            new AlertDialog.Builder(MainActivity.this).setIcon(R.drawable.ic_baseline_warning_24)
                    .setMessage("Are you sure want to exit")
                    .setNegativeButton(android.R.string.no,null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MainActivity.super.onBackPressed();
                            finishAffinity();
                        }
                    }).create().show();

        }
        else  {

            Toast.makeText(MainActivity.this, "Press back to exit", Toast.LENGTH_SHORT).show();

        }
        pressedTime = System.currentTimeMillis();





    }

}