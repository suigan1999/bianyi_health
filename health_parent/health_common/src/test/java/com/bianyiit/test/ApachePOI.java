package com.bianyiit.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ApachePOI {
    /**
     * ApachePOI用来操作excel文档
     * 使用POI可以从一个已经存在的Excel文件中读取数据。
     * 注意 excel里面的内容如果是数字需要转换
     */
    @Test
    public void test01() throws Exception {
        //加载指定文件，创建一个Excel对象（工作簿）
        XSSFWorkbook excle=new XSSFWorkbook(new FileInputStream(new File("C:\\Users\\翟辉\\Desktop\\不常用应用\\test\\excel\\Student.xlsx")));
        //读取Excel文件中第一个Sheet标签页
        XSSFSheet sheetAt = excle.getSheetAt(0);
        //遍历sheetAt获取每一行的数据
        for (Row row : sheetAt) {
            //遍历行，获取每一个单元格
            for (Cell cell : row) {
                System.out.print(cell.getStringCellValue());
            }
            System.out.println();
        }
        excle.close();

    }

    @Test
    public void test2() throws Exception{
        //加载指定文件，创建一个Excel对象（工作簿）
        XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(new File("C:\\Users\\翟辉\\Desktop\\不常用应用\\test\\excel\\Student.xlsx")));
        //读取Excel文件中第一个Sheet标签页
        XSSFSheet sheet = excel.getSheetAt(0);
        //获得当前工作表中最后一个行号，需要注意：行号从0开始
        int lastRowNum = sheet.getLastRowNum();
        System.out.println("lastRowNum = " + lastRowNum);
        for(int i=0;i<=lastRowNum;i++){
            XSSFRow row = sheet.getRow(i);//根据行号获取每一行
            //获得当前行最后一个单元格索引
            short lastCellNum = row.getLastCellNum();
            System.out.println("lastCellNum = " + lastCellNum);
            for(int j=0;j<lastCellNum;j++){
                XSSFCell cell = row.getCell(j);//根据单元格索引获得单元格对象
                System.out.println(cell.getStringCellValue());
            }
        }
        //关闭资源
        excel.close();
    }
    /*向excel写入数据*/
    @Test
    public void test03() throws Exception{
        XSSFWorkbook excel = new XSSFWorkbook();
        //创建一个工作表对象
        XSSFSheet sheet = excel.createSheet("oracle");
        //在工作表中创建行对象
        XSSFRow title = sheet.createRow(0);
        //在行中创建单元格对象
        title.createCell(0).setCellValue("姓名");
        title.createCell(1).setCellValue("地址");
        title.createCell(2).setCellValue("年龄");

        XSSFRow dataRow = sheet.createRow(1);
        dataRow.createCell(0).setCellValue("小明");
        dataRow.createCell(1).setCellValue("北京");
        dataRow.createCell(2).setCellValue("20");

        //创建一个输出流，通过输出流将内存中的Excel文件写到磁盘
        FileOutputStream out = new FileOutputStream(new File("C:\\Users\\翟辉\\Desktop\\不常用应用\\test\\excelhello.xlsx"));
        excel.write(out);
        out.flush();
        excel.close();

    }

}
