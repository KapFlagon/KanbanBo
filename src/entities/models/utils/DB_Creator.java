package entities.models.utils;

import org.sqlite.SQLiteConfig;

import java.sql.*;

public class DB_Creator {

	private static Connection connection;
	private static final String DRIVER = "org.sqlite.JDBC";
	private static final String DB_URL = "jdbc:sqlite:KanbanBo.db";

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
			Class.forName(DRIVER);
		} catch (Exception ex_01) {
			System.out.println("Error in 'checkExistingDB()', section 'Class.forName(...': " + ex_01.toString());
		}
		try {
			// http://code-know-how.blogspot.com/2011/10/how-to-enable-foreign-keys-in-sqlite3.html
			SQLiteConfig config = new SQLiteConfig();
			config.enforceForeignKeys(true);
			connection = DriverManager.getConnection(DB_URL, config.toProperties());
		} catch (Exception ex_02) {
			System.out.println("Error in 'checkExistingDB()', section 'connection = DriverManager.getConnection(...': " + ex_02.toString());
		}
		// TODO Logic here to determine if db actually exists and return appropriate boolean.
		return true;
	}

	private void createDB() {
		//createTable__user();
	}

	private void createTable__user() throws SQLException {
		String sqlStatement = "CREATE TABLE user("
				+ "user_uuid TEXT NOT NULL UNIQUE PRIMARY KEY,"
				+ "first_name TEXT NOT NULL"
				+ "last_name TEXT NOT NULL,"
				+ "display_name TEXT NOT NULL);";
		executePreparedSQL(sqlStatement);
	}

	private void createTable__user_password() throws SQLException {
		String sqlStatement = "CREATE TABLE user_password("
				+ "user_uuid TEXT,"
				+ "hashed_password TEXT NOT NULL,"
				+ "PRIMARY KEY (user_uuid),"
				+ "FOREIGN KEY (user_uuid) REFERENCES user (user_uuid));";
		executePreparedSQL(sqlStatement);
	}

	private void createTable__watch_list() throws SQLException {
		String sqlStatement = "CREATE TABLE watch_list("
				+ "user_uuid TEXT NOT NULL,"
				+ "watched_item_uuid TEXT NOT NULL,"
				+ "PRIMARY KEY (user_uuid),"
				+ "FOREIGN KEY (user_uuid) REFERENCES user (user_uuid));";
		executePreparedSQL(sqlStatement);
	}

	private void createTable__activity() throws SQLException {
		String sqlStatement = "CREATE TABLE activity("
				+ "user_uuid TEXT,"
				+ "parent_item_uuid TEXT NOT NULL,"
				+ "date TEXT NOT NULL,"
				+ "time TEXT NOT NULL,"
				+ "log_data TEXT NOT NULL"
				+ "PRIMARY KEY (user_uuid),"
				+ "FOREIGN KEY (user_uuid) REFERENCES user (user_uuid));";
		executePreparedSQL(sqlStatement);
	}

	private void createTable__user_contact() throws SQLException{
		String sqlStatement = "CREATE TABLE user_contact("
				+ "user_uuid TEXT,"
				+ "email TEXT,"
				+ "PRIMARY KEY (user_uuid),"
				+ "FOREIGN KEY (user_uuid) REFERENCES user (user_uuid));";
		executePreparedSQL(sqlStatement);
	}

	private void createTable__salt() throws SQLException{
		String sqlStatement = "CREATE TABLE salt("
				+ "user_uuid TEXT,"
				+ "salt_key TEXT,"
				+ "PRIMARY KEY (user_uuid),"
				+ "FOREIGN KEY (user_uuid) REFERENCES user (user_uuid));";
		executePreparedSQL(sqlStatement);
	}

	private void createTable__user_setting() throws SQLException{
		String sqlStatement = "CREATE TABLE user_setting("
				+ "user_uuid TEXT,"
				+ "colourblind BOOLEAN,"
				+ "notifications BOOLEAN,"
				+ "PRIMARY KEY (user_uuid),"
				+ "FOREIGN KEY (user_uuid) REFERENCES user (user_uuid));";
		executePreparedSQL(sqlStatement);
	}

	private void createTable__team_member() throws SQLException{
		String sqlStatement = "CREATE TABLE team_member("
				+ "user_uuid TEXT,"
				+ "team_uuid TEXT,"
				+ "FOREIGN KEY (user_uuid) REFERENCES user (user_uuid),"
				+ "FOREIGN KEY (team_uuid) REFERENCES team (team_uuid));";
		executePreparedSQL(sqlStatement);
	}

	private void createTable__team() throws SQLException{
		String sqlStatement = "CREATE TABLE user_setting("
				+ "team_uuid TEXT,"
				+ "team_title TEXT,"
				+ "PRIMARY KEY (team_uuid));";
		executePreparedSQL(sqlStatement);
	}

	private void createTable__project_folder() throws SQLException{
		String sqlStatement = "CREATE TABLE project_folder("
				+ "project_folder_uuid TEXT,"
				+ "parent_item_uuid TEXT,"
				+ "folder_path TEXT,"
				+ "PRIMARY KEY (project_folder_uuid),"
				// Need to examine structure further, to clarify the foreign key relationship
				+ "FOREIGN KEY (parent_item_uuid) REFERENCES (...));";
		//executePreparedSQL(sqlStatement);
	}

	private void createTable__category() throws SQLException{

	}

	private void createTable__board_facts() throws SQLException{

	}

	private void createTable__team_board_access() throws SQLException{

	}

	private void createTable__card_facts() throws SQLException{

	}

	private void createTable__card_due_date() throws SQLException{

	}

	private void createTable__card() throws SQLException{

	}

	private void createTable__card_members() throws SQLException{

	}

	private void createTable__card_cover() throws SQLException{

	}

	private void createTable__lane() throws SQLException{

	}

	private void createTable__board() throws SQLException{

	}

	private void createTable__label() throws SQLException{

	}

	private void createTable__card_labels() throws SQLException{

	}

	private void createTable__attachment() throws SQLException{

	}

	private void createTable__checklist_item() throws SQLException{

	}

	private void createTable__card_checklists() throws SQLException{

	}

	private void createTable__checklist() throws SQLException{

	}


	private void executePreparedSQL(String sqlStatement) throws SQLException {
		Statement statement = connection.createStatement();
		PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
		preparedStatement.execute();
	}
}
