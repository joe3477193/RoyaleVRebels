package model;

public class Tile {


	private Card card;


	public Tile() {
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public Card getCard() {
		return card;
	}

	public boolean hasCard() {
		if (card!=null){
			return true;
		}
		return false;
	}

	public void removeCard() {
		card = null;
	}

}
