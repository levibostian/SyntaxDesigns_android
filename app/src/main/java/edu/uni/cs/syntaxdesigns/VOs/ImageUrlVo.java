package edu.uni.cs.syntaxdesigns.VOs;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import android.os.Parcel;
import android.os.Parcelable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageUrlVo implements Parcelable {
    public String hostedSmallUrl;
    public String hostedMediumUrl;
    public String hostedLargeUrl;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.hostedSmallUrl);
        dest.writeString(this.hostedMediumUrl);
        dest.writeString(this.hostedLargeUrl);
    }

    public ImageUrlVo() {
    }

    private ImageUrlVo(Parcel in) {
        this.hostedSmallUrl = in.readString();
        this.hostedMediumUrl = in.readString();
        this.hostedLargeUrl = in.readString();
    }

    public static final Parcelable.Creator<ImageUrlVo> CREATOR = new Parcelable.Creator<ImageUrlVo>() {
        public ImageUrlVo createFromParcel(Parcel source) {
            return new ImageUrlVo(source);
        }

        public ImageUrlVo[] newArray(int size) {
            return new ImageUrlVo[size];
        }
    };
}
