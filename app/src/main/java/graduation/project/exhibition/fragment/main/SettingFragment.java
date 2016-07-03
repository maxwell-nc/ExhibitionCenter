package graduation.project.exhibition.fragment.main;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;

import cn.pedant.SweetAlert.SweetAlertDialog;
import graduation.project.exhibition.R;
import graduation.project.exhibition.activity.setting.AboutActivity;
import graduation.project.exhibition.activity.setting.CheckNetworkActivity;
import graduation.project.exhibition.activity.setting.FeedbackActivity;
import graduation.project.exhibition.activity.setting.ShareActivity;
import graduation.project.exhibition.activity.setting.UserActivity;
import graduation.project.exhibition.business.ImageUtil;
import graduation.project.exhibition.business.UpdateBusiness;
import graduation.project.exhibition.fragment.BaseFragment;
import graduation.project.exhibition.ui.ThemeSelectDialog;
import graduation.project.exhibition.utils.IntentUtils;
import graduation.project.exhibition.utils.MethodUtils;
import graduation.project.exhibition.utils.SharePreferencesUtils;
import graduation.project.exhibition.utils.ThreadQueue;
import other.activity.WelcomeActivity;
import other.ui.ToggleButton;
import pres.nc.maxwell.asynchttp.HttpConnector;

/**
 * 设置Fragment
 */
public class SettingFragment extends BaseFragment implements View.OnClickListener {

    private static final int REQUEST_CODE_FROM_SETTING = 0;

    private SwipeRefreshLayout refreshLayout;
    private View btnExit;
    private View btnWelcome;
    private View btnAbout;
    private View btnNext;
    private View btnImgQual;
    private TextView imgQualText;
    private ToggleButton btnNoImg;
    private View btnCleanCache;
    private View redPoint;
    private TextView UserIdText;
    private TextView UserNameText;
    private View btnShare;
    private View btnUpdate;
    private View btnFeedback;
    private View btnEditName;

    private static final String imgQualKey = "img_quality";
    private static final String noImgKey = "no_img";

    private String imgQuality;//图片质量

    public SettingFragment() {
        super(R.layout.fragment_setting);
    }

    @Override
    public void initView() {
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_refresh);
        refreshLayout.setColorSchemeResources(R.color.main_theme);

        btnExit = findViewById(R.id.ll_exit);
        btnWelcome = findViewById(R.id.ll_welcome);
        btnAbout = findViewById(R.id.ll_about);
        btnNext = findViewById(R.id.ll_network);
        btnImgQual = findViewById(R.id.ll_img_quality);
        imgQualText = (TextView) findViewById(R.id.tv_img_quality);
        btnNoImg = (ToggleButton) findViewById(R.id.tb_no_img);
        btnCleanCache = findViewById(R.id.ll_clean_cahe);
        redPoint = findViewById(R.id.iv_red_point);
        UserIdText = (TextView) findViewById(R.id.tv_user_id);
        UserNameText = (TextView) findViewById(R.id.tv_username);
        btnShare = findViewById(R.id.ll_share);
        btnUpdate = findViewById(R.id.ll_update);
        btnFeedback = findViewById(R.id.ll_feedback);
        btnEditName = findViewById(R.id.iv_edit_name);
    }

    @Override
    public void initData() {
        //刷新操作
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ThreadQueue.newQueue(attachActivity).sleep(2000).onUiThread(new ThreadQueue.Task() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                    }
                }).exec();
            }
        });

        //显示图片质量
        imgQuality = SharePreferencesUtils.newGetTask().get(imgQualKey, "0");
        switchQuality(imgQuality, false);


        //显示无图模式
        String noImg = SharePreferencesUtils.newGetTask()
                .get(noImgKey, "off");
        if ("on".equals(noImg)) {
            btnNoImg.setToggleOn();
        }
        btnNoImg.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                String noImg = "off";
                if (on) {
                    noImg = "on";
                }
                SharePreferencesUtils.newPutTask().put(noImgKey, noImg).commit();
            }
        });

        //显示设备码
        UserIdText.setText(MethodUtils.getDrvHashCode());
        showUsername();


        btnExit.setOnClickListener(this);
        btnWelcome.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnImgQual.setOnClickListener(this);
        btnCleanCache.setOnClickListener(this);
        btnShare.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnFeedback.setOnClickListener(this);
        btnEditName.setOnClickListener(this);
    }

    /**
     * 显示用户名
     */
    public void showUsername() {
        UserNameText.setText(MethodUtils.getUserName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_exit://退出
                MethodUtils.exit(v.getContext());
                break;
            case R.id.ll_welcome://欢迎页
                IntentUtils.newIntent(attachActivity)
                        .setActivityClass(WelcomeActivity.class)
                        .requestCode(REQUEST_CODE_FROM_SETTING)
                        .putExtra("from", "Setting")
                        .startActivity();
                break;
            case R.id.ll_about://关于页
                IntentUtils.newIntent(attachActivity)
                        .setActivityClass(AboutActivity.class)
                        .startActivity();
                break;
            case R.id.ll_network://诊断网络
                IntentUtils.newIntent(attachActivity)
                        .setActivityClass(CheckNetworkActivity.class)
                        .startActivity();
                break;
            case R.id.ll_img_quality://图片质量
                ThemeSelectDialog.newDialog(attachActivity)
                        .title("请选择网络图片质量")
                        .addItem("普通", new ThemeSelectDialog.ButtonClickListener() {
                            @Override
                            public void onClick() {
                                switchQuality("0", true);
                            }
                        })
                        .addItem("高清", new ThemeSelectDialog.ButtonClickListener() {
                            @Override
                            public void onClick() {
                                switchQuality("1", true);
                            }
                        })
                        .show(Integer.parseInt(imgQuality));
                break;
            case R.id.ll_clean_cahe://清理缓存
                if (redPoint.getVisibility() == View.INVISIBLE) {
                    new SweetAlertDialog(attachActivity, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("提示")
                            .setContentText("已经清理过缓存文件了，不需要再清理！")
                            .setConfirmText("知道了!")
                            .setConfirmClickListener(null)
                            .show();
                    return;
                }
                new SweetAlertDialog(attachActivity, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("是否确认删除缓存文件?")
                        .setContentText("删除缓存文件可以减少SD卡存储空间，但加载速度会变慢并且消耗更多的流量!")
                        .setCancelText("取消")
                        .setConfirmText("删除")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                sDialog.setTitleText("提示")
                                        .setContentText("缓存文件没有清理！")
                                        .setConfirmText("知道了！")
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);

                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.setTitleText("提示")
                                        .setContentText("缓存文件清理成功！")
                                        .setConfirmText("知道了！")
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                redPoint.setVisibility(View.INVISIBLE);
                                //删除网络数据缓存和图片缓存
                                HttpConnector.clearCache(attachActivity);
                                ImageUtil.clearCache(attachActivity);
                            }
                        })
                        .show();
                break;
            case R.id.ll_share://推荐分享
                IntentUtils.newIntent(attachActivity)
                        .setActivityClass(ShareActivity.class)
                        .startActivity();
                break;
            case R.id.ll_update://更新
                UpdateBusiness.toUpdate(attachActivity);
                break;
            case R.id.ll_feedback://反馈
                IntentUtils.newIntent(attachActivity)
                        .setActivityClass(FeedbackActivity.class)
                        .startActivity();
                break;
            case R.id.iv_edit_name://修改用户名
                IntentUtils.newIntent(attachActivity)
                        .setActivityClass(UserActivity.class)
                        .requestCode(0x100)
                        .startActivity();
                break;
        }
    }

    /**
     * 切换图片质量
     */
    private void switchQuality(String qulity, boolean isWrite) {
        if (isWrite) {
            SharePreferencesUtils.newPutTask().put(imgQualKey, qulity).commit();
        }
        imgQuality = qulity;//记录
        if ("0".equals(qulity)) {
            imgQualText.setText("普通");
        } else {
            imgQualText.setText("高清");
        }
    }


}
