package edu.uni.cs.syntaxdesigns.VOs;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/*
I commented out anything that would take a lot of time
but that I knew we might want later. Just wanted to get the initial retrofit set up.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class PhraseResults implements Parcelable {
    public List<String> smallImageUrls;
    public String sourceDisplayName;
    public List<String> ingredients;
    public String id;
    public String recipeName;
    public int totalTimeInSeconds;
    //public CookingAttributes attributes;
    //public Flavors flavors;
    public int rating;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sourceDisplayName);
        dest.writeList(this.ingredients);
        dest.writeList(this.smallImageUrls);
        dest.writeString(this.id);
        dest.writeString(this.recipeName);
        dest.writeInt(this.totalTimeInSeconds);
        dest.writeInt(this.rating);
    }

    public PhraseResults() {
    }

    private PhraseResults(Parcel in) {
        this.sourceDisplayName = in.readString();
        in.readList(this.ingredients, getClass().getClassLoader());
        in.readList(this.smallImageUrls, getClass().getClassLoader());
        this.id = in.readString();
        this.recipeName = in.readString();
        this.totalTimeInSeconds = in.readInt();
        this.rating = in.readInt();
    }

    public static final Creator<PhraseResults> CREATOR = new Creator<PhraseResults>() {
        public PhraseResults createFromParcel(Parcel source) {
            return new PhraseResults(source);
        }

        public PhraseResults[] newArray(int size) {
            return new PhraseResults[size];
        }
    };
}
