package com.ding.basic.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间转换的工具类
 */

public class TimeUtlis {


    /**
     * 把时间转换为：时分秒格式。
     *
     * @param time 秒，传入单位为秒
     * @return
     */
    public static String getTimeString(int time) {
        int miao = time % 60;
        int fen = time / 60;
        int hour = 0;
        if (fen >= 60) {
            hour = fen / 60;
            fen = fen % 60;
        }
        String miaoString = (miao < 10 ? "0" : "") + miao;
        String fenString = (fen < 10 ? "0" : "") + fen;
        String hourString = (hour < 10 ? "0" : "") + hour;
        return hourString + ":" + fenString + ":" + miaoString;
    }

    public static String timeFormat(String pattern, long timestamp) {
        return timeFormat(pattern, new Date(timestamp));
    }

    /**
     * 如果希望格式化时间为12小时制的，则使用hh:mm:ss
     * 而如果希望格式化时间为24小时制的，则使用HH:mm:ss
     * String  pattern   ;"yyyy-MM-dd HH:mm:ss"
     */
    public static String timeFormat(String pattern, Date time) {
        try {
            SimpleDateFormat dateformat = new SimpleDateFormat(pattern);
            return dateformat.format(time);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 如果希望格式化时间为12小时制的，则使用hh:mm:ss
     * 而如果希望格式化时间为24小时制的，则使用HH:mm:ss
     * String  pattern   ;"yyyy-MM-dd HH:mm:ss"
     * srcDate 格式要与 srcDatePattern 一致
     */
    public static String getDateFormat(String srcDate, String srcDatePattern, String pattern) {
        if (TextUtils.isEmpty(srcDate))
            return "";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.format(new SimpleDateFormat(srcDatePattern).parse(srcDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String ms2DateOnlyDay(Long timestamp) {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(timestamp));
    }

    public static String ms2DateOnlyDayTime(Long timestamp) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp));

        //long timeStamp = 1495777335060;//直接是时间戳
//        long timeStamp = System.currentTimeMillis();  //获取当前时间戳,也可以是你自已给的一个随机的或是别人给你的时间戳(一定是long型的数据)
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//这个是你要转成后的时间的格式
//        String sd = sdf.format(new Date(timeStamp));   // 时间戳转换成时间
    }

    /*
     * 将时间转换为时间戳
     */
    public static long dateToStamp(String s) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        return ts;
    }
}
