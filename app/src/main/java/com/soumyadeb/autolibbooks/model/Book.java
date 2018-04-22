package com.soumyadeb.autolibbooks.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Soumya Deb on 08-04-2018.
 */

public class Book implements Parcelable{
    private String id, bookName, author, genre, thumbnail;

    public Book(String id, String bookName, String author, String genre, String thumbnail) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.genre = genre;
        this.thumbnail = thumbnail;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getId() {
        return id;
    }


    // In constructor you will read the variables from Parcel. Make sure to read them in the same sequence in which you have written them in Parcel.
    public Book(Parcel in) {
        id = in.readString();
        bookName = in.readString();
        author = in.readString();
        genre = in.readString();
        thumbnail = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // This is where you will write your member variables in Parcel. Here you can write in any order. It is not necessary to write all members in Parcel.
    @Override
    public void writeToParcel(Parcel dest, int flags) {
// Write data in any order
        dest.writeString(id);
        dest.writeString(bookName);
        dest.writeString(author);
        dest.writeString(genre);
        dest.writeString(thumbnail);
    }
    // This is to de-serialize the object
    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>(){
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

}
