package ism;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ism.Bill;

/**
 * Servlet implementation class BillServlet
 */
@WebServlet("/BillServlet")
public class BillServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BillServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Bill> store=new ArrayList<Bill>();// dynamic storage to keep objects
		try {
			
			String cemail=request.getParameter("cemail");
			HttpSession session=request.getSession();
            session.setAttribute("cemail",cemail);
            Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3307/icecream","root","");
			String qry="select * from vbill where cemail=?";
			PreparedStatement ps=con.prepareStatement(qry);
			ps.setString(1,cemail);
			ResultSet rs = ps.executeQuery();
			Bill tmp=null;// initalize
			while(rs.next())
			{
				tmp=new Bill();
				tmp.setIname(rs.getString("iname"));tmp.setIprice(rs.getLong("iprice"));
				tmp.setQuantity(rs.getLong("quantity"));tmp.setIid(rs.getLong("iid"));
				store.add(tmp);
			}
			RequestDispatcher dis=request.getRequestDispatcher("bill.jsp");
			request.setAttribute("data", store);
			dis.forward(request, response);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
