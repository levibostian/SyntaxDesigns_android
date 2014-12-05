package edu.uni.cs.syntaxdesigns.VOs;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeIdVo implements Parcelable {
    public String yield;
    public String totalTime;
    public List<ImageUrlVo> images;
    public String name;
    public SourceVo source;
    public String id;
    public List<String> ingredientLines;
    public int rating;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.yield);
        dest.writeString(this.totalTime);
        dest.writeTypedList(images);
        dest.writeString(this.name);
        dest.writeParcelable(this.source, 0);
        dest.writeString(this.id);
        dest.writeList(this.ingredientLines);
        dest.writeInt(this.rating);
    }

    public RecipeIdVo() {
    }

    private RecipeIdVo(Parcel in) {
        this.yield = in.readString();
        this.totalTime = in.readString();
        in.readTypedList(images, ImageUrlVo.CREATOR);
        this.name = in.readString();
        this.source = in.readParcelable(SourceVo.class.getClassLoader());
        this.id = in.readString();
        in.readList(this.ingredientLines, getClass().getClassLoader());
        this.rating = in.readInt();
    }

    public static final Parcelable.Creator<RecipeIdVo> CREATOR = new Parcelable.Creator<RecipeIdVo>() {
        public RecipeIdVo createFromParcel(Parcel source) {
            return new RecipeIdVo(source);
        }

        public RecipeIdVo[] newArray(int size) {
            return new RecipeIdVo[size];
        }
    };
}
