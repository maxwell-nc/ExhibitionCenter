package graduation.project.exhibition.activity.base;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import graduation.project.exhibition.R;

/**
 * 带标题栏的Activity
 */
public abstract class TitleBarActivity extends BaseActivity {

    protected LinearLayout toolbarLayout;
    protected TextView titleText;
    protected ImageView leftBtn;
    protected ImageView rightBtn;
    private View spanView;//占位置使用的

    /**
     * 设置显示的内容
     */
    @Override
    protected void setView() {
        LinearLayout baseView = (LinearLayout) View.inflate(this, R.layout.activity_base_title, null);
        setTranslucentBar(R.color.main_theme, baseView);

        ViewStub contentStub = (ViewStub) baseView.findViewById(R.id.vs_content);
        contentStub.setLayoutResource(setViewId());
        contentStub.inflate();

        setContentView(baseView);

        initTitleBar();
    }

    /**
     * 初始化标题栏
     */
    protected void initTitleBar() {
        toolbarLayout = (LinearLayout) findViewById(R.id.ll_toolbar);
        titleText = (TextView) toolbarLayout.findViewById(R.id.tv_title);
        spanView = toolbarLayout.findViewById(R.id.v_span);
        leftBtn = (ImageView) toolbarLayout.findViewById(R.id.iv_left);
        rightBtn = (ImageView) toolbarLayout.findViewById(R.id.iv_right);
    }

    /**
     * 设置标题
     */
    private void setTitle(@NonNull String title) {
        toolbarLayout.setVisibility(View.VISIBLE);
        titleText.setText(title);
    }

    /**
     * 设置左按钮
     */
    public void setLeftBtn(@DrawableRes int resId) {
        leftBtn.setVisibility(View.VISIBLE);
        spanView.setVisibility(View.GONE);
        leftBtn.setImageResource(resId);
    }

    /**
     * 设置右按钮
     */
    public void setRightBtn(@DrawableRes int resId) {
        rightBtn.setVisibility(View.VISIBLE);
        rightBtn.setImageResource(resId);
    }

    /**
     * 设置工具条
     */
    public void setToolbar(@NonNull String title, @DrawableRes int leftResId, @DrawableRes int rightResId) {
        setTitle(title);
        setLeftBtn(leftResId);
        setRightBtn(rightResId);
    }

    /**
     * 设置工具条
     */
    public void setToolbar(@NonNull String title) {
        setTitle(title);
        leftBtn.setVisibility(View.GONE);
        spanView.setVisibility(View.VISIBLE);
        rightBtn.setVisibility(View.GONE);
    }

    /**
     * 隐藏工具条
     */
    public void hideToolbar() {
        toolbarLayout.setVisibility(View.GONE);
    }

    /**
     * 设置点击监听器
     */
    public void setListener(@NonNull final SimpleClickListener listener) {
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.leftClick();
            }
        });
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.rightClick();
            }
        });
    }

    /**
     * 简单点击事件监听器
     */
    public static class SimpleClickListener {
        public void leftClick() {
        }

        public void rightClick() {
        }
    }
}
