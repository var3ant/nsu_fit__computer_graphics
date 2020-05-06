package tests;

// GridBagStart.java
// ������ ����� � ������������� GridBagLayout
import java.awt.*;
import javax.swing.*;

public class GridBagStart extends JFrame {
    public GridBagStart() {
        super("GridBagStart");
        // ����� ��� �������� ����
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // ������������� ������������ �����������
        setLayout(new GridBagLayout());
        // ��������� ��� ������, ������ �� ���������
        // ��������� ������ ��� ���������� ����

        GridBagConstraints constraints1 =
                new GridBagConstraints();
        // ���������� ������ �� �����������
        constraints1.fill = GridBagConstraints.BOTH;
        // ������ ������ ��� ���������� ������
        constraints1.gridwidth =
                GridBagConstraints.REMAINDER;
        constraints1.weightx=0.2f;
        constraints1.weighty=0.2f;
        add(new JTextField(10), constraints1);
        // ������� ���� �� �����
        GridBagConstraints constraints2 =
                new GridBagConstraints();
        constraints2.fill = GridBagConstraints.BOTH;
        // ������ ������ ��� ���������� ������
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