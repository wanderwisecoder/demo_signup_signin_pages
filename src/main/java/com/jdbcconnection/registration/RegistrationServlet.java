package com.jdbcconnection.registration;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;




@WebServlet("/registrationServlet")
public class RegistrationServlet extends HttpServlet {
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

		
		String uname = request.getParameter("name");
		String uemail = request.getParameter("email");
		String upwd  = request.getParameter("pass");
		String umobile = request.getParameter("contact");
		RequestDispatcher dispatcher = null ;
		

//		PrintWriter out = response.getWriter();
//		out.print("Working");
//		out.print(uname);
//		out.print(uemail);
//		out.print(upwd);
//		out.print(umobile);
		
		try 
		{
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userName, password);
			System.out.println(conn + " Connected To DB");
			
			String sql = "insert into users(uname,upwd,uemail,umobile) values(?,?,?,?) ";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, uname);
			ps.setString(2, upwd);
			ps.setString(3, uemail);
			ps.setString(4, umobile);
		
			int rowCount = ps.executeUpdate();
			dispatcher = request.getRequestDispatcher("registration.jsp");

			
			if (rowCount == 1) 
			{
				request.setAttribute("status", "success");
				System.out.println("Data Inserted Sucessfully");
				//response.sendRedirect("registration.jsp");
			}
			else
			{
				request.setAttribute("status", "failed");
			}
		
			dispatcher.forward(request,response);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{
			//System.exit(0);
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
	}

}
