package com.itheima.bos.web.action;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Region;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.service.SubareaService;
import com.itheima.bos.utils.FileUtils;

@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea> {
	private String ids;

	public void setIds(String ids) {
		this.ids = ids;
	}

	@Autowired
	private SubareaService subareaService;
	
	
	/**
	 * 分区保存
	 * @return
	 * @throws Exception
	 */
	public String deleteBatch() throws Exception {
		subareaService.deleteBatch(ids);
		return LIST;
	}
	
	
	/**
	 * 分区保存
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		subareaService.save(model);
		return LIST;
	}
	
	
	
	
	/**
	  * @Description: 分页查询（带条件查询）
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String pageQuery() throws Exception {
		DetachedCriteria criteria = pageBean.getDetachedCriteria();
		// 设置查询的条件
		if(StringUtils.isNotBlank(model.getAddresskey())){
			//MatchMode.ANYWHERE like %百度%  
			//MatchMode.START like %百度  
			//MatchMode.END like 百度%  
			//默认 MatchMode.EXACT like 百度   
			criteria.add(Restrictions.like("addresskey", model.getAddresskey(), MatchMode.ANYWHERE));
		}
		//根据关联表region表中普通字段查询
		Region region = model.getRegion();
		if(region!=null){
			//1、两张表关联查询，使用离线对象查询，需要给关联属性起别名
			//p1:关联对象的属性
			//p2:对象的别名，任意
			criteria.createAlias("region", "test");
			//通过别名设置查询条件
			if(StringUtils.isNotBlank(region.getProvince())){
				//2、通过别名.属性来设置关联表中普通字段查询条件
				criteria.add(Restrictions.like("test.province", region.getProvince(), MatchMode.ANYWHERE));
			}
			//通过别名设置查询条件
			if(StringUtils.isNotBlank(region.getCity())){
				//2、通过别名.属性来设置关联表中普通字段查询条件
				criteria.add(Restrictions.like("test.city", region.getCity(), MatchMode.ANYWHERE));
			}
			//通过别名设置查询条件
			if(StringUtils.isNotBlank(region.getDistrict())){
				//2、通过别名.属性来设置关联表中普通字段查询条件
				criteria.add(Restrictions.like("test.district", region.getDistrict(), MatchMode.ANYWHERE));
			}
		}
		pageBean.setDetachedCriteria(criteria);
		subareaService.pageQuery(pageBean);
		this.java2JSon(pageBean, new String[]{"currentPage","pageSize","detachedCriteria","subareas","decidedzone"});
		return NONE;
	}
	
	
	/**
	  * @Description: 导出分区数据，使用附件下载Excel文件
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String exportExls() throws Exception {
		//查询数据
		List<Subarea> list = subareaService.findAll();
		//创建HSSFWorkbook对象
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建标签页
		HSSFSheet sheet = workbook.createSheet("分区数据");
		//创建标题行
		HSSFRow headRow = sheet.createRow(0);
		//创建单元格
		headRow.createCell(0).setCellValue("编号");
		headRow.createCell(1).setCellValue("关键字");
		headRow.createCell(2).setCellValue("位置");
		headRow.createCell(3).setCellValue("省市区");
		//创建数据行
		for (Subarea subarea : list) {
			//getLastRowNum获取标签页中，最后一行的索引值
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum()+1);
			dataRow.createCell(0).setCellValue(subarea.getId());
			dataRow.createCell(1).setCellValue(subarea.getAddresskey());
			dataRow.createCell(2).setCellValue(subarea.getPosition());
			dataRow.createCell(3).setCellValue(subarea.getRegion().getName());
		}
		
		String fileName = "分区数据.xls";
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
	
	/**
	  * @Description: 未关联到定区分区记录
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String findNotAssociationList() throws Exception {
		List<Subarea> list = subareaService.findNotAssociationList();
		//转json
		this.java2JSon(list, new String[]{"decidedzone","region"});
		return NONE;
	}
	
	
	//接收页面提交的定区ID
	private String decidedzoneId;
	public void setDecidedzoneId(String decidedzoneId) {
		this.decidedzoneId = decidedzoneId;
	}


	/**
	  * @Description: 根据定区ID查询分区集合
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String findListByDecidedzoneId() throws Exception {
		List<Subarea> list = subareaService.findListByDecidedzoneId(decidedzoneId);
		if(list!=null){
			this.java2JSon(list, new String[]{"decidedzone","subareas"});
		}else{
			String result = "{\"total\":0,\"rows\":[]}";
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().write(result);
		}
		return NONE;
	}
	
	/**
	  * @Description: 根据省分组统计分区信息
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String findListGroupByProvince() throws Exception {
		List<Object> list = subareaService.findListGroupByProvince();
		this.java2JSon(list, new String[]{});
		return NONE;
	}

// 修改取派员的方法
			public String update() throws Exception {
				//先查询
				Subarea subarea = subareaService.findOne(model.getId());
				if(subarea!=null){

					subarea.setAddresskey(model.getAddresskey());
					subarea.setDecidedzone(model.getDecidedzone());
					subarea.setEndnum(model.getEndnum());
					subarea.setPosition(model.getPosition());
					subarea.setRegion(model.getRegion());
					subarea.setSingle(model.getSingle());
					subarea.setStartnum(model.getStartnum());
					subareaService.update(subarea);
				}
				/*private String id;
				private Decidedzone decidedzone;
				private Region region;
				private String addresskey;
				private String startnum;
				private String endnum;
				private String single;
				private String position;*/
				
				
				return "list";
			}
}
