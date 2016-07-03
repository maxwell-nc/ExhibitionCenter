package graduation.project.exhibition.fragment.main;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import graduation.project.exhibition.activity.base.SearchActivity;
import graduation.project.exhibition.activity.home.CompanyActivity;
import graduation.project.exhibition.activity.home.CooperationActivity;
import graduation.project.exhibition.activity.home.IntroActivity;
import graduation.project.exhibition.business.ImageUtil;
import graduation.project.exhibition.domain.ListResponse;
import graduation.project.exhibition.domain.Weather;
import graduation.project.exhibition.R;
import graduation.project.exhibition.fragment.BaseFragment;
import graduation.project.exhibition.activity.home.SelectAreaActivity;
import graduation.project.exhibition.activity.home.ShowroomActivity;
import graduation.project.exhibition.business.HttpRequest;
import graduation.project.exhibition.utils.IntentUtils;
import graduation.project.exhibition.utils.ThreadQueue;
import graduation.project.exhibition.http.JsonCallback;
import pres.nc.maxwell.asynchttp.request.Request;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * 主页Fragment
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private View recommendedBtn;
    private View companyBtn;
    private View mapBtn;
    private View searchBtn;
    private View cooperationBtn;
    private View introBtn;
    private View banner;

    private View weatherBtn;
    private ImageView weatherImg;
    private TextView weatherText;
    private boolean isUpdating = false;

    private ImageView bannerImg;
    protected SwipeRefreshLayout mRefreshLayout;

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void initView() {
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_refresh);

        recommendedBtn = findViewById(R.id.ll_recommended);
        companyBtn = findViewById(R.id.ll_company);
        searchBtn = findViewById(R.id.ll_search);
        mapBtn = findViewById(R.id.ll_map);
        cooperationBtn = findViewById(R.id.ll_cooperation);
        introBtn = findViewById(R.id.ll_introduce);

        weatherBtn = findViewById(R.id.ll_weather);
        weatherImg = (ImageView) findViewById(R.id.iv_weather);
        weatherText = (TextView) findViewById(R.id.tv_weather);

        banner = findViewById(R.id.rl_banner);
        bannerImg =  (ImageView) findViewById(R.id.iv_banner);
    }

    @Override
    public void initData() {
        requestData();

        mRefreshLayout.setColorSchemeResources(R.color.main_theme);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData();
            }
        });

        banner.setOnClickListener(this);

        recommendedBtn.setOnClickListener(this);
        weatherBtn.setOnClickListener(this);
        mapBtn.setOnClickListener(this);
        companyBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        cooperationBtn.setOnClickListener(this);
        introBtn.setOnClickListener(this);
    }

    private void requestData() {
        ImageUtil.loadWebImage(bannerImg,"/pics/home/banner.jpg");
        updateWeather();
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_recommended://推荐展馆
                IntentUtils.newIntent(attachActivity)
                        .setActivityClass(ShowroomActivity.class)
                        .startActivity();
                break;
            case R.id.ll_map://地图
                IntentUtils.newIntent(attachActivity)
                        .setActivityClass(SelectAreaActivity.class)
                        .startActivity();
                break;
            case R.id.ll_weather://天气
                updateWeather();
                break;
            case R.id.rl_banner://横幅
            case R.id.ll_company://参展公司
                IntentUtils.newIntent(attachActivity)
                        .setActivityClass(CompanyActivity.class)
                        .startActivity();
                break;
            case R.id.ll_search://搜索
                IntentUtils.newIntent(attachActivity)
                        .setActivityClass(SearchActivity.class)
                        .putExtra("search", "home")
                        .startActivity();
                break;
            case R.id.ll_cooperation://商务合作
                IntentUtils.newIntent(attachActivity)
                        .setActivityClass(CooperationActivity.class)
                        .startActivity();
                break;
            case R.id.ll_introduce://介绍
                IntentUtils.newIntent(attachActivity)
                        .setActivityClass(IntroActivity.class)
                        .startActivity();
                break;
        }
    }

    /**
     * 更新天气
     */
    public void updateWeather() {

        if (isUpdating) {
            return;
        }

        isUpdating = true;

        HttpRequest.jsonRequest("weather", new JsonCallback<ListResponse<Weather>>() {

            @Override
            public void onPrepared(Request request) {
                super.onPrepared(request);
                weatherText.setText("更新中...");

                weatherImg.setImageResource(R.mipmap.icon_big_refresh);
                Animation animation = AnimationUtils.loadAnimation(attachActivity, R.anim.anim_refresh_rotate);
                animation.setInterpolator(new LinearInterpolator());

                weatherImg.startAnimation(animation);
            }

            @Override
            public void onSuccess(Response<ListResponse<Weather>> response) {
                super.onSuccess(response);
                final Weather weather = response.getResponseData().getResponse().get(0);
                ThreadQueue.newQueue(attachActivity).sleep(1000).onUiThread(new ThreadQueue.Task() {
                    @Override
                    public void run() {
                        weatherText.setText(weather.getWeather() + " " + weather.getTem());
                        //TODO：根据类型改变图标
                        weatherImg.clearAnimation();
                        weatherImg.setImageResource(R.mipmap.icon_big_weather);
                    }
                }).exec();
            }

            @Override
            public void onFailure(Response<ListResponse<Weather>> response) {
                super.onFailure(response);
                ThreadQueue.newQueue(attachActivity).sleep(1000).onUiThread(new ThreadQueue.Task() {
                    @Override
                    public void run() {
                        weatherImg.clearAnimation();
                        weatherText.setText("获取天气失败");
                        weatherImg.setImageResource(R.mipmap.icon_big_question);
                    }
                }).exec();

            }

            @Override
            public void onAfter(Response<ListResponse<Weather>> response) {
                super.onAfter(response);
                isUpdating = false;
            }

        });

    }
}
