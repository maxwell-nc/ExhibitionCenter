package graduation.project.exhibition.activity.setting;

import graduation.project.exhibition.R;
import graduation.project.exhibition.activity.base.BackActivity;

/**
 * 关于此应用界面
 */
public class AboutActivity extends BackActivity {

    @Override
    protected int setViewId() {
        return R.layout.activity_about;
    }

    @Override
    protected String initBackBar() {
        return "关于此应用";
    }

}
