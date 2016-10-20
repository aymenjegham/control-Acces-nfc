package com.support.android.designlibdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Print Secure on 5/7/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_PRENOM = "prenom";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_SOCIETE = "societe";
    private static final String KEY_PROFILE = "profile";
    private static final String KEY_UID = "UID";
    private static final String KEY_ADRESS = "adresse";
    private static final String KEY_FIXE = "fixe";
    private static final String KEY_FAX = "fax";
    private static final String KEY_SKYPE = "skype";
    private static final String KEY_SCORE = "score";
    private static final String KEY_NOTE = "note";
    private static final String KEY_SITE = "site";
    private static final String KEY_DATE = "date_ajout";
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT ,"
                + KEY_PRENOM + " TEXT ,"
                + KEY_EMAIL + " TEXT ,"
                + KEY_SOCIETE + " TEXT ,"
                + KEY_PROFILE + " TEXT ,"
                + KEY_UID + " TEXT ,"
                + KEY_ADRESS + " TEXT,"
                + KEY_FIXE + " TEXT,"
                + KEY_FAX + " TEXT,"
                + KEY_SKYPE + " TEXT,"
                + KEY_SCORE + " INTEGER,"
                + KEY_NOTE + " TEXT,"
                + KEY_SITE + " TEXT,"
                + KEY_DATE + " DATETIME"
                +    ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName()); // Contact Name
        values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Phone
        values.put(KEY_PRENOM, contact.get_prenom()); // Contact Name
        values.put(KEY_EMAIL, contact.get_email()); // Contact Phone
        values.put(KEY_SOCIETE, contact.get_societe()); // Contact Name
        values.put(KEY_PROFILE, contact.get_profile()); // Contact Phone
        values.put(KEY_UID, contact.get_UID()); // Contact Name
        values.put(KEY_ADRESS, contact.get_adresse()); // Contact Phone
        values.put(KEY_FIXE, contact.get_fixe()); // Contact Name
        values.put(KEY_FAX, contact.get_fax()); // Contact Phone
        values.put(KEY_SKYPE, contact.get_skype()); // Contact Name
        values.put(KEY_SCORE, contact.getScore()); // Contact Name
        values.put(KEY_NOTE, contact.getNote()); // Contact Name
        values.put(KEY_SITE, contact.get_site()); // Contact Name
        values.put(KEY_DATE, contact.get_dateAjout()); // Contact Name
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_ID,
                        KEY_NAME, KEY_PH_NO, KEY_PRENOM ,KEY_EMAIL,KEY_SOCIETE,KEY_PROFILE ,
                        KEY_UID , KEY_ADRESS,KEY_FIXE,KEY_FAX,KEY_SKYPE,KEY_SCORE,KEY_NOTE,KEY_SITE,KEY_DATE }, KEY_ID + "=?",
                new String[]{String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)
                , cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10)
                , cursor.getString(11),Integer.parseInt(cursor.getString(12)), cursor.getString(13), cursor.getString(14), cursor.getString(15));
        // return contact
        return contact;
    }

    // Getting All Contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                contact.set_prenom(cursor.getString(3));
                contact.set_email(cursor.getString(4));
                contact.set_societe(cursor.getString(5));
                contact.set_profile(cursor.getString(6));
                contact.set_UID(cursor.getString(7));
                contact.set_adresse(cursor.getString(8));
                contact.set_fixe(cursor.getString(9));
                contact.set_fax(cursor.getString(10));
                contact.set_skype(cursor.getString(11));
                contact.setScore(Integer.parseInt(cursor.getString(12)));
                contact.setNote(cursor.getString(13));
                contact.set_site(cursor.getString(14));
                contact.set_dateAjout(cursor.getString(15));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Updating single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getPhoneNumber());
        values.put(KEY_PRENOM, contact.get_prenom()); // Contact Name
        values.put(KEY_EMAIL, contact.get_email()); // Contact Phone
        values.put(KEY_SOCIETE, contact.get_societe()); // Contact Name
        values.put(KEY_PROFILE, contact.get_profile()); // Contact Phone
        values.put(KEY_UID, contact.get_UID()); // Contact Name
        values.put(KEY_ADRESS, contact.get_adresse()); // Contact Phone
        values.put(KEY_FIXE, contact.get_fixe()); // Contact Name
        values.put(KEY_FAX, contact.get_fax()); // Contact Phone
        values.put(KEY_SKYPE, contact.get_skype()); // Contact Name
        values.put(KEY_SCORE, contact.getScore()); // Contact Phone
        values.put(KEY_NOTE, contact.getNote()); // Contact Name
        values.put(KEY_SITE, contact.get_site()); // Contact Name
        values.put(KEY_DATE, contact.get_dateAjout()); // Contact Name
        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }

    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }

    // Getting All Contacts
    public List<Contact> getAllContactsWhere(String str) {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS + " where "
                + KEY_NAME + " LIKE '%"+str+"%' or "
                + KEY_PH_NO + " LIKE '%"+str+"%' or "
                + KEY_PRENOM + " LIKE '%"+str+"%' or "
                + KEY_EMAIL + " LIKE '%"+str+"%' or "
                + KEY_SOCIETE + " LIKE '%"+str+"%' or "
                + KEY_PROFILE + " LIKE '%"+str+"%' or "

                + KEY_ADRESS + " LIKE '%"+str+"%' or "
                + KEY_FIXE + " LIKE '%"+str+"%' or "
                + KEY_FAX + " LIKE '%"+str+"%' or "
                + KEY_SKYPE + " LIKE '%"+str+"%' or "

                + KEY_NOTE + " LIKE '%"+str+"%' or "
                + KEY_SITE + " LIKE '%"+str+"%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                contact.set_prenom(cursor.getString(3));
                contact.set_email(cursor.getString(4));
                contact.set_societe(cursor.getString(5));
                contact.set_profile(cursor.getString(6));
                contact.set_UID(cursor.getString(7));
                contact.set_adresse(cursor.getString(8));
                contact.set_fixe(cursor.getString(9));
                contact.set_fax(cursor.getString(10));
                contact.set_skype(cursor.getString(11));
                contact.setScore(Integer.parseInt(cursor.getString(12)));
                contact.setNote(cursor.getString(13));
                contact.set_site(cursor.getString(14));
                contact.set_dateAjout(cursor.getString(15));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
