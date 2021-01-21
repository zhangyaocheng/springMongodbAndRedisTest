package com.example.mongodb.util;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * 用于处理Excel相关事件
 */
@Slf4j
public class ExcelUtil {

    /**
     * 从excel文件指定sheet中读取所有元素，按照对象二维数组的形式导出
     *
     * @param excelFilePath excel文件路径
     * @param sheetNumber excel中sheet编码,从1开始 默认是第一个
     * @return sheet表中所有元素 以二维数组的形式存在
     */
    public static List<Map<String, Object>> getExcelInfo(String excelFilePath, int sheetNumber){

        try {
            // 获取指定excel路径下workbook对象
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(excelFilePath));
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0); // 指定获取第一个sheet 默认第一个
            if (sheet==null){
                log.info("指定sheet对象为null");
                return null;
            }

            //获取文件对象到string[][]
            // 获取到行数
            int rowNum = sheet.getLastRowNum();
            XSSFRow row = sheet.getRow(0);
            // 获取到列行数，一般第一行的列长度就是整体的列长度
            int colNum = row.getLastCellNum();

            List<Map<String,Object>> result = new ArrayList<>();
            // 获取每一行的名称
            String[] firstRow = new String[colNum];
            for (int i=0;i<colNum;i++){
                XSSFRow row1 = sheet.getRow(0);
                XSSFCell cell = row1.getCell(i);
                firstRow[i]=cell.getStringCellValue();
            }

            for (int i=1;i<=rowNum;i++){
                XSSFRow row1 = sheet.getRow(i);
                Map<String,Object> map = new HashMap<>();
                for (int j=0;j<colNum;j++){
                    XSSFCell cell = row1.getCell(j);
                    if (cell==null){
                        map.put(firstRow[j],"");
                        continue;
                    }
                    CellType type = cell.getCellType();
                    if (type==CellType.BLANK){
                        map.put(firstRow[j],"");
                        continue;
                    }
                    if (type==CellType.BOOLEAN){
                        map.put(firstRow[j],cell.getBooleanCellValue());
                        continue;
                    }
                    if (type==CellType.NUMERIC){
                        map.put(firstRow[j],cell.getNumericCellValue());
                        continue;
                    }
                    if (type==CellType.STRING){
                        if (cell.getStringCellValue().trim().equalsIgnoreCase("null")){
                            map.put(firstRow[j],null);
                        }else if (cell.getStringCellValue()==null){
                            map.put(firstRow[j],"");
                        }else {
                            map.put(firstRow[j],cell.getStringCellValue());
                        }
                        continue;
                    }
                    if (type==CellType.FORMULA){
                        map.put(firstRow[j],cell.getCellFormula());
                        continue;
                    }
                    if (type==CellType.ERROR){
                        map.put(firstRow[j],cell.getErrorCellString());
                        continue;
                    }
                    String value = cell.getRawValue();
                    map.put(firstRow[j],value);
                }
                result.add(map);
            }
            return result;

        }catch (FileNotFoundException e) {
            log.info("文件未找到",e);
        } catch (IOException e) {
            log.info("获取文件流异常",e);
        }

        return null;
    }

    public static void main(String[] args){
        String filePath = "C:\\Users\\zyc\\Desktop\\test.xlsx";
        List<Map<String,Object>> result = getExcelInfo(filePath,0);
        for (int i=0;i<result.size();i++){
            log.info("map:"+result.get(i));
            Set<String> keys = result.get(i).keySet();
            for (String key:keys){
                Object value = result.get(i).get(key);
                if (value==null){
                    log.info("key:"+key+" value:"+value+" type:"+null);
                }else{
                    log.info("key:"+key+" value:"+value+" type:"+value.getClass());
                }
            }
        }
    }

}
