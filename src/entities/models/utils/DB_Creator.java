package entities.models.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class DB_Creator {

	private static Connection connection;

	/*public DB_Creator() {

	}*/

	public void begin() {
		boolean exists = checkExistingDb();
		if (!exists) {
			createDB();
		}
	}


	private boolean checkExistingDb() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (Exception ex_01) {
			System.out.println("Error in 'checkExistingDB()', section 'Class.forName(...': " + ex_01.toString());
		}
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:KanbanBo.db");
		} catch (Exception ex_02) {
			System.out.println("Error in 'checkExistingDB()', section 'connection = DriverManager.getConnection(...': " + ex_02.toString());
		}
		// TODO Logic here to determine if db actually exists and return appropriate boolean.
		return true;
	}

	private void createDB() {
		createTable_user();
	}

	private void createTable_user() {
		Statement statement;
		PreparedStatement preparedStatement;
		try {
			statement = connection.createStatement();
		} catch (Exception ex_01) {
			System.out.println("Error in 'createTable_user()', while creating statement. Details: " + ex_01.toString());
		}

		try {
			preparedStatement = connection.prepareStatement("CREATE TABLE user("
				+ "user_uuid varchar(40),"
				+ "first_name varchar(20),"
				+ "last_name varchar(30),"
				+ "display_name varchar(40),"
				+ "primary key(user_uuid));");
			preparedStatement.execute();
		} catch (Exception ex_02) {
			System.out.println("Error in 'createTable_user()', while creating table. Details: " + ex_02.toString());
		}

	}
}
