package app.com.wikistarwars.Api;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    private static final String ROOT_URL = "https://swapi.co/api/";
    public static Retrofit retrofit = null;

    private static Retrofit getRetrofitInstance() {
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(ROOT_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Service getApiService() {
        return getRetrofitInstance().create(Service.class);
    }

}
