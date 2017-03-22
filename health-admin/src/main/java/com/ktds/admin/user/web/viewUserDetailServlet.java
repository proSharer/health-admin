package com.ktds.admin.user.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ktds.admin.authorization.vo.AuthorizationVO;
import com.ktds.admin.user.service.UserService;
import com.ktds.admin.user.service.UserServiceImpl;
import com.ktds.admin.user.vo.UserVO;


public class viewUserDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserService userService;

	public viewUserDetailServlet() {
		userService = new UserServiceImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String userId = request.getParameter("userId");
		Map<String, Object> user = userService.getOneUserWithAuthorizations(userId);
		UserVO userVO = (UserVO) user.get("user");
		
		List<AuthorizationVO> authList = (List<AuthorizationVO>) user.get("authorizations");
		
		request.setAttribute("user", userVO);
		request.setAttribute("authList", authList);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/authorization/detail.jsp");
		dispatcher.forward(request, response);
		
	}

}
