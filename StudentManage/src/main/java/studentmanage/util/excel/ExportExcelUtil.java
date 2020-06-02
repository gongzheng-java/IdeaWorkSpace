package studentmanage.util.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * 导出excel
 */
public class ExportExcelUtil {
    /**
     * 把List<Map<String, Object>>格式的数据，写到一个新的excel文件
     * 使用情况：
     * 数据是List<Map<String, Object>>格式
     * excel是新文件不使用模板
     *
     * @param lstResult
     * @param lstFieldDef
     * @param fileName
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String listMapToExcelFile(List<Map<String, Object>> lstResult, List<FieldDef> lstFieldDef, String filePath, String fileName) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        workbook.createInformationProperties();
        HSSFSheet sheet = workbook.createSheet();

        //添加标题行
        HSSFRow row = sheet.createRow(0);
        int fieldIdx = 0;
        for (FieldDef fieldDef : lstFieldDef) {

            HSSFCell cell = row.createCell(fieldIdx);
            cell.setCellValue(fieldDef.getTitle());

            fieldIdx++;
        }

        //添加数据行
        int rowIdx = 1;
        for (Map<String, Object> mapResult : lstResult) {
            row = sheet.createRow(rowIdx);

            fieldIdx = 0;
            for (FieldDef fieldDef : lstFieldDef) {
                HSSFCell cell = row.createCell(fieldIdx);

                Object objValue = mapResult.get(fieldDef.getProp());
                if (objValue instanceof Long) {
                    cell.setCellValue((Long) objValue);
                } else if (objValue instanceof Double) {
                    cell.setCellValue((Double) objValue);
                } else if (objValue instanceof Integer) {
                    cell.setCellValue((Integer) objValue);
                } else if (objValue != null) {
                    cell.setCellValue(objValue.toString());
                } else {
                    cell.setCellValue("");
                }

                fieldIdx++;
            }

            rowIdx++;
        }

        //保存excel文件
        OutputStream output = new FileOutputStream(filePath);
        workbook.write(output);
        output.close();

        return fileName;
    }
}
