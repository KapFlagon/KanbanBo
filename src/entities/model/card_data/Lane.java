package entities.model.card_data;

import java.util.List;

public class Lane {

	private String title;
	private List<Card> cardsList;
	private int lanePosition;

	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public List<Card> getCardsList() { return cardsList; }
	public void setCardsList(List<Card> cardsList) { this.cardsList = cardsList; }
	public int getLanePosition() { return lanePosition; }
	public void setLanePosition(int lanePosition) { this.lanePosition = lanePosition; }
}
