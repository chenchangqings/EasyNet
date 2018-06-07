package com.android.ccq.easynet.analysis;

import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by yangbing 2017/3/11.
 */
public class GsonSerializer {


    private static GsonSerializer mInstance;

    public static GsonSerializer getInstance() {
        synchronized (GsonSerializer.class) {
            if (mInstance == null) {
                synchronized (GsonSerializer.class) {
                    mInstance = new GsonSerializer();
                }
            }
        }
        return mInstance;
    }

    private com.google.gson.Gson mGson =  new GsonBuilder()
        .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
        .registerTypeAdapter(int.class, new IntegerDefault0Adapter()).create();


    public String toJson(Map object) {
        return mGson.toJson(object);
    }
    

    public String toJson(Object object) {
        return mGson.toJson(object);
    }
    

    public <T> T fromJsonT(String json, Class<T> cls) {
        return mGson.fromJson(json, cls);
    }

    public Object fromJson(String json,  Class<?> cls) {
        return mGson.fromJson(json, cls);
    }

    public Object fromJson(String json, Type type) {
        return mGson.fromJson(json, type);
    }


}
