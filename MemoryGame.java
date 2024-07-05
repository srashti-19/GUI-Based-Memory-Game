import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryGame extends JFrame implements ActionListener {
    private final JButton[] buttons = new JButton[16];
    private final String[] symbols = {"A", "B", "C", "D", "E", "F", "G", "H"};
    private String[] assignedSymbols = new String[16];
    private JButton firstButton, secondButton;
    private int moves = 0;

    public MemoryGame() {
        setTitle("Memory Game");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 4));

        List<String> symbolList = new ArrayList<>();
        for (String symbol : symbols) {
            symbolList.add(symbol);
            symbolList.add(symbol);
        }
        Collections.shuffle(symbolList);

        for (int i = 0; i < 16; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Arial", Font.BOLD, 24));
            buttons[i].setFocusPainted(false);
            buttons[i].addActionListener(this);
            assignedSymbols[i] = symbolList.get(i);
            add(buttons[i]);
        }

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        if (firstButton == null) {
            firstButton = clickedButton;
            revealSymbol(firstButton);
        } else if (secondButton == null && clickedButton != firstButton) {
            secondButton = clickedButton;
            revealSymbol(secondButton);
            Timer timer = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    checkMatch();
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void revealSymbol(JButton button) {
        int index = getButtonIndex(button);
        button.setText(assignedSymbols[index]);
    }

    private int getButtonIndex(JButton button) {
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i] == button) {
                return i;
            }
        }
        return -1;
    }

    private void checkMatch() {
        if (firstButton.getText().equals(secondButton.getText())) {
            firstButton.setEnabled(false);
            secondButton.setEnabled(false);
        } else {
            firstButton.setText("");
            secondButton.setText("");
        }
        firstButton = null;
        secondButton = null;
        moves++;
        checkGameEnd();
    }

    private void checkGameEnd() {
        boolean allMatched = true;
        for (JButton button : buttons) {
            if (button.isEnabled()) {
                allMatched = false;
                break;
            }
        }
        if (allMatched) {
            JOptionPane.showMessageDialog(this, "Congratulations! You've matched all pairs in " + moves + " moves.");
        }
    }

    public static void main(String[] args) {
        new MemoryGame();
    }
}
