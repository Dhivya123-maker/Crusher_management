package com.example.crushermanagement.UserList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.crushermanagement.R;
import com.example.crushermanagement.utils.PreferenceUtils;
import com.github.ybq.android.spinkit.SpinKitView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserListClass extends AppCompatActivity{
    RecyclerView recyclerView;
    List<UserListModel> userModelList;
    UserListAdapter adapter;
    String id,name,email;
    ProgressBar progressBar;
    SpinKitView spinKitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        recyclerView = findViewById(R.id.recycle);
        spinKitView = findViewById(R.id.progressBar);
        spinKitView.setVisibility(View.VISIBLE);

        user_list();

    }
    public void user_list(){

        String JSON_URL = "http://sbm.spksystems.in/public/api/user-list";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONObject>() {
            @SuppressLint("CheckResult")
            @Override
            public void onResponse(JSONObject response) {
                Log.i("fjhdhfiuewh2",response.toString());
                spinKitView.setVisibility(View.GONE);
                try {

                    userModelList = new ArrayList<>();

                    String Success = response.getString("success");
                    JSONArray jsonArray = response.getJSONArray("data");
                    for(int i=0;i< jsonArray.length();i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        id = jsonObject.getString("id");
                        name = jsonObject.getString("name");
                        email = jsonObject.getString("email");


                        UserListModel model = new UserListModel();
                        model.setName(name);
                        model.setEmail(email);
                        model.setId(id);

                        userModelList.add(model);

                    }

                        recyclerView.setLayoutManager(new LinearLayoutManager(UserListClass.this));

                        adapter =  new UserListAdapter(UserListClass.this,userModelList);
                        recyclerView.setAdapter(adapter);







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
                params.put("Authorization","Bearer "+ PreferenceUtils.getToken(UserListClass.this));


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(UserListClass.this);
        requestQueue.add(jsonObjectRequest);
        requestQueue.getCache().remove(jsonObjectRequest.getCacheKey());

    }
    public void onBackPressed() {

        UserListClass.super.onBackPressed();

    }
}