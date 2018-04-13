package cn.nomad.web.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import com.sun.xml.internal.bind.v2.TODO;

public class CharaterEncodingFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/*;charset=UTF-8");
		System.out.println("===characterencodingfilter===");
		//chain.doFilter(new MyRequest(request), response);
		chain.doFilter(request, response);
	}
	
	//TODO 如果是浏览器访问服务器，则下面的增强request类才使用
	class MyRequest extends HttpServletRequestWrapper{

		private HttpServletRequest request;
		public MyRequest(HttpServletRequest request) {
			super(request);
			this.request = request;
		}
		@Override
		public String getParameter(String name) {

			String value = this.request.getParameter(name);
			if(!request.getMethod().equalsIgnoreCase("get")){
				return value;
			}
			if(value == null){
				return null;
			}
			try {
				return value = new String(value.getBytes("iso8859-1"), request.getCharacterEncoding());
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException();
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
