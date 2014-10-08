package edu.uni.cs.syntaxdesigns.module;

import dagger.Module;
import dagger.Provides;
import edu.uni.cs.syntaxdesigns.Service.YummlyApi;
import edu.uni.cs.syntaxdesigns.application.SyntaxDesignsApplication;
import org.codehaus.jackson.map.ObjectMapper;
import retrofit.RestAdapter;

import javax.inject.Singleton;

@Module(library = true, complete = false)
public class YummlyModule {

    private SyntaxDesignsApplication mApplication;
    private final ObjectMapper mapper = new ObjectMapper();
    public final static String APP_ID = "018d2c56";
    public final static String APP_KEY = "81b86f1ca40d639d48561e68d67c04e7";
    private static String YUMMLY_API = "http://api.yummly.com/v1/api";

    public YummlyModule(SyntaxDesignsApplication application) {
       mApplication = application;
    }

    @Provides @Singleton
    RestAdapter provideRestAdapter() {
        return new RestAdapter.Builder().setEndpoint(YUMMLY_API).setConverter(new JacksonConverter(mapper)).build();
    }


    @Provides
    public YummlyApi YummlyApi(RestAdapter restAdapter) {
        return restAdapter.create(YummlyApi.class);
    }
}
