package edu.uni.cs.syntaxdesigns.VOs;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

/*
I commented out anything that would take a lot of time
but that I knew we might want later. Just wanted to get the initial retrofit set up.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class PhraseResults {
    //public ImageUrlsBySize imageUrlBySize;
    public String sourceDisplayName;
    public List<String> ingredients;
    public String id;
    public String recipeName;
    public int totalTimeInSeconds;
    //public CookingAttributes attributes;
    //public Flavors flavors;
    public int rating;

}
