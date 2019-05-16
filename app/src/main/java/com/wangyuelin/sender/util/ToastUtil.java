package com.wangyuelin.sender.util;

import android.text.TextUtils;
import android.widget.Toast;

import com.wangyuelin.sender.MainApplaction;


/**
 *
 */
public class ToastUtil {

    public static void showToast(String txt){
        if (TextUtils.isEmpty(txt)) {
            return;
        }
        Toast.makeText(MainApplaction.getApplaction(), txt, Toast.LENGTH_SHORT).show();

    }

}
