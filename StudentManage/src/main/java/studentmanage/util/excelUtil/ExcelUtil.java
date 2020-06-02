package studentmanage.util.excelUtil;

import org.apache.poi.hssf.usermodel.*;

/**
 * .基本操作步骤：

 首先，我们应该要知道的是，一个Excel文件对应一个workbook，一个workbook中有多个sheet组成，一个sheet是由多个行(row)和列(cell)组成。那么我们用poi要导出一个Excel表格

 的正确顺序应该是：

 1、用HSSFWorkbook打开或者创建“Excel文件对象”

 2、用HSSFWorkbook对象返回或者创建Sheet对象

 3、用Sheet对象返回行对象，用行对象得到row对象

 4、对Cell对象读写。

 5、将生成的HSSFWorkbook放入HttpServletResponse中响应到前端页面
 * Created by gongzheng on 2019-09-05.
 */
public class ExcelUtil {

    /**
     *  Excel文档
     * @param sheetName 文档名
     * @param title 标题
     * @param values
     * @param wb
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName,String[] title,String[][] values,HSSFWorkbook wb){

        //创建一个HSSFWorkbook，对应一个excel文件
        if (wb==null){
            wb=new HSSFWorkbook();
        }
       //在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet= wb.createSheet(sheetName);
       //在sheet中添加表头第1行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row= sheet.createRow(0);

        //创建一个居中格式
        HSSFCellStyle style=wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
         //声明列对象
        HSSFCell cell=null;

        //给每一列赋值，创建第一行标题
        for (int i=0;i<title.length;i++){
            cell=row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }
        //填充内容
        for (int i=0;i<values.length;i++){
            row= sheet.createRow(i+1);//创建行
            for (int j=0;j<values[i].length;j++){
                cell=row.createCell(j);
                cell.setCellValue(values[i][j]);
            }
        }
        return wb;
    }
}
