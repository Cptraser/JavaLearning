import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattlefieldTest {
    Battlefield f = new Battlefield();

    @Test
    void main() {
        f.push(1, 1, 1);
        f.push(1, 1, 1);
        f.push(1, 1, 1);
        f.push(1, 1, 1);
        f.push(1, 1, 1);
        f.push(1, 1, 1);
        f.push(1, 1, 1);
        assertEquals(7, f.cnt);
        assertTrue(f.del(7));
    }

}