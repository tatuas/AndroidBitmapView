package com.tatuas.android.bitmapview;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.ImageView;

public class BitmapView extends ImageView {
    private Bitmap bitmap;
    private File file;
    private Bitmap.Config config = Bitmap.Config.ARGB_8888;
    private final String NAMESPACE = "http://tatuas.com/android/BitmapView";

    public BitmapView(Context context) {
        super(context);
    }

    public BitmapView(Context context, String path) {
        super(context);
        createFile(path);
    }

    public BitmapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        String path = attrs.getAttributeValue(NAMESPACE, "pictureFilePath");
        createFile(path);
    }

    public BitmapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        String path = attrs.getAttributeValue(NAMESPACE, "pictureFilePath");
        createFile(path);
    }

    public void setImageConfig(Bitmap.Config config) {
        this.config = config;
    }

    private void createFile(String path) {
        if (path != null) {
            file = new File(path);
        }
    }

    public void setImageFromFilePath(String path) {
        setImage(path, getWidth(), getHeight());
    }

    public void setImageFromFilePath(String path, int imageWidthDp,
            int imageHeightDp) {
        setImage(path, imageWidthDp, imageHeightDp);
    }

    private void setImage(String path, int widthDp, int heightDp) {
        setImageBitmap(null);
        refreshDrawableState();

        createFile(path);

        if (file == null) {
            return;
        }

        if (!file.exists()) {
            return;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        options.inSampleSize = calculateInSampleSize(options, widthDp, heightDp);
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = config;

        bitmap = BitmapFactory.decodeFile(path, options);
        setImageBitmap(bitmap);
        refreshDrawableState();

    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public String getPicturePath() {
        if (file != null) {
            return file.getAbsolutePath();
        } else {
            return null;
        }
    }

    private int calculateInSampleSize(BitmapFactory.Options options,
            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        reqWidth = convertDpToPx(reqWidth);
        reqHeight = convertDpToPx(reqHeight);

        if (height > reqHeight || width > reqWidth) {
            final int calcHeight = height;
            final int calcWidth = width;

            while ((calcHeight / inSampleSize) > reqHeight
                    || (calcWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public int convertPxToDp(int value) {
        return (int) (value / getDisplayMetrics().density);
    }

    public int convertDpToPx(int value) {
        return (int) (value * getDisplayMetrics().density);
    }

    private DisplayMetrics getDisplayMetrics() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    @Override
    protected void onDetachedFromWindow() {
        setImageDrawable(null);
        if (bitmap != null) {
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
        super.onDetachedFromWindow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (file != null) {
            if (getWidth() > 0 && getHeight() > 0) {
                setImage(file.getAbsolutePath(), getWidth(), getHeight());
                file = null;
            }
        }
        // setMeasuredDimension(getWidth(), getHeight());
    }
}
