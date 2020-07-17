package com.MiniDouyin;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static String ID;
    public static String NAME;
    public static String mp4Path;
    public String[] mPermissionsArrays = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO};
    public final static int REQUEST_PERMISSION = 1;
    public final static int REQUEST_VIDEO_CAPTURE = 2;
    public final static int REQUEST_CHANGE = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ID=getIntent().getStringExtra("id");
        NAME=getIntent().getStringExtra("name");

        ViewPager pager = findViewById(R.id.view_pager);
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                if(i == 0)
                {
                    return new ListFragment();
                }
                else if(i==1){
                    return new AddFragment();
                }
                else
                {
                    return new MineFragment();
                }
            }
            @Override
            public int getCount() {
                return 3;
            }
            @Override
            public CharSequence getPageTitle(int position) {
                if(position == 0){
                    return "List";
                }
                if(position==1){
                    return "Add";
                }
                else {
                    return "Home";
                }
            }
        });
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(pager);
    }
    public void openCamera(){
        Intent SystemIntent=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        mp4Path=getOutputMediaPath();
        SystemIntent.putExtra(MediaStore.EXTRA_OUTPUT,mp4Path);
        SystemIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
        if(SystemIntent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(SystemIntent,REQUEST_VIDEO_CAPTURE);
        }
    }
    public boolean checkPermissionAllGranted(String[] permissions) {
        // 6.0以下不需要
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String permission : permissions) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }
    public String getOutputMediaPath(){
        File mediaStorageDir=getExternalFilesDir(Environment.DIRECTORY_MOVIES);
        String timeStamp=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile=new File(mediaStorageDir,"Video_"+timeStamp+".mp4");
        if(mediaFile.exists()) mediaFile.getParentFile().mkdirs();
        return mediaFile.getAbsolutePath();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            Toast.makeText(this, "已经授权" + Arrays.toString(permissions), Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onActivityResult(int RequestCode,int resultCode, Intent intent) {
        super.onActivityResult(RequestCode, resultCode, intent);
        if (RequestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();
            Intent VideoIntent=new Intent(MainActivity.this,HandleVideoActivity.class);
            VideoIntent.setData(videoUri);
            startActivity(VideoIntent);
        }
        else if(RequestCode == REQUEST_CHANGE && resultCode == RESULT_OK){
                NAME=intent.getStringExtra("NAME");
                ID=intent.getStringExtra("ID");

        }
    }
    public void return_back(){
        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
    }
    public void changeInfo(){
        Intent intent=new Intent(MainActivity.this,ChangeActivity.class);
        startActivityForResult(intent,REQUEST_CHANGE);
    }
    public String getID(){
        return ID;
    }

    public String getNAME(){
        return NAME;
    }
}

