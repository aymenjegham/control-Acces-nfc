/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.support.android.designlibdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Random;

public class DetailContactActivity extends BasicActivity {

    public static final String EXTRA_NAME = "cheese_name";
    TextView _name;
    private final Handler mHandler = new Handler();
    TextView _prenom;
    ImageView Pic;
    String QRcode,photo;
    public  Contact contact;
    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        contact= getIntent().getParcelableExtra("ContactTag");


        // Inserting Contacts
       // if(contact.getID()==0) {
       //     db.addContact(contact);
      //  }
        //TextView txt = (TextView) findViewById(R.id.no);
        final String cheeseName = intent.getStringExtra(EXTRA_NAME);
         QRcode = intent.getStringExtra("QRcode");
         photo = intent.getStringExtra("URL");

        contact= getIntent().getParcelableExtra("ContactTag");

        System.out.println(contact.toString());




        Typeface type = Typeface.createFromAsset(getAssets(), "varsity_regular.ttf");
        TextView logo = (TextView)findViewById(R.id.logo);
        logo.setTypeface(type);


        Pic = (ImageView)findViewById(R.id.access);

        _name=         ((TextView) findViewById(R.id.Nom));


        _prenom=       ((TextView) findViewById(R.id.Prenom));


        _name.setText(contact.getName());
        _prenom.setText(contact.get_prenom());

        new DownloadImageTask().execute(Pic);


       // for(int i = 0 ; i < 1 ; i++ )
       //     new FetchFilesTask().execute(image_url[i]);
    }





    private void loadBackdrop() {
        //final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
       // Glide.with(this).load(R.drawable.image).centerCrop().into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_action, menu);
        return true;
    }
    public void Print(View view) {
        sendURL();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_edit:
                Intent intent = new Intent(this, EditContactActivity.class);
                intent.putExtra(EditContactActivity.EXTRA_NAME, "Hello");
                intent.putExtra("ContactTag",contact);
                this.startActivity(intent);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public class DownloadImageTask extends AsyncTask<ImageView, Void, Bitmap> {

        ImageView imageView = null;

        @Override
        protected Bitmap doInBackground(ImageView... imageViews) {
            this.imageView = imageViews[0];
            return download_Image("http://192.168.1.179:12065/nfc/profile.png");
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }

        private Bitmap download_Image(String url) {

            Bitmap bmp =null;
            try{
                URL ulrn = new URL(url);


                HttpURLConnection con = (HttpURLConnection)ulrn.openConnection();
                InputStream is = con.getInputStream();
                bmp = BitmapFactory.decodeStream(is);
                if (null != bmp)
                    return bmp;

            }catch(Exception e){}
            return bmp;
        }


    }

    private void sendURL() {




        new Thread(new Runnable() {
            final ProgressDialog loadingdialog = ProgressDialog.show(DetailContactActivity.this,
                    "","Scanning Please Wait",true);
            @Override
            public void run() {
                try {

                    String dataUrl = "http://192.168.1.179:12065/nfc/printBadge.php?id="+QRcode+"&printer=3";
                    String dataUrlParameters = "id="+QRcode+"&printer=3";
                    Log.e("dataUrlParameters ", " j " + dataUrl + dataUrlParameters);
                    final String res= postData(dataUrl, dataUrlParameters);

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            loadingdialog.dismiss();
                            showChangeLangDialog(0, res);

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }
    public String postData(String sURL,String sData)
    {
        try
        {
            String data = URLEncoder.encode("jsonString", "UTF-8")+ "=" + URLEncoder.encode(sData, "UTF-8");
            URL url = new URL(sURL);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while((line = reader.readLine()) != null)
            {

                sb.append(line + "\n");
            }


            return sb.toString();

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }


    public void showChangeLangDialog(int position,final String  strt) {
        if (strt==null) {
            Toast.makeText(this, R.string.info_no_tag_found,
                    Toast.LENGTH_LONG).show();

            return;
        }







        String str[]= strt.split("\\|");

        Log.e("str  : strstr   " + str.length, str[0]);
        if(strt.contains("OK")==true) {
            Toast.makeText(this,"Print on cours",
                    Toast.LENGTH_LONG).show();
            finish();
        }
      /* AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        final int pos=position;

        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);
        final RatingBar RatingBar = (RatingBar) dialogView.findViewById(R.id.ratingBar);
        final EditText Note = (EditText) dialogView.findViewById(R.id.editText);
        edt.setText(str[1] + " " + str[2]);
        dialogBuilder.setMessage(str[1] + " " + str[2] + "\n" + str[5]);
        dialogBuilder.setTitle("Description Géneral du Contact");
        dialogBuilder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                AjouterContact(edt.getText().toString(), pos, str,RatingBar.getNumStars(),Note.getText().toString());
            }
        });
        dialogBuilder.setNeutralButton("Editer", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                EditerContact(edt.getText().toString(), pos, str,RatingBar.getNumStars(),Note.getText().toString());
            }
        });
        dialogBuilder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();*/

        // EditerContact("", position, str,0,"");
    }
}