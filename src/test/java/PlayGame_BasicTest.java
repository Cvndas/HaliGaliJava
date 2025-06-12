import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class PlayGame_BasicTest {

    @Test
    public void testProgressTurnIndex_skipsDeadPlayers() {

        Participant player = new Participant("Player");
        Participant cpu1 = new Participant("CPU1");
        Participant cpu2 = new Participant("CPU2");

        player.TableCards.clear();
        cpu1.TableCards.clear();
        cpu2.TableCards.clear();

        player.AddCard_ToBottomOfHands(new HaliCard(FruitType.Banana, 1)); 

        cpu2.AddCard_ToBottomOfHands(new HaliCard(FruitType.Plum, 2));


        Main._aliveParticipants = new ArrayList<>();
        Main._aliveParticipants.add(player);
        Main._aliveParticipants.add(cpu1);
        Main._aliveParticipants.add(cpu2);

        Main._deadParticipants = new ArrayList<>();
        Main._deadParticipants.add(cpu1);

        Main._allCpuParticipants = new ArrayList<>();
        Main._allCpuParticipants.add(cpu1);
        Main._allCpuParticipants.add(cpu2);

        Main._player = player;

        Main._participantCount = 3;

        int newTurn = Main.ProgressTurnIndex(
                1,
                Main._allCpuParticipants,
                Main._deadParticipants,
                Main._player);

        assertEquals(2, newTurn, "Turn should skip dead cpu1 and go to cpu2");
    }
}
