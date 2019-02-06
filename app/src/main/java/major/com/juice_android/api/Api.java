package major.com.juice_android.api;

import major.com.juice_android.model.DefaultResponse;
import major.com.juice_android.model.LoginResponse;
import major.com.juice_android.model.SongResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

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
    
}
