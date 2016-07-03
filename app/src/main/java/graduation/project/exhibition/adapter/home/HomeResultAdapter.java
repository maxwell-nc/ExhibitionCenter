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
import graduation.project.exhibition.activity.home.ProductDetailActivity;
import graduation.project.exhibition.business.ImageUtil;
import graduation.project.exhibition.domain.Company;
import graduation.project.exhibition.domain.Product;
import graduation.project.exhibition.utils.IntentUtils;

/**
 * 主页搜索结果数据适配器
 */
public class HomeResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_MARK = 0;
    private static final int VIEW_TYPE_CONTENT = 1;

    private List<Company> companies;
    private List<Product> products;

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_MARK;
        } else if (position > 0 && position <= companies.size()) {
            return VIEW_TYPE_CONTENT;
        } else if (position == companies.size() + 1) {
            return VIEW_TYPE_MARK;
        } else {
            return VIEW_TYPE_CONTENT;
        }
    }


    @Override
    public int getItemCount() {
        if (companies == null || products == null) {
            return 0;
        }
        return companies.size() + products.size() + 2;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_MARK:
                View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_mark, parent, false);
                return new MarkHolder(headerView);
            case VIEW_TYPE_CONTENT:
                View contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_home_result, parent, false);
                return new ContentHolder(contentView);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.getItemViewType();


        if (position == 0) {
            MarkHolder markHolder = (MarkHolder) holder;

            markHolder.relativeText.setText("相关公司");
            if (companies.size() == 0) {
                markHolder.noResultLayout.setVisibility(View.VISIBLE);
            } else {
                markHolder.noResultLayout.setVisibility(View.GONE);
            }


        } else if (position > 0 && position <= companies.size()) {

            ContentHolder contentHolder = (ContentHolder) holder;

            final Company company = companies.get(position - 1);
            contentHolder.title.setText(company.getTitle());
            contentHolder.text.setText(company.getText());
            ImageUtil.loadWebImage(contentHolder.imageView, company.getPic());

            contentHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentUtils.newIntent(v.getContext())
                            .setActivityClass(CompanyDetailActivity.class)
                            .putExtra("id", company.getId())
                            .startActivity();
                }
            });

        } else if (position == companies.size() + 1) {

            MarkHolder markHolder = (MarkHolder) holder;

            markHolder.relativeText.setText("相关展品");
            if (products.size() == 0) {
                markHolder.noResultLayout.setVisibility(View.VISIBLE);
            } else {
                markHolder.noResultLayout.setVisibility(View.GONE);
            }

        } else {

            ContentHolder contentHolder = (ContentHolder) holder;

            final Product product = products.get(position - companies.size() - 2);
            contentHolder.title.setText(product.getTitle());
            contentHolder.text.setText(product.getText());
            ImageUtil.loadWebImage(contentHolder.imageView, product.getPic());

            contentHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentUtils.newIntent(v.getContext())
                            .setActivityClass(ProductDetailActivity.class)
                            .putExtra("id", product.getId())
                            .startActivity();
                }
            });
        }


    }

    public void setCompanyData(List<Company> companies) {
        this.companies = companies;
        notifyDataSetChanged();
    }

    public void setProductData(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    class MarkHolder extends RecyclerView.ViewHolder {

        TextView relativeText;
        View noResultLayout;

        public MarkHolder(View itemView) {
            super(itemView);
            relativeText = (TextView) itemView.findViewById(R.id.tv_relative);
            noResultLayout = itemView.findViewById(R.id.tv_no_result);
        }

    }

    class ContentHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title;
        TextView text;

        public ContentHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_img);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            text = (TextView) itemView.findViewById(R.id.item_text);
        }

    }
}