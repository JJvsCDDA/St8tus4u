package gui;


import javax.security.auth.login.FailedLoginException;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

import controller.ApplicationController;


public class LoginFrame extends JFrame {
	private JButton btnSignUp;
	private JButton btnSignIn;
	private JLabel lblTitle;
	private JLabel lblBottom;
	private JPanel loginPanel;
	private JPasswordField txtPassword;
	private final String TXT_USERNAME_STANDARD_TEXT = "Username";
	private final String TXT_PASSWORD_STANDARD_TEXT = "Password";
	private JTextField txtUsername;
	private ApplicationController controller;
	private String username;
	private String password;
	
	public LoginFrame(ApplicationController controller) {
		this.controller = controller;
		initComponents();
	}

	private void initComponents() {
		this.setTitle("St8tus4U");
		loginPanel = new JPanel();
		txtUsername = new JTextField(TXT_USERNAME_STANDARD_TEXT);
		txtPassword = new JPasswordField(TXT_PASSWORD_STANDARD_TEXT);
		txtUsername.addMouseListener(new AutoEraseListener(TXT_USERNAME_STANDARD_TEXT, txtUsername));
		txtPassword.addMouseListener(new AutoEraseListener(TXT_PASSWORD_STANDARD_TEXT, txtPassword));
		btnSignUp = new JButton("Sign Up");
		btnSignIn = new JButton("Sign In");
		lblTitle = new JLabel("ST8TUS4U");
		lblBottom = new JLabel();
		btnSignUp.addActionListener(e -> loginFrameSignUp());
		btnSignIn.addActionListener(e -> loginFrameSignIn());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		StyleComponents.styleJPanel(loginPanel);
		StyleComponents.styleDefaultTextBox(txtUsername);
		StyleComponents.styleDefaultTextBox(txtPassword);
		StyleComponents.styleDefaultButton(btnSignUp);
		StyleComponents.styleDefaultButton(btnSignIn);
		StyleComponents.styleTitleLabel(lblTitle);
		StyleComponents.styleBottomLabel(lblBottom);
		
		// For reference: Related = small gap, Unrelated = medium gap, indent = big gap.
		GroupLayout loginPanelLayout = new GroupLayout(loginPanel); // GroupLayout has to be declared here, otherwise it
																	// doesn't show up???? 
		loginPanel.setLayout(loginPanelLayout);
		loginPanelLayout.setHorizontalGroup(loginPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(lblBottom, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(GroupLayout.Alignment.TRAILING, loginPanelLayout.createSequentialGroup().addGap(36, 36, 36)
						.addGroup(loginPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addGroup(GroupLayout.Alignment.LEADING,
										loginPanelLayout.createSequentialGroup()
												.addComponent(btnSignIn, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
												.addComponent(btnSignUp, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
								.addComponent(txtUsername).addComponent(txtPassword))
						.addGap(36, 36, 36)));
		loginPanelLayout.setVerticalGroup(loginPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(loginPanelLayout.createSequentialGroup().addGap(39, 39, 39).addComponent(lblTitle)
						.addGap(40, 40, 40)
						.addComponent(txtUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addGap(26, 26, 26)
						.addGroup(loginPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(btnSignIn, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnSignUp, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
						.addComponent(lblBottom, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)));

		// Creates an empty label with white background at the bottom and stretches it
		// over so it covers everything horizontally speaking
		GroupLayout bottomLayout = new GroupLayout(getContentPane());
		getContentPane().setLayout(bottomLayout);
		bottomLayout.setHorizontalGroup(bottomLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(
				loginPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE));
		bottomLayout.setVerticalGroup(bottomLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(loginPanel, GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE));

		pack();
	}

	@SuppressWarnings("deprecation")
	private void loginFrameSignIn() {
		username = this.txtUsername.getText();
		password = this.txtPassword.getText();
		this.setVisible(false);
		try {
			controller.logIn(username, password);
		} catch (FailedLoginException e) {
		}
	}

	private void loginFrameSignUp() {
		controller.openSignUpWindow();
	}
}
