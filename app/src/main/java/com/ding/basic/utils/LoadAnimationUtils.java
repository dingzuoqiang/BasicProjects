package com.ding.basic.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ding.basic.R;

import java.lang.ref.WeakReference;

import static android.view.View.GONE;

/**
 * Created by dingzuoqiang on 2017/04/26.
 */
public class LoadAnimationUtils {
    public Dialog processDialog;
    private ImageView view;
    private TextView tv;
    private Context mContext;

    public LoadAnimationUtils(Context context) {
//        mContext = context;
        WeakReference<Context> weakContext = new WeakReference<>(context);
        mContext = weakContext.get();
    }


    public Dialog showProcess(String content) {
        if (mContext == null) return null;
        if (processDialog == null) {
            processDialog = new Dialog(mContext, R.style.progress_dialog);
            View layout = LayoutInflater.from(mContext).inflate(R.layout.custom_loading_view, null);
            tv = layout.findViewById(R.id.custom_toast_tv);
//        view = layout.findViewById(R.id.custom_toast_iv);
//        final AnimationDrawable loadingDw = ((AnimationDrawable) view
//                .getDrawable());
//        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//                loadingDw.start();
//                return true; // 必须要有这个true返回
//            }
//        });
            processDialog.setContentView(layout);
//        processDialog.setCanceledOnTouchOutside(false);
            processDialog.setCancelable(false);
            processDialog.setOnKeyListener(new OnKeyListener() {

                @Override
                public boolean onKey(DialogInterface arg0, int keyCode,
                                     KeyEvent arg2) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        processDialog.dismiss();
                    }
                    return false;
                }
            });
        }
        closeProcess();

        if (!TextUtils.isEmpty(content)) {
            tv.setText(content);
            tv.setVisibility(View.VISIBLE);
        } else {
            tv.setVisibility(GONE);
        }

        processDialog.show();
        return processDialog;
    }

    public void showProcess() {
        showProcess(null);
    }

    public void closeProcess() {
        if (view != null)
            ((AnimationDrawable) view.getDrawable()).stop();
        if (processDialog != null && processDialog.isShowing()) {
            processDialog.dismiss();
        }
    }

}
