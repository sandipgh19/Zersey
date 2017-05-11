package com.example.sandipghosh.zersey;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
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
 * Created by sandipghosh on 11/05/17.
 */

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText email,password;
    Button login;
    private User user;
    private TextInputLayout emailInput;
    private TextInputLayout passwordInput;
    private ScrollView scrollView;
    private static final String LOGIN_URL = "https://sandipgh19.000webhostapp.com/zersey/login.php";
    private SharedPreferences sharedPreferences;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Login");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        emailInput = (TextInputLayout) findViewById(R.id.email_layout);
        passwordInput = (TextInputLayout) findViewById(R.id.password_layout);
        scrollView = (ScrollView) findViewById(R.id.scrollViewLogin);
        user = new User();

        sharedPreferences = getSharedPreferences("ZerseyDetails", Context.MODE_PRIVATE);


        login = (Button) findViewById(R.id.login);

        login.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

            int id = v.getId();
            if(id==R.id.login) {
                //Toast.makeText(this,"Login",Toast.LENGTH_LONG).show();
                logIn();
            }
    }

    private void logIn() {

        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());

        passwordInput.setErrorEnabled(false);
        emailInput.setErrorEnabled(false);

        String errorMessage;
        if (!user.emailValid(user.getEmail())) {
            errorMessage = getString(R.string.error_email);
            emailInput.setErrorEnabled(true);
            emailInput.setError(errorMessage);
            scrollView.smoothScrollTo(0, emailInput.getTop());
            return;
        }
        if (!User.passwordValid(user.getPassword())) {
            errorMessage = getString(R.string.error_password);
            passwordInput.setErrorEnabled(true);
            passwordInput.setError(errorMessage);
            scrollView.smoothScrollTo(0, passwordInput.getTop());
            return;
        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST,LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("MY TEST",response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray result = jsonObject.getJSONArray("result");
                            JSONObject Data = result.getJSONObject(0);
                            user.setName(Data.getString("name"));
                            User.saveLoginCredentials(sharedPreferences,
                                    user.getEmail(),
                                    user.getName());
                            Intent intent = new Intent(Login.this, SecondActivity.class);
                            setResult(RESULT_OK, intent);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this,error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                params.put("email",user.getEmail());
                params.put("password",user.getPassword());
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
