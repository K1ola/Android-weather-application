package com.example.myapplication.model;

public class ModelMainFragment {

    private DataSource mDataSource;
    private String temp;

    public void setTemp(){
        temp = mDataSource.getTest();
    }
}
