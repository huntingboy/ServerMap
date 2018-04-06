package cn.nomad.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.xml.internal.bind.v2.TODO;

import cn.nomad.domain.User;
import cn.nomad.exception.UserExistException;
import cn.nomad.service.BusinessService;
import cn.nomad.service.impl.BusinessServiceImpl;
import cn.nomad.utils.WebUtils;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BusinessService service = new BusinessServiceImpl();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if("login".equals(method)){
			login(request, response);
		}
		if("register".equals(method)){
			register(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		User user = service.login(username, password);
		
		// 给客户端返回信息
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("username", user.getUsername());
			jsonObject.put("password", user.getPassword());
			response.getWriter().write(jsonObject.toString());
			request.setAttribute("message", "登录成功！");
		} catch (JSONException e) {
			e.printStackTrace();
			request.setAttribute("message", "登录失败！");
		} catch (IOException e) {
			e.printStackTrace();
			request.setAttribute("message", "登录失败！");
		}
		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}
	
	private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		//User user = WebUtils.request2Bean(request, User.class);
		User user = new User(username, password);

		try {
			service.register(user);	
			// 给客户端返回信息
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("username", user.getUsername());
			jsonObject.put("password", user.getPassword());
			response.getWriter().write(jsonObject.toString());
			request.setAttribute("message", "注册成功！");
		} catch (UserExistException e) {
			e.printStackTrace();
			request.setAttribute("message", "注册失败！");
		} catch (JSONException e) {
			e.printStackTrace();
			request.setAttribute("message", "注册失败！");
		} catch (IOException e) {
			e.printStackTrace();
			request.setAttribute("message", "注册失败！");
		} 
		//request.getRequestDispatcher("/message.jsp").forward(request, response);
	}
}
