package com.MiniDouyin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.MiniDouyin.api.IMiniDouyinService;
import com.MiniDouyin.model.PostVideoResponse;
import com.MiniDouyin.util.ResourceUtils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HandleVideoActivity extends AppCompatActivity {
    private boolean isChoose=false;
    private ImageButton return_back;
    private ImageButton release;
    private VideoView videoView;
    private TextView text;
    private Uri imageUri;
    private Uri  videoUri;
    private static final int PICK_IMAGE = 1;
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(IMiniDouyinService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private IMiniDouyinService miniDouyinService = retrofit.create(IMiniDouyinService.class);
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handlevideo);

        Intent intent=getIntent();
        videoUri=intent.getData();

        return_back=findViewById(R.id.return_back);
        return_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HandleVideoActivity.this, "上传失败", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        text=findViewById(R.id.nnn);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
                isChoose=true;
            }
        });
        release=findViewById(R.id.release);
        release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:数据库有关
                if(isChoose) {
                    postVideo();
                    finish();
                }
                else{
                    Toast.makeText(HandleVideoActivity.this, "还未选择封面", Toast.LENGTH_LONG).show();
                }
            }
        });

        videoView=findViewById(R.id.videoView);
        videoView.setVideoURI(videoUri);
        videoView.start();
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(videoView.isPlaying()){
                    videoView.pause();
                }
                else{
                    videoView.start();
                }
            }
        });
    }
    public void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && null != data) {
            if (requestCode == PICK_IMAGE) {
                imageUri = data.getData();
            }
        }
    }
    private MultipartBody.Part getMultipartFromUri(String name, Uri uri) {
        File f = new File(ResourceUtils.getRealPath(HandleVideoActivity.this, uri));
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), f);
        return MultipartBody.Part.createFormData(name, f.getName(), requestFile);
    }

    private void postVideo() {
        MultipartBody.Part coverImagePart = getMultipartFromUri("cover_image", imageUri);
        MultipartBody.Part videoPart = getMultipartFromUri("video", videoUri);
        miniDouyinService.postVideo("12345678", "tz", coverImagePart, videoPart).enqueue(
                new Callback<PostVideoResponse>() {
                    @Override
                    public void onResponse(Call<PostVideoResponse> call, Response<PostVideoResponse> response) {
                        if (response.body() != null) {
                            Toast.makeText(HandleVideoActivity.this, response.body().toString(), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PostVideoResponse> call, Throwable throwable) {
                        Toast.makeText(HandleVideoActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
