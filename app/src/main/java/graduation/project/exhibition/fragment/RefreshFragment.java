package graduation.project.exhibition.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import graduation.project.exhibition.R;

/**
 * 刷新页面抽取
 */
public abstract class RefreshFragment<T extends RecyclerView.Adapter> extends BaseFragment {

    protected RecyclerView mRecyclerView;
    protected T mRecyclerViewAdapter;
    protected SwipeRefreshLayout mRefreshLayout;

    public RefreshFragment() {
        super(R.layout.refresh_list);
    }

    public RefreshFragment(int viewId) {
        super(viewId > 0 ? viewId : R.layout.refresh_list);
    }

    @Override
    public void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_refresh);
    }

    @Override
    public void initData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(attachActivity));
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

    public abstract T getAdapter();

    /**
     * 请求数据
     */
    public abstract void requestData();

    /**
     * 清空缓存
     */
    public abstract void clearCache();

    /**
     * 停止刷新
     */
    public void finishRefreshed(){
        mRefreshLayout.setRefreshing(false);
    }
}
