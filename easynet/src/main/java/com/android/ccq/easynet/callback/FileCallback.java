package com.android.ccq.easynet.callback;

import com.lzy.okgo.model.Progress;

import java.io.File;

/**
 * Description : 网络请求File回调
 * Author :      Gaozhanzhong
 * Create :      2017/10/18 22:05
 * Update :      2017/10/18 22:05
 * Version :     V1.1.1
 */

public abstract class FileCallback extends BaseNetCallback<File>{

    private String targetPath;
    private String fileName;

    public FileCallback(String targetPath, String fileName) {
        this.targetPath = targetPath;
        this.fileName = fileName;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public String getFileName() {
        return fileName;
    }

    public abstract void onProgress (Progress progress);


}
