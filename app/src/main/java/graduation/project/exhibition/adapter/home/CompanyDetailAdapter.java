package graduation.project.exhibition.adapter.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import graduation.project.exhibition.R;
import graduation.project.exhibition.activity.home.ProductDetailActivity;
import graduation.project.exhibition.business.ImageUtil;
import graduation.project.exhibition.domain.Company;
import graduation.project.exhibition.domain.Product;
import graduation.project.exhibition.utils.IntentUtils;
import graduation.project.exhibition.utils.MethodUtils;

/**
 * 推荐内容
 */
public class CompanyDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_HEAD = 0;
    private static final int VIEW_TYPE_CONTENT = 1;
    private Company company;
    private List<Product> products;

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
                View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company_detail_head, parent, false);
                return new HeaderHolder(headerView);
            case VIEW_TYPE_CONTENT:
                View contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company_detail_content, parent, false);
                return new ContentHolder(contentView);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {

            final HeaderHolder headerHolder = (HeaderHolder) holder;
            if (company != null) {

                headerHolder.title.setText(company.getTitle());
                headerHolder.text.setText(MethodUtils.replaceLineAndAddSpace(company.getText()));
                ImageUtil.loadWebImage(headerHolder.image, company.getPic());


            }
        } else {

            ContentHolder contentHolder = (ContentHolder) holder;
            if (products != null) {
                final Product product = products.get(position - 1);

                contentHolder.title.setText(product.getTitle());
                contentHolder.text.setText(product.getText());
                contentHolder.price.setText("惊喜价：" + product.getPrice());
                ImageUtil.loadWebImage(contentHolder.image, product.getPic());

                contentHolder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentUtils.newIntent(v.getContext())
                                .setActivityClass(ProductDetailActivity.class)
                                .putExtra("id",product.getId())
                                .startActivity();
                    }
                });

            }
        }

    }


    @Override
    public int getItemCount() {
        return products != null ? products.size() + 1 : 1;
    }

    public void setHeaderData(Company company) {
        this.company = company;
    }

    public void setContentData(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    class HeaderHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView image;
        TextView text;

        public HeaderHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.tv_title);
            image = (ImageView) itemView.findViewById(R.id.item_img);
            text = (TextView) itemView.findViewById(R.id.item_text);

        }

    }


    class ContentHolder extends RecyclerView.ViewHolder {

        private final View root;
        TextView title;
        ImageView image;
        TextView text;
        TextView price;

        public ContentHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.tv_title);
            image = (ImageView) itemView.findViewById(R.id.item_img);
            text = (TextView) itemView.findViewById(R.id.item_text);
            price = (TextView) itemView.findViewById(R.id.tv_price);
            root = itemView.findViewById(R.id.rl_root);

        }

    }
}
