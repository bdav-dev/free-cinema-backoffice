package ui.pages;

import java.awt.Dimension;
import java.awt.Toolkit;

import db.model.UserAccount;
import free_ui.Page;
import free_ui.components.DatatypeList;

@SuppressWarnings("serial")
public class UserAccountManagementPage extends Page {

    DatatypeList<UserAccount> userAccountList;
    
    public UserAccountManagementPage() {
    super();
    }

    @Override
    public void launch() {
    setSize(700, 500);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);
    setTitle("FREE CINEMA Backoffice: Benutzerverwaltung");
    setResizable(false);
    
    userAccountList = new DatatypeList<UserAccount>();
    userAccountList.setBounds(5, 5, 600, 400);
    addUIComponent(userAccountList);
    
    setVisible(true);
    }
}