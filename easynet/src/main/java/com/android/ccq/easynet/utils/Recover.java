package com.android.ccq.easynet.utils;

import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.List;

public class Recover {
    private List<Object> tagList;

    public Recover() {
        this.tagList = new ArrayList<>();
    }

    public void addTag(Object Tag) {
        tagList.add(Tag);
    }


    public void cancelTag(Object tag){
        OkGo.getInstance().cancelTag(tag);
    }

    public void cancelCall() {
        for(Object tag:tagList){
            OkGo.getInstance().cancelTag(tag);
        }
        tagList.clear();
    }
}
