package com.MiniDouyin;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class AddFragment extends Fragment {
    public ImageView imageView;
    public static String mp4Path;
    public static String ID;
    public static String NAME;
    private TextView text1;
    private TextView text2;
    private MainActivity douyinActivity;
    public String[] mPermissionsArrays = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO};
    public final static int REQUEST_PERMISSION = 1;
    public final static int REQUEST_VIDEO_CAPTURE = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstancedState) {
        super.onViewCreated(view, savedInstancedState);
        douyinActivity = (MainActivity) getActivity();


        imageView = view.findViewById(R.id.img_main_record);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!douyinActivity.checkPermissionAllGranted(mPermissionsArrays)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(mPermissionsArrays, REQUEST_PERMISSION);
                        if (!douyinActivity.checkPermissionAllGranted(mPermissionsArrays)) {
                            //Toast.makeText(douyinActivity, "获取权限失败", Toast.LENGTH_SHORT).show();
                        } else {
                            douyinActivity.openCamera();
                        }
                    } else {
                        //按说是不可能出现这种情况的
                        //Toast.makeText(douyinActivity, "获取权限失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //.makeText(douyinActivity, "已经获取所有所需权限", Toast.LENGTH_SHORT).show();
                    douyinActivity.openCamera();
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

//    @Override
//    public void onActivityResult(int RequestCode, int resultCode, Intent intent) {
//        super.onActivityResult(RequestCode, resultCode, intent);
//        if (RequestCode == REQUEST_VIDEO_CAPTURE && resultCode == douyinActivity.RESULT_OK) {
//            Uri videoUri = intent.getData();
//            Intent VideoIntent = new Intent(getActivity().getApplicationContext(), HandleVideoActivity.class);
//            VideoIntent.setData(videoUri);
//            startActivity(VideoIntent);
//        }
//    }
}
