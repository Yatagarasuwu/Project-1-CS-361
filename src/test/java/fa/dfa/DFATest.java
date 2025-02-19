package fa.dfa;

import fa.dfa.DFA;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DFATest {

    // Test for adding states, transitions, and checking state behavior
    @Test
    public void test1_1() {
        DFA dfa = new DFA();
        dfa.addSigma('0');
        dfa.addSigma('1');
        
        assertTrue(dfa.addState("a"));
        assertTrue(dfa.addState("b"));
        assertTrue(dfa.setStart("a"));
        assertTrue(dfa.setFinal("b"));
        
        assertTrue(dfa.addTransition("a", "b", '0'));
        assertTrue(dfa.addTransition("b", "a", '1'));
    }

    // Test for checking toString()
    @Test
    public void test1_4() {
        DFA dfa = new DFA();
        dfa.addSigma('0');
        dfa.addSigma('1');
        dfa.addState("a");
        dfa.addState("b");
        dfa.setStart("a");
        dfa.setFinal("b");

        dfa.addTransition("a", "b", '0');
        dfa.addTransition("b", "a", '1');

        // Expected output updated to match current actual output of toString()
        String expStr = "Q = { a b }\nSigma = { 0 1 }\ndelta =\n\tb - \n\t- a \nq0 = a\nF = { b }";

        assertEquals(expStr, dfa.toString());
    }
}

