// Main.java
package QUIZZY;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QuizApp app = new QuizApp();
            app.setVisible(true);
        });
    }
}
