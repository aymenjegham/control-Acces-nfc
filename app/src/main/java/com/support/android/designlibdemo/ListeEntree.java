package com.support.android.designlibdemo;

import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.support.android.designlibdemo.adapter.MyAdapter;
import com.support.android.designlibdemo.model.Model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Print Secure on 6/9/2016.
 */
public class ListeEntree extends BasicActivity {


    ArrayList<Model> models = new ArrayList<Model>();
    int next;
    MyAdapter Listadapter ;
    boolean isLoading=false;
    List<Contact> contacts;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listentree);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
                //String message = "abc";
                //showChangeLangDialog(position);
                //executeDone(message, position, null);
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





        Log.e("Search Query: ", "Search Query: ");

        //handleIntent(getIntent());
        Common.setNfcAdapter(NfcAdapter.getDefaultAdapter(this));
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
        if(contacts.size()==0){
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = new Date();
            db.addContact(new Contact(1,"Ali","Arfaoui","0021655225522","arfaoui.a@printsecure.com","Printsecure","Ingénieur Informatique",
                    "","mghira fouchana","0021671717171","0021671717171","ali.arfaoui",2,"Note","",dateFormat.format(date)));
            db.addContact(new Contact(2,"bechir","ben slama","0021655225522","bechir.a@printsecure.com","Printsecure","Ingénieur Informatique",
                    "","mghira fouchana","0021671717171","0021671717171","bechir.benslama",5,"Note","",dateFormat.format(date)));
            contacts = db.getAllContacts();
        }

        models.clear();
        //models.add(new Model("Group Title"));
        for (Contact cn : contacts) {
            String log = "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber();
            Log.d("Name: ", log);
            models.add(new Model( cn.getName(), cn.getPhoneNumber(), "" + cn.get_dateAjout()));
            next++;
        }




        return models;
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




        }
        return super.onOptionsItemSelected(item);
    }


}
