package graduation.project.exhibition.adapter.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import graduation.project.exhibition.R;
import graduation.project.exhibition.business.ImageUtil;
import graduation.project.exhibition.domain.ServiceInfo;
import graduation.project.exhibition.utils.MethodUtils;

/**
 * 增值服务适配器
 */
public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ContentHolder> {

    private List<ServiceInfo> serviceInfoList;

    @Override
    public ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_content, parent, false);
        return new ContentHolder(contentView);
    }

    @Override
    public void onBindViewHolder(ContentHolder holder, int position) {
        ServiceInfo serviceInfo = serviceInfoList.get(position);
        holder.name.setText(serviceInfo.getName());
        holder.text.setText(MethodUtils.replaceLineAndAddSpace(serviceInfo.getText()));
        ImageUtil.loadWebImage(holder.imageView, serviceInfo.getPic());
    }

    @Override
    public int getItemCount() {
        return serviceInfoList == null ? 0 : serviceInfoList.size();
    }

    public void setContentData(List<ServiceInfo> serviceInfoList) {
        this.serviceInfoList = serviceInfoList;
        notifyDataSetChanged();
    }

    class ContentHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name;
        TextView text;

        public ContentHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_img);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            text = (TextView) itemView.findViewById(R.id.tv_text);
        }
    }
}

