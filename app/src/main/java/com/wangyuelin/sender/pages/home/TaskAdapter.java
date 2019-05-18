package com.wangyuelin.sender.pages.home;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wangyuelin.sender.R;
import com.wangyuelin.sender.bean.SendTaskBean;
import com.wangyuelin.sender.pages.SelTimeLocActivity;
import com.wangyuelin.sender.util.TaskStatus;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    List<SendTaskBean> tasks;
    Context context;
    LayoutInflater inflater;
    int status;

    public TaskAdapter(List<SendTaskBean> tasks, Context context, int status) {
        this.tasks = tasks;
        this.context = context;
        this.status = status;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_task, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SendTaskBean bean = tasks.get(position);
        holder.tvName.setText(bean.getName());
        String statusStr = parseStatus(bean.getStatus());
        setText(holder.tvStatus, TextUtils.isEmpty(statusStr) ? null : "状态：" + statusStr);
        setText(holder.tvPhone, TextUtils.isEmpty(bean.getRecvPhone()) ? null : "接收手机：" + bean.getRecvPhone());
        setText(holder.tvLocation, TextUtils.isEmpty(bean.getUserSelLocation()) ? null : "接收地址：" + bean.getUserSelLocation());
        setText(holder.tvTime, TextUtils.isEmpty(bean.getUserSelTime()) ? null : "接收时间：" + bean.getUserSelTime());
        holder.llWhole.setOnClickListener(v -> {
            if (status == TaskStatus.WAIT_SEND) {
                SelTimeLocActivity.open(context, bean);
            }
        });


    }

    @Override
    public int getItemCount() {
        return tasks == null ? 0 : tasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_location)
        TextView tvLocation;
        @BindView(R.id.whole)
        LinearLayout llWhole;


        public ViewHolder(@NonNull View v) {
            super(v);
            ButterKnife.bind(this, v);


        }
    }

    /**
     * 翻译任务装填
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

    /**
     * 设置文字
     * @param tv
     * @param str
     */
    private void setText(TextView tv, String str) {
        if (TextUtils.isEmpty(str)) {
            tv.setVisibility(View.GONE);
        } else {
            tv.setVisibility(View.VISIBLE);
            tv.setText(str);
        }

    }
}
