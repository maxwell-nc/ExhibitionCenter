package graduation.project.exhibition.adapter.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import graduation.project.exhibition.R;
import graduation.project.exhibition.activity.home.MapActivity;
import graduation.project.exhibition.business.ImageUtil;
import graduation.project.exhibition.domain.Hall;
import graduation.project.exhibition.utils.IntentUtils;

/**
 * 展览馆数据适配器
 */
public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.Holder> {

    private List<Hall> mHalls;

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_area, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        final Hall hall = mHalls.get(position);

        holder.title.setText(hall.getTitle());
        holder.text.setText(hall.getText());
        ImageUtil.loadWebImage(holder.imageView, hall.getPic());

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.newIntent(v.getContext())
                        .setActivityClass(MapActivity.class)
                        .putExtra("id",hall.getId())
                        .startActivity();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHalls != null ? mHalls.size() : 0;
    }

    public void setHalls(List<Hall> halls) {
        mHalls = halls;
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title;
        TextView text;
        View root;

        public Holder(View itemView) {
            super(itemView);

            root = itemView.findViewById(R.id.fl_root);
            imageView = (ImageView) itemView.findViewById(R.id.item_img);
            title = (TextView) itemView.findViewById(R.id.tv_name);
            text = (TextView) itemView.findViewById(R.id.tv_text);

        }
    }
}