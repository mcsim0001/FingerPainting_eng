package ua.com.mcsim.fingerpainting;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mcsim on 10.11.2016.
 */
public class DrawView extends View implements View.OnTouchListener {
    public static final String TAG = "DrawView";

    List<Point> points = new ArrayList<Point>();
    Paint paint = new Paint();
    int col_mode;
    int wid_mode;

    public DrawView(Context context) {
        super(context);

        // set default colour to white
        col_mode = Color.WHITE;

        // set default width to 10px
        wid_mode = 10;

        setFocusable(true);
        setFocusableInTouchMode(true);

        this.setOnTouchListener(this);

        paint.setAntiAlias(true);
    }

    // used to clear the screen
    public void clearPoints () {
        points.clear();
        forceRedraw();
    }

    /**
     * Force view to redraw. Without this points aren't cleared until next action
     */
    public void forceRedraw() {
        invalidate();
    }

    // used to set drawing colour
    public void changeColour (int col_in) {
        col_mode = col_in;
    }

    // used to set drawing width
    public void changeWidth (int wid_in) {
        wid_mode = wid_in;
    }

    @Override
    public void onDraw(Canvas canvas) {
        // for each point, draw on canvas
        for (Point point : points) {
            point.draw(canvas, paint);
            Log.d(TAG, "pointcount: " + points.size());
        }
    }

    public boolean onTouch(View view, MotionEvent event) {
        int new_col = col_mode;

        Point point;
        if(event.getAction() == MotionEvent.ACTION_MOVE) {
            point = new FriendlyPoint(event.getX(), event.getY(), new_col, points.get(points.size() - 1), wid_mode);
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            point = new Point(event.getX(), event.getY(), new_col, wid_mode);
        } else {
            return false;
        }
        points.add(point);
        forceRedraw();
        Log.d(TAG, "point: " + point);
        return true;
    }
}
