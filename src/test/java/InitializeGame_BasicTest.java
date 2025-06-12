import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InitializeGame_BasicTest {

    @Test
    public void InitializeGame_BasicTest_ReturnsCorrectParticipantCount() {
        String simulatedInput = "3\nPlayerName\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Main._inputScanner = new java.util.Scanner(System.in);
        int count = Main.InitializeGame();

        assertEquals(3, count); // 1 player + 2 CPUs
    }
}
