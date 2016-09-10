package app.com.example.a65400k.sunshine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

/**
 * Created by A6 5400K on 9/10/2016.
 */
public class Compass extends View {

    private float direction;

    private String mWindSpeedDir;

    public Compass(Context context) {
        super(context);

        AccessibilityManager accessibilityManager =
                (AccessibilityManager) context.getSystemService(
                        Context.ACCESSIBILITY_SERVICE);
        if (accessibilityManager.isEnabled()) {
            sendAccessibilityEvent(
                    AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED);
        }
    }

    public Compass(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public Compass(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(
                MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int w = getMeasuredWidth();
        int h = getMeasuredHeight();
        int r;
        if(w > h){
            r = h/2;
        }else{
            r = w/2;
        }

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.GRAY);

        canvas.drawCircle(w/2, h/2, r, paint);

        paint.setColor(getResources().getColor(R.color.sunshine_dark_blue));
        canvas.drawLine(
                w/2,
                h/2,
                (float)(w/2 + r * Math.sin(direction)),
                (float)(h/2 - r * Math.cos(direction)),
                paint);

    }

    public void update(float dir){
        // Convert direction from degrees to radians
        direction = dir * ((float) Math.PI / 180);
        mWindSpeedDir = "Compass showing wind direction at " + dir + " degrees";

        // Call invalidate to force drawing on page.
        invalidate();
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent ev) {
        ev.getText().add(mWindSpeedDir);
        return true;
    }
}