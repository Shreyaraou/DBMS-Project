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
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class FillServlet
 */
@WebServlet("/FillServlet")
public class FillServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FillServlet() {
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
		// TODO Auto-generated method stub
		HttpSession session2=request.getSession();
		String email=(String)session2.getAttribute("cemail");
		String siid=request.getParameter("iid");
		String squantity=request.getParameter("quantity");
		long iid=Long.parseLong(siid);
		long quantity=Long.parseLong(squantity);
		
		try {
			// Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			// connection: url, username,pass
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3307/icecream","root","");
			// writing query
			String qry="insert into orders(cemail,iid,quantity) values(?,?,?)";
			// Writing statement
			PreparedStatement ps=con.prepareStatement(qry);
			ps.setString(1, email);ps.setLong(2, iid);ps.setLong(3, quantity);
			
			//execute 
			int ack=ps.executeUpdate();
			RequestDispatcher dis=request.getRequestDispatcher("fill.jsp");
			if(ack!=0)
			{
				System.out.println("Record inserted");
				request.setAttribute("info","Icecream Added");
			}
			else
			{
				System.out.println("Record not inserted");
				request.setAttribute("info", "Icecream not added");
			}
			dis.forward(request, response);
			
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			RequestDispatcher dis=request.getRequestDispatcher("fill.jsp");
			System.out.println("Invalid iid");
			request.setAttribute("info", "Invalid Icecream ID");
			dis.forward(request, response);
			e1.printStackTrace();
		
	}
	}

}
