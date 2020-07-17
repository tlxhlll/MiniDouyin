package com.MiniDouyin;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.MiniDouyin.api.IMiniDouyinService;

import com.MiniDouyin.model.GetVideosResponse;
import com.MiniDouyin.model.PostVideoResponse;
import com.MiniDouyin.model.Video;
import com.MiniDouyin.util.ResourceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MineFragment extends Fragment {
    private RecyclerView mRv;
    private List<Video> mVideos = new ArrayList<>();
    public Uri mSelectedImage;
    private Uri mSelectedVideo;
    private MainActivity douyinActivity;
    private Button change;
    private Button LogOut;
    private String ID;
    private String NAME;
    private TextView id;
    private TextView name;
    private Retrofit retrofit = new Retrofit.Builder().baseUrl(IMiniDouyinService.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    private IMiniDouyinService miniDouyinService = retrofit.create(IMiniDouyinService.class);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.minefragment, container, false);
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstancedState) {
        super.onViewCreated(view, savedInstancedState);
        douyinActivity=(MainActivity) getActivity();
        change=view.findViewById(R.id.button);
        LogOut=view.findViewById(R.id.btn_logout);
        ID=douyinActivity.getID();
        NAME=douyinActivity.getNAME();
        id=view.findViewById(R.id.text_id_mine);
        name=view.findViewById(R.id.text_name_mine);
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                douyinActivity.return_back();
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                douyinActivity.changeInfo();
                id.setText("");
                name.setText("");
                ID=douyinActivity.getID();
                NAME=douyinActivity.getNAME();
                id.setText(ID);
                name.setText(NAME);
            }
        });

        id.setText(ID);
        name.setText(NAME);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerView();
        init();
    }

    private void init() {
        miniDouyinService.getVideos().enqueue(new Callback<GetVideosResponse>() {
            @Override
            public void onResponse(Call<GetVideosResponse> call, Response<GetVideosResponse> response) {
                if (response.body() != null && response.body().videos != null) {
                    mVideos = response.body().videos;
                    Iterator<Video> it = mVideos.iterator();
                  while(it.hasNext()) {
                        Video v = it.next();
                        if (!v.studentId.equals("12345678")) {
                            it.remove();
                        }
                    }
                    mRv.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<GetVideosResponse> call, Throwable throwable) {
                Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void initRecyclerView() {
        mRv = getView().findViewById(R.id.viewpager_mine);
        mRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRv.setAdapter(new RecyclerView.Adapter<MyViewHolder>() {
            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new MyViewHolder(
                        LayoutInflater.from(getActivity()).inflate(R.layout.videoitemview, viewGroup, false));
            }



            @Override
            public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
                final Video video = mVideos.get(i);
                viewHolder.bind(getActivity(), video);
            }

            @Override
            public int getItemCount() {
                return mVideos.size();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123 )
        {
            onResume();
        }
    }

    private MultipartBody.Part getMultipartFromUri(String name, Uri uri) {
        File f = new File(ResourceUtils.getRealPath(getActivity(), uri));
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), f);
        return MultipartBody.Part.createFormData(name, f.getName(), requestFile);
    }

    private void postVideo() {
        MultipartBody.Part coverImagePart = getMultipartFromUri("cover_image", mSelectedImage);
        MultipartBody.Part videoPart = getMultipartFromUri("video", mSelectedVideo);
        //@TODO 4下面的id和名字替换成自己的
        miniDouyinService.postVideo(ID, NAME, coverImagePart, videoPart).enqueue(
                new Callback<PostVideoResponse>() {
                    @Override
                    public void onResponse(Call<PostVideoResponse> call, Response<PostVideoResponse> response) {
                        if (response.body() != null) {
                            Toast.makeText(getActivity(), response.body().toString(), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PostVideoResponse> call, Throwable throwable) {
                        Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }
}
