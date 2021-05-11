package com.andeptrai.doantotnghiep.interf;

import com.andeptrai.doantotnghiep.data.model.Comment;

public interface InterfCmt {
    void openReplyCmtClickListener(Comment comment, int position);
    void deleteCmtClickListener(Comment comment, int position);
}
