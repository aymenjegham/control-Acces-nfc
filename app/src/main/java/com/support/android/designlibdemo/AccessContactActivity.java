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

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.support.android.designlibdemo.model.Model;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static com.support.android.designlibdemo.Common.byte2HexString;

public class AccessContactActivity extends BasicActivity {
    @Override
    public void onPause() {
        super.onPause();
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    private final Handler mHandler = new Handler();
    TextView _name;
    TextView _nbrAcces;
    TextView _prenom;
    ImageView Acces;
    Contact contact;
    public static final String EXTRA_NAME = "cheese_name";
    Handler handler = new Handler();
    private Intent mOldIntent = null;
    String lastUID;
    boolean mReload= false;
    int nbrAcces=0,nbrPerson=0;
    private AlertDialog mEnableNfc;
    private boolean mResume = true;
    final Handler myHandler = new Handler();
    DatabaseHandler db;
     MCReader reader;
    Thread thread = new Thread() {
        @Override
        public void run() {
            try {
                while(true) {

                    sleep(1000);
                    if(!reader.isConnected())
                    test();


                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.access_contact);

        Intent intent = getIntent();
        //contact= getIntent().getParcelableExtra("ContactTag");
        final String cheeseName = intent.getStringExtra(EXTRA_NAME);

        Typeface type = Typeface.createFromAsset(getAssets(), "varsity_regular.ttf");
        TextView logo = (TextView)findViewById(R.id.logo);
        logo.setTypeface(type);







        Acces = (ImageView)findViewById(R.id.access);

        _name=         ((TextView) findViewById(R.id.Nom));
        _nbrAcces=         ((TextView) findViewById(R.id.nbrA));

        _prenom=       ((TextView) findViewById(R.id.Prenom));






        Acces.setBackgroundResource(R.drawable.shield3);


        Log.e("Search Query: ", "  hellooo " + cheeseName);


        handleIntent(getIntent());
        Common.setNfcAdapter(NfcAdapter.getDefaultAdapter(this));

        db = new DatabaseHandler(this);
       // reader = Common.checkForTagAndCreateReader(this);

    }


    @Override
    public void onNewIntent(Intent intent) {
        Log.e("Search onNewIntent: ", "  onNewIntent == onNewIntent ");
        int typeCheck =Common.treatAsNewTag(intent, this);

        if (typeCheck == -1 || typeCheck == -2) {
            final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
            tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE);
            Acces.setBackgroundResource(R.drawable.shield1);
        }
        else
        {
            if (typeCheck != -4)
            {
                reader = Common.checkForTagAndCreateReader(this);
                if(!thread.isAlive()) {
                    Log.e("Search handleIntent: ", " isAlive ");

                    //thread.start();
                }
                mReload=true;
                readTag(typeCheck);
                // reader = Common.checkForTagAndCreateReader(this);
            }
        }
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
            //updateTagInfo(Common.getTag());
        }

    }
    private void handleIntent(Intent intent) {


        Log.e("Search handleIntent: ", "  onNewIntent == handleIntent ");
            int typeCheck = Common.treatAsNewTag(intent, this);
            if (typeCheck == -1 || typeCheck == -2) {
                final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
                tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE);

                Acces.setBackgroundResource(R.drawable.shield1);
            }
            else
            {
                if (typeCheck != -4)
                {

                    reader = Common.checkForTagAndCreateReader(this);
                    readTag(typeCheck);


                }
            }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                onBackPressed();
                return true;
            case R.id.action_save:
                Log.e("Search Query: ", "  action_save ");
                WriteTag(0);



        }
        return super.onOptionsItemSelected(item);
    }

    private void test() {
        Log.e("Search handleIntent: ", " test ");
        if(mReload){
            mReload=false;
            Log.e("Search handleIntent: ", " typeCheck ");
            try {
                // Double beeps:     tg.startTone(ToneGenerator.TONE_PROP_ACK);
                // Double beeps:     tg.startTone(ToneGenerator.TONE_PROP_BEEP2);
                // Sounds all wrong: tg.startTone(ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE);
                final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
                tg.startTone(ToneGenerator.TONE_PROP_BEEP2);
                //Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
               // Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
               // r.play();



                new Timer().schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                Log.e("Search handleIntent: ", " ffffff ");
                                clearView();
                            }
                        },
                        5000
                );
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


      //  }
/*
        if (reader == null) {
            Log.e("Search Query: ", "  reader == null ");
            finish();
            return;
        }

        new Thread(new Runnable() {
            final ProgressDialog loadingdialog = ProgressDialog.show(AccessContactActivity.this,
                    "","Scanning Please Wait",true);
            @Override
            public void run() {


                // read.close();
                try {
                    String[] str=reader.CheckCard();//reader.readSector(1, new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,}, false);
                    String NO_DATA = "--------------------------------";
                    String tmpt="";
                    if(str==null ) {
                        loadingdialog.dismiss();

                    }

                    final String[] res= str;

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            loadingdialog.dismiss();
                            String tmp=byte2HexString(Common.getTag().getId());
                            if(lastUID!=tmp)
                                lastUID=tmp;
                            showChangeLangDialog(typeCheck, res);

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
                //final String [] finalTmpt = reader.ReadCard();
                //reader.close();

            }
        }).start();*/
    }
    public void clearView(){
        myHandler.post(myRunnable);
    }

    final Runnable myRunnable = new Runnable() {
        public void run() {
            _name.setText("");
            _prenom.setText("");

            Acces.setBackgroundResource(R.drawable.shield3);
        }
    };


    private void readTag(final int typeCheck) {


        if (reader == null) {
            Log.e("Search Query: ", "  reader == null ");
            finish();
            return;
        }

        new Thread(new Runnable() {
            final ProgressDialog loadingdialog = ProgressDialog.show(AccessContactActivity.this,
                    "","Scanning Please Wait",true);
            @Override
            public void run() {


                // read.close();
                try {
                    String[] str=reader.CheckCard();//reader.readSector(1, new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,}, false);
                    String NO_DATA = "--------------------------------";
                    String tmpt="";
                    if(str==null ) {
                        loadingdialog.dismiss();

                    }
                      /*  for (int i = 0; i < 2; i++) {
                            if (str[i].compareTo(NO_DATA) == 0) break;

                            tmpt += reader.hex2ascii(str[i]);
                            //tmpt+="\n";
                        }*/
                    final String[] res= str;

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            loadingdialog.dismiss();
                            String tmp=byte2HexString(Common.getTag().getId());
                            if(lastUID!=tmp)
                            lastUID=tmp;
                            showChangeLangDialog(typeCheck, res);

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
                //final String [] finalTmpt = reader.ReadCard();
                //reader.close();

            }
        }).start();
    }

    private void WriteTag(final int typeCheck) {


        if (reader == null) {
            Log.e("Search Query: ", "  reader == null ");
            finish();
            return;
        }

        new Thread(new Runnable() {
            final ProgressDialog loadingdialog = ProgressDialog.show(AccessContactActivity.this,
                    "","Scanning Please Wait",true);
            @Override
            public void run() {
               /* contact=new Contact(0, str[1], str[2], str[3], str[4], str[6], str[11]
                        , "0", str[5], str[7], str[9], str[8], 1, "", str[10]);
                new Contact(0,_name.getText().toString(),_prenom.getText().toString()
                        ,_phone_number.getText().toString()
                        ,_email.getText().toString(),
                        _societe.getText().toString()
                        ,_profile.getText().toString(),
                        "",
                        _adresse.getText().toString()
                        ,_fixe.getText().toString()
                        ,"",
                        _skype.getText().toString(),contact.getScore(),contact.getNote(),
                        _site.getText().toString());*/
                // read.close();
                Log.e("TTTTTTTTT",""+_name.getText().toString().getBytes()[0]+ "  dd "+_name.getText().toString().getBytes().length );
                int s=_name.getText().toString().getBytes().length;
                int l =(s<(0+1)*16 ? s:(0+1)*16);

                Log.e("TTTTTTTTT",""+_name.getText().toString().substring(0*16,l)+ "  dd "+_name.getText().toString().getBytes().length/16+1 );
                try {

                  //  for(int i =0;i<15;i++)
                   // {
                       // byte[] tmp= new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
                       //         (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,};

                        byte[] tmp2=_name.getText().toString().getBytes();
                        for(int j=0;j<(int)tmp2.length/16+1;j++){
                            byte[] tmp= new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
                                    (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,};
                            //_name.getText().toString().substring(j*16,(j+1)*16);
                            int ss=_name.getText().toString().getBytes().length;
                            int ll =(s<(0+1)*16 ? s:(0+1)*16);
                            Log.e("TTTTTTTTT",""+_name.getText().toString().substring(j*16,l)+ "  dd "+(int)tmp2.length/16 );

                            tmp= Arrays.copyOf(_name.getText().toString().substring(j * 16,l).getBytes(), 16);
                            reader.writeBlock(2, j,tmp

                                    ,
                                    new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,}, false);

                        }

                 //   }
                 //   reader.writeBlock(2, 0,_name.getText().toString().getBytes()

                 //          ,
                 //           new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,}, false);

                    String[] str=reader.ReadCard();//reader.readSector(1, new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,}, false);
                    String NO_DATA = "--------------------------------";
                    String tmpt="";
                    if(str==null ) {
                        loadingdialog.dismiss();

                    }
                      /*  for (int i = 0; i < 2; i++) {
                            if (str[i].compareTo(NO_DATA) == 0) break;

                            tmpt += reader.hex2ascii(str[i]);
                            //tmpt+="\n";
                        }*/
                    final String[] res= str;

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            loadingdialog.dismiss();
                            showChangeLangDialog(typeCheck, res);

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
                //final String [] finalTmpt = reader.ReadCard();
                //reader.close();

            }
        }).start();
    }
    public void showChangeLangDialog(int position,final String [] str) {
        if (str==null) {
            Toast.makeText(this, R.string.info_no_tag_found,
                    Toast.LENGTH_LONG).show();
            Log.e("Search Query: ", "  showChangeLangDialog ");
            return;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        contact=new Contact(0, str[1], str[2], str[3], str[4], str[6], str[11]
                                   , "0", str[5], str[7], str[9], str[8], 1, "", str[10],dateFormat.format(date));
        _name.setText(contact.getName());
        _prenom.setText(contact.get_prenom());
        db.addContact(contact);

        Acces.setBackgroundResource(R.drawable.shield);
        nbrAcces++;
        _nbrAcces.setText(""+nbrAcces);

        List<Contact> contacts;
        contacts = db.getAllContacts();

        for (Contact cn : contacts) {
            String log = "Contact  : "+cn.toString();
            Log.d("Contact: ", log);

        }
    }
    private void checkNfc() {

        Log.e("Search handleIntent: ", "  checkNfc == checkNfc ");
        // Check if the NFC hardware is enabled.
        if (Common.getNfcAdapter() != null
                && !Common.getNfcAdapter().isEnabled()) {
            // NFC is disabled.
            // Use as editor only?
        Log.e("getNfcAdapter"," getNfcAdapter");

        } else {
            // NFC is enabled. Hide dialog and enable NFC
            // foreground dispatch.
            Log.e("getNfcAdapter"," else getNfcAdapter");
            if (mOldIntent != getIntent()) {
                int typeCheck = Common.treatAsNewTag(getIntent(), this);
                Log.e("getNfcAdapter"," else typeCheck"+typeCheck);
                if (typeCheck == -1 || typeCheck == -2) {
                    // Device or tag does not support Mifare Classic.
                    // Run the only thing that is possible: The tag info tool.
                    Intent i = new Intent(this, TagInfoTool.class);
                    startActivity(i);
                }
                mOldIntent = getIntent();
            }
            Common.enableNfcForegroundDispatch(this);

            if (mEnableNfc == null) {
                createNfcEnableDialog();
            }
            mEnableNfc.hide();
            if (Common.hasMifareClassicSupport() ) {

            }
        }
    }

    public void ListeActivite(View view) {
        Intent intent = new Intent(this, ListeEntree.class);
        intent.putExtra(EditContactActivity.EXTRA_NAME, "Ajouter");
        startActivity(intent);
    }

    /**
     * Create a dialog that send user to NFC settings if NFC is off (and save
     * the dialog in {@link #mEnableNfc}). Alternatively the user can choos to
     * use the App in editor only mode or exit the App.
     */
    private void createNfcEnableDialog() {
        mEnableNfc = new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_nfc_not_enabled_title)
                .setMessage(R.string.dialog_nfc_not_enabled)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton(R.string.action_nfc,
                        new DialogInterface.OnClickListener() {
                            @Override
                            @SuppressLint("InlinedApi")
                            public void onClick(DialogInterface dialog, int which) {
                                // Goto NFC Settings.
                                if (Build.VERSION.SDK_INT >= 16) {
                                    startActivity(new Intent(
                                            Settings.ACTION_NFC_SETTINGS));
                                } else {
                                    startActivity(new Intent(
                                            Settings.ACTION_WIRELESS_SETTINGS));
                                }
                            }
                        })
                .setNeutralButton(R.string.action_editor_only,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Only use Editor.

                            }
                        })
                .setNegativeButton(R.string.action_exit_app,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // Exit the App.
                                finish();
                            }
                        }).create();
    }
}
