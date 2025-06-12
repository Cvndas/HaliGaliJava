import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ProvidePlayerNameTest {

    @Test
    public void testProvidePlayerName_ValidName() {
        String input = "Alice\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        String result = Main.providePlayerName(scanner);
        assertEquals("Alice", result);
    } }