package major.com.juice_android.api;

import major.com.juice_android.model.DefaultResponse;
import major.com.juice_android.model.DisplayRatingResponse;
import major.com.juice_android.model.GenreListResponse;
import major.com.juice_android.model.InsertRatingResponse;
import major.com.juice_android.model.LoggidInUserResponse;
import major.com.juice_android.model.LoginResponse;
import major.com.juice_android.model.RecommendedSongResponse;
import major.com.juice_android.model.SearchSongResponse;
import major.com.juice_android.model.SongResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface Api
{
    @FormUrlEncoded
    @POST("createuser")
    Call<DefaultResponse> createUser(
            @Field("username") String username,
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("phone") String phone
    );

    @FormUrlEncoded
    @POST("userlogin")
    Call<LoginResponse> userLogin(
            @Field("username") String username,
            @Field("password") String password
    );

    @GET("allsongs")
    Call<SongResponse> getSongs();

    @FormUrlEncoded
    @POST("searchsongs")
    Call<SearchSongResponse> searchSongs(
            @Field("keyword") String keyword
    );

    @FormUrlEncoded
    @POST("insertrating")
    Call<InsertRatingResponse> insertRating(
            @Field("songid") int songid,
            @Field("username") String username,
            @Field("rating") float rating
    );

    @FormUrlEncoded
    @POST("displayrating")
    Call<DisplayRatingResponse> displayRating(
            @Field("songid") int songid,
            @Field("username") String username
    );

    @FormUrlEncoded
    @POST("recommendedsongs")
    Call<RecommendedSongResponse> getRecommendedSongs(
            @Field("userid") String userid
    );

    @FormUrlEncoded
    @POST("loggedinuserinfo")
    Call<LoggidInUserResponse> getLoggedInUserInfo(
      @Field("username") String username

    );

    @FormUrlEncoded
    @POST("genrelist")
    Call<GenreListResponse> getGenreListSongs(
            @Field("genreid") int genreid
    );

    @Streaming
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlAsync(@Url String fileUrl);

}
