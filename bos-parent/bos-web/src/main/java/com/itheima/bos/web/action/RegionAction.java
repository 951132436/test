package com.itheima.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.dao.DecidedzoneDao;
import com.itheima.bos.dao.SubareaDao;
import com.itheima.bos.domain.Region;
import com.itheima.bos.service.RegionService;
import com.itheima.bos.service.SubareaService;
import com.itheima.bos.utils.PinYin4jUtils;

@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region> {

	@Autowired
	private RegionService regionService;
	
	//接收上传文件
	private File regionXls;
	public void setRegionXls(File regionXls) {
		this.regionXls = regionXls;
	}


	/**
	  * @Description: 上传excel文件
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String upload() throws Exception {
		List<Region> list = new ArrayList<>();
		//获取到excel文件对象
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(regionXls));
		//获取标签页
		HSSFSheet sheet = workbook.getSheet("Sheet1");
		//遍历标签页，
		for (Row row : sheet) {
			//判断：第一行表头信息不需要导入到数据库中
			if(row.getRowNum()==0){
				continue;
			}
			//获取行中数据
			//通过getCell（int index单元格索引）
			String id = row.getCell(0).getStringCellValue();
			String province = row.getCell(1).getStringCellValue();
			String city = row.getCell(2).getStringCellValue();
			String district = row.getCell(3).getStringCellValue();
			String postcode = row.getCell(4).getStringCellValue();
			
			Region region = new Region(id, province, city, district, postcode, null, null, null);	
			
			// 简码：HBSJZQX
			province = province.substring(0, province.length()-1);
			city = city.substring(0, city.length()-1);
			district = district.substring(0, district.length()-1);
			
			String info = province+city+district;
			System.out.println(info);
			
			String[] headByString = PinYin4jUtils.getHeadByString(info);
			
			String shortcode = StringUtils.join(headByString, "");
			region.setShortcode(shortcode);
			
			// 城市编码：shijiazhuang
			String citycode = PinYin4jUtils.hanziToPinyin(city, "");
			region.setCitycode(citycode);
						
			list.add(region);
		}
		regionService.saveBatch(list);
		return LIST;
	}
	
	
	/**
	  * @Description: 分页查询并带条件查询
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String pageQuery() throws Exception {
		DetachedCriteria criteria = pageBean.getDetachedCriteria();
		if(StringUtils.isNotBlank(model.getProvince())){
			criteria.add(Restrictions.like("province", model.getProvince(),MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(model.getCity())){
			criteria.add(Restrictions.like("city", model.getCity(),MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(model.getDistrict())){
			criteria.add(Restrictions.like("district", model.getDistrict(),MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(model.getCitycode())){
			criteria.add(Restrictions.like("citycode", model.getCitycode(),MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(model.getPostcode())){
			criteria.add(Restrictions.like("postcode", model.getPostcode(),MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(model.getShortcode())){
			criteria.add(Restrictions.like("shortcode",model.getShortcode(),MatchMode.ANYWHERE));
		}
		pageBean.setDetachedCriteria(criteria);
		//在BaseAction中前三个属性赋值完成。
		//目的：给PageBean CurrentPage  PageSize设值
		regionService.pageQuery(pageBean);
		this.java2JSon(pageBean, new String[]{"currentPage","pageSize","detachedCriteria","subareas"});
		return NONE;
	}
	
	
	private String q;
	public void setQ(String q) {
		this.q = q;
	}


	/**
	  * @Description: 获取区域全部数据
	  * 返回数据json格式：[{},{},{}]
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String listajax() throws Exception {
		List<Region> list = null;
		if(StringUtils.isNotBlank(q)){
			//根据参数查询数据
			list = regionService.findByQ(q);
		}else{
			//查询所有
			list = regionService.findAll();
		}
		//将集合转为json
		this.java2JSon(list,  new String[]{"subareas"});
		return NONE;
	}
	/**
	  * @Description: 区域的添加
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String save() throws Exception {
		regionService.save(model);
		return "list";
	}
	
	private String ids;
	public void setIds(String ids) {
		this.ids = ids;
	}
	/**
	  * @Description: 区域的批量删除
	  * @return
	  * @throws Exception
	  * @return String
	 */
	@Autowired
	private SubareaService subareaService;
	public String deleteIds() throws Exception {
		subareaService.updateOrId(ids);
		regionService.deleteIds(ids);
		return "list";
	}
	/**
	  * @Description: 区域的修改
	  * @return
	  * @throws Exception
	  * @return String
	 */
	public String edit() throws Exception {
		String id = model.getId();
		System.out.println(id);
		Region region = regionService.findById(id);
		region.setCity(model.getCity());//市
		region.setCitycode(model.getCitycode());//城市编码
		region.setDistrict(model.getDistrict());//区
		region.setPostcode(model.getPostcode());//邮编
		region.setProvince(model.getProvince());//省
		region.setShortcode(model.getShortcode());//简码
		regionService.edit(region);
		return "list";
	}
	
	

	
}
