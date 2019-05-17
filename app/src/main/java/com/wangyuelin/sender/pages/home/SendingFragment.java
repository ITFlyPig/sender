package com.wangyuelin.sender.pages.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lzy.okgo.OkGo;
import com.wangyuelin.sender.R;
import com.wangyuelin.sender.bean.SendTaskBean;
import com.wangyuelin.sender.bean.TaskResp;
import com.wangyuelin.sender.helper.StatusUIHelper;
import com.wangyuelin.sender.net.FastBaseResp;
import com.wangyuelin.sender.net.FastJsonCallback;
import com.wangyuelin.sender.util.Server;
import com.wangyuelin.sender.util.TaskStatus;
import com.wangyuelin.sender.util.Urls;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SendingFragment extends Fragment {
    @BindView(R.id.ul_recyclerview)
    PullLoadMoreRecyclerView ulRecyclerview;
    @BindView(R.id.rl_whole)
    RelativeLayout rlWhole;

    StatusUIHelper statusUIHelper;
    int curPage = 1;
    int pageSize = 10;
    List<SendTaskBean> tasks;
    TaskAdapter adapter;



    public static SendingFragment newInstance(Bundle bundle) {
        SendingFragment fragment = new SendingFragment();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusUIHelper = new StatusUIHelper();
        tasks = new ArrayList<>();
        adapter = new TaskAdapter(tasks, getContext());

        getData(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wait_send, container, false);
        ButterKnife.bind(this, v);
        initView();
        return v;
    }

    /**
     * 初始化View
     */
    private void initView() {

        ulRecyclerview.setLinearLayout();
        ulRecyclerview.setAdapter(adapter);
        ulRecyclerview.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                curPage = 1;
                getData(false);
                ulRecyclerview.setPushRefreshEnable(true);

            }

            @Override
            public void onLoadMore() {
                curPage++;
                getData(false);

            }
        });

    }

    private void getData(boolean isFirst) {
        int status = TaskStatus.SENDING;
        OkGo.<FastBaseResp<TaskResp>>get(Server.HOST + Urls.GET_TASKS)
                .params("status", status)
                .params("curPage", curPage)
                .params("pageSize", pageSize)
                .execute(new FastJsonCallback<FastBaseResp<TaskResp>>() {
                    @Override
                    public void onSuccessBiz(FastBaseResp<TaskResp> resp) {
                        if (curPage == 1) {
                            tasks.clear();
                        }

                        if (resp.isSuccessful() && resp.res != null) {

                            if (resp.res.tasks != null) {
                                tasks.addAll(resp.res.tasks);
                            }


                            if (!resp.res.hasMore) {
                                ulRecyclerview.setPushRefreshEnable(false);
                            }
                        }

                        //数据为空
                        if (tasks.size() == 0) {
                            statusUIHelper.showEmptyView(rlWhole);
                        } else {
                            statusUIHelper.hiddenEmptyView();
                        }
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onErrorBiz(int code, String msg) {
                        statusUIHelper.showToast(msg);

                    }

                    @Override
                    public void onStartBiz(String url) {
                        super.onStartBiz(url);
                        if (isFirst) {
                            statusUIHelper.showLoading(getFragmentManager());
                        }

                    }

                    @Override
                    public void onFinishBiz() {
                        super.onFinishBiz();
                        if (isFirst) {
                            statusUIHelper.dismissLoading();
                        }
                        ulRecyclerview.setPullLoadMoreCompleted();
                    }
                });

    }
}
