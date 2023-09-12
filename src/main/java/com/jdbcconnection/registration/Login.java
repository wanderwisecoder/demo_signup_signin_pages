package com.jdbcconnection.registration;

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


@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	private static Connection conn;
	private static String userName;
	private static String password;
	private static String url;
	private static String driver;
       
       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		userName = "root";
		password = "MySQL@5050";
		url = "jdbc:mysql://localhost:3306/company_table1";
		driver = "com.mysql.cj.jdbc.Driver";
	
		
		String uemail = request.getParameter("uemail");
		String upwd = request.getParameter("password");
		
		HttpSession session = request.getSession();
		
		RequestDispatcher dispatcher = null ;
		

		
		
		try
		{
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userName, password);
			System.out.println(conn + " Connected To DB");
			
			String sql = "select * from users where uemail = ? and upwd = ?"; 
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, uemail);
			ps.setString(2, upwd);
			

			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
			{
				System.out.println("Logined Sucessfully");
				
				session.setAttribute("name", rs.getString("uname"));
				dispatcher = request.getRequestDispatcher("index.jsp");
			}
			else
			{
				request.setAttribute("status", "failed");
				dispatcher = request.getRequestDispatcher("login.jsp");	
			}
			dispatcher.forward(request,response);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}


