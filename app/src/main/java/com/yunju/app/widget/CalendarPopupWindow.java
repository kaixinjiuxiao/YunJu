package com.yunju.app.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yunju.app.R;
import com.yunju.app.util.DateUtil;
import com.yunju.app.util.StringUtil;
import com.yunju.app.util.ToastUtil;
import com.yunju.app.widget.calendar.KCalendar;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sm on 2018/3/5 0005.
 */

public class CalendarPopupWindow extends PopupWindow {

    private Context context;
    private Window window;
    private KCalendar calendar;
    private TextView tvCheckInDate, tvCheckInWeek, tvCheckOutDate, tvCheckOutWeek, tvStayNum;
    private ImageView ivLastMonth, ivNextMonth;
    private Button btnSubmit;
    private int stayNum;
    private int clickNum = 1;
    private String fromDate, toDate, currentDate,forbiddenDate;
    private List<String> mStringList;


//    public CalendarPopupWindow(final Context context, Window win) {
//        super(context);
//        this.context = context;
//        this.window = win;
//        View view = LayoutInflater.from(context).inflate(R.layout.layout_popwindow_calendar, null);
//        ImageView ivClose = view.findViewById(R.id.popwindow_calendar_close);
//        TextView tvClear = view.findViewById(R.id.popwindow_calendar_clear);
//        tvCheckInDate = view.findViewById(R.id.popwindow_calendar_checkInDate);
//        tvCheckOutDate = view.findViewById(R.id.popwindow_calendar_checkOutDate);
//        tvCheckInWeek = view.findViewById(R.id.popwindow_calendar_checkInWeek);
//        tvCheckOutWeek = view.findViewById(R.id.popwindow_calendar_checkOutWeek);
//        tvStayNum = view.findViewById(R.id.popwindow_calendar_stayNum);
////        ListView listView = view.findViewById(R.id.popwindow_calendar_listview);
//        btnSubmit = view.findViewById(R.id.popwindow_calendar_submit);
//
//        ivLastMonth=view.findViewById(R.id.popwindow_calendar_lastMonth);
//        ivNextMonth=view.findViewById(R.id.popwindow_calendar_nextMonth);
//        final TextView tvCurrentMonth=view.findViewById(R.id.popwindow_calendar_dateMonth);
//        calendar = view.findViewById(R.id.popwindow_calendar_cr);
//        currentDate = DateUtil.dateToStr(new Date());
//        tvCurrentMonth.setText(DateUtil.getWantDate(currentDate,"yyyy年MM月"));
//        if (StringUtil.isNullString(fromDate)) {
//            fromDate = currentDate;
//            toDate = DateUtil.getDate(currentDate, 1);
//            stayNum=1;
//            setData(fromDate, toDate, stayNum);
//        }
//
//        ivClose.setOnClickListener(new PopClickListener());
//        tvClear.setOnClickListener(new PopClickListener());
//        btnSubmit.setOnClickListener(new PopClickListener());
//        ivLastMonth.setOnClickListener(new PopClickListener());
//        ivNextMonth.setOnClickListener(new PopClickListener());
//
//        setContentView(view);
//        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
//        setFocusable(true);
////		ColorDrawable dw = new ColorDrawable(0x88000000);
////		setBackgroundDrawable(dw);
//        setAnimationStyle(R.style.popwindowAnimation);
//        setOnDismissListener(new OnDismissListener() {
//
//            @Override
//            public void onDismiss() {
//                WindowManager.LayoutParams lp = window.getAttributes();
//                lp.alpha = 1.0f;
//                window.setAttributes(lp);
//            }
//        });
//
//        calendar.setOnCalendarDateChangedListener(new KCalendar.OnCalendarDateChangedListener() {
//            @Override
//            public void onCalendarDateChanged(int year, int month) {
//                tvCurrentMonth.setText(year+"年"+month+"月");
//            }
//        });
//        calendar.setOnCalendarClickListener(new KCalendar.OnCalendarClickListener() {
//            @Override
//            public void onCalendarClick(int row, int col, String dateFormat) {
//                int month = Integer.parseInt(dateFormat.substring(
//                        dateFormat.indexOf("-") + 1,
//                        dateFormat.lastIndexOf("-")));
//
//                if (calendar.getCalendarMonth() - month == 1// 跨年跳转
//                        || calendar.getCalendarMonth() - month == -11) {
//                    calendar.lastMonth();
//
//                } else if (month - calendar.getCalendarMonth() == 1 // 跨月跳转
//                        || month - calendar.getCalendarMonth() == -11) {
//                    calendar.nextMonth();
//
//                } else {
//                    try {
//                        int days = DateUtil.daysOfTwo(currentDate, dateFormat);
//                        if ((days >= 0)&&(days<=90)) { //今天之前点击无效
//                            if (clickNum % 2 != 0) {
//                                calendar.removeAllBgColor();
//                                fromDate = dateFormat;
//                                toDate = null;
//                                stayNum=0;
//                                setData(fromDate, toDate, stayNum);
//                                clickNum = 2;
//                            } else {
//                                int n = DateUtil.daysOfTwo(fromDate, dateFormat);
//                                if (n > 0) {
//                                    toDate = dateFormat;
//                                    stayNum=n;
//                                    setData(fromDate, toDate, stayNum);
//                                } else if (n < 0) {
//                                    toDate = fromDate;
//                                    fromDate = dateFormat;
//                                    stayNum=n*(-1);
//                                    setData(fromDate, toDate, stayNum);
//                                } else {
//                                    return;
//                                }
//
//                                clickNum = 1;
//                            }
//                        }else{
//                            ToastUtil.showShort("不可选~");
//                        }
//
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//    }
public CalendarPopupWindow(final Context context, Window win,List<String> data) {
    super(context);
    this.context = context;
    this.window = win;
    this.mStringList=data;
    View view = LayoutInflater.from(context).inflate(R.layout.layout_popwindow_calendar, null);
    ImageView ivClose = view.findViewById(R.id.popwindow_calendar_close);
    TextView tvClear = view.findViewById(R.id.popwindow_calendar_clear);
    tvCheckInDate = view.findViewById(R.id.popwindow_calendar_checkInDate);
    tvCheckOutDate = view.findViewById(R.id.popwindow_calendar_checkOutDate);
    tvCheckInWeek = view.findViewById(R.id.popwindow_calendar_checkInWeek);
    tvCheckOutWeek = view.findViewById(R.id.popwindow_calendar_checkOutWeek);
    tvStayNum = view.findViewById(R.id.popwindow_calendar_stayNum);
//        ListView listView = view.findViewById(R.id.popwindow_calendar_listview);
    btnSubmit = view.findViewById(R.id.popwindow_calendar_submit);

    ivLastMonth=view.findViewById(R.id.popwindow_calendar_lastMonth);
    ivNextMonth=view.findViewById(R.id.popwindow_calendar_nextMonth);
    final TextView tvCurrentMonth=view.findViewById(R.id.popwindow_calendar_dateMonth);
    calendar = view.findViewById(R.id.popwindow_calendar_cr);
    currentDate = DateUtil.dateToStr(new Date());
    tvCurrentMonth.setText(DateUtil.getWantDate(currentDate,"yyyy年MM月"));
    if (StringUtil.isNullString(fromDate)) {
        fromDate = currentDate;
        toDate = DateUtil.getDate(currentDate, 1);
        stayNum=1;
        setData(fromDate, toDate, stayNum,mStringList);
    }

    ivClose.setOnClickListener(new PopClickListener());
    tvClear.setOnClickListener(new PopClickListener());
    btnSubmit.setOnClickListener(new PopClickListener());
    ivLastMonth.setOnClickListener(new PopClickListener());
    ivNextMonth.setOnClickListener(new PopClickListener());

    setContentView(view);
    setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
    setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
    setFocusable(true);
//		ColorDrawable dw = new ColorDrawable(0x88000000);
//		setBackgroundDrawable(dw);
    setAnimationStyle(R.style.popwindowAnimation);
    setOnDismissListener(new OnDismissListener() {

        @Override
        public void onDismiss() {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.alpha = 1.0f;
            window.setAttributes(lp);
        }
    });

    calendar.setOnCalendarDateChangedListener(new KCalendar.OnCalendarDateChangedListener() {
        @Override
        public void onCalendarDateChanged(int year, int month) {
            tvCurrentMonth.setText(year+"年"+month+"月");
        }
    });
    calendar.setOnCalendarClickListener(new KCalendar.OnCalendarClickListener() {
        @Override
        public void onCalendarClick(int row, int col, String dateFormat) {
            int month = Integer.parseInt(dateFormat.substring(
                    dateFormat.indexOf("-") + 1,
                    dateFormat.lastIndexOf("-")));

            if (calendar.getCalendarMonth() - month == 1// 跨年跳转
                    || calendar.getCalendarMonth() - month == -11) {
                calendar.lastMonth();

            } else if (month - calendar.getCalendarMonth() == 1 // 跨月跳转
                    || month - calendar.getCalendarMonth() == -11) {
                calendar.nextMonth();

            } else {
                try {
                    int days = DateUtil.daysOfTwo(currentDate, dateFormat);
                    if (days >= 0) { //今天之前点击无效

                        if(mStringList.size()!=0){
                            for (int i = 0; i <mStringList.size() ; i++) {
                                forbiddenDate+=mStringList.get(i);
                            }
                            if(forbiddenDate.contains(fromDate)){
                                clickNum = 1;
                            }
                        }


                        if (clickNum % 2 != 0) {
                            calendar.removeAllBgColor();
                            fromDate = dateFormat;
                            toDate = null;
                            stayNum=0;
                            setData(fromDate, toDate, stayNum,mStringList);
                            clickNum = 2;
                        } else {
                            int n = DateUtil.daysOfTwo(fromDate, dateFormat);
                            if (n > 0) {
                                toDate = dateFormat;
                                stayNum=n;
                                setData(fromDate, toDate, stayNum,mStringList);
                            } else if (n < 0) {
                                toDate = fromDate;
                                fromDate = dateFormat;
                                stayNum=n*(-1);
                                setData(fromDate, toDate, stayNum,mStringList);
                            } else {
                                return;
                            }

                            clickNum = 1;
                        }
                    }else{
                        ToastUtil.showShort("不可选~");
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    });
}



    public void setData(String fd, String td, int num) {
        calendar.removeAllMarks(KCalendar.FLAG_TEXT_MARK);
        if (StringUtil.isNotNullString(fd)) {
            tvCheckInDate.setText(fd);
            tvCheckInWeek.setText(DateUtil.getWeek(fd));
            calendar.setCalendarDayBgColor(fd, R.color.colorPrimary);
            calendar.addMark(fd,"入住");
        } else {
            tvCheckInDate.setText("");
            tvCheckInWeek.setText("");
        }
        if (StringUtil.isNotNullString(td)) {
            tvCheckOutDate.setText(td);
            tvCheckOutWeek.setText(DateUtil.getWeek(td));
            calendar.setCalendarDayBgColor(td, R.color.colorPrimary);
            calendar.addMark(td,"离店");
        } else {
            tvCheckOutDate.setText("");
            tvCheckOutWeek.setText("");
        }

        if (num >= 2) {
            List<String> ls=new ArrayList<>();
            for (int i = 1; i < num; i++) {
                ls.add(DateUtil.getDate(fd, i));
            }
            calendar.setCalendarDaysBgColor(ls, R.color.colorAccent);
        }
        tvStayNum.setText("共" + num + "晚");
        if (StringUtil.isNullString(fd) || StringUtil.isNullString(td)) {
            btnSubmit.setText(context.getResources().getString(R.string.pop_calendar));
            btnSubmit.setBackgroundResource(R.drawable.bg_button_gray);
        } else {
            btnSubmit.setText("确定");
            btnSubmit.setBackgroundResource(R.drawable.general_button_bg);
        }
    }

    public void setData(String fd, String td, int num, List<String>remarksList) {
        if(remarksList==null||remarksList.size()==0){
            setData(fd,td,num);
            return;
        }
        calendar.removeAllMarks(KCalendar.FLAG_TEXT_MARK);
        String str="";
        for (int i = 0; i <remarksList.size() ; i++) {
            calendar.addMark(remarksList.get(i),"已预订");
            str+=remarksList.get(i);
        }
        while (str.contains(fd)){
            fd=DateUtil.getDate(fd,1);
            td=DateUtil.getDate(fd,1);
//            fromDate = fd;
//            toDate=td;
        }

        if (StringUtil.isNotNullString(fd)) {
            if(!str.contains(fd)){
                tvCheckInDate.setText(fd);
                tvCheckInWeek.setText(DateUtil.getWeek(fd));
                calendar.setCalendarDayBgColor(fd, R.color.colorPrimary);
                calendar.addMark(fd,"入住");
            }

        } else {
            tvCheckInDate.setText("");
            tvCheckInWeek.setText("");
        }
        if (StringUtil.isNotNullString(td)) {
            tvCheckOutDate.setText(td);
            tvCheckOutWeek.setText(DateUtil.getWeek(td));
            calendar.setCalendarDayBgColor(td, R.color.colorPrimary);
            calendar.addMark(td,"离店");
        } else {
            tvCheckOutDate.setText("");
            tvCheckOutWeek.setText("");
        }


        List<String> ls=new ArrayList<>();
        if (num >= 2) {
            for (int i = 1; i < num; i++) {
                ls.add(DateUtil.getDate(fd, i));
            }
            calendar.setCalendarDaysBgColor(ls, R.color.colorAccent);
        }
        tvStayNum.setText("共" + num + "晚");



        if (StringUtil.isNullString(fd) || StringUtil.isNullString(td)) {
            btnSubmit.setText(context.getResources().getString(R.string.pop_calendar));
            btnSubmit.setBackgroundResource(R.drawable.bg_button_gray);
        } else {
            if(!str.contains(fd)){

                btnSubmit.setText("确定");
                btnSubmit.setBackgroundResource(R.drawable.general_button_bg);
            }
        }
    }

    public class PopClickListener implements View.OnClickListener {
            @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.popwindow_calendar_close:
                    dismiss();
                    break;
                case R.id.popwindow_calendar_lastMonth:
                    calendar.lastMonth();
                    break;
                case R.id.popwindow_calendar_nextMonth:
                    calendar.nextMonth();
                    break;
                case R.id.popwindow_calendar_clear:
                    // 清空
                    fromDate = null;
                    toDate = null;
                    stayNum=0;
                    setData(fromDate, toDate, stayNum);
                    calendar.removeAllBgColor();
                  //  calendar.removeAllMarks(KCalendar.FLAG_TEXT_MARK);
                    break;
                case R.id.popwindow_calendar_submit:
                    // 确定
                    fromDate=tvCheckInDate.getText().toString();
                    toDate = tvCheckOutDate.getText().toString();
                    for (int i = 1; i < stayNum; i++) {
                        if(!TextUtils.isEmpty(forbiddenDate)&&forbiddenDate.contains(DateUtil.getDate(fromDate, i))){
                            ToastUtil.showShort("包含已预定日期，请重新选择");fromDate="";
                            break;
                        }
                    }
                    if (StringUtil.isNullString(fromDate) || StringUtil.isNullString(toDate)) {
                        return;
                    }
                    if (onDateSubmitListener != null) {
                        onDateSubmitListener.submit(fromDate, toDate, stayNum);
                        dismiss();
                    }
                    break;
            }
        }
    }

    /**
     * 设置当月不可选择日期日期背景
     */
    public void setTodayBeforeBg(){
        String currentDate = DateUtil.dateToStr(new Date());
        int  currentYear = Integer.parseInt(currentDate.substring(0,currentDate.indexOf("-")));
        int currentMonth = Integer.parseInt(currentDate.substring(currentDate.indexOf("-")+1,currentDate.lastIndexOf("-")));
        int currentDay = Integer.parseInt(currentDate.substring(currentDate.lastIndexOf("-")+1,currentDate.length()));
        List<String> list = new ArrayList<>();
        for (int i = 1; i <currentDay; i++) {
            if(i>=10){
                String date =currentYear+"-0"+currentMonth+"-"+i;
                list.add(date);
            }else{
                String date =currentYear+"-0"+currentMonth+"-0"+i;
                list.add(date);
            }
            calendar.setCalendarDaysBgColor(list,R.color.white);

        }
    }


    /**
     * 设置九十天后当月不可选择日期的背景色
     */
    public void setAnimDayAfterBg(){
        String currentDate = DateUtil.dateToStr(new Date());
        String animDay = DateUtil.getDate(currentDate,90);
        List<String> dateList = new ArrayList<>();
        int  year = Integer.parseInt(animDay.substring(0,animDay.indexOf("-")));
        int month = Integer.parseInt(animDay.substring(animDay.indexOf("-")+1,animDay.lastIndexOf("-")));
        String test = animDay.substring(animDay.indexOf("-")+1,animDay.lastIndexOf("-"));
        int day = Integer.parseInt(animDay.substring(animDay.lastIndexOf("-")+1,animDay.length()));
        int totalDay = DateUtil.getDaysByYearMonth(year,month);
        for (int i = day+1; i <=totalDay ; i++) {
            if(day>=10){
                String date = year+"-"+test+"-"+i;
                dateList.add(date);
            }else{
                String date = year+"-"+test+"-0"+i;
                dateList.add(date);
            }

        }
        calendar.setCalendarDaysBgColor(dateList,R.color.test);
    }


    public void setMarks(String data,String res){
        calendar.addMark(data,res);
    }

    public void show() {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.4f;
        window.setAttributes(lp);
        showAtLocation(window.getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 设置可看月份
     * @param visibleMonths
     */
    public void setVisibleMonth(String[] visibleMonths){
        if(visibleMonths!=null && visibleMonths.length>=2) {
            calendar.setVisibleMonth(visibleMonths);
        }
    }

    private OnDateSubmitListener onDateSubmitListener;

    public interface OnDateSubmitListener {
        void submit(String fromDate, String toDate, int stayNum);
    }

    public void setOnDateSubmitListener(OnDateSubmitListener onDateSubmitListener) {
        this.onDateSubmitListener = onDateSubmitListener;
    }
}
