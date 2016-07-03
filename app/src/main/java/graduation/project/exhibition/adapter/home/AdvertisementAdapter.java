package graduation.project.exhibition.adapter.home;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import graduation.project.exhibition.R;
import graduation.project.exhibition.business.ImageUtil;
import graduation.project.exhibition.domain.Advertisement;

/**
 * 广告合作信息适配器
 */
public class AdvertisementAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_HEAD = 0;
    private static final int VIEW_TYPE_CONTENT = 1;
    private List<Advertisement> adList;

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_HEAD : VIEW_TYPE_CONTENT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_HEAD:
                View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_advertisement_header, parent, false);
                return new HeaderHolder(headerView);
            case VIEW_TYPE_CONTENT:
                View contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_advertisement_content, parent, false);
                return new ContentHolder(contentView);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {

            final HeaderHolder headerHolder = (HeaderHolder) holder;
            headerHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.getContext().startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getPhone())));
                }

                /**
                 * 正则获取电话号码
                 */
                @NonNull
                private String getPhone() {
                    String text = headerHolder.phone.getText().toString();

                    Pattern pattern = Pattern.compile("(?<!\\d)(?:(?:1[358]\\d{9})|(?:861[358]\\d{9}))(?!\\d)");
                    Matcher matcher = pattern.matcher(text);
                    StringBuilder bf = new StringBuilder(64);
                    while (matcher.find()) {
                        bf.append(matcher.group()).append(",");
                    }
                    int len = bf.length();
                    if (len > 0) {
                        bf.deleteCharAt(len - 1);
                    }
                    return bf.toString();
                }
            });

        } else {

            ContentHolder contentHolder = (ContentHolder) holder;
            Advertisement advertisement = adList.get(position - 1);

            contentHolder.name.setText(advertisement.getName());
            contentHolder.size.setText("规格："+advertisement.getSize());
            contentHolder.price.setText("参考价格："+advertisement.getPrice());

            ImageUtil.loadWebImage(contentHolder.imageView, advertisement.getPics());
        }
    }

    @Override
    public int getItemCount() {
        return adList == null ? 1 : adList.size() + 1;
    }

    public void setContentData(List<Advertisement> adList) {
        this.adList = adList;
        notifyDataSetChanged();
    }

    class HeaderHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView phone;

        public HeaderHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_call);
            phone = (TextView) itemView.findViewById(R.id.tv_phone);
        }
    }

    class ContentHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name;
        TextView size;
        TextView price;

        public ContentHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_img);
            name = (TextView) itemView.findViewById(R.id.tv_title);
            size = (TextView) itemView.findViewById(R.id.item_size);
            price = (TextView) itemView.findViewById(R.id.item_price);
        }
    }
}

