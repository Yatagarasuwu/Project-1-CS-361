/*
 * Adding a block-comment so I don't forget to later.
 * @Author
 * @Date
 * @Description
 */
package fa.dfa;

import java.util.HashMap;
import java.util.Map;

public class DFAState extends fa.State {
    
    // Map to store transitions
    private Map<Character, DFAState> transitions;

    // Constructor to initialize the state and its transition map
    public DFAState(String name) {
        super(name);
        transitions = new HashMap<>();
    }

    // Adds a transition from this state to another on a given symbol
    public void addTransition(char symbol, DFAState nextState) {
        transitions.put(symbol, nextState);
    }

    // Retrieves the next state for a given symbol
    public DFAState getNextState(char symbol) {
        return transitions.get(symbol);
    }

    // Returns the transition map
    public Map<Character, DFAState> getTransitions() {
        return transitions;
    }
}

