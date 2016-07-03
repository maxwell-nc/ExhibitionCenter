package graduation.project.exhibition.fragment.info;

import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import graduation.project.exhibition.R;
import graduation.project.exhibition.activity.info.NewsDetailActivity;
import graduation.project.exhibition.fragment.RefreshFragment;
import graduation.project.exhibition.adapter.info.RecommendedAdapter;
import graduation.project.exhibition.business.HttpRequest;
import graduation.project.exhibition.business.ImageUtil;
import graduation.project.exhibition.domain.BigPic;
import graduation.project.exhibition.domain.ListResponse;
import graduation.project.exhibition.domain.RecInfo;
import graduation.project.exhibition.http.JsonCallback;
import graduation.project.exhibition.utils.IntentUtils;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * 资讯页面:推荐内容
 */
public class RecommendedFragment extends RefreshFragment<RecommendedAdapter> {


    @Override
    public RecommendedAdapter getAdapter() {
        return new RecommendedAdapter();
    }

    @Override
    public void requestData() {
        requestBigPic();
        requestRecInfo();
    }

    @Override
    public void clearCache() {
        HttpRequest.clearCache("bigpic");
        HttpRequest.clearCache("recinfo");
    }

    /**
     * 请求大图
     */
    private void requestBigPic() {

        HttpRequest.jsonRequest("bigpic", new JsonCallback<ListResponse<BigPic>>() {
            @Override
            public void onSuccess(Response<ListResponse<BigPic>> response) {
                super.onSuccess(response);
                List<BigPic> pics = response.getResponseData().getResponse();

                List<View> views = new ArrayList<>();
                List<String> tips = new ArrayList<>();
                for (BigPic pic : pics) {
                    tips.add(pic.getText());
                    views.add(getPageView(pic));
                }

                mRecyclerViewAdapter.setHeaderData(views, tips);
            }
        });

    }

    private View getPageView(final BigPic pic) {
        View view = View.inflate(attachActivity,R.layout.view_banner_imageview, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.newIntent(attachActivity)
                        .setActivityClass(NewsDetailActivity.class)
                        .putExtra("id", pic.getId())
                        .startActivity();
            }
        });
        ImageUtil.loadWebImage(imageView, pic.getPic());
        return view;
    }


    /**
     * 请求焦点资讯
     */
    private void requestRecInfo() {

        HttpRequest.jsonRequest("recinfo", new JsonCallback<ListResponse<RecInfo>>() {
            @Override
            public void onSuccess(Response<ListResponse<RecInfo>> response) {
                super.onSuccess(response);
                List<RecInfo> recInfos = response.getResponseData().getResponse();
                mRecyclerViewAdapter.setContentData(recInfos);
            }

            @Override
            public void onAfter(Response<ListResponse<RecInfo>> response) {
                super.onAfter(response);
                finishRefreshed();
            }
        });

    }
}
