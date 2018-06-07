package com.android.ccq.easynet.log;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class LogUtils {
    private static final String LOG_TAG = LogUtils.class.getSimpleName();

    /**
     * 打开log日志开关,true为打开，false为关闭
     */
    private static int LOG_LEVEL = true ? 0x10FF : 0x0000;

    private static final int VERBOSE = 0x0001;
    private static final int DEBUG = 0x0002;
    private static final int INFO = 0x0004;
    private static final int WARN = 0x0008;
    private static final int ERROR = 0x0010;

    private static final int TRACE_INFO = 0x1000;

    private static String APP_TAG = "DXLIVE";

    private static boolean logAble = false;
    private static final String LOG_PATH = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),APP_TAG).getPath() + "/logs";

    public static void setLogTag(String tag) {
        logAble = true;
        APP_TAG = tag;
    }

    public static void i(String tag, String msg) {
        if(logAble){
            if ((LOG_LEVEL & INFO) == INFO ) {
                if ((LOG_LEVEL & TRACE_INFO) == TRACE_INFO)
                    Log.i(APP_TAG, getFormatMessage(tag, msg + getTraceInfo()));
                else
                    Log.i(APP_TAG, getFormatMessage(tag, msg));
            }
        }
    }

    public static void d(String tag, String msg) {
        if(logAble) {
            if ((LOG_LEVEL & DEBUG) == DEBUG) {
                if ((LOG_LEVEL & TRACE_INFO) == TRACE_INFO)
                    Log.d(APP_TAG, getFormatMessage(tag, msg + getTraceInfo()));
                else
                    Log.d(APP_TAG, getFormatMessage(tag, msg));
            }
        }
    }

    public static void v(String tag, String msg) {
        if ((LOG_LEVEL & VERBOSE) == VERBOSE) {
            if ((LOG_LEVEL & TRACE_INFO) == TRACE_INFO)
                Log.v(APP_TAG, getFormatMessage(tag, msg + getTraceInfo()));
            else
                Log.v(APP_TAG, getFormatMessage(tag, msg));
        }
    }

    public static void e(String tag, String msg) {
        if ((LOG_LEVEL & ERROR) == ERROR) {
            if ((LOG_LEVEL & TRACE_INFO) == TRACE_INFO)
                Log.e(APP_TAG, getFormatMessage(tag, msg + getTraceInfo()));
            else
                Log.e(APP_TAG, getFormatMessage(tag, msg));
        }
    }

    public static void w(String tag, String msg) {
        if ((LOG_LEVEL & WARN) == WARN) {
            if ((LOG_LEVEL & TRACE_INFO) == TRACE_INFO)
                Log.w(APP_TAG, getFormatMessage(tag, msg + getTraceInfo()));
            else
                Log.w(APP_TAG, getFormatMessage(tag, msg));
        }
    }

    public static void debugWriteFile(String filename, String content) {
        File folder = new File(LOG_PATH);
        if (!folder.exists())
            folder.mkdirs();

        try {
            File file = new File(LOG_PATH+"/"+filename);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            out.write(content);
            out.close();
        } catch (Exception e) {
            LogUtils.e(LOG_TAG, e.toString());
        }
    }

    private static String getTraceInfo() {
        StringBuffer sb = new StringBuffer();

        StackTraceElement[] stacks = new Throwable().getStackTrace();
        int stacksLen = stacks.length;
        if (stacksLen <= 2)
            return "";
        sb.append("   {at ").append(stacks[2].getClassName()).append(".")
                .append(stacks[2].getMethodName()).append("(")
                .append(stacks[2].getFileName()).append(":")
                .append(stacks[2].getLineNumber()).append(")}");
        return sb.toString();
    }

    private static String getFormatMessage(String tag, String msg) {
        StringBuffer sbuf = new StringBuffer();
        sbuf.append("[").append(tag).append("]").append("\t").append(msg);

        return sbuf.toString();
    }
}
