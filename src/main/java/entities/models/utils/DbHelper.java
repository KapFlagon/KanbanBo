package entities.models.utils;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import utils.FileAndDirectoryHelper;
import utils.ProgramDirectoryHelper;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.sql.SQLException;

public class DbHelper {

	//private static Connection connection;
	private static final String DRIVER = "org.sqlite.JDBC";

	// jdbc:sqlite:KanbanBo.db
	// jdbc:sqlite:UserTemplates.db

	private static String fileToDbURL(File dbFile) {
		String fileName = dbFile.getName();
		String dbURL = buildDbUrlWithFileName(fileName);
		return dbURL;
	}

	private static String buildDbUrlWithFileName(String fileNameString) {
		String prefix = "jdbc:sqlite:";
		String dbURL = prefix + fileNameString;
		return dbURL;
	}

	public static JdbcPooledConnectionSource getConnectionSourceForSpecifiedDbFile(File dbFile) throws SQLException {
		String dbURLString = fileToDbURL(dbFile);
		JdbcPooledConnectionSource specifiedDbConnection = new JdbcPooledConnectionSource(dbURLString);
		return specifiedDbConnection;
	}


	public static boolean templatesDbExists() throws URISyntaxException {
		Path templateDbPath = ProgramDirectoryHelper.parseUserTemplatesDbPath();
		return FileAndDirectoryHelper.fileExists(templateDbPath);
	}

	public static JdbcPooledConnectionSource getConnectionSourceForUserTemplateDbFile() throws URISyntaxException, SQLException {
		Path templateDbPath = ProgramDirectoryHelper.parseUserTemplatesDbPath();
		JdbcPooledConnectionSource templateDbConnection = new JdbcPooledConnectionSource(templateDbPath.toString());
		return templateDbConnection;
	}

	public static void createTemplatesDb() {
		// TODO create an empty user template DB file, with some empty tables.
	}

}
