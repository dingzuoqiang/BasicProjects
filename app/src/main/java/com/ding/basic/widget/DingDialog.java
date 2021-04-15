package com.ding.basic.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ding.basic.R;


public class DingDialog extends Dialog {

    public static final float TITLE_TEXT_SIZE = 20f;//默认标题 字体大小
    public static final float MESSAGE_TEXT_SIZE = 14f;//默认中间内容 字体大小

    private static TextView positiveButton, negativeButton, tvTopRightBtn;

    public DingDialog(Context context, int theme) {
        super(context, theme);
    }

    public DingDialog(Context context) {
        super(context);
    }

    public TextView getButton(int whichButton) {
        switch (whichButton) {
            case BUTTON_POSITIVE:
                return positiveButton;
            case BUTTON_NEGATIVE:
                return negativeButton;
            case BUTTON_NEUTRAL:
                return tvTopRightBtn;
            default:
                return null;
        }
    }

    /**
     * Helper class for creating a custom dialog
     */
    public static class Builder {

        private Context context;
        private CharSequence title;
        private CharSequence message;
        private CharSequence positiveButtonText;
        private CharSequence negativeButtonText;
        private CharSequence topRightBtnText; // 是否展示标题右方 按钮文本
        private View contentView;
        private float screenRatioX = 0.8f;//dialog 宽度 占屏比  0-1
        private float titleTextSize = 0f;//标题 字体大小
        private int titleTextColor = 0;//标题 字体颜色
        private float messageTextSize = 0f;//中间内容 字体大小
        private int padding15;
        private int topMarginTitle, bottomMarginTitle;// 标题 距上距下高度 px
        private int topMarginMessage, bottomMarginMessage;// 中间Message 距上距下高度 px
        private boolean showTitleBottomLine;// 是否展示标题下方的 分割线
        private View.OnClickListener topRightBtnListener;// 是否展示标题右方 按钮监听
        private OnClickListener positiveButtonClickListener, negativeButtonClickListener;

        public Builder(Context context) {
            this.context = context;
            if (context != null) {
                padding15 = dip2px(context, 15);
                topMarginTitle = padding15;
                bottomMarginTitle = 0;
                topMarginMessage = padding15;
                bottomMarginMessage = padding15;
            }
        }

        public Builder setScreenRatioX(float screenRatioX) {
            this.screenRatioX = screenRatioX;
            return this;
        }

        public Builder setTitleTextSize(float titleTextSize) {
            this.titleTextSize = titleTextSize;
            return this;
        }

        public Builder setTitleTextColor(int titleTextColor) {
            this.titleTextColor = titleTextColor;
            return this;
        }

        public Builder setMessageTextSize(float messageTextSize) {
            this.messageTextSize = messageTextSize;
            return this;
        }

        // dp
        public Builder setTopMarginTitle(int topMarginTitle) {
            this.topMarginTitle = dip2px(context, topMarginTitle);
            return this;
        }

        // dp
        public Builder setBottomMarginTitle(int bottomMarginTitle) {
            this.bottomMarginTitle = dip2px(context, bottomMarginTitle);
            return this;
        }

        // dp
        public Builder setMarginTitle(int topMarginTitle, int bottomMarginTitle) {
            this.topMarginTitle = dip2px(context, topMarginTitle);
            this.bottomMarginTitle = dip2px(context, bottomMarginTitle);
            return this;
        }

        // dp
        public Builder setTopMarginMessage(int topMarginMessage) {
            this.topMarginMessage = dip2px(context, topMarginMessage);
            return this;
        }

        // dp
        public Builder setBottomMarginMessage(int bottomMarginMessage) {
            this.bottomMarginMessage = dip2px(context, bottomMarginMessage);
            return this;
        }

        // dp
        public Builder setMarginMessage(int topMarginMessage, int bottomMarginMessage) {
            this.topMarginMessage = dip2px(context, topMarginMessage);
            this.bottomMarginMessage = dip2px(context, bottomMarginMessage);
            return this;
        }

        /**
         * Set the Dialog message from CharSequence
         *
         * @param message
         * @return
         */
        public Builder setMessage(CharSequence message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param message
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from CharSequence
         *
         * @param title
         * @return
         */
        public Builder setTitle(CharSequence title) {
            this.title = title;
            return this;
        }

        /**
         * Set a custom content view for the Dialog.
         * If a message is set, the contentView is not
         * added to the Dialog...
         *
         * @param v
         * @return
         */
        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = (String) context.getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * Set the positive button text and it's listener
         *
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(CharSequence positiveButtonText, OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * Set the negative button resource and it's listener
         *
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(int negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = (String) context.getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * Set the negative button text and it's listener
         *
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(CharSequence negativeButtonText, OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setTopRightBtn(CharSequence topRightBtnText, View.OnClickListener topRightBtnListener) {
            this.topRightBtnText = topRightBtnText;
            this.topRightBtnListener = topRightBtnListener;
            return this;
        }

        public Builder setShowTitleBottomLine(boolean showTitleBottomLine) {
            this.showTitleBottomLine = showTitleBottomLine;
            return this;
        }

        /**
         * Create the custom dialog
         */
        public DingDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final DingDialog dialog = new DingDialog(context, R.style.dzq_dialog);

            View layout = inflater.inflate(R.layout.lay_ding_dialog, null);
            dialog.addContentView(layout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            TextView tvTitle = (TextView) layout.findViewById(R.id.tv_title);
            TextView tvMessage = (TextView) layout.findViewById(R.id.tv_content);
            tvTopRightBtn = (TextView) layout.findViewById(R.id.tv_top_right_btn);

            tvTitle.setTextSize(TITLE_TEXT_SIZE);
            tvMessage.setTextSize(MESSAGE_TEXT_SIZE);
            layout.findViewById(R.id.v_title_bottom_line).setVisibility(showTitleBottomLine ? View.VISIBLE : View.GONE);

            positiveButton = (TextView) layout.findViewById(R.id.tv_ok);
            negativeButton = (TextView) layout.findViewById(R.id.tv_cancel);

            // set the dialog title
            if (!TextUtils.isEmpty(title)) {
                if (titleTextColor != 0)
                    tvTitle.setTextColor(titleTextColor);
                if (titleTextSize > 0)
                    tvTitle.setTextSize(titleTextSize);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) tvTitle.getLayoutParams();
                layoutParams.topMargin = topMarginTitle;
                layoutParams.bottomMargin = bottomMarginTitle;
                tvTitle.setText(title);
            } else {
                // if no title just set the visibility to  GONE
                tvTitle.setVisibility(View.GONE);
            }
            // set the dialog  top right btn
            if (topRightBtnText != null) {
                tvTopRightBtn.setVisibility(View.VISIBLE);
                tvTopRightBtn.setText(topRightBtnText);
                tvTopRightBtn.setOnClickListener(topRightBtnListener);
            } else {
                // if no title just set the visibility to  GONE
                tvTopRightBtn.setVisibility(View.GONE);
            }

            // set the content message
            if (!TextUtils.isEmpty(message)) {
                if (messageTextSize > 0)
                    tvMessage.setTextSize(messageTextSize);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tvMessage.getLayoutParams();
                layoutParams.topMargin = topMarginMessage;
                layoutParams.bottomMargin = bottomMarginMessage;
                tvMessage.setText(message);
            } else {
                tvMessage.setVisibility(View.GONE);
            }
            if (contentView != null) {
                // if no message set
                // add the contentView to the dialog body
                ((LinearLayout) layout.findViewById(R.id.lay_content)).removeAllViewsInLayout();
//                ((LinearLayout) layout.findViewById(R.id.lay_content)).addView(contentView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                ((LinearLayout) layout.findViewById(R.id.lay_content)).addView(contentView);

            }

            // set the confirm button
            if (!TextUtils.isEmpty(positiveButtonText)) {
                positiveButton.setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    positiveButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                positiveButton.setVisibility(View.GONE);
            }

            // set the cancel button
            if (!TextUtils.isEmpty(negativeButtonText)) {
                negativeButton.setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    negativeButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                        }
                    });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                negativeButton.setVisibility(View.GONE);
            }

            if (TextUtils.isEmpty(positiveButtonText) || TextUtils.isEmpty(negativeButtonText))
                layout.findViewById(R.id.v_line).setVisibility(View.GONE);
            if (TextUtils.isEmpty(positiveButtonText) && TextUtils.isEmpty(negativeButtonText)) {
                layout.findViewById(R.id.v_line2).setVisibility(View.GONE);
                layout.findViewById(R.id.lay_btn).setVisibility(View.GONE);
            }

            dialog.setContentView(layout);
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.width = (int) (getScreenWidth(context) * screenRatioX);
//            params.height = 1000;
            dialog.getWindow().setAttributes(params);

            return dialog;
        }

    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }


}