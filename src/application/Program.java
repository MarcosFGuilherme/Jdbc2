package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import Db.DB;
import Db.DbException;

public class Program {

	public static void main(String[] args) {
		
		//showDepartment();
		showSeller();
		
	}
	
	public static void showSeller() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Connection conn = null;
		PreparedStatement st = null;
				
		try {
			conn = DB.getConnection();
			
			String sql = "INSERT INTO seller "
					+ " (Name, Email, BirthDate, BaseSalary, DepartmentId)"
					+ " VALUES "
					+ "( ?,?,?,?,? )";
								
			st = conn.prepareStatement(sql);
			
			st.setString(1, "Carl Purple");
			st.setString(2, "carl@gmail.com");
			st.setDate(3, new java.sql.Date(sdf.parse("20/04/1985").getTime()));
			st.setDouble(4, 3000.0);
			st.setInt(5,4);
			
			int rowAffeccted = st.executeUpdate();
			
			System.out.println("Done! Rows affected: " + rowAffeccted);
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch ( ParseException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeStatemet(st);
			DB.closeConnection();
		}
	}
	
	public static void showDepartment() {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("select * from department");
			
			while (rs.next()) {
				System.out.println(rs.getInt("Id") + " - " + rs.getString("Name"));
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatemet(st);
			DB.closeConnection();
		}
	}
}
