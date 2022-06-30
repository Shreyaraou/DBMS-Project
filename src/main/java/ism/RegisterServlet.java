package ism;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name=request.getParameter("name");
		String phone=request.getParameter("phno");
		String mail=request.getParameter("email");
		String address=request.getParameter("address");
		String pass=request.getParameter("pass");
		long pno=Long.parseLong(phone);
		
		try {
			// Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			// connection: url, username,pass
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3307/icecream","root","");
			// writing query
			String qry="insert into customer(name,phone,email,address,pass) values(?,?,?,?,?)";
			// Writing statement
			PreparedStatement ps=con.prepareStatement(qry);
			ps.setString(1, name);ps.setLong(2, pno);ps.setString(3, mail);ps.setString(4, address);ps.setString(5, pass);
			
			//execute 
			int ack=ps.executeUpdate();
			RequestDispatcher dis=request.getRequestDispatcher("custlogin.jsp");
			if(ack!=0)
			{
				System.out.println("Record inserted");
				request.setAttribute("info","account created");
			}
			else
			{
				System.out.println("Record not inserted");
				request.setAttribute("info", "account not created");
			}
			dis.forward(request, response);
			
		} catch (ClassNotFoundException e1) {
			System.out.println("Record not inserted");
			request.setAttribute("info", "account already exists");
			e1.printStackTrace();
		} catch (SQLException e1) {
			RequestDispatcher dis=request.getRequestDispatcher("custlogin.jsp");
			System.out.println("account already exists");
			request.setAttribute("info", "account already exists");
			dis.forward(request, response);
			e1.printStackTrace();
		
	}
	}

}
