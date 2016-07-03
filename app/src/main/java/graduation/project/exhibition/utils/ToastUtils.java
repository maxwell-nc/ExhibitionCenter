package graduation.project.exhibition.utils;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import graduation.project.exhibition.AppApplication;
import graduation.project.exhibition.R;

/**
 * 吐司工具类
 */
public class ToastUtils {

    private static Toast mToast;

    private static Handler mHandler = new Handler();
    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
            mToast = null;//toast隐藏后，将其置为null
        }
    };

    /**
     * 显示吐司
     */
    public static void toast(String msg) {

        int length = Toast.LENGTH_SHORT;
        if (msg.length() > 20) {
            length = Toast.LENGTH_LONG;
        }

        Context context = AppApplication.getAppContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.toast_custom, null);//自定义布局
        TextView text = (TextView) view.findViewById(R.id.toast_message);//显示的提示文字
        text.setText(msg);
        mHandler.removeCallbacks(r);
        if (mToast == null) {//只有mToast==null时才重新创建，否则只需更改提示文字
            mToast = new Toast(context);
            mToast.setDuration(length);
            mToast.setGravity(Gravity.BOTTOM, 0, 150);
            mToast.setView(view);
        }

        int times = msg.length() * 50;
        mHandler.postDelayed(r, times < 1500 ? 1500 : times);//延迟隐藏toast
        mToast.show();
    }

}
