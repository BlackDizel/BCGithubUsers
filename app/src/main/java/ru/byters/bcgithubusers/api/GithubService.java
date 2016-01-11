package ru.byters.bcgithubusers.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class GithubService {
    private static GithubApi api;

    public static synchronized GithubApi getApi() {
        if (api == null) {
            api = initializeApi();
        }
        return api;
    }

    private static GithubApi initializeApi() {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(double.class, new JsonDeserializer<Double>() {
                    @Override
                    public Double deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        try {
                            return Double.parseDouble(json.getAsString().replace(',', '.'));
                        } catch (NumberFormatException e) {
                            throw new JsonParseException(e);
                        }
                    }
                })
                .setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit r = new Retrofit.Builder()
                .baseUrl(GithubApi.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return r.create(GithubApi.class);
    }
}