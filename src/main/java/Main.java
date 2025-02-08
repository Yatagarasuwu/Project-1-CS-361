import fa.dfa.DFA;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // new DFA
        DFA dfa = new DFA();
        
        // Alphabet
        dfa.addSigma('0');
        dfa.addSigma('1');
        
        // Add states
        dfa.addState("a");
        dfa.addState("b");

        // Set start and final states
        dfa.setStart("a");
        dfa.setFinal("b");

        // Transitions
        dfa.addTransition("a", "a", '0');
        dfa.addTransition("a", "b", '1');
        dfa.addTransition("b", "a", '0');
        dfa.addTransition("b", "b", '1');

        // Interactive testing
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a binary string to check if the DFA accepts or rejects (type 'exit' to quit):");
        
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) break;

            boolean accepted = dfa.accepts(input);
            System.out.println("DFA " + (accepted ? "ACCEPTS" : "REJECTS") + " the string: " + input);
        }
        
        scanner.close();
        System.out.println("Program exited.");
    }
}

