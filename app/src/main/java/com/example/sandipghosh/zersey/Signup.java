package com.example.sandipghosh.zersey;

import android.app.ProgressDialog;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sandipghosh on 11/05/17.
 */

public class Signup extends AppCompatActivity implements View.OnClickListener {

    EditText name,email,password,mobile;
    Button signup;
    private User user;
    private TextInputLayout emailInput;
    private TextInputLayout passwordInput;
    private TextInputLayout nameInput;
    private TextInputLayout mobileInput;
    private ScrollView scrollView;
    private static final String SIGNUP_URL = "https://sandipgh19.000webhostapp.com/zersey/register.php";
    private SharedPreferences sharedPreferences;
    Toolbar toolbar;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        setTitle("Sign Up");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        mobile = (EditText) findViewById(R.id.mobile);
        emailInput = (TextInputLayout) findViewById(R.id.email_layout);
        passwordInput = (TextInputLayout) findViewById(R.id.password_layout);
        nameInput = (TextInputLayout) findViewById(R.id.name_layout);
        mobileInput = (TextInputLayout) findViewById(R.id.mobile_layout);
        scrollView = (ScrollView) findViewById(R.id.scrollViewSignup);
        user = new User();

        sharedPreferences = getSharedPreferences("ZerseyDetails", Context.MODE_PRIVATE);


        signup = (Button) findViewById(R.id.signup);

        signup.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if(id==R.id.signup) {
            //Toast.makeText(this,"Login",Toast.LENGTH_LONG).show();
            signUp();

        }

    }

    private void signUp() {

        user.setName(name.getText().toString());
        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());
        user.setMobile(mobile.getText().toString());

        passwordInput.setErrorEnabled(false);
        nameInput.setErrorEnabled(false);
        emailInput.setErrorEnabled(false);

        String errorMessage;
        if (!User.fieldValid(user.getName())) {
            errorMessage = getString(R.string.error_username);
            nameInput.setErrorEnabled(true);
            nameInput.setError(errorMessage);
            scrollView.smoothScrollTo(0, nameInput.getTop());
            return;
        }
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

        if(!User.MobileValid(user.getMobile())) {
            errorMessage = getString(R.string.error_mobile);
            mobileInput.setErrorEnabled(true);
            mobileInput.setError(errorMessage);
            scrollView.smoothScrollTo(0, mobileInput.getTop());
            return;

        }

        dialog = ProgressDialog.show(Signup.this,"","Sign Up...",true);


        StringRequest stringRequest = new StringRequest(Request.Method.POST,Config.URL_REQUEST_SMS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("MY TEST",response);

                        dialog.dismiss();

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String message;
                            boolean success = jsonResponse.getBoolean("error");
                            message =jsonResponse.getString("message") ;
                           // if (message.equals("Success")) {
                            if(!success) {

                                Intent intent = new Intent(Signup.this,Verification.class);
                                intent.putExtra("mobile",user.getMobile());
                                startActivity(intent);

                              /*  User.saveLoginCredentials(sharedPreferences,
                                        user.getEmail(),
                                        user.getName());
                                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                                setResult(RESULT_OK, intent);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();*/
                            } else {

                                Toast.makeText(Signup.this,message,Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Signup.this,"Something Went Wrong", Toast.LENGTH_LONG).show();
                    }
                }){
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                params.put("name",user.getName());
                params.put("email",user.getEmail());
                params.put("password",user.getPassword());
                params.put("mobile",user.getMobile());
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
