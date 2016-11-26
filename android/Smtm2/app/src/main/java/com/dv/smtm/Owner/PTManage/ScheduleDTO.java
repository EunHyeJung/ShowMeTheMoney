package com.dv.smtm.Owner.PTManage;

import java.util.Date;

/**
 * Created by Sue on 16. 9. 25..
 */
public class ScheduleDTO {
    public static int staffId;
    public static int startHour;
    public static int startMinute;
    public static int endHour;
    public static int endMinute;
    public static Date start;
    public static Date finish;

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staff_id) {
        this.staffId = staffId;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public static int getEndHour() {
        return endHour;
    }

    public static void setEndHour(int endHour) {
        ScheduleDTO.endHour = endHour;
    }

    public static int getEndMinute() {
        return endMinute;
    }

    public static void setEndMinute(int endMinute) {
        ScheduleDTO.endMinute = endMinute;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getFinish() {
        return finish;
    }

    public void setFinish(Date finish) {
        this.finish = finish;
    }
}
