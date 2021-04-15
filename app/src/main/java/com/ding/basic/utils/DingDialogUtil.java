package com.ding.basic.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;

import com.ding.basic.R;
import com.ding.basic.widget.DingDialog;


/**
 * Created by dingzuoqiang on 2017/5/3.
 */

public class DingDialogUtil {

    private static final float listViewMaxHeight = 220f;
    public static final int topMarginTitle = 15;
    public static final int bottomMarginTitle = 15;// title 距上距下高度  dp
    public static final float titleTextSize = 14;//title 字体大小 sp
    public static final int titleTextColor = Color.parseColor("#666666");

    /**
     * 通用dialog
     * 有title，有Message ，使用默认字体大小、上下间距
     * 不可设置按钮文本，使用默认的
     *
     * final Context context,
     * String title
     * String content,
     * final DialogInterface.OnClickListener okListener
     * final DialogInterface.OnClickListener cancelListener
     * boolean isAutoDissmiss
     */
    public static DingDialog showDialog(final Context context, String title, String content, final DialogInterface.OnClickListener okListener, final DialogInterface.OnClickListener cancelListener, boolean isAutoDissmiss) {
        if (context == null) return null;
        return showDialog(context, title, content, context.getString(R.string.commit), context.getString(R.string.cancel), okListener, cancelListener, isAutoDissmiss);
    }

    /**
     * 通用dialog
     * 有title，有Message ，使用默认字体大小、上下间距
     * 可设置按钮文本
     * final Context context,
     * String title
     * String content,
     * String okText,
     * String cancelText,
     * final DialogInterface.OnClickListener okListener
     * final DialogInterface.OnClickListener cancelListener
     * boolean isAutoDissmiss
     */
    public static DingDialog showDialog(final Context context, String title, String content, String okText, String cancelText, final DialogInterface.OnClickListener okListener, final DialogInterface.OnClickListener cancelListener, boolean isAutoDissmiss) {
        if (context == null) return null;
        DingDialog.Builder customBuilder = new DingDialog.Builder(context);
        customBuilder.setTitle(title)
                .setMessage(content)
                .setNegativeButton(cancelText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (cancelListener != null)
                            cancelListener.onClick(dialog, which);
                    }
                })
                .setPositiveButton(okText,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if (okListener != null)
                                    okListener.onClick(dialog, which);
                            }
                        });
        DingDialog dialog = customBuilder.create();
        dialog.setCancelable(isAutoDissmiss);
        dialog.show();

        return dialog;
//        return dialog.getWindow().getDecorView();
    }

    /**
     * 通用dialog
     * 有title 使用默认字体大小、上下间距 ，没有Message
     * 使用默认按钮文本
     * final Context context,
     * String title
     * View view
     * final DialogInterface.OnClickListener okListener
     * final DialogInterface.OnClickListener cancelListener
     */
    public static DingDialog showDialogView(final Context context, String title, View view, final DialogInterface.OnClickListener okListener, final DialogInterface.OnClickListener cancelListener) {
        if (context == null) return null;
        return showDialogView(context, title, view, context.getString(R.string.commit), context.getString(R.string.cancel), okListener, cancelListener,
                DingDialogUtil.topMarginTitle, DingDialogUtil.bottomMarginTitle, DingDialog.TITLE_TEXT_SIZE);
    }

    /**
     * 通用dialog
     * 有title 且 可设置有title 字体大小、上下间距 ，没有Message
     * 可设置按钮文本
     * final Context context,
     * String title
     * View view
     * String okText,
     * String cancelText,
     * final DialogInterface.OnClickListener okListener
     * final DialogInterface.OnClickListener cancelListener
     * int topMarginTitle, int bottomMarginTitle;// title 距上距下高度  dp
     * titleTextSize ;//title 字体大小 sp
     */
    public static DingDialog showDialogView(final Context context, String title, View view, String okText, String cancelText, final DialogInterface.OnClickListener okListener, final DialogInterface.OnClickListener cancelListener, int topMarginTitle, int bottomMarginTitle, float titleTextSize) {
        if (context == null) return null;
        DingDialog.Builder customBuilder = new DingDialog.Builder(context);
        customBuilder.setTitle(title)
                .setMarginTitle(topMarginTitle, bottomMarginTitle)
                .setTitleTextSize(titleTextSize)
                .setContentView(view)
                .setNegativeButton(cancelText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (cancelListener != null)
                            cancelListener.onClick(dialog, which);
                    }
                })
                .setPositiveButton(okText,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if (okListener != null)
                                    okListener.onClick(dialog, which);
                            }
                        });
        DingDialog dialog = customBuilder.create();
        dialog.setCancelable(true);
        dialog.show();

        return dialog;
    }

    /**
     * 通用dialog
     * 没有title，有Message 且 字体大小20sp，上下间距30dp
     * 不可设置按钮文本，使用默认的
     * <p>
     * final Context context,
     * String content,
     * final DialogInterface.OnClickListener okListener
     * final DialogInterface.OnClickListener cancelListener
     * boolean isAutoDissmiss
     */
    public static DingDialog showDialogMessage(final Context context, String content, final DialogInterface.OnClickListener okListener, final DialogInterface.OnClickListener cancelListener, boolean isAutoDissmiss) {
        if (context == null) return null;
        float messageTextSize = 20f;//中间内容 字体大小
        int margin = 30;
        return showDialogMessage(context, content, okListener, cancelListener, margin, margin, messageTextSize, isAutoDissmiss);
    }

    /**
     * 通用dialog
     * 没有title，可设置Message 字体大小、上下间距
     * 不可设置按钮文本，使用默认的
     * <p>
     * final Context context,
     * String content,
     * final DialogInterface.OnClickListener okListener
     * final DialogInterface.OnClickListener cancelListener
     * topMarginMessage, bottomMarginMessage;// 中间Message 距上距下高度  dp
     * messageTextSize ;//中间内容 字体大小 sp
     * boolean isAutoDissmiss
     */
    public static DingDialog showDialogMessage(final Context context, String content, final DialogInterface.OnClickListener okListener, final DialogInterface.OnClickListener cancelListener, int topMarginMessage, int bottomMarginMessage, float messageTextSize, boolean isAutoDissmiss) {
        if (context == null) return null;
        return showDialogMessage(context, null, content, context.getString(R.string.commit), context.getString(R.string.cancel), okListener, cancelListener, topMarginMessage, bottomMarginMessage, messageTextSize, isAutoDissmiss);
    }

    /**
     * 通用dialog
     * 有title，有Message 且 可设置Message 字体大小、上下间距
     * 可设置按钮文本
     * <p>
     * final Context context,
     * String title
     * String content,
     * String okText,
     * String cancelText,
     * final DialogInterface.OnClickListener okListener
     * final DialogInterface.OnClickListener cancelListener
     * topMarginMessage, bottomMarginMessage;// 中间Message 距上距下高度  dp
     * messageTextSize ;//中间内容 字体大小 sp
     * boolean isAutoDissmiss
     */
    public static DingDialog showDialogMessage(final Context context, String title, String content, String okText, String cancelText, final DialogInterface.OnClickListener okListener, final DialogInterface.OnClickListener cancelListener, int topMarginMessage, int bottomMarginMessage, float messageTextSize, boolean isAutoDissmiss) {
        if (context == null) return null;
        DingDialog.Builder customBuilder = new DingDialog.Builder(context);
        customBuilder.setTitle(title)
                .setMessage(content)
                .setMarginMessage(topMarginMessage, bottomMarginMessage)
                .setMessageTextSize(messageTextSize)
                .setNegativeButton(cancelText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (cancelListener != null)
                            cancelListener.onClick(dialog, which);
                    }
                })
                .setPositiveButton(okText,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if (okListener != null)
                                    okListener.onClick(dialog, which);
                            }
                        });
        DingDialog dialog = customBuilder.create();
        dialog.setCancelable(isAutoDissmiss);
        dialog.show();

        return dialog;
//        return dialog.getWindow().getDecorView();
    }


//    /**
//     * 通用 底部选择 dialog  页面底部展示
//     * 有title ,使用默认设置 字体大小、上下间距 ，没有Message
//     * 可设置按钮文本
//     * <p>
//     * final Context context,
//     * String title
//     * String okText,
//     * String cancelText,
//     * final DialogInterface.OnClickListener okListener
//     * final DialogInterface.OnClickListener cancelListener
//     * final List<String> list,
//     * final OnSingleChoiceListener onSingleChoiceListener
//     * boolean showTitleBottomLine 是否展示标题下方的 分割线
//     * CharSequence topRightBtnText, // 是否展示标题右方 按钮文本
//     * final View.OnClickListener topRightBtnListener // 是否展示标题右方 按钮监听
//     */
//    public static DingDialog showSingleChoiceDialog(final Context context, String title
//            , String cancelText, final DialogInterface.OnClickListener cancelListener
//            , List<String> list, final OnSingleChoiceListener onSingleChoiceListener
//            , boolean showTitleBottomLine, CharSequence topRightBtnText, View.OnClickListener topRightBtnListener) {
//        if (context == null) return null;
//        return showSingleChoiceDialog(context, title, cancelText
//                , cancelListener, topMarginTitle, bottomMarginTitle
//                , titleTextSize, titleTextColor, list, 0
//                , onSingleChoiceListener, showTitleBottomLine
//                , topRightBtnText, topRightBtnListener);
//    }
//
//    /**
//     * 通用 底部选择 dialog  页面中间展示
//     * 有title ,使用默认设置 字体大小、上下间距 ，没有Message
//     * 可设置按钮文本
//     * <p>
//     * final Context context,
//     * String title
//     * String okText,
//     * String cancelText,
//     * final DialogInterface.OnClickListener okListener
//     * final DialogInterface.OnClickListener cancelListener
//     * final List<String> list,
//     * final OnSingleChoiceListener onSingleChoiceListener
//     * boolean showTitleBottomLine 是否展示标题下方的 分割线
//     * CharSequence topRightBtnText, // 是否展示标题右方 按钮文本
//     * final View.OnClickListener topRightBtnListener // 是否展示标题右方 按钮监听
//     */
//    public static DingDialog showSingleChoiceDialogCenter(final Context context, String title
//            , String cancelText, final DialogInterface.OnClickListener cancelListener
//            , List<String> list, final OnSingleChoiceListener onSingleChoiceListener
//            , boolean showTitleBottomLine, CharSequence topRightBtnText, View.OnClickListener topRightBtnListener) {
//        if (context == null) return null;
//        return showSingleChoiceDialogCenter(context, title, cancelText
//                , cancelListener, topMarginTitle, bottomMarginTitle
//                , titleTextSize, titleTextColor, list, 0
//                , onSingleChoiceListener, showTitleBottomLine
//                , topRightBtnText, topRightBtnListener);
//    }
//
//    /**
//     * 通用 底部选择 dialog  页面底部展示
//     * 有title 且 可设置有title 字体大小、上下间距 ，没有Message
//     * 可设置按钮文本
//     * <p>
//     * final Context context,
//     * String title
//     * String okText,
//     * String cancelText,
//     * final DialogInterface.OnClickListener okListener
//     * final DialogInterface.OnClickListener cancelListener
//     * int topMarginTitle, int bottomMarginTitle;// title 距上距下高度  dp
//     * titleTextSize ;//title 字体大小 sp
//     * int titleTextColor
//     * final List<String> list,
//     * float listViewHeightDp,
//     * final OnSingleChoiceListener onSingleChoiceListener
//     * boolean showTitleBottomLine 是否展示标题下方的 分割线
//     * CharSequence topRightBtnText, // 是否展示标题右方 按钮文本
//     * final View.OnClickListener topRightBtnListener // 是否展示标题右方 按钮监听
//     */
//    public static DingDialog showSingleChoiceDialog(final Context context, String title, String cancelText
//            , final DialogInterface.OnClickListener cancelListener, int topMarginTitle, int bottomMarginTitle
//            , float titleTextSize, int titleTextColor, final List<String> list, float listViewHeightDp
//            , final OnSingleChoiceListener onSingleChoiceListener, boolean showTitleBottomLine
//            , CharSequence topRightBtnText, final View.OnClickListener topRightBtnListener) {
//        if (context == null) return null;
//
//        return showSingleChoiceDialog(context, title, cancelText
//                , cancelListener, topMarginTitle, bottomMarginTitle
//                , titleTextSize, titleTextColor, list, listViewHeightDp
//                , onSingleChoiceListener, showTitleBottomLine
//                , topRightBtnText, topRightBtnListener, Gravity.BOTTOM);
//    }
//
//    /**
//     * 通用 底部选择 dialog  页面中间展示
//     * 有title 且 可设置有title 字体大小、上下间距 ，没有Message
//     * 可设置按钮文本
//     * <p>
//     * final Context context,
//     * String title
//     * String okText,
//     * String cancelText,
//     * final DialogInterface.OnClickListener okListener
//     * final DialogInterface.OnClickListener cancelListener
//     * int topMarginTitle, int bottomMarginTitle;// title 距上距下高度  dp
//     * titleTextSize ;//title 字体大小 sp
//     * int titleTextColor
//     * final List<String> list,
//     * float listViewHeightDp,
//     * final OnSingleChoiceListener onSingleChoiceListener
//     * boolean showTitleBottomLine 是否展示标题下方的 分割线
//     * CharSequence topRightBtnText, // 是否展示标题右方 按钮文本
//     * final View.OnClickListener topRightBtnListener // 是否展示标题右方 按钮监听
//     */
//    public static DingDialog showSingleChoiceDialogCenter(final Context context, String title, String cancelText
//            , final DialogInterface.OnClickListener cancelListener, int topMarginTitle, int bottomMarginTitle
//            , float titleTextSize, int titleTextColor, final List<String> list, float listViewHeightDp
//            , final OnSingleChoiceListener onSingleChoiceListener, boolean showTitleBottomLine
//            , CharSequence topRightBtnText, final View.OnClickListener topRightBtnListener) {
//        if (context == null) return null;
//        return showSingleChoiceDialog(context, title, cancelText
//                , cancelListener, topMarginTitle, bottomMarginTitle
//                , titleTextSize, titleTextColor, list, listViewHeightDp
//                , onSingleChoiceListener, showTitleBottomLine
//                , topRightBtnText, topRightBtnListener, Gravity.CENTER);
//    }
//
//    /**
//     * 通用 底部选择 dialog
//     * 有title 且 可设置有title 字体大小、上下间距 ，没有Message
//     * 可设置按钮文本
//     * <p>
//     * final Context context,
//     * String title
//     * String okText,
//     * String cancelText,
//     * final DialogInterface.OnClickListener okListener
//     * final DialogInterface.OnClickListener cancelListener
//     * int topMarginTitle, int bottomMarginTitle;// title 距上距下高度  dp
//     * titleTextSize ;//title 字体大小 sp
//     * int titleTextColor
//     * final List<String> list,
//     * float listViewHeightDp,
//     * final OnSingleChoiceListener onSingleChoiceListener
//     * boolean showTitleBottomLine 是否展示标题下方的 分割线
//     * CharSequence topRightBtnText, // 是否展示标题右方 按钮文本
//     * final View.OnClickListener topRightBtnListener // 是否展示标题右方 按钮监听
//     * int gravity,  展示位置 如 Gravity.BOTTOM
//     */
//    public static DingDialog showSingleChoiceDialog(final Context context, String title, String cancelText
//            , final DialogInterface.OnClickListener cancelListener, int topMarginTitle, int bottomMarginTitle
//            , float titleTextSize, int titleTextColor, final List<String> list, float listViewHeightDp
//            , final OnSingleChoiceListener onSingleChoiceListener, boolean showTitleBottomLine
//            , CharSequence topRightBtnText, final View.OnClickListener topRightBtnListener, int gravity) {
//        if (context == null) return null;
//        View view = LayoutInflater.from(context).inflate(R.layout.pop_single_choice, null);
//        ListView listView = (ListView) view.findViewById(R.id.list_pop_choice);
//
//        final SingleChoiceAdapter singleChoiceAdapter = new SingleChoiceAdapter(context, list);
//        listView.setAdapter(singleChoiceAdapter);
//
//        if (listViewHeightDp > 0) {
//            setViewHeight(context, listView, listViewHeightDp);
//        } else {
//            if (list.size() > 5)
//                setViewHeight(context, listView, listViewMaxHeight);
//        }
//
//        DingDialog.Builder customBuilder = new DingDialog.Builder(context);
//        customBuilder.setTitle(title)
//                .setShowTitleBottomLine(showTitleBottomLine)
//                .setScreenRatioX(gravity == Gravity.CENTER ? 0.8f : 1)
//                .setMarginTitle(topMarginTitle, bottomMarginTitle)
//                .setTitleTextSize(titleTextSize)
//                .setTitleTextColor(titleTextColor)
//                .setTopRightBtn(topRightBtnText, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (topRightBtnListener != null)
//                            topRightBtnListener.onClick(view);
//                    }
//                })
//                .setContentView(view)
//                .setNegativeButton(cancelText, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        if (cancelListener != null)
//                            cancelListener.onClick(dialog, which);
//                    }
//                });
//        final DingDialog dialog = customBuilder.create();
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (onSingleChoiceListener != null) {
//                    onSingleChoiceListener.onSingleChoice(dialog, position, list.get(position));
//                }
//                dialog.dismiss();
//            }
//        });
//
//        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
//        dialog.getWindow().setAttributes(params);
//        params.gravity = gravity;
//        params.windowAnimations = R.style.popWindow_animation;
//        dialog.setCancelable(true);
//        dialog.show();
//        return dialog;
//    }
//
//    public interface OnSingleChoiceListener {
//        void onSingleChoice(Dialog dialog, int position, String value);//
//    }
//
//    private static void setViewHeight(Context context, View view, float dpValue) {
//        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
//        layoutParams.height = CommonUtil.dip2px(context, dpValue);
//        view.setLayoutParams(layoutParams);
//    }
}
