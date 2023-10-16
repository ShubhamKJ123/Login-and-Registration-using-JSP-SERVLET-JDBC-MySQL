package com.uniquedeveloper.registration;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uemail= request.getParameter("username");
		String upswd= request.getParameter("password");
		Connection con=null;
		HttpSession session=request.getSession();
		RequestDispatcher dispatcher=null;
		
		try {
			String url ="jdbc:mysql://localhost:3306/data1?useSSL=false";
			String username = "root";
			String password = "";
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url,username,password);
			PreparedStatement pst = con.prepareStatement("select * from user where uemail=? and upswd=?");
			pst.setString(1, uemail);
			pst.setString(2, upswd);
			
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				session.setAttribute("name", rs.getString("uname"));
				dispatcher= request.getRequestDispatcher("index.jsp");
			}
			else {
				request.setAttribute("status", "failed");
				dispatcher= request.getRequestDispatcher("login.jsp");
			}
			dispatcher.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		
		}
	}

}
