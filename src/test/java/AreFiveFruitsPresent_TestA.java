import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AreFiveFruitsPresent_TestA {
	@Test
	public void AreFiveFruitsPresent_NoParticipants() {

		ArrayList<Participant> aliveParticipants = new ArrayList<>() {};
		boolean result = Main.AreFiveFruitsPresent(aliveParticipants);
		assertFalse(result);
	}
	
}
