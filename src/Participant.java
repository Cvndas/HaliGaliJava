import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Stack;

public class Participant {

    // ::: Front of queue is the top of the stack (where cards are taken), back is the bottom (where cards are added)
    private ArrayDeque<HaliCard> _deque;





    public Participant() {
        _deque = new ArrayDeque<>();
        CardsInFrontOfParticipant = new Stack<>();
    }





    public Stack<HaliCard> CardsInFrontOfParticipant;





    public void AddCardToBottom(HaliCard card) {
        _deque.addLast(card);
    }





    // Returns null if there was no card.
    public HaliCard RemoveCardFromTop() {
        if (_deque.isEmpty()) {
            return null;
        }
        return _deque.removeFirst();
    }





    public boolean HasACard() {
        return !_deque.isEmpty();
    }





    public void PutCardOnTable() {
        HaliCard card = RemoveCardFromTop();
        if (card == null) {
            return;
        }
        CardsInFrontOfParticipant.push(card);
    }

}
