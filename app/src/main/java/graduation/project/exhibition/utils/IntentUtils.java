package graduation.project.exhibition.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;

/**
 * 意图工具类
 */
public class IntentUtils {

    private Builder builder;

    public IntentUtils(Builder builder) {
        this.builder = builder;
    }

    /**
     * 带结果显式启动Activity
     */
    public void startActivity() {
        Intent intent = new Intent(builder.context, builder.clazz);

        if (builder.bundle != null) {
            intent.putExtras(builder.bundle);
        }

        if (builder.requestCode != null && builder.context instanceof Activity) {
            ((Activity) builder.context).startActivityForResult(intent, builder.requestCode);
        } else {
            builder.context.startActivity(intent);
        }

        setActivityAnimation();
    }


    /**
     * 关闭当前Activity
     */
    public void finishActivity() {
        if (builder.context instanceof Activity) {
            ((Activity) builder.context).finish();
            setActivityAnimation();
        }
    }

    private void setActivityAnimation() {
        setActivityAnimation(builder.context);
    }

    /**
     * 设置Activity切换动画
     */
    public static void setActivityAnimation(Context context) {
        //动画
        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    public static Builder newIntent(Context context) {
        return new Builder(context);
    }

    public static class Builder {

        IntentUtils mIntentUtils;
        Context context;
        Class<?> clazz;
        Integer requestCode;//使用对象类型，判断是否已经赋值
        Bundle bundle;

        /**
         * 构建，即使不调用也会自动调用
         */
        public Builder build() {
            if (mIntentUtils == null) {
                mIntentUtils = new IntentUtils(this);
            }
            return this;
        }

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setActivityClass(Class<?> clazz) {
            this.clazz = clazz;
            return this;
        }

        public Builder bundle(Bundle bundle) {
            if (this.bundle == null) {
                this.bundle = new Bundle();
            }
            this.bundle.putAll(bundle);
            return this;
        }

        public Builder putExtra(String key, String value) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putString(key, value);
            return this;
        }

        public Builder putExtra(String key, Serializable value) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putSerializable(key, value);
            return this;
        }

        public Builder requestCode(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        /**
         * 关闭Actiivity
         */
        public void finishActivity() {
            build();
            mIntentUtils.finishActivity();
        }

        /**
         * 启动Actiivity
         */
        public Builder startActivity() {
            build();
            mIntentUtils.startActivity();
            return this;
        }
    }

}
