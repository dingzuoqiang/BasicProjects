package com.ding.basic.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ding.basic.R;


/**
 * title bar
 */
public class BaseTitleBar extends RelativeLayout {

    protected ImageView imvLeftBack, imvShare, imvCollect;
    protected TextView tvTitle;
    protected TextView tvRightText;
    protected FrameLayout layLeftBack;

    public BaseTitleBar(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }

    public BaseTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BaseTitleBar(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.include_layout_title, this);
        setBackgroundResource(R.color.color_actionbar_bg);
        imvLeftBack = (ImageView) findViewById(R.id.imv_left_back);
        imvShare = (ImageView) findViewById(R.id.imv_share);
        imvCollect = (ImageView) findViewById(R.id.imv_collect);
        tvTitle = (TextView) findViewById(R.id.tv_title_text);
        tvRightText = (TextView) findViewById(R.id.title_right_text);
        layLeftBack = (FrameLayout) findViewById(R.id.lay_title_image_left_back);

        parseStyle(context, attrs);
    }

    private void parseStyle(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BaseTitleBar);
            String title = ta.getString(R.styleable.BaseTitleBar_btbTitle);
            if (null != title)
                tvTitle.setText(title);

            Drawable leftDrawable = ta.getDrawable(R.styleable.BaseTitleBar_btbLeftImage);
            if (null != leftDrawable) {
                imvLeftBack.setImageDrawable(leftDrawable);
            }
            Drawable collectDrawable = ta.getDrawable(R.styleable.BaseTitleBar_btbCollectImage);
            if (null != collectDrawable) {
                imvCollect.setImageDrawable(collectDrawable);
                imvCollect.setVisibility(VISIBLE);
            }
            Drawable shareDrawable = ta.getDrawable(R.styleable.BaseTitleBar_btbShareImage);
            if (null != shareDrawable) {
                imvShare.setImageDrawable(shareDrawable);
                imvShare.setVisibility(VISIBLE);
            }

            Drawable background = ta.getDrawable(R.styleable.BaseTitleBar_btbBackground);
            if (null != background) {
                this.setBackgroundDrawable(background);
            }

            ta.recycle();
        }
    }


    public void setLayLeftBackClickListener(OnClickListener listener) {
        layLeftBack.setOnClickListener(listener);
    }

    public void setImvCollectClickListener(OnClickListener listener) {
        imvCollect.setOnClickListener(listener);
    }

    public void setImvShareClickListener(OnClickListener listener) {
        imvShare.setOnClickListener(listener);
    }

    public void setTvRightTextClickListener(OnClickListener listener) {
        tvRightText.setOnClickListener(listener);
    }

    public void setLeftImageResource(int resId) {
        imvLeftBack.setImageResource(resId);
    }

    public void setCollectImageResource(int resId) {
        imvCollect.setImageResource(resId);
        imvCollect.setVisibility(VISIBLE);
    }

    public void setShareImageResource(int resId) {
        imvShare.setImageResource(resId);
        imvShare.setVisibility(VISIBLE);
    }

    public void setTitle(CharSequence title) {
        tvTitle.setText(title);
    }

    public void setTvRightText(CharSequence title) {
        tvRightText.setText(title);
        tvRightText.setVisibility(VISIBLE);
    }

    /**
     * @param isEnable 是否可点击
     */
    public void setTvRightEnable(Boolean isEnable) {
        tvRightText.setEnabled(isEnable);
        tvRightText.setVisibility(VISIBLE);
    }


    /**
     * @param idColor 字体颜色
     */
    public void setTvRightTextColor(int idColor) {
        tvRightText.setTextColor(idColor);
        tvRightText.setVisibility(VISIBLE);
    }


    public ImageView getImvLeftBack() {
        return imvLeftBack;
    }

    public ImageView getImvShare() {
        return imvShare;
    }

    public ImageView getImvCollect() {
        return imvCollect;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public TextView getTvRightText() {
        return tvRightText;
    }

    public FrameLayout getLayLeftBack() {
        return layLeftBack;
    }
}
