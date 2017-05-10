package com.example.sandipghosh.zersey;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sandipghosh on 10/05/17.
 */

public class Comment extends AppCompatActivity implements View.OnClickListener {

    private ListView listView;
    private static final String SHOW_URL = "";
    private static final String SEND_URL = "";
    private Button send;
    private EditText text;
    String data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        listView = (ListView) findViewById(R.id.listView);

        text = (EditText) findViewById(R.id.text);

        send = (Button) findViewById(R.id.comment);

        send.setOnClickListener(this);

        getData();
    }


    private void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,SHOW_URL ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("MY TEST",response);

                        showJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Comment.this,error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                params.put("","");
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json){
        ParseJSON pj = new ParseJSON(json);
        pj.parseJSON_follow();
        Custom_list cl = new Custom_list(this, ParseJSON.name,ParseJSON.comment);
        listView.setAdapter(cl);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if(id==R.id.comment) {
            //Toast.makeText(this,"Login",Toast.LENGTH_LONG).show();

        }

    }
}
