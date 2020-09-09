package entities.models.domain_objects.db_version;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "db_version")
public class DB_Version {

	@DatabaseField(id = true, unique = true, useGetSet = true, canBeNull = false)
	private String version_no;


	// Getters and Setters
	public String getVersion_no() {
		return version_no;
	}

	public void setVersion_no(String version_no) {
		this.version_no = version_no;
	}
}
