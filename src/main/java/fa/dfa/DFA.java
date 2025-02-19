package fa.dfa;

import fa.State;
import fa.FAInterface;
import java.util.*;

public class DFA implements DFAInterface {
    private Set<DFAState> states;           // Set of all states
    private Set<Character> sigma;           // Alphabet (Sigma)
    private Map<String, Map<Character, String>> transitions;  // Transition function
    private String startState;              // The start state
    private Set<String> finalStates;        // Set of final states

    public DFA() {
        states = new LinkedHashSet<>();         // Use LinkedHashSet to maintain insertion order
        sigma = new LinkedHashSet<>();          // Alphabet (Sigma)
        transitions = new HashMap<>();          // Transitions map
        finalStates = new LinkedHashSet<>();    // Set for final states
    }

    @Override
    public boolean addState(String name) {
        if (containsState(name)) return false;
        states.add(new DFAState(name));                  // Add new state
        transitions.put(name, new HashMap<>());           // Initialize transition map for new state
        return true;
    }

    @Override
    public boolean setFinal(String name) {
        if (!containsState(name)) return false;
        finalStates.add(name);                    // Mark state as final
        return true;
    }

    @Override
    public boolean setStart(String name) {
        if (!containsState(name)) return false;
        startState = name;                         // Set the start state
        return true;
    }

    @Override
    public void addSigma(char symbol) {
        sigma.add(symbol);                       // Add symbol to alphabet (Sigma)
    }

    @Override
    public boolean addTransition(String fromState, String toState, char onSymb) {
        if (!containsState(fromState) || !containsState(toState) || !sigma.contains(onSymb)) {
            return false;                        // Check validity of transition
        }
        transitions.putIfAbsent(fromState, new HashMap<>());  // Add transition map if absent
        transitions.get(fromState).put(onSymb, toState);      // Add transition from one state to another
        return true;
    }

    @Override
    public DFA swap(char symb1, char symb2) {
        DFA newDFA = new DFA();
        for (DFAState state : states) {
            newDFA.addState(state.getName());   // Copy all states
        }

        newDFA.setStart(startState);              // Set the start state
        for (String finalState : finalStates) {
            newDFA.setFinal(finalState);         // Copy all final states
        }

        // Swap the transitions between the two symbols
        for (String fromState : transitions.keySet()) {
            for (Map.Entry<Character, String> entry : transitions.get(fromState).entrySet()) {
                char currentSymbol = entry.getKey();
                String toState = entry.getValue();
                if (currentSymbol == symb1) {
                    newDFA.addTransition(fromState, toState, symb2);
                } else if (currentSymbol == symb2) {
                    newDFA.addTransition(fromState, toState, symb1);
                } else {
                    newDFA.addTransition(fromState, toState, currentSymbol);
                }
            }
        }
        return newDFA;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Q = { ");
        for (DFAState state : states) {
            sb.append(state.getName()).append(" ");
        }
        sb.append("}\n");
    
        sb.append("Sigma = { ");
        for (char symbol : sigma) {
            sb.append(symbol).append(" ");
        }
        sb.append("}\n");
    
        sb.append("delta =\n");
    
        // Ensure that the transition matrix matches expected format
        for (DFAState state : states) {
            sb.append("\t");
            for (char symbol : sigma) {
                String toState = transitions.getOrDefault(state.getName(), new HashMap<>()).get(symbol);
                // Print "-" if transition does not exist
                sb.append(toState != null ? toState : "-").append(" ");
            }
            sb.append("\n");
        }
    
        sb.append("q0 = ").append(startState).append("\n");
        sb.append("F = { ");
        for (String finalState : finalStates) {
            sb.append(finalState).append(" ");
        }
        sb.append("}");
    
        return sb.toString();
    }
    
    
    

    @Override
    public boolean isFinal(String name) {
        return finalStates.contains(name);      // Return true if state is final
    }

    @Override
    public boolean accepts(String s) {
        if (startState == null || s == null) return false;
        String currentState = startState;
        for (char c : s.toCharArray()) {
            if (!sigma.contains(c)) return false;
            if (!transitions.containsKey(currentState) || !transitions.get(currentState).containsKey(c)) {
                return false;
            }
            currentState = transitions.get(currentState).get(c);
        }
        return finalStates.contains(currentState);  // Accept if we reach a final state
    }

    @Override
    public boolean isStart(String name) {
        return name.equals(startState);          // Check if state is the start state
    }

    @Override
    public Set<Character> getSigma() {
        return new LinkedHashSet<>(sigma);       // Return alphabet (Sigma)
    }

    @Override
    public State getState(String name) {
        for (DFAState state : states) {
            if (state.getName().equals(name)) {
                return state;                    // Return state object if found
            }
        }
        return null;                             // Return null if state doesn't exist
    }

    private boolean containsState(String name) {
        for (DFAState state : states) {
            if (state.getName().equals(name)) {
                return true;                   // Check if state exists
            }
        }
        return false;
    }
}
