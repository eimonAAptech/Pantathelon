package com.pentathlon.pentathlon.models.getFilterModel;

import com.google.gson.annotations.SerializedName;

public class ValuesItem {

    public ValuesItem() {

    }

    @SerializedName("display")
    private String display;

    @SerializedName("value")
    private String value;
    private boolean isSelected = false;

    public ValuesItem(String display, String value, boolean isSelected) {
        this.display = display;
        this.value = value;
        this.isSelected = isSelected;
    }

    public String getDisplay() {
        return display;
    }

    public String getValue() {
        return value;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public void setValue(String value) {
        this.value = value;
    }
}