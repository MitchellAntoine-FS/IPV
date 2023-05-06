package com.fullsail.apolloarchery;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.google.rpc.context.AttributeContext;

import java.util.ArrayList;

public class DrawingSurface extends SurfaceView implements SurfaceHolder.Callback {

    private Rect mDimensions;
    private Paint mBlankPaint;
    private Paint mTextPaint;
    private Paint mBlackHolePaint;
    ArrayList<Point> mPoints;

    private Bitmap mBackground;

    public DrawingSurface(Context context) {
        super(context);
    }

    public DrawingSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawingSurface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DrawingSurface(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        setWillNotDraw(false);
        getHolder().addCallback(this);

        Resources res = getResources();
        mBackground = BitmapFactory.decodeResource(res, R.drawable.archery_target);

        mBlankPaint = new Paint();

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(60.0f);

        mBlackHolePaint = new Paint();
        mBlackHolePaint.setColor(Color.BLACK);

        mPoints = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Clear the canvas by drawing a single color
        canvas.drawColor(Color.BLACK);

        // Draw the background image to fill the entire surface of the canvas
        canvas.drawBitmap(mBackground, null, mDimensions, mBlankPaint);

        // Draw a circle on each touch point
        for (Point p : mPoints){
            canvas.drawCircle(p.x, p.y, 35.0f, mBlackHolePaint);
        }

        // Draw some text in the middle of the screen
        canvas.drawText("Hey Antoine", mDimensions.width()/2.7f, mDimensions.height()/2.0f, mTextPaint);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // Check if the touch event is a down event
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Add the points to the list
            mPoints.add(new Point(
                    (int) event.getX(),
                    (int) event.getY()
            ));

            // Tell the view to re-draw on the next canvas
            postInvalidate();
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        storeDimensions(holder);
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    public void storeDimensions(SurfaceHolder holder) {

        // Lock the canvas to get an instance of it back.
        Canvas canvas = holder.lockCanvas();

        // Retrieve the dimensions and hold onto them for later.
        mDimensions = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Release the canvas and post a draw.
        holder.unlockCanvasAndPost(canvas);
    }
}
