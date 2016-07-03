package graduation.project.exhibition.activity.discover;

import java.util.List;

import graduation.project.exhibition.activity.common.RefreshActivity;
import graduation.project.exhibition.adapter.discover.ItemAdapter;
import graduation.project.exhibition.business.HttpRequest;
import graduation.project.exhibition.domain.Discover;
import graduation.project.exhibition.domain.ListResponse;
import graduation.project.exhibition.http.JsonCallback;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * 发现条目显示的Activity
 */
public class ItemActivity extends RefreshActivity<ItemAdapter> {

    private String type;

    @Override
    protected String initBackBar() {
        return "";
    }

    @Override
    protected void initData() {
        type = getIntent().getStringExtra("type");
        String title = getIntent().getStringExtra("title");
        titleText.setText(title);

        super.initData();
    }

    @Override
    protected void clearCache() {
        HttpRequest.clearCache("discover?class=" + type);
    }

    @Override
    protected ItemAdapter getAdapter() {
        return new ItemAdapter();
    }

    @Override
    protected void requestData() {
        HttpRequest.jsonRequest("discover?class=" + type, new JsonCallback<ListResponse<Discover>>() {

            @Override
            public void onSuccess(Response<ListResponse<Discover>> response) {
                super.onSuccess(response);
                List<Discover> discovers = response.getResponseData().getResponse();
                mRecyclerViewAdapter.setData(discovers);
            }

            @Override
            public void onAfter(Response<ListResponse<Discover>> response) {
                super.onAfter(response);
                finishRefreshed();
            }

        });
    }
}
