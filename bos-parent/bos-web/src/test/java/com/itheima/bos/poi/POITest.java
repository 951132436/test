package com.itheima.bos.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

public class POITest {

	/**
	  * @Description: 从本地磁盘获取excel文件，从excel文件中获取数据
	  * @return void
	 */
	@Test
	public void test(){
		try {
			String filePath = "H:\\javaEE49_BOS\\BOS-day05\\3.参考资料\\区域导入测试数据.xls";
			//使用POI提供对象HSSFWorkBook
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File(filePath)));
			//获取第一个sheet标签页
			HSSFSheet sheet = workbook.getSheetAt(0);
			//遍历标签页获取到行
			for (Row row : sheet) {
				//遍历行获取到单元格
				System.out.println();
				for (Cell cell : row) {
					System.out.print(cell.getStringCellValue() + " ");
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
