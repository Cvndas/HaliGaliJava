import java.util.ArrayList;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class GrabAllTableCards_BasicTest {

    @Test
    public void GrabAllTableCards_BasicTest_WinnerGetsCards() {
        Participant winner = new Participant("Winner");
        Participant p1 = new Participant("P1");
        Participant p2 = new Participant("P2");

        p1.TableCards.push(new HaliCard(FruitType.Banana, 1));
        p2.TableCards.push(new HaliCard(FruitType.Plum, 2));

        ArrayList<Participant> players = new ArrayList<>();
        players.add(winner);
        players.add(p1);
        players.add(p2);

        Main._aliveParticipants = players;
        Main._player = winner;
        Main._allCpuParticipants = new ArrayList<>();
        Main._aliveCpuParticipants = new ArrayList<>();
        Main._deadParticipants = new ArrayList<>();

        Main.GrabAllTableCards(winner);

        int cards = 0;
        while (winner.HasACard()) {
            winner.RemoveCard_FromHandTop();
            cards++;
        }

        assertEquals(2, cards);
        assertTrue(p1.TableCards.isEmpty());
        assertTrue(p2.TableCards.isEmpty());
    }
}
