package com.itheima.bos.web.action;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import com.itheima.bos.domain.Region;
import com.itheima.bos.utils.PageBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
	
	protected String INDEX = "index";
	protected String LIST = "list";

	protected T model;
	
	PageBean pageBean = new PageBean();
	
	DetachedCriteria detachedCriteria = null;
	
	//页面提交参数，调用set方法赋值
	public void setPage(int page) {
		pageBean.setCurrentPage(page);
	}

	public void setRows(int rows) {
		pageBean.setPageSize(rows);
	}
	
	public BaseAction() {
		try {
			//获取当前运行类class
			Class clzz = this.getClass();  //子类CustomerDaoImpl<Customer>
			//获取父类参数化类型
			Type type = clzz.getGenericSuperclass();
			ParameterizedType pt = (ParameterizedType) type;
			//获取实际类型参数
			Type[] types = pt.getActualTypeArguments();
			clzz =  (Class) types[0];
			detachedCriteria = DetachedCriteria.forClass(clzz);
			pageBean.setDetachedCriteria(detachedCriteria);
			model = (T) clzz.newInstance();
			System.out.println(model);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public T getModel() {
		return (T) model;
	}
	
	/**
	  * @Description: 将java对象转为json字符串
	  * @return void
	  * "currentPage","pageSize","detachedCriteria"
	 */
	public void java2JSon(Object obj, String[] excludes){
		try {
			//转json
			JSONObject jsonObject = new JSONObject();
			//将PageBean对象中不需要转json的属性排除掉
			JsonConfig config = new JsonConfig();
			//排除属性
			config.setExcludes(excludes);
			JSONObject json = jsonObject.fromObject(obj, config);  //total rows
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @Description: 将java对象转为json字符串
	 * @return void
	 * "currentPage","pageSize","detachedCriteria"
	 */
	public void java2JSon(List list, String[] excludes){
		try {
			//转json
			JSONArray jsonArray = new JSONArray();
			//将PageBean对象中不需要转json的属性排除掉
			JsonConfig config = new JsonConfig();
			//排除属性
			config.setExcludes(excludes);
			String json = jsonArray.fromObject(list, config).toString();  //total rows
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/json;charset=utf-8");
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public String excel2String(Cell memberCell){
		String str = null;
		DecimalFormat df = new DecimalFormat("#"); 
		  switch (memberCell.getCellType())
        {
            case HSSFCell.CELL_TYPE_NUMERIC:// 数字
            	str = df.format(memberCell.getNumericCellValue());
                break;
            case HSSFCell.CELL_TYPE_STRING:// 字符串
            	str = df.format(Double.parseDouble(memberCell.toString()));
                break;
            default:
            	str = memberCell.toString();
                break;
        }
		 return str;
	}
	
}
