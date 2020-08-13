package entities.models.utils;

import java.util.UUID;

public class UUID_Helper {


	private UUID uuid;
	private String uuid_string;


	public UUID_Helper() {
		generate_uuid();
	}

	public UUID_Helper(String uuid_string) {
		this.uuid_string = uuid_string;
		uuid = UUID.fromString(uuid_string);
	}


	public UUID get_uuid() { return uuid; }
	//public void set_uuid(UUID uuid) { this.uuid = uuid; }

	public String getUUIDstring() { return uuid_string; 	}
	public void setUUIDstring(String uuid_string) {
		this.uuid_string = uuid_string;
		uuid = UUID.fromString(uuid_string);
	}

	public void generate_uuid() {
		uuid = UUID.randomUUID();
		uuid_string = uuid.toString();
	}

	public boolean compare(String other_uuid_string) {
		String temp_uuid_string = other_uuid_string;
		UUID temp_uuid = UUID.fromString(temp_uuid_string);
		boolean result = uuid.equals(temp_uuid);
		return result;
	}
}
