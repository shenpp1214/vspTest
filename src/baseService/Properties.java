package baseService;

import java.sql.*;

public class Properties {

	public static void main(String args[]) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Success loading Mysql Driver!");
		} catch (Exception e) {
			System.out.print("Error loading Mysql Driver!");
			e.printStackTrace();
		}
		try {
			Connection connect = DriverManager.getConnection("jdbc:mysql://58.215.50.62:3307/iov", "iov_user",
					"2wsx3edc");

			System.out.println("Success connect Mysql server!");
			Statement stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery("select device_id from iov.device_status where DEVICE_ID='81044110'");

			while (rs.next()) {
				System.out.println(rs.getString("device_id"));
			}
		} catch (Exception e) {
			System.out.print("get data error!");
			e.printStackTrace();
		}

	}

}