package graduation.project.exhibition.fragment.discover;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.labo.kaji.swipeawaydialog.SwipeAwayDialogFragment;

import graduation.project.exhibition.R;
import graduation.project.exhibition.business.ImageUtil;
import graduation.project.exhibition.domain.Ticket;

/**
 * 票券二维码对话框
 */
public class QRCodeDialogFragment extends SwipeAwayDialogFragment {

    private Ticket ticket;
    private Activity attachActivity;
    private View root;

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public void setAttachActivity(Activity attachActivity) {
        this.attachActivity = attachActivity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AppCompatDialog dialog = new AppCompatDialog(attachActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        root = View.inflate(attachActivity, R.layout.dialog_discover_qrcode, null);
        dialog.setContentView(root);

        initViewAndData();
        return dialog;
    }

    private void initViewAndData() {
        ((TextView) findViewById(R.id.tv_title)).setText(ticket.getName());
        ((TextView) findViewById(R.id.tv_time)).setText("有效时间：" + ticket.getTime());
        ((TextView) findViewById(R.id.tv_no1)).setText(ticket.getNo1());
        ((TextView) findViewById(R.id.tv_no2)).setText(ticket.getNo2());
        ((TextView) findViewById(R.id.tv_addr)).setText(ticket.getAddr());
        ((TextView) findViewById(R.id.tv_addr_bottom)).setText("二维码对准[" + ticket.getAddr() + "]摄像头出票");
        ImageView imageView = (ImageView) findViewById(R.id.iv_pic);
        ImageUtil.loadWebImage(imageView, ticket.getPic());
    }

    private View findViewById(int resId) {
        return root.findViewById(resId);
    }
}
