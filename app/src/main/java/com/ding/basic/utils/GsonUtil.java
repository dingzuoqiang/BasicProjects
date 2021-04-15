package com.ding.basic.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 项目名称：
 * 类名称：GsonUtil
 * 类描述：Gson简单封装，去除null异常
 *
 */
public class GsonUtil {

    private static Gson mgson = new Gson();
    public static Gson getGson() {
        return mgson;
    }


    //定义并配置gson
    private static final Gson gson = new GsonBuilder()//建造者模式设置不同的配置
            .serializeNulls()//序列化为null对象
            .setDateFormat("yyyy-MM-dd HH:mm:ss") //设置日期的格式
            .disableHtmlEscaping()//防止对网址乱码 忽略对特殊字符的转换
            .registerTypeAdapter(String.class, new StringConverter())//对为null的字段进行转换
            .create();


    /**
     * 对解析数据的形式进行转换
     *
     * @param obj 解析的对象
     * @return 转化结果为json字符串
     */
    public static String toJsonStr(Object obj) {
        if (obj == null) {
            return "";
        }
        try {
            return gson.toJson(obj);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 解析为一个具体的对象
     *
     * @param json 要解析的字符串
     * @param obj  要解析的对象
     * @param <T>  将json字符串解析成obj类型的对象
     * @return
     */
    public static <T> T toObj(String json, Class<T> obj) {
        //如果为null直接返回为null
        if (obj == null || TextUtils.isEmpty(json)) {
            return null;
        }
        try {
            return gson.fromJson(json, obj);
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * @return 不区分类型 传什么解析什么
     */
    public static <T> T toObj(String jsonStr, Type type) {
        return gson.fromJson(jsonStr, type);
    }

    /**
     * 将Json数组解析成相应的映射对象列表
     * 解决类型擦除的问题
     */
    public static <T> List<T> toList(String jsonStr, Class<T> clz) {
        List<T> list = gson.fromJson(jsonStr, new type(clz));
        if (list == null) list = new ArrayList<>();
        return list;
    }

    public static <T> Map<String, T> toMap(String jsonStr, Class<T> clz) {
        Map<String, T> map = gson.fromJson(jsonStr, new type(clz));
        if (map == null) map = new HashMap<>();
        return map;
    }

    private static class type implements ParameterizedType {
        private Type type;

        private type(Type type) {
            this.type = type;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{type};
        }

        @Override
        public Type getRawType() {
            return ArrayList.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }
    /**
     * 实现了 序列化 接口    对为null的字段进行转换
     */
    public static class StringConverter implements JsonSerializer<String>, JsonDeserializer<String> {
        //字符串为null 转换成"",否则为字符串类型
        @Override
        public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return json.getAsJsonPrimitive().getAsString();
        }

        @Override
        public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext context) {
            return src == null || src.equals("null") ? new JsonPrimitive("") : new JsonPrimitive(src.toString());
        }
    }
}

