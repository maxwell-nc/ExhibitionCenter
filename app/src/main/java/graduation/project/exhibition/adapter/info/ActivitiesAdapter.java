package graduation.project.exhibition.adapter.info;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import graduation.project.exhibition.R;
import graduation.project.exhibition.activity.info.ActivitiesDetailActivity;
import graduation.project.exhibition.business.ImageUtil;
import graduation.project.exhibition.config.ConfigConstant;
import graduation.project.exhibition.domain.Activities;
import graduation.project.exhibition.utils.IntentUtils;
import graduation.project.exhibition.utils.MethodUtils;
import io.github.maxwell_nc.imageloader.ImageLoader;

/**
 * 展会活动
 */
public class ActivitiesAdapter extends RecyclerView.Adapter<ActivitiesAdapter.Holder> {


    private List<Activities> activitiesList;

    @Override
    public ActivitiesAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info_activities, parent, false);
        return new Holder(contentView);

    }

    @Override
    public void onBindViewHolder(ActivitiesAdapter.Holder holder, int position) {

        if (activitiesList != null) {
            final Activities activities = activitiesList.get(position);
            ImageUtil.loadWebImage(holder.pic, activities.getPic());
            holder.title.setText(MethodUtils.replaceLine(activities.getTitle()));

            int state = Integer.valueOf(activities.getState());
            switch (state) {
                case 0://未开始
                    holder.state.setText("未开始");
                    holder.state.setBackground(new ColorDrawable(MethodUtils.getColor(holder.state, R.color.grey)));
                    break;
                case 1://售票中
                    holder.state.setText("售票中");
                    holder.state.setBackground(new ColorDrawable(MethodUtils.getColor(holder.state, R.color.orange)));
                    break;
                case 2://预热
                    holder.state.setText("预热");
                    holder.state.setBackground(new ColorDrawable(MethodUtils.getColor(holder.state, R.color.light_red)));
                    break;
                case 3://已结束
                    holder.state.setText("已结束");
                    holder.state.setBackground(new ColorDrawable(MethodUtils.getColor(holder.state, R.color.green_blue)));
                    break;
            }

            holder.time.setText(activities.getTime());
            holder.addr.setText(activities.getAddr());
            holder.price.setText(activities.getPrice());

            if (TextUtils.isEmpty(activities.getText())) {
                holder.desc.setVisibility(View.GONE);
            } else {
                holder.desc.setVisibility(View.VISIBLE);
                holder.desc.setText(activities.getText());
            }

            holder.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentUtils.newIntent(v.getContext())
                            .setActivityClass(ActivitiesDetailActivity.class)
                            .putExtra("id", activities.getId())
                            .startActivity();
                }
            });
        }


    }


    @Override
    public int getItemCount() {
        return activitiesList == null ? 0 : activitiesList.size();
    }

    public void setData(List<Activities> activitiesList) {
        this.activitiesList = activitiesList;
        notifyDataSetChanged();
    }


    class Holder extends RecyclerView.ViewHolder {

        View container;
        ImageView pic;
        TextView title;
        TextView state;
        TextView time;
        TextView addr;
        TextView price;
        TextView desc;

        public Holder(View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.ll_container);
            pic = (ImageView) itemView.findViewById(R.id.item_img);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            state = (TextView) itemView.findViewById(R.id.item_state);
            time = (TextView) itemView.findViewById(R.id.item_time);
            addr = (TextView) itemView.findViewById(R.id.item_addr);
            price = (TextView) itemView.findViewById(R.id.item_price);
            desc = (TextView) itemView.findViewById(R.id.item_notice);
        }

    }
}
