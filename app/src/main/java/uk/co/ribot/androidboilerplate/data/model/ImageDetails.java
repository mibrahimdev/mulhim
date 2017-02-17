
package uk.co.ribot.androidboilerplate.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
public class ImageDetails implements Parcelable {

    @SerializedName("author")
    private String mAuthor;
    @SerializedName("colors")
    private String mColors;
    @SerializedName("link")
    private String mLink;
    @SerializedName("title")
    private String mTitle;

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getColors() {
        return mColors;
    }

    public void setColors(String colors) {
        mColors = colors;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mAuthor);
        dest.writeString(this.mColors);
        dest.writeString(this.mLink);
        dest.writeString(this.mTitle);
    }

    public ImageDetails() {
    }

    protected ImageDetails(Parcel in) {
        this.mAuthor = in.readString();
        this.mColors = in.readString();
        this.mLink = in.readString();
        this.mTitle = in.readString();
    }

    public static final Parcelable.Creator<ImageDetails> CREATOR = new Parcelable.Creator<ImageDetails>() {
        @Override
        public ImageDetails createFromParcel(Parcel source) {
            return new ImageDetails(source);
        }

        @Override
        public ImageDetails[] newArray(int size) {
            return new ImageDetails[size];
        }
    };
}
