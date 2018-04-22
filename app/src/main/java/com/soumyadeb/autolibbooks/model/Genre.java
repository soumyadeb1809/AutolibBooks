package com.soumyadeb.autolibbooks.model;

/**
 * Created by Soumya Deb on 14-04-2018.
 */

public class Genre {
    private String id, name, filter, image;

    public Genre(String id, String name, String filter, String image) {
        this.id = id;
        this.name = name;
        this.filter = filter;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFilter() {
        return filter;
    }

    public String getImage() {
        return image;
    }
}
