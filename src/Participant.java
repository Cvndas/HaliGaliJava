import java.util.Deque;
import java.util.ArrayDeque;

public class Participant {

    // ::: Front of queue is the top of the stack (where cards are taken), back is the bottom (where cards are added)
    private ArrayDeque<HaliCard> _deque;

    public Participant()
    {
        _deque = new ArrayDeque<>();
    }






    public void AddCardToBottom(HaliCard card) {
        _deque.addLast(card);
    }





    public HaliCard RemoveCardFromTop() {
        return _deque.removeFirst();
    }

}
