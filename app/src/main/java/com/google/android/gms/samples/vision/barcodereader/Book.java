package com.google.android.gms.samples.vision.barcodereader;

/**
 * Created by b1014100_2 on 2017/01/14.
 */

public class Book {


    String name;
    float locat;
    int areaId;
    /*-----constract---------------*/
    public Book(String name, float locat,int areaId){

        this.name = name;
        this.locat  = locat;
        this.areaId = areaId;
    }

   /*-----------getter-------------*/
    public String getName() {
        return name;
    }
    public float getLocat() {
        return locat;
    }


    public int getAreaId() {
        return areaId;
    }

    /*-------------setter---------------*/
    public void setLocat(float locat) {
        this.locat = locat;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }
}
