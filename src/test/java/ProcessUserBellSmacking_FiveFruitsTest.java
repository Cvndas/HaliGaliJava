// import java.io.ByteArrayInputStream;
// import java.util.ArrayList;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import org.junit.jupiter.api.Test;

// public class ProcessUserBellSmacking_FiveFruitsTest {

//     @Test
//     public void userSmackCorrectBell_IncreasesCorrectBellCount() throws InterruptedException {
//         Participant player = new Participant("Player1");
//         player.correctBellCount = 0;

//         Main._player = player;
//         Main._deadParticipants = new ArrayList<>();
//         Main.bellAlreadyHandled = false;
//         Main.userSmackedBell = false;

//         String simulatedInput = "\n";
//         ByteArrayInputStream testIn = new ByteArrayInputStream(simulatedInput.getBytes());

//         Thread userThread = Main.ProcessUserBellSmacking(true, testIn);
//         userThread.join(2000);

//         assertTrue(Main.userSmackedBell, "User should have smacked the bell");
//         assertEquals(1, player.correctBellCount, "Player's correctBellCount should increase by 1");
//     }

// }