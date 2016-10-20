package com.support.android.designlibdemo;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.support.android.designlibdemo.model.SettingActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.R.attr.data;
import static android.R.attr.value;

public class ProfileActivity extends AppCompatActivity {
    private static final String JSON_URL = "http://192.168.1.208/www/nfc/parsemysql.php";
    TextView _name;
     TextView _prenom;
    ImageView Pic;
    Context context;
    Button button;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        _name=         ((TextView) findViewById(R.id.Nom));


        _prenom=       ((TextView) findViewById(R.id.Prenom));
        Pic = (ImageView)findViewById(R.id.access);
        button= (Button) findViewById(R.id.button2);


        try {
            getJSON(JSON_URL);

        }catch(IllegalArgumentException e){
            e.printStackTrace();
            Log.e("check","connexion");

        }





    }

    private void getJSON(String url) {
        class GetJSON extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            public String value1;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ProfileActivity.this, "Please Wait...",null,true,true);
             }

            @Override
            protected String doInBackground(String... params) {
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    String value = extras.getString("msg");
                    value1=value;
                    Log.e("qr","intent"+value);
                }
                  Log.e("vfvf","cvd"+value1);

                //String uri = params[0];
                Uri uri = new Uri.Builder()
                        .scheme("http")
                        .authority("192.168.1.208")
                        .path("/www/nfc/parsemysql.php")
                        .appendQueryParameter("param1",value1)
                        .appendQueryParameter("param2", "home")



                        .build();


                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri.toString());
                    Log.e("stringed","uri"+url);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setConnectTimeout(15000);
                    con.setReadTimeout(15000);


                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));


                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }


                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                 Log.e("jso,","mesg"+s);
                 try {
                    JSONObject  jsonRootObject = new JSONObject(s);
                    Log.e("json","obj"+jsonRootObject);

                     JSONArray jsonArray = jsonRootObject.optJSONArray("result");
                     Log.e("json","array"+jsonArray);
                     if(jsonArray.isNull(1)){
                         Log.e("null","array");
                         _name.setText("no identified acount");
                         button.setEnabled(false);


                     }

                     for(int i=0; i < jsonArray.length(); i++){
                                 JSONObject jsonObject = jsonArray.getJSONObject(i);
                             String name = jsonObject.optString("name").toString();
                             String prenom = jsonObject.optString("prenom").toString();
                             String img = jsonObject.optString("img").toString();

                         Log.e("pong","kong"+name);
                         Log.e("pong","kong"+img);



                         _name.setText(name);
                             _prenom.setText(prenom);
                         Picasso.with(ProfileActivity.this).load("http://192.168.1.208/www/nfc/user_images/"+img).resize(100,100)
                                 .centerCrop().into(Pic);
                         button.setEnabled(true);

                         }






                 } catch (JSONException e)
                 {e.printStackTrace();
                     Log.e("no","identifier");
                     Log.e("check","connexion");
                     _name.setText("check you connexion with mysql Data Base");
                     button.setEnabled(false);



                 }
                catch (NullPointerException e){
                    e.printStackTrace();
                    Log.e("empty","json");
                    _name.setText("retry again");
                    button.setEnabled(false);


                }


            }





        }
        GetJSON gj = new GetJSON();
        gj.execute(url);

    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent i =new Intent(this,MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
