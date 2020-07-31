package entities.models.interfaces;

import java.util.List;
import java.util.Optional;

public interface I_DAO<T> {
	// DOA: Database Access Object pattern

	// CRUD operations
	List<T> getAllRecords();
	Optional<T> getRecord(String uuid_val);
	void saveRecord(T t);
	void updateRecord(T t, String[] params);
	void deleteRecord(T t);
}
