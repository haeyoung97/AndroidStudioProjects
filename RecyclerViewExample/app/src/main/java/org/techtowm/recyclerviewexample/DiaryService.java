package org.techtowm.recyclerviewexample;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DiaryService {
    @POST("/diaries")
    Call<ResultGet> createDiary(@Body Diary diary);
}
