package graduation.project.exhibition.adapter.discover;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import graduation.project.exhibition.R;
import graduation.project.exhibition.activity.discover.DiscoverDetailActivity;
import graduation.project.exhibition.activity.discover.ItemActivity;
import graduation.project.exhibition.business.DiscoverBusiness;
import graduation.project.exhibition.domain.Discover;
import graduation.project.exhibition.utils.IntentUtils;

/**
 * 看资讯页面数据适配器
 */
public class DiscoverAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_HEAD = 0;
    private static final int VIEW_TYPE_CONTENT = 1;
    private List<Discover> discovers;
    private List<Discover> bannerInfo;

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
                View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discover_header, parent, false);
                return new HeaderHolder(headerView);
            case VIEW_TYPE_CONTENT:
                View contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discover, parent, false);
                return new DiscoverBusiness.ContentHolder(contentView);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {

            HeaderHolder headerHolder = (HeaderHolder) holder;

            setItemClick(headerHolder.business, "business");
            setItemClick(headerHolder.bank, "bank");
            setItemClick(headerHolder.hotel, "hotel");
            setItemClick(headerHolder.travel, "travel");
            setItemClick(headerHolder.food, "food");
            setItemClick(headerHolder.transportation, "transportation");
            setItemClick(headerHolder.shop, "shop");
            setItemClick(headerHolder.help, "help");

            if (bannerInfo != null && bannerInfo.size() >= 4) {
                setBannerClick(headerHolder.banner1, bannerInfo.get(0));
                setBannerClick(headerHolder.banner2, bannerInfo.get(1));
                setBannerClick(headerHolder.banner3, bannerInfo.get(2));
                setBannerClick(headerHolder.banner4, bannerInfo.get(3));
            }

        } else {

            DiscoverBusiness.ContentHolder contentHolder = (DiscoverBusiness.ContentHolder) holder;

            if (discovers != null) {
                Discover discover = discovers.get(position - 1);
                DiscoverBusiness.showItem(contentHolder, discover);
            }
        }

    }

    /**
     * 设置点击事件
     */
    private void setBannerClick(View banner, final Discover discover) {
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.newIntent(v.getContext())
                        .setActivityClass(DiscoverDetailActivity.class)
                        .putExtra("discover",discover)
                        .startActivity();
            }
        });
    }

    /**
     * 设置点击事件
     */
    private void setItemClick(final TextView textView, final String type) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.newIntent(v.getContext())
                        .setActivityClass(ItemActivity.class)
                        .putExtra("type", type)
                        .putExtra("title", textView.getText().toString())
                        .startActivity();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (discovers == null) {
            return 1;
        }
        return discovers.size() + 1;
    }

    public void setContentData(List<Discover> discovers) {
        this.discovers = discovers;
        notifyDataSetChanged();
    }

    public void setBannerData(List<Discover> discovers) {
        this.bannerInfo = discovers;
        notifyItemChanged(0);
    }

    class HeaderHolder extends RecyclerView.ViewHolder {

        TextView business;
        TextView bank;
        TextView hotel;
        TextView travel;
        TextView food;
        TextView transportation;
        TextView shop;
        TextView help;
        View banner1;
        View banner2;
        View banner3;
        View banner4;

        public HeaderHolder(View itemView) {
            super(itemView);
            business = (TextView) itemView.findViewById(R.id.tv_business);
            bank = (TextView) itemView.findViewById(R.id.tv_bank);
            hotel = (TextView) itemView.findViewById(R.id.tv_hotel);
            travel = (TextView) itemView.findViewById(R.id.tv_travel);
            food = (TextView) itemView.findViewById(R.id.tv_food);
            transportation = (TextView) itemView.findViewById(R.id.tv_transportation);
            shop = (TextView) itemView.findViewById(R.id.tv_shop);
            help = (TextView) itemView.findViewById(R.id.tv_help);
            banner1 = itemView.findViewById(R.id.banner_1);
            banner2 = itemView.findViewById(R.id.banner_2);
            banner3 = itemView.findViewById(R.id.banner_3);
            banner4 = itemView.findViewById(R.id.banner_4);
        }

    }
}