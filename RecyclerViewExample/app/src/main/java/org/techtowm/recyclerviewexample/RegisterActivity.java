package org.techtowm.recyclerviewexample;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{

    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private Uri uri;
    private File file = null;

    private TextInputEditText usernameEditText;
    private TextInputEditText passwordEditText1;
    private TextInputEditText passwordEditText2;
    private CircleImageView profile;
    private Button signInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
        setListeners();
    }

    private void init(){
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText1 = findViewById(R.id.passwordEditText1);
        passwordEditText2 = findViewById(R.id.passwordEditText2);
        profile = findViewById(R.id.profile);
        signInButton = findViewById(R.id.signInButton);
    }

    private void setListeners(){
        profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getImage();
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(file !=null){
                    if(passwordEditText1.getText().toString().equals(passwordEditText2.getText().toString())){
                        register();
                    }else{
                        Toast.makeText(getApplicationContext(), "비밀번호를 확인해주세요!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "프로필 사진을 선택해주세요!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getImage(){
        if(EasyPermissions.hasPermissions(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)){
            Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
            openGalleryIntent.setType("image/*");
            startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
        }else{
            EasyPermissions.requestPermissions(this, "파일을 읽으려면 권한이 필요합니다.", READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_GALLERY_CODE && resultCode == RESULT_OK){
            uri = data.getData();
            Glide.with(this).load(uri).into(profile);
            String filePath = getRealPathFromURIPath(uri, this);
            file = new File(filePath);
        }
    }

    private String getRealPathFromURIPath(Uri contentURI, Activity activity){
        Cursor cursor = activity.getContentResolver().query(contentURI, null,null,null,null);
        if(cursor == null){
            return contentURI.getPath();
        }else{
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        getImage();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        EasyPermissions.requestPermissions(this, "파일을 읽으려면 권한이 필요합니다.", READ_REQUEST_CODE,Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    private void register(){
        UserService userService = RetrofitUtill.retrofit.create(UserService.class);
        Call<ResultGet> call = userService.register(new User(usernameEditText.getText().toString(), passwordEditText1.getText().toString()), RetrofitUtill.createMultipartBody(file, "profile"));
        call.enqueue(new Callback<ResultGet>() {
            @Override
            public void onResponse(Call<ResultGet> call, Response<ResultGet> response) {
                if(response.body() != null && response.body().getResult().isSuccess()){
                    Toast.makeText(getApplicationContext(), "회원가입에 성공하였습니다!", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Log.d("test", response.body().getResult().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResultGet> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "회원가입에 실패하였습니다!", Toast.LENGTH_SHORT).show();
                Log.e("error", t.toString());
            }
        });
    }
}
