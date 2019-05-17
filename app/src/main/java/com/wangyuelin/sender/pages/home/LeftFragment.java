package com.wangyuelin.sender.pages.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.wangyuelin.sender.R;
import com.wangyuelin.sender.helper.StatusUIHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LeftFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.iv_scan)
    ImageView ivScan;
    @BindView(R.id.tv_scan)
    TextView tvScan;
    @BindView(R.id.ll_scan)
    LinearLayout llScan;

    private static final int REQUEST_CODE = 100;
    StatusUIHelper statusUIHelper;

    public static LeftFragment newInstance(Bundle bundle) {
        LeftFragment fragment = new LeftFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ZXingLibrary.initDisplayOpinion(getActivity());
        statusUIHelper = new StatusUIHelper();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_left, container, false);
        ButterKnife.bind(this, v);

        llScan.setOnClickListener(this);
        return v;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_scan:
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    statusUIHelper.showToast("二维码扫描到的结果：" + result);

                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    statusUIHelper.showToast("二维码解析失败");
                }
            }
        }
    }
}
