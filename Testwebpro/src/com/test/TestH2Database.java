package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TestH2Database {
	public static void main(String[] args) throws Exception {

		Connection conn = null;
		Statement stmt = null;

		Class.forName("org.h2.Driver");
		conn = DriverManager.getConnection("jdbc:h2:~/lonsec2", "", "");
		stmt = conn.createStatement();

		stmt.execute("drop table if exists FUND");
		stmt.execute("drop table if exists BENCHMARK");
		stmt.execute("drop table if exists benchreturnseries");
		stmt.execute("drop table if exists fundreturnseries");

		try {

			String createFundTable = "CREATE TABLE FUND(FUNDCODE VARCHAR(200) PRIMARY KEY, FUNDNAME VARCHAR(200), BENCHMARKCODE VARCHAR(200) ) AS SELECT * FROM CSVREAD('C:\\gitrepo\\Testwebpro\\src\\data\\fund.csv')";

			stmt.execute(createFundTable);

			String createBenchMarkTable = "CREATE TABLE BENCHMARK(BENCHMARKCODE VARCHAR(200), BENCHMARKNAME VARCHAR(200)) AS SELECT * FROM CSVREAD('C:\\gitrepo\\Testwebpro\\src\\data\\benchmark.csv')";

			stmt.execute(createBenchMarkTable);

			// String createBenchReturnSeriesTable = "CREATE TABLE
			// benchreturnseries(code varchar(200), returndate DATE, returnper
			// DOUBLE)";
			// stmt.execute(createBenchReturnSeriesTable);
			// String insertBenchReturnSeriesTable = "insert into
			// benchreturnseries ( code, returndate, returnper ) SELECT
			// CONVERT(\"return(%)\",double) FROM
			// CSVREAD('C:\\gitrepo\\Testwebpro\\src\\data\\BenchReturnSeries.csv')";
			// stmt.execute(insertBenchReturnSeriesTable);

			// readFundReturnServies(stmt);

			String createBenchReturnSeriesTable = "CREATE TABLE benchreturnseries(code VARCHAR(200), returndate DATE, returnper FLOAT) AS SELECT * FROM CSVREAD('C:\\gitrepo\\Testwebpro\\src\\data\\BenchReturnSeries.csv',null,'charset=UTF-8')";
			stmt.execute(createBenchReturnSeriesTable);

			 

			//because of dateformat not default one and column name return(%) having problem in assesing query
			String createTempTab="CREATE TABLE tempfundreturntab(code VARCHAR(200), returndate VARCHAR(10), returnper DOUBLE) AS SELECT  * FROM CSVREAD('C:\\gitrepo\\Testwebpro\\src\\data\\FundReturnSeries.csv',null,null)";
			stmt.execute(createTempTab);

			String createFundReturnSeriesTable="CREATE TABLE fundreturnseries(code VARCHAR(200), returndate Date, returnper DOUBLE) AS SELECT   Code, FORMATDATETIME(PARSEDATETIME(returndate ,'dd/MM/yyyy') ,'yyyy-MM-dd') as returndate,returnper FROM tempfundreturntab";
			stmt.execute(createFundReturnSeriesTable);
			
			//
			//
			// String createFundReturnSeriesTable = "CREATE TABLE
			// fundreturnseries(code varchar(200), date DATE, return DOUBLE) AS
			// SELECT * FROM
			// CSVREAD('C:\\gitrepo\\Testwebpro\\src\\data\\FundReturnSeries.csv',
			// CONVERT(\"return(%)\",double))";
			//
			// stmt.execute(createFundReturnSeriesTable);
			//
			// readTable("benchmark", stmt);
			// readTable("fund", stmt);
			// readTable("benchreturnseries", stmt);
			// readTable("fundreturnseries", stmt);

			// readFundReturnServies(stmt);
			readTable("FUND", stmt);
			readTable("BENCHMARK", stmt);
			readTable("benchreturnseries", stmt);
			readTable("fundreturnseries", stmt);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
	}

	public static void readFundReturnServies(Statement stmt) {
		// String selectq = "SELECT * FROM
		// CSVREAD('C:\\gitrepo\\Testwebpro\\src\\data\\FundReturnSeries.csv')";
		String selectq = "SELECT * FROM CSVREAD('C:\\gitrepo\\Testwebpro\\src\\data\\BenchReturnSeries.csv')";
		ResultSet resultset;
		try {
			resultset = stmt.executeQuery(selectq);
			ResultSetMetaData meta = resultset.getMetaData();
			Integer columncount = meta.getColumnCount();

			int count = 1; // start counting from 1 always

			ArrayList<String> columnNames = new ArrayList<String>();

			while (count <= columncount) {
				String columnName = meta.getColumnName(count);
				String columnType = meta.getColumnTypeName(count);
				System.out.println("CoulmnName:" + columnName + " Coulmntype:" + columnType);
				columnNames.add(columnName);
				count++;
			}

			int rowCount = 0;

			while (resultset.next()) {
				// for (String columnName : columnNames) {
				// System.out.print(columnName + resultset.getString("" +
				// columnName + "") + " ");
				// }
				rowCount++;
				System.out.println("Row:" + rowCount);
				System.out.print("code" + resultset.getString("Code") + " ");
				System.out.print("Date" + resultset.getString("returnDate") + " ");
				System.out.print("Return" + resultset.getString("Return(%)") + " ");
				System.out.println();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void readTable(String tableName, Statement stmt) {
		String readTableSQL = "select * from " + tableName;
		ResultSet resultset;
		try {
			resultset = stmt.executeQuery(readTableSQL);
			ResultSetMetaData meta = resultset.getMetaData(); // for a valid
																// resultset
																// object after
																// executing
																// query

			Integer columncount = meta.getColumnCount();

			int count = 1; // start counting from 1 always

			ArrayList<String> columnNames = new ArrayList<String>();

			while (count <= columncount) {
				String columnName = meta.getColumnName(count);
				String columnType = meta.getColumnTypeName(count);
				System.out.println("CoulmnName:" + columnName + " Coulmntype:" + columnType);
				columnNames.add(columnName);
				count++;
			}

			while (resultset.next()) {
				for (String columnName : columnNames) {
					System.out.print(columnName + resultset.getString("" + columnName + "") + " ");
				}
				System.out.println();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
