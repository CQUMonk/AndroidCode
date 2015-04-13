package cqu.cqumonk.androidcode.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CQUMonk on 2015/4/10.
 */
public class Book implements Parcelable {
    private String name;
    private String author;
    private int year;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public static final Parcelable.Creator<Book> CREATOR=new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            Book mBook=new Book();
            mBook.name=source.readString();
            mBook.author=source.readString();
            mBook.year=source.readInt();

            return mBook;
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(author);
        dest.writeInt(year);

    }
}
