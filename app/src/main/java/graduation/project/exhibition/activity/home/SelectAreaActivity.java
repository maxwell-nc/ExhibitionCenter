package graduation.project.exhibition.activity.home;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.balysv.materialripple.MaterialRippleLayout;

import java.util.List;

import graduation.project.exhibition.R;
import graduation.project.exhibition.activity.base.BackActivity;
import graduation.project.exhibition.activity.common.RefreshActivity;
import graduation.project.exhibition.adapter.home.AreaAdapter;
import graduation.project.exhibition.adapter.home.ShowroomAdapter;
import graduation.project.exhibition.business.HttpRequest;
import graduation.project.exhibition.domain.Hall;
import graduation.project.exhibition.http.JsonCallback;
import graduation.project.exhibition.http.ListResponse;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * 地图：选择展览馆
 */
public class SelectAreaActivity extends RefreshActivity<AreaAdapter> {

    private RadioGroup radioGroup;
    private String spec = "0";

    @Override
    protected String initBackBar() {
        return "选择展览馆";
    }

    @Override
    protected int setViewId() {
        return R.layout.activity_select_area;
    }

    @Override
    public void initView() {
        super.initView();
        radioGroup = (RadioGroup) findViewById(R.id.rg_area);
    }

    @Override
    protected void initData() {
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mRecyclerViewAdapter = new AreaAdapter());
        mRefreshLayout.setColorSchemeResources(R.color.main_theme);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshing();
            }
        });

        requestData();
    }

    @Override
    protected void clearCache() {
        HttpRequest.clearCache("/hall?spec=" + spec);
    }

    @Override
    protected AreaAdapter getAdapter() {
        return new AreaAdapter();
    }

    @Override
    protected void requestData() {
        HttpRequest.jsonRequest("/hall?spec=" + spec, new JsonCallback<ListResponse<Hall>>() {

            @Override
            public void onSuccess(Response<ListResponse<Hall>> response) {
                super.onSuccess(response);
                List<Hall> halls = response.getResponseData().getResponse();
                mRecyclerViewAdapter.setHalls(halls);
            }

            @Override
            public void onAfter(Response<ListResponse<Hall>> response) {
                super.onAfter(response);
                finishRefreshed();
            }

        });
    }

    /**
     * 点击事件
     */
    public void radioClick(View v) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton radioButton = ((MaterialRippleLayout) radioGroup.getChildAt(i)).getChildView();
            if (radioButton == v) {
                ((RadioButton) v).setChecked(true);
                spec = String.valueOf(i);
                requestData();
            } else {
                radioButton.setChecked(false);
            }
        }
    }
}
