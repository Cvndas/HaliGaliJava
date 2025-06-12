// import java.util.ArrayList;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import org.junit.jupiter.api.Test;

// public class KickOutDeadParticipants_BasicTest {

//     @Test
//     public void KickOutDeadParticipants_RemovesPlayersWithNoCards() {
//         Participant p1 = new Participant("Alive1");
//         Participant p2 = new Participant("Dead1");
//         Participant p3 = new Participant("Alive2");

//         p1.AddCard_ToBottomOfHands(new HaliCard(FruitType.Lime, 1));
//         p3.AddCard_ToBottomOfHands(new HaliCard(FruitType.Strawberry, 1));

//         ArrayList<Participant> players = new ArrayList<>();
//         players.add(p1);
//         players.add(p2);
//         players.add(p3);

//         Main._aliveParticipants = new ArrayList<>(players);
//         Main._aliveCpuParticipants = new ArrayList<>(players);
//         Main._deadParticipants = new ArrayList<>();

//         ArrayList<Participant> eliminated = Main.KickOutDeadParticipants();

//         assertEquals(1, eliminated.size());
//         assertTrue(eliminated.contains(p2));
//         assertFalse(Main._aliveParticipants.contains(p2));
//         assertFalse(Main._aliveCpuParticipants.contains(p2));
//         assertTrue(Main._deadParticipants.contains(p2));
//     }
// }
