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
		// Consider using check for existing "user" table...
		return true;
	}

	private void createDB() {
		//createTable__user();
	}

	// TODO Need to add logic to the SQL statements to mark columns as NOT NULL etc.
	// TODO final pass to make sure that all 'No FK' columns no longer have foreign keys
	private void createTable__user() throws SQLException {
		String sqlStatement = "CREATE TABLE user("
				+ "user_uuid TEXT NOT NULL UNIQUE PRIMARY KEY,"
				+ "first_name TEXT NOT NULL"
				+ "last_name TEXT NOT NULL);";
		executePreparedSQL(sqlStatement);
	}

	/* // May not use passwords to protect the file
	private void createTable__user_password() throws SQLException {
		String sqlStatement = "CREATE TABLE user_password("
				+ "user_uuid TEXT,"
				+ "hashed_password TEXT NOT NULL,"
				+ "PRIMARY KEY (user_uuid),"
				+ "FOREIGN KEY (user_uuid) REFERENCES user (user_uuid));";
		executePreparedSQL(sqlStatement);
	}
	 */

	private void createTable__db_version() throws SQLException {
		// parent_item_uuid can be a category, board, lane, card, image, etc.
		String sqlStatement = "CREATE TABLE createTable__db_version("
				+ "version_no TEXT NOT NULL,"
				+ "PRIMARY KEY (version_no));";
		executePreparedSQL(sqlStatement);
	}

	private void createTable__activity() throws SQLException {
		// parent_item_uuid can be a category, board, lane, card, image, etc.
		String sqlStatement = "CREATE TABLE activity("
				+ "activity_uuid TEXT NOT NULL,"
				+ "parent_item_uuid TEXT NOT NULL,"
				+ "date TEXT NOT NULL,"
				+ "time TEXT NOT NULL,"
				+ "log_data TEXT NOT NULL"
				+ "PRIMARY KEY (activity_uuid));";
		executePreparedSQL(sqlStatement);
	}

	/* // May not use passwords to protect the file
	private void createTable__salt_and_pepper() throws SQLException{
		String sqlStatement = "CREATE TABLE salt_and_pepper("
				+ "user_uuid TEXT,"
				+ "salt_key TEXT,"
				+ "pepper_key TEXT,"
				+ "PRIMARY KEY (user_uuid),"
				+ "FOREIGN KEY (user_uuid) REFERENCES user (user_uuid));";
		executePreparedSQL(sqlStatement);
	}
	 */

	private void createTable__user_setting() throws SQLException{
		String sqlStatement = "CREATE TABLE user_setting("
				+ "user_uuid TEXT,"
				+ "colourblind BOOLEAN,"
				+ "font TEXT,"
				+ "font_size INTEGER,"
				+ "PRIMARY KEY (user_uuid),"
				+ "FOREIGN KEY (user_uuid) REFERENCES user (user_uuid));";
		executePreparedSQL(sqlStatement);
	}

	private void createTable__project_folder() throws SQLException{
		String sqlStatement = "CREATE TABLE project_folder("
				+ "project_folder_uuid TEXT,"
				+ "parent_item_uuid TEXT,"
				+ "folder_title TEXT,"
				+ "folder_description TEXT,"
				+ "folder_path TEXT,"
				+ "PRIMARY KEY (project_folder_uuid));";
				// No enforced foreign key relationship, only a soft one. Gives more flexibility for linking different sources.
				// E.G. Board can have project folder, Lane, Card, etc.
		executePreparedSQL(sqlStatement);
	}

	private void createTable__category() throws SQLException{
		String sqlStatement = "CREATE TABLE category("
				+ "category_uuid TEXT,"
				+ "user_uuid TEXT,"
				+ "category_title TEXT"
				+ "PRIMARY KEY (category_uuid),"
				+ "FOREIGN KEY (user_uuid) REFERENCES user (user_uuid));";
		executePreparedSQL(sqlStatement);
	}

	private void createTable__board_facts() throws SQLException {
		// No enforced foreign key relationship, only a soft one. Gives more flexibility for linking different sources.
		String sqlStatement = "CREATE TABLE board_facts("
				+ "board_facts_uuid TEXT,"
				+ "board_uuid TEXT,"    // No FK here, soft link
				+ "board_description TEXT),"
				+ "PRIMARY KEY (board_facts_uuid));";
		executePreparedSQL(sqlStatement);
	}

	private void createTable__card_facts() throws SQLException{
		String sqlStatement = "CREATE TABLE card_facts("
				+ "card_uuid TEXT,"  // No Foreign Key
				+ "card_description TEXT,"
				+ "PRIMARY KEY (card_uuid));";
		executePreparedSQL(sqlStatement);
	}

	private void createTable__card_due_date() throws SQLException{
		String sqlStatement = "CREATE TABLE card_due_date("
				+ "card_uuid TEXT,"     // No Foreign Key, soft link
				+ "card_due_date TEXT,"
				+ "card_due_time TEXT,"
				+ "completed BOOLEAN,"
				+ "PRIMARY KEY (card_uuid));";
		executePreparedSQL(sqlStatement);
	}

	private void createTable__active_card() throws SQLException{
		// An active card must always be linked to a lane
		String sqlStatement = "CREATE TABLE active_card("
				+ "active_card_uuid TEXT,"
				+ "lane_uuid TEXT,"     // No foreign key, soft link
				+ "active_card_position INTEGER,"
				+ "active_card_title TEXT,"
				+ "Primary KEY (active_card_uuid));";
		executePreparedSQL(sqlStatement);
	}

	private void createTable__template_card() throws SQLException{
		// An active card must always be linked to a lane
		String sqlStatement = "CREATE TABLE template_card("
				+ "template_card_uuid TEXT,"
				+ "user_uuid TEXT,"
				+ "template_card_title TEXT,"
				+ "Primary KEY (template_card_uuid),"
				+ "FOREIGN KEY (user_uuid) REFERENCES user (user_uuid));";
		executePreparedSQL(sqlStatement);
	}

	private void createTable__archived_card() throws SQLException{
		// An archived card should always be linked to a lane
		String sqlStatement = "CREATE TABLE archived_card("
				+ "archived_card_uuid TEXT,"
				+ "lane_uuid TEXT,"  // No Foreign Key, soft link
				+ "archived_card_title TEXT,"
				+ "Primary KEY (archived_card_uuid));";
		executePreparedSQL(sqlStatement);
	}

	private void createTable__card_cover() throws SQLException{
		// TODO Consider changing this so that covers can be used in both boards
		String sqlStatement = "CREATE TABLE card_cover("
				+ "card_uuid TEXT,"     // No Foreign Key, soft link
				+ "cover_title TEXT,"
				+ "cover_data BLOB,"
				+ "PRIMARY KEY (card_uuid));";
		executePreparedSQL(sqlStatement);
	}

	private void createTable__active_lane() throws SQLException{
		String sqlStatement = "CREATE TABLE active_lane("
				+ "active_lane_uuid TEXT,"
				+ "board_uuid TEXT,"    // No foreign key, flexible board usage
				+ "active_lane_title TEXT,"
				+ "active_lane_position INTEGER,"
				+ "PRIMARY KEY (active_lane_uuid));";
		executePreparedSQL(sqlStatement);
	}

	private void createTable__archived_lane() throws SQLException{
		String sqlStatement = "CREATE TABLE archived_lane("
				+ "archived_lane_uuid TEXT,"
				+ "board_uuid TEXT,"    // No foreign key, flexible board usage
				+ "archived_lane_title TEXT,"
				+ "archived_lane_position INTEGER,"
				+ "PRIMARY KEY (archived_lane_uuid));";
		executePreparedSQL(sqlStatement);
	}

	private void createTable__active_board() throws SQLException{
		String sqlStatement = "CREATE TABLE active_board("
				+ "active_board_uuid TEXT,"
				+ "category_uuid TEXT,"
				+ "active_board_title TEXT,"
				+ "active_board_position INTEGER,"
				+ "active_board_favourite BOOLEAN,"
				+ "PRIMARY KEY (active_board_uuid),"
				+ "FOREIGN KEY (category_uuid) REFERENCES category (category_uuid));";
		executePreparedSQL(sqlStatement);
	}

	private void createTable__archived_board() throws SQLException{
		String sqlStatement = "CREATE TABLE archived_board("
				+ "archived_board_uuid TEXT,"
				+ "user_uuid TEXT,"
				+ "archived_board_title TEXT,"
				+ "PRIMARY KEY (archived_board_uuid),"
				+ "FOREIGN KEY (user_uuid) REFERENCES user (user_uuid));";
		executePreparedSQL(sqlStatement);
	}

	private void createTable__template_board() throws SQLException{
		String sqlStatement = "CREATE TABLE template_board("
				+ "template_board_uuid TEXT,"
				+ "user_uuid TEXT,"
				+ "template_board_title TEXT,"
				+ "PRIMARY KEY (template_board_uuid),"
				+ "FOREIGN KEY (user_uuid) REFERENCES user (user_uuid));";
		executePreparedSQL(sqlStatement);
	}

	private void createTable__label() throws SQLException{
		String sqlStatement = "CREATE TABLE label("
				+ "label_uuid TEXT,"
				+ "title TEXT,"
				+ "base_colour TEXT,"
				+ "colourblind_texture TEXT,"
				+ "PRIMARY KEY (label_uuid));";
		executePreparedSQL(sqlStatement);
	}

	private void createTable__card_labels() throws SQLException{
		String sqlStatement = "CREATE TABLE card_labels("
				+ "label_uuid TEXT,"
				+ "card_uuid TEXT,"     // No Foreign Key, soft link
				+ "position INTEGER,"
				+ "FOREIGN KEY (label_uuid) REFERENCES label (label_uuid));";
		executePreparedSQL(sqlStatement);
	}

	private void createTable__attachment() throws SQLException{
		String sqlStatement = "CREATE TABLE attachment("
				+ "attachment_uuid TEXT,"
				+ "card_uuid TEXT,"     // No Foreign Key, soft link
				+ "attachment_title TEXT,"
				+ "attachment_data BLOB,"
				+ "PRIMARY KEY (attachment_uuid));";
		executePreparedSQL(sqlStatement);
	}

	private void createTable__checklist_item() throws SQLException{
		String sqlStatement = "CREATE TABLE checklist_item("
				+ "checklist_item_uuid TEXT,"
				+ "checklist_uuid TEXT,"
				+ "item_position INTEGER,"
				+ "item_description TEXT,"
				+ "item_completed BOOLEAN,"
				+ "PRIMARY KEY (checklist_item_uuid),"
				+ "FOREIGN KEY (checklist_uuid) REFERENCES checklist (checklist_uuid));";
		executePreparedSQL(sqlStatement);
	}

	private void createTable__card_checklists() throws SQLException{
		String sqlStatement = "CREATE TABLE card_checklists("
				+ "card_uuid TEXT,"
				+ "checklist_uuid TEXT,"
				+ "FOREIGN KEY (card_uuid) REFERENCES card (card_uuid),"
				+ "FOREIGN KEY (checklist_uuid) REFERENCES checklist (checklist_uuid));";
		executePreparedSQL(sqlStatement);
	}

	private void createTable__checklist() throws SQLException{
		String sqlStatement = "CREATE TABLE checklist("
				+ "checklist_uuid TEXT,"
				+ "checklist_title TEXT,"
				+ "checklist_position INTEGER,"
				+ "PRIMARY KEY (checklist_item_uuid));";
		executePreparedSQL(sqlStatement);
	}


	private void executePreparedSQL(String sqlStatement) throws SQLException {
		Statement statement = connection.createStatement();
		PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
		preparedStatement.execute();
	}
}
