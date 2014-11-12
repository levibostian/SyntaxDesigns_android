package edu.uni.cs.syntaxdesigns.VOs;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SourceVo {
    public String sourceRecipeUrl;
}
