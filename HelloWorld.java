//package tejas.project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class HelloWorld
 */
@WebServlet("/HelloWorld")
public class HelloWorld extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public HelloWorld() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
		//String FID = "";							//The fruit ID for the customer
		String mango_qty = "";
		String mango_d = "";
		String chikoo_qty= "";
		String chikoo_d= "";
		String guava_qty= "";
		String guava_d= "";
		
		String first_name = request.getParameter("first_name");
		String last_name = request.getParameter("last_name");
		String address = request.getParameter("address");
		String city = request.getParameter("city");
		String zip = request.getParameter("zip");
		Context envContext = null;
		try{
			envContext = new InitialContext();
			Context initContext = (Context)envContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)initContext.lookup("jdbc/tejas");
			Connection con = ds.getConnection();
			Statement stmt = con.createStatement();
			int rs;

			String get_table_count = "SELECT COUNT(*) from fruits";
			ResultSet ss = stmt.executeQuery(get_table_count);
			int count = 0;
			while(ss.next()){
				count = ss.getInt(1);
			}
			
			if(request.getParameter("mango_qty") != ""){	//Checks if customer added mangoes
				 mango_qty = request.getParameter("mango_qty");
				 mango_d = request.getParameter("mango_d");
				 String f_query = "INSERT INTO fruits (customerId, FID, deliveryDate, quantity)" +
						"VALUES ("+ count + ", "+ "'Mango', "+ "'"+ mango_d+"'" +", " + mango_qty+")" ;
				 rs = stmt.executeUpdate(f_query);
			}
			if(request.getParameter("chikoo_qty") != ""){	//Checks if customer added chikoos
				 chikoo_qty = request.getParameter("chikoo_qty");
				 chikoo_d = request.getParameter("chikoo_d");
				 String f_query = "INSERT INTO fruits (customerId, FID, deliveryDate, quantity)" +
							"VALUES ("+ count + ", "+ "'Chikoo', "+ "'"+ chikoo_d+"'" +", " + chikoo_qty+")" ;
					 rs = stmt.executeUpdate(f_query);
			}
			if(request.getParameter("guava_qty") != ""){	//Checks if customer added guava
				 guava_qty = request.getParameter("guava_qty");
				 guava_d = request.getParameter("guava_d");
				 String f_query = "INSERT INTO fruits (customerId, FID, deliveryDate, quantity)" +
							"VALUES ("+ count + ", "+ "'Guava', "+ "'"+ guava_d+"'" +", " + guava_qty+")" ;
					 rs = stmt.executeUpdate(f_query);
			}
			
			String query = "INSERT INTO customerInfo (name, address, city, zip, customerId)" +
			"VALUES ("+"'"+ first_name +" "+ last_name+"'" +" ,"+"'"+ address+"'" + " ,"+"'"+ city + "'"+" ,"+ zip +" ,"+ count+")";
			rs = stmt.executeUpdate(query);
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			out.print(request.getParameter("chikoo_qty"));
			out.print("<center><h1>Your Order</h1></center>");
			out.print("<tr>");
			out.print(first_name + " " + last_name);
			out.print("</tr");
			out.println("<br />");
            String customer_req = "SELECT customerId FROM customerInfo WHERE name ="+ "'"+first_name +
            		" "+ last_name+"'"; 
            ResultSet cs = stmt.executeQuery(customer_req);
			int cus_Id = 0;
			out.print("<style>");
			out.print("table, th, td {" + "border: 1px solid black"+"}");
			out.print("</style>");
			out.print("<table>");
			out.print("<tr>");
			out.print("<th>" + "Fruit Type" + "</ th>");
			out.print("<th>" + "Delivery Date" + "</ th>");
			out.print("<th>" + "Quantity" + "</ th>");
			out.print("</tr>");
			while(cs.next()){
				count = cs.getInt(1);
				String order_req = "SELECT FID, deliveryDate, quantity FROM fruits WHERE customerId =" + count; 
	            Statement state = con.createStatement();
	            ResultSet info = state.executeQuery(order_req);
	            while(info.next()){
	            	out.print("<tr>");
	                out.print("<td>" + info.getString("FID") + "</td>" + " ");
	                out.print("<td>" + info.getString("deliveryDate") + "</td>"+ " ");
	                out.print("<td>" + info.getInt("quantity") + "</td>"+ " ");
	                out.print("</tr>");  
	            }
			}
			
			out.print("<p>To cancel some orders: <a href=" + "http://localhost:8080/test.jsp" + ">Delete</a></p>");
			/**
            while(rs.next())
            {
                out.print("<tr>");
                out.print("<td>" + rs.getString("firstname") + "</td>");
                out.print("</tr>"); 
                out.print("<td>" + rs.getString("lastname") + "</td>");
                out.print("<td>" + rs.getDouble("personId") + "</td>");
                out.print("</tr>");               
 			}
            */
		}catch(SQLException e){
			e.printStackTrace();
		}catch(NamingException e){
			e.printStackTrace();
		}
		
			//	new InitialContext();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
