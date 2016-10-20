package com.support.android.designlibdemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Print Secure on 5/7/2016.
 */
public class Contact implements Parcelable {
    //private variables
    int _id;
    String _name;
    String _prenom;
    String _phone_number;
    String _email;
    String _societe;
    String _profile;
    String _UID;
    String _adresse;
    String _fixe;
    String _fax;
    String _skype;
    int _score;
    String _Note;
    String _site;
    String _dateAjout;
    // Empty constructor
    public Contact() {

    }

    public Contact(int id,String _name, String _prenom, String _phone_number, String _email, String _societe, String _profile,
                   String _UID, String _adresse, String _fixe, String _fax,String _skype, int _score,  String _Note,String _Site,String _dateAjout) {
        this._id = id;
        this._name = _name;
        this._prenom = _prenom;
        this._phone_number = _phone_number;
        this._email = _email;
        this._societe = _societe;
        this._profile = _profile;
        this._UID = _UID;
        this._adresse = _adresse;
        this._fixe = _fixe;
        this._fax = _fax;
        this._skype = _skype;
        this._score = _score;
        this._Note = _Note;
        this._site =_Site;
        this._dateAjout = _dateAjout;
    }

    // constructor
    public Contact(int id, String name, String _phone_number) {
        this._id = id;
        this._name = name;
        this._phone_number = _phone_number;
        this._prenom = "";
        this._email = "";
        this._societe = "";
        this._profile = "";
        this._UID ="";
        this._adresse = "";
        this._fixe = "";
        this._fax = "";
        this._skype = "";
        this._score = 0;
        this._Note = "";
        this._site = "";
        this._dateAjout="";
    }

    public String  toString(){
        return "name = "+_name+
                "_prenom = "+_prenom+
                "_phone_number = "+_phone_number+
                "_email = "+_email+
                "_societe = "+_societe+
                "_profile = "+_profile+
                "_UID = "+_UID+
                "_adresse = "+_adresse+
                "_fixe = "+_fixe+
                "_fax = "+_fax+
                "_skype = "+_skype+
                "_score = "+_score+
                "_site = "+_site+
                "_Note = "+_Note+
                "_dateAjout"+_dateAjout;

    }



    // constructor
    public Contact(String name, String _phone_number) {
        this._name = name;
        this._phone_number = _phone_number;
        this._prenom = "";
        this._email = "";
        this._societe = "";
        this._profile = "";
        this._UID ="";
        this._adresse = "";
        this._fixe = "";
        this._fax = "";
        this._skype = "";
        this._score = 0;
        this._Note = "";
        this._site = "";
        this._dateAjout= "";
    }



    // getting ID
    public int getID() {
        return this._id;
    }

    // setting id
    public void setID(int id) {
        this._id = id;
    }

    // getting name
    public String getName() {
        return this._name;
    }

    // setting name
    public void setName(String name) {
        this._name = name;
    }

    // getting phone number
    public String getPhoneNumber() {
        return this._phone_number;
    }

    // setting phone number
    public void setPhoneNumber(String phone_number) {
        this._phone_number = phone_number;
    }
    public String get_prenom() {
        return _prenom;
    }

    public void set_prenom(String _prenom) {
        this._prenom = _prenom;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_societe() {
        return _societe;
    }

    public void set_societe(String _societe) {
        this._societe = _societe;
    }

    public String get_profile() {
        return _profile;
    }

    public void set_profile(String _profile) {
        this._profile = _profile;
    }

    public String get_UID() {
        return _UID;
    }

    public void set_UID(String _UID) {
        this._UID = _UID;
    }

    public String get_adresse() {
        return _adresse;
    }

    public void set_adresse(String _adresse) {
        this._adresse = _adresse;
    }

    public String get_fixe() {
        return _fixe;
    }

    public void set_fixe(String _fixe) {
        this._fixe = _fixe;
    }

    public String get_fax() {
        return _fax;
    }

    public void set_fax(String _fax) {
        this._fax = _fax;
    }
    public String get_skype() {
        return _skype;
    }

    public void set_skype(String _skype) {
        this._skype = _skype;
    }

    public String get_site() {
        return _site;
    }

    public void set_site(String _site) {
        this._site = _site;
    }
    // getting ID
    public int getScore() {
        return this._score;
    }

    // setting id
    public void setScore(int _score) {
        this._score = _score;
    }

    // getting name
    public String getNote() {
        return this._Note;
    }

    // setting name
    public void setNote(String _Note) {
        this._Note = _Note;
    }
    public String get_dateAjout() {
        return _dateAjout;
    }

    public void set_dateAjout(String _dateAjout) {
        this._dateAjout = _dateAjout;
    }
    protected Contact(Parcel in) {
        _id = in.readInt();
        _name = in.readString();
        _prenom = in.readString();
        _phone_number = in.readString();
        _email = in.readString();
        _societe = in.readString();
        _profile = in.readString();
        _UID = in.readString();
        _adresse = in.readString();
        _fixe = in.readString();
        _fax = in.readString();
        _skype = in.readString();
        _score = in.readInt();
        _Note = in.readString();
        _site = in.readString();
        _dateAjout= in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeString(_name);
        dest.writeString(_prenom);
        dest.writeString(_phone_number);
        dest.writeString(_email);
        dest.writeString(_societe);
        dest.writeString(_profile);
        dest.writeString(_UID);
        dest.writeString(_adresse);
        dest.writeString(_fixe);
        dest.writeString(_fax);
        dest.writeString(_skype);
        dest.writeInt(_score);
        dest.writeString(_Note);
        dest.writeString(_site);
        dest.writeString(_dateAjout);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}