package graduation.project.exhibition.activity.info;

import android.app.AlertDialog;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meetic.dragueur.Direction;
import com.meetic.dragueur.DraggableView;
import com.meetic.shuffle.Shuffle;

import java.util.ArrayList;

import graduation.project.exhibition.R;
import graduation.project.exhibition.activity.base.BaseActivity;
import graduation.project.exhibition.business.HttpRequest;
import graduation.project.exhibition.business.ImageUtil;
import graduation.project.exhibition.domain.NewsDetail;
import graduation.project.exhibition.http.JsonCallback;
import graduation.project.exhibition.http.ListResponse;
import graduation.project.exhibition.utils.DimenUtils;
import graduation.project.exhibition.utils.MethodUtils;
import other.ui.MainThemeOnClickDialog;
import pres.nc.maxwell.asynchttp.response.Response;

/**
 * 新闻详情
 */
public class NewsDetailActivity extends BaseActivity {

    private Shuffle picsBanner;
    private TextView contentText;
    private ImageView btnTextSize;
    private ImageView btnFullScreen;
    private RelativeLayout toolbarLayout;
    private TextView title;
    private TextView source;
    private TextView link;
    private ShuffleAdapter shuffleAdapter;

    @Override
    protected int setViewId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initView() {

        setTranslucentBar(R.color.main_theme,R.id.ll_root);

        picsBanner = (Shuffle) findViewById(R.id.shuffle);
        btnTextSize = (ImageView) findViewById(R.id.iv_text_size);
        btnFullScreen = (ImageView) findViewById(R.id.iv_fullscreen);
        toolbarLayout = (RelativeLayout) findViewById(R.id.rl_bar);

        title = (TextView) findViewById(R.id.tv_title);
        source = (TextView) findViewById(R.id.tv_source_time);
        link = (TextView) findViewById(R.id.tv_link);
        contentText = (TextView) findViewById(R.id.tv_content);
    }

    @Override
    protected void initData() {

        String id = getIntent().getStringExtra("id");
        requestData(id);

        picsBanner.getParent().requestDisallowInterceptTouchEvent(true);
        picsBanner.getParent().getParent().requestDisallowInterceptTouchEvent(true);

        picsBanner.addListener(new Shuffle.Listener() {
            @Override
            public void onViewChanged(int position) {
                if (position == 3) {
                    picsBanner.restartShuffling();
                }
            }

            @Override
            public void onScrollStarted() {
            }

            @Override
            public void onScrollFinished() {
            }

            @Override
            public void onViewExited(DraggableView draggableView, Direction direction) {
            }

            @Override
            public void onViewScrolled(DraggableView draggableView, float percentX, float percentY) {
            }
        });
        shuffleAdapter = new ShuffleAdapter();
        picsBanner.setShuffleAdapter(shuffleAdapter);
        btnTextSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainThemeOnClickDialog dialog = new MainThemeOnClickDialog(mActivity, new MainThemeOnClickDialog.DialogDataAdapter() {

                    @Override
                    public int[] getItemNames() {
                        return new int[]{R.string.little_text_size, R.string.middle_text_size, R.string.large_text_size};
                    }

                    @Override
                    public View.OnClickListener[] getItemOnClickListeners(final AlertDialog alertDialog) {
                        return new View.OnClickListener[]{new View.OnClickListener() {

                            @Override
                            public void onClick(View v1) {// 小号字体14sp
                                contentText.setTextSize(14);
                                alertDialog.dismiss();
                            }
                        }, new View.OnClickListener() {// 中号字体24sp

                            @Override
                            public void onClick(View v1) {
                                contentText.setTextSize(24);
                                alertDialog.dismiss();
                            }
                        }, new View.OnClickListener() {// 大号字体32sp

                            @Override
                            public void onClick(View v1) {
                                contentText.setTextSize(32);
                                alertDialog.dismiss();
                            }
                        }};
                    }

                });

                // 添加手动修改大小的View
                dialog.setExtraCustomViewAdapter(new MainThemeOnClickDialog.ExtraCustomViewAdapter() {

                    public TextView sizeText;

                    /**
                     * 显示TextView字体大小
                     */
                    public int showTextViewTextSize(int increase) {
                        int spSize = DimenUtils.px2sp(mActivity, contentText.getTextSize()) + increase;// 转换为sp
                        sizeText.setText(spSize + "sp");
                        return spSize;
                    }

                    @Override
                    public View getExtraCustomFooterView() {

                        // 添加手动设置的字体View
                        LinearLayout view = (LinearLayout) View.inflate(mActivity, R.layout.view_extra_custom_footer_text_size, null);

                        ImageView addBtn = (ImageView) view.findViewById(R.id.iv_add);
                        ImageView descBtn = (ImageView) view.findViewById(R.id.iv_desc);
                        sizeText = (TextView) view.findViewById(R.id.tv_size);

                        // 显示当前的字体大小
                        showTextViewTextSize(0);

                        addBtn.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                //转来转去为了防止系统字体改变而出现放大反而更小的BUG
                                int pxSize = DimenUtils.sp2px(mActivity, showTextViewTextSize(1));
                                contentText.setTextSize(TypedValue.COMPLEX_UNIT_PX, pxSize);
                            }

                        });

                        descBtn.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                int pxSize = DimenUtils.sp2px(mActivity, showTextViewTextSize(-1));
                                contentText.setTextSize(TypedValue.COMPLEX_UNIT_PX, pxSize);
                            }

                        });

                        return view;

                    }

                });

                dialog.show();
            }
        });
        btnFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTranslucentBar(R.color.deep_red);
                toolbarLayout.setVisibility(View.GONE);
            }
        });
    }

    private void requestData(String id) {
        HttpRequest.jsonRequest("infonews_details?id=" + id, new JsonCallback<ListResponse<NewsDetail>>() {

            @Override
            public void onSuccess(Response<ListResponse<NewsDetail>> response) {
                super.onSuccess(response);
                NewsDetail newsDetail = response.getResponseData().getResponse().get(0);

                title.setText(newsDetail.getTitle());
                source.setText(newsDetail.getTime());
                link.setText(newsDetail.getLink());

                contentText.setText(MethodUtils.replaceLineAndAddSpace(newsDetail.getText()));

                ArrayList<String> list = new ArrayList<>();
                list.add(newsDetail.getPic1());
                list.add(newsDetail.getPic2());
                list.add(newsDetail.getPic3());

                shuffleAdapter.setData(list);
            }

        });
    }

    @Override
    public void onBackPressed() {

        // 检查是否全屏，是则退出全屏不退出Activity
        if (toolbarLayout != null) {
            if (toolbarLayout.getVisibility() == View.GONE) {
                toolbarLayout.setVisibility(View.VISIBLE);
                setTranslucentBar(R.color.main_theme);
                return;
            }
        }

        super.onBackPressed();
    }

    private static class ShuffleAdapter extends Shuffle.Adapter<ShuffleAdapter.ViewHolder> {
        private ArrayList<String> list;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_shuffle_item, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            ImageUtil.loadWebImage(viewHolder.imageView, list.get(position));
        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }

        public void setData(ArrayList<String> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        static class ViewHolder extends Shuffle.ViewHolder {

            ImageView imageView;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.iv_image);
            }
        }
    }
}
