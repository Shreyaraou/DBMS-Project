package ism;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AdminloginServlet
 */
@WebServlet("/AdminloginServlet")
public class AdminloginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminloginServlet() {
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
		String aid = request.getParameter("aid");
        String password = request.getParameter("pass");
        String uid=aid;
        try
        {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3307/icecream","root","");
            PreparedStatement statement = con.prepareStatement("select aid, pass from admin where aid =? and pass=?");
            statement.setString(1, aid);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            if(result.next()){  
            	RequestDispatcher dispatch=request.getRequestDispatcher("admincontrol.jsp");
    			request.setAttribute("info","Sucess");
    			dispatch.forward(request, response);
            }else{
            	RequestDispatcher dispatch=request.getRequestDispatcher("adminlogin.jsp");
    			request.setAttribute("info", "Invalid credentials");
    			dispatch.forward(request, response);
            }
        }catch(Exception e){
            System.out.println("DB related Error");
            e.printStackTrace();
        }
	}

}
