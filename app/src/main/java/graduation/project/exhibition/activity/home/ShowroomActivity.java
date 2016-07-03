package graduation.project.exhibition.activity.home;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import graduation.project.exhibition.R;
import graduation.project.exhibition.activity.base.BackActivity;
import graduation.project.exhibition.activity.common.RefreshActivity;
import graduation.project.exhibition.adapter.home.ShowroomAdapter;
import graduation.project.exhibition.business.HttpRequest;
import graduation.project.exhibition.domain.Hall;
import graduation.project.exhibition.http.JsonCallback;
import graduation.project.exhibition.http.ListResponse;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * 推荐展馆（陈列室）
 */
public class ShowroomActivity extends RefreshActivity<ShowroomAdapter> {


    @Override
    protected String initBackBar() {
        return "推荐展馆";
    }

    @Override
    protected ShowroomAdapter getAdapter() {
        return new ShowroomAdapter();
    }

    @Override
    protected void clearCache() {
        HttpRequest.clearCache("hall?spec=rec");
    }

    @Override
    protected void requestData() {
        HttpRequest.jsonRequest("hall?spec=rec", new JsonCallback<ListResponse<Hall>>() {

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

}
