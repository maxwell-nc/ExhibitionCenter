package graduation.project.exhibition.ui.viewpager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.LinkedHashMap;
import java.util.Map;

import graduation.project.exhibition.utils.DimenUtils;

/**
 * ViewPager带图标的指示器
 */
public class IconIndicator extends RadioGroup {

    private LinkedHashMap<String, Integer[]> tabMap = new LinkedHashMap<>();
    private Context mContext;
    private ViewPager viewPager;
    private float iconHeight;
    private float iconWidth;
    private float textSize;
    private int normalColor;
    private int checkColor;
    public float leftMargin;
    public float topMargin;
    public float rightMargin;
    public float bottomMargin;
    private float drawablePadding;

    public IconIndicator(Context context) {
        super(context);
        init(context);
    }

    public IconIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
    }

    /**
     * 绑定ViewPager
     */
    public IconIndicator bind(ViewPager viewPager) {
        this.viewPager = viewPager;
        return this;
    }

    /**
     * 设置正常和选中的文字颜色
     */
    public IconIndicator textColor(int normalColor, int checkColor) {
        this.normalColor = normalColor;
        this.checkColor = checkColor;
        return this;
    }

    /**
     * 设置图标大小（dp）
     */
    public IconIndicator iconSize(float iconHeight,float iconWidth) {
        this.iconHeight = iconHeight;
        this.iconWidth = iconWidth;
        return this;
    }

    /**
     * 设置文字大小（sp）
     */
    public IconIndicator textSize(float textSize) {
        this.textSize = textSize;
        return this;
    }

    /**
     * 添加一个按钮，设置文本，正常和选中的图标id
     */
    public IconIndicator addTab(String text, int normalId, int checkId) {
        if (tabMap.containsKey(text)){
            throw new RuntimeException("按钮名字不能相同！");
        }
        tabMap.put(text, new Integer[]{normalId, checkId});
        return this;
    }

    /**
     * 默认配置
     */
    public IconIndicator defaultSetting() {
        textSize = 14;//sp
        iconHeight = 30;//dp
        iconWidth = 30;//dp
        drawablePadding = 0;//dp
        topMargin = 5;//dp
        bottomMargin = 3;//dp
        return this;
    }

    /**
     * 设置外边距（dp）
     */
    public IconIndicator margins(float left, float top, float right, float bottom) {
        leftMargin = left;
        topMargin = top;
        rightMargin = right;
        bottomMargin = bottom;
        return this;
    }

    /**
     * 设置图标内边距（dp）
     */
    public IconIndicator drawablePadding(float drawablePadding) {
        this.drawablePadding = drawablePadding;
        return this;
    }

    /**
     * 加载按钮
     */
    @SuppressWarnings("deprecation")
    public void load() {

        this.setOrientation(HORIZONTAL);

        viewPager.addOnPageChangeListener(new IndicatorOnPageChangeListener(this));

        int position = 0;
        for (Map.Entry<String, Integer[]> e : tabMap.entrySet()) {

            RadioButton radioButton = new RadioButton(mContext);
            LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
            params.setMargins(DimenUtils.dp2px(mContext, leftMargin),
                    DimenUtils.dp2px(mContext, topMargin),
                    DimenUtils.dp2px(mContext, rightMargin),
                    DimenUtils.dp2px(mContext, bottomMargin));
            params.weight = 1;
            radioButton.setLayoutParams(params);

            radioButton.setGravity(Gravity.CENTER_HORIZONTAL);

            radioButton.setText(e.getKey());
            radioButton.setTextSize(textSize);
            radioButton.setTextColor(mContext.getResources().getColor(normalColor));

            final Integer[] drawableResIds = e.getValue();

            radioButton.setButtonDrawable(getResources().getDrawable(android.R.color.transparent));
            radioButton.setCompoundDrawablePadding(DimenUtils.dp2px(mContext, drawablePadding));

            setDrawableTop(radioButton, drawableResIds[0]);

            radioButton.setTag(position);//记录位置
            radioButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem((Integer) v.getTag(), false);
                }
            });

            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    buttonView.setTextColor(mContext.getResources().getColor(isChecked ? checkColor : normalColor));
                    setDrawableTop(buttonView, drawableResIds[isChecked ? 1 : 0]);
                }
            });

            addView(radioButton);
            position++;
        }

        //选中当前的
        check(getChildAt(viewPager.getCurrentItem()).getId());
    }

    /**
     * 设置图标
     */
    @SuppressWarnings("deprecation")
    private void setDrawableTop(CompoundButton radioButton, Integer drawableResId) {
        Drawable drawable = mContext.getResources().getDrawable(drawableResId);
        drawable.setBounds(0, 0, DimenUtils.dp2px(mContext, iconWidth), DimenUtils.dp2px(mContext, iconHeight));
        radioButton.setCompoundDrawables(null, drawable, null, null);
    }

    private class IndicatorOnPageChangeListener implements ViewPager.OnPageChangeListener {

        private final IconIndicator mIconIndicator;

        public IndicatorOnPageChangeListener(IconIndicator iconIndicator) {
            this.mIconIndicator = iconIndicator;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            //选中图标
            mIconIndicator.check(mIconIndicator.getChildAt(position).getId());
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    public void select(int position) {
        check(getChildAt(position).getId());
        viewPager.setCurrentItem(position);
    }
}
