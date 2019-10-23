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
import Db.DbIntegrityException;

public class Program {

	public static void main(String[] args) {
		
		//showDepartment();
		//insertSeller();
		//insertSeller2();
		//insertDepartment();
		//UpdateSellerSalary();
		//deleteDepartment();
		transationsExecute();
	}
	
	public static void modelDB() {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DB.getConnection();
			String sql = "";
			
			st = conn.prepareStatement(sql);
			
			st.setInt(1, 2);
			
			int rowsAffected = st.executeUpdate();
			
			System.out.println("Done! Rows affected:" + rowsAffected);
		}
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		}
		finally {
			DB.closeStatemet(st);
			DB.closeConnection();
		}
	}
	
	public static void transationsExecute() {
		Connection conn = null;
		Statement st = null;
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			
			st = conn.createStatement();
			
			String sqlTrans1 = "UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1 ";
			int rows1 = st.executeUpdate(sqlTrans1);
			/*
			int x =1;
			if (x<2) {
				throw new SQLException("Fake Error.!");
			}
			*/
			String sqlTrans2 = "UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 2 ";
			int rows2 = st.executeUpdate(sqlTrans2);
			
			conn.commit();
			
			System.out.println("Rows1 affected:" + rows1);
			System.out.println("Rows2 affected:" + rows2);
			
		}
		catch (SQLException e) {
			try {
				conn.rollback();
				throw new DbException("Transaction rolled back! Caused by: " + e.getMessage());
			} catch (SQLException e1) {
				throw new DbException("Error trying to rollback! Caused by: " + e.getMessage());
			}
		}
		finally {
			DB.closeStatemet(st);
			DB.closeConnection();
		}
	}
	public static void deleteDepartment() {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DB.getConnection();
			String sql = "DELETE FROM department "
					+ "WHERE "
					+ "(id = ?) ";
			
			st = conn.prepareStatement(sql);
			
			st.setInt(1, 2);
			
			int rowsAffected = st.executeUpdate();
			
			System.out.println("Done! Rows affected:" + rowsAffected);
		}
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		}
		finally {
			DB.closeStatemet(st);
			DB.closeConnection();
		}
	}
	public static void UpdateSellerSalary() {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DB.getConnection();
			String sql = "UPDATE seller "
					+ "Set BaseSalary = BaseSalary + ? "
					+ "WHERE "
					+ "(DepartmentId = ?) ";
			
			st = conn.prepareStatement(sql);
			
			st.setDouble(1, 200.0);
			st.setInt(2, 2);
			
			int rowsAffected = st.executeUpdate();
			
			System.out.println("Done! Rows affected:" + rowsAffected);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeStatemet(st);
			DB.closeConnection();
		}
	}
	public static void insertDepartment() {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		try {
			conn = DB.getConnection();
			
			String sql = "INSERT INTO department "
					+ " (Name)"
					+ " VALUES "
					+ "('D1'), ('D2')";
								
			st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			
			int rowAffeccted = st.executeUpdate();
			
			if (rowAffeccted > 0 ) {
				rs =  st.getGeneratedKeys();
				while (rs.next()) {
					int id = rs.getInt(1);
					System.out.println("Done! Id = " + id);
				}
			}
			else {
				System.out.println("No rown affected.");
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatemet(st);
			DB.closeConnection();
		}
	}
	
	public static void insertSeller2() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement st = null;
				
		try {
			conn = DB.getConnection();
			
			String sql = "INSERT INTO seller "
					+ " (Name, Email, BirthDate, BaseSalary, DepartmentId)"
					+ " VALUES "
					+ "( ?,?,?,?,? )";
								
			st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, "Carl Purple");
			st.setString(2, "carl@gmail.com");
			st.setDate(3, new java.sql.Date(sdf.parse("20/04/1985").getTime()));
			st.setDouble(4, 3000.0);
			st.setInt(5,4);
			
			int rowAffeccted = st.executeUpdate();
			
			if (rowAffeccted > 0 ) {
				rs =  st.getGeneratedKeys();
				while (rs.next()) {
					int id = rs.getInt(1);
					System.out.println("Done! Id = " + id);
				}
			}
			else {
				System.out.println("No rown affected.");
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch ( ParseException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatemet(st);
			DB.closeConnection();
		}
	}
	
	
	public static void insertSeller() {
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
