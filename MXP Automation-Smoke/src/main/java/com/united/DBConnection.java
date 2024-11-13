//package com.united;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class DBConnection {
//	public static void main(String[] args) {
//		try {
//		try {
//			Class.forName("org.postgresql.Driver");
//		} catch (ClassNotFoundException e) {
//			System.out.println("PostgreSQL JDBC Driver not found.");
//			e.printStackTrace();
//			return;
//		}
//
//		String url = "jdbc:postgresql://rdsdbaepic-auroracluster-us-east-2-qa.cluster-cbgf3f13hxtb.us-east-2.rds.amazonaws.com:5432/epicdb?sslmode=require";
//		String username = "epicapp";
//		String password = "ppa4#epicqa";
//
//		Connection connection = null;
//
//		try {
//			connection = DriverManager.getConnection(url, username, password);
//		} catch (SQLException e) {
//			System.out.println("Connection Failed.");
//			e.printStackTrace();
//			return;
//		}
//
//		if (connection != null) {
//			System.out.println("Successful connection to the database.");
//		} else {
//			System.out.println("Failed to make connection to the database.");
//		}
////		
////        resultSet.close();
////        statement.close();
//        connection.close();
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//		finally{
//			
//		}
//
//	}
//}
