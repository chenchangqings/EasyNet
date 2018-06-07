package com.android.ccq.easynet.response;

public class SuccessResponse{

    private int reCode;
    private String json;
    private Object entity;


    public SuccessResponse(int reCode,String json,Object entity) {
        this.reCode = reCode;
        this.json = json;
        this.entity = entity;

    }

    public int getReCode() {
        return reCode;
    }

    public String getJson() {
        return json;
    }

    public Object getEntity() {
        return entity;
    }


}
