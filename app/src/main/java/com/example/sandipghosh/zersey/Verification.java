package com.example.sandipghosh.zersey;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by sandipghosh on 14/05/17.
 */

public class Verification extends AppCompatActivity implements View.OnClickListener {

    EditText code;
    TextView mobile;
    Button verify;
    Toolbar toolbar;
    String no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        setTitle("Verification");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        if(intent.getStringExtra("mobile")!= null) {

            no = intent.getStringExtra("mobile");

        }

        code = (EditText) findViewById(R.id.confirm);

        mobile = (TextView) findViewById(R.id.phone);

        verify = (Button) findViewById(R.id.verify);

        mobile.setText(no);

        verify.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if(id==R.id.verify) {
            //Toast.makeText(this,"Login",Toast.LENGTH_LONG).show();
            verifyOtp();

        }

    }


    private void verifyOtp() {
        String otp = code.getText().toString().trim();

        if (!otp.isEmpty()) {
            Intent grapprIntent = new Intent(getApplicationContext(), HttpService.class);
            grapprIntent.putExtra("otp", otp);
            startService(grapprIntent);
        } else {
            Toast.makeText(getApplicationContext(), "Please enter the OTP", Toast.LENGTH_SHORT).show();
        }
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
