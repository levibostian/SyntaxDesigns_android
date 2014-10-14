package edu.uni.cs.syntaxdesigns.VOs;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchByPhraseVo {

    public int totalMatchCount;
    public List<PhraseResults> matches;

    public List<PhraseResults> getPhraseResults() {
        return matches;
    }
}
