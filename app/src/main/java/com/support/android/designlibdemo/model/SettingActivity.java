package com.support.android.designlibdemo.model;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.picasso.Picasso;
import com.support.android.designlibdemo.MainActivity;
import com.support.android.designlibdemo.ProfileActivity;
import com.support.android.designlibdemo.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.R.attr.button;
import static android.R.attr.text;
import static android.R.id.text1;

public class SettingActivity extends AppCompatActivity {
    EditText User, Pass;
    Button button;
    private static final String JSON_URL = "http://192.168.1.208/www/nfc/dbConnected.php";
    public Editable value2;
    public String value1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        User = (EditText) findViewById(R.id.editText2);
        Pass = (EditText) findViewById(R.id.editText3);
        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             }
        });


    }


    @Override
    protected void onPause() {
        super.onPause();
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }


}
