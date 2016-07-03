package graduation.project.exhibition.fragment.info;

import java.util.List;

import graduation.project.exhibition.fragment.RefreshFragment;
import graduation.project.exhibition.adapter.info.InformationAdapter;
import graduation.project.exhibition.business.HttpRequest;
import graduation.project.exhibition.domain.InfoNews;
import graduation.project.exhibition.domain.ListResponse;
import graduation.project.exhibition.http.JsonCallback;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * 资讯页面:最新资讯
 */
public class InformationFragment extends RefreshFragment<InformationAdapter> {

    @Override
    public InformationAdapter getAdapter() {
        return new InformationAdapter();
    }

    @Override
    public void requestData() {
        requestNews();
    }

    @Override
    public void clearCache() {
        HttpRequest.clearCache("infonews");
    }

    private void requestNews() {
        HttpRequest.jsonRequest("infonews", new JsonCallback<ListResponse<InfoNews>>() {

            @Override
            public void onSuccess(Response<ListResponse<InfoNews>> response) {
                super.onSuccess(response);
                List<InfoNews> infoNews = response.getResponseData().getResponse();
                mRecyclerViewAdapter.setContentData(infoNews);
            }

            @Override
            public void onAfter(Response<ListResponse<InfoNews>> response) {
                super.onAfter(response);
                finishRefreshed();
            }

        });

    }

}
