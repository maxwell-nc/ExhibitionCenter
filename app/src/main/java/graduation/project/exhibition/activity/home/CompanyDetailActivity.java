package graduation.project.exhibition.activity.home;

import java.util.List;

import graduation.project.exhibition.activity.common.RefreshActivity;
import graduation.project.exhibition.adapter.home.CompanyDetailAdapter;
import graduation.project.exhibition.business.HttpRequest;
import graduation.project.exhibition.domain.Company;
import graduation.project.exhibition.domain.Product;
import graduation.project.exhibition.http.JsonCallback;
import graduation.project.exhibition.http.ListResponse;
import graduation.project.exhibition.utils.ToastUtils;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * 公司详情页
 */
public class CompanyDetailActivity extends RefreshActivity<CompanyDetailAdapter> {

    private String id;

    @Override
    protected String initBackBar() {
        return "公司详情页";
    }

    @Override
    protected void initData() {
        id = getIntent().getStringExtra("id");

        super.initData();
    }

    @Override
    protected void clearCache() {
        HttpRequest.clearCache("/company?id=" + id);
        HttpRequest.clearCache("/product?com=" + id);
    }

    @Override
    protected CompanyDetailAdapter getAdapter() {
        return new CompanyDetailAdapter();
    }

    @Override
    protected void requestData() {
        HttpRequest.jsonRequest("/company?id=" + id, new JsonCallback<ListResponse<Company>>() {

            @Override
            public void onSuccess(Response<ListResponse<Company>> response) {
                super.onSuccess(response);
                List<Company> companies = response.getResponseData().getResponse();
                if (companies.size() > 0) {
                    mRecyclerViewAdapter.setHeaderData(companies.get(0));
                }else {
                    ToastUtils.toast("不存在的公司信息！");
                    finish();
                }
            }

        });

        HttpRequest.jsonRequest("/product?com=" + id, new JsonCallback<ListResponse<Product>>() {

            @Override
            public void onSuccess(Response<ListResponse<Product>> response) {
                super.onSuccess(response);
                List<Product> productList = response.getResponseData().getResponse();
                mRecyclerViewAdapter.setContentData(productList);
            }

            @Override
            public void onAfter(Response<ListResponse<Product>> response) {
                super.onAfter(response);
                finishRefreshed();
            }
        });
    }

}
