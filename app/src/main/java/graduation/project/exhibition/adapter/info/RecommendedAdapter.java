package graduation.project.exhibition.adapter.info;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import graduation.project.exhibition.R;
import graduation.project.exhibition.activity.info.NewsDetailActivity;
import graduation.project.exhibition.activity.setting.AboutActivity;
import graduation.project.exhibition.business.ImageUtil;
import graduation.project.exhibition.domain.BigPic;
import graduation.project.exhibition.domain.RecInfo;
import graduation.project.exhibition.utils.IntentUtils;

/**
 * 推荐内容
 */
public class RecommendedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_HEAD = 0;
    private static final int VIEW_TYPE_CONTENT = 1;
    private List<View> views;
    private List<String> tips;
    private List<RecInfo> recInfos;

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEAD;
        }
        return VIEW_TYPE_CONTENT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_HEAD:
                View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info_recommended_banner, parent, false);
                return new HeaderHolder(headerView);
            case VIEW_TYPE_CONTENT:
                View contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info_recommended, parent, false);
                return new ContentHolder(contentView);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {

            final HeaderHolder headerHolder = (HeaderHolder) holder;
            if (views != null && tips != null) {
                headerHolder.banner.setViews(views);
                headerHolder.banner.setTips(tips);
            }
        } else {

            ContentHolder contentHolder = (ContentHolder) holder;
            if (recInfos != null) {
                final RecInfo recInfo = recInfos.get(position - 1);
                ImageUtil.loadWebImage(contentHolder.imageView, recInfo.getPic());
                contentHolder.title.setText(recInfo.getTitle());
                contentHolder.content.setText(recInfo.getText());
                contentHolder.praiseCount.setText(recInfo.getPraise());
                contentHolder.conmmentCount.setText(recInfo.getComment());

                contentHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentUtils.newIntent(v.getContext())
                                .setActivityClass(NewsDetailActivity.class)
                                .putExtra("id",recInfo.getId())
                                .startActivity();
                    }
                });
            }
        }

    }


    @Override
    public int getItemCount() {
        if (recInfos != null) {
            return recInfos.size() + 1;
        }
        return 1;
    }

    public void setHeaderData(List<View> views, List<String> tips) {
        this.views = views;
        this.tips = tips;
        notifyDataSetChanged();
    }

    public void setContentData(List<RecInfo> recInfos) {
        this.recInfos = recInfos;
        notifyDataSetChanged();
    }

    class HeaderHolder extends RecyclerView.ViewHolder {

        BGABanner banner;

        public HeaderHolder(View itemView) {
            super(itemView);

            banner = (BGABanner) itemView.findViewById(R.id.banner_splash_pager);
        }

    }


    class ContentHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        TextView content;
        TextView praiseCount;
        TextView conmmentCount;

        public ContentHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.item_img);
            title = (TextView) itemView.findViewById(R.id.ll_title);
            content = (TextView) itemView.findViewById(R.id.item_content);
            praiseCount = (TextView) itemView.findViewById(R.id.item_thumbs_up_count);
            conmmentCount = (TextView) itemView.findViewById(R.id.item_comment_count);

        }

    }
}
