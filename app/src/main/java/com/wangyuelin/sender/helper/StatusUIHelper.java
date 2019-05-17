package com.wangyuelin.sender.helper;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wangyuelin.sender.R;
import com.wangyuelin.sender.util.ToastUtil;
import com.wangyuelin.sender.widgtes.LoadingWidget;

import androidx.fragment.app.FragmentManager;


/**
 * 状态相关的UI放在这里，比如加载中、数据为空、加载失败等等
 */
public class StatusUIHelper {
    private LoadingWidget loadingWidget;
    private View emptyView;
    /**
     * 显示加载中的UI
     * @param fragmentManager
     */
    public void showLoading(FragmentManager fragmentManager) {
        if (loadingWidget == null) {
            loadingWidget = LoadingWidget.newInstance(null);
        }
        loadingWidget.show(fragmentManager, "loadingWidget");
    }

    /**
     * 应藏LoadingWidget
     */
    public void dismissLoading() {
        if (loadingWidget == null) {
            return;
        }
        loadingWidget.dismiss();
    }

    /**
     * 显示提示框
     * @param tip
     */
    public void showToast(String tip) {
        if (TextUtils.isEmpty(tip)) {
            return;
        }
        ToastUtil.showToast(tip);
    }


    /**
     * 显示为空的页面
     * @param container
     */
    public void showEmptyView(ViewGroup container) {
        if (container == null) {
            return;
        }

        if (emptyView == null) {
            emptyView = LayoutInflater.from(container.getContext()).inflate(R.layout.v_empty, container, false);
        }
        //已经添加的情况
        if (emptyView.getParent() != null ) {
            if ( emptyView.getParent() != container) {
                ((ViewGroup)emptyView.getParent()).removeView(emptyView);
                container.addView(emptyView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            }
        } else {
            container.addView(emptyView);
        }
        emptyView.setVisibility(View.VISIBLE);

    }

    /**
     * 隐藏为空的页面
     */
    public void hiddenEmptyView() {
        if (emptyView != null) {
            emptyView.setVisibility(View.GONE);
        }

    }

}
