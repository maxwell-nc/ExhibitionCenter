package other.activity;

import android.view.View;
import android.widget.TextView;

import graduation.project.exhibition.R;
import graduation.project.exhibition.activity.base.BaseActivity;
import graduation.project.exhibition.activity.MainActivity;
import graduation.project.exhibition.utils.IntentUtils;
import graduation.project.exhibition.utils.SharePreferencesUtils;
import other.ui.parallaxpager.ParallaxContainer;

/**
 * 欢迎界面
 */
public class WelcomeActivity extends BaseActivity implements View.OnClickListener {

    public static final String FROM_SETTING = "Setting";

    TextView startBtn;
    TextView nextBtn1;
    TextView nextBtn2;
    ParallaxContainer parallaxContainer;
    private String fromActivity;

    @Override
    protected int setViewId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void beforeSetView() {
        setFullScreen();
    }

    @Override
    protected void initView() {
        parallaxContainer = (ParallaxContainer) findViewById(R.id.parallax_container);

        parallaxContainer.setupChildren(getLayoutInflater(),
                R.layout.view_welcome_1, R.layout.view_welcome_2,
                R.layout.view_welcome_3);

        startBtn = (TextView) findViewById(R.id.tv_start);
        nextBtn1 = (TextView) findViewById(R.id.tv_next_1);
        nextBtn2 = (TextView) findViewById(R.id.tv_next_2);
    }

    @Override
    protected void initData() {

        try {
            String fromSetting = (String) getIntent().getExtras().get("from");
            if (FROM_SETTING.equals(fromSetting)) {//从设置页面进入
                this.fromActivity = FROM_SETTING;
            }
        } catch (NullPointerException e) {
            //这里捕获不需要处理，没有data不影响运行
        }

        startBtn.setOnClickListener(this);
        nextBtn1.setOnClickListener(this);
        nextBtn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start://开启主界面

                if (FROM_SETTING.equals(fromActivity)){
                    IntentUtils.newIntent(this)
                            .finishActivity();
                    return;
                }

                SharePreferencesUtils.newPutTask().put("isFirst", "false").commit();

                IntentUtils.newIntent(this)
                        .setActivityClass(MainActivity.class)
                        .startActivity()
                        .finishActivity();

                break;
            case R.id.tv_next_1:
            case R.id.tv_next_2:

                parallaxContainer.nextPager();

                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (!parallaxContainer.prePager()) {
            super.onBackPressed();
        }
    }
}
