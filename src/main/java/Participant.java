package main.java;

import java.util.ArrayDeque;
import java.util.Stack;

public class Participant {

	// ::: Front of queue is the top of the stack (where cards are taken), back is the bottom (where cards are added)
	public String name;
	public int correctBellCount = 0;
	public int maxInventorySize = 0;
	private ArrayDeque<HaliCard> _handCards;





	public Participant(String name) {
		this.name = name;
		_handCards = new ArrayDeque<>();
		TableCards = new Stack<>();
	}





	public int getHandCardSize() {
		return _handCards.size();
	}





	public Stack<HaliCard> TableCards;





	public void AddCard_ToBottomOfHands(HaliCard card) {
		_handCards.addLast(card);
		if (_handCards.size() > maxInventorySize) {
			maxInventorySize = _handCards.size();
		}
	}





	// Returns null if there was no card.
	public HaliCard RemoveCard_FromHandTop() {
		if (_handCards.isEmpty()) {
			return null;
		}

		return _handCards.removeFirst();
	}





	public boolean HasACard() {
		return !_handCards.isEmpty();
	}





	public void PutCardOnTable() {
		HaliCard card = RemoveCard_FromHandTop();
		if (card == null) {
			return;
		}
		TableCards.push(card);
	}

}
