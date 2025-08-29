
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class NumberGameGUI extends JFrame {
    private int randomNumber;
    private int attempts;
    private final int maxAttempts = 10;
    private final int lowerBound = 1;
    private final int upperBound = 100;

    private JTextField guessField;
    private JButton guessButton, playAgainButton;
    private JTextArea messageArea;

    public NumberGameGUI() {
        setTitle(" Number Guessing Game");
        setSize(450, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Instructions at the top
        JLabel instructions = new JLabel("Guess a number between " + lowerBound + " and " + upperBound, JLabel.CENTER);
        instructions.setFont(new Font("Arial", Font.BOLD, 14));
        add(instructions, BorderLayout.NORTH);

        // Message area in the CENTER
        messageArea = new JTextArea(10, 35);
        messageArea.setEditable(false);
        messageArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        messageArea.setBorder(BorderFactory.createTitledBorder("Game Messages"));
        add(new JScrollPane(messageArea), BorderLayout.CENTER);

        // Bottom panel for input and buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(new JLabel("Enter your guess: "));
        guessField = new JTextField(5);
        bottomPanel.add(guessField);
        guessButton = new JButton("Guess");
        bottomPanel.add(guessButton);

        playAgainButton = new JButton("Play Again");
        playAgainButton.setEnabled(false);
        bottomPanel.add(playAgainButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // Start first game
        startNewGame();

        // Guess button action
        guessButton.addActionListener(e -> makeGuess());

        // Press Enter in text field = Guess
        guessField.addActionListener(e -> makeGuess());

        // Play Again button action
        playAgainButton.addActionListener(e -> startNewGame());
    }

    // Start or reset game
    private void startNewGame() {
        Random random = new Random();
        randomNumber = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
        attempts = 0;
        messageArea.setText("I have picked a number between " + lowerBound + " and " + upperBound + ".\n");
        messageArea.append("You have " + maxAttempts + " attempts. Good luck!\n");
        guessField.setText("");
        guessField.setEnabled(true);
        guessButton.setEnabled(true);
        playAgainButton.setEnabled(false);
    }

    // Handle guess
    private void makeGuess() {
        try {
            int guess = Integer.parseInt(guessField.getText());
            attempts++;

            if (guess < randomNumber) {
                messageArea.append("Attempt " + attempts + ": " + guess + " → Too low!\n");
            } else if (guess > randomNumber) {
                messageArea.append("Attempt " + attempts + ": " + guess + " → Too high!\n");
            } else {
                messageArea.append(" Correct! You guessed in " + attempts + " attempts.\n");
                endGame(true);
                return;
            }

            if (attempts >= maxAttempts) {
                messageArea.append(" No more attempts! The number was " + randomNumber + ".\n");
                endGame(false);
            }

            guessField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // End game and calculate score
    private void endGame(boolean guessedCorrectly) {
        int score = guessedCorrectly ? 100 - (attempts - 1) * 10 : 0;
        score = Math.max(score, 0);
        messageArea.append(" Your score: " + score + "\n");

        guessField.setEnabled(false);
        guessButton.setEnabled(false);
        playAgainButton.setEnabled(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NumberGameGUI().setVisible(true));
    }
}

