/*
 * The logic for a DFA within this project.
 * By extending the DFAInterface, this class defines the methods needed for a DFA's states, alphabet, transitions, and start/final points.
 * Out of the concrete classes given, we chose to use a HashSet and HashMap for the structure of this class.
 * @Author Parker Smith, Sabastian Leeper
 */

 package fa.dfa;

 import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
 
     /*
     * Adds a state to the DFA
     * @param name of the added state
     * @return true if the state is added, otherwise false
     */  
     @Override
     public boolean addState(String name) {
         return states.add(new DFAState(name));
     }
 
     /*
     * Sets a state as final
     * @param name of the state to be set as final
     * @return true if the state is set as final, otherwise false
     */
     @Override
     public boolean setFinal(String name) {
         if (containsState(name)) {
             finalStates.add(name);
             return true;
         }
         return false;
     }
 
     /*
     * Sets a state as the start state
     * @param name of the state to be set as the start
     * @return true if the state is set as start, otherwise false
     */
     @Override
     public boolean setStart(String name) {
         if (containsState(name)) {
             startState = name;
             return true;
         }
         return false;
     }
 
     /*
     * Adds a symbol to the alphabet
     * @param symbol the symbol added to the alphabet
     */  
     @Override
     public void addSigma(char symbol) {
         sigma.add(symbol);
     }

     /*
     * Checks if a string is accepted by the DFA
     * @param s the string to check
     * @return true if the string is accepted, otherwise false
     */  
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
 

     /*
     * Returns the alphabet set
     * @return a set containing all the alphabet symbols
     */  
     @Override
     public Set<Character> getSigma() {
         return new HashSet<>(sigma);
     }
 
     
     /*
     * Retrieves a state by its name
     * @param name the name of the state to retrieve
     * @return the state with the given name, if there is none then null
     */  
     @Override
     public State getState(String name) {
         for (DFAState state : states) {
             if (state.getName().equals(name)) {
                 return state;
             }
         }
         return null;
     }
 
     /*
     * Checks if a string is final
     * @param name the name of which state the method checks
     * @return true if the state is final, otherwise false
     */  
     @Override
     public boolean isFinal(String name) {
         return finalStates.contains(name);
     }
 
     /*
     * Checks if a string is the start state
     * @param name the name of which state the method checks
     * @return true if the state is the start state, otherwise false
     */  
     @Override
     public boolean isStart(String name) {
         return name.equals(startState);
     }
 
      /* 
      * Adds a transition between states on a given symbol
      * @param fromState the name of the state to transition from
      * @param toState the name of the state to transition to
      * @param onSymb the symbol on which the transition occurs
      * @return true if the transition was added, otherwise false  
      */  
     @Override
     public boolean addTransition(String fromState, String toState, char onSymb) {
         if (!containsState(fromState) || !containsState(toState) || !sigma.contains(onSymb)) {
             return false;
         }
         transitions.putIfAbsent(fromState, new HashMap<>());
         transitions.get(fromState).put(onSymb, toState);
         return true;
     }
     
     /* 
     * Swaps two transition symbols in the DFA
     * @param symb1 the first symbol to swap
     * @param symb2 the second symbol to swap
     * @return a new DFA with the swapped symbols
     */
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
 
     /*
     * Helper method to check if a state exists
     * @param name the name of which state the method checks
     * @return true if the state exists, otherwise false
     */  
     private boolean containsState(String name) {
         for (DFAState state : states) {
             if (state.getName().equals(name)) {
                 return true;
             }
         }
         return false;
     }
 }
 
