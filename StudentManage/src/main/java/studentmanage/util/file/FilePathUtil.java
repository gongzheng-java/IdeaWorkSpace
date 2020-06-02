package studentmanage.util.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件路径与文件操作
 *
 * @author cannel
 */
public class FilePathUtil {
    /**
     * 从文件路径获取扩展名
     *
     * @param filename
     * @return
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * 从文件路径获取不带扩展名的文件名
     *
     * @param filename
     * @return
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * 检查文件夹是否存在，不存在则创建
     *
     * @param floderPath
     */
    public static void checkAndCreateFolder(String floderPath) {
        File fileFloder = new File(floderPath);

        if (fileFloder.exists() == false) {
            fileFloder.mkdirs();
        }
    }

    /**
     * 获取文件夹下某个扩展名的所有文件
     *
     * @param floderPath 文件夹路径
     * @param fileExt    扩展名，是否带.都可以
     * @return
     */
    public static List<File> getFloderFileByFileExt(String floderPath, String fileExt) {
        File fileFloder = new File(floderPath);

        List<File> lstFile = new ArrayList<>();
        //获取文件夹下所有文件
        File[] files = fileFloder.listFiles();
        for (File file : files) {
            //排除文件夹
            if (!file.isDirectory()) {
                //扩展名判断
                if (file.getName().endsWith(fileExt)) {
                    lstFile.add(file);
                }
            }
        }

        return lstFile;
    }
}
