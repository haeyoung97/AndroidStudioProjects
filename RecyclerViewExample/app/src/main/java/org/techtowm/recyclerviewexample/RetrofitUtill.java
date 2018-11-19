package org.techtowm.recyclerviewexample;

import android.support.annotation.NonNull;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtill {
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://purplebeen.kr:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static MultipartBody.Part createMultipartBody(@NonNull File file, String name){
        RequestBody mFile = RequestBody.create(MediaType.parse("images/*"),file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData(name, file.getName(),mFile);
        return fileToUpload;
    }

    public static RequestBody createRequestBody(@NonNull String value){
        return RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), value);
    }
}
