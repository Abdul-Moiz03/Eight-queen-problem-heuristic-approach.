public class EightQueensSolver {

    // Q1: Check if character is valid (either 'Q' or '.')
    public static boolean isValidSquareChar(Character ch) {
        return ch != null && (ch == 'Q' || ch == '.');
    }

    // Q2: Check if board is valid (8x8, valid characters, exactly 8 queens)
    public static boolean isValidBoard(Character[][] board) {
        if (board == null || board.length != 8) return false;

        int queenCount = 0;
        for (int i = 0; i < 8; i++) {
            if (board[i] == null || board[i].length != 8) return false;
            for (int j = 0; j < 8; j++) {
                if (!isValidSquareChar(board[i][j])) return false;
                if (board[i][j] == 'Q') queenCount++;
            }
        }
        return queenCount == 8;
    }

    // Q3: Convert board to binary string (3-bit per queen)
    public static String boardToBinary(Character[][] board) {
        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 'Q') {
                    String bin = String.format("%3s", Integer.toBinaryString(j)).replace(' ', '0');
                    binary.append(bin);
                    break;
                }
            }
        }
        return binary.toString();
    }

    // Q4: Generate initial random binary string
    public static String generateRandomBinaryBoard() {
        StringBuilder binary = new StringBuilder();
        java.util.Random rand = new java.util.Random();
        for (int i = 0; i < 8; i++) {
            int col = rand.nextInt(8);
            String bin = String.format("%3s", Integer.toBinaryString(col)).replace(' ', '0');
            binary.append(bin);
        }
        return binary.toString();
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
            System.out.println(isValidSquareChar('Q')); // true
            System.out.println(isValidSquareChar('.')); // true
            System.out.println(isValidSquareChar('x')); // false
            System.out.println(isValidSquareChar(null)); // false

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
            System.out.println(isValidBoard(validBoard)); // true

            // Test Q3 - boardToBinary()
            System.out.println("\nQ3: boardToBinary()");
            String binary = boardToBinary(validBoard);
            System.out.println("Binary: " + binary); // should be 24 bits
            System.out.println("Length: " + binary.length()); // should be 24

            // Test Q4 - generateRandomBinaryBoard()
            System.out.println("\nQ4: generateRandomBinaryBoard()");
            String randBoard = generateRandomBinaryBoard();
            System.out.println("Random Binary: " + randBoard);

            
        }

    }