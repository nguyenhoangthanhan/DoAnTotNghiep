package com.andeptrai.doantotnghiep.data.model;

import java.io.Serializable;

public class Comment  implements Serializable {

    private String idComt;
    private int  idUser;
    private String nameCmter;
    private String idRestaurant;
    private String content;
    private int likeNumber;
    private int cmtNumber;
    private int shareNumber;
    private String time_create_cmt;
    private boolean likeIt;
    private double pointReview;

    public Comment(String idComt, int idUser, String nameCmter, String idRestaurant
            , String content, int likeNumber, int cmtNumber, int shareNumber, String time_create_cmt, boolean likeIt, double pointReview) {
        this.idComt = idComt;
        this.idUser = idUser;
        this.nameCmter = nameCmter;
        this.idRestaurant = idRestaurant;
        this.content = content;
        this.likeNumber = likeNumber;
        this.cmtNumber = cmtNumber;
        this.shareNumber = shareNumber;
        this.time_create_cmt = time_create_cmt;
        this.likeIt = likeIt;
        this.pointReview = pointReview;
    }

    public Comment(String nameCmter, String content, int likeNumber, int cmtNumber, int shareNumber, boolean likeIt, double pointReview) {
        this.nameCmter = nameCmter;
        this.content = content;
        this.likeNumber = likeNumber;
        this.cmtNumber = cmtNumber;
        this.shareNumber = shareNumber;
        this.likeIt = likeIt;
        this.pointReview = pointReview;
    }

    public double getPointReview() {
        return pointReview;
    }

    public void setPointReview(double pointReview) {
        this.pointReview = pointReview;
    }

    public boolean isLikeIt() {
        return likeIt;
    }

    public void setLikeIt(boolean likeIt) {
        this.likeIt = likeIt;
    }

    public int getShareNumber() {
        return shareNumber;
    }

    public void setShareNumber(int shareNumber) {
        this.shareNumber = shareNumber;
    }

    public String getNameCmter() {
        return nameCmter;
    }

    public void setNameCmter(String nameCmter) {
        this.nameCmter = nameCmter;
    }

    public String getIdComt() {
        return idComt;
    }

    public void setIdComt(String idComt) {
        this.idComt = idComt;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(String idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }

    public int getCmtNumber() {
        return cmtNumber;
    }

    public void setCmtNumber(int cmtNumber) {
        this.cmtNumber = cmtNumber;
    }

    public String getTime_create_cmt() {
        return time_create_cmt;
    }

    public void setTime_create_cmt(String time_create_cmt) {
        this.time_create_cmt = time_create_cmt;
    }
}
