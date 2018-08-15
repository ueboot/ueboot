package com.ueboot.file.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class DateUtil {

	public static final String defaultDatePattern = "yyyy-MM-dd HH:mm:ss";

	private static SimpleDateFormat datePattern = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat datePatternYY_MM_DD_HHMM = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static final String datePatternYYMMDD = "yyyyMMdd";
	public static final String datePatternYY_MM_DD = "yyyy-MM-dd";
	public static final String datePatternYY_MM_DDHHMMSS = "yyyy-MM-dd HH:mm:ss";
	public static final String datePatternYYMM = "yyyyMM";
	public static final String datePatternYYYYVMMVDD = "yyyy/MM/dd";
	public static final String datePatternMMDDYYYY = "MM/dd/yyyy";
	public static final String datePatternMMMDDYYYYHHMMSS = "MMM d yyyy HH:mm:ss";
	public static final String datePatternMMMDDYYYY = "MMM d yyyy";
	public static final String datePatternYYYY = "yyyy";
	public static final String datePatternyyyyMMddHHmmss = "yyyyMMddHHmmss";
	
	
	public static String converDateToString(Date date , String pattern){
		SimpleDateFormat sdf  = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	public static Date getSimpleDate(Date date,int day, int minute, int second) {
		if (date == null) {
			date = new Date();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, day);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		return calendar.getTime();
	}
	
	public static Date getSimpleEffDate(Date date) {
		if (date == null) {
			date = new Date();
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
	
	public static Date getSimpleExpDate(Date date) {
		if (date == null) {
			date = new Date();
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	public static Date getStandardEffDate(Date date) {
		if (date == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date getStandardExpDate(Date date) {
		if (date == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	public static Calendar getCalendar(Date date) {
		if (date == null || "".equals(date))
			return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	public static Calendar getCurrentCalendar() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	public static Date getCurrentTime() {
		return new Date();
	}

	public static String getCurrentTimeByDefaultPattern() {
		return new SimpleDateFormat(defaultDatePattern).format(new Date());
	}

	public static String getCurrentTimeByCustom1Pattern() {
		return new SimpleDateFormat(datePatternyyyyMMddHHmmss).format(new Date());
	}
	
	public static String getTimeByDefaultPattern(Date date) {
		if (date != null) {
			return new SimpleDateFormat(defaultDatePattern).format(date);
		}
		return null;
	}

	public static String getTimeByCustomPattern(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}

	public static String getCurrentTimeByCustomPattern(String pattern) {
		return new SimpleDateFormat(pattern).format(new Date());
	}

	public static boolean isEndOfSeason(Date date) {
		Calendar calendar = getCalendar(date);
		int month = calendar.get(Calendar.MONTH);
		if (month % 3 == 2) {
			return true;
		}
		return false;
	}

	public static int getMonth(Date date) {
		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	public static int getDay(Date date) {
		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.DATE);
	}

	public static int getYear(Date date) {
		Calendar calendar = getCalendar(date);
		return calendar.get(Calendar.YEAR);
	}

	public static int getMonthLength(Date date) {
		Calendar calendar = getCalendar(date);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static Date getActualMaximumDate(Date date) {
		Calendar calendar = getCalendar(date);
		int actualMaximumDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, actualMaximumDay);
		return calendar.getTime();
	}

	public static Date getActualMinimumDate(Date date) {
		Calendar calendar = getCalendar(date);
		int actualMininumDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, actualMininumDay);
		return calendar.getTime();
	}

	public static Date getSpecifyDate(Date date, int indexDay) {
		Calendar calendar = getCalendar(date);
		int actualMininumDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, actualMininumDay + indexDay - 1);
		return calendar.getTime();
	}

	public static String getYearMonth(Date date) {
		return new SimpleDateFormat("yyyyMM").format(date);
	}

	public static Date getPriorMonthDate(Date date) {
		Calendar calendar = getCalendar(date);
		int month = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);
		Calendar newCalendar = Calendar.getInstance();
		if (month == 0) {
			year--;
			month = 11;
		} else {
			month--;
		}
		newCalendar.set(year, month, 1);
		return newCalendar.getTime();
	}

	public static Date getpriorMonthDateByMonth(Date date) {
		Calendar calendar = getCalendar(date);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		Calendar newCalendar = Calendar.getInstance();

		if (month == 4 || month == 5 || month == 6) {
			newCalendar.set(year, 3, 0);
			return newCalendar.getTime();
		} else if (month == 7 || month == 8 || month == 9) {
			newCalendar.set(year, 6, 0);
			return newCalendar.getTime();
		} else if (month == 10 || month == 11 || month == 12) {
			newCalendar.set(year, 9, 0);
			return newCalendar.getTime();
		} else {
			year--;
			newCalendar.set(year, 12, 0);
			return newCalendar.getTime();
		}

	}

	public static Date getPerforMancebonusMonthDateByMonth(Date date) {
		Calendar calendar = getCalendar(date);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		Calendar newCalendar = Calendar.getInstance();
		if (month == 3 || month == 4 || month == 5) {
			newCalendar.set(year, 3, 0);
			return newCalendar.getTime();
		} else if (month == 6 || month == 7 || month == 8) {
			newCalendar.set(year, 6, 0);
			return newCalendar.getTime();
		} else if (month == 9 || month == 10 || month == 11) {
			newCalendar.set(year, 9, 0);
			return newCalendar.getTime();
		} else {
			year--;
			newCalendar.set(year, 12, 0);
			return newCalendar.getTime();
		}

	}

	public static Date getLastMonth(Date date) {
		Calendar calendar = getCalendar(date);
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	public static Date getNextMonth(Date date) {
		Calendar calendar = getCalendar(date);
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month + 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return getActualMaximumDate(calendar.getTime());
	}

	public static Date parse(String str) throws ParseException {
		return parse(str, defaultDatePattern);
	}

	public static Date parse(String str, String pattern) {
		if (str == null)
			return null;
		try {
			return new SimpleDateFormat(pattern).parse(str);
		} catch (ParseException e) {
			throw new RuntimeException("���ڸ�ʽת������", e);
		}
	}

	public static Date parse(Date date, int months) {
		String str = getTimeByCustomPattern(date, "yyyy-MM");
		Date d = parse(str, "yyyy-MM");
		Calendar calendar = getCalendar(d);
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, (month - months));
		return calendar.getTime();
	}

	public static boolean isEndQuarter(Date date) {
		boolean retval = false;
		Calendar calendar = getCalendar(date);
		int month = calendar.get(Calendar.MONTH);
		if ((month + 1) % 3 == 0) {
			retval = true;
		}
		return retval;
	}

	public static boolean isMidYear(Date date) {
		boolean retval = false;
		Calendar calendar = getCalendar(date);
		int month = calendar.get(Calendar.MONTH);
		if ((month + 1) % 6 == 0) {
			retval = true;
		}
		return retval;
	}

	public static boolean isEndYear(Date date) {
		boolean retval = false;
		Calendar calendar = getCalendar(date);
		int month = calendar.get(Calendar.MONTH);
		if ((month + 1) % 12 == 0) {
			retval = true;
		}
		return retval;
	}

	/**
	 * 
	 * @return
	 */
	public static String getCurrentTimeByFullPattern() {
		return new SimpleDateFormat(getDateTimePattern()).format(new Date());
	}

	public static String getDateTimePattern() {
		return defaultDatePattern + " HH:mm:ss.SSS";
	}

	public static String convertToDateString(Object exeDate) {
		String exeDateStr = "";
		if (exeDate != null && exeDate instanceof String) {
			long dataValue = Long.parseLong((String) exeDate);
			Date date = new Date(dataValue);
			exeDateStr = getTimeByCustomPattern(date, "yyyy-MM-dd");
		}
		return exeDateStr;
	}

	public static Date getDate(Date date, String dateType, int dateValue) {
		if (date == null) {
			date = new Date();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (dateType.equals("DATE")) {
			calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + dateValue);
		} else if (dateType.equals("MONTH")) {
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + dateValue);
		} else if (dateType.equals("YEAR")) {
			calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + dateValue);
		}

		return getSimpleEffDate(calendar.getTime());
	}

	public static Date getNextYear(Date date, int yearNum) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + yearNum);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR));
		return calendar.getTime();
	}

	public static Date getNextDateByMonth(Date date, int monthNum) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + monthNum);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR));
		return calendar.getTime();
	}
	
	public static Date getNextHour(Date date, int dayNum) {
		if(date == null){
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + dayNum);
		return calendar.getTime();
	}

	public static Date getNextDay(Date date, int dayNum) {
		if(date == null){
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + dayNum);
		return calendar.getTime();
	}

	public static Date getFirstDayOfLastMonth(Date date, int monthNum, int dayNum) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + monthNum);
		calendar.set(Calendar.DAY_OF_MONTH, dayNum);
		return calendar.getTime();
	}

	public static Date getLastDayOfThisMonth(String strDate) throws Exception {

		try {
			Date date = new SimpleDateFormat("yyyy-MM").parse(strDate.trim());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.MONTH, (calendar.get(Calendar.MONTH) + 1));
			calendar.set(Calendar.DAY_OF_YEAR, (calendar.get(Calendar.DAY_OF_YEAR) - 1));
			return calendar.getTime();
		} catch (ParseException e) {
			throw new Exception("??????????");
		}

	}

	public static Object checkPlanTermDate(Date startDate, Date endDate) {
		Calendar calenderStart = Calendar.getInstance();
		calenderStart.setTime(getSimpleEffDate(startDate));
		Calendar calenderEnd = Calendar.getInstance();
		calenderEnd.setTime(getSimpleEffDate(endDate));
		StringBuffer sm = new StringBuffer();
		if (calenderStart.after(calenderEnd) || calenderStart.equals(calenderEnd))
			sm.append("??????????��?????????!");
		if (calenderStart.get(Calendar.MONTH) != calenderEnd.get(Calendar.MONTH))
			sm.append("??????????????????????????!");
		if (sm.length() != 0) {
			return sm;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static Map getDateRange(Date data) {
		Map dates = new HashMap();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		dates.put("DATEBEGIN", calendar.getTime());
		calendar.setTime(data);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MINUTE, 59);
		dates.put("DATEEND", calendar.getTime());
		return dates;
	}

	public static Date transStringToDate(String str) {
		try {
			Date dateTemp = datePattern.parse(str);
			return dateTemp;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getTimeByPattern(Date date, String formatType) {
		return new SimpleDateFormat(formatType).format(date);
	}


	public static String getTimeByPattern(Date date, String formatType, Locale locale) {
		if (locale == null) {
			locale = Locale.getDefault();
		}
		if (date == null) {
			date = new Date();
		}
		return new SimpleDateFormat(formatType, locale).format(date);
	}

	public static String dateToString(Date date) {
		if(date == null){
			return null;
		}
		try {
			String dateStr = datePattern.format(date);
			return dateStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String dateToStringHHMM(Date date) {
		
		try {
			String dateStr = datePatternYY_MM_DD_HHMM.format(date);
			return dateStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date getDateByString(String strDate, String strFormate){
		try {
			SimpleDateFormat simpleFormat = new SimpleDateFormat(strFormate);
			return simpleFormat.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static boolean isEquals(Date src, Date dest) {
		if (src == null || dest == null) {
			return false;
		}
		return datePattern.format(src).equals(datePattern.format(dest));

	}
	
	public static java.sql.Date getNewSqlDate(){
		return new java.sql.Date(new Date().getTime());
	}
	
	public static String formatTime(GregorianCalendar ca) throws Exception {

		int yy = ca.get(Calendar.YEAR);
		int mm = ca.get(Calendar.MONTH) + 1;
		int dd = ca.get(Calendar.DATE);
		int hh = ca.get(Calendar.HOUR_OF_DAY);
		int ms = ca.get(Calendar.MINUTE);
		int ss = ca.get(Calendar.SECOND);

		String yyy = String.valueOf(yy);
		String mmm = (mm < 10) ? "0" + String.valueOf(mm) : String.valueOf(mm);
		String ddd = (dd < 10) ? "0" + String.valueOf(dd) : String.valueOf(dd);
		String hhh = (hh < 10) ? "0" + String.valueOf(hh) : String.valueOf(hh);
		String mss = (ms < 10) ? "0" + String.valueOf(ms) : String.valueOf(ms);
		String sss = (ss < 10) ? "0" + String.valueOf(ss) : String.valueOf(ss);
		String datetime;
		datetime = yyy + mmm + ddd + hhh + mss + sss;
		return datetime;
	}
	
	public static String formatDateTimeSss(GregorianCalendar ca) throws Exception {

		int yy = ca.get(Calendar.YEAR);
		int mm = ca.get(Calendar.MONTH) + 1;
		int dd = ca.get(Calendar.DATE);
		int hh = ca.get(Calendar.HOUR_OF_DAY);
		int mi = ca.get(Calendar.MINUTE);
		int ss = ca.get(Calendar.SECOND);
		int sss = ca.get(Calendar.MILLISECOND);
		
		String yyy = String.valueOf(yy);
		String mmm = (mm < 10) ? "0" + String.valueOf(mm) : String.valueOf(mm);
		String ddd = (dd < 10) ? "0" + String.valueOf(dd) : String.valueOf(dd);
		String hhh = (hh < 10) ? "0" + String.valueOf(hh) : String.valueOf(hh);
		String msStr = (mi < 10) ? "0" + String.valueOf(mi) : String.valueOf(mi);
		String ssStr = (ss < 10) ? "0" + String.valueOf(ss) : String.valueOf(ss);
		String sssStr = (sss < 10) ? ".0" + String.valueOf(sss) : "."+String.valueOf(sss);
		String datetime;
		datetime = yyy + mmm + ddd +"T"+ hhh + msStr + ssStr + sssStr;
		return datetime;
	}
	
	public static String genRandomStr(){
		String randomStr = null;
		try {
			Random r = new Random();
			randomStr = formatDateTimeSss(new GregorianCalendar())+"_"+r.nextInt(999999)+r.nextInt(666666);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return randomStr;

	}
	
	
	public static String subOneDay(String str) {
		if(str==null||str.length()==0){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");    
		Calendar cd = Calendar.getInstance();  
		try {  
			cd.setTime(sdf.parse(str));  
		} catch (ParseException e) {              
			e.printStackTrace();  
		}  
		cd.add(Calendar.DATE, -1);//����һ�� 
		Date date=cd.getTime();
		String time=sdf.format(date);
		return time;
	}


	public static void main(String[] args) {
		//System.out.println(getNextHour(getCurrentTime(), -10));
		try {
//			String s = "2012-02-07 10:14:45";
//			
//			Date date = DateUtil.getDateByString(s, datePatternYY_MM_DDHHMMSS);
//			System.out.println(date.toString());
//			
//			Date now = new Date();
//			System.out.println(now.toString());
			
			/*System.out.println(DateUtil.getTimeByCustomPattern(new Date(), DateUtil.datePatternYYYY));
			System.out.println(getNextDay(new Date(), -1));
			System.out.println(getSimpleDate(new Date()));
			 */
			System.out.println(getSimpleEffDate(new Date()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
