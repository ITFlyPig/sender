package com.wangyuelin.sender.helper;

import android.text.TextUtils;

import com.wangyuelin.sender.util.ToastUtil;
import com.wangyuelin.sender.widgtes.LoadingWidget;

import androidx.fragment.app.FragmentManager;


/**
 * 状态相关的UI放在这里，比如加载中、数据为空、加载失败等等
 */
public class StatusUIHelper {
    private LoadingWidget loadingWidget;
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


}
