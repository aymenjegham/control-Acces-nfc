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
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.WriterException;
import com.support.android.designlibdemo.adapter.MyAdapter;
import com.support.android.designlibdemo.adapter.NavDrawerListAdapter;
import com.support.android.designlibdemo.model.Model;
import com.support.android.designlibdemo.model.NavDrawerItem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.zxing.Result;
import com.support.android.designlibdemo.model.SettingActivity;

import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * TODO
 */
public class MainActivity extends BasicActivity   implements  ZXingScannerView.ResultHandler  {
    private ZXingScannerView mScannerView;








    public static final int CONTACT_QUERY_LOADER = 0;
    public static final String QUERY_KEY = "query";


    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;
    MCReader reader = null;
    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;

    ArrayList<Model> models = new ArrayList<Model>();
    int next;
    MyAdapter Listadapter ;
    boolean isLoading=false;



    List<Contact> contacts;

    private Intent mOldIntent = null;
    private AlertDialog mEnableNfc;
    private boolean mResume = true;
    private static final String JSON_URL = "http://192.168.1.208/www/nfc/parsemysql.php";
    Button setting;





    private final Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);



        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // Typeface type = Typeface.createFromAsset(getAssets(),"Charbroil.ttf");
        Typeface type = Typeface.createFromAsset(getAssets(),"varsity_regular.ttf");
        TextView logo = (TextView)findViewById(R.id.logo);

        logo.setTypeface(type);
       /* mTitle = mDrawerTitle = getTitle();

        // load slide menu items

        navMenuTitles= new String[0];
      //  navMenuTitles[0]="Tout";
      //  navMenuTitles[1]="Group 1";

		/*
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);
        */
   /*     mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // Home
        for(int i = 0; i < navMenuTitles.length; i++)
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[i], 0, true, "22"));




        // Recycle the typed array
        //navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

        // enabling action bar app icon and behaving it as toggle button
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
  /*              mDrawerLayout,         /* DrawerLayout object */
   /*             R.drawable.ic_dashboard,  /* nav drawer image to replace 'Up' caret */
   /*             R.string.drawer_open,  /* "open drawer" description for accessibility */
   /*             R.string.drawer_close  /* "close drawer" description for accessibility */
   /*     ) {
            public void onDrawerClosed(View view) {
                //getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                //getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
*/

/*
        // 1. pass context and data to the custom adapter
        Listadapter = new MyAdapter(this, generateData());

        // if extending Activity 2. Get ListView from activity_main.xml
        ListView listView = (ListView) findViewById(R.id.listviews);

        // 3. setListAdapter
        listView.setAdapter(Listadapter);// if extending Activity

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
               /* Intent intent = new Intent(MainActivity.this, SendMessage.class);
                String message = "abc";
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);*/
/*                String message = "abc";
                //showChangeLangDialog(position);
                executeDone(message, position, null);
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                // Ignore this method

            }


            @Override

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                int lastIndexInScreen = visibleItemCount + firstVisibleItem;


                if (lastIndexInScreen >= totalItemCount && !isLoading) {

                    // It is time to load more items

                    isLoading = true;

                    // loadMore();

                }

            }

        });
*/


       /* LinearLayout menu_photos = (LinearLayout )findViewById(R.id.accesControl);
        menu_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(this, AccessContactActivity.class);
                intent.putExtra(EditContactActivity.EXTRA_NAME, "Ajouter");
                startActivity(intent);
            }
        });*/

        Log.e("Search Query: ", "Search Query: ");

        handleIntent(getIntent());
        Common.setNfcAdapter(NfcAdapter.getDefaultAdapter(this));


    }







    public void loadMore() {

        int i;

        if (models.size() <= 90) { // Limit the number of items to 100 (stop loading when reaching 100 items)

            for (i = next; i <= next + 9; i++)
                models.add(new Model("Menu Item "+i,"Menu Item 1","12"));

            // Notify the ListView of data changed

            Listadapter.notifyDataSetChanged();

            isLoading = false;

            // Update next

            next = i;

        }
    }




    private ArrayList<Model> generateData(){
        DatabaseHandler db = new DatabaseHandler(this);

        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");


        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        contacts = db.getAllContacts();
        //models.add(new Model("Group Title"));
        for (Contact cn : contacts) {
            String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
            Log.d("Name: ", log);
            models.add(new Model( cn.getName(), cn.getPhoneNumber(), "" + cn.getScore()));
            next++;
        }




        return models;
    }


    public void showChangeLangDialog(int position,final String  strt) {
        if (strt==null) {
            Toast.makeText(this, R.string.info_no_tag_found,
                   Toast.LENGTH_LONG).show();

            return;
        }

        String str[]= strt.split("\\|");
       // Toast.makeText(this, str+ " "+str.length,
       //         Toast.LENGTH_LONG).show();
        Log.d("str  : strstr   "+str.length , str[0]);
        Intent intent = new Intent(MainActivity.this, DetailContactActivity.class);
        intent.putExtra(DetailContactActivity.EXTRA_NAME, "Print");
        intent.putExtra("QRcode", str[5]);
        intent.putExtra("URL", str[13]);

        intent.putExtra("ContactTag",
                new Contact(0, str[1], str[2], str[9], str[3], str[6], str[8]
                        , "0", str[7], str[10], str[10], str[11], 0, "", str[10],""));

        startActivity(intent);
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

    private void executeDone(String message,int position,String [] str) {
        Intent intent = new Intent(MainActivity.this, DetailContactActivity.class);
        intent.putExtra(DetailContactActivity.EXTRA_NAME, message);

        intent.putExtra("ContactTag", contacts.get(position));
        startActivity(intent);
       // Toast.makeText(MainActivity.this, message+" This is position " + position, Toast.LENGTH_LONG).show();

    }
    private void AjouterContact(String message,int position,String [] str,int score,String note) {
        Intent intent = new Intent(MainActivity.this, DetailContactActivity.class);
        intent.putExtra(DetailContactActivity.EXTRA_NAME, message);
        intent.putExtra("ContactTag",
                new Contact(0, str[1], str[2], str[3], str[4], str[6], str[11]
                        , "0", str[5], str[7], str[9], str[8], score, note, str[10],""));

        startActivity(intent);
      //  Toast.makeText(MainActivity.this, "Contact Ajouter", Toast.LENGTH_LONG).show();

    }

    private void EditerContact() {
        Intent intent = new Intent(MainActivity.this, EditContactActivity.class);
        intent.putExtra(EditContactActivity.EXTRA_NAME, "Editer");

      //  intent.putExtra("ContactTag",
      //          new Contact(0, str[1], str[2], str[3], str[4], str[6], str[11]
     //                   , "0", str[5], str[7], str[9], str[8], score, note, str[10]));
        startActivity(intent);
       // Toast.makeText(MainActivity.this, message+" This is position " + position, Toast.LENGTH_LONG).show();

    }

    @Override
    public void handleResult(Result rawResult) {

        Log.e("handler", rawResult.getText()); // Prints scan results
        Log.e("handler", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode)

        // show the scanner result into dialog box.
        // AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Scan Result");
         //builder.setMessage(rawResult.getText());
         //AlertDialog alert1 = builder.create();
         //alert1.show();
        //getJSON(JSON_URL);
       Intent i=new Intent(MainActivity.this, ProfileActivity.class);
        i.putExtra("msg", rawResult.getText());
       Log.e("raw","results"+ rawResult.getText());
        startActivity(i);



        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);

    }



    /**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }
    private void displayView(int position) {
        // update the main content by replacing fragments


        // update selected item and title, then close the drawer
     //   mDrawerList.setItemChecked(position, true);
     //   mDrawerList.setSelection(position);
     //   setTitle(navMenuTitles[position]);
      //  mDrawerLayout.closeDrawer(mDrawerList);
	/*	} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}*/
    }

    @Override
    public void onNewIntent(Intent intent) {


        handleIntent(intent);

    }


    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data and show it in
            //SearchResultsActivity
            Log.e("Search Query: ","Search Query: "+query );
        }
        else
        {
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

                    //readTag(typeCheck);
                    EditerContact();
                }
            }
        }
    }

    private void readTag(final int typeCheck) {

        if (reader == null) {
            finish();
            return;
        }

        new Thread(new Runnable() {
            final ProgressDialog loadingdialog = ProgressDialog.show(MainActivity.this,
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
                                //showChangeLangDialog(typeCheck, res);

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.


        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sample_actions, menu);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            final SearchView searchView = (SearchView) menu.findItem(R.id.search_menu).getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String s) {
                    search(s);
                    return true;
                }


                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return true;
                }


                private void search(String s) {
                    Log.e("Search Query: ","Search Query: "+s );
                }
            });
        } else {
            menu.findItem(R.id.search).setVisible(false);
        }


        return super.onCreateOptionsMenu(menu);





    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
           /* case R.id.menu_search:
                getMenuInflater().inflate(R.menu.search_action, menu);
                return true;*/
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new CheeseListFragment(), "Category 1");

        viewPager.setAdapter(adapter);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
      //  mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
      //  mDrawerToggle.onConfigurationChanged(newConfig);
    }


    public void onResume() {
        super.onResume();
        Log.e("resumed","resumed");
          checkNfc();



         if (mResume) {


         }
    }

    /**
     * Check if NFC adapter is enabled. If not, show the user a dialog and let
     * him choose between "Goto NFC Setting", "Use Editor Only" and "Exit App".
     * Also enable NFC foreground dispatch system.
     * @see Common#enableNfcForegroundDispatch(Activity)
     */
    private void checkNfc() {
        // Check if the NFC hardware is enabled.
        if (Common.getNfcAdapter() != null
                && !Common.getNfcAdapter().isEnabled()) {
            // NFC is disabled.
            // Use as editor only?


        } else {
            // NFC is enabled. Hide dialog and enable NFC
            // foreground dispatch.
            if (mOldIntent != getIntent()) {
                int typeCheck = Common.treatAsNewTag(getIntent(), this);
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
    public void AjouterContact(View view) {
        Intent intent = new Intent(this, EditContactActivity.class);
        intent.putExtra(EditContactActivity.EXTRA_NAME, "editer");
        //static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
        startActivity(intent);
    }

    public void AccesContact(View view) {
        Intent intent = new Intent(this, AccessContactActivity.class);
        intent.putExtra(AccessContactActivity.EXTRA_NAME, "Ajouter");
        startActivity(intent);
    }


    public void setup(View view) {
        Intent intent = new Intent(MainActivity.this,SettingActivity.class);
        startActivity(intent);
    }



    public void QRCodeReder(View view) {
        /*final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
        try {
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            showDialog(MainActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        } */
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);

        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        Log.e("testing","camera activity");

        mScannerView.startCamera();         // Start camera



    }



    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
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



    public void onPause() {
        Common.disableNfcForegroundDispatch(this);
        super.onPause();
         try {
             //this.finish();
mScannerView.stopCamera();

        }catch (NullPointerException e){
            e.printStackTrace();

        }



    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                sendURL(contents);
               // Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
              //  toast.show();
            }
        }
    }

    private void sendURL(final String typeCheck) {




        new Thread(new Runnable() {
            final ProgressDialog loadingdialog = ProgressDialog.show(MainActivity.this,
                    "","Scanning Please Wait",true);
            @Override
            public void run() {
              try {

                  String dataUrl = "http://192.168.1.179:12065/nfc/getuser.php?id="+typeCheck;
                  String dataUrlParameters = "id="+"s2d45sd";

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


    private void getJSON(String url) {
        class GetJSON extends AsyncTask<String, Void, String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Please Wait...",null,true,true);
            }

            @Override
            protected String doInBackground(String... params) {

                //String uri = params[0];
                Uri uri = new Uri.Builder()
                        .scheme("http")
                        .authority("192.168.1.208")
                        .path("/www/nfc/parsemysql.php")
                        .appendQueryParameter("param1","1")
                        .appendQueryParameter("param2", "home")
                        .build();


                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri.toString());
                    Log.e("stringed","uri"+url);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
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
               // textViewJSON.setText(s);
                Log.e("jso,","mesg"+s);
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute(url);
    }
}
