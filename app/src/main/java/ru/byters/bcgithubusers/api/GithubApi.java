package ru.byters.bcgithubusers.api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.byters.bcgithubusers.model.UserInfo;

public interface GithubApi {

    String BASE_URL = "https://api.github.com/";

    @GET("users/{username}")
    Call<UserInfo> getUserInfo(@Path("username") int username);

    @GET("users")
    Call<ArrayList<UserInfo>> getUsers(@Query("since") int id);

    @GET("users")
    Call<ArrayList<UserInfo>> getUsers();

    @GET("search/users")
    Call<ArrayList<UserInfo>> searchUsers(@Query("q") String query);

}
