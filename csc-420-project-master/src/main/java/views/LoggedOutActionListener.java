package views;

import models.CurrentUser;
import models.UserRestCallResult;
import org.omg.CORBA.Current;
import views.appViews.HomeFeedView;
import views.subviews.NavBarView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Andres on 12/8/16.
 */
public class LoggedOutActionListener implements ActionListener {
    JTextField username = new JTextField();
    JTextField password = new JPasswordField();

    Object[] loginObjects = {
            "Username:", username,
            "Password:", password
    };

    public LoggedOutActionListener() {
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        int option = JOptionPane.showConfirmDialog(null, loginObjects, "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            String userName = username.getText().trim();
            String pass = password.getText().trim();

            if (userName.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(null, "all fields must be filled", "Login failed", JOptionPane.PLAIN_MESSAGE);
            } else {
                UserRestCallResult result = CurrentUser.sharedInstance.attemptLogin(userName, pass);
                if (!result.getSuccess()) {
                    JOptionPane.showMessageDialog(null, result.getErrorMessage(), "Login failed", JOptionPane.PLAIN_MESSAGE);
                }
            }
        }
        username.setText("");
        password.setText("");
    }
}
