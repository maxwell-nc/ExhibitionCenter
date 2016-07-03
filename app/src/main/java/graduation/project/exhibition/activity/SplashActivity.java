package graduation.project.exhibition.activity;

import android.text.TextUtils;

import graduation.project.exhibition.R;
import graduation.project.exhibition.activity.base.BaseActivity;
import graduation.project.exhibition.business.UpdateBusiness;
import graduation.project.exhibition.business.UserInfoBusiness;
import graduation.project.exhibition.utils.IntentUtils;
import graduation.project.exhibition.utils.SharePreferencesUtils;
import graduation.project.exhibition.utils.ThreadQueue;
import graduation.project.exhibition.utils.ToastUtils;
import other.activity.WelcomeActivity;
import pres.nc.maxwell.asynchttp.callback.impl.StringCallback;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * 闪屏页面
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected int setViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void beforeSetView() {
        setTranslucentBar(R.color.main_theme);
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {

        ThreadQueue.newQueue(mActivity).sleep(1500).onUiThread(new ThreadQueue.Task() {
            @Override
            public void run() {

                String isFirst = SharePreferencesUtils.newGetTask().get("isFirst", "true");

                if ("true".equals(isFirst)) {//第一次进入，显示欢迎引导界面

                    UserInfoBusiness.getUsername(new StringCallback() {

                        @Override
                        public void onSuccess(Response<String> response) {
                            String name = response.getResponseData();
                            if (!TextUtils.isEmpty(name)) {
                                SharePreferencesUtils.newPutTask().put("username", name).commit();
                            }

                        }

                    });

                    IntentUtils.newIntent(mActivity)
                            .setActivityClass(WelcomeActivity.class)
                            .startActivity()
                            .finishActivity();

                } else {//非第一次，直接进入主界面

                    IntentUtils.newIntent(mActivity)
                            .setActivityClass(MainActivity.class)
                            .startActivity()
                            .finishActivity();

                }

            }
        }).exec();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);//防止后退后程序再开启Activity
    }
}
