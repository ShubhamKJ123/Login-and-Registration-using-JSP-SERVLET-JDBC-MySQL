package com.uniquedeveloper.registration;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.xdevapi.Statement;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uname=request.getParameter("name");
		String uemail=request.getParameter("email");
		String upswd=request.getParameter("pass");
		String umob=request.getParameter("contact");
		RequestDispatcher dispatcher=null;
		Connection con=null;
		
		try {
			String url ="jdbc:mysql://localhost:3306/data1?useSSL=false";
			String username = "root";
			String password = "";
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url,username,password);
			PreparedStatement pst = con.prepareStatement("insert into user(uname,upswd,uemail,umob) values(?,?,?,?)");
			pst.setString(1, uname);
			pst.setString(2, upswd);
			pst.setString(3, uemail);
			pst.setString(4, umob);
			
			int rowCount = pst.executeUpdate();
			dispatcher= request.getRequestDispatcher("registration.jsp");
			
			if(rowCount>0) {
				request.setAttribute("status", "success");
			}
			else {
				request.setAttribute("status", "failed");
			}
			dispatcher.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
