package org.techtowm.recyclerviewexample;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserService {
    @Multipart
    @POST("/users")
    public Call<ResultGet> register(@Part("data") User user, @Part MultipartBody.Part profile);

    @POST("/sign")
    public Call<LoginResponse> login(@Body User user);
}
