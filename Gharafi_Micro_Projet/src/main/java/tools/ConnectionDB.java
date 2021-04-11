package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDB {
	
	private Connection con;
	private Statement st;
	
	public ConnectionDB() {
		con = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mpdb", "root", "root");
			st=con.createStatement();
			System.out.println("connected");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
		try {
			this.setSt(con.createStatement());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Statement getSt() {
		return st;
	}

	public void setSt(Statement st) {
		this.st = st;
	}
	
	public void close() {
		try {
		if(con!=null) con.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
