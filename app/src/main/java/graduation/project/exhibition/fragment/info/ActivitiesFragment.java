package graduation.project.exhibition.fragment.info;

import graduation.project.exhibition.fragment.RefreshFragment;
import graduation.project.exhibition.adapter.info.ActivitiesAdapter;
import graduation.project.exhibition.business.HttpRequest;
import graduation.project.exhibition.domain.Activities;
import graduation.project.exhibition.domain.ListResponse;
import graduation.project.exhibition.http.JsonCallback;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * 资讯页面:展会活动
 */
public class ActivitiesFragment extends RefreshFragment<ActivitiesAdapter> {

    @Override
    public ActivitiesAdapter getAdapter() {
        return new ActivitiesAdapter();
    }

    @Override
    public void clearCache() {
        HttpRequest.clearCache("activities");
    }

    @Override
    public void requestData() {
        HttpRequest.jsonRequest("activities", new JsonCallback<ListResponse<Activities>>() {
            @Override
            public void onSuccess(Response<ListResponse<Activities>> response) {
                super.onSuccess(response);
                mRecyclerViewAdapter.setData(response.getResponseData().getResponse());
            }

            @Override
            public void onAfter(Response<ListResponse<Activities>> response) {
                super.onAfter(response);
                finishRefreshed();
            }
        });
    }


}
