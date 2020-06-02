package studentmanage.biz.controller;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import studentmanage.biz.model.Student;
import studentmanage.biz.service.StudentService;
import studentmanage.util.excelUtil.ExcelUtil;
import studentmanage.util.file.FilePathUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * Created by gongzheng on 2019-09-05.
 */
@Controller
@RequestMapping("/exportFile")
public class ExportFileController {

    @Autowired
    private StudentService studentService;

    private static int k=0;


    /**
     *导出文件到本地
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/excel")
    public String exportExcel(HttpServletRequest request, HttpServletResponse response) {

        Page<Student> page = studentService.findPageStu(1, 20, new Student());
        List<Student> students = page.getContent();
        //第一行标题
        String[] title = {"编号", "姓名", "年龄", "性别", "班级"};
        //sheet名
        String sheetName = "学生信息表";

        String[][] strs = new String[students.size()][title.length];
        int i = 0;
        for (Student student : students) {
            strs[i][0] = Integer.toString(student.getId());
            strs[i][1] = student.getName();
            strs[i][2] = Integer.toString(student.getAge());
            strs[i][3] = student.getGender();
            strs[i][4] = Integer.toString(student.getClassid());
            i++;
        }

        HSSFWorkbook workbook = ExcelUtil.getHSSFWorkbook(sheetName, title, strs, null);

        //1、默认将文档放到指定目录
        String fileName = "学生信息表"+k+".xls"; //文档名称
        String path2 = System.getProperty("user.dir");
        String filePath = "E:/IdeaWorkSpace/"+fileName;
        //如果目录不存在，则创建
        FilePathUtil.checkAndCreateFolder(path2);
        try {
            //输出Excel文件
            FileOutputStream os = new FileOutputStream(filePath);
            workbook.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
      k++;
     return "/upload";
    }

    /**
     * 下载文件
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping("/upload")
    public void upload(HttpServletRequest request, HttpServletResponse response){
        if (k>0){
            k--;
        }
        //下载文件
        /* 第一步:根据文件路径获取文件 */
        String path2 = System.getProperty("user.dir");
        File file = new File("D:/IdeaWorkSpace/学生信息表"+k+".xls");
        if (file.exists()) {
            try {
                /* 第二步：根据已存在的文件，创建文件输入流 */
                InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
                 /* 第三步：创建缓冲区，大小为流的最大字符数 */
                byte[] buffer = new byte[inputStream.available()]; // int available() 返回值为流中尚未读取的字节的数量
                /* 第四步：从文件输入流读字节流到缓冲区 */
                inputStream.read(buffer);
                /* 第五步： 关闭输入流 */
                inputStream.close();

                this.setResponseHeader(response, file.getName());
                OutputStream os = response.getOutputStream();
                os.write(buffer);
                os.flush();
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    //发送响应流方法
    private void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
