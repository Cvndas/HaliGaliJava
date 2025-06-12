

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ProgressTurnIndex_Tests {

	@Test
	public void ProgressTurnIndex_TwoDeadParticipants_2()
	{
		// ::: To hit max coverage, the following properties must be met
		/*
		- currentPlayerTurn input must be equal to allCpuParticipants.size()
		- The player must be dead
		- The next CPU must be dead
		- The CPU After the dead cpu is not dead.
		 */

		Participant deadPlayer = new Participant("DeadPlayer");
		Participant deadCPU = new Participant("DeadCPU");
		Participant aliveCPU = new Participant("AliveCPU");

		ArrayList<Participant> allCpuParticipants = new ArrayList<>();
		allCpuParticipants.add(deadCPU);
		allCpuParticipants.add(aliveCPU);
		

		ArrayList<Participant> deadParticipants = new ArrayList<>();
		deadParticipants.add(deadPlayer);
		deadParticipants.add(deadCPU);


		int result = Main.ProgressTurnIndex(
				  allCpuParticipants.size(),
				  allCpuParticipants,
				  deadParticipants,
				  deadPlayer
		);

		assertEquals(2, result);
	}

}
