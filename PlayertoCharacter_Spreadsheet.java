package tekkenTraining.Koyobi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class PlayertoCharacter_Spreadsheet {
	
	private static Connection conn;  //making this variable static makes it so that I do not have to constantly create new Connection variables called "conn" repeatedly
	private static PreparedStatement ps;
	
	
	

	public static void main(String[] args) throws SQLException {
		
		CreateTable();
		addPlayer(ps);
		
		

	}
	
	protected static Connection getConnected(String database) {
		
		conn = null;
			
			try {
				
				Class.forName("org.sqlite.JDBC");
				conn = DriverManager.getConnection("jdbc:sqlite:" + database + ".db");
				
				
				
			}catch (Exception e){
				
				System.out.println("Connection Error");
				
				
			}
			
			
			return conn;
		}
		
	
	
	protected static void CreateTable () throws SQLException {
		
		conn = getConnected("Tekken SpreadSheet"); //Reinitialize Connection variable with getConnection method name of database I chose is "Tekken SpreadSheet"
	
		Statement state = conn.createStatement(); //initialize Statement variable with Connection variable through createStatement constructor
		

		 
		ResultSet rs = state.executeQuery("SELECT name FROM sqlite_master WHERE type = 'table' AND name = ' NC_TekkenSpreadSheet' ");
		
		if (rs.next()) {
		
		try { 
			
			String table = "CREATE TABLE NC_TekkenSpreadSheet"
			+ "(ID INT PRIMARY KEY, PLAYER VARCHAR(60), CHARACTER VARCHAR(255));"; //Creates Table using SQL code
			
			
			
				
				state.execute(table);
				state.close();
				conn.close();
						
			
		}catch (Exception e) {
			
			System.out.println("Table Error");
		}
		
			}
		
		System.out.println("Table already created");
		
		InsertSampleValues();
		
	}
	
	protected static void InsertSampleValues() {
		
		try {
			
			conn = getConnected("Tekken SpreadSheet");
			 ps = conn.prepareStatement("INSERT INTO NC_TekkenSpreadSheet"
					+ " VALUES (?,?,?)");
			
			ps.setString(2, "FinalBoss");
			ps.setString(3, "Steve, Hwoarang");
			ps.executeQuery();
			ps.close();
			
			
			
		}catch(Exception e) {
			
			System.out.println("Insert Sample Error");
			System.out.println();
		}
		
		
	}
	
	protected static void addPlayer(PreparedStatement ps) {
		
		
		conn = getConnected("Tekken SpreadSheet");
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter Your Name: ");
		String Player = sc.nextLine();
		
		
		System.out.println("Which Character do you play: ");
		String Character = sc.nextLine();
		
		
		
		try {
			
		ps.setString(2, Player);
		ps.setString(3, Character);
		ps.executeQuery();
		
		sc.close();
		ps.close();
		conn.close();
		
		
		printSpreadSheet(Player, Character);
		
		}catch(Exception e) {
			
			System.out.println("addPlayer Error");
		}
		
		
	}
	
	protected static void printSpreadSheet(String Player, String Character){
		
		System.out.println("Player name: " + Player);
		
		System.out.println(Player + " plays " + Character);
		
	}
}
