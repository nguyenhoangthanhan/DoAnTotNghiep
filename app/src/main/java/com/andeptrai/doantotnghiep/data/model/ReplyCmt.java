package com.andeptrai.doantotnghiep.data.model;

public class ReplyCmt {

    private String id_reply_cmt;
    private String id_cmt;
    private int idCmter;
    private String nameCmter;
    private String content;
    private String id_list_id_like_replycmt;
    private String time_create_reply;

    public ReplyCmt() {
    }

    public ReplyCmt(String id_cmt, int idCmter, String content, String nameCmter) {
        this.id_cmt = id_cmt;
        this.idCmter = idCmter;
        this.content = content;
        this.nameCmter = nameCmter;
    }

    public ReplyCmt(String id_reply_cmt, String id_cmt, int idCmter, String nameCmter, String content, String id_list_id_like_replycmt, String time_create_reply) {
        this.id_reply_cmt = id_reply_cmt;
        this.id_cmt = id_cmt;
        this.idCmter = idCmter;
        this.nameCmter = nameCmter;
        this.content = content;
        this.id_list_id_like_replycmt = id_list_id_like_replycmt;
        this.time_create_reply = time_create_reply;
    }

    public String getId_reply_cmt() {
        return id_reply_cmt;
    }

    public void setId_reply_cmt(String id_reply_cmt) {
        this.id_reply_cmt = id_reply_cmt;
    }

    public int getIdCmter() {
        return idCmter;
    }

    public void setIdCmter(int idCmter) {
        this.idCmter = idCmter;
    }

    public String getNameCmter() {
        return nameCmter;
    }

    public void setNameCmter(String nameCmter) {
        this.nameCmter = nameCmter;
    }

    public String getId_cmt() {
        return id_cmt;
    }

    public void setId_cmt(String id_cmt) {
        this.id_cmt = id_cmt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId_list_id_like_replycmt() {
        return id_list_id_like_replycmt;
    }

    public void setId_list_id_like_replycmt(String id_list_id_like_replycmt) {
        this.id_list_id_like_replycmt = id_list_id_like_replycmt;
    }

    public String getTime_create_reply() {
        return time_create_reply;
    }

    public void setTime_create_reply(String time_create_reply) {
        this.time_create_reply = time_create_reply;
    }
}
