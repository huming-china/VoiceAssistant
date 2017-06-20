package com.cloudring.commonlib.http.dataBean;


import java.util.List;

/**
 * Created by slx on 2016/11/1.
 */

public class GreatestMode {

    private String titleName;
    private List<GridMode> gridModes;


    private GreatestMode() {

    }


    public GreatestMode(List<GridMode> gridModes) {
        this.gridModes = gridModes;
    }


    public List<GridMode> getGridModes() {
        return gridModes;
    }

    public void setGridModes(List<GridMode> gridModes) {
        this.gridModes = gridModes;
    }



}
