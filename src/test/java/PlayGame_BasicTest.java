import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class PlayGame_BasicTest {

    @Test
    public void testProgressTurnIndex_skipsDeadPlayers() {
        // Maak deelnemers aan
        Participant player = new Participant("Player");
        Participant cpu1 = new Participant("CPU1");
        Participant cpu2 = new Participant("CPU2");

        // Stel handkaarten leeg om "dead" te maken
        player.TableCards.clear();
        cpu1.TableCards.clear();
        cpu2.TableCards.clear();

        // Stel leeftijden in
        player.AddCard_ToBottomOfHands(new HaliCard(FruitType.Banana, 1)); // alive
        // cpu1 heeft geen kaarten → dead
        cpu2.AddCard_ToBottomOfHands(new HaliCard(FruitType.Plum, 2)); // alive

        // Zet Main variabelen correct
        Main._aliveParticipants = new ArrayList<>();
        Main._aliveParticipants.add(player);
        Main._aliveParticipants.add(cpu1);
        Main._aliveParticipants.add(cpu2);

        Main._deadParticipants = new ArrayList<>();
        Main._deadParticipants.add(cpu1); // cpu1 is dead

        Main._allCpuParticipants = new ArrayList<>();
        Main._allCpuParticipants.add(cpu1);
        Main._allCpuParticipants.add(cpu2);

        Main._player = player;

        Main._participantCount = 3;

        // Stel huidige beurt in op cpu1 (index 1, die dead is)
        int newTurn = Main.ProgressTurnIndex(
                1,
                Main._allCpuParticipants,
                Main._deadParticipants,
                Main._player);

        // Verwacht dat de beurt geskiped is naar cpu2 (index 2)
        assertEquals(2, newTurn, "Turn should skip dead cpu1 and go to cpu2");
    }
}
