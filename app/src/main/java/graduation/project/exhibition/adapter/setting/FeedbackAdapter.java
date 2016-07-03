package graduation.project.exhibition.adapter.setting;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import graduation.project.exhibition.R;
import graduation.project.exhibition.domain.Feedback;

/**
 * 反馈信息展示
 */
public class FeedbackAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_SELF = 0;
    private static final int VIEW_TYPE_OTHER = 1;
    private List<Feedback> list;

    @Override
    public int getItemViewType(int position) {
        if (list != null) {

            if (position % 2 == 0) {
                return VIEW_TYPE_SELF;
            } else {
                return VIEW_TYPE_OTHER;
            }

        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_SELF:
                View selfView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback_self, parent, false);
                return new SelfHolder(selfView);
            case VIEW_TYPE_OTHER:
                View otherView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback_other, parent, false);
                return new OtherHolder(otherView);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Feedback feedback = list.get(Math.round(position / 2));

        if (feedback == null) {
            return;
        }

        if (position % 2 == 0) {

            SelfHolder selfHolder = (SelfHolder) holder;


            if (TextUtils.isEmpty(feedback.getText())) {
                selfHolder.root.getLayoutParams().height = 0;
            } else {
                selfHolder.root.setVisibility(View.VISIBLE);
                selfHolder.text.setText(feedback.getText());
            }
        } else {

            OtherHolder otherHolder = (OtherHolder) holder;
            if (feedback.getRepeat() != null) {
                otherHolder.text.setText(feedback.getRepeat());
            } else {
                otherHolder.text.setText("感谢你的反馈！");
            }

        }

    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size() * 2;
        } else {
            return 0;
        }
    }

    public void setContentData(List<Feedback> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    class SelfHolder extends RecyclerView.ViewHolder {

        TextView text;
        LinearLayout root;

        public SelfHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.tv_text);
            root = (LinearLayout) itemView.findViewById(R.id.ll_root);
        }

    }

    class OtherHolder extends RecyclerView.ViewHolder {

        TextView text;

        public OtherHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.tv_text);
        }

    }
}