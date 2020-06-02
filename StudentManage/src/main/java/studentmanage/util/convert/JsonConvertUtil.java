package studentmanage.util.convert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * json转换
 */
public class JsonConvertUtil {
    /**
     * json字符串转为java对象
     *
     * @param strJson
     * @param c
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T fromJsonString(String strJson, Class<T> c) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(strJson, c);
    }

    /**
     * java对象转json字符串
     *
     * @param object
     * @return
     */
    public static String toJsonString(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(object);
    }
}

