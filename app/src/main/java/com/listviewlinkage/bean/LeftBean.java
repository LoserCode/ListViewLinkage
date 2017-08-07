package com.listviewlinkage.bean;

import java.io.Serializable;

/**
 * Created by dell on 2017/8/4.
 */

public class LeftBean implements Serializable {
    public boolean isSelect;
    public String leftString;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getLeftString() {
        return leftString;
    }

    public void setLeftString(String leftString) {
        this.leftString = leftString;
    }
}
