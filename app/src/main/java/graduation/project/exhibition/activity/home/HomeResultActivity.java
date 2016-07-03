package graduation.project.exhibition.activity.home;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import graduation.project.exhibition.activity.common.RefreshActivity;
import graduation.project.exhibition.adapter.home.HomeResultAdapter;
import graduation.project.exhibition.business.HttpRequest;
import graduation.project.exhibition.domain.Company;
import graduation.project.exhibition.domain.ListResponse;
import graduation.project.exhibition.domain.Product;
import graduation.project.exhibition.http.JsonCallback;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * 主页搜索的结果Activity
 */
public class HomeResultActivity extends RefreshActivity<HomeResultAdapter> {

    private String keyword;

    @Override
    protected void initData() {
        String key = getIntent().getStringExtra("keyword");
        try {
            keyword = URLEncoder.encode(key, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        super.initData();
    }

    @Override
    protected String initBackBar() {
        return "搜索结果";
    }

    @Override
    protected void clearCache() {
        HttpRequest.clearCache("search_company?key=" + keyword);
        HttpRequest.clearCache("search_product?key=" + keyword);
    }

    @Override
    protected HomeResultAdapter getAdapter() {
        return new HomeResultAdapter();
    }

    @Override
    protected void requestData() {
        searchCompany();
    }

    private void searchProduct() {
        HttpRequest.jsonRequest("search_product?key=" + keyword, new JsonCallback<ListResponse<Product>>() {

            @Override
            public void onSuccess(Response<ListResponse<Product>> response) {
                super.onSuccess(response);
                List<Product> products = response.getResponseData().getResponse();
                mRecyclerViewAdapter.setProductData(products);
            }

            @Override
            public void onAfter(Response<ListResponse<Product>> response) {
                super.onAfter(response);
                finishRefreshed();
            }
        });
    }

    private void searchCompany() {
        HttpRequest.jsonRequest("search_company?key=" + keyword, new JsonCallback<ListResponse<Company>>() {
            @Override
            public void onSuccess(Response<ListResponse<Company>> response) {
                super.onSuccess(response);
                List<Company> companies = response.getResponseData().getResponse();
                mRecyclerViewAdapter.setCompanyData(companies);
            }

            @Override
            public void onAfter(Response<ListResponse<Company>> response) {
                super.onAfter(response);
                searchProduct();
            }
        });
    }

}
