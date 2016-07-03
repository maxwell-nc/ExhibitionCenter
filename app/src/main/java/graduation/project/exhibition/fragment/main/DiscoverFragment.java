package graduation.project.exhibition.fragment.main;

import android.view.View;

import java.util.List;

import graduation.project.exhibition.activity.base.SearchActivity;
import graduation.project.exhibition.domain.Discover;
import graduation.project.exhibition.domain.ListResponse;
import graduation.project.exhibition.R;
import graduation.project.exhibition.fragment.RefreshFragment;
import graduation.project.exhibition.adapter.discover.DiscoverAdapter;
import graduation.project.exhibition.business.HttpRequest;
import graduation.project.exhibition.http.JsonCallback;
import graduation.project.exhibition.utils.IntentUtils;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * 发现Fragment
 */
public class DiscoverFragment extends RefreshFragment<DiscoverAdapter> implements View.OnClickListener {

    private View searchText;
    private View searchBtn;

    public DiscoverFragment() {
        super(R.layout.fragment_discover);
    }


    @Override
    public void initView() {
        super.initView();
        searchText = findViewById(R.id.tv_search);
        searchBtn =  findViewById(R.id.iv_search);
    }

    @Override
    public void initData() {
        super.initData();
        searchText.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_search://搜索
            case R.id.iv_search:
                IntentUtils.newIntent(attachActivity)
                        .setActivityClass(SearchActivity.class)
                        .putExtra("search","discover")
                        .startActivity();
                break;
        }
    }

    @Override
    public DiscoverAdapter getAdapter() {
        return new DiscoverAdapter();
    }

    @Override
    public void requestData() {
        requestDiscoverItem();
    }

    @Override
    public void clearCache() {
        HttpRequest.clearCache("discover?class=rec");
        HttpRequest.clearCache("discover?class=banner");
    }

    public void requestDiscoverItem() {

        HttpRequest.jsonRequest("discover?class=banner", new JsonCallback<ListResponse<Discover>>() {

            @Override
            public void onSuccess(Response<ListResponse<Discover>> response) {
                super.onSuccess(response);
                List<Discover> discovers = response.getResponseData().getResponse();
                mRecyclerViewAdapter.setBannerData(discovers);
            }

        });

        HttpRequest.jsonRequest("discover?class=rec", new JsonCallback<ListResponse<Discover>>() {

            @Override
            public void onSuccess(Response<ListResponse<Discover>> response) {
                super.onSuccess(response);
                List<Discover> discovers = response.getResponseData().getResponse();
                mRecyclerViewAdapter.setContentData(discovers);
            }

            @Override
            public void onAfter(Response<ListResponse<Discover>> response) {
                super.onAfter(response);
                finishRefreshed();
            }

        });

    }

}
