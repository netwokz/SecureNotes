package com.computeerror.securenotes;

import java.util.ArrayList;

public class MyListData {
    public String mDescription;
    public String mTitle;

    public MyListData(String title, String description) {
        this.mDescription = description;
        this.mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public String isDescription() {
        return mDescription;
    }

    public static ArrayList<MyListData> generateList(int num) {
        ArrayList<MyListData> contacts = new ArrayList<MyListData>();
        for (int i = 1; i <= num; i++) {
            contacts.add(new MyListData("Title " + i, "Description " + i));
        }

        return contacts;
    }

}
