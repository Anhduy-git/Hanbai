package com.example.androidapp.HelperClass;

import android.os.Parcel;
import android.os.Parcelable;



public class ProductAttributeItem implements Parcelable {
    String attributeItemName;

    public ProductAttributeItem(String attributeItemName) {
        this.attributeItemName = attributeItemName;
    }
    protected ProductAttributeItem(Parcel in) {
        attributeItemName = in.readString();
    }
    public static final Creator<ProductAttributeItem> CREATOR = new Creator<ProductAttributeItem>() {
        @Override
        public ProductAttributeItem createFromParcel(Parcel in) {
            return new ProductAttributeItem(in);
        }

        @Override
        public ProductAttributeItem[] newArray(int size) {
            return new ProductAttributeItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(attributeItemName);
    }
    public String getAttributeItemName() {
        return attributeItemName;
    }

    public void setAttributeItemName(String attributeItemName) {
        this.attributeItemName = attributeItemName;
    }


}
