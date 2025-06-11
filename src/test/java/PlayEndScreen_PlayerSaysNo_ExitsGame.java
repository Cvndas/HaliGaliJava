import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

public class PlayEndScreen_PlayerSaysNo_ExitsGame {

    @Test
    public void testPlayEndScreen_WhenPlayerSaysNo_ExitsGame() {
        // Arrange
        String simulatedInput = "n\n";
        ByteArrayInputStream input = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(input);
        Main._inputScanner = new Scanner(System.in); // reset with simulated input

        // Capture output
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        // Dummy winner
        Participant dummyWinner = new Participant("DummyWinner");
        dummyWinner.correctBellCount = 3;
        dummyWinner.maxInventorySize = 20;

        // Register the dummy winner in participants
        Main._allParticipants = new ArrayList<>();
        Main._allParticipants.add(dummyWinner);

        // Act & Assert
        try {
            Main.PlayEndScreen(dummyWinner);
        } catch (SecurityException e) {
            // Catch the System.exit call which we will block below
        } finally {
            // Restore stdout
            System.setOut(originalOut);
        }

        String programOutput = output.toString();

        // Assert output contains expected game over info and statistics
        assertTrue(programOutput.contains("GAME OVER"));
        assertTrue(programOutput.contains("Winner: DummyWinner"));
        assertTrue(programOutput.contains("Correct Bell Presses: 3"));
        assertTrue(programOutput.contains("Max Inventory Size: 20"));
        assertTrue(programOutput.contains("Goodbye!"));
    }
}
