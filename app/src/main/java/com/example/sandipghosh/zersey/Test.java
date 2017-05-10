package com.example.sandipghosh.zersey;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by sandipghosh on 10/05/17.
 */

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        TextView textView = (TextView) findViewById(R.id.text);

        Intent intent = getIntent();

        textView.setText("Title: "+intent.getStringExtra("title")+" "+"Catagory: "+intent.getStringExtra("catagory")+ " "+
                " Description: "+intent.getStringExtra("description"));
    }
}
