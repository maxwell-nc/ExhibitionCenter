package graduation.project.exhibition.activity.home;

import graduation.project.exhibition.activity.common.RefreshActivity;
import graduation.project.exhibition.adapter.home.CompanyAdapter;
import graduation.project.exhibition.business.HttpRequest;
import graduation.project.exhibition.domain.Company;
import graduation.project.exhibition.http.JsonCallback;
import graduation.project.exhibition.http.ListResponse;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * 参展公司
 */
public class CompanyActivity extends RefreshActivity<CompanyAdapter> {

    @Override
    protected String initBackBar() {
        return "参展公司";
    }


    @Override
    protected CompanyAdapter getAdapter() {
        return new CompanyAdapter();
    }

    @Override
    protected void clearCache() {
        HttpRequest.clearCache("/company");
    }

    @Override
    protected void requestData() {
        HttpRequest.jsonRequest("/company", new JsonCallback<ListResponse<Company>>() {

            @Override
            public void onSuccess(Response<ListResponse<Company>> response) {
                super.onSuccess(response);
                mRecyclerViewAdapter.setData(response.getResponseData().getResponse());
            }

            @Override
            public void onAfter(Response<ListResponse<Company>> response) {
                super.onAfter(response);
                finishRefreshed();
            }
        });
    }

}
