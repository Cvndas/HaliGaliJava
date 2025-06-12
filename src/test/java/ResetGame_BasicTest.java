import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class ResetGame_BasicTest {

    @Test
    public void ResetGame_ClearsAllLists() {
        Main._allCpuParticipants = new ArrayList<>();
        Main._aliveParticipants = new ArrayList<>();
        Main._deadParticipants = new ArrayList<>();

        Main._allCpuParticipants.add(new Participant("Bot1"));
        Main._aliveParticipants.add(new Participant("Player1"));
        Main._deadParticipants.add(new Participant("Player2"));

        boolean result = Main.ResetGame();

        assertTrue(result);
    }
}
