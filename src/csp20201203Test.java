//import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class csp20201203Test {

    @org.junit.jupiter.api.Test
    void main() {
//        Scanner mockScanner = mock(Scanner.class);
        assertEquals(true, Csp20201203.check(3, 1));
        assertEquals(false, Csp20201203.check(2, 4));
    }
}