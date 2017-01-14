package com.google.android.gms.samples.vision.barcodereader;

/**
 * Created by b1014100_2 on 2017/01/14.
 */

public class Book {

    int Bookid;
    String name;
    float locat;

    /*-----constract---------------*/
    public Book(int Bookid,String name, float locat){
        this.Bookid = Bookid;
        this.name = name;
        this.locat  = locat;
    }

   /*-----------getter-------------*/
    public String getName() {
        return name;
    }
    public float getLocat() {
        return locat;
    }

    public int getBookid() {
        return Bookid;
    }

/*-------------setter---------------*/
    public void setBookid(int bookid) {
        Bookid = bookid;
    }
    public void setLocat(float locat) {
        this.locat = locat;
    }

    public void setName(String name) {
        this.name = name;
    }
}
