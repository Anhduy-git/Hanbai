package com.example.androidapp.HelperClass;

import java.util.List;

public class PriceQuantity {
    String attribute1;
    List<PriceQuantityItem> attribute2;
    public PriceQuantity(String attribute1, List<PriceQuantityItem> attribute2) {
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
    }
    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    public List<PriceQuantityItem> getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(List<PriceQuantityItem> attribute2) {
        this.attribute2 = attribute2;
    }


}
