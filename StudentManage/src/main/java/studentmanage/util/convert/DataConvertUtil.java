package studentmanage.util.convert;


import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.sql.Clob;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 常用类型转换
 *
 * @author cannel
 */
public class DataConvertUtil {

    /**XX转Str------------------------------------*/

    /**
     * object转String，无法转换返回""
     *
     * @param value
     * @return
     */
    public static String objToStr(Object value) {
        return value != null ? value.toString() : "";
    }

    /**
     * Date按固定格式转String，无法转换返回""
     *
     * @param value
     * @param format
     * @return
     */
    public static String dateToStr(Date value, String format) {
        String dateString;
        if (value == null) {
            dateString = "";
        } else {
            SimpleDateFormat formatDate = new SimpleDateFormat(format, Locale.getDefault());
            dateString = formatDate.format(value);
        }

        return dateString;
    }

    /**
     * Date按格式yyyy-MM-dd转String，无法转换返回""
     *
     * @param value
     * @return
     */
    public static String dateToStr(Date value) {
        return dateToStr(value, "yyyy-MM-dd");
    }

    /**
     * Double转String , 如果传入null则返回""
     */
    public static String douToStr(Double value) {
        if (value == null) {
            return "";
        } else {
            return value.toString();
        }
    }

    /**
     * Integer转String , 如果传入null则返回""
     */
    public static String integerToStr(Integer value) {
        if (value == null) {
            return "";
        } else {
            return value.toString();
        }
    }

    /**
     * Long转String , 如果传入null则返回""
     */
    public static String longToStr(Long value) {
        if (value == null) {
            return "";
        } else {
            return value.toString();
        }
    }

    /**obj转XX------------------------------*/

    /**
     * Object转double，无法转换返回0
     *
     * @param value
     * @return
     */
    public static double objToDou(Object value) {
        double result = 0;

        if (value != null) {
            String str = value.toString();
            try {
                result = Double.parseDouble(str);
            } catch (Exception e) {

            }
        }

        return result;
    }

    /**
     * 真实值为doubel的object，转string，不能转换返回空字符串
     *
     * @param value
     * @return
     */
    public static String objDouToStr(Object value) {
        return objDouToStr(value, null);
    }

    /**
     * 真实值为doubel的object，转string，不能转换返回空字符串
     *
     * @param value
     * @param format
     * @return
     */
    public static String objDouToStr(Object value, String format) {
        String result = "";

        if (value != null) {
            Double dou = null;
            String str = value.toString();
            try {
                dou = Double.parseDouble(str);
            } catch (Exception e) {

            }
            if (dou != null) {
                if (StringUtils.isEmpty(format)) {
                    result = dou.toString();
                } else {
                    DecimalFormat df = new DecimalFormat(format);
                    result = df.format(dou);
                }
            }
        }

        return result;
    }

    /**
     * object转Long，无法转换返回0
     *
     * @param value
     * @return
     */
    public static Long objToLong(Object value) {
        Long result = 0l;

        if (value != null) {
            String str = value.toString();
            try {
                result = Long.parseLong(str);
            } catch (Exception e) {

            }
        }

        return result;
    }

    /**
     * obj转Integer，无法转换返回null
     */
    public static Integer objToInteger(Object obj) {
        String strObj = objToStr(obj);
        Integer result = null;
        try {
            result = Integer.parseInt(strObj);
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * Clob字段的object转string
     *
     * @param value
     * @return
     * @throws Exception
     */
    public static String objClobToStr(Object value) throws Exception {
        Clob clob = (Clob) value;
        String objv = "";
        BufferedReader cb = new BufferedReader(clob.getCharacterStream());
        String str = "";
        while ((str = cb.readLine()) != null) {
            objv = objv.concat(str); // 最后以String的形式得到
        }

        return objv;
    }

    /**str转XX------------------------------*/

    /**
     * string转double，无法转换返回0
     *
     * @param value
     * @return
     */
    public static double strToDou(String value) {
        double result = 0;

        try {
            result = Double.parseDouble(value);
        } catch (Exception e) {

        }

        return result;
    }

    /**
     * string转double，无法转换返回0
     *
     * @param value
     * @return
     */
    public static Double strToDouble(String value) {
        Double result = (double) 0;

        try {
            result = Double.parseDouble(value);
        } catch (Exception e) {

        }

        return result;
    }

    /**
     * string转Double，无法转换返回null
     *
     * @param value
     * @return
     */
    public static Double strToDouNull(String value) {
        Double result = null;

        try {
            result = Double.parseDouble(value);
        } catch (Exception e) {

        }

        return result;
    }

    /**
     * string转Long，无法转换返回0
     *
     * @param value
     * @return
     */
    public static Long strToLong(String value) {
        Long result = (long) 0;

        try {
            result = Long.parseLong(value);
        } catch (Exception e) {

        }

        return result;
    }

    /**
     * string转Long，无法转换返回null
     *
     * @param value
     * @return
     */
    public static Long strToLongNull(String value) {
        Long result = null;

        try {
            result = Long.parseLong(value);
        } catch (Exception e) {

        }

        return result;
    }

    /**
     * string转int，无法转换返回0
     *
     * @param value
     * @return
     */
    public static int strToInt(String value) {
        int result = 0;

        try {
            result = Integer.parseInt(value);
        } catch (Exception e) {

        }

        return result;
    }

    /**
     * string转Integer，无法转换返回null
     *
     * @param value
     * @return
     */
    public static Integer strToIntegerNull(String value) {
        Integer result = null;

        try {
            result = Integer.parseInt(value);
        } catch (Exception e) {

        }

        return result;
    }

    /**
     * string转Date，无法转换返回null
     *
     * @param value
     * @return
     */
    public static Date strToDate(String value) {
        return strToDate(value, "yyyy-MM-dd");
    }

    /**
     * string转Date，无法转换返回null
     *
     * @param value
     * @param format
     * @return
     */
    public static Date strToDate(String value, String format) {
        if (value != null && value.trim().length() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());

            try {
                Date e = sdf.parse(value);
                return e;
            } catch (ParseException var4) {
                var4.printStackTrace();
            }
        }

        return null;
    }

    /**
     * string转string，如果传入null则返回空字符串""
     */
    public static String strToStr(String value) {
        if (value != null)
            return value;
        else
            return "";
    }

    /**
     * string转string，如果传入null或空字符串则返回null
     */
    public static String strToStrNull(String value) {
        if (!StringUtils.isEmpty(value))
            return value;
        else
            return null;
    }

    /**XX对象类型转XX值类型，例如Double转double---------------------------*/

    /**
     * Double转double , 如果传入null则返回0
     */
    public static double douNullToDou(Double value) {
        double result = 0;
        if (value == null) {
            return result;
        } else {
            result = value;
            return result;
        }
    }

    /**
     * Integer转int , 如果传入null则返回0
     */
    public static int intNullToInt(Integer value) {
        int result = 0;
        if (value == null) {
            return result;
        } else {
            result = value;
            return result;
        }
    }

    /**
     * Long转long , 如果传入null则返回0
     */
    public static long longNullToLong(Long value) {
        long result = 0;
        if (value == null) {
            return result;
        } else {
            result = value;
            return result;
        }
    }

    /**其他--------------------------------*/

    /**
     * Long转int
     *
     * @param value
     * @return
     */
    public static int LongToInt(Long value) {
        if (value == null)
            return 0;
        else
            return value.intValue();
    }

    /**
     * 把string按逗号分隔开并转为Long的List
     *
     * @param strs
     * @return
     */
    public static List<Long> strsToLongList(String strs) {
        if (StringUtils.isEmpty(strs)) return new ArrayList<>();

        List<String> lstStrId = Arrays.asList(strs.contains(",") ? strs.split(",") : new String[]{strs});

        List<Long> lstId = new ArrayList<>();
        for (String strId : lstStrId) {
            Long value = DataConvertUtil.strToLong(strId);
            if (!lstId.contains(value)) {
                lstId.add(value);
            }
        }

        return lstId;
    }

    /**
     * 把string按逗号分隔开并转为Int的List
     *
     * @param strs
     * @return
     */
    public static List<Integer> strsToIntList(String strs) {
        if (StringUtils.isEmpty(strs)) return new ArrayList<>();

        List<String> lstStrId = Arrays.asList(strs.contains(",") ? strs.split(",") : new String[]{strs});

        List<Integer> lstId = new ArrayList<>();
        for (String strId : lstStrId) {
            int value = DataConvertUtil.strToInt(strId);
            if (!lstId.contains(value)) {
                lstId.add(value);
            }
        }

        return lstId;
    }

    /**
     * 把string按逗号分隔开并转为Double的List
     *
     * @param strs
     * @return
     */
    public static List<Double> strsToDouList(String strs) {
        if (StringUtils.isEmpty(strs)) return new ArrayList<>();

        List<String> lstStrId = Arrays.asList(strs.contains(",") ? strs.split(",") : new String[]{strs});

        List<Double> lstId = new ArrayList<>();
        for (String strId : lstStrId) {
            Double value = DataConvertUtil.strToDouble(strId);
            if (!lstId.contains(value)) {
                lstId.add(value);
            }
        }

        return lstId;
    }

    /**
     * 把string按逗号分隔开并转为String的List，空字符串不会加到返回结果
     *
     * @param strs
     * @return
     */
    public static List<String> strsToStrList(String strs) {
        if (StringUtils.isEmpty(strs)) return new ArrayList<>();

        List<String> lstStrId = Arrays.asList(strs.contains(",") ? strs.split(",") : new String[]{strs});

        List<String> lstId = new ArrayList<>();
        for (String strId : lstStrId) {
            if (!StringUtils.isEmpty(strId)) {
                lstId.add(strId);
            }
        }

        return lstId;
    }

    /**
     * Date转LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        if (date == null) return null;

        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Date转LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate dateToLocalDate(Date date) {
        if (date == null) return null;

        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * LocalDateTime转Date
     *
     * @param localDateTime
     * @return
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;

        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDate转Date
     *
     * @param localDate
     * @return
     */
    public static Date localDateToDate(LocalDate localDate) {
        if (localDate == null) return null;

        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 日期时间（LocalDateTime）按默认格式转字符串
     *
     * @param value
     * @return
     */
    public static String localDateTimeToStr(LocalDateTime value) {
        return localDateTimeToStr(value, "yyyy-MM-dd");
    }

    /**
     * 日期时间（LocalDateTime）按指定格式转字符串
     *
     * @param value
     * @param format
     * @return
     */
    public static String localDateTimeToStr(LocalDateTime value, String format) {
        String dateString;
        if (value == null) {
            dateString = "";
        } else {
            DateTimeFormatter formatDate = DateTimeFormatter.ofPattern(format);
            dateString = value.format(formatDate);
        }

        return dateString;
    }

    /**
     * 日期（LocalDate）按默认格式转字符串
     *
     * @param value
     * @return
     */
    public static String localDateToStr(LocalDate value) {
        return localDateToStr(value, "yyyy-MM-dd");
    }

    /**
     * 日期（LocalDate）按指定格式转字符串
     *
     * @param value
     * @param format
     * @return
     */
    public static String localDateToStr(LocalDate value, String format) {
        String dateString;
        if (value == null) {
            dateString = "";
        } else {
            DateTimeFormatter formatDate = DateTimeFormatter.ofPattern(format);
            dateString = value.format(formatDate);
        }

        return dateString;
    }

    /**
     * 字符串按默认格式转日期时间（LocalDateTime）
     *
     * @param value
     * @return
     */
    public static LocalDateTime strToLocalDateTime(String value) {
        return strToLocalDateTime(value, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 字符串按指定格式转日期时间（LocalDateTime）
     *
     * @param value
     * @param format
     * @return
     */
    public static LocalDateTime strToLocalDateTime(String value, String format) {
        if (value != null && value.trim().length() > 0) {
            DateTimeFormatter formatDate = DateTimeFormatter.ofPattern(format);

            try {
                return LocalDateTime.parse(value, formatDate);
            } catch (Exception e) {
                return null;
            }
        }

        return null;
    }

    /**
     * 字符串按默认格式转日期时间（LocalDate）
     *
     * @param value
     * @return
     */
    public static LocalDate strToLocalDate(String value) {
        return strToLocalDate(value, "yyyy-MM-dd");
    }

    /**
     * 字符串按指定格式转日期时间（LocalDate）
     *
     * @param value
     * @param format
     * @return
     */
    public static LocalDate strToLocalDate(String value, String format) {
        if (value != null && value.trim().length() > 0) {
            DateTimeFormatter formatDate = DateTimeFormatter.ofPattern(format);

            try {
                return LocalDate.parse(value, formatDate);
            } catch (Exception e) {
                return null;
            }
        }

        return null;
    }

    /**
     * RGB转16进制
     */
    public static String rgbToHexEncoding(int R, int G, int B) {
        StringBuffer strHex = new StringBuffer();
        String strR = Integer.toHexString(R);
        String strG = Integer.toHexString(G);
        String strB = Integer.toHexString(B);

        strR = strR.length() == 1 ? "0" + strR : strR;
        strG = strG.length() == 1 ? "0" + strG : strG;
        strB = strB.length() == 1 ? "0" + strB : strB;

        strHex.append("#");
        strHex.append(strR.toUpperCase());
        strHex.append(strG.toUpperCase());
        strHex.append(strB.toUpperCase());

        return strHex.toString();
    }
}
