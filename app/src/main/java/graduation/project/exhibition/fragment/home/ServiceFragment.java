package graduation.project.exhibition.fragment.home;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.List;

import graduation.project.exhibition.R;
import graduation.project.exhibition.adapter.home.ServiceAdapter;
import graduation.project.exhibition.business.HttpRequest;
import graduation.project.exhibition.domain.ServiceInfo;
import graduation.project.exhibition.fragment.RefreshFragment;
import graduation.project.exhibition.http.JsonCallback;
import graduation.project.exhibition.http.ListResponse;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * 增值服务页面
 */
public class ServiceFragment extends RefreshFragment<ServiceAdapter> {

    @Override
    public ServiceAdapter getAdapter() {
        return new ServiceAdapter();
    }

    @Override
    public void initData() {
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
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

    @Override
    public void requestData() {
        HttpRequest.jsonRequest("/service", new JsonCallback<ListResponse<ServiceInfo>>() {

            @Override
            public void onAfter(Response<ListResponse<ServiceInfo>> response) {
                super.onAfter(response);
                finishRefreshed();
            }

            @Override
            public void onSuccess(Response<ListResponse<ServiceInfo>> response) {
                super.onSuccess(response);
                List<ServiceInfo> serviceInfoList = response.getResponseData().getResponse();
                mRecyclerViewAdapter.setContentData(serviceInfoList);
            }
        });
    }

    @Override
    public void clearCache() {
        HttpRequest.clearCache("/service");
    }
}
