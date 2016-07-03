package graduation.project.exhibition.adapter.discover;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import graduation.project.exhibition.R;
import graduation.project.exhibition.business.DiscoverBusiness;
import graduation.project.exhibition.domain.Discover;

/**
 * 条目适配器
 */
public class ItemAdapter extends RecyclerView.Adapter<DiscoverBusiness.ContentHolder> {

    private List<Discover> discovers;

    @Override
    public DiscoverBusiness.ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discover, parent, false);
        return new DiscoverBusiness.ContentHolder(contentView);
    }

    @Override
    public void onBindViewHolder(DiscoverBusiness.ContentHolder holder, int position) {

        if (discovers != null) {
            Discover discover = discovers.get(position);
            DiscoverBusiness.showItem(holder, discover);
        }
    }


    @Override
    public int getItemCount() {
        return discovers == null ? 0 : discovers.size();
    }

    public void setData(List<Discover> discovers) {
        this.discovers = discovers;
        notifyDataSetChanged();
    }


}
