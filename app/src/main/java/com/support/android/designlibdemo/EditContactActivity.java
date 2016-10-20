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
import android.graphics.Typeface;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Arrays;

public class EditContactActivity extends BasicActivity {


    private final Handler mHandler = new Handler();
    EditText _name;
    EditText _prenom;
    EditText _phone_number;
    EditText _email;
    EditText _societe;
    EditText _profile;
    EditText _UID;
    EditText _adresse;
    EditText _fixe;
    EditText _fax;
    EditText _skype;
    EditText _site;
    Contact contact;
    public static final String EXTRA_NAME = "cheese_name";

    private Intent mOldIntent = null;
    private AlertDialog mEnableNfc;
    private boolean mResume = true;
     MCReader reader;
    String cheeseName;

    @Override
    public void onPause() {
        super.onPause();
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        //contact= getIntent().getParcelableExtra("ContactTag");
          cheeseName = intent.getStringExtra(EXTRA_NAME);


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

        Button CallTel1 = (Button) findViewById(R.id.CallTel1);


        Button CallTel2 = (Button) findViewById(R.id.CallTel2);

        Button SendMail = (Button) findViewById(R.id.SendMail1);

        Button LocateSociete1 = (Button) findViewById(R.id.LocateSociete1);

        Button LocateSite1 = (Button) findViewById(R.id.LocateSite1);

        Button LocateAdres1 = (Button) findViewById(R.id.LocateAdres1);


        CallTel1.setTypeface(fontAwesomeFont);

        CallTel2.setTypeface(fontAwesomeFont);
        SendMail.setTypeface(fontAwesomeFont);

        LocateAdres1.setTypeface(fontAwesomeFont);

        LocateSite1.setTypeface(fontAwesomeFont);

        LocateSociete1.setTypeface(fontAwesomeFont);


        _name=         ((EditText) findViewById(R.id.Nom));

        _prenom=       ((EditText) findViewById(R.id.Prenom));

        _phone_number= ((EditText) findViewById(R.id.Tel1));

        _email=        ((EditText) findViewById(R.id.Mail1));

        _fixe=      ((EditText) findViewById(R.id.Tel2));


        _societe=      ((EditText) findViewById(R.id.Societe1));

        _adresse=          ((EditText) findViewById(R.id.Adres1));

        _profile=      ((EditText) findViewById(R.id.Position));

        _site=         ((EditText) findViewById(R.id.Site1));

        _skype=          ((EditText) findViewById(R.id.Skype1));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Log.e("Search Query: ", "  hellooo " + cheeseName);


        handleIntent(getIntent());
        Common.setNfcAdapter(NfcAdapter.getDefaultAdapter(this));
        reader = Common.checkForTagAndCreateReader(this);
        if(cheeseName.compareTo("Editer")==0)
        readTag(0);
    }



    @Override
    public void onNewIntent(Intent intent) {
        Log.e("Search Query: ","onNewIntent Query: " );
        int typeCheck = Common.treatAsNewTag(intent, this);
        if (typeCheck == -1 || typeCheck == -2) {
            // Device or tag does not support Mifare Classic.
            // Run the only thing that is possible: The tag info tool.
            Intent i = new Intent(this, TagInfoTool.class);
            startActivity(i);
        }
        else
        {
            if (typeCheck != -4)
            {
                if(cheeseName.compareTo("Editer")==0) {
                    reader = Common.checkForTagAndCreateReader(this);
                    readTag(typeCheck);
                }
                // reader = Common.checkForTagAndCreateReader(this);
            }
        }
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
            //updateTagInfo(Common.getTag());
        }
    }
    private void handleIntent(Intent intent) {
        Log.e("Search Query: ","handleIntent Query: " );

            int typeCheck = Common.treatAsNewTag(intent, this);
            if (typeCheck == -1 || typeCheck == -2) {
                // Device or tag does not support Mifare Classic.
                // Run the only thing that is possible: The tag info tool.
                Intent i = new Intent(this, TagInfoTool.class);
                startActivity(i);
            }
            else
            {
                if (typeCheck != -4)
                {
                    reader = Common.checkForTagAndCreateReader(this);
                    if(cheeseName.compareTo("Editer")==0)
                    readTag(typeCheck);
                   // reader = Common.checkForTagAndCreateReader(this);
                }
            }

    }

    private void loadBackdrop() {
      //  final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
      //  Glide.with(this).load(R.drawable.image).centerCrop().into(imageView);
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
                //reader = Common.checkForTagAndCreateReader(this);
                WriteTag(0);



        }
        return super.onOptionsItemSelected(item);
    }
    private void readTag(final int typeCheck) {


        if (reader == null) {
            Log.e("Search Query: ", " reader == null ");
            finish();
            return;
        }

        new Thread(new Runnable() {
            final ProgressDialog loadingdialog = ProgressDialog.show(EditContactActivity.this,
                    "","Scanning Please Wait",true);
            @Override
            public void run() {


                // read.close();
                try {
                    String[] str=reader.ReadCard();//reader.readSector(1, new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,}, false);
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
                            showChangeLangDialog(typeCheck, res);

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }


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
            final ProgressDialog loadingdialog = ProgressDialog.show(EditContactActivity.this,
                    "","Scanning Please Wait",true);
            @Override
            public void run() {
                String[] A=new String[]{
                        _name.getText().toString()
                        ,_prenom.getText().toString()
                        ,_phone_number.getText().toString()
                        ,_email.getText().toString(),
                        _societe.getText().toString()
                        ,_profile.getText().toString()
                        ,_adresse.getText().toString()
                        ,_fixe.getText().toString()
                        ,_skype.getText().toString()
                        ,_site.getText().toString()};

                Log.e("TTTTTTTTT",""+_name.getText().toString().getBytes()[0]+ "  dd "+_name.getText().toString().getBytes().length );
                int s=_name.getText().toString().getBytes().length;
                int l =(s<(0+1)*16 ? s:(0+1)*16);

                Log.e("TTTTTTTTT",""+_name.getText().toString().substring(0*16,l)+ "  dd "+_name.getText().toString().getBytes().length/16+1 );
                try {
                    for(int i =0;i<A.length;i++)
                   {
                       Log.e("for Start ", " i " + (i + 1));
                       int ss=A[i].getBytes().length;

                        byte[] tmp2=A[i].getBytes();
                        for(int j=0;j<3;j++){
                            Log.e("Start for", " j " + j + 1);
                            byte[] tmp= new byte[]{(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                                    (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
                            //_name.getText().toString().substring(j*16,(j+1)*16);

                            int ll =(ss<(j+1)*16 ? ss:(j+1)*16);

                            if(j<(int)tmp2.length/16+1 ) {
                                tmp = Arrays.copyOf(A[i].substring(j * 16, ll).getBytes(), 16);
                                Log.e("TTTTTTTTT ", "  " + A[i].substring(j * 16, ll) + " String ");
                            }
                            reader.writeBlock(i + 1, j, tmp

                                    ,
                                    new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,}, false);
                            Log.e("writeBlock", "" + (i + 1));
                        }


                    }
                       String[] str=reader.ReadCard();//reader.readSector(1, new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,}, false);
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
                            showChangeLangDialog(typeCheck, res);

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
                //final String [] finalTmpt = reader.ReadCard();
                reader.close();

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
            contact=new Contact(0, str[1], str[2], str[3], str[4], str[5], str[6]
                                       , "0", str[7], str[8], str[11], str[9], 1, "", str[10],"");

/*        this._name = _name;1
        this._prenom = _prenom;2
        this._phone_number = _phone_number;3
        this._email = _email;4
        this._societe = _societe;5
        this._profile = _profile;6
        this._UID = _UID;0
        this._adresse = _adresse;7
        this._fixe = _fixe;8
        this._fax = _fax;11
        this._skype = _skype;9
        this._score = _score;1
        this._Note = _Note;
        this._site =_Site;
*/
        Log.e("Search Query: ", "  contact "+contact.toString());
        _name.setText(contact.getName());
        _prenom.setText(contact.get_prenom());
        _phone_number.setText(contact.getPhoneNumber());
        _email.setText(contact.get_email());
        _fixe.setText(contact.get_fixe());
        _societe.setText(contact.get_societe());
        _adresse.setText(contact.get_adresse());
        _profile.setText(contact.get_profile());
        _skype.setText(contact.get_skype());
        _site.setText(contact.get_site());
      /*  AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        final int pos=position;

        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);
        final RatingBar RatingBar = (RatingBar) dialogView.findViewById(R.id.ratingBar);
        final EditText Note = (EditText) dialogView.findViewById(R.id.editText);
        edt.setText(str[1] + " " + str[2]);
        dialogsBuilder.setMessage(str[1] + " " + str[2] + "\n" + str[5]);
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

       // EditerContact("", position, str, 0, "");
    }
    private void checkNfc() {


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
