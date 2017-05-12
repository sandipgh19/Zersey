package com.example.sandipghosh.zersey;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by sandipghosh on 09/05/17.
 */

public class NewEvent extends AppCompatActivity implements View.OnClickListener {

    private static final int STORAGE_PERMISSION_CODE = 123;
    private static final int PICK_FILE_REQUEST = 1;
    private static final String TAG = MainActivity.class.getSimpleName();
    private String selectedFilePath;
    private String SERVER_URL = "https://sandipgh19.000webhostapp.com/zersey/send.php";
    ImageView ivAttachment;
    Button bUpload;
    TextView tvFileName;
    EditText title,category,description;
    ProgressDialog dialog;
    SharedPreferences sharedPreferences;
    String name,email;
    Toolbar toolbar;
    private TextInputLayout titleInput;
    private TextInputLayout categoryInput;
    private TextInputLayout descriptionInput;
    private ScrollView scrollView;

    String userTitle,userCatagory,userDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        requestStoragePermission();

        setTitle("New Event");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences("ZerseyDetails", Context.MODE_PRIVATE);
        name = sharedPreferences.getString("name","");
        email = sharedPreferences.getString("email","");

        title = (EditText) findViewById(R.id.title);
        category = (EditText) findViewById(R.id.category);
        description = (EditText) findViewById(R.id.description);
        ivAttachment = (ImageView) findViewById(R.id.ivAttachment);
        bUpload = (Button) findViewById(R.id.b_upload);
        tvFileName = (TextView) findViewById(R.id.tv_file_name);

        titleInput = (TextInputLayout) findViewById(R.id.title_layout);
        categoryInput = (TextInputLayout) findViewById(R.id.category_layout);
        descriptionInput = (TextInputLayout) findViewById(R.id.description_layout);
        scrollView = (ScrollView) findViewById(R.id.scrollViewNew);

        ivAttachment.setOnClickListener(this);
        bUpload.setOnClickListener(this);
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View v) {

        if(v== ivAttachment){

            //on attachment icon click
            showFileChooser();
        }
        if(v== bUpload){

           // dialog = ProgressDialog.show(NewEvent.this,"","Uploading File...",true);

            userTitle = title.getText().toString();
            userCatagory = category.getText().toString();
            userDescription = description.getText().toString();

            Log.i("user11",userTitle);
            Log.i("user12",userCatagory);
            Log.i("user13",userDescription);

            String errorMessage;
            if (userTitle.equals("")) {
                errorMessage = getString(R.string.error_title);
                titleInput.setErrorEnabled(true);
                titleInput.setError(errorMessage);
                scrollView.smoothScrollTo(0, titleInput.getTop());
                return;
            }
            if (userCatagory.equals("")) {
                errorMessage = getString(R.string.error_catagory);
                categoryInput.setErrorEnabled(true);
                categoryInput.setError(errorMessage);
                scrollView.smoothScrollTo(0, categoryInput.getTop());
                return;
            }

            if (userDescription.equals("")) {
                errorMessage = getString(R.string.error_description);
                descriptionInput.setErrorEnabled(true);
                descriptionInput.setError(errorMessage);
                scrollView.smoothScrollTo(0, descriptionInput.getTop());
                return;
            }

            //on upload button Click
            if(selectedFilePath != null){

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //creating new thread to handle Http Operations
                        uploadFile(selectedFilePath);
                    }
                }).start();

              // sendData();
            }else{
                Toast.makeText(NewEvent.this,"Please choose a File First",Toast.LENGTH_SHORT).show();
            }

        }

    }

    private void showFileChooser() {
        Intent intent = new Intent();
        //sets the select file to all types of files
        intent.setType("image/*");
        //allows to select data and return it
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //starts new activity to select file and return data
        startActivityForResult(Intent.createChooser(intent,"Choose File to Upload.."),PICK_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == PICK_FILE_REQUEST){
                if(data == null){
                    //no data present
                    return;
                }


                Uri selectedFileUri = data.getData();
                selectedFilePath = FilePath.getPath(this,selectedFileUri);
                Log.i(TAG,"Selected File Path:" + selectedFilePath);

                if(selectedFilePath != null && !selectedFilePath.equals("")){
                    tvFileName.setText(selectedFilePath);
                }else{
                    Toast.makeText(this,"Cannot upload file to server",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //android upload file to server
    public void uploadFile(final String selectedFilePath){


        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, SERVER_URL)
                    .addFileToUpload(selectedFilePath, "uploaded_file") //Adding file
                    .addParameter("title", userTitle)//Adding text parameter to the request
                    .addParameter("category",userCatagory)
                    .addParameter("description",userDescription)
                    .addParameter("email",email)
                    .addParameter("name",name)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }

       // dialog.dismiss();

        //Intent intent = new Intent(this,SecondActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
       // startActivity(intent);



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
                Intent intent = new Intent(this,SecondActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        // code here to show dialog
       // super.onBackPressed();  // optional depending on your needs
        Intent intent = new Intent(this,SecondActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}
