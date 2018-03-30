package com.teamnamenotfoundexception.hoteller.Database;

import android.util.Log;


public class DishItem{

    public int mDishId;
    public String mDishName;
    public String mDishType;
    public int mPrice;
    public int mQuantity;
    public String mDescription;
    public String mImagePath;
    public int mTotalPrice;
    public int isFav;
    public int isCart;
    public String mTags;
    public String mRest;

    public DishItem() {
        this.mQuantity = 1;
    }

    public DishItem(DishItem dishItem) {

        this.mDishId = dishItem.getDishId();
        this.mDishName = dishItem.getDishName();
        this.mDishType = dishItem.getDishType();
        this.mPrice = dishItem.getPrice();
        this.mDescription = dishItem.getDescription();
        this.mImagePath = dishItem.getImagePath() ;
        this.isFav = dishItem.getIsFav();
        this.isCart = dishItem.getIsCart();
        this.mQuantity = dishItem.getQuantity();
        this.mTotalPrice = dishItem.getTotalPrice();
        this.mTags = "";
        this.mRest = "";
    }

    public DishItem(int dishId, String dishName, String dishType, int price, String description, String imagePath, String tags, String rest) {
        this.mDishId = dishId;
        this.mDishName = dishName;
        this.mDishType = dishType;
        this.mPrice = price;
        this.mDescription = description;
        this.mImagePath = imagePath ;
        this.isFav = 0;
        this.isCart = 0;
        this.mTags = tags;
        this.mRest = rest;
    }

    public void setTags(String tags) {
        this.mTags = tags;
    }


    public String  getTags() {
        return mTags;
    }

    public int getIsCart(){return isCart;}

    public void setIsCart(int a) {isCart = a;};

    public int getIsFav(){
        return isFav;
    }
    public int getDishId() {
        return mDishId;
    }

    public void setDishId(int dishId) {
        mDishId = dishId;
    }

    public void setQuantity (int quantity) {

        this.mQuantity = quantity ;
        this.mTotalPrice = quantity * mPrice;
        Log.i("price", "quantity and total price set" + mQuantity + " "  + mTotalPrice);

    }
    public int isDishFav() { return isFav;};

    public void setDishFav(int a){ isFav = a;}
    public int getQuantity() {
        return mQuantity ;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String imagePath) {
        this.mImagePath = imagePath;
    }


    public int getTotalPrice() {
        return mTotalPrice ;
    }


    public String getDishName() {
        return mDishName;
    }

    public String getDishType() {
        return mDishType;
    }

    public int getPrice() {
        return mPrice;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDishName(String mDishName) {
        this.mDishName = mDishName;
    }

    public void setDishType(String mDishType) {
        this.mDishType = mDishType;
    }

    public void setPrice(int price) {
        this.mPrice = price;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}
