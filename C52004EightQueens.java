public class C52004EightQueens {

    // Q1: Check if character is valid (either 'Q' or '.')
    public static boolean Q1_checkCharacter(Character c) {
	    return c != null && (c == 'Q' || c == '.');
	}

    // Q2: Check if board is valid (8x8, valid characters, exactly 8 queens)
    public static boolean Q2_validBoard(Character[][] board) {
        // Test 1: Check if board is null or not 8x8
        if (board == null || board.length != 8) return false;

        int queenCount = 0;
        for (int i = 0; i < 8; i++) {
            // Test 2: Check if any row is null or not of length 8
            if (board[i] == null || board[i].length != 8) return false;
            for (int j = 0; j < 8; j++) {
                // Test 3: Check if any character is invalid
                if (!Q1_checkCharacter(board[i][j])) return false;
                // Count queens for Test 4
                if (board[i][j] == 'Q') queenCount++;
            }
        }
        // Test 4: Check if there are exactly 8 queens
        return queenCount == 8;
    }

    // Q3: Convert board to binary string (3-bit per queen)
    public static String Q3_generateBinaryString(Character[][] board) {
        // Test 1: If board is null, return null
        if (board == null) return null;

        StringBuilder binary = new StringBuilder();
        // Process rows from bottom (row 7) to top (row 0)
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 'Q') {
                    // Convert column index to 3-bit binary string
                    String bin = String.format("%3s", Integer.toBinaryString(j)).replace(' ', '0');
                    binary.append(bin);
                    break; // Since there's exactly one queen per row
                }
            }
        }
        return binary.toString();
    }

    // Q4: Generate initial random binary string
    public static String Q4_initialStart() {
        StringBuilder binary = new StringBuilder();
        java.util.Random rand = new java.util.Random();
        // Process rows from bottom (row 7) to top (row 0)
        for (int i = 7; i >= 0; i--) {
            int col = rand.nextInt(8); // Random column index (0 to 7)
            String bin = String.format("%3s", Integer.toBinaryString(col)).replace(' ', '0');
            binary.append(bin);
        }
        return binary.toString();
    }

    // Q5: Calculate the fitness of a binary string
    public static Double Q5_fitnessFunction(String solution) {
        // Test 2: If input is null, return null
        if (solution == null) return null;

        // Test 1: If input is empty or invalid, return Double.MAX_VALUE
        if (solution.isEmpty() || solution.length() != 24 || !solution.matches("[01]+")) {
            return Double.MAX_VALUE;
        }

        // Parse the binary string into queen positions (column indices)
        int[] positions = new int[8];
        for (int i = 0; i < 8; i++) {
            // Map row i (top to bottom) to the correct 3-bit segment
            // Binary string is ordered bottom to top (row 7 to row 0)
            // So row i corresponds to segment (7-i)
            int segmentIndex = 7 - i;
            String bin = solution.substring(segmentIndex * 3, segmentIndex * 3 + 3);
            positions[i] = Integer.parseInt(bin, 2);
        }

        // Calculate clashes
        int clashes = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = i + 1; j < 8; j++) {
                // Clash if queens are in the same column or on the same diagonal
                if (positions[i] == positions[j] || Math.abs(i - j) == Math.abs(positions[i] - positions[j])) {
                    clashes++;
                }
            }
        }

        // Test 3: Return fitness value
        return 56.0 - clashes * 2.0;
    }

    // Q6: Mutate a binary string by flipping one random bit
    public static String Q6_smallChange(String solution) {
        // Test 1: Check for invalid input and return null
        if (solution == null || solution.isEmpty() || solution.length() != 24 || !solution.matches("[01]+")) {
            return null;
        }

        // Test 2: Flip a random bit and return the modified string
        java.util.Random rand = new java.util.Random();
        int bitToFlip = rand.nextInt(24);
        char[] bits = solution.toCharArray();
        bits[bitToFlip] = (bits[bitToFlip] == '0') ? '1' : '0';
        return new String(bits);
    }
    // Q7: Solve the Eight Queens problem using hill climbing
    public static String init(int iterations) {
        // Test 1: If iterations < 1, return null
        if (iterations < 1) return null;

        // Start with a random solution
        String current = Q4_initialStart();
        Double currentFitness = Q5_fitnessFunction(current);

        // If the initial solution is invalid, return null
        if (currentFitness == null || currentFitness == Double.MAX_VALUE) {
            return null;
        }

        // Hill climbing: iterate until max iterations or a perfect solution is found
        for (int i = 0; i < iterations && currentFitness < 56.0; i++) {
            String mutated = Q6_smallChange(current);
            // If the mutated solution is invalid, skip it
            if (mutated == null) continue;

            Double mutatedFitness = Q5_fitnessFunction(mutated);
            // If the mutated fitness is invalid, skip it
            if (mutatedFitness == null || mutatedFitness == Double.MAX_VALUE) {
                continue;
            }

            // Accept the new solution if it's at least as good
            if (mutatedFitness >= currentFitness) {
                current = mutated;
                currentFitness = mutatedFitness;
            }
        }

        return current;
    }
    // Optional: Test main method (can be removed for CodeRunner)
    public static void main(String[] args) {
//    	Character[][] validBoard = {
//              {'.', 'Q', '.', '.', '.', '.', '.', '.'},
//              {'.', '.', '.', '.', '.', 'Q', '.', '.'},
//              {'Q', '.', '.', '.', '.', '.', '.', '.'},
//              {'.', '.', '.', '.', '.', '.', 'Q', '.'},
//              {'.', '.', '.', 'Q', '.', '.', '.', '.'},
//              {'.', '.', '.', '.', '.', '.', '.', 'Q'},
//              {'.', '.', 'Q', '.', '.', '.', '.', '.'},
//              {'.', '.', '.', '.', 'Q', '.', '.', '.'}
//          };
//    		
//        String solution = solve(1000);
//      System.out.println("'.' is valid: " + isValidSquareChar('.'));
//      System.out.println("'Q' is valid: " + isValidSquareChar('Q'));
//      System.out.println("'x' is valid: " + isValidSquareChar('x'));
//        System.out.println("Final solution: " + solution);
//        System.out.println("Fitness: " + getFitness(solution));
//      System.out.println("Binary string: " + boardToBinary(validBoard));
    	

            // Test Q1 - Check Character
            System.out.println("Q1: isValidSquareChar()");
            System.out.println(Q1_checkCharacter('Q')); // true
            System.out.println(Q1_checkCharacter('.')); // true
            System.out.println(Q1_checkCharacter('x')); // false
            System.out.println(Q1_checkCharacter(null)); // false

            // Test Q2 - Valid Board
            System.out.println("\nQ2: isValidBoard()");
            Character[][] validBoard = {
                {'.', 'Q', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', 'Q', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', 'Q', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', 'Q'},
                {'.', '.', 'Q', '.', '.', '.', '.', '.'},
                {'Q', '.', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', 'Q', '.'},
                {'.', '.', '.', '.', 'Q', '.', '.', '.'}
            };
            System.out.println(Q2_validBoard(validBoard)); // true

            // Test Q3 - boardToBinary()
            System.out.println("\nQ3: boardToBinary()");
            String binary = Q3_generateBinaryString(validBoard);
            System.out.println("Binary: " + binary); // should be 24 bits
            System.out.println("Length: " + binary.length()); // should be 24

            // Test Q4 - generateRandomBinaryBoard()
            System.out.println("\nQ4: generateRandomBinaryBoard()");
            String randBoard = Q4_initialStart();
            System.out.println("Random Binary: " + randBoard);
            String solution = init(1050);
            System.out.println("Solution: " + solution);
            System.out.println("Fitness : " + Q5_fitnessFunction(solution));
            
        }

    }