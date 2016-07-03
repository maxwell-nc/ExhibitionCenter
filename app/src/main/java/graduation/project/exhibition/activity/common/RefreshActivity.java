package graduation.project.exhibition.activity.common;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import graduation.project.exhibition.R;
import graduation.project.exhibition.activity.base.BackActivity;

/**
 * 带后退的刷新Activity
 */
public abstract class RefreshActivity<T extends RecyclerView.Adapter> extends BackActivity {

    protected RecyclerView mRecyclerView;
    protected T mRecyclerViewAdapter;
    protected SwipeRefreshLayout mRefreshLayout;


    @Override
    protected int setViewId() {
        return R.layout.refresh_list;
    }

    @Override
    public void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_refresh);
    }

    @Override
    protected void initData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mRecyclerViewAdapter = getAdapter());
        mRefreshLayout.setColorSchemeResources(R.color.main_theme);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshing();
            }
        });

        requestData();
    }


    /**
     * 刷新操作
     */
    public void onRefreshing() {
        clearCache();
        requestData();
    }

    /**
     * 停止刷新
     */
    protected void finishRefreshed() {
        mRefreshLayout.setRefreshing(false);
    }

    /**
     * 清空缓存
     */
    protected abstract void clearCache();

    protected abstract T getAdapter();


    protected abstract void requestData();

}
