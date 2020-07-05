package entities.models.abstract_classes;

import entities.models.interfaces.I_Savable;

import java.util.UUID;

public abstract class DataObject implements I_Savable {

	private UUID uuid;
	private String UUIDstring;

	public UUID get_uuid() { return uuid; }
	//public void set_uuid(UUID uuid) { this.uuid = uuid; }
	public String getUUIDstring() { return UUIDstring; 	}
	public void setUUIDstring(String UUIDstring) {
		this.UUIDstring = UUIDstring;
		uuid = UUID.fromString(UUIDstring);
	}

	public void generate_uuid() {
		uuid = UUID.randomUUID();
		UUIDstring = uuid.toString();
	}


}
