package com.example.surendra.groceryapp;
import android.app.Application;


/**
 * Created by surendra on 11/22/2016.
 */

public class GlobalState extends Application {

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
