package com.ssafy.controller;

import com.ssafy.vo.PageInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/main.do")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private FoodController foodController = FoodController.getInstance();
	private UserController userController = UserController.getInstance();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    process(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    process(request, response);
	}

	private void process(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String action = req.getParameter("action");
		PageInfo page = null;

		try {
			switch (action) {
			    // Food
                case "foodList": page = foodController.getBookList(req, res); break;
                // User
				case "login": page = userController.login(req, res); break;
				case "logout": page = userController.logout(req, res); break;
				case "signUp": page = userController.signUp(req, res); break;
				case "userList": page = userController.getUserList(req, res); break;
				case "userDelete": page = userController.deleteUser(req, res); break;

//				// 도서 관련
//				case "getRegister": page = bookController.getRegister(req, res); break;
//				case "register": page = bookController.register(req, res); break;
//				case "bookList": page = bookController.findAll(req, res); break;
//				case "view": page = bookController.findByIsbn(req, res); break;
//				case "search": page = bookController.searchBy(req, res); break;
//				case "bookDelete": page = bookController.delete(req, res); break;
//				// 사용자 관련
//				case "orderList": page = userController.getPurchaseListByUser(req, res); break;
//				case "order": page = userController.doPurchase(req, res); break;
			}

			if (page.isForward()) {
				req.getRequestDispatcher(page.getUrl()).forward(req, res);
				return;
			} else {
				res.sendRedirect(page.getUrl());
				return;
			}
		} catch (Exception e) {
			req.setAttribute("errorMsg", e.getMessage());
			req.getRequestDispatcher("/error.jsp").forward(req, res);
		}
	}
}
