package studentmanage.util.web;


import studentmanage.util.file.FilePathUtil;

/**
 * Http Response操作
 */
public class ResponseUtil {
    /**
     * 根据文件名获取ContentType
     *
     * @param fileName
     * @return
     */
    public static String getResponseContentType(String fileName) {
        String fileExt = FilePathUtil.getExtensionName(fileName);

        String contentType = null;

        //图片
        if ("jpg".equalsIgnoreCase(fileExt) || "jpe".equalsIgnoreCase(fileExt) || "jpeg".equalsIgnoreCase(fileExt)) {
            contentType = "image/jpeg";
        } else if ("png".equalsIgnoreCase(fileExt)) {
            contentType = "image/png";
        } else if ("gif".equalsIgnoreCase(fileExt)) {
            contentType = "image/gif";
        } else if ("bmp".equalsIgnoreCase(fileExt)) {
            contentType = "application/x-bmp";
        } else {
            contentType = "application/octet-stream";
        }

        return contentType;
    }
}
