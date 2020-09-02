

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.connection.DatabaseConnection;

import java.util.Random;

/**
 * Servlet implementation class AddVehicle
 */
@WebServlet("/AddLocation")
public class AddLocation extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		String l = request.getParameter("location");
		String n = request.getParameter("slots");
		String location=l;
		int slots=Integer.parseInt(n);
		
		int addVehicle = 0;
		String status = "";
		int count = 0;
		try {
			Connection connection = DatabaseConnection.getConnection();
			Statement statement = connection.createStatement();
			ResultSet total_parking_seat_result = statement.executeQuery("select * from tblParkingSeatCapacity");
			
			
				addVehicle = statement.executeUpdate(
						"insert into location(loc,slots)values('"
								
								+ location  + "','" + slots + "')");
				statement.executeUpdate("update tblParkingSeatCapacity set parking_seat=(select sum(slots) from location)");
			if (addVehicle > 0) {
				response.sendRedirect("add-location.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
