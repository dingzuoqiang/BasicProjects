package com.ding.basic.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.ding.basic.R;
import com.ding.basic.app.MyApplicaion;


/**
 * Created by dingzuoqiang on 2021/5/3.
 * 自定义Dialog 工具类
 */
public class CustomDialogUtils {

    /**
     * contentView:  setContentView
     * gravity: Gravity.CENTER, Gravity.BOTTOM
     * anim: true 有动画效果
     **/
    public static Dialog createDialog(Context mContext, View contentView, int gravity, boolean anim) {
        return createDialog(mContext, contentView, gravity, anim, true);
    }

    /**
     * contentView:  setContentView
     * gravity: Gravity.CENTER, Gravity.BOTTOM
     * anim: true 有动画效果
     * cancelOnTouch
     **/
    public static Dialog createDialog(Context mContext, View contentView, int gravity, boolean anim, boolean cancelOnTouch) {
        return createDialog(mContext, contentView, gravity, anim, cancelOnTouch, true);
    }

    /**
     * contentView:  setContentView
     * gravity: Gravity.CENTER, Gravity.BOTTOM
     * anim: true 有动画效果
     * cancelOnTouch
     * boolean cancelable, true 点击物理返回键，dialog 消失
     **/
    public static Dialog createDialog(Context mContext, View contentView, int gravity, boolean anim, boolean cancelOnTouch, boolean cancelable) {
        return createDialog(mContext, contentView, gravity, anim, cancelOnTouch, cancelable, true);
    }

    /**
     * contentView:  setContentView
     * gravity: Gravity.CENTER, Gravity.BOTTOM
     * anim: true 有动画效果
     * cancelOnTouch
     * boolean cancelable, true 点击物理返回键，dialog 消失
     **/
    public static Dialog createDialog(Context mContext, View contentView, int gravity, boolean anim, boolean cancelOnTouch, boolean cancelable, boolean show) {
        final Dialog dialog = new Dialog(mContext, anim ? R.style.DialogTheme_Anim : R.style.DialogTheme);
        dialog.setCanceledOnTouchOutside(cancelOnTouch);
        dialog.setCancelable(cancelable);
        if (contentView != null)
            dialog.setContentView(contentView);
        Window window = dialog.getWindow();
        if (gravity != 0)
            window.setGravity(gravity);
//        //设置弹出动画
//        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (show)
            dialog.show();
        View cancel = dialog.findViewById(R.id.tv_cancel);
        if (cancel != null)
            cancel.setOnClickListener(view -> {
                dialog.dismiss();
            });
        return dialog;
    }

    /**
     * 通用dialog，可设置标题、内容、两个按钮点击事件(按钮文案默认：取消，确认),  cancelOnTouch = true
     * Context mContext
     * CharSequence title
     * CharSequence desc
     * final View.OnClickListener cancelListener
     * final View.OnClickListener okListener
     */
    public static Dialog showDialog(Context mContext, CharSequence title, CharSequence desc, final View.OnClickListener cancelListener, final View.OnClickListener okListener) {
        Context context = mContext == null ? MyApplicaion.getContext() : mContext;

        return showDialog(mContext, title, desc, context.getResources().getString(R.string.cancel), context.getResources().getString(R.string.commit), cancelListener, okListener, true);
    }

    /**
     * 通用dialog，可设置标题、内容、两个按钮文案以及点击事件,  cancelOnTouch = true
     * Context mContext
     * CharSequence title
     * CharSequence desc
     * CharSequence cancel
     * CharSequence ok
     * final View.OnClickListener cancelListener
     * final View.OnClickListener okListener
     */
    public static Dialog showDialog(Context mContext, CharSequence title, CharSequence desc, CharSequence cancel, CharSequence ok, final View.OnClickListener cancelListener, final View.OnClickListener okListener) {
        return showDialog(mContext, title, desc, cancel, ok, cancelListener, okListener, true);
    }

    /**
     * 通用dialog，可设置标题、内容、两个按钮文案以及点击事件,  cancelOnTouch
     * Context mContext
     * CharSequence title
     * CharSequence desc
     * CharSequence cancel
     * CharSequence ok
     * final View.OnClickListener cancelListener
     * final View.OnClickListener okListener
     * boolean cancelOnTouch
     */
    public static Dialog showDialog(Context mContext, CharSequence title, CharSequence desc, CharSequence cancel, CharSequence ok, final View.OnClickListener cancelListener, final View.OnClickListener okListener, boolean cancelOnTouch) {
        Context context = mContext == null ? MyApplicaion.getContext() : mContext;
        View view = View.inflate(context, R.layout.lay_custom_dialog, null);
        final Dialog dialog = createDialog(context, view, Gravity.CENTER, false, cancelOnTouch, cancelOnTouch);
        TextView tvTitle = dialog.findViewById(R.id.tv_title);
        TextView tvContent = dialog.findViewById(R.id.tv_content);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvOk = dialog.findViewById(R.id.tv_ok);
        View hLine = dialog.findViewById(R.id.hLine);
        hLine.setVisibility((TextUtils.isEmpty(cancel) || TextUtils.isEmpty(ok)) ? View.GONE : View.VISIBLE);

        if (TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setText(title);
        }

        if (TextUtils.isEmpty(desc)) {
            tvContent.setVisibility(View.GONE);
        } else {
            tvContent.setText(desc);
        }

        if (TextUtils.isEmpty(cancel)) {
            tvCancel.setVisibility(View.GONE);
        } else {
            tvCancel.setText(cancel);
        }

        if (TextUtils.isEmpty(ok)) {
            tvOk.setVisibility(View.GONE);
        } else {
            tvOk.setText(ok);
        }

        tvCancel.setOnClickListener(view1 -> {
            dialog.dismiss();
            if (null != cancelListener) {
                cancelListener.onClick(view1);
            }
        });

        tvOk.setOnClickListener(view1 -> {
            dialog.dismiss();
            if (null != okListener) {
                okListener.onClick(view1);
            }
        });

        return dialog;
    }

}
