package graduation.project.exhibition.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.jiahuan.svgmapview.SVGMapView;
import com.jiahuan.svgmapview.overlay.SVGMapBaseOverlay;

import graduation.project.exhibition.R;

/**
 * 地图展台覆盖物
 */
public class HallOverlay extends SVGMapBaseOverlay {
    private SVGMapView mMapView;
    private int x;
    private int y;
    private Bitmap mBitmap;
    private Bitmap pic1;
    private Bitmap pic2;
    private onClickListener mListener;
    private int type;

    public HallOverlay(SVGMapView mapView, int x, int y,int type) {
        mMapView = mapView;
        this.x = x;
        this.y = y;
        this.type = type;
        initLayer();
    }

    public void setOnClickListener(onClickListener listener) {
        mListener = listener;
    }

    private void initLayer() {

        switch (type){
            default:
                pic1 = BitmapFactory.decodeResource(mMapView.getResources(), R.mipmap.img_type_bed);
                pic2 = BitmapFactory.decodeResource(mMapView.getResources(), R.mipmap.img_type_bed_press);
                break;
            case 1:
                pic1 = BitmapFactory.decodeResource(mMapView.getResources(), R.mipmap.img_type_cabinet);
                pic2 = BitmapFactory.decodeResource(mMapView.getResources(), R.mipmap.img_type_cabinet_press);
                break;
            case 2:
                pic1 = BitmapFactory.decodeResource(mMapView.getResources(), R.mipmap.img_type_chair);
                pic2 = BitmapFactory.decodeResource(mMapView.getResources(), R.mipmap.img_type_chair_press);
                break;
            case 3:
                pic1 = BitmapFactory.decodeResource(mMapView.getResources(), R.mipmap.img_type_desk);
                pic2 = BitmapFactory.decodeResource(mMapView.getResources(), R.mipmap.img_type_desk_press);
                break;
            case 4:
                pic1 = BitmapFactory.decodeResource(mMapView.getResources(), R.mipmap.img_type_lamp);
                pic2 = BitmapFactory.decodeResource(mMapView.getResources(), R.mipmap.img_type_lamp_press);
                break;
        }

        mBitmap = pic1;
    }


    @Override
    public void onDestroy() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onTap(MotionEvent event) {
        float[] mapCoordinate = mMapView.getMapCoordinateWithScreenCoordinate(event.getX(), event.getY());
        if (mapCoordinate[0] >= x && mapCoordinate[0] <= x + mBitmap.getWidth() && mapCoordinate[1] >= y && mapCoordinate[1] <= y + mBitmap.getHeight()) {

            if (mListener != null) {

                mBitmap = pic2;
                mMapView.refresh();

                mListener.onClick();//处理点击事件

                new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mBitmap = pic1;
                        mMapView.refresh();
                    }
                }.start();

            }
        }
    }

    @Override
    public void draw(Canvas canvas, Matrix matrix, float currentZoom, float currentRotateDegrees) {
        canvas.save();
        canvas.setMatrix(matrix);
        canvas.drawBitmap(mBitmap, x, y, new Paint(Paint.ANTI_ALIAS_FLAG));
        canvas.restore();
    }

    public interface onClickListener {
        void onClick();
    }
}

