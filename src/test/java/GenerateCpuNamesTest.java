import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class GenerateCpuNamesTest {

    @Test
    public void testGenerateCpuNames_CorrectCountAndUniqueNames() {
        int cpuCount = 3;
        ArrayList<String> generatedNames = Main.generateCpuNames(cpuCount);

        assertEquals(cpuCount, generatedNames.size(), "CPU name count should match.");

        Set<String> uniqueNames = new HashSet<>(generatedNames);
        assertEquals(cpuCount, uniqueNames.size(), "CPU names should be unique.");

        String[] possibleNames = {"BotMax", "Botana", "NeuroBot", "HaliBot", "GaliBot"};
        for (String name : generatedNames) {
            boolean valid = false;
            for (String validName : possibleNames) {
                if (validName.equals(name)) {
                    valid = true;
                    break;
                }
            }
            assertTrue(valid, "CPU name should be in the list: " + name);
        }
    }
}