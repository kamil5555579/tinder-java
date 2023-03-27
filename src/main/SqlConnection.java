package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class SqlConnection {

	public com.mysql.jdbc.Connection connect()
	{
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");  
			conn = DriverManager.getConnection(
					"jdbc:mysql://s176.cyber-folks.pl/gacteavduo_projektjava", "gacteavduo_gacteavduo",
					"ProjektJava2023!");

		} catch (SQLException e) {
			e.printStackTrace();//problem z polczeniem do bazy
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		return (com.mysql.jdbc.Connection) conn;
	}
}
