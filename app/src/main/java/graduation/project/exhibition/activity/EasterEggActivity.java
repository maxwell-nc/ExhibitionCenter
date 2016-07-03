package graduation.project.exhibition.activity;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;

import graduation.project.exhibition.R;
import graduation.project.exhibition.activity.base.BackActivity;
import graduation.project.exhibition.activity.base.TitleBarActivity;
import graduation.project.exhibition.utils.MethodUtils;
import graduation.project.exhibition.utils.ThreadQueue;
import tyrantgit.explosionfield.ExplosionField;

/**
 * 彩蛋页面
 */
public class EasterEggActivity extends BackActivity implements View.OnClickListener {

    private ImageView mEggView;
    private ExplosionField mExplosionField;
    private int[] eggs = {R.mipmap.egg_1, R.mipmap.egg_2, R.mipmap.egg_3, R.mipmap.egg_4, R.mipmap.egg_5,
            R.mipmap.egg_6, R.mipmap.egg_7, R.mipmap.egg_8, R.mipmap.egg_9, R.mipmap.egg_10, R.mipmap.egg_11};


    @Override
    protected int setViewId() {
        return R.layout.activity_easter_egg;
    }

    @Override
    protected void initView() {
        mEggView = (ImageView) findViewById(R.id.iv_egg);
    }

    @Override
    protected void initData() {
        mExplosionField = ExplosionField.attach2Window(this);
        mEggView.setOnClickListener(this);
    }

    @Override
    protected String initBackBar() {
        return "彩蛋";
    }

    @Override
    public void onClick(final View view) {

        mEggView.setOnClickListener(null);
        mExplosionField.explode(view);

        ThreadQueue.newQueue(this)
                .sleep(800)
                .onUiThread(new ThreadQueue.Task() {
                    @Override
                    public void run() {

                        int randomInt = MethodUtils.randomInt(11);
                        ((ImageView) view).setImageResource(eggs[randomInt]);

                        //reset
                        view.setScaleX(1);
                        view.setScaleY(1);
                        view.setAlpha(1);

                        mExplosionField.clear();

                        mEggView.setOnClickListener(EasterEggActivity.this);
                    }

                }).exec();
    }
}
