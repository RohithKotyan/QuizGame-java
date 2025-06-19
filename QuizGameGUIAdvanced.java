import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class QuizGameGUIAdvanced extends JFrame implements ActionListener {
    String[] questions = {
        "Capital of India?",
        "Who invented Java?",
        "Keyword for inheritance in Java?",
        "Largest planet?",
        "Fastest land animal?",
        "Who wrote 'Hamlet'?",
        "Which element has the chemical symbol 'O'?",
        "How many continents are there?",
        "Prime Minister of India (2024)?",
        "First programmer in history?"
    };

    String[][] options = {
        {"Mumbai", "Delhi", "Chennai", "Kolkata"},
        {"James Gosling", "Dennis Ritchie", "Guido van Rossum", "Bjarne Stroustrup"},
        {"this", "implements", "extends", "import"},
        {"Earth", "Jupiter", "Mars", "Venus"},
        {"Cheetah", "Tiger", "Lion", "Panther"},
        {"Shakespeare", "Dickens", "Milton", "Tolkien"},
        {"Oxygen", "Gold", "Hydrogen", "Carbon"},
        {"5", "6", "7", "8"},
        {"Rahul Gandhi", "Narendra Modi", "Arvind Kejriwal", "Yogi Adityanath"},
        {"Ada Lovelace", "Alan Turing", "Charles Babbage", "Tim Berners-Lee"}
    };

    char[] answers = {'b', 'a', 'c', 'b', 'a', 'a', 'a', 'c', 'b', 'a'};
    char[] userAnswers = new char[10];

    int index = 0;
    int score = 0;
    int timeRemaining = 120; // 2 minutes in seconds

    JLabel questionLabel = new JLabel();
    JButton[] optionButtons = new JButton[4];
    JButton submitButton = new JButton("Submit");
    JButton prevButton = new JButton("Previous");
    JLabel statusLabel = new JLabel();
    JLabel timerLabel = new JLabel();

    Timer timer;

    public QuizGameGUIAdvanced() {
        setTitle("Advanced Quiz Game");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 1));

        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(questionLabel);

        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton();
            optionButtons[i].addActionListener(this);
            add(optionButtons[i]);
        }

        JPanel navPanel = new JPanel();
        navPanel.add(prevButton);
        navPanel.add(submitButton);
        add(navPanel);

        prevButton.addActionListener(e -> goToPrevious());
        submitButton.addActionListener(e -> submitAnswer());

        add(statusLabel);
        add(timerLabel);

        loadQuestion();
        startTimer();
        setVisible(true);
    }

    void loadQuestion() {
        if (index < questions.length) {
            questionLabel.setText("Q" + (index + 1) + ": " + questions[index]);
            for (int i = 0; i < 4; i++) {
                optionButtons[i].setText((char)('a' + i) + ") " + options[index][i]);
                optionButtons[i].setBackground(null);
            }
            updateStatus();
        }
    }

    void submitAnswer() {
        for (int i = 0; i < 4; i++) {
            if (optionButtons[i].getBackground() == Color.GREEN) {
                userAnswers[index] = (char) ('a' + i);
            }
        }
        if (index < questions.length - 1) {
            index++;
            loadQuestion();
        } else {
            endQuiz();
        }
    }

    void goToPrevious() {
        if (index > 0) {
            index--;
            loadQuestion();
        }
    }

    void updateStatus() {
        int attempted = 0;
        for (char c : userAnswers) {
            if (c != '\0') attempted++;
        }
        statusLabel.setText("Attempted: " + attempted + "/10");
    }

    void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                timeRemaining--;
                timerLabel.setText("Time left: " + timeRemaining + " seconds");
                if (timeRemaining <= 0) {
                    timer.cancel();
                    endQuiz();
                }
            }
        }, 1000, 1000);
    }

    void endQuiz() {
        for (int i = 0; i < 10; i++) {
            if (userAnswers[i] == answers[i]) {
                score++;
            }
        }
        dispose();
        showScorePage();
    }

    void showScorePage() {
        JFrame resultFrame = new JFrame("Quiz Results");
        resultFrame.setSize(400, 200);
        resultFrame.setLayout(new GridLayout(3, 1));

        JLabel result = new JLabel("🎉 Your Score: " + score + "/10", SwingConstants.CENTER);
        result.setFont(new Font("Arial", Font.BOLD, 20));
        resultFrame.add(result);

        JLabel message = new JLabel("Thanks for playing!", SwingConstants.CENTER);
        resultFrame.add(message);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        resultFrame.add(exitButton);

        resultFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 4; i++) {
            if (e.getSource() == optionButtons[i]) {
                for (JButton btn : optionButtons) {
                    btn.setBackground(null);
                }
                optionButtons[i].setBackground(Color.GREEN);
            }
        }
    }

    public static void main(String[] args) {
        new QuizGameGUIAdvanced();
    }
}
