package edu.uni.cs.syntaxdesigns.VOs;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeIdVo {
    public String yield;
    public String totalTime;
    public List<ImageUrlVo> images;
    public String name;
    public SourceVo source;
    public String id;
    public List<String> ingredientLines;
    public int rating;
}
