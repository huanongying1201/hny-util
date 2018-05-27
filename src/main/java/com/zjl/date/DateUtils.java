package com.zjl.date;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import com.zjl.exception.MyException;

/**
 * 工具类
 * @author Administrator
 *
 */
public class DateUtils {

	public static final String LONG_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	public static final String LONG_DATE_PATTERN_NOSPLIT = "yyyyMMddHHmmss";

	public static final String CHINA_MINITE_DATE_PATTERN = "yyyy年MM月dd日  HH:mm";

	public static final String MAIL_CHINA_MINITE_DATE_PATTERN = "yyyy年MM月dd日 HH:mm (EEE)";

	public static final String SHORT_DATE_PATTERN = "yyyy-MM-dd";
	
	public static final String SHORT_TIME_PATTERN = "HH:mm:ss";

	public static final String CHINA_MONTH_DAY_DATE_PATTERN = "MM月dd日";

	public static final String CHINA_MONTH_DATE_PATTERN = "MM月dd日  HH:mm";

	public static final String MINITE_SECOND_PATTERN = "HH:mm";

	public static final String MINITE_DATE_PATTERN = "yyyy-MM-dd HH:mm";

	public static final long MS_OF_A_DAY = 1000 * 60 * 60 * 24; // 一天的毫秒数

	public static String formatMsec(Date date) {
		String dateStr = format(date, LONG_DATE_PATTERN);
		return dateStr.replace(" ", "T") + ".000";
	}
	
	public static String formatTime(Date date) {
		return format(date, LONG_DATE_PATTERN);
	}

	public static String formatDate(Date date) {
		return format(date, SHORT_DATE_PATTERN);
	}

	public static String format(Date date, String pattern) {
		return date != null ? new DateTime(date).toString(pattern) : "";
	}

	public static String format(String pattern) {
		return format(new Date(), pattern);
	}

	public static String getWeek(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.dayOfWeek().getAsText();
	}

	public static int getYear(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.getYear();
	}
	
	public static int getMonth(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.getMonthOfYear() - 1;
	}
	
	public static int getDay(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.getDayOfMonth();
	}
	
	public static int getHour(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.getHourOfDay();
	}
	
	public static int getMin(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.getMinuteOfHour();
	}
	
	public static int getCurrentYear() {
		DateTime dateTime = new DateTime();
		return dateTime.getYear();
	}

	public static Date parseDate(String source) {
		DateTime dateTime = new DateTime(source);
		return dateTime.toDate();
	}

	public static Date parseDate(String source, String pattern) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
		return fmt.parseDateTime(source).toDate();
	}

	public static Date parseTime(String source) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern(LONG_DATE_PATTERN);
		return fmt.parseDateTime(source).toDate();
	}

	public static Date addMinutes(Date lockTime, Integer lockPeriod) {
		DateTime dateTime = new DateTime(lockTime);
		return dateTime.plusMinutes(lockPeriod).toDate();
	}

	public static Date addDays(Date pswBeainTime, Integer pswLife) {
		DateTime dateTime = new DateTime(pswBeainTime);
		return dateTime.plusDays(pswLife).toDate();
	}

	public static Date minusDays(Date pswBeainTime, Integer pswLife) {
		DateTime dateTime = new DateTime(pswBeainTime);
		return dateTime.minusDays(pswLife).toDate();
	}

	/**
	 * 
	 * 描述: 截取 date 到日期
	 * 
	 * @author liulb
	 * @created 2014年8月20日 上午9:51:32
	 * @since v1.1.0
	 * @param date
	 * @return
	 */
	public static Date truncateDate(Date date) {
		return date != null ? new LocalDate(date).toDate() : null;
	}

	/**
	 * 时间的合并显示
	 */
	public static String converTime(long timeMillis) {
		long timeGap = (System.currentTimeMillis() - timeMillis) / 1000;//与现在时间相差秒数
		String timeStr = null;
		if (timeGap > 365 * 24 * 60 * 60) {//1年以上
			timeStr = format(new Date(timeMillis), CHINA_MINITE_DATE_PATTERN);
		} else if (timeGap > 30 * 24 * 60 * 60) {//1月以上
			timeStr = format(new Date(timeMillis), CHINA_MONTH_DATE_PATTERN);
		} else if (timeGap > 24 * 60 * 60) {//1天以上
			timeStr = timeGap / (24 * 60 * 60) + "天前";
		} else if (timeGap > 60 * 60) {//1小时-24小时
			timeStr = timeGap / (60 * 60) + "小时前";
		} else if (timeGap > 60) {//1分钟-59分钟
			timeStr = timeGap / 60 + "分钟前";
		} else {//1秒钟-59秒钟
			timeStr = "刚刚";
		}
		return timeStr;
	}

	//上周的周日
	@SuppressWarnings("deprecation")
	@Deprecated
	public static Date getPreviousWeekSunday()
	  {
	    int  weeks = -1;
	    int mondayPlus = getMondayPlus();
	    GregorianCalendar currentDate = new GregorianCalendar();
	    currentDate.add(5, mondayPlus + weeks);
	    Date monday = currentDate.getTime();
	    monday.setHours(23);
		monday.setMinutes(59);
		monday.setSeconds(59);
	    return monday;
	  }

	//上周的周一
	@SuppressWarnings("deprecation")
	@Deprecated
	public static Date getPreviousWeekMonday() {
		int weeks = -1;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(5, mondayPlus + 7 * weeks);
		Date monday = currentDate.getTime();
		monday.setHours(0);
		monday.setMinutes(0);
		monday.setSeconds(0);
		return monday;
	}

	private static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		int dayOfWeek = cd.get(7) - 1;
		if (dayOfWeek == 1) {
			return 0;
		}
		return (1 - dayOfWeek);
	}
	
	/**
	 * 返回日期开始时间(yyyy-MM-dd 00:00:00 000)
	 * @param date
	 * @return
	 */
	@Deprecated
	public static Date toDayStartTime(Date date) {
		return org.apache.commons.lang.time.DateUtils.truncate(date, Calendar.DATE);
	}

	/**
	 * 返回日期结束时间(yyyy-MM-dd 23:59:59 999)
	 * @param date
	 * @return
	 */
	@Deprecated
	public static Date toDayEndTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}
	/**
	 * 
	 *	
	 * 描述: 链接两个日期,默认认为是同一天,不考虑跨天的情况
	 *
	 * @author  basil
	 * @created 2014-10-24 下午3:24:21
	 * @since   v1.0.0 
	 * @param startDate
	 * @param endDate
	 * @return
	 * @return  String
	 */
	public static String joinDate(Date startDate, Date endDate) {
		return format(startDate, LONG_DATE_PATTERN) + "-" + format(endDate, SHORT_TIME_PATTERN);
	}
	
	/**
	 * 随机生成某个区间内的时间
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Date randomRangeDate(String startDate,String endDate) {
		Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
		if(!pattern.matcher(startDate).matches() || !pattern.matcher(endDate).matches()) {
			throw new MyException("时间格式必须为yyyy-MM-dd!");
		}
		if(startDate.compareTo(endDate) >= 0) {
			throw new MyException("开始时间必须小于结束时间!");
		}
		Calendar calendar = Calendar.getInstance();
		// 注意月份要减去1
		String[] startYMD = startDate.split("-");
		calendar.set(Integer.parseInt(startYMD[0]), Integer.parseInt(startYMD[1]) - 1, Integer.parseInt(startYMD[2]));
		calendar.getTime().getTime();
		// 根据需求，这里要将时分秒设置为0
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		long min = calendar.getTime().getTime();

		String[] endYMD = endDate.split("-");
		calendar.set(Integer.parseInt(endYMD[0]), Integer.parseInt(endYMD[1]) - 1, Integer.parseInt(endYMD[2]));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.getTime().getTime();
		long max = calendar.getTime().getTime();
		// 得到大于等于start小于end的double值
		double randomDate = Math.random() * (max - min) + min;
		// 将double值舍入为整数，转化成long类型
		calendar.setTimeInMillis(Math.round(randomDate));
		return calendar.getTime();
	}

}
