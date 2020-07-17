package com.MiniDouyin;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.MiniDouyin.model.Video;
import com.airbnb.lottie.LottieAnimationView;
import com.bytedance.androidcamp.network.lib.util.ImageHelper;

import static com.airbnb.lottie.L.TAG;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public ImageView img;
    public LottieAnimationView lottie;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        img = itemView.findViewById(R.id.img);
        lottie = itemView.findViewById(R.id.dianzan);
    }

    public void bind(final Activity activity, final Video video) {
        ImageHelper.displayWebImage(video.imageUrl, img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoActivity.launch(activity, video.videoUrl);
            }
        });
        lottie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lottie.playAnimation();
//                Log.i(TAG, "onClick: 点击成功");

            }
        });
    }
}