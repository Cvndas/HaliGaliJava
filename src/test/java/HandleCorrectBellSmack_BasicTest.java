import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

public class HandleCorrectBellSmack_BasicTest {

    @Test
    public void increasesCorrectBellCount_WhenHandleCorrectBellSmackCalled() {
        Participant winner = new Participant("Amira");
        Participant cpu1 = new Participant("Botana");
        Participant cpu2 = new Participant("HaliBot");

        cpu1.TableCards.push(new HaliCard(FruitType.Banana, 1));
        cpu2.TableCards.push(new HaliCard(FruitType.Plum, 2));

        ArrayList<Participant> all = new ArrayList<>();
        all.add(winner);
        all.add(cpu1);
        all.add(cpu2);

        Main._player = winner;
        Main._aliveParticipants = new ArrayList<>(all);
        Main._aliveCpuParticipants = new ArrayList<>();
        Main._aliveCpuParticipants.add(cpu1);
        Main._aliveCpuParticipants.add(cpu2);
        Main._allCpuParticipants = new ArrayList<>(Main._aliveCpuParticipants);
        Main._allParticipants = new ArrayList<>(all);
        Main._deadParticipants = new ArrayList<>();

        int before = winner.correctBellCount;
        Main.HandleCorrectBellSmack(winner);
        int after = winner.correctBellCount;

        assertEquals(before + 1, after);
    }
}
