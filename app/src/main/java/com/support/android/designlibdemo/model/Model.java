package com.support.android.designlibdemo.model;

/**
 * Created by Print Secure on 5/5/2016.
 */
public class Model {


    private String title;
    private String nom;
    private String counter;

    private boolean isGroupHeader = false;

    public Model(String title) {
        this(title,title,null);
        isGroupHeader = true;
    }
    public Model( String title,String nom, String counter) {
        super();

        this.title = title;
        this.nom = nom;
        this.counter = counter;
    }


    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String title) {
        this.nom = nom;
    }
    public String getCounter() {
        return counter;
    }
    public void setCounter(String counter) {
        this.counter = counter;
    }
    public boolean isGroupHeader() {
        return isGroupHeader;
    }
    public void setGroupHeader(boolean isGroupHeader) {
        this.isGroupHeader = isGroupHeader;
    }



}
