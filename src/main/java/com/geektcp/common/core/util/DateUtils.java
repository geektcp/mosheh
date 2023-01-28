package com.geektcp.common.core.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils {
    private static String[] m_nMonth = new String[]{"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};

    public DateUtils() {
    }


    public static boolean isDate(String strDate, String... format) {
        String fstr = "yyyy-MM-dd";
        if (format.length == 1) {
            fstr = format[0];
        }

        SimpleDateFormat formatdate = new SimpleDateFormat(fstr);
        formatdate.setLenient(false);

        try {
            formatdate.parse(strDate);
            return true;
        } catch (ParseException var5) {
            return false;
        }
    }

    public static boolean isDatetime(String datetime, String... format) {
        String fstr = "yyyy-MM-dd HH:mm:ss";
        if (format.length == 1) {
            fstr = format[0];
        }

        return isDate(datetime, new String[]{fstr});
    }

    public static Date getNow() {
        return new Date();
    }

    public static Date getNowDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(0);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    public static String getUserDate(String sformat) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(sformat);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static String getStringDate() {
        return getUserDate("yyyy-MM-dd HH:mm:ss");
    }

    public static String getStringDateShortmm() {
        return getUserDate("yyyy-MM-dd HHmm");
    }

    public static String getStringDateShort() {
        return getUserDate("yyyy-MM-dd");
    }

    public static String getStringTime() {
        return getUserDate("HH:mm:ss");
    }

    public static String getTimeShort() {
        return getUserDate("HH:mm");
    }

    public static String getHour() {
        String dateString = getStringDate();
        return dateString.substring(11, 13);
    }

    public static String getTime() {
        String dateString = getStringDate();
        return dateString.substring(14, 16);
    }

    public static String getSecond() {
        String dateString = getStringDate();
        return dateString.substring(17, 19);
    }

    public static Date formatToDate(String strDate, String format) {
        if (strDate == null) {
            return null;
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            ParsePosition pos = new ParsePosition(0);
            Date strtodate = formatter.parse(strDate, pos);
            return strtodate;
        }
    }

    public static String formatToStr(Date dateDate, String format) {
        if (dateDate == null) {
            return null;
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            String dateString = formatter.format(dateDate);
            return dateString;
        }
    }

    public static Date strToDateLong(String strDate) {
        return formatToDate(strDate, "yyyy-MM-dd HH:mm:ss");
    }

    public static String dateToStrLong(Date dateDate) {
        return formatToStr(dateDate, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date strToDate(String strDate) {
        return formatToDate(strDate, "yyyy-MM-dd");
    }

    public static String dateToStr(Date dateDate) {
        return formatToStr(dateDate, "yyyy-MM-dd");
    }

    public static double getTwoHourShort(String st1, String st2) {
        Date d1 = formatToDate(st1, "HH:mm");
        Date d2 = formatToDate(st2, "HH:mm");
        double l = (double) (d1.getTime() - d2.getTime());
        return l / 1000.0D / 60.0D / 60.0D;
    }

    public static double getTwoMinShort(String st1, String st2) {
        Date d1 = formatToDate(st1, "HH:mm");
        Date d2 = formatToDate(st2, "HH:mm");
        double l = (double) (d1.getTime() - d2.getTime());
        return l / 1000.0D / 60.0D;
    }

    public static boolean getSxsjIsOk(String sxsj) {
        if (StringUtils.isBlank(sxsj)) {
            return true;
        } else {
            String[] s = sxsj.split(",");
            String nt = getUserDate("HH:mm");
            String[] var3 = s;
            int var4 = s.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                String o = var3[var5];
                String[] o1 = o.split("-");
                if (o1.length == 2) {
                    if (nt.compareTo(o1[0]) >= 0 && nt.compareTo(o1[1]) <= 0) {
                        return true;
                    }

                    if (o1[0].compareTo(o1[1]) > 0) {
                        if (nt.compareTo(o1[1]) >= 0 && nt.compareTo(o1[0]) <= 0) {
                            return false;
                        }

                        return true;
                    }
                }
            }

            return false;
        }
    }

    public static int getTwoMonth(String date1, String date2) {
        try {
            long d1 = formatToDate(date1, "yyyy-MM-dd").getTime();
            long d2 = formatToDate(date2, "yyyy-MM-dd").getTime();
            return d1 < d2 ? -Integer.valueOf(DurationFormatUtils.formatPeriod(d1, d2, "M")).intValue() : Integer.valueOf(DurationFormatUtils.formatPeriod(d2, d1, "M")).intValue();
        } catch (Exception var6) {
            return 0;
        }
    }

    public static int getTwoDay(String date1, String date2) {
        try {
            Date d1 = formatToDate(date1, "yyyy-MM-dd");
            Date d2 = formatToDate(date2, "yyyy-MM-dd");
            Long day = Long.valueOf((d1.getTime() - d2.getTime()) / 86400000L);
            return day.intValue();
        } catch (Exception var5) {
            return 0;
        }
    }

    public static Date getDateShort(Date currentTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(0);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }

    public static int getTwoDay(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            try {
                date1 = getDateShort(date1);
                date2 = getDateShort(date2);
                Long day = Long.valueOf((date1.getTime() - date2.getTime()) / 86400000L);
                return day.intValue();
            } catch (Exception var3) {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public static int getTwoMin(String datettime1, String datettime2) {
        try {
            Date d1 = formatToDate(datettime1, "yyyy-MM-dd HH:mm:ss");
            Date d2 = formatToDate(datettime2, "yyyy-MM-dd HH:mm:ss");
            Long day = Long.valueOf((d1.getTime() - d2.getTime()) / 60000L);
            return day.intValue();
        } catch (Exception var5) {
            return 0;
        }
    }

    public static int getTwoMin(Date datettime1, Date datettime2) {
        try {
            Long day = Long.valueOf((datettime1.getTime() - datettime2.getTime()) / 60000L);
            return day.intValue();
        } catch (Exception var3) {
            return 0;
        }
    }

    public static int getTwoSec(String datettime1, String datettime2) {
        try {
            Date d1 = formatToDate(datettime1, "yyyy-MM-dd HH:mm:ss");
            Date d2 = formatToDate(datettime2, "yyyy-MM-dd HH:mm:ss");
            Long day = Long.valueOf((d1.getTime() - d2.getTime()) / 1000L);
            return day.intValue();
        } catch (Exception var5) {
            return 0;
        }
    }

    public static int getTwoSec(Date datettime1, Date datettime2) {
        try {
            Long day = Long.valueOf((datettime1.getTime() - datettime2.getTime()) / 1000L);
            return day.intValue();
        } catch (Exception var3) {
            return 0;
        }
    }

    public static Date getPreSec(Date date, int second) {
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        gc.add(13, second);
        return gc.getTime();
    }

    public static String getPreSec(String datetime, int second) {
        Date date = strToDateLong(datetime);
        return dateToStrLong(getPreSec(date, second));
    }

    public static Date getPreMin(Date datetime, int min) {
        Calendar gc = Calendar.getInstance();
        gc.setTime(datetime);
        gc.add(12, min);
        return gc.getTime();
    }

    public static String getPreMin(String datetime, int min) {
        Date date = strToDateLong(datetime);
        return dateToStrLong(getPreMin(date, min));
    }

    public static Date getPreDay(Date dateDate, int day) {
        Calendar gc = Calendar.getInstance();
        gc.setTime(dateDate);
        gc.add(5, day);
        return gc.getTime();
    }

    public static String getPreDay(String date, int day) {
        Date dateDate = strToDate(date);
        return dateToStr(getPreDay(dateDate, day));
    }

    public static Date getPreMonth(Date dateDate, int month) {
        Calendar gc = Calendar.getInstance();
        gc.setTime(dateDate);
        gc.add(2, month);
        return gc.getTime();
    }

    public static Date getPreMonth(String date, int month) {
        Date dateDate = strToDate(date);
        return getPreMonth(dateDate, month);
    }

    public static String getWeekEn(String sdate) {
        Date date = strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return (new SimpleDateFormat("EEE", Locale.ENGLISH)).format(c.getTime());
    }

    public static int getWeekNum(String sdate) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(strToDate(sdate));
        return cal.get(7) - 1;
    }

    public static int getWeekNum(Date sdate) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(sdate);
        return cal.get(7) - 1;
    }

    public static String getWeekXq(String sdate) {
        int w = getWeekNum(sdate);
        String s = "";
        switch (w) {
            case 0:
                s = "Sunday";
                break;
            case 1:
                s = "Monday";
                break;
            case 2:
                s = "Tuesday";
                break;
            case 3:
                s = "Wednesday";
                break;
            case 4:
                s = "Thursday ";
                break;
            case 5:
                s = "Friday";
                break;
            case 6:
                s = "Saturday";
        }

        return s;
    }


    public static boolean isSameWeekDates(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int subYear = cal1.get(1) - cal2.get(1);
        if (0 == subYear) {
            if (cal1.get(3) == cal2.get(3)) {
                return true;
            }
        } else if (1 == subYear && 11 == cal2.get(2)) {
            if (cal1.get(3) == cal2.get(3)) {
                return true;
            }
        } else if (-1 == subYear && 11 == cal1.get(2) && cal1.get(3) == cal2.get(3)) {
            return true;
        }

        return false;
    }

    public static Date getMonthFirst(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(5, 1);
        return c.getTime();
    }

    public static Date getMonthEnd(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(5, c.getActualMaximum(5));
        return c.getTime();
    }

    public static Date startDateTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTime();
    }

    public static Date startDateTime(String dateStr) {
        Date date = formatToDate(dateStr, "yyyy-MM-dd");
        return startDateTime(date);
    }

    public static Date endDateTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        calendar.set(14, 999);
        return calendar.getTime();
    }

    public static Date endDateTime(String dateStr) {
        Date date = formatToDate(dateStr, "yyyy-MM-dd");
        return endDateTime(date);
    }

    public static String getSeqWeek() {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        String week = Integer.toString(c.get(3));
        if (week.length() == 1) {
            week = "0" + week;
        }

        String year = Integer.toString(c.get(1));
        return year + week;
    }

    public static String getWeek(String sdate, int week) {
        Date dd = strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(dd);
        if (1 == week) {
            c.set(7, 2);
        } else if (2 == week) {
            c.set(7, 3);
        } else if (3 == week) {
            c.set(7, 4);
        } else if (4 == week) {
            c.set(7, 5);
        } else if (5 == week) {
            c.set(7, 6);
        } else if (6 == week) {
            c.set(7, 7);
        } else if (0 == week) {
            c.set(7, 1);
        }

        return (new SimpleDateFormat("yyyy-MM-dd")).format(c.getTime());
    }


    public static int getAge(String birthday) {
        Date now = getNow();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        Date csrq = strToDate(birthday);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(csrq);
        return calendar.get(1) - calendar2.get(1);
    }

    public static String getNumMonthByEn(String monthEn) {
        return "JAN".equals(monthEn) ? "01" : ("FEB".equals(monthEn) ? "02" : ("MAR".equals(monthEn) ? "03" : ("APR".equals(monthEn) ? "04" : ("MAY".equals(monthEn) ? "05" : ("JUN".equals(monthEn) ? "06" : ("JUL".equals(monthEn) ? "07" : ("AUG".equals(monthEn) ? "08" : ("SEP".equals(monthEn) ? "09" : ("OCT".equals(monthEn) ? "10" : ("NOV".equals(monthEn) ? "11" : ("DEC".equals(monthEn) ? "12" : monthEn)))))))))));
    }

    public static List<Date[]> date2SplitMonth(Date startDate, Date endDate) {
        List<Date[]> l = new ArrayList();
        if (endDate.compareTo(getMonthEnd(startDate)) <= 0) {
            l.add(new Date[]{startDate, endDate});
        } else {
            Date tn = getMonthEnd(startDate);
            l.add(new Date[]{startDate, tn});

            for (Date fn = getPreMonth((Date) getMonthFirst(startDate), 1); endDate.compareTo(getMonthEnd(fn)) > 0; fn = getPreMonth((Date) fn, 1)) {
                l.add(new Date[]{fn, getMonthEnd(fn)});
            }

            l.add(new Date[]{getMonthFirst(endDate), endDate});
        }

        return l;
    }

    public static List<Date[]> dateExclude(Date fromDate, Date toDate, String str) {
        Set<Date> dset = new TreeSet();
        int pre = getTwoDay(toDate, fromDate);

        for (int i = 0; i <= pre; ++i) {
            dset.add(getPreDay(fromDate, i));
        }

        String[] strsz = StringUtils.trimToEmpty(str).replaceAll("\\s", "").split(",");
        String[] var6 = strsz;
        int var7 = strsz.length;

        for (int var8 = 0; var8 < var7; ++var8) {
            String s = var6[var8];
            String[] ssz = StringUtils.trimToEmpty(s).split("\\|");
            Date d1 = null;
            Date d2 = null;
            if (ssz.length > 0) {
                d1 = strToDate(ssz[0]);
            }

            if (ssz.length > 1) {
                d2 = strToDate(ssz[1]);
            }

            if (d1 != null || d2 != null) {
                if (d1 != null && d2 != null && d1.compareTo(d2) != 0) {
                    if (d1.compareTo(d2) > 0) {
                        Date dt = d1;
                        d1 = d2;
                        d2 = dt;
                    }

                    int p = getTwoDay(d2, d1);

                    for (int i = 0; i <= p; ++i) {
                        dset.remove(getPreDay(d1, i));
                    }
                } else {
                    d1 = d1 == null ? d2 : d1;
                    dset.remove(d1);
                }
            }
        }

        List<Date[]> list = new ArrayList();
        Iterator<Date> iter = dset.iterator();
        Date[] da = new Date[2];

        while (iter.hasNext()) {
            Date dd = (Date) iter.next();
            if (da[0] == null) {
                da[0] = dd;
                da[1] = dd;
            } else if (dd.equals(getPreDay((Date) da[1], 1))) {
                da[1] = dd;
            } else {
                list.add(da);
                da = new Date[]{dd, dd};
            }
        }

        if (da[0] != null && da[1] != null) {
            list.add(da);
        }

        return list;
    }

    public static String fmtDate(String date) {
        if (StringUtils.isBlank(date)) {
            return "";
        } else {
            String str = "";
            String[] yyhh = date.split(" ");
            String[] hhmmsssz;
            if (yyhh.length > 0) {
                hhmmsssz = yyhh[0].split("-");
                String[] var4 = hhmmsssz;
                int var5 = hhmmsssz.length;

                for (int var6 = 0; var6 < var5; ++var6) {
                    String s = var4[var6];
                    if (s.length() == 1) {
                        s = "0" + s;
                    }

                    if ("".equals(str)) {
                        str = s;
                    } else {
                        str = str + "-" + s;
                    }
                }
            }

            if (yyhh.length > 1) {
                str = str + " ";
                hhmmsssz = yyhh[1].split(":");

                for (int i = 0; i < hhmmsssz.length; ++i) {
                    String s = hhmmsssz[i];
                    if (s.length() == 1) {
                        s = "0" + s;
                    }

                    if (i == 0) {
                        str = str + s;
                    } else {
                        str = str + ":" + s;
                    }
                }
            }

            return str;
        }
    }

    public static String Ftmwk(String tm) {
        tm = StringUtils.trimToEmpty(tm).replaceAll(":+", ":");
        if (tm.length() > 5) {
            tm = StringUtils.substring(tm, 0, 5);
        }

        int c = tm.indexOf(":");
        if (c == 2 && tm.length() == 5) {
            return tm;
        } else {
            if (tm.endsWith(":")) {
                tm = tm + "0";
            }

            if (c > 0) {
                Date d = formatToDate(tm, "HH:mm");
                tm = formatToStr(d, "HH:mm");
            }

            if (tm == null) {
                return "00:00";
            } else {
                if (tm.length() == 1) {
                    tm = "0" + tm + ":00";
                } else if (tm.length() == 2) {
                    tm = tm + ":00";
                } else if (tm.length() == 3) {
                    tm = tm + "00";
                } else if (tm.length() == 4) {
                    tm = StringUtils.substring(tm, 0, 2) + ":" + StringUtils.substring(tm, 2);
                }

                if (tm.indexOf(":") < 0) {
                    tm = StringUtils.substring(tm, 0, 2) + ":" + StringUtils.substring(tm, 2, 4);
                }

                return tm;
            }
        }
    }

    public static String getFutureDay(String appDate, String format, int days) {
        String future = "";

        try {
            Calendar calendar = GregorianCalendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            Date date = simpleDateFormat.parse(appDate);
            calendar.setTime(date);
            calendar.add(5, days);
            date = calendar.getTime();
            future = simpleDateFormat.format(date);
        } catch (Exception var7) {
            ;
        }

        return future;
    }

    public static String getNextDay(String nowdate, String delay) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String mdate = "";
        Date d = null;
        if (nowdate != null && !"".equals(nowdate)) {
            d = strToDate(nowdate);
        } else {
            d = new Date();
        }

        long myTime = d.getTime() / 1000L + (long) (Integer.parseInt(delay) * 24 * 60 * 60);
        d.setTime(myTime * 1000L);
        mdate = format.format(d);
        return mdate;
    }

    public static boolean isRightDate(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        try {
            return sdf.format(sdf.parse(date)).equalsIgnoreCase(date);
        } catch (Exception var4) {
            return false;
        }
    }

    public static String getEDate(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(str, pos);
        String j = strtodate.toString();
        String[] k = j.split(" ");
        return k[2] + k[1].toUpperCase() + k[5].substring(2, 4);
    }

    public static int getYear() {
        Calendar a = Calendar.getInstance();
        return a.get(1);
    }

    public static String dateCommandTime(String date) {
        if (date != null && date.length() == 10 && isRightDate(date, "yyyy-MM-dd")) {
            String nmonth = date.substring(5, 7);
            String nDate = date.substring(8, 10);
            String nyear = date.substring(0, 4);
            if (StringUtils.isNotBlank(nyear)) {
                if (nyear.length() == 4) {
                    nyear = nyear.substring(2, 4);
                }

                String EnMonth = m_nMonth[Integer.parseInt(nmonth) - 1];
                return nDate + EnMonth + nyear;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static long getDays(String date1, String date2) {
        if (!StringUtils.isBlank(date1) && !StringUtils.isBlank(date2)) {
            SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            Date mydate = null;

            try {
                date = myFormatter.parse(date1);
                mydate = myFormatter.parse(date2);
            } catch (Exception var7) {
                ;
            }

            if (date != null && mydate != null) {
                long day = (date.getTime() - mydate.getTime()) / 86400000L;
                return day;
            } else {
                return 0L;
            }
        } else {
            return 0L;
        }
    }

    public static Calendar strToCalendar(String strDate) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        } else {
            int len = strDate.length();
            if (len == 16) {
                strDate = strDate + ":00";
            } else if (len == 10) {
                strDate = strDate + " 00:00:00";
            }

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;

            try {
                date = sdf.parse(strDate);
            } catch (ParseException var6) {
                return null;
            }

            calendar.setTime(date);
            return calendar;
        }
    }

    public static int compareDate(String origDate, String destDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        boolean var3 = false;

        byte i;
        try {
            Date dt1 = df.parse(origDate);
            Date dt2 = df.parse(destDate);
            if (dt1.getTime() > dt2.getTime()) {
                i = 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                i = -1;
            } else {
                i = 0;
            }
        } catch (Exception var6) {
            i = -2;
        }

        return i;
    }

    public static String getNextMonth(String dates, int count) {
        Date date = strToDate(dates);
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        gc.add(2, count);
        return dateToStr(gc.getTime());
    }

    public static String getMonthBegin(String dat) {
        String str = dat.substring(0, 8);
        return str + "01";
    }

    public static String getEndDateOfMonth(String dat) {
        String str = dat.substring(0, 7);
        String month = dat.substring(5, 7);
        int mon = Integer.parseInt(month);
        if (mon != 1 && mon != 3 && mon != 5 && mon != 7 && mon != 8 && mon != 10 && mon != 12) {
            if (mon != 4 && mon != 6 && mon != 9 && mon != 11) {
                if (isLeapYear(dat)) {
                    str = str + "-29";
                } else {
                    str = str + "-28";
                }
            } else {
                str = str + "-30";
            }
        } else {
            str = str + "-31";
        }

        return str;
    }

    public static boolean isLeapYear(String ddate) {
        Date d = strToDate(ddate);
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(d);
        int year = gc.get(1);
        return year % 400 == 0 ? true : (year % 4 == 0 ? year % 100 != 0 : false);
    }

    public static String getTwoMil(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long day = 0L;

        try {
            Date date = myFormatter.parse(sj1);
            Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / 60000L;
        } catch (Exception var7) {
            return "";
        }

        return day + "";
    }

    public static String getTwoMiaos(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long day = 0L;

        try {
            Date date = myFormatter.parse(sj1);
            Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / 1000L;
        } catch (Exception var7) {
            return "";
        }

        return day + "";
    }

    public static String getTwoHour(String st1, String st2) {
        String[] kk = null;
        String[] jj = null;
        kk = st1.split(":");
        jj = st2.split(":");
        if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0])) {
            return "0";
        } else {
            double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60.0D;
            double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60.0D;
            return y - u > 0.0D ? y - u + "" : "0";
        }
    }

    public static int getMinutesFromTwoHour(String st1, String st2) {
        String[] kk = st1.split(":");
        String[] jj = st2.split(":");
        if (kk.length == 2 && jj.length == 2) {
            int y = NumberUtils.toInt(kk[0], 0) * 60 + NumberUtils.toInt(kk[1], 0);
            int u = NumberUtils.toInt(jj[0], 0) * 60 + NumberUtils.toInt(jj[1], 0);
            return u - y;
        } else {
            return 0;
        }
    }

    public static Boolean isWeekend(String dateStr) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(strToDateLong(dateStr));
        int weekNum = cal.get(7) - 1;
        return weekNum != 6 && weekNum != 0 ? Boolean.valueOf(false) : Boolean.valueOf(true);
    }

    public static boolean after(String srcDate, String desDate) {
        Date date1 = strToDate(srcDate);
        Date date2 = strToDate(desDate);
        return date1.after(date2);
    }

    public static String formatLongToTimeStr(Long l) {
        long day = 0L;
        long hour = 0L;
        long minute = 0L;
        long second = 0L;
        second = l.longValue() / 1000L;
        if (second > 60L) {
            minute = second / 60L;
            second %= 60L;
        }

        if (minute > 60L) {
            hour = minute / 60L;
            minute %= 60L;
        }

        if (hour > 24L) {
            day = hour / 24L;
            hour %= 24L;
        }

        String s;
        if (day > 0L) {
            s = day + "day" + hour + "hour" + minute + "minute" + second + "second";
        } else if (hour > 0L) {
            s = hour + "hour" + minute + "minute" + second + "second";
        } else if (minute > 0L) {
            s = minute + "minute" + second + "second";
        } else {
            s = second + "second";
        }

        return s;
    }
}
