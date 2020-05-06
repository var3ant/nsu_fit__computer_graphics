package tests;

// GridBagStart.java
// ѕервые опыты с расположением GridBagLayout
import java.awt.*;
import javax.swing.*;

public class GridBagStart extends JFrame {
    public GridBagStart() {
        super("GridBagStart");
        // выход при закрытии окна
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // устанавливаем расположение компонентов
        setLayout(new GridBagLayout());
        // добавл€ем две кнопки, €чейки по умолчанию
        // настройка €чейки дл€ текстового пол€

        GridBagConstraints constraints1 =
                new GridBagConstraints();
        // заполнение €чейки по горизонтали
        constraints1.fill = GridBagConstraints.BOTH;
        // просим зан€ть все оставшиес€ €чейки
        constraints1.gridwidth =
                GridBagConstraints.REMAINDER;
        constraints1.weightx=0.2f;
        constraints1.weighty=0.2f;
        add(new JTextField(10), constraints1);
        // выведем окно на экран
        GridBagConstraints constraints2 =
                new GridBagConstraints();
        constraints2.fill = GridBagConstraints.BOTH;
        // просим зан€ть все оставшиес€ €чейки
        constraints2.gridwidth =
                GridBagConstraints.REMAINDER;
        constraints2.weightx=0.8f;
        constraints2.weighty=0.8f;
        add(new JTextField(10), constraints2);
        setSize(400, 200);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        new GridBagStart();
                    }
                });
    }
}