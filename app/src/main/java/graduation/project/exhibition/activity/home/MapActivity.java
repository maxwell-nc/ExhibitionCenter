package graduation.project.exhibition.activity.home;

import android.graphics.Bitmap;

import com.jiahuan.svgmapview.SVGMapView;
import com.jiahuan.svgmapview.SVGMapViewListener;

import java.util.List;

import graduation.project.exhibition.R;
import graduation.project.exhibition.activity.base.BackActivity;
import graduation.project.exhibition.business.HttpRequest;
import graduation.project.exhibition.domain.Overlay;
import graduation.project.exhibition.http.JsonCallback;
import graduation.project.exhibition.http.ListResponse;
import graduation.project.exhibition.ui.HallOverlay;
import graduation.project.exhibition.utils.IntentUtils;
import graduation.project.exhibition.utils.ToastUtils;
import pres.nc.maxwell.asynchttp.callback.impl.StringCallback;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * 地图界面
 */
public class MapActivity extends BackActivity {

    private SVGMapView mapView;
    private String hallId;
    private List<Overlay> overlays;

    @Override
    protected String initBackBar() {
        return "展馆室内地图";
    }

    @Override
    protected int setViewId() {
        return R.layout.activity_location;
    }

    @Override
    protected void initView() {
        super.initView();
        mapView = (SVGMapView) findViewById(R.id.svg_map);
    }

    @Override
    protected void initData() {
        super.initData();

        hallId = getIntent().getStringExtra("id");

        mapView.registerMapViewListener(new SVGMapViewListener() {
            @Override
            public void onMapLoadComplete() {
                mapView.getController().setRotationGestureEnabled(false);

                if (overlays != null) {
                    for (final Overlay overlay : overlays) {

                        int x = Integer.parseInt(overlay.getX());
                        int y = Integer.parseInt(overlay.getY());
                        int type = Integer.parseInt(overlay.getType());
                        HallOverlay hallOverlay = new HallOverlay(mapView, x, y, type);
                        hallOverlay.setOnClickListener(new HallOverlay.onClickListener() {
                            @Override
                            public void onClick() {
                                IntentUtils.newIntent(mActivity)
                                        .setActivityClass(CompanyDetailActivity.class)
                                        .putExtra("id",overlay.getComid())
                                        .startActivity();
                            }
                        });
                        mapView.getOverLays().add(hallOverlay);
                    }

                }

                mapView.refresh();
            }

            @Override
            public void onMapLoadError() {
                ToastUtils.toast("加载地图失败！");
                finish();
            }

            @Override
            public void onGetCurrentMap(Bitmap bitmap) {
            }
        });

        getOverlays();
    }

    private void downloadMap() {
        HttpRequest.jsonRequestWithoutCache("map/1.svg", new StringCallback() {

            @Override
            public void onSuccess(Response<String> response) {
                super.onSuccess(response);
                String mapData = response.getResponseData();
                mapView.loadMap(mapData);
            }

            @Override
            public void onFailure(Response<String> response) {
                super.onFailure(response);
                ToastUtils.toast("加载地图失败！");
                finish();
            }
        });
    }


    public void getOverlays() {
        HttpRequest.jsonRequestWithoutCache("overlay?mapid=" + hallId, new JsonCallback<ListResponse<Overlay>>() {

            @Override
            public void onSuccess(Response<ListResponse<Overlay>> response) {
                super.onSuccess(response);
                overlays = response.getResponseData().getResponse();
                downloadMap();
            }

            @Override
            public void onFailure(Response<ListResponse<Overlay>> response) {
                super.onFailure(response);
                ToastUtils.toast("加载地图失败！");
                finish();
            }
        });
    }
}
