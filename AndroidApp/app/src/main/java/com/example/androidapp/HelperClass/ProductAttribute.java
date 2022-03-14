package com.example.androidapp.HelperClass;

import java.util.List;

public class ProductAttribute {
    String attributeTitle;
    List<ProductAttributeItem> productAttributeItemList;

    public ProductAttribute(String attributeTitle, List<ProductAttributeItem> productAttributeItemList) {
        this.attributeTitle = attributeTitle;
        this.productAttributeItemList = productAttributeItemList;
    }

    public void setAttributeTitle(String attributeTitle) {
        this.attributeTitle = attributeTitle;
    }

    public String getAttributeTitle() {
        return attributeTitle;
    }

    public List<ProductAttributeItem> getProductAttributeItemList() {
        return productAttributeItemList;
    }

    public void setProductAttributeItemList(List<ProductAttributeItem> productAttributeItemList) {
        this.productAttributeItemList = productAttributeItemList;
    }
}
