package graduation.project.exhibition.activity.discover;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import graduation.project.exhibition.activity.common.RefreshActivity;
import graduation.project.exhibition.adapter.discover.ItemAdapter;
import graduation.project.exhibition.business.HttpRequest;
import graduation.project.exhibition.domain.Discover;
import graduation.project.exhibition.domain.ListResponse;
import graduation.project.exhibition.http.JsonCallback;
import graduation.project.exhibition.utils.ToastUtils;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * 搜索发现的结果Activity
 */
public class DiscoverResultActivity extends RefreshActivity<ItemAdapter> {

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
        HttpRequest.clearCache("search_discover?key=" + keyword);
    }

    @Override
    protected ItemAdapter getAdapter() {
        return new ItemAdapter();
    }

    @Override
    protected void requestData() {
        HttpRequest.jsonRequest("search_discover?key=" + keyword, new JsonCallback<ListResponse<Discover>>() {
            @Override
            public void onSuccess(Response<ListResponse<Discover>> response) {
                super.onSuccess(response);
                List<Discover> discovers = response.getResponseData().getResponse();
                if (discovers.size() > 0) {
                    mRecyclerViewAdapter.setData(discovers);
                } else {
                    ToastUtils.toast("没有搜索到任何信息，请换其他关键字试试！");
                    finish();
                }
            }

            @Override
            public void onAfter(Response<ListResponse<Discover>> response) {
                super.onAfter(response);
                finishRefreshed();
            }
        });
    }
}
