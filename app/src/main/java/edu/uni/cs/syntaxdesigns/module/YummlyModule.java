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
