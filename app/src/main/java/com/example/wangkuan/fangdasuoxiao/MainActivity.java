package com.example.wangkuan.fangdasuoxiao;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private ImageView mImageView;
    private GestureDetector detector;
    private Matrix matrix;
    private Bitmap bitmap;
    private int width;
    private int height;

    // 记录当前的缩放比
    float currentScale = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.a1);
        //创建收拾检测器
        detector = new GestureDetector(MainActivity.this);
        //控制图片缩放的matrix对象
        matrix = new Matrix();
        //获取被缩放的图片资源
        bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.x9);
        //获取位图的宽和高
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        //  设置imager初始化的图片
        mImageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.mipmap.x9));

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 将该Activity上的触碰时间交个 GestureDetector处理
        return detector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        v = v > 4000 ? 4000 : v;
        v1 = v1 < -4000 ? -4000 : v1;
        // 感觉手势的速度来计算缩放比，如果 velocityX>0,放大图像，否则缩小图像
        currentScale += currentScale * v / 4000.0f;
        // 保证 currentScale 不会等于0
        currentScale = currentScale > 0.01 ? currentScale : 0.01f;
        // 重置 Matrix
        matrix.setScale(currentScale, currentScale, 160, 200);
        BitmapDrawable tmp = (BitmapDrawable) mImageView.getDrawable();
        // 如果图片还未回收，先强制收回该图片
        if (!tmp.getBitmap().isRecycled()) {
            tmp.getBitmap().recycle();
        }
        // 根据原始位图和 Matrix创建新图片
        Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                matrix, true);
        //显示新的位图
        mImageView.setImageBitmap(bitmap2);
        return true;
    }
}
