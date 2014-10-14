package edu.uni.cs.syntaxdesigns.Service;

import edu.uni.cs.syntaxdesigns.VOs.SearchByPhraseVo;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface YummlyApi {

    @GET("/recipes")
    void searchByPhrase(@Query("_app_id") String appId, @Query("_app_key") String appKey, @Query("q") String query,Callback<SearchByPhraseVo> callback);
}
