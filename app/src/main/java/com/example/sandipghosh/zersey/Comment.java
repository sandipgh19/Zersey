package com.example.sandipghosh.zersey;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sandipghosh on 10/05/17.
 */

public class Comment extends AppCompatActivity implements View.OnClickListener {

    private ListView listView;
    private static final String SHOW_COMMENT_URL = "https://sandipgh19.000webhostapp.com/zersey/show_comment.php";
    private static final String SEND_COMMENT_URL = "https://sandipgh19.000webhostapp.com/zersey/send_comment.php";
    private static final String SEND_LIKE_URL = "https://sandipgh19.000webhostapp.com/zersey/send_like.php";
    private static final String SHOW_LIKE_URL = "https://sandipgh19.000webhostapp.com/zersey/show_like.php";
    private Button send,like;
    private EditText text;
    String comment,name,email;
    int id;
    TextView likeView;
    SharedPreferences sharedPreferences;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        setTitle("Like & Comment");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        if(Integer.valueOf(intent.getStringExtra("position"))>=0) {

            id = Integer.valueOf(intent.getStringExtra("position"));
        }

        Log.i("Position", String.valueOf(id));

        listView = (ListView) findViewById(R.id.listView);

        text = (EditText) findViewById(R.id.text);

        send = (Button) findViewById(R.id.comment);

        sharedPreferences = getSharedPreferences("ZerseyDetails", Context.MODE_PRIVATE);
        name = sharedPreferences.getString("name","");
        email = sharedPreferences.getString("email","");

        like = (Button) findViewById(R.id.like);

        likeView = (TextView) findViewById(R.id.liketext);

        send.setOnClickListener(this);
        like.setOnClickListener(this);

        showCommentData();
        showLikeData();
    }


    private void showCommentData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,SHOW_COMMENT_URL ,
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

                params.put("id",String.valueOf(id+1));
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json){
        ParseJSON pj = new ParseJSON(json);
        pj.parseJSON();
        Custom_list cl = new Custom_list(this, ParseJSON.name,ParseJSON.comment);
        listView.setAdapter(cl);
    }


    private void sendCommentData() {

        comment = text.getText().toString();

        if(comment==null) {

            Toast.makeText(this,"Please fill comment line",Toast.LENGTH_LONG).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST,SEND_COMMENT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("MY TEST",response);

                        showCommentData();
                        text.setText("");


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

                params.put("id",String.valueOf(id+1));
                params.put("name",name);
                params.put("comment",comment);
                params.put("email",email);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if(id==R.id.comment) {
            //Toast.makeText(this,"Login",Toast.LENGTH_LONG).show();
            sendCommentData();

        } else if(id == R.id.like) {

            sendLikeData();
        }

    }

    public void showLikeData() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,SHOW_LIKE_URL ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("MY TEST",response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            likeView.setText(jsonObject.getString("result")+" likes");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


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

                params.put("id",String.valueOf(id+1));
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    public void sendLikeData() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,SEND_LIKE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("MY TEST",response);

                        showLikeData();

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

                params.put("id",String.valueOf(id+1));
                params.put("name",name);
                params.put("email",email);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
