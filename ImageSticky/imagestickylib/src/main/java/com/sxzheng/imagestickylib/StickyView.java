package com.sxzheng.imagestickylib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class StickyView extends View implements DragRotate {
    private static final float MAX_SCALE = 2.0f;
    private static final int MODE_NONE = 0x0;
    private static final int MODE_DRAG = 0x1;
    private static final int MODE_ZOOM_OR_ROTATE = 0x2;
    private static final int MODE_DELETE = 0x4;
    private static final int CLICK_DEL = 1;
    private static final int CLICK_DRAG = 2;
    private static final int CLICK_NONE = 0;
    private float mAngle;
    private float mScaleCoefficient;
    private int wW = 0, wH = 0;
    private int rotatedImageW;
    private int rotatedImageH;
    private int viewW;
    private int viewH;
    private int viewL;
    private int viewT;
    private int mMode = MODE_NONE;
    private int mDeltaX, mDeltaY;
    private int mColor;
    private Matrix mMatrix = new Matrix();
    private PointF mPointA = new PointF();
    private PointF mPointB = new PointF();
    private Point mIconP1, mIconP2;
    private Point np1;
    private Point np2;
    private Point np3;
    private Point np4;
    private Point centrePoint;
    private Context mContext;
    private Paint paint;
    private Bitmap mBitmap;
    private Bitmap delIcon, rotateIcon;
    private Bitmap srcBitmap;
    private boolean isHiddenIcon = false;

    public StickyView(Context context) {
        super(context);
        init(context);
    }

    public StickyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StickyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }


    public void init(Context context) {
        mColor = getResources().getColor(R.color.green_background_02c7c5);
        mContext = context;
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paint.setStyle(Paint.Style.STROKE);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img);
        delIcon =
                BitmapFactory.decodeResource(getResources(), R.drawable.delete_strick);
        rotateIcon =
                BitmapFactory.decodeResource(getResources(), R.drawable.scale_strick);
        wW = delIcon.getWidth() / 2;
        wH = delIcon.getHeight() / 2;
        setImageBitmap(mBitmap, new Point(getWidth() / 2, getHeight() / 2), 30, 0.2f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!isHiddenIcon) {
            this.paint.setColor(mColor);
            this.paint.setStrokeWidth(4.0f);
        /*Draw circle.*/
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - 5,
                    paint);

        /*Draw rectangle*/
            //        canvas.drawLine(np1.x, np1.y, np2.x, np2.y, paint);
            //        canvas.drawLine(np2.x, np2.y, np3.x, np3.y, paint);
            //        canvas.drawLine(np3.x, np3.y, np4.x, np4.y, paint);
            //        canvas.drawLine(np4.x, np4.y, np1.x, np1.y, paint);
            canvas.drawBitmap(this.delIcon, mIconP1.x - wW, mIconP1.y - wH, paint);
            canvas.drawBitmap(this.rotateIcon, mIconP2.x - wW, mIconP2.y - wH, paint);
        }

        // canvas.drawBitmap(srcBitmap, wW, wH, paint);
        canvas.drawBitmap(mBitmap, mMatrix, paint);
        setViewLayout(rotatedImageW, rotatedImageH, centrePoint.x - rotatedImageW / 2,
                centrePoint.y - rotatedImageH / 2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mPointA.set(event.getX() + viewL, event.getY() + viewT);
                // mPointB.set(event.getX(), event.getY());
                judgeActionMode(event);
                break;
            case MotionEvent.ACTION_MOVE:
                doTouchAction(event);
                break;
            case MotionEvent.ACTION_UP:
                cancel();
                break;
        }
        return true;
    }

    @Override
    public void setViewLayout(int w, int h, int l, int t) {
        int nviewW = w + wW * 2;
        int nviewH = h + wH * 2;
        int nviewL = l - wW;
        int nviewT = t - wH;
        viewW = nviewW;
        viewH = nviewH;
        viewL = nviewL;
        viewT = nviewT;
        this.layout(viewL, viewT, viewL + viewW, viewT + viewH);
    }

    @Override
    public void setCentrePoint(Point c) {
        centrePoint = c;
        setViewLayout(rotatedImageW, rotatedImageH, centrePoint.x - rotatedImageW / 2,
                centrePoint.y - rotatedImageH / 2);
    }

    @Override
    public void setImageBitmap(Bitmap bmp, Point pt, float angle,
            float scaleCoefficient) {
        mBitmap.recycle();
        redrawBitmap(bmp, pt, angle, scaleCoefficient);
    }

    @Override
    public void cancel() {
        if (mMode == MODE_DELETE) {
            setVisibility(GONE);
        }
    }

    @Override
    public void hideIcon(boolean bl) {
        isHiddenIcon = bl;
        invalidate();
    }

    private void judgeActionMode(MotionEvent event) {
        int actionMode = getIconClick((int) event.getX(), (int) event.getY());
        if (actionMode == CLICK_DRAG) {
            mMode = MODE_ZOOM_OR_ROTATE;
        } else if (actionMode == CLICK_DEL) {
            mMode = MODE_DELETE;
        } else {
            mMode = MODE_DRAG;
        }
    }

    private void redrawBitmap(Bitmap bmp, Point pt, float angle,
            float scaleCoefficient) {
        mBitmap = bmp;
        centrePoint = pt;
        this.mAngle = angle;
        this.mScaleCoefficient = scaleCoefficient;
        drawRectR(0, 0, (int) (mBitmap.getWidth() * this.mScaleCoefficient),
                (int) (mBitmap.getHeight() * this.mScaleCoefficient), angle);
        mMatrix.reset();
        mMatrix.setScale(scaleCoefficient, scaleCoefficient);
        mMatrix.postRotate(angle % 360, mBitmap.getWidth() * scaleCoefficient / 2,
                mBitmap.getHeight() * scaleCoefficient / 2);
        mMatrix.postTranslate(mDeltaX + wW, mDeltaY + wH);
        setViewLayout(rotatedImageW, rotatedImageH, centrePoint.x - rotatedImageW / 2,
                centrePoint.y - rotatedImageH / 2);
        this.postInvalidate();
    }

    private void doTouchAction(MotionEvent event) {
        if (mMode == MODE_ZOOM_OR_ROTATE) {
            float scaleCfft = 1f;
            // float newx=event.getX();
            // float newy=event.getY();
            mPointB.set(event.getX() + viewL, event.getY() + viewT);
            float realL = (float) Math
                    .sqrt((float) (mBitmap.getWidth() * mBitmap.getWidth() +
                            mBitmap.getHeight() * mBitmap.getHeight()) / 4);
            float newL = (float) Math.sqrt((mPointB.x - (float) centrePoint.x) *
                    (mPointB.x - (float) centrePoint.x) +
                    (mPointB.y - (float) centrePoint.y) *
                            (mPointB.y - (float) centrePoint.y));
            scaleCfft = newL / realL;
            double degree = computeDegree();
            float newAngle = (float) (degree / Math.PI * 180);

            float p1x = mPointA.x - (float) centrePoint.x;
            float p2x = mPointB.x - (float) centrePoint.x;

            float p1y = mPointA.y - (float) centrePoint.y;
            float p2y = mPointB.y - (float) centrePoint.y;
            if (p1x == 0) {
                if (p2x > 0 && p1y >= 0 && p2y >= 0) {
                    newAngle = -newAngle;
                } else if (p2x < 0 && p1y < 0 && p2y < 0) {
                    newAngle = -newAngle;
                }
            } else if (p2x == 0) {
                if (p1x < 0 && p1y >= 0 && p2y >= 0) {
                    newAngle = -newAngle;
                } else if (p1x > 0 && p1y < 0 && p2y < 0) {
                    newAngle = -newAngle;
                }
            } else if (p1x != 0 && p2x != 0 && p1y / p1x < p2y / p2x) {
                if (p1x < 0 && p2x > 0 && p1y >= 0 && p2y >= 0) {
                    newAngle = -newAngle;
                } else if (p2x < 0 && p1x > 0 && p1y < 0 && p2y < 0) {
                    newAngle = -newAngle;
                } else {

                }
            } else {
                if (p2x < 0 && p1x > 0 && p1y >= 0 && p2y >= 0) {//3-4

                } else if (p2x > 0 && p1x < 0 && p1y < 0 && p2y < 0) {//1-2

                } else {
                    newAngle = -newAngle;
                }
            }
            mPointA.x = mPointB.x;
            mPointA.y = mPointB.y;
            if (scaleCfft == 0) {
                scaleCfft = 0.1f;
            } else if (scaleCfft >= 3) {
                scaleCfft = 3f;
            }
            redrawBitmap(this.mBitmap, centrePoint, mAngle + newAngle, scaleCfft);
        }

        if (mMode == MODE_DRAG) {
            mPointB.set(event.getX() + viewL, event.getY() + viewT);
            centrePoint.x += mPointB.x - mPointA.x;
            centrePoint.y += mPointB.y - mPointA.y;
            mPointA.x = mPointB.x;
            mPointA.y = mPointB.y;
            setCentrePoint(centrePoint);
        }
    }

    public void drawRectR(int l, int t, int r, int b, float angle) {
        Point p1 = new Point(l, t);
        Point p2 = new Point(r, t);
        Point p3 = new Point(r, b);
        Point p4 = new Point(l, b);
        Point tp = new Point((l + r) / 2, (t + b) / 2);
        np1 = rotationPoint(tp, p1, angle);
        np2 = rotationPoint(tp, p2, angle);
        np3 = rotationPoint(tp, p3, angle);
        np4 = rotationPoint(tp, p4, angle);
        int w;
        int h;
        int maxn;
        int mixn;
        maxn = np1.x;
        mixn = np1.x;
        if (np2.x > maxn) {
            maxn = np2.x;
        }
        if (np3.x > maxn) {
            maxn = np3.x;
        }
        if (np4.x > maxn) {
            maxn = np4.x;
        }

        if (np2.x < mixn) {
            mixn = np2.x;
        }
        if (np3.x < mixn) {
            mixn = np3.x;
        }
        if (np4.x < mixn) {
            mixn = np4.x;
        }
        w = maxn - mixn;

        maxn = np1.y;
        mixn = np1.y;
        if (np2.y > maxn) {
            maxn = np2.y;
        }
        if (np3.y > maxn) {
            maxn = np3.y;
        }
        if (np4.y > maxn) {
            maxn = np4.y;
        }

        if (np2.y < mixn) {
            mixn = np2.y;
        }
        if (np3.y < mixn) {
            mixn = np3.y;
        }
        if (np4.y < mixn) {
            mixn = np4.y;
        }

        h = maxn - mixn;
        Point npc = intersects(np4, np2, np1, np3);
        mDeltaX = w / 2 - npc.x;
        mDeltaY = h / 2 - npc.y;
        np1.x = np1.x + mDeltaX + wW;
        np2.x = np2.x + mDeltaX + wW;
        np3.x = np3.x + mDeltaX + wW;
        np4.x = np4.x + mDeltaX + wW;

        np1.y = np1.y + mDeltaY + wH;
        np2.y = np2.y + mDeltaY + wH;
        np3.y = np3.y + mDeltaY + wH;
        np4.y = np4.y + mDeltaY + wH;
        rotatedImageW = w;
        rotatedImageH = h;
        mIconP1 = np1;
        mIconP2 = np3;
    }

    private Point rotationPoint(Point rotateCentre, Point pointRotate, float degree) {
        pointRotate.x = pointRotate.x - rotateCentre.x;
        pointRotate.y = pointRotate.y - rotateCentre.y;
        double alpha = 0;
        double beta = 0;
        Point result = new Point();
        double dis = Math.sqrt(
                pointRotate.x * pointRotate.x + pointRotate.y * pointRotate.y);
        if (pointRotate.x == 0 && pointRotate.y == 0) {
            return rotateCentre;
        } else if (pointRotate.x >= 0 && pointRotate.y >= 0) {
            alpha = Math.asin(pointRotate.y / dis);
        } else if (pointRotate.x < 0 && pointRotate.y >= 0) {
            alpha = Math.asin(Math.abs(pointRotate.x) / dis);
            alpha = alpha + Math.PI / 2;
        } else if (pointRotate.x < 0 && pointRotate.y < 0) {
            alpha = Math.asin(Math.abs(pointRotate.y) / dis);
            alpha = alpha + Math.PI;
        } else if (pointRotate.x >= 0 && pointRotate.y < 0) {
            alpha = Math.asin(pointRotate.x / dis);
            alpha = alpha + Math.PI * 3 / 2;
        }
        alpha = alpha * 180 / Math.PI;
        beta = alpha + degree;
        beta = beta * Math.PI / 180;
        result.x = (int) Math.round(dis * Math.cos(beta));
        result.y = (int) Math.round(dis * Math.sin(beta));
        result.x += rotateCentre.x;
        result.y += rotateCentre.y;

        return result;
    }

    public Point intersects(Point sp3, Point sp4, Point sp1, Point sp2) {
        Point localPoint = new Point(0, 0);
        double num =
                (sp4.y - sp3.y) * (sp3.x - sp1.x) - (sp4.x - sp3.x) * (sp3.y - sp1.y);
        double denom =
                (sp4.y - sp3.y) * (sp2.x - sp1.x) - (sp4.x - sp3.x) * (sp2.y - sp1.y);
        localPoint.x = (int) (sp1.x + (sp2.x - sp1.x) * num / denom);
        localPoint.y = (int) (sp1.y + (sp2.y - sp1.y) * num / denom);
        return localPoint;
    }

    public int getIconClick(int x, int y) {
        int xx = x;
        int yy = y;
        int kk1 = ((xx - mIconP1.x) * (xx - mIconP1.x) +
                (yy - mIconP1.y) * (yy - mIconP1.y));
        int kk2 = ((xx - mIconP2.x) * (xx - mIconP2.x) +
                (yy - mIconP2.y) * (yy - mIconP2.y));
        if (kk1 < wW * wW) {
            return CLICK_DEL;
        } else if (kk2 < wW * wW) {
            return CLICK_DRAG;
        }
        return CLICK_NONE;
    }

    private double computeDegree() {
        double a = computeDistance2Point(mPointA.x, mPointA.y, (float) centrePoint.x,
                (float) centrePoint.y);
        double b = computeDistance2Point(mPointB.x, mPointB.y, mPointA.x, mPointA.y);
        double c = computeDistance2Point(mPointB.x, mPointB.y, (float) centrePoint.x,
                (float) centrePoint.y);
        double cosB = (a * a + c * c - b * b) / (2 * a * c);
        if (cosB > 1) {
            cosB = 1f;
        }
        return Math.acos(cosB);
    }

    private float computeDistance2Point(float x1, float y1, float x2, float y2) {
        float x = x1 - x2;
        float y = y1 - y2;
        return (float) Math.sqrt(x * x + y * y);
    }
}
