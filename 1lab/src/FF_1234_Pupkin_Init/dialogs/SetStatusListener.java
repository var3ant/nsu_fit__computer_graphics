package FF_1234_Pupkin_Init.dialogs;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SetStatusListener extends MouseAdapter {
    private final String message;
    private JLabel label;
    public SetStatusListener(JLabel _label, String _message) {
        message = _message;
        label = _label;
    }
    @Override
    public void mouseEntered(MouseEvent e){
        label.setText(message);
    }
    @Override
    public void mouseExited(MouseEvent e){
        label.setText("");
    }
}
