package edu.uni.cs.syntaxdesigns.VOs;

import android.os.Parcel;
import android.os.Parcelable;

public class SavedRecipeVo implements Parcelable {
    public String recipeName;
    public long rowId;
    public String yummlyUrl;
    public boolean isFavorite;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.recipeName);
        dest.writeLong(this.rowId);
        dest.writeString(this.yummlyUrl);
        dest.writeByte(isFavorite ? (byte) 1 : (byte) 0);
    }

    public SavedRecipeVo() {
    }

    private SavedRecipeVo(Parcel in) {
        this.recipeName = in.readString();
        this.rowId = in.readLong();
        this.yummlyUrl = in.readString();
        this.isFavorite = in.readByte() != 0;
    }

    public static final Parcelable.Creator<SavedRecipeVo> CREATOR = new Parcelable.Creator<SavedRecipeVo>() {
        public SavedRecipeVo createFromParcel(Parcel source) {
            return new SavedRecipeVo(source);
        }

        public SavedRecipeVo[] newArray(int size) {
            return new SavedRecipeVo[size];
        }
    };
}
