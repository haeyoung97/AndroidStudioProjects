package org.techtowm.recyclerviewexample;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteActivity extends AppCompatActivity {

    private TextInputEditText titleEditText;
    private TextInputEditText contentEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        init();
        setListeners();
    }

    private void init(){
        titleEditText = findViewById(R.id.titleEditText);
        contentEditText = findViewById(R.id.contentEditText);
        saveButton = findViewById(R.id.saveButton);
    }

    private void setListeners(){
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                save();
            }
        });
    }

    private void save(){
        Diary diary = new Diary(titleEditText.getText().toString(), contentEditText.getText().toString(),
                SharedPreferenceUtil.getPreference(getApplicationContext(), "username"));
        DiaryService diaryService = RetrofitUtill.getLoginRetrofit(getApplicationContext()).create(DiaryService.class);
        Call<ResultGet> call = diaryService.createDiary(diary);

        call.enqueue(new Callback<ResultGet>() {
            @Override
            public void onResponse(Call<ResultGet> call, Response<ResultGet> response) {
                if(response.body() != null && response.body().getResult().isSuccess()){
                    Toast.makeText(getApplicationContext(), "작성에 성공하였습니다!",Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Log.e("error", response.body().getResult().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResultGet> call, Throwable t) {
                Log.e("ERROR",t.getMessage());
            }
        });
    }

}
