import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ProcessCPUBellSmacking_BasicTest {

    @Test
    public void cpuSmackIncreasesCorrectBellCount_WhenFiveFruitsPresent() throws InterruptedException {
        Participant cpu = new Participant("Botana");
        cpu.correctBellCount = 0;

        Main._aliveCpuParticipants = new ArrayList<>();
        Main._aliveCpuParticipants.add(cpu);
        Main._deadParticipants = new ArrayList<>();
        Main.bellAlreadyHandled = false;

        int correctBefore = cpu.correctBellCount;

        Random fixed = new Random() {
            @Override
            public boolean nextBoolean() {
                return true;
            }

            @Override
            public int nextInt(int bound) {
                return 0;
            }
        };

        Thread t = Main.ProcessCPUBellSmacking(true, fixed);
        t.join();

        int correctAfter = cpu.correctBellCount;

        assertEquals(correctBefore + 1, correctAfter, "CorrectBellCount should increase by 1");
    }
}

