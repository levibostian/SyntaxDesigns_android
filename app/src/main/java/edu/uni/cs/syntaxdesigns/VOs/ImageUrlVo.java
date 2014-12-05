package edu.uni.cs.syntaxdesigns.VOs;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageUrlVo {
    public String hostedSmallUrl;
    public String hostedMediumUrl;
    public String hostedLargeUrl;
}
