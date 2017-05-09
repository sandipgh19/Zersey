package com.example.sandipghosh.zersey;

import android.graphics.Bitmap;

/**
 * Created by sandipghosh on 09/05/17.
 */

public class Card {

    private String title;
    private Bitmap image;
    private String category;
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
