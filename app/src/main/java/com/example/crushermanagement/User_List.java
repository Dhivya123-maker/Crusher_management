package com.example.crushermanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.crushermanagement.Old_entry.Old_entry;
import com.example.crushermanagement.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User_List extends AppCompatActivity{
    RecyclerView recyclerView;
    List<User_model> userModelList;
    Adapter adapter;
    String id,name,email;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        recyclerView = findViewById(R.id.recycle);

        user_list();

    }
    public void user_list(){

        String JSON_URL = "http://sbm.spksystems.in/public/api/user-list";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONObject>() {
            @SuppressLint("CheckResult")
            @Override
            public void onResponse(JSONObject response) {
                Log.i("fjhdhfiuewh2",response.toString());

                try {

                    userModelList = new ArrayList<>();

                    String Success = response.getString("success");
                    JSONArray jsonArray = response.getJSONArray("data");
                    for(int i=0;i< jsonArray.length();i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        id = jsonObject.getString("id");
                        name = jsonObject.getString("name");
                        email = jsonObject.getString("email");


                        User_model model = new User_model();
                        model.setName(name);
                        model.setEmail(email);
                        model.setId(id);

                        userModelList.add(model);

                    }

                        recyclerView.setLayoutManager(new LinearLayoutManager(User_List.this));

                        adapter =  new Adapter(User_List.this,userModelList);
                        recyclerView.setAdapter(adapter);







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
                params.put("Authorization","Bearer "+ PreferenceUtils.getToken(User_List.this));


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(User_List.this);
        requestQueue.add(jsonObjectRequest);
        requestQueue.getCache().remove(jsonObjectRequest.getCacheKey());

    }

}