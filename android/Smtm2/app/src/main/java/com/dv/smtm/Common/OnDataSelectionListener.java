package com.dv.smtm.Common;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by user on 2016-07-28.
 */
public interface OnDataSelectionListener {

    /**
     * 아이템이 선택되었을 때 호출되는 메소드
     *
     * @param parent Parent View
     * @param v Target View
     * @param row Row Index
     * @param column Column Index
     * @param id ID for the View
     */
    public void onDataSelected(AdapterView parent, View v, int position, long id);

}
