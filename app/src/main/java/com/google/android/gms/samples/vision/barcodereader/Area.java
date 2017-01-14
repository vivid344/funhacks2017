package com.google.android.gms.samples.vision.barcodereader;

import java.util.ArrayList;

/**
 * Created by b1014100_2 on 2017/01/14.
 */

public class Area {

    int id;
    int freq;
    ArrayList<Integer> Bookid = new ArrayList<>();

    /*------------constracter-----------*/
    public Area(int id, int freq , ArrayList<Integer> bookid){
        this.id = id;
        this.freq = freq;
        this.Bookid.addAll(bookid);
    }

    /*--------------getter-------------*/
    public int getId() {
        return id;
    }

    public ArrayList<Integer> getBookid() {
        return Bookid;
    }

    public int getFreq() {
        return freq;
    }

    /*--------------setter------------------*/
    public void setId(int id) {
        this.id = id;
    }
    public void setBookid(ArrayList<Integer> bookid) {
        Bookid = bookid;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }
}
