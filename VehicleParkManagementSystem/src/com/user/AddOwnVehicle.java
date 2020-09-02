package com.user;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.connection.DatabaseConnection;
import com.mysql.cj.Session;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Servlet implementation class AddOwnVehicle
 */
@WebServlet("/AddOwnVehicle")
public class AddOwnVehicle extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Random rand = new Random();
		int ParkingNumber = rand.nextInt(9000000) + 1000000;
		HttpSession hs = request.getSession();
		System.out.println("ParkingNumber        " + ParkingNumber);
		String catename = request.getParameter("catename");
		String vehcomp = request.getParameter("vehcomp");
		String vehreno = request.getParameter("vehreno");
		String location=request.getParameter("loc");
		String ownername = request.getParameter("ownername");
		String ownercontno = request.getParameter("ownercontno");
		String intime=request.getParameter("in");
		String outtime=request.getParameter("out");
		int parkingcharge=0;
		long differenceHours = 0;
		String datest=intime;
		String dateend=outtime;
		SimpleDateFormat format=new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
		Date d1=null;
		Date d2=null;
		try
		{
			d1 = format.parse(datest);
			d2 = format.parse(dateend);

			//in milliseconds
			long diff = d2.getTime() - d1.getTime();

			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			long timeDifference = d2.getTime() - d1.getTime();
			differenceHours = timeDifference / (60 * 60 * 1000) % 24;
			
			differenceHours = differenceHours + (diffDays * 24);

			if ("Six Wheeler Vehicles".equals(catename) && differenceHours < 8) {
				parkingcharge = 40;

			} else if ("Six Wheeler Vehicles".equals(catename) && differenceHours > 8 && differenceHours < 16) {
				parkingcharge = 60;

			} else if ("Six Wheeler Vehicles".equals(catename) && differenceHours > 16 && differenceHours < 24) {
				parkingcharge = 100;

			} else if ("Six Wheeler Vehicles".equals(catename) && differenceHours > 24) {
				parkingcharge = 150;

			} else if ("Four Wheeler Vehicle".equals(catename) && differenceHours < 8) {
				parkingcharge = 30;

			} else if ("Four Wheeler Vehicle".equals(catename) && differenceHours > 8 && differenceHours < 16) {
				parkingcharge = 50;

			} else if ("Four Wheeler Vehicle".equals(catename) && differenceHours > 16 && differenceHours < 24) {
				parkingcharge = 80;

			} else if ("Four Wheeler Vehicle".equals(catename) && differenceHours > 24) {
				parkingcharge = 120;

			} else if ("Two Wheeler Vehicle".equals(catename) && differenceHours < 8) {
				parkingcharge = 15;

			} else if ("Two Wheeler Vehicle".equals(catename) && differenceHours > 8 && differenceHours < 16) {
				parkingcharge = 20;

			} else if ("Two Wheeler Vehicle".equals(catename) && differenceHours > 16 && differenceHours < 24) {
				parkingcharge = 25;

			} else if ("Two Wheeler Vehicle".equals(catename) && differenceHours > 24) {
				parkingcharge = 45;

			} else if ("Bicycles".equals(catename) && differenceHours < 8) {
				parkingcharge = 2;

			} else if ("Bicycles".equals(catename) && differenceHours > 8 && differenceHours < 16) {
				parkingcharge = 5;

			} else if ("Bicycles".equals(catename) && differenceHours > 16 && differenceHours < 24) {
				parkingcharge = 10;
			} else if ("Bicycles".equals(catename) && differenceHours > 24) {
				parkingcharge = 12;
			} else {
				System.out.println("Bye");
			}
			int pc;
			pc=parkingcharge;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		int parking_seat=0;
		int addVehicle = 0;
		String status = "";
		int count = 0,p_count=0;
		try {
			Connection connection = DatabaseConnection.getConnection();
			Statement statement = connection.createStatement();
			ResultSet total_parking_seat_result= statement.executeQuery("select * from tblParkingSeatCapacity");
			if(total_parking_seat_result.next()){
				parking_seat = total_parking_seat_result.getInt(1);
			}
			ResultSet resultSet = statement.executeQuery("select count(*) from tblvehicle where status=''");
			ResultSet rs = statement.executeQuery("select slots from location where loc='"+location+"'");
			if (rs.next()) {
				count = rs.getInt(1);
			}
			ResultSet rss = statement.executeQuery("select count(*) from tblvehicle where location='"+location+"' and status=''");
			
			if (rss.next()) {
				p_count = rss.getInt(1);
			}
			String str=String.valueOf(java.time.LocalDate.now());
			String st[]=str.split("-");
			String in[]=datest.split(" ");
			String inarray[]=in[0].split("-");
			String out[]=dateend.split(" ");
			String outarray[]=out[0].split("-");
			String iarray[]=in[1].split(":");
			String oarray[]=in[1].split(":");
			
			for(int i=0;i<inarray.length;i++)
			{
				//System.out.print(inarray[i]+  " "+st[i]);
			}
			/*if(!)
			{
				response.sendRedirect("error1.jsp");
			}*/
			 if(st[0].equals(inarray[0])&&st[1].equals(inarray[1])&&st[2].equals(inarray[2])&&inarray[2].equals(outarray[2]))
			{
			
			if (p_count <= count) {
				addVehicle = statement.executeUpdate(
						"insert into tblvehicle(ParkingNumber,VehicleCategory,VehicleCompanyname,RegistrationNumber,OwnerName,OwnerContactNumber,status,location,InTime,OutTime,parkingcharge)values('"
								+ ParkingNumber + "','" + catename + "','" + vehcomp + "','" + vehreno + "','"
								+ ownername + "','" + ownercontno + "','" + status + "','"+location+"','"+intime+"','"+outtime+"',"+parkingcharge+")");
			} else {
				String message="Parking slot is full, Wait for sometime";
				hs.setAttribute("message", message);
				response.sendRedirect("add-user-vehicle.jsp");
			}
			}
			else
				response.sendRedirect("error.jsp");
			if (addVehicle > 0) {
				response.sendRedirect("add-user-vehicle.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
