package com.andeptrai.doantotnghiep.interf;

import android.app.Dialog;
import android.widget.TextView;

public interface SetNumberDialogInterf {
    void setNumberAdultsDialogClickListener(Dialog childrenNumberDialog, String s, TextView txtNumber);
    void setNumberChildrenDialogClickListener(Dialog childrenNumberDialog, String s, TextView txtNumber);
}
