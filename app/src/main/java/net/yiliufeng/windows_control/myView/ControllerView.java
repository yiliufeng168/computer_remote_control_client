package net.yiliufeng.windows_control.myView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import net.yiliufeng.windows_control.R;

public class ControllerView extends View implements View.OnTouchListener {
    private Paint borderPaint = new Paint();//大圆的画笔
    private Paint fingerPaint = new Paint();//小圆的画笔
    private float radius = 160;//默认大圆的半径
    private float centerX = radius;//大圆中心点的位置cx
    private float centerY = radius;//大圆中心点的位置cy
    private float fingerX = centerX, fingerY = centerY;//小圆圆心的位置(ax,ay)
    private float lastX = fingerX, lastY = fingerY;//小圆自动回归中点动画中上一点的位置
    private float innerRadius = 30;//默认小圆半径
    private float radiusBorder = (radius - innerRadius);//大圆减去小圆的半径
    private ValueAnimator positionAnimator;//自动回中的动画
    private MoveListener moveListener;//移动回调的接口

    public ControllerView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public ControllerView(Context context,
                          @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ControllerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    //初始化
    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ControllerView);
            int fingerColor = typedArray.getColor(R.styleable.ControllerView_fingerColor,
                    Color.parseColor("#3fffffff"));
            int borderColor = typedArray.getColor(R.styleable.ControllerView_borderColor,
                    Color.GRAY);
            radius = typedArray.getDimension(R.styleable.ControllerView_radius, 220);
            innerRadius = typedArray.getDimension(R.styleable.ControllerView_fingerSize, innerRadius);
            borderPaint.setColor(borderColor);
            fingerPaint.setColor(fingerColor);
            lastX = lastY = fingerX = fingerY = centerX = centerY = radius;
            radiusBorder = radius - innerRadius;
            typedArray.recycle();
        }
        setOnTouchListener(this);
        positionAnimator = ValueAnimator.ofFloat(1);
        positionAnimator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Float aFloat = (Float) animation.getAnimatedValue();
                        changeFingerPosition(lastX + (centerX - lastX) * aFloat, lastY + (centerY - lastY) * aFloat);
                    }
                }
        );
    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(getActualSpec(widthMeasureSpec), getActualSpec(heightMeasureSpec));
    }


    //处理wrapcontent的测量
    //默认wrapcontent,没有做matchParent,指定大小的适配
    //view实际的大小是通过大圆半径确定的
    public int getActualSpec(int spec) {
        int mode = MeasureSpec.getMode(spec);
        int len = MeasureSpec.getSize(spec);
        switch (mode) {
            case MeasureSpec.AT_MOST:
                len = (int) (radius * 2);
                break;
        }
        return MeasureSpec.makeMeasureSpec(len, mode);
    }

    //绘制
    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(centerX, centerY, radius, borderPaint);
        canvas.drawCircle(fingerX, fingerY, innerRadius, fingerPaint);
    }

    @Override public boolean onTouch(View v, MotionEvent event) {
        float evx = event.getX(), evy = event.getY();
        float deltaX = evx - centerX, deltaY = evy - centerY;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //圆外按压不生效
                if (deltaX * deltaX + deltaY * deltaY > radius * radius) {
                    break;
                }
            case MotionEvent.ACTION_MOVE:
                //如果触摸点在圆外
                if (Math.abs(deltaX) > radiusBorder || Math.abs(deltaY) > radiusBorder) {
                    float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                    changeFingerPosition(centerX + (deltaX * radiusBorder / distance),
                            centerY + (deltaY * radiusBorder / distance));
                } else { //如果触摸点在圆内
                    changeFingerPosition(evx, evy);
                }
                positionAnimator.cancel();
                break;
            case MotionEvent.ACTION_UP:
                positionAnimator.setDuration(1000);
                positionAnimator.start();
                break;
        }
        return true;
    }

    /**
     * 改变位置的回调出来
     */
    private void changeFingerPosition(float fingerX, float fingerY) {
        this.fingerX = fingerX;
        this.fingerY = fingerY;
        if (moveListener != null) {
            float r = radius - innerRadius;
            if (r == 0) {
                invalidate();
                return;
            }
            moveListener.move((fingerX - centerX) / r, (fingerY - centerY) / r);
        }
        invalidate();
    }

    @Override protected void finalize() throws Throwable {
        super.finalize();
        positionAnimator.removeAllListeners();
    }

    public void setMoveListener(
            MoveListener moveListener) {
        this.moveListener = moveListener;
    }

    /**
     *回调事件的接口
     *
     **/
    public interface MoveListener {
        void move(float dx, float dy);
    }
}
