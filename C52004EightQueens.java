public class C52004EightQueens {

    // Q1: Check if character is valid (either 'Q' or '.')
    public static boolean Q1_checkCharacter(Character symbolToCheck) {
        return symbolToCheck != null && (symbolToCheck == 'Q' || symbolToCheck == '.');
    }
    

    // Q2: Check if board is valid (8x8, valid characters, exactly 8 queens)
    public static boolean Q2_validBoard(Character[][] chessGrid) {
        // Test 1: Check if board is null or not 8x8
        if (chessGrid == null || chessGrid.length != 8) return false;
    
        int queenCounter = 0;
        for (int rowIdx = 0; rowIdx < 8; rowIdx++) {
            // Test 2: Check if any row is null or not of length 8
            if (chessGrid[rowIdx] == null || chessGrid[rowIdx].length != 8) return false;
            for (int colIdx = 0; colIdx < 8; colIdx++) {
                // Test 3: Check if any character is invalid
                if (!Q1_checkCharacter(chessGrid[rowIdx][colIdx])) return false;
                // Count queens for Test 4
                if (chessGrid[rowIdx][colIdx] == 'Q') queenCounter++;
            }
        }
        // Test 4: Check if there are exactly 8 queens
        return queenCounter == 8;
    }
    
    // Q3: Convert board to binary string (3-bit per queen)
    public static String Q3_generateBinaryString(Character[][] chessGrid) {
        // Test 1: If board is null, return null
        if (chessGrid == null) return null;
    
        StringBuilder binaryBuilder = new StringBuilder();
        // Process rows from bottom (row 7) to top (row 0)
        for (int rowIdx = 7; rowIdx >= 0; rowIdx--) {
            for (int colIdx = 0; colIdx < 8; colIdx++) {
                if (chessGrid[rowIdx][colIdx] == 'Q') {
                    // Convert column index to 3-bit binary string
                    String binaryCol = String.format("%3s", Integer.toBinaryString(colIdx)).replace(' ', '0');
                    binaryBuilder.append(binaryCol);
                    break; // Since there's exactly one queen per row
                }
            }
        }
        return binaryBuilder.toString();
    }
    
    // Q4: Generate initial random binary string
    public static String Q4_initialStart() {
        StringBuilder binaryBuilder = new StringBuilder();
        java.util.Random randomGenerator = new java.util.Random();
        // Process rows from bottom (row 7) to top (row 0)
        for (int rowIdx = 7; rowIdx >= 0; rowIdx--) {
            int colIdx = randomGenerator.nextInt(8); // Random column index (0 to 7)
            String binaryCol = String.format("%3s", Integer.toBinaryString(colIdx)).replace(' ', '0');
            binaryBuilder.append(binaryCol);
        }
        return binaryBuilder.toString();
    }
    

    // Q5: Calculate the fitness of a binary string
    public static Double Q5_fitnessFunction(String binarySolution) {
        // Test 2: If input is null, return null
        if (binarySolution == null) return null;
    
        // Test 1: If input is empty or invalid, return Double.MAX_VALUE
        if (binarySolution.isEmpty() || binarySolution.length() != 24 || !binarySolution.matches("[01]+")) {
            return Double.MAX_VALUE;
        }
    
        // Parse the binary string into queen positions (column indices)
        int[] queenPositions = new int[8];
        for (int rowIdx = 0; rowIdx < 8; rowIdx++) {
            // Map row i (top to bottom) to the correct 3-bit segment
            // Binary string is ordered bottom to top (row 7 to row 0)
            // So row i corresponds to segment (7-i)
            int segmentIdx = 7 - rowIdx;
            String binarySegment = binarySolution.substring(segmentIdx * 3, segmentIdx * 3 + 3);
            queenPositions[rowIdx] = Integer.parseInt(binarySegment, 2);
        }
    
        // Calculate clashes
        int conflicts = 0;
        for (int colIdx1 = 0; colIdx1 < 8; colIdx1++) {
            for (int colIdx2 = colIdx1 + 1; colIdx2 < 8; colIdx2++) {
                // Clash if queens are in the same column or on the same diagonal
                if (queenPositions[colIdx1] == queenPositions[colIdx2] ||
                    Math.abs(colIdx1 - colIdx2) == Math.abs(queenPositions[colIdx1] - queenPositions[colIdx2])) {
                    conflicts++;
                }
            }
        }
    
        // Test 3: Return fitness value
        // Each attack is counted twice (Q1 attacks Q2, Q2 attacks Q1)
        return 56.0 - conflicts * 2.0;
    }
    
    // Q6: Mutate a binary string by flipping one random bit
    public static String Q6_smallChange(String binarySolution) {
        // Test 1: Check for invalid input and return null
        if (binarySolution == null || binarySolution.isEmpty() || binarySolution.length() != 24 || !binarySolution.matches("[01]+")) {
            return null;
        }
    
        // Test 2: Flip a random bit and return the modified string
        java.util.Random randomGenerator = new java.util.Random();
        int flipIdx = randomGenerator.nextInt(24);
        char[] binaryArray = binarySolution.toCharArray();
        binaryArray[flipIdx] = (binaryArray[flipIdx] == '0') ? '1' : '0';
        return new String(binaryArray);
    }
    
    // Q7: Solve the Eight Queens problem using hill climbing
    public static String init(int maxIterations) {
        // Test 1: If iterations < 1, return ""
        if (maxIterations < 1) return "";
    
        // Start with a random solution
        String currentSolution = Q4_initialStart();
        Double currentFitnessScore = Q5_fitnessFunction(currentSolution);
    
        // If the initial solution is invalid, return null
        if (currentFitnessScore == null || currentFitnessScore == Double.MAX_VALUE) {
            return null;
        }
    
        // Hill climbing: iterate until max iterations or a perfect solution is found
        for (int attemptIdx = 0; attemptIdx < maxIterations && currentFitnessScore < 56.0; attemptIdx++) {
            String mutatedSolution = Q6_smallChange(currentSolution);
            // If the mutated solution is invalid, skip it
            if (mutatedSolution == null) continue;
    
            Double mutatedFitnessScore = Q5_fitnessFunction(mutatedSolution);
            // If the mutated fitness is invalid, skip it
            if (mutatedFitnessScore == null || mutatedFitnessScore == Double.MAX_VALUE) {
                continue;
            }
    
            // Accept the new solution if it's at least as good
            if (mutatedFitnessScore >= currentFitnessScore) {
                currentSolution = mutatedSolution;
                currentFitnessScore = mutatedFitnessScore;
            }
        }
    
        return currentSolution;
    }
    }