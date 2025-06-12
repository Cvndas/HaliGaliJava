import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ProcessUserBellSmacking_FiveFruitsTest {

    @Test
    public void userSmackCorrectBell_IncreasesCorrectBellCount() throws InterruptedException, IOException {
        // Setup participant and game state
        Participant player = new Participant("Player1");
        player.correctBellCount = 0;

        Main._player = player;
        Main._deadParticipants = new ArrayList<>();
        Main.bellAlreadyHandled = false;
        Main.userSmackedBell = false;

        // Simuleer dat de gebruiker op enter drukt door System.in te vervangen
        String simulatedInput = "\n"; // enter key
        ByteArrayInputStream testIn = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(testIn);

        // Start de thread die de bell smacking afhandelt
        Thread userThread = Main.ProcessUserBellSmacking(true);

        // Wacht tot thread klaar is
        userThread.join();

        // Controleer of de gebruiker de bel heeft geslagen en correctBellCount is verhoogd
        assertTrue(Main.userSmackedBell, "User should have smacked the bell");
        assertEquals(1, player.correctBellCount, "Player's correctBellCount should increase by 1");

        // Zet System.in terug naar standaard
        System.setIn(System.in);
    }
}
