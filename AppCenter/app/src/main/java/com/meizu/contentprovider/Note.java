package com.meizu.contentprovider;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by root on 14-11-19.
 */
public class Note implements Parcelable {

    public int _id;
    public String title;
    public String content;
    public String time;

    public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>() {
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
    public Note() {
    }
    private Note(Parcel in) {
        _id = in.readInt();
        title = in.readString();
        content = in.readString();
        time = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(time);
    }
}
