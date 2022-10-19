import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Csp20180903Test {

    @Test
    void getCent() {
        assertEquals(3, Csp20180903.getCent("......sdfaf"));
    }

    @Test
    void rep(){
        assertEquals("#iIfnfLKd", Csp20180903.rep("#iIfnfLKd"));
        assertEquals("iifnflkd", Csp20180903.rep("iIfnfLKd"));
    }

}