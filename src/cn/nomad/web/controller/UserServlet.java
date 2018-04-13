package cn.nomad.web.controller;

import java.io.BufferedReader;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		printClientInfo(request);
		String method = request.getParameter("method");
		if ("login".equals(method)) {
			login(request, response);
		}
		if ("register".equals(method)) {
			register(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		printClientInfo(request);
		BufferedReader reader = request.getReader();
		String temp, stringJson = "";
		while ((temp = reader.readLine()) != null) {
			stringJson += temp;
		}
		try {
			JSONObject jsonObject = new JSONObject(stringJson);
			String method = jsonObject.getString("method");
			if ("login".equals(method)) {
				login(request, response);
			}
			if ("register".equals(method)) {
				register(request, response);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		User user = service.login(username, password);

		try {
			// 给客户端返回信息 jsonobject or jsonarray 看返回的是一个对象还是对象的集合
			JSONObject jsonObject = new JSONObject();
			if (user == null) {
				request.setAttribute("message", "登录失败！");
			} else {
				jsonObject.put("username", user.getUsername());
				jsonObject.put("password", user.getPassword());
				System.out.println("userservlet->doget->login->jsonobject:"
						+ jsonObject.toString());
				response.getWriter().write(jsonObject.toString());
				request.setAttribute("message", "登录成功！");
			}
		} catch (JSONException e) {
			e.printStackTrace();
			request.setAttribute("message", "登录失败！");
		} catch (IOException e) {
			e.printStackTrace();
			request.setAttribute("message", "登录失败！");
		}
		//request.getRequestDispatcher("/message.jsp").forward(request, response);
	}

	private void register(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		// User user = WebUtils.request2Bean(request, User.class);
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
	
	public void printClientInfo(HttpServletRequest request){
		String address = request.getRemoteAddr();
		String host = request.getRemoteHost();
		int port = request.getRemotePort();
		String user = request.getRemoteUser();
		
		System.out.println("address:" + address + ", host:" + host + ", port:" + port + ", user:" + user);
	}
}
