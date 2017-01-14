package com.google.android.gms.samples.vision.barcodereader;

import java.util.ArrayList;

/**
 * Created by b1014100_2 on 2017/01/14.
 */

public class Area {

    int id;
    int freq;

    /*------------constracter-----------*/
    public Area(int id, int freq){
        this.id = id;
        this.freq = freq;
    }

    /*--------------getter-------------*/
    public int getId() {
        return id;
    }

    public int getFreq() {
        return freq;
    }

    /*--------------setter------------------*/
    public void setId(int id) {
        this.id = id;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public void addfreq(){
        freq++;
    }
}
