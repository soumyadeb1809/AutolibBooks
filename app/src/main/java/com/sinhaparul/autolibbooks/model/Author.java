package com.sinhaparul.autolibbooks.model;

/**
 * Created by Parul Sinha on 14-04-2018.
 */

public class Author {
    String id, name, image;

    public Author(String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
