package graduation.project.exhibition.activity.base;

import graduation.project.exhibition.R;

/**
 * 后退按钮Activity
 */
public abstract class BackActivity extends TitleBarActivity {

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        setToolbar(initBackBar());
        setLeftBtn(R.mipmap.img_back);
        setListener(new SimpleClickListener() {
            @Override
            public void leftClick() {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initView() {}

    @Override
    protected void initData() {}

    /**
     * 获取标题文本
     */
    protected abstract String initBackBar();
}