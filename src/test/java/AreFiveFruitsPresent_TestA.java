import org.junit.jupiter.api.Test;

import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;

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
		
		HaliCard bananaFour = new HaliCard(FruitType.Banana, 4);
		HaliCard bananaOne = new HaliCard(FruitType.Banana, 1);
		
		participantA.AddCard_ToBottomOfHands(bananaFour);
		participantB.AddCard_ToBottomOfHands(bananaOne);
		
		participantA.PutCardOnTable();
		participantB.PutCardOnTable();
		
		aliveParticipants.add(participantA);
		aliveParticipants.add(participantB);
		
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
