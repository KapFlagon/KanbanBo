package entities.models.board_data;

public class Board {

	private ArchiveData archiveData;
	private ActiveData activeData;

	public ArchiveData getArchiveData() { return archiveData; }
	public void setArchiveData(ArchiveData archiveData) { this.archiveData = archiveData; }
	public ActiveData getActiveData() { return activeData; }
	public void setActiveData(ActiveData activeData) { this.activeData = activeData; }
}
