package graduation.project.exhibition.business;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import graduation.project.exhibition.R;
import graduation.project.exhibition.activity.discover.DiscoverDetailActivity;
import graduation.project.exhibition.domain.Discover;
import graduation.project.exhibition.utils.IntentUtils;
import graduation.project.exhibition.utils.MethodUtils;

/**
 * 发现页面的业务
 */
public class DiscoverBusiness {

    public static void showItem(ContentHolder holder, final Discover discover) {
        ImageUtil.loadWebImage(holder.imageView, discover.getPic());
        holder.title.setText(discover.getTitle());

        int tapNum = Integer.valueOf(discover.getTitle_tap());
        switch (tapNum) {
            case 0://无
                holder.titleTap.setVisibility(View.GONE);
                break;
            case 1://￥
                holder.titleTap.setText("￥");
                holder.titleTap.setBackground(new ColorDrawable(MethodUtils.getColor(holder.titleTap, R.color.light_red)));
                break;
            case 2://惠
                holder.titleTap.setText("惠");
                holder.titleTap.setBackground(new ColorDrawable(MethodUtils.getColor(holder.titleTap, R.color.green_blue)));
                break;
            case 3://券
                holder.titleTap.setText("券");
                holder.titleTap.setBackground(new ColorDrawable(MethodUtils.getColor(holder.titleTap, R.color.orange)));
                break;
        }


        holder.star1.setImageResource(R.mipmap.img_star_on);
        holder.star2.setImageResource(R.mipmap.img_star_off);
        holder.star3.setImageResource(R.mipmap.img_star_off);
        holder.star4.setImageResource(R.mipmap.img_star_off);
        holder.star5.setImageResource(R.mipmap.img_star_off);

        float starNum = Float.valueOf(discover.getStar());
        if (starNum >= 4.5) {
            holder.star5.setImageResource(R.mipmap.img_star_on);
        }
        if (starNum >= 3.5) {
            holder.star4.setImageResource(R.mipmap.img_star_on);
        }
        if (starNum >= 2.5) {
            holder.star3.setImageResource(R.mipmap.img_star_on);
        }
        if (starNum >= 1.5) {
            holder.star2.setImageResource(R.mipmap.img_star_on);
        }

        holder.starCount.setText(starNum + "分");
        holder.spend.setText("人均￥" + discover.getSpend());

        holder.desc.setText(discover.getText());

        int distance = Integer.valueOf(discover.getNear());
        if (distance > 1000) {
            holder.near.setText("附近 " + distance / 1000f + " km");
        } else {
            holder.near.setText("附近 " + distance + " m");
        }

        int textTapNum = Integer.valueOf(discover.getText_tap());
        switch (textTapNum) {
            case 0://无
                holder.textTap.setVisibility(View.GONE);
                break;
            case 1://团
                holder.textTap.setText("团");
                holder.textTap.setBackground(new ColorDrawable(MethodUtils.getColor(holder.titleTap, R.color.light_red)));
                break;
            case 2://满
                holder.textTap.setText("满");
                holder.textTap.setBackground(new ColorDrawable(MethodUtils.getColor(holder.titleTap, R.color.green_blue)));
                break;
            case 3://减
                holder.textTap.setText("减");
                holder.textTap.setBackground(new ColorDrawable(MethodUtils.getColor(holder.titleTap, R.color.orange)));
                break;
        }

        if (discover.getAdd_text().length() == 0) {
            discover.setAdd_text("暂无优惠信息");
        }
        holder.addText.setText(discover.getAdd_text());

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.newIntent(v.getContext())
                        .setActivityClass(DiscoverDetailActivity.class)
                        .putExtra("discover",discover)
                        .startActivity();
            }
        });
    }


    public static class ContentHolder extends RecyclerView.ViewHolder {

        View container;
        ImageView imageView;
        TextView title;
        TextView titleTap;
        ImageView star1;
        ImageView star2;
        ImageView star3;
        ImageView star4;
        ImageView star5;
        TextView starCount;
        TextView spend;
        TextView desc;
        TextView near;
        TextView textTap;
        TextView addText;

        public ContentHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_img);
            titleTap = (TextView) itemView.findViewById(R.id.tv_title_tap);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            star1 = (ImageView) itemView.findViewById(R.id.iv_star_1);
            star2 = (ImageView) itemView.findViewById(R.id.iv_star_2);
            star3 = (ImageView) itemView.findViewById(R.id.iv_star_3);
            star4 = (ImageView) itemView.findViewById(R.id.iv_star_4);
            star5 = (ImageView) itemView.findViewById(R.id.iv_star_5);
            starCount = (TextView) itemView.findViewById(R.id.tv_star_count);
            spend = (TextView) itemView.findViewById(R.id.tv_spend);
            desc = (TextView) itemView.findViewById(R.id.tv_desc);
            near = (TextView) itemView.findViewById(R.id.tv_near);
            textTap = (TextView) itemView.findViewById(R.id.item_text_tap);
            addText = (TextView) itemView.findViewById(R.id.item_add_text);
            container = itemView.findViewById(R.id.rl_container);
        }

    }
}
