import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.Date;

/**
 * Created by gongzheng on 2019-09-06.
 */
public class Test {
    public static void main(String[] args) {

        try {
            File patha = new File("");
            String filePath = patha.getCanonicalPath();
            System.out.println(filePath);
            //获取跟目录
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if(!path.exists()) path = new File("");

            System.out.println("path:"+path.getAbsolutePath());

            //springboot获取当前项目路径的地址
             String path2=   System.getProperty("user.dir");
            File pathb = new File(path2);
            System.out.println("配置文件："+System.getProperty("server.port"));
            System.out.println("上级目录："+pathb.getParentFile());

            //获取classes目录绝对路径
            String path3 = ClassUtils.getDefaultClassLoader().getResource("").getPath();
            String path4 = ResourceUtils.getURL("classpath:").getPath();
            System.out.println(path3);
            System.out.println(path4);

            //如果上传目录为/static/images/upload/，则可以如下获取：
            File upload = new File(path2,"static/images/upload/");
            if(!upload.exists()) upload.mkdirs();
            System.out.println("upload url:"+upload.getAbsolutePath());

            //读取本地文件

        }catch (Exception e){

        }





    }

    /**
     * 获取精确到秒的时间戳
     * @return
     */
    public static int getSecondTimestamp(Date date){
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            return Integer.valueOf(timestamp.substring(0,length-3));
        } else {
            return 0;
        }
    }
}
