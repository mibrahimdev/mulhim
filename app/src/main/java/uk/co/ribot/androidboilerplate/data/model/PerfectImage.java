package uk.co.ribot.androidboilerplate.data.model;

import android.os.Parcel;
import android.os.Parcelable;

//object that has the properties of (Image and ImageDetails)
public class PerfectImage implements Parcelable {

    private Image mImage;
    private ImageDetails mImageDetails;
    private int backgroundColor = 0xFF333333; //default color

    public PerfectImage(Image image, ImageDetails imageDetails) {
        mImage = image;
        mImageDetails = imageDetails;
    }

    public Image getImage() {
        return mImage;
    }

    public void setImage(Image image) {
        mImage = image;
    }

    public ImageDetails getImageDetails() {
        return mImageDetails;
    }

    public void setImageDetails(ImageDetails imageDetails) {
        mImageDetails = imageDetails;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mImage, flags);
        dest.writeParcelable(this.mImageDetails, flags);
        dest.writeInt(this.backgroundColor);
    }

    protected PerfectImage(Parcel in) {
        this.mImage = in.readParcelable(Image.class.getClassLoader());
        this.mImageDetails = in.readParcelable(ImageDetails.class.getClassLoader());
        this.backgroundColor = in.readInt();
    }

    public static final Creator<PerfectImage> CREATOR = new Creator<PerfectImage>() {
        @Override
        public PerfectImage createFromParcel(Parcel source) {
            return new PerfectImage(source);
        }

        @Override
        public PerfectImage[] newArray(int size) {
            return new PerfectImage[size];
        }
    };

    @Override
    public String toString() {
        return "PerfectImage{" +
                "mImage=" + mImage +
                ", mImageDetails=" + mImageDetails +
                ", backgroundColor=" + backgroundColor +
                '}';
    }
}
