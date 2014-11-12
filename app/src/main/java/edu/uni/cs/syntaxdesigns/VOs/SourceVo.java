package edu.uni.cs.syntaxdesigns.VOs;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import android.os.Parcel;
import android.os.Parcelable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SourceVo implements Parcelable {
    public String sourceRecipeUrl;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sourceRecipeUrl);
    }

    public SourceVo() {
    }

    private SourceVo(Parcel in) {
        this.sourceRecipeUrl = in.readString();
    }

    public static final Parcelable.Creator<SourceVo> CREATOR = new Parcelable.Creator<SourceVo>() {
        public SourceVo createFromParcel(Parcel source) {
            return new SourceVo(source);
        }

        public SourceVo[] newArray(int size) {
            return new SourceVo[size];
        }
    };
}
