package graduation.project.exhibition.adapter.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import graduation.project.exhibition.R;
import graduation.project.exhibition.activity.home.CompanyDetailActivity;
import graduation.project.exhibition.business.ImageUtil;
import graduation.project.exhibition.domain.Company;
import graduation.project.exhibition.utils.IntentUtils;
import graduation.project.exhibition.utils.MethodUtils;

/**
 * 条目适配器
 */
public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.Holder> {

    private List<Company> companies;

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company, parent, false);
        return new Holder(contentView);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {

        if (companies != null) {

            final Company company = companies.get(position);

            holder.title.setText(company.getTitle());
            holder.text.setText(MethodUtils.replaceLineAndAddSpace(company.getText()));
            ImageUtil.loadWebImage(holder.image, company.getPic());

            holder.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    IntentUtils.newIntent(v.getContext())
                            .setActivityClass(CompanyDetailActivity.class)
                            .putExtra("id", company.getId())
                            .startActivity();
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return companies == null ? 0 : companies.size();
    }

    public void setData(List<Company> companies) {
        this.companies = companies;
        notifyDataSetChanged();
    }


    public class Holder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView image;
        TextView text;
        View more;


        public Holder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.tv_title);
            image = (ImageView) itemView.findViewById(R.id.item_img);
            text = (TextView) itemView.findViewById(R.id.item_text);
            more = itemView.findViewById(R.id.item_more);
        }
    }
}
