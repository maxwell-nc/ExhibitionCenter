package graduation.project.exhibition.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import graduation.project.exhibition.R;
import graduation.project.exhibition.utils.PhoneInfoUtils;

/**
 * 主题选择对话框
 */
public class ThemeSelectDialog extends Dialog {

    private Builder mBuilder;
    //private ArrayList<ImageView> radioBtnList;

    private ThemeSelectDialog(Builder builder) {
        super(builder.context, R.style.dialog);
        this.mBuilder = builder;
        initDialog();
    }

    public static Builder newDialog(Context context) {
        return new Builder(context);
    }

    private void initDialog() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_select);

        Window window = getWindow();
        window.setWindowAnimations(R.style.anim_dialog_style);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = 0;
        //wl.width = WindowManager.LayoutParams.MATCH_PARENT;
        wl.width = PhoneInfoUtils.getScreenWidth(mBuilder.context)/5*4;
        wl.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wl.alpha = 1.0f;
        window.setAttributes(wl);

        //设置标题
        TextView title = (TextView) findViewById(R.id.tv_title);
        if (mBuilder.title != null) {
            title.setText(mBuilder.title);
        }

        LinearLayout items = (LinearLayout) findViewById(R.id.ll_items);
        //if (radioBtnList == null) {
        //    radioBtnList = new ArrayList<>();
        //}
        //添加item
        int i = 0;
        for (final Map.Entry<String, ButtonClickListener> item : mBuilder.itemList.entrySet()) {
            View itemView = View.inflate(mBuilder.context, R.layout.item_dialog_select, null);
            TextView itemName = (TextView) itemView.findViewById(R.id.tv_item_name);
            itemName.setText(item.getKey());

            final ImageView radioBtn = (ImageView) itemView.findViewById(R.id.iv_radio);
            if (i == mBuilder.checkIndex) {
                radioBtn.setImageResource(R.mipmap.img_radio_button_on);
            }
            //radioBtnList.add(radioBtn);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //由于点击了立马消失，所以不需要显示那个被选中
                    //for (ImageView view : radioBtnList) {//取消选择所有
                    //    view.setImageResource(R.mipmap.img_radio_button);
                    //}
                    //radioBtn.setImageResource(R.mipmap.img_radio_button_on);
                    item.getValue().onClick();
                    dismiss();
                }
            });

            items.addView(itemView);
            i++;
        }
    }


    public static class Builder {
        Context context;
        String title;
        int checkIndex;
        LinkedHashMap<String, ButtonClickListener> itemList;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder addItem(String itemName, ButtonClickListener listener) {
            if (itemList == null) {
                itemList = new LinkedHashMap<>();
            }
            if (listener == null || itemList.containsKey(itemName)) {
                return this;
            }
            this.itemList.put(itemName, listener);
            return this;
        }

        public void show(int checkIndex) {
            this.checkIndex = checkIndex;
            new ThemeSelectDialog(this).show();
        }

    }


    public interface ButtonClickListener {
        /**
         * 点击事件
         */
        void onClick();
    }

}
