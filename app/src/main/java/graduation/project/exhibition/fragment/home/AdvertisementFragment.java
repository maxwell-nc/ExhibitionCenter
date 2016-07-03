package graduation.project.exhibition.fragment.home;

import java.util.List;

import graduation.project.exhibition.adapter.home.AdvertisementAdapter;
import graduation.project.exhibition.business.HttpRequest;
import graduation.project.exhibition.domain.Advertisement;
import graduation.project.exhibition.fragment.RefreshFragment;
import graduation.project.exhibition.http.JsonCallback;
import graduation.project.exhibition.http.ListResponse;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * 广告合作页面
 */
public class AdvertisementFragment extends RefreshFragment<AdvertisementAdapter> {

    @Override
    public AdvertisementAdapter getAdapter() {
        return new AdvertisementAdapter();
    }

    @Override
    public void requestData() {
        HttpRequest.jsonRequest("/ad", new JsonCallback<ListResponse<Advertisement>>() {

            @Override
            public void onAfter(Response<ListResponse<Advertisement>> response) {
                super.onAfter(response);
                finishRefreshed();
            }

            @Override
            public void onSuccess(Response<ListResponse<Advertisement>> response) {
                super.onSuccess(response);
                List<Advertisement> adList = response.getResponseData().getResponse();
                mRecyclerViewAdapter.setContentData(adList);
            }
        });
    }

    @Override
    public void clearCache() {
        HttpRequest.clearCache("/ad");
    }
}
