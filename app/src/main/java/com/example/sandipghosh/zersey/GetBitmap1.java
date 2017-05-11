package com.example.sandipghosh.zersey;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.net.URL;

/**
 * Created by sandipghosh on 11/05/17.
 */

public class GetBitmap1 extends AsyncTask<Void,Void,Void> {

    private Context context;
    private String[] urls;
    private ProgressDialog loading;
    private SecondActivity secondActivity;

    public GetBitmap1(Context context, SecondActivity secondActivity, String[] urls){
        this.context = context;
        this.urls = urls;
        this.secondActivity = secondActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loading = ProgressDialog.show(context,"Downloading Image","Please wait...",false,false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        loading.dismiss();
        secondActivity.showData();
    }

    @Override
    protected Void doInBackground(Void... params) {
        for(int i=0; i<urls.length; i++){
            Config.bitmaps[i] = getImage(urls[i]);
        }
        return null;
    }

    private Bitmap getImage(String bitmapUrl){
        URL url;
        Bitmap image = null;
        try {
            url = new URL(bitmapUrl);
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }catch(Exception e){}
        return image;
    }
}