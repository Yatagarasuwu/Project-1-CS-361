/*
 * Adding a block-comment so I don't forget to later.
 * @Author
 * @Date
 * @Description
 */

 package fa.dfa;

 import java.util.HashSet;
 import java.util.HashMap;
 import java.util.Set;
 import java.util.Map;
 import fa.State;
 
 public class DFA implements DFAInterface {
     // Set of states in the DFA
     private Set<DFAState> states;
 
     // alphabet
     private Set<Character> sigma;
 
     // Transition function represented as a map
     private Map<String, Map<Character, String>> transitions;
 
     // Start state
     private String startState;
 
     // Final states
     private Set<String> finalStates;
 
     // Constructor to initialize DFA components
     public DFA() {
         states = new HashSet<>();
         sigma = new HashSet<>();
         transitions = new HashMap<>();
         finalStates = new HashSet<>();
     }
 
     // Adds a state to the DFA
     @Override
     public boolean addState(String name) {
         return states.add(new DFAState(name));
     }
 
     // Sets a state as final
     @Override
     public boolean setFinal(String name) {
         if (containsState(name)) {
             finalStates.add(name);
             return true;
         }
         return false;
     }
 
     // Sets a state as the start state
     @Override
     public boolean setStart(String name) {
         if (containsState(name)) {
             startState = name;
             return true;
         }
         return false;
     }
 
     // Adds a symbol to the alphabet
     @Override
     public void addSigma(char symbol) {
         sigma.add(symbol);
     }
 
     // Checks if a string is accepted by the DFA
     @Override
     public boolean accepts(String s) {
         if (startState == null) return false;
         String currentState = startState;
         for (char c : s.toCharArray()) {
             if (!sigma.contains(c)) return false;
             if (!transitions.containsKey(currentState) || !transitions.get(currentState).containsKey(c)) {
                 return false;
             }
             currentState = transitions.get(currentState).get(c);
         }
         return finalStates.contains(currentState);
     }
 
     // Returns the alphabet set
     @Override
     public Set<Character> getSigma() {
         return new HashSet<>(sigma);
     }
 
     // Retrieves a state by its name
     @Override
     public State getState(String name) {
         for (DFAState state : states) {
             if (state.getName().equals(name)) {
                 return state;
             }
         }
         return null;
     }
 
     // Checks if a state is final
     @Override
     public boolean isFinal(String name) {
         return finalStates.contains(name);
     }
 
     // Checks if a state is the start state
     @Override
     public boolean isStart(String name) {
         return name.equals(startState);
     }
 
     // Adds a transition between states on a given symbol
     @Override
     public boolean addTransition(String fromState, String toState, char onSymb) {
         if (!containsState(fromState) || !containsState(toState) || !sigma.contains(onSymb)) {
             return false;
         }
         transitions.putIfAbsent(fromState, new HashMap<>());
         transitions.get(fromState).put(onSymb, toState);
         return true;
     }
 
     // Swaps two transition symbols in the DFA
     @Override
     public DFA swap(char symb1, char symb2) {
         DFA swappedDFA = new DFA();
         swappedDFA.sigma.addAll(this.sigma);
 
         for (DFAState state : states) {
             swappedDFA.addState(state.getName());
         }
 
         for (String finalState : finalStates) {
             swappedDFA.setFinal(finalState);
         }
 
         swappedDFA.setStart(startState);
 
         for (Map.Entry<String, Map<Character, String>> entry : transitions.entrySet()) {
             String from = entry.getKey();
             for (Map.Entry<Character, String> transition : entry.getValue().entrySet()) {
                 char swappedSymbol = (transition.getKey() == symb1) ? symb2 : (transition.getKey() == symb2 ? symb1 : transition.getKey());
                 swappedDFA.addTransition(from, transition.getValue(), swappedSymbol);
             }
         }
         return swappedDFA;
     }
 
     // Helper method to check if a state exists
     private boolean containsState(String name) {
         for (DFAState state : states) {
             if (state.getName().equals(name)) {
                 return true;
             }
         }
         return false;
     }
 }
 
