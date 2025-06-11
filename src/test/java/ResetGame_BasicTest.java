// import java.util.ArrayList;

// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.junit.jupiter.api.Assertions.fail;
// import org.junit.jupiter.api.Test;

// public class ResetGame_BasicTest {

//     @Test
//     public void ResetGame_ClearsAllParticipantLists() {
//         Participant player = new Participant("Player");
//         Participant cpu = new Participant("CPU");

//         Main._allCpuParticipants = new ArrayList<>();
//         Main._aliveParticipants = new ArrayList<>();
//         Main._deadParticipants = new ArrayList<>();

//         Main._allCpuParticipants.add(cpu);
//         Main._aliveParticipants.add(player);
//         Main._deadParticipants.add(cpu);

//         boolean result = invokeResetGame();

//         assertTrue(result);
//         assertTrue(Main._allCpuParticipants.isEmpty());
//         assertTrue(Main._aliveParticipants.isEmpty());
//         assertTrue(Main._deadParticipants.isEmpty());
//     }

//     private boolean invokeResetGame() {
//         try {
//             var method = Main.class.getDeclaredMethod("ResetGame");
//             method.setAccessible(true);
//             return (boolean) method.invoke(null);
//         } catch (Exception e) {
//             fail("Failed to invoke ResetGame: " + e.getMessage());
//             return false;
//         }
//     }
// }
