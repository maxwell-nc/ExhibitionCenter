package graduation.project.exhibition.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import graduation.project.exhibition.R;

/**
 * 主题确认对话框
 */
public class ThemeConfirmDialog extends Dialog implements View.OnClickListener {

    private Builder mBuilder;

    private ThemeConfirmDialog(Builder builder) {
        super(builder.context, R.style.dialog);
        this.mBuilder = builder;
        initDialog();
    }

    /**
     * 创建新的dialog
     * @param context 不要使用applicationContext
     */
    public static Builder newDialog(Context context) {
        return new Builder(context);
    }

    private void initDialog() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_common);

        Window window = getWindow();
        window.setWindowAnimations(R.style.anim_dialog_style);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = 0;
        wl.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wl.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wl.alpha = 1.0f;
        window.setAttributes(wl);

        //设置标题
        TextView title = (TextView) findViewById(R.id.tv_title);
        if (mBuilder.title != null) {
            title.setText(mBuilder.title);
        }

        //设置描述
        TextView desc = (TextView) findViewById(R.id.tv_desc);
        if (mBuilder.desc != null) {
            desc.setText(mBuilder.desc);
        }

        //设置左按钮
        TextView btnLeft = (TextView) findViewById(R.id.tv_left);
        if (mBuilder.leftText == null) {
            btnLeft.setVisibility(View.GONE);
        } else {
            btnLeft.setText(mBuilder.leftText);
            btnLeft.setOnClickListener(this);
        }

        //设置右按钮
        TextView btnRight = (TextView) findViewById(R.id.tv_right);
        if (mBuilder.rightText == null) {
            btnRight.setVisibility(View.GONE);
        } else {
            btnRight.setText(mBuilder.rightText);
            btnRight.setOnClickListener(this);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                if (mBuilder.leftClickListener != null) {
                    mBuilder.leftClickListener.onClick();
                }
                break;
            case R.id.tv_right:
                if (mBuilder.rightClickListener != null) {
                    mBuilder.rightClickListener.onClick();
                }
                break;
        }
        dismiss();
    }


    public static class Builder {
        Context context;
        ButtonClickListener leftClickListener;
        ButtonClickListener rightClickListener;
        String title;
        String desc;
        String leftText;
        String rightText;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder desc(String desc) {
            this.desc = desc;
            return this;
        }

        public Builder leftText(String leftText, ButtonClickListener leftClickListener) {
            this.leftText = leftText;
            this.leftClickListener = leftClickListener;
            return this;
        }

        public Builder rightText(String rightText, ButtonClickListener rightClickListener) {
            this.rightText = rightText;
            this.rightClickListener = rightClickListener;
            return this;
        }

        public void show() {
            new ThemeConfirmDialog(this).show();
        }

    }


    public interface ButtonClickListener {
        /**
         * 点击事件
         */
        void onClick();
    }

}




