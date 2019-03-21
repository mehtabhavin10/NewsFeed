package com.example.admin.newsfeed.Model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class RSSObject {

    @Expose
    String status;

    @Expose
    Feed feed;

    @Expose
    ArrayList< Item > items = new ArrayList < Item > ();

    public RSSObject(String status, Feed feedObject, ArrayList<Item> items) {
        this.status = status;
        feed = feedObject;
        this.items = items;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFeed(Feed feedObject) {
        this.feed = feedObject;
    }
}
