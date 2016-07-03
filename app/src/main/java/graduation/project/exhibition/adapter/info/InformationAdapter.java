package graduation.project.exhibition.adapter.info;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import graduation.project.exhibition.R;
import graduation.project.exhibition.activity.info.NewsDetailActivity;
import graduation.project.exhibition.business.ImageUtil;
import graduation.project.exhibition.domain.InfoNews;
import graduation.project.exhibition.utils.IntentUtils;
import graduation.project.exhibition.utils.MethodUtils;
import graduation.project.exhibition.utils.ShareUtils;

/**
 * 最新资讯
 */
public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.Holder> {

    private List<InfoNews> infoNews;

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info_information, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        if (infoNews != null) {
            final InfoNews news = this.infoNews.get(position);
            ImageUtil.loadWebImage(holder.imageView, news.getPic());
            holder.title.setText(news.getTitle());
            holder.content.setText("\u3000\u3000" + news.getText());
            holder.readCount.setText(news.getReadcount());
            holder.btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ShareUtils(v, news.getTitle() + news.getText() + news.getUrl()).all();
                }
            });
            holder.btnCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MethodUtils.copyTextToClipBoard(v.getContext(), news.getUrl());
                }
            });
            holder.contentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentUtils.newIntent(v.getContext())
                            .setActivityClass(NewsDetailActivity.class)
                            .putExtra("id", news.getId())
                            .startActivity();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return infoNews == null ? 0 : infoNews.size();
    }

    public void setContentData(List<InfoNews> infoNews) {
        this.infoNews = infoNews;
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title;
        TextView content;
        TextView readCount;
        View btnCopy;
        View btnShare;
        View contentLayout;

        public Holder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_img);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            content = (TextView) itemView.findViewById(R.id.tv_content);
            readCount = (TextView) itemView.findViewById(R.id.tv_read_count);
            btnCopy = itemView.findViewById(R.id.ll_copy);
            btnShare = itemView.findViewById(R.id.ll_share);
            contentLayout = itemView.findViewById(R.id.ll_content);
        }

    }
}
