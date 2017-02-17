
package uk.co.ribot.androidboilerplate.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Image implements Parcelable {

    @SerializedName("background")
    private String mBackground;
    @SerializedName("height")
    private String mHeight;
    @SerializedName("id")
    private String mId;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("width")
    private String mWidth;

    public String getBackground() {
        return mBackground;
    }

    public void setBackground(String background) {
        mBackground = background;
    }

    public String getHeight() {
        return mHeight;
    }

    public void setHeight(String height) {
        mHeight = height;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getWidth() {
        return mWidth;
    }

    public void setWidth(String width) {
        mWidth = width;
    }

    @Override
    public String toString() {
        return "Image{" +
                "mBackground='" + mBackground + '\'' +
                ", mHeight='" + mHeight + '\'' +
                ", mId='" + mId + '\'' +
                ", mUrl='" + mUrl + '\'' +
                ", mWidth='" + mWidth + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mBackground);
        dest.writeString(this.mHeight);
        dest.writeString(this.mId);
        dest.writeString(this.mUrl);
        dest.writeString(this.mWidth);
    }

    public Image() {
    }

    protected Image(Parcel in) {
        this.mBackground = in.readString();
        this.mHeight = in.readString();
        this.mId = in.readString();
        this.mUrl = in.readString();
        this.mWidth = in.readString();
    }

    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
