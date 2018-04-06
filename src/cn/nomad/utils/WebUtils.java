package cn.nomad.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

//把request请求里面的参数填充到相应的bean里面
public class WebUtils {  

	public static <T> T request2Bean(HttpServletRequest request, Class<T> beanClass){
		
		try {
			T t = beanClass.newInstance();
			Map map = request.getParameterMap();
			
			BeanUtils.populate(t, map);
			return t;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
}
