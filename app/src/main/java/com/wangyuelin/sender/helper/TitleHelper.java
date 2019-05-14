package com.wangyuelin.sender.helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangyuelin.sender.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


/**
 * 设置Activity的title
 */
public class TitleHelper {
    private TextView tvTitle;
    private ImageView ivBack;
    private View content;


    /**
     * 设置activiy的title
     * @param resLayout
     * @param activity
     */
    public void setContentView(int resLayout, AppCompatActivity activity) {
        if (activity == null) {
            return;
        }
        activity.setContentView(R.layout.base_activity);
        ViewGroup rlTitle = activity.findViewById(R.id.title);
        FrameLayout flContent = activity.findViewById(R.id.content);

        View vTitle = LayoutInflater.from(activity).inflate(R.layout.v_activity_title, rlTitle);
        tvTitle = vTitle.findViewById(R.id.tv_title);
        ivBack = vTitle.findViewById(R.id.iv_back);


        content = LayoutInflater.from(activity).inflate(resLayout, flContent);

    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public ImageView getIvBack() {
        return ivBack;
    }

    public View getContent() {
        return content;
    }


    /**
     * 设置Fragment的标题
     * @param resLayout
     * @param fragment
     * @return
     */
    public View onCreateView(int resLayout, Fragment fragment){
        View v = LayoutInflater.from(fragment.getContext()).inflate(R.layout.base_activity, null);
        ViewGroup rlTitle = v.findViewById(R.id.title);
        FrameLayout flContent = v.findViewById(R.id.content);
        //设置标题
        View vTitle = LayoutInflater.from(fragment.getContext()).inflate(R.layout.v_activity_title, rlTitle);
        tvTitle = vTitle.findViewById(R.id.tv_title);
        ivBack = vTitle.findViewById(R.id.iv_back);

        content = LayoutInflater.from(fragment.getContext()).inflate(resLayout, flContent);
        return v;
    }
}
