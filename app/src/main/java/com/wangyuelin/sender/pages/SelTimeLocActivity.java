package com.wangyuelin.sender.pages;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lzy.okgo.OkGo;
import com.wangyuelin.sender.R;
import com.wangyuelin.sender.bean.SendTaskBean;
import com.wangyuelin.sender.helper.StatusUIHelper;
import com.wangyuelin.sender.helper.TitleHelper;
import com.wangyuelin.sender.net.FastBaseResp;
import com.wangyuelin.sender.net.FastJsonCallback;
import com.wangyuelin.sender.util.Constant;
import com.wangyuelin.sender.util.Server;
import com.wangyuelin.sender.util.TaskStatus;
import com.wangyuelin.sender.util.Urls;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.qqtheme.framework.picker.SinglePicker;

public class SelTimeLocActivity extends AppCompatActivity implements View.OnClickListener {

    TitleHelper titleHelper;
    @BindView(R.id.tv_task_info)
    TextView tvTaskInfo;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_time_sel)
    TextView tvTimeSel;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_loc)
    TextView tvLoc;
    @BindView(R.id.tv_loc_sel)
    TextView tvLocSel;
    @BindView(R.id.tv_ok)
    TextView tvOk;

    SendTaskBean sendTaskBean;
    StatusUIHelper statusUIHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleHelper = new TitleHelper();
        titleHelper.setContentView(R.layout.activity_sel, this);
        titleHelper.getTvTitle().setText("配送");
        titleHelper.getIvBack().setOnClickListener(this);
        ButterKnife.bind(this, titleHelper.getContent());

        tvLocSel.setOnClickListener(this);
        tvTimeSel.setOnClickListener(this);
        tvOk.setOnClickListener(this);

        sendTaskBean = (SendTaskBean) getIntent().getSerializableExtra(Constant.Key.TASK);

        if (sendTaskBean != null) {
            tvName.setText("任务名称：" + sendTaskBean.getName());
            String statusStr = parseStatus(sendTaskBean.getStatus());
            tvStatus.setText("状态：" + (TextUtils.isEmpty(statusStr) ? "无" : statusStr));
            tvPhone.setText("手机号：" + (TextUtils.isEmpty(sendTaskBean.getRecvPhone()) ? "无" : sendTaskBean.getRecvPhone()));
            tvLocSel.setText((TextUtils.isEmpty(sendTaskBean.getUserSelLocation()) ? "点击选择" : sendTaskBean.getUserSelLocation()));
            tvTimeSel.setText((TextUtils.isEmpty(sendTaskBean.getUserSelTime()) ? "点击选择" : sendTaskBean.getUserSelTime()));
        }
        statusUIHelper = new StatusUIHelper();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_loc_sel:
                selLoc();
                break;
            case R.id.tv_time_sel:
                selTime();
                break;
            case R.id.tv_ok:
                update(tvTimeSel.getText().toString(), tvLocSel.getText().toString());
                break;
        }

    }

    /**
     * 打开
     *
     * @param context
     */
    public static void open(Context context, SendTaskBean taskBean) {
        Intent intent = new Intent(context, SelTimeLocActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.Key.TASK, taskBean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 选择时间
     */
    private void selTime() {
        SinglePicker<String> picker = new SinglePicker<>(this, getTimeData());
        picker.setCanceledOnTouchOutside(false);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setOnItemPickListener((index, time) -> {
            tvTimeSel.setText(time);
        });
        picker.show();
    }


    /**
     * 选择地址
     */
    private void selLoc() {
        SinglePicker<String> picker = new SinglePicker<>(this, getLocData());
        picker.setCanceledOnTouchOutside(false);
        picker.setSelectedIndex(1);
        picker.setCycleDisable(true);
        picker.setOnItemPickListener((index, loc) -> {
            tvLocSel.setText(loc);
        });
        picker.show();
    }

    /**
     * 获得时间
     *
     * @return
     */
    private List<String> getTimeData() {
        String[] times = new String[]{"11:00 ~ 11:30", "11:30 ~ 12:00", "12:00 ~ 12:30", "12:30 ~ 13:00", "13:00 ~ 13:30", "13:30 ~ 14:00", "14:00 ~ 14:30", "14:30 ~ 15:00",
                "15:00 ~ 15:30", "15:30 ~ 16:00", "16:00 ~ 16:30", "16:30 ~ 17:00", "17:00 ~ 17:30", "17:30 ~ 18:00", "18:00 ~ 18:30", "18:30 ~ 19:00"};
        return Arrays.asList(times);
    }

    /**
     * 获得地址
     *
     * @return
     */
    private List<String> getLocData() {
        String[] locs = new String[]{"嘉园宿舍楼", "18号宿舍楼", "19号宿舍楼", "22号宿舍楼", "学生活动中心", "建筑艺术楼", "思源楼", "逸夫楼", "机械工程楼"};
        return Arrays.asList(locs);
    }

    /**
     * 翻译任务装填
     *
     * @param status
     * @return
     */
    private String parseStatus(int status) {
        String str = null;
        switch (status) {
            case TaskStatus.SENDED:
                str = "已配送";
                break;
            case TaskStatus.SENDING:
                str = "正在配送";
                break;
            case TaskStatus.WAIT_SEND:
                str = "等待配送";
                break;
        }
        return str;
    }

    private void update(String selTime, String selLoc) {
        if (TextUtils.isEmpty(selTime) || TextUtils.equals(selTime, "点击选择")) {
            statusUIHelper.showToast("时间不能为空");
            return;
        }
        if (TextUtils.isEmpty(selLoc) || TextUtils.equals(selLoc, "点击选择")) {
            statusUIHelper.showToast("地址不能为空");
            return;
        }
        if (sendTaskBean == null) {
            return;
        }

        sendTaskBean.setUserSelLocation(selLoc);
        sendTaskBean.setUserSelTime(selTime);

        OkGo.<FastBaseResp<String>>get(Server.HOST + Urls.UPDATE_TASK)
                .params("status", TaskStatus.SENDING)
                .params("selTime", selTime)
                .params("selLocation", selLoc)
                .params("id", sendTaskBean.getId())
                .execute(new FastJsonCallback<FastBaseResp<String>>() {
                    @Override
                    public void onSuccessBiz(FastBaseResp<String> resp) {
                        if (resp.isSuccessful()) {
                            statusUIHelper.showToast(resp.message);

                        }
                        finish();

                    }

                    @Override
                    public void onErrorBiz(int code, String msg) {
                        statusUIHelper.showToast(msg);

                    }

                    @Override
                    public void onStartBiz(String url) {
                        super.onStartBiz(url);
                        statusUIHelper.showLoading(getSupportFragmentManager());
                    }

                    @Override
                    public void onFinishBiz() {
                        super.onFinishBiz();
                        statusUIHelper.dismissLoading();
                    }
                });

    }
}
