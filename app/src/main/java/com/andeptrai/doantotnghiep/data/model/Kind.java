package com.andeptrai.doantotnghiep.data.model;

import java.io.Serializable;

public class Kind implements Serializable {
    private String idKind;
    private String nameKind;
    private int classifyKind;
    private boolean check = false;

    public Kind(String idKind, String nameKind, int classifyKind) {
        this.idKind = idKind;
        this.nameKind = nameKind;
        this.classifyKind = classifyKind;
    }

    public Kind(String idKind, String nameKind, int classifyKind, boolean check) {
        this.idKind = idKind;
        this.nameKind = nameKind;
        this.classifyKind = classifyKind;
        this.check = check;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getIdKind() {
        return idKind;
    }

    public void setIdKind(String idKind) {
        this.idKind = idKind;
    }

    public String getNameKind() {
        return nameKind;
    }

    public void setNameKind(String nameKind) {
        this.nameKind = nameKind;
    }

    public int getClassifyKind() {
        return classifyKind;
    }

    public void setClassifyKind(int classifyKind) {
        this.classifyKind = classifyKind;
    }
}
