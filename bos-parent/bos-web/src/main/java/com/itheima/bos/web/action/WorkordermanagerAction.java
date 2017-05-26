package com.itheima.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Subarea;
import com.itheima.bos.domain.Workordermanager;
import com.itheima.bos.service.WorkordermanagerService;
import com.itheima.bos.utils.FileUtils;

@Controller
@Scope("prototype")
public class WorkordermanagerAction extends BaseAction<Workordermanager> {

	@Autowired
	private WorkordermanagerService workordermanagerService;
	
	/**
	  * @Description: 保存工作单
	  * @return
	  * @throws Exception
	  * @return String
	 */
	/**
	  * @Description: 
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String save() throws Exception {
		String flag = "1";
		try {
			workordermanagerService.save(model);
		} catch (Exception e) {
			e.printStackTrace();
			flag  = "0";
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(flag);
		return NONE;
	}
	/**
	  * @Description: 批量导入工作单
	  * @return
	  * @throws Exception
	  * @return String
	 */
	private File workordermanagerXls;
	public void setWorkordermanagerXls(File workordermanagerXls) {
		this.workordermanagerXls = workordermanagerXls;
	}
	public String batchImport() throws Exception {
		List<Workordermanager> list = new ArrayList<>();
		//先获取到文件对象
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(workordermanagerXls));
		//获取到sheet 标签页
		HSSFSheet sheet = workbook.getSheet("sheet1");
		//遍历sheet  获取到行数据
		for (Row row : sheet) {
			//判断是否是第一行，第一行不需要导入到数据库中
			if(row.getRowNum()==0){
				continue;
			}
			//String id = row.getCell(0).getStringCellValue();
			String id = excel2String(row.getCell(0));
			String product = excel2String(row.getCell(1));
			String prodtimelimit = excel2String(row.getCell(2));
			String prodtype = excel2String(row.getCell(3));
			String sendername = excel2String(row.getCell(4));
			String senderphone = excel2String(row.getCell(5));
			String senderaddr = excel2String(row.getCell(6));
			String receivername = excel2String(row.getCell(7));
			String receiverphone = excel2String(row.getCell(8));
			String receiveraddr = excel2String(row.getCell(9));
			Double actlweit = Double.valueOf(excel2String(row.getCell(10)));
			
//			double actlweit = row.getCell(10).getNumericCellValue();
			Workordermanager workordermanager = new Workordermanager(id, null, product,null,null,null, prodtimelimit, prodtype, sendername, senderphone, senderaddr, receivername, receiverphone, receiveraddr, null, actlweit, null, null, null);
			list.add(workordermanager);
		}
		workordermanagerService.batchImport(list);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.getWriter().print("1");
		return NONE;
	}
	/**
	  * @Description: 分页展示工作单
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String pageQuery() throws Exception {
		workordermanagerService.pageQuery(pageBean);
		this.java2JSon(pageBean,new String[]{"currentPage","pageSize","detachedCriteria"});
		return NONE;
	}
	/**
	  * @Description: 模板的下载
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String download() throws Exception {
		//创建HSSFWorkbook对象
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建标签页
		HSSFSheet sheet = workbook.createSheet("sheet1");
		//创建标题行
		HSSFRow headRow = sheet.createRow(0);
		//创建单元格
		headRow.createCell(0).setCellValue("编号");
		headRow.createCell(1).setCellValue("产品");
		headRow.createCell(2).setCellValue("产品时限");
		headRow.createCell(3).setCellValue("产品类型");
		headRow.createCell(4).setCellValue("发件人姓名");
		headRow.createCell(5).setCellValue("发件人电话");
		headRow.createCell(6).setCellValue("发件人地址");
		headRow.createCell(7).setCellValue("收件人姓名");
		headRow.createCell(8).setCellValue("收件人电话");
		headRow.createCell(9).setCellValue("收件人地址");
		headRow.createCell(10).setCellValue("实际重量");
		
		//创建标题行
		HSSFRow Row1 = sheet.createRow(1);
		//创建单元格
		Row1.createCell(0).setCellValue("xxx");
		Row1.createCell(1).setCellValue("测试产品");
		Row1.createCell(2).setCellValue("测试时限");
		Row1.createCell(3).setCellValue("测试类型");
		Row1.createCell(4).setCellValue("张某某");
		Row1.createCell(5).setCellValue("136********");
		Row1.createCell(6).setCellValue("测试地址");
		Row1.createCell(7).setCellValue("张某某");
		Row1.createCell(8).setCellValue("136********");
		Row1.createCell(9).setCellValue("XX省XX市XX");
		Row1.createCell(10).setCellValue("12.5");
		
		
		String fileName = "工作单导入模板.xls";
		//下载文件：一个流（文件流），两个头（文件MIME类型，文件打开方式）
		HttpServletResponse response = ServletActionContext.getResponse();
		//文件MIME类型
		String contentType = ServletActionContext.getServletContext().getMimeType(fileName);
		response.setContentType(contentType);
		//浏览器类型
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");
		//attachment:附件形式下载
		fileName= FileUtils.encodeDownloadFilename(fileName, agent);
		response.setHeader("content-disposition", "attachment;fileName="+fileName);
		
		OutputStream outputStream = response.getOutputStream();
		//导出excel文件
		workbook.write(outputStream);
		return NONE;
	}
}
