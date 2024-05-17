package serpientes_escaleras;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class SerpientesYEscaleras extends JFrame {
    private JButton rollDiceButton;
    private JTextArea eventLogTextArea;
    private JLabel currentPlayerLabel;
    private int currentPlayer;
    private int[] playerPositions;
    private int numPlayers;
    private int boardSize;
    private JButton[] boardButtons;
    private Map<Integer, Integer> snakesAndLadders;

    public SerpientesYEscaleras(int numPlayers, int boardSize) {
        this.numPlayers = numPlayers;
        this.boardSize = boardSize;
        this.currentPlayer = 0;
        this.playerPositions = new int[numPlayers];
        this.boardButtons = new JButton[boardSize * boardSize];
        this.snakesAndLadders = new HashMap<>();

        
        snakesAndLadders.put(2, 22);  
        snakesAndLadders.put(5, 8);   
        snakesAndLadders.put(15, 5);  
        snakesAndLadders.put(18, 11);
        snakesAndLadders.put(30, 1);  
        snakesAndLadders.put(25, 35); 
        snakesAndLadders.put(7, 38);  
        snakesAndLadders.put(50, 3);  
        snakesAndLadders.put(24, 60); 
        snakesAndLadders.put(70, 17); 
        snakesAndLadders.put(55, 80); 
        snakesAndLadders.put(98, 10); 

        setTitle("Serpientes y Escaleras");
        setSize(600, 600);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(boardSize, boardSize));
        add(boardPanel, BorderLayout.CENTER);

        for (int i = 0; i < boardSize * boardSize; i++) {
            boardButtons[i] = new JButton();
            boardButtons[i].setFont(new Font("Arial", Font.PLAIN, 10));
            boardButtons[i].setMargin(new Insets(0, 0, 0, 0));
            boardButtons[i].setEnabled(false);  
            boardPanel.add(boardButtons[i]);
        }

        rollDiceButton = new JButton("Lanzar Dado");
        add(rollDiceButton, BorderLayout.SOUTH);

        eventLogTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(eventLogTextArea);
        add(scrollPane, BorderLayout.EAST);

        currentPlayerLabel = new JLabel("Jugador Actual: 1");
        add(currentPlayerLabel, BorderLayout.NORTH);

        rollDiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int diceRoll = rollDice();
                int currentPosition = playerPositions[currentPlayer];
                int newPosition = currentPosition + diceRoll;

                if (newPosition >= boardSize * boardSize) {
                    newPosition = boardSize * boardSize - 1;
                }

                playerPositions[currentPlayer] = newPosition;

                eventLogTextArea.append("Jugador " + (currentPlayer + 1) + " lanzó un " + diceRoll + ". Avanza a la casilla " + (newPosition + 1) + ".\n");
                eventLogTextArea.setCaretPosition(eventLogTextArea.getDocument().getLength());

                checkSnakeAndLadder(newPosition);

                if (playerPositions[currentPlayer] == boardSize * boardSize - 1) {
                    JOptionPane.showMessageDialog(null, "¡Jugador " + (currentPlayer + 1) + " ha ganado!");
                    System.exit(0);
                }

                currentPlayer = (currentPlayer + 1) % numPlayers;
                currentPlayerLabel.setText("Jugador Actual: " + (currentPlayer + 1));
            }
        });

        updateBoard();
    }

    private int rollDice() {
        return (int) (Math.random() * 6) + 1;
    }

    private void checkSnakeAndLadder(int position) {
        if (snakesAndLadders.containsKey(position)) {
            int newPosition = snakesAndLadders.get(position);
            playerPositions[currentPlayer] = newPosition;

            eventLogTextArea.append("¡Jugador " + (currentPlayer + 1) + " ha encontrado una " +
                    (position < newPosition ? "escalera" : "serpiente") + "! Ahora está en la casilla " + (newPosition + 1) + ".\n");
            updateBoard();
        }
    }

    private void updateBoard() {
        for (int i = 0; i < boardSize * boardSize; i++) {
            boardButtons[i].setText("");  // Limpia las etiquetas de los botones
            boardButtons[i].setBackground(null); // Restablece el color de fondo
        }

        for (Map.Entry<Integer, Integer> entry : snakesAndLadders.entrySet()) {
            int start = entry.getKey();
            int end = entry.getValue();
            if (start < end) {
                boardButtons[start].setText(""); // Escalera
                boardButtons[start].setBackground(Color.GREEN); // Color para escaleras
            } else {
                boardButtons[start].setText(""); // Serpiente
                boardButtons[start].setBackground(Color.RED); // Color para serpientes
            }
        }

        for (int i = 0; i < numPlayers; i++) {
            int position = playerPositions[i];
            boardButtons[position].setText(boardButtons[position].getText() + " J" + (i + 1) + " ");
        }
    }

    public static void main(String[] args) {
        int numPlayers = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de jugadores (2-4):"));
        int boardSize = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el tamaño del tablero (10, 13, 15):"));

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SerpientesYEscaleras(numPlayers, boardSize).setVisible(true);
            }
        });
    }
}






