package other.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import graduation.project.exhibition.R;

/**
 * 主题风格的长按弹出的对话框
 */
public class MainThemeOnClickDialog {

    /**
     * 依附的Activity
     */
    private Activity mActivity;

    /**
     * 基本数据适配器
     */
    private DialogDataAdapter mAdapter;

    /**
     * 额外的自定义View数据适配器
     */
    private ExtraCustomViewAdapter mCustomViewAdapter;

    /**
     * 绘制条目的监听器
     */
    private OnDrawItemsListener onDrawItemsListener;

    /**
     * 创建新的对话框，必须手动调用show()方法才显示
     *
     * @param activity 依附的Activity
     * @param adapter  数据适配器
     * @see #show()
     */
    public MainThemeOnClickDialog(Activity activity, DialogDataAdapter adapter) {
        this.mActivity = activity;
        this.mAdapter = adapter;
    }

    /**
     * 设置绘制条目监听器
     *
     * @param listener 监听器
     */
    public void setOnDrawItemListener(OnDrawItemsListener listener) {
        this.onDrawItemsListener = listener;
    }

    /**
     * 设置额外的自定义View数据适配器
     *
     * @param adapter 数据适配器
     */
    public void setExtraCustomViewAdapter(ExtraCustomViewAdapter adapter) {
        this.mCustomViewAdapter = adapter;
    }

    /**
     * 显示对话框
     */
    public void show() {

        // 整个警告框的布局
        LinearLayout wrapper = new LinearLayout(mActivity);
        wrapper.setOrientation(LinearLayout.VERTICAL);
        wrapper.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        // 获取布局填充器
        LayoutInflater inflater = mActivity.getLayoutInflater();

        ArrayList<TextView> items = new ArrayList<TextView>();

        // 生成TextView和分割线View
        int itemCount = mAdapter.getItemNames().length;
        for (int i = 0; i < itemCount; i++) {

            View view = inflater.inflate(R.layout.view_main_theme_onclick_dialog_text, wrapper, false);
            TextView item = (TextView) view.findViewById(R.id.tv_text);
            item.setText(mActivity.getString(mAdapter.getItemNames()[i]));

            // 检查监听器
            if (onDrawItemsListener != null) {
                onDrawItemsListener.onDrawItems(item);
            }

            items.add(item);// 添加到TextView集合
            wrapper.addView(view);

            // 最后一个不添加下划线
            if (i != itemCount - 1
                    || (mCustomViewAdapter != null && i == itemCount - 1)) {
                View hrLine = inflater.inflate(R.layout.view_main_theme_onclick_dialog_hr_line, wrapper, false);

                wrapper.addView(hrLine);
            }

        }

        // 添加底部自定义view
        if (mCustomViewAdapter != null) {
            wrapper.addView(mCustomViewAdapter.getExtraCustomFooterView());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);

        final AlertDialog alertDialog = builder.create();
        alertDialog.setView(wrapper, 0, 0, 0, 0);

        // 设置点击事件
        OnClickListener[] listeners = mAdapter
                .getItemOnClickListeners(alertDialog);
        for (int i = 0; i < listeners.length; i++) {
            items.get(i).setOnClickListener(listeners[i]);
        }

        alertDialog.show();
    }

    /**
     * 额外的自定义View数据适配器
     */
    public interface ExtraCustomViewAdapter {

        /**
         * 获取额外的自定义View,添加在尾部
         *
         * @return 自定义的View
         */
        public View getExtraCustomFooterView();
    }

    /**
     * 绘制条目的监听器
     */
    public interface OnDrawItemsListener {

        /**
         * 绘制条目中
         *
         * @param item 正在绘制的TextView
         */
        public void onDrawItems(TextView item);
    }

    /**
     * 数据适配器
     */
    public interface DialogDataAdapter {

        /**
         * 获得条目的名字数组
         *
         * @return 条目的名字数组
         */
        public int[] getItemNames();

        /**
         * 获取每个条目的点击监听器数组
         *
         * @param alertDialog 当前显示的对话框
         * @return 每个条目的点击监听器数组
         */
        public OnClickListener[] getItemOnClickListeners(AlertDialog alertDialog);

    }

    /**
     * 传入长按弹出的条目位置和当前对话框的监听器
     */
    public static abstract class AlertDialogOnClickListener
            implements
            OnClickListener {

        /**
         * 点击的位置（ListView中的位置）
         */
        protected int position;

        /**
         * 对话框
         */
        protected AlertDialog alertDialog;

        /**
         * 传入长按弹出的条目位置和当前对话框
         *
         * @param position    长按弹出的条目位置
         * @param alertDialog 当前对话框
         */
        public AlertDialogOnClickListener(int position, AlertDialog alertDialog) {
            this.position = position;
            this.alertDialog = alertDialog;
        }

        /**
         * 默认取消对话框
         */
        @Override
        public void onClick(View v) {
            alertDialog.dismiss();
        }

    }

    /**
     * 默认的图片点击监听器
     */
    public static abstract class ImageClickListener implements OnClickListener {

        /**
         * 消息对话框
         */
        protected final AlertDialog alertDialog;

        /**
         * 图片链接
         */
        protected final String imgLink;

        /**
         * 初始化
         *
         * @param alertDialog 消息对话框
         * @param imgLink     图片链接
         */
        protected ImageClickListener(AlertDialog alertDialog, String imgLink) {
            this.alertDialog = alertDialog;
            this.imgLink = imgLink;
        }

        /**
         * 默认取消对话框
         */
        @Override
        public void onClick(View v) {
            alertDialog.dismiss();
        }

    }

}
