package com.dv.smtm.Common;

import android.content.Context;

import android.graphics.Typeface;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dv.smtm.Model.DailyDTO;

import com.dv.smtm.R;
import static com.dv.smtm.Common.CommonData.dailySeq;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by eunhye on 2016-10-30.
 */
public class CalendarAdapter extends ArrayAdapter<Date> {
    private static final String TAG = "CalendarAdapter";


    Context context;
    // days with events
    private HashSet<Date> eventDays;

    // for view inflation
    private LayoutInflater inflater;

    //
    LinearLayout dailyItemLayout;
    TextView textViewDate;          // 날짜(몇일인지)
    TextView textViewDailySeq;      // DailyTable Id
    TextView textViewWorkTime;      // 근무시간
    TextView textViewTodaySalary;   // 오늘 급여

    //
    List<DailyDTO> dailyDTOList = new ArrayList<DailyDTO>();
    Map<Integer, Integer> workTime = new HashMap<Integer, Integer>();
    Map<Integer, Integer> todaySalary = new HashMap<Integer, Integer>();

    //
    public CalendarAdapter(Context context, ArrayList<Date> days, HashSet<Date> eventDays) {
        super(context, R.layout.control_calendar_day, days);
        this.eventDays = eventDays;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public CalendarAdapter(Context context, ArrayList<Date> days, HashSet<Date> eventDays, List<DailyDTO> dailyDTOList) {
        super(context, R.layout.control_calendar_day, days);
        this.eventDays = eventDays;
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.dailyDTOList = dailyDTOList;
        for (int i = 1; i <= 31; i++) {
            workTime.put(i, 0);
            todaySalary.put(i, 0);
            dailySeq.put(i, 0);
        }
        Log.d(TAG, "CalendarAdapter Called");
        Log.d(TAG, "dailyDTOListSize : " + dailyDTOList.size());
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // day in question
        Date date = getItem(position);
        int day = date.getDate();
        int month = date.getMonth();
        int year = date.getYear();

        // today
        Date today = new Date();

        // inflate item if it does not exist yet
        if (view == null)
            view = inflater.inflate(R.layout.control_calendar_day, parent, false);

        // if this day has an event, specify event image
        view.setBackgroundResource(0);
        if (eventDays != null) {
            for (Date eventDate : eventDays) {
                if (eventDate.getDate() == day &&
                        eventDate.getMonth() == month &&
                        eventDate.getYear() == year) {
                    // mark this day for event
                    view.setBackgroundResource(R.drawable.reminder);
                    break;
                }
            }
        }
        try {
            loadData();     //임시 지울것.
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // clear styling
/*
            ((TextView)view).setTypeface(null, Typeface.NORMAL);
			((TextView)view).setTextColor(Color.BLACK);
*/
        //
        //
        dailyItemLayout = (LinearLayout) view.findViewById(R.id.dailyItem);
        textViewDate = (TextView) view.findViewById(R.id.textView_date);
        textViewDate.setText(String.valueOf(date.getDate()));
        textViewDailySeq = (TextView) view.findViewById(R.id.textView_daily_seq);
        textViewWorkTime = (TextView) view.findViewById(R.id.textView_worktime);
        textViewTodaySalary = (TextView) view.findViewById(R.id.textView_todaysalary);
        //

        if (month != today.getMonth() || year != today.getYear()) { // 이번달이아니면
            textViewDate.setText("");
            textViewTodaySalary.setText("");
            textViewWorkTime.setText("");
            //textViewDate.setTextColor(context.getResources().getColor(R.color.greyed_out));
        } else if (day == today.getDate()) // 오늘날짜
        {
            textViewDate.setTypeface(null, Typeface.BOLD);
            textViewDate.setTextColor(context.getResources().getColor(R.color.today));
        } else {
            textViewDailySeq.setText(dailySeq.get(date.getDate()) + "");
            textViewWorkTime.setText(workTime.get(date.getDate()) + "시간");
            textViewTodaySalary.setText(todaySalary.get(date.getDate()) + "원");
        }
        //

        //

        return view;
    }

    public void loadData() throws ParseException {


        for (DailyDTO dto : dailyDTOList) {
            Log.d(TAG, dto.toString());
            // Start time
            String stTime = null;
            String endTime = null;
            int date = 0;
            if (dto.getStart_time() != null) {
                String[] data = dto.getStart_time().split(" "); // 2016-10-21 13:00:00.0
                String curDate = data[0];
                date = Integer.parseInt(curDate.split("-")[2]); // workTime Key
                stTime = data[1];
            } else {
                stTime = "00:00:00";
            }
            // End Time
            if (dto.getEnd_time() != null) {
                String[] data = dto.getEnd_time().split(" ");
                endTime = data[1];
            } else {
                endTime = "00:00:00";
            }

            // DailySeq
            dailySeq.put(date, dto.getDaily_seq());

            // WorkTime 계산
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            Date date1 = format.parse(stTime);
            Date date2 = format.parse(endTime);
            long difference = date2.getTime() - date1.getTime();
            int time = (int) (difference / (60 * 60 * 1000) % 24);
            Log.d(TAG, "workTim : "+time);
            workTime.put(date, time);
            todaySalary.put(date, 6000 * time);
        }

    }
}