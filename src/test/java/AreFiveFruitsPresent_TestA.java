import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class AreFiveFruitsPresent_TestA {
	
	//	@Test
//	public void AreFiveFruitsPresent_NoParticipants_False() {
//		ArrayList<Participant> aliveParticipants = new ArrayList<>() {};
//		boolean result = Main.AreFiveFruitsPresent(aliveParticipants);
//		assertFalse(result);
//	}

	
	@Test
	public void AreFiveFruitsPresent_FiveFruits_True() {
		ArrayList<Participant> aliveParticipants = new ArrayList<>() {};

		Participant participantA = new Participant("Joseph");
		Participant participantB = new Participant("Broseph");
		Participant participantC = new Participant("Drloseph");

		HaliCard bananaFour = new HaliCard(FruitType.Lime, 4);
		HaliCard bananaOne = new HaliCard(FruitType.Plum, 5);
		HaliCard strawberryOne = new HaliCard(FruitType.Strawberry, 1);

		participantA.AddCard_ToBottomOfHands(bananaFour);
		participantB.AddCard_ToBottomOfHands(bananaOne);
		participantC.AddCard_ToBottomOfHands(strawberryOne);

		participantA.PutCardOnTable();
		participantB.PutCardOnTable();
		participantC.PutCardOnTable();

		aliveParticipants.add(participantA);
		aliveParticipants.add(participantB);
		aliveParticipants.add(participantC);

		boolean result = Main.AreFiveFruitsPresent(aliveParticipants);

		assertTrue(result);
	}
	
	@Test
	public void AreFiveFruitsPresent_NullCardAndFourFruits_False() {
		// Intended to hit the continue line for tableCard == null, for coverage
		ArrayList<Participant> aliveParticipants = new ArrayList<>() {};
		
		Participant participantA = new Participant("Droseph");
		Participant participantB = new Participant("Lroseph");
		
		HaliCard bananaFour = new HaliCard(FruitType.Banana, 4);
		
		participantA.AddCard_ToBottomOfHands(bananaFour);
		
		participantA.PutCardOnTable();
		
		aliveParticipants.add(participantA);
		aliveParticipants.add(participantB);
		
		boolean result = Main.AreFiveFruitsPresent(aliveParticipants);
		
		assertFalse(result);
	}
	
}
