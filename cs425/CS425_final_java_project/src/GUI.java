import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Vector;

import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import oracle.sql.DATE;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Properties;

public class GUI extends JFrame implements ActionListener
{
	public boolean Man1movPerm;
	public boolean Man2movPerm;
	public int count = 0;
	public int randomNum = (int)(Math.random() % 2147483640);
	
	User user = new User();
	Guest guest = new Guest();
	Admin admin = new Admin();
	Location location = new Location();
	MovieList movl = new MovieList();
	ShowingsList showing = new ShowingsList();
	
	//Test Variables
	String username = "test user";
	String password = "test pw";
	String test1 = "generic test string 1";
	String test2 = "generic test string 2";
	String test3 = "generic test string 3";
	String name = "test name";
	String ccn = "123465789";
	String phone = "123-456-7890";
	String email = "test@email.com";
	int points = 123;
	String status = "test status";
	String thread1 = "thread1";
	String thread2 = "thread2";
	String comment1 = "comment1";
	String comment2 = "comment2";
	
	Showing selected;
	
	Vector<Vector<String>> movieThreads = new Vector<Vector<String>>();
	Vector<Vector<String>> starThreads = new Vector<Vector<String>>();
	Vector<String> Movies = new Vector<String>();
	Vector<String> Theaters = new Vector<String>();
	Vector<String> Dates = new Vector<String>();
	
	//Main
	public static void main(String args[])
	{
		GUI app = new GUI();
	}

	//Frame
	JFrame frame;
	
	//Panels
	JPanel contentPanel = new JPanel();
	JPanel loginPanel = new JPanel();
	JPanel userTicketTab = new JPanel();
	JPanel userForumTab = new JPanel();
	JPanel userInfoTab = new JPanel();
	JPanel forumCreatePanel = new JPanel();
	JPanel guestInfoPanel = new JPanel();
	JPanel registerPanel = new JPanel();
	JPanel empInfoPanel = new JPanel();
	JPanel empAddShiftTab = new JPanel();
	JPanel empRemoveShiftTab = new JPanel();
	JPanel empSeeInfoTab = new JPanel();
	JPanel empAddMoviesTab = new JPanel();
	JPanel empRemoveMoviesTab = new JPanel();
	JPanel empPermissionsTab = new JPanel();
	JTabbedPane userTabbedPane = new JTabbedPane();
	JTabbedPane guestTabbedPane = new JTabbedPane();
	JTabbedPane empTabbedPane = new JTabbedPane();
	
	//Text
	JTextField ccvText;
	JTextField ccNameText;
	JTextField ccTypeText;
	JTextField ccExpText;
	JTextField street1Text;
	JTextField street2Text;
	JTextField cityText;
	JTextField stateText;
	JTextField zipText;
	JTextField usernameText;
	JTextField nameText;
	JTextField passwordText;
	JTextField ccnText;
	JTextField phoneText;
	JTextField emailText;
	JTextField pointsText;
	JTextField statusText;
	JTextField createThreadText;
	JTextField createCommentText;
	JTextField ticketPriceText;
	JTextField priceConfirmText;
	JTextField loginField;
	JTextField movieNameField;
	JTextField movieTimeField;
	JTextArea commentText;
	JPasswordField pwField;

	//Buttons
	JButton userLoginButton;
	JButton empLoginButton;
	JButton createUserButton;
	JButton guestLoginButton;
	JButton updateUserButton;
	JButton createCommentButton;
	JButton createThreadButton;
	JButton threadSubmitButton;
	JButton commentSubmitButton;
	JButton buyTicketButton;
	JButton guestPurchaseButton;
	JButton guestCancelButton;
	JButton registerUserButton;
	JButton addShiftButton;
	JButton removeShiftButton;
	JButton addMovieButton;
	JButton removeMovieButton;
	JButton seeInfoButton;
	JButton returnButton;
	JButton addPermissionButton;
	JButton removePermissionsButton;
	JRadioButton manager1RadioButton;
	JRadioButton manager2RadioButton;
	JRadioButton movieRadioButton;
	JRadioButton starRadioButton;
	JRadioButton createMovieThreadButton;
	JRadioButton createStarThreadButton;
	JRadioButton selectMovieRadioButton;
	JRadioButton selectTheaterRadioButton;
	JRadioButton viewByEmpRadioButton;
	JRadioButton viewByTheaterRadioButton;
	
	//Combo Boxes
	JComboBox<String> threadList;
	JComboBox<String> commentList;
	JComboBox<String> theaterList;
	JComboBox<String> movieList;
	JComboBox<String> dateList;
	JComboBox<String> ticketNumList;
	JComboBox<String> empShiftList;
	JComboBox<String> theaterShiftList;
	JComboBox<String> timeShiftList;
	JComboBox<String> typeShiftList;
	JComboBox<String> removeEmpList;
	JComboBox<String> removeShiftList;
	JComboBox<String> addMovieLocList;
	JComboBox<String> removeMovieLocList;
	JComboBox<String> removeMovieList;
	JComboBox<String> removeMovieDateList;
	JComboBox<String> removeMovieTimeList;
	JComboBox<String> guestInfoList;
	
	//Other
	Properties p1;
	UtilDateModel model1;
	JDatePanelImpl datePanel1;
	JDatePickerImpl datePicker1;
	Properties p2;
	UtilDateModel model2;
	JDatePanelImpl datePanel2;
	JDatePickerImpl datePicker2;
	File f;
	
	
	//Constructor
	GUI()
	{
		createFrame();
		createLoginPanel();
		
		//This is for testing purposes only.  Remove once DAOs are complete
		movieThreads.add(new Vector<String>());
		movieThreads.add(new Vector<String>());
		movieThreads.add(new Vector<String>());
		starThreads.add(new Vector<String>());
		starThreads.add(new Vector<String>());
		starThreads.add(new Vector<String>());
		
		movieThreads.get(0).add(thread1);
		movieThreads.get(0).add(thread2);
		starThreads.get(0).add(thread1);
		starThreads.get(0).add(thread2);
		
		movieThreads.get(1).add(comment1);
		movieThreads.get(2).add(comment2);
		starThreads.get(1).add(comment1);
		starThreads.get(2).add(comment2);
	}
	
	//Creates main frame object
	private void createFrame()
	{
		frame = new JFrame("CS 425 Final Project");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPanel = new JPanel(new CardLayout());
		
		contentPanel.setVisible(true);
		frame.setVisible(true);
		
		frame.add(contentPanel);
		
		f = new File("C:/Users/Ayesha Ahmed/Documents/CS425/Final Project/sqlhw_final_project_ahmed_warman_caron/temp.txt");
		//f = File.createTempFile("projPermissions", "txt");
		f.setWritable(true);
		f.setReadable(true);
	}
	
	//creates and displays login panel
	private void createLoginPanel()
	{
		loginPanel = new JPanel(new BorderLayout());
		
		JLabel panelLabel = new JLabel("Login or Click Guest Access");
		panelLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		createLoginButtons();
		createLoginField();
		createPWField();
		
		loginPanel.add(panelLabel,BorderLayout.NORTH);

		panelLabel.setVisible(true);
		
		loginPanel.setVisible(true);
		
		contentPanel.add(loginPanel, "Login Panel");
		frame.pack();
	}
	
	//creates login field portion of login panel
	private void createLoginField()
	{
		JPanel loginFieldPanel = new JPanel(new GridLayout(2,1));
		loginField = new JTextField(50);
	
		JLabel loginLabel = new JLabel("Username: ");
		loginLabel.setLabelFor(loginField);
		loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		loginFieldPanel.add(loginLabel);
		loginFieldPanel.add(loginField);
		
		loginField.setVisible(true);
		loginLabel.setVisible(true);
		loginFieldPanel.setVisible(true);		
		
		loginPanel.add(loginFieldPanel,BorderLayout.WEST);
	}
	
	//creates pw field portion of login panel
	private void createPWField()
	{	
		JPanel pwFieldPanel = new JPanel(new GridLayout(2,1));
		pwField = new JPasswordField(50);
		
		JLabel pwLabel = new JLabel("Password: ");
		pwLabel.setLabelFor(pwField);
		pwLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		pwFieldPanel.add(pwLabel);
		pwFieldPanel.add(pwField);

		pwFieldPanel.setVisible(true);
		pwField.setVisible(true);
		pwLabel.setVisible(true);
		
		loginPanel.add(pwFieldPanel,BorderLayout.EAST);
	}
	
	//creates button portion of login panel
	private void createLoginButtons()
	{
		userLoginButton = new JButton("User Login");
		empLoginButton = new JButton("Employee Login");
		createUserButton = new JButton("Create Account");
		guestLoginButton = new JButton("Enter as Guest");
		
		userLoginButton.addActionListener(this);
		empLoginButton.addActionListener(this);
		createUserButton.addActionListener(this);
		guestLoginButton.addActionListener(this);
		
		userLoginButton.setActionCommand("user login");
		empLoginButton.setActionCommand("emp login");
		createUserButton.setActionCommand("register user");
		guestLoginButton.setActionCommand("guest login");
		
		JPanel buttonPanel = new JPanel(new FlowLayout());
		
		buttonPanel.add(userLoginButton);
		buttonPanel.add(empLoginButton);
		buttonPanel.add(createUserButton);
		buttonPanel.add(guestLoginButton);
	
		userLoginButton.setVisible(true);
		empLoginButton.setVisible(true);
		createUserButton.setVisible(true);
		guestLoginButton.setVisible(true);
		buttonPanel.setVisible(true);
		
		loginPanel.add(buttonPanel,BorderLayout.SOUTH);
	}

	//creates user register panel
	private void createRegisterPanel()
	{
		registerPanel = new JPanel(new GridLayout(2,1));
		contentPanel.add(registerPanel, "Register Panel");
		
		JPanel userInfoMain = new JPanel(new GridLayout(7,4));
		JPanel userInfoButton = new JPanel();
		
		registerUserButton = new JButton("Create Account");
		registerUserButton.setActionCommand("create account");
		registerUserButton.addActionListener(this);
		
		JLabel usernameLabel = new JLabel("Username: ");
		usernameText = new JTextField("");
		
		JLabel passwordLabel = new JLabel("Password: ");
		passwordText = new JTextField("");
		
		JLabel nameLabel = new JLabel("Name: ");
		nameText = new JTextField("");
		
		JLabel phoneLabel = new JLabel("Phone: ");
		phoneText = new JTextField("");
		
		JLabel emailLabel = new JLabel("Email: ");
		emailText = new JTextField("");
		
		JLabel ccnLabel = new JLabel("CCN: ");
		ccnText = new JTextField("");
		
		JLabel ccvLabel = new JLabel("CCV: ");
		ccvText = new JTextField("");
		
		JLabel ccNameLabel = new JLabel("Name on CC: ");
		ccNameText = new JTextField("");
		
		JLabel ccExpLabel = new JLabel("CC Exp Date: ");
		ccExpText = new JTextField("");
		
		JLabel street1Label = new JLabel("Street 1: ");
		street1Text = new JTextField("");
		
		JLabel street2Label = new JLabel("Street 2: ");
		street2Text = new JTextField("");
		
		JLabel cityLabel = new JLabel("City: ");
		cityText = new JTextField("");
		
		JLabel stateLabel = new JLabel("State: ");
		stateText = new JTextField("");
		
		JLabel zipLabel = new JLabel("Zip Code: ");
		zipText = new JTextField("");
		
		userInfoMain.add(usernameLabel);
		userInfoMain.add(usernameText);
		userInfoMain.add(ccnLabel);
		userInfoMain.add(ccnText);
		userInfoMain.add(passwordLabel);
		userInfoMain.add(passwordText);
		userInfoMain.add(ccvLabel);
		userInfoMain.add(ccvText);
		userInfoMain.add(nameLabel);
		userInfoMain.add(nameText);
		userInfoMain.add(ccNameLabel);
		userInfoMain.add(ccNameText);
		userInfoMain.add(phoneLabel);
		userInfoMain.add(phoneText);
		userInfoMain.add(ccExpLabel);
		userInfoMain.add(ccExpText);
		userInfoMain.add(emailLabel);
		userInfoMain.add(emailText);
		userInfoMain.add(zipLabel);
		userInfoMain.add(zipText);
		userInfoMain.add(street1Label);
		userInfoMain.add(street1Text);
		userInfoMain.add(street2Label);
		userInfoMain.add(street2Text);
		userInfoMain.add(cityLabel);
		userInfoMain.add(cityText);
		userInfoMain.add(stateLabel);
		userInfoMain.add(stateText);

		
		userInfoButton.add(registerUserButton,SwingConstants.CENTER);
		
		userInfoMain.setVisible(true);
		userInfoButton.setVisible(true);
		
		registerPanel.add(userInfoMain);
		registerPanel.add(userInfoButton);
	}
	
	//creates tabbed pane for emp login
	private void createEmpTabs() throws SQLException, FileNotFoundException
	{
		empTabbedPane = new JTabbedPane();
		contentPanel.add(empTabbedPane,"Emp Tabs");
		
		empAddShiftTab = new JPanel(new GridLayout(3,1));
		createAddEmpShiftTab();
		empSeeInfoTab.setEnabled(false);
		
		empRemoveShiftTab = new JPanel(new GridLayout(3,1));
		createRemoveShiftTab();
		
		empSeeInfoTab = new JPanel(new GridLayout(3,1));
		createEmpSeeInfoTab();
	
		empAddMoviesTab = new JPanel(new GridLayout(3,1));
		createAddMoviesTab();
		
		empRemoveMoviesTab = new JPanel(new GridLayout(3,1));
		createRemoveMoviesTab();
		
		empPermissionsTab = new JPanel(new GridLayout(2,1));
		createPermissionsTab();
		
		empTabbedPane.setVisible(false);
	}
	
	private void createPermissionsTab()
	{
		JPanel rp = new JPanel(new GridLayout(1,2));
		
		manager1RadioButton = new JRadioButton("Manager 1");
		manager2RadioButton = new JRadioButton("Manager 2");
		ButtonGroup b = new ButtonGroup();
		b.add(manager1RadioButton);
		b.add(manager2RadioButton);
		rp.add(manager1RadioButton);
		rp.add(manager2RadioButton);
		
		JPanel bp = new JPanel(new GridLayout(1,2));
		addPermissionButton = new JButton("Add Permission");
		addPermissionButton.setActionCommand("add permission");
		addPermissionButton.addActionListener(this);
		removePermissionsButton = new JButton("Remove All Permissions");
		removePermissionsButton.setActionCommand("remove permissions");
		removePermissionsButton.addActionListener(this);
		bp.add(addPermissionButton);
		bp.add(removePermissionsButton);
		
		empPermissionsTab.add(rp);
		empPermissionsTab.add(bp);
		if(!admin.type.equalsIgnoreCase("owner"))
		{
			addPermissionButton.setEnabled(false);
			removePermissionsButton.setEnabled(false);
		}
		
		empTabbedPane.add("Permissions",empPermissionsTab);
	}
	
	//creates tabbed pane for guest login
	private void createGuestTabs()
	{
		guestTabbedPane = new JTabbedPane();
		contentPanel.add(guestTabbedPane,"Guest Tabs");
		
		userTicketTab = new JPanel();
		createGuestTicketTab();
		
		userForumTab = new JPanel(new GridLayout(2,1));
		createGuestForumTab();
		
		guestTabbedPane.setVisible(false);
	}
	
	//creates tabbed pane for user login
	private void createUserTabs()
	{
		userTabbedPane = new JTabbedPane();
		contentPanel.add(userTabbedPane,"User Tabs");	
		
		userInfoTab = new JPanel(new GridLayout(2,1));
		createUserInfoTab();
		
		userTicketTab = new JPanel();
		createUserTicketTab();
		
		userForumTab = new JPanel(new GridLayout(2,1));
		createUserForumTab();
		
		userTabbedPane.setVisible(false);
	}
	
	private void createAddEmpShiftTab() throws SQLException
	{
		JPanel lp = new JPanel(new GridLayout(1,5));
		lp.add(new JLabel("Employee ID"));
		lp.add(new JLabel("Type"));
		lp.add(new JLabel("Location"));
		lp.add(new JLabel("Date"));
		lp.add(new JLabel("Time"));
		
		JPanel cp = new JPanel(new GridLayout(1,5));
			
		empShiftList = new JComboBox<String>();
		empShiftList.removeAllItems();
		for(int i=0;i<admin.emps.size();i++)
		{
			empShiftList.addItem(Integer.toString(admin.emps.get(i).id));
		}
		empShiftList.setSelectedIndex(-1);
		empShiftList.setActionCommand("chose shift emp");
		empShiftList.addActionListener(this);
		cp.add(empShiftList);
		
		typeShiftList = new JComboBox<String>();
		typeShiftList.setSelectedIndex(-1);
		typeShiftList.setEnabled(false);
		typeShiftList.setActionCommand("chose shift type");
		typeShiftList.addActionListener(this);
		cp.add(typeShiftList);
		
		theaterShiftList = new JComboBox<String>();
		
//		//TODO: Fill theaterShiftList with all possible theaters
//		//testing only 
		
		if(admin.type.equalsIgnoreCase("owner"))
		{
			int j, size = location.locations == null ? 0 : location.locations.size();
			for(j = 0; j<size;j++){
				theaterShiftList.addItem(Integer.toString(location.locations.get(j).th_id));
			}
		}
		else if(admin.type.equalsIgnoreCase("web admin"))
		{
			theaterShiftList.setEnabled(false);
		}
		else theaterShiftList.addItem(new Integer(admin.theater).toString());
		
		theaterShiftList.setSelectedIndex(-1);
		theaterShiftList.setActionCommand("chose shift theater");
		theaterShiftList.addActionListener(this);
		cp.add(theaterShiftList);
		
		model1 = new UtilDateModel();
		Date d = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		model1.setDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));	
		p1 = new Properties();
		p1.put("text.today", "Today");
		p1.put("text.month", "Month");
		p1.put("text.year", "Year");
		datePanel1 = new JDatePanelImpl(model1, p1);
		datePicker1 = new JDatePickerImpl(datePanel1,new DateLabelFormatter());
		cp.add(datePicker1);
		
		timeShiftList = new JComboBox<String>();
		timeShiftList.addItem("9am-12pm");
		timeShiftList.addItem("12pm-3pm");
		timeShiftList.addItem("3pm-6pm");
		timeShiftList.addItem("6pm-9pm");
		timeShiftList.addItem("9pm-12am");
		timeShiftList.setSelectedIndex(-1);
		timeShiftList.setActionCommand("chose shift time");
		timeShiftList.addActionListener(this);
		cp.add(timeShiftList);
		
		JPanel bp = new JPanel(new GridLayout(1,3));

		addShiftButton = new JButton("Add Shift");
		addShiftButton.setActionCommand("add shift");
		addShiftButton.addActionListener(this);
		bp.add(new JPanel());
		bp.add(addShiftButton);
		bp.add(new JPanel());
		
		empAddShiftTab.add(lp);
		empAddShiftTab.add(cp);
		empAddShiftTab.add(bp);
		
		empTabbedPane.add(empAddShiftTab, "Add Shift");
	}
	
	private void createRemoveShiftTab()
	{
		JPanel lp = new JPanel(new GridLayout(1,2));
		lp.add(new JLabel("Employee"));
		lp.add(new JLabel("Shift"));
		
		JPanel cp = new JPanel(new GridLayout(1,2));
		
		removeEmpList = new JComboBox<String>();
		for(int i=0;i<admin.emps.size();i++)
		{
			removeEmpList.addItem(Integer.toString(admin.emps.get(i).id));
		}
		removeEmpList.setSelectedIndex(-1);
		removeEmpList.setActionCommand("chose remove emp");
		removeEmpList.addActionListener(this);
		cp.add(removeEmpList);
		
		removeShiftList = new JComboBox<String>();
		removeShiftList.setSelectedIndex(-1);
		removeShiftList.setEnabled(false);
		cp.add(removeShiftList);
		
		JPanel bp = new JPanel(new GridLayout(1,3));
		removeShiftButton = new JButton("Remove Shift");
		removeShiftButton.setActionCommand("remove shift");
		removeShiftButton.addActionListener(this);
		bp.add(new JPanel());
		bp.add(removeShiftButton);
		bp.add(new JPanel());
		
		empRemoveShiftTab.add(lp);
		empRemoveShiftTab.add(cp);
		empRemoveShiftTab.add(bp);
		
		empTabbedPane.add(empRemoveShiftTab, "Remove Shift");
		
	}
	
	private void createEmpSeeInfoTab()
	{
		if(admin.type.equalsIgnoreCase("web admin") || admin.type.equalsIgnoreCase("owner"))
		{
			JLabel l = new JLabel("Guest Username");
			
			guestInfoList = new JComboBox<String>();
			for(int i=0;i<admin.users.size();i++)
			{
				guestInfoList.addItem(admin.users.get(i));
			}
			guestInfoList.setSelectedIndex(-1);
			
			seeInfoButton = new JButton("View Info");
			seeInfoButton.setActionCommand("Admin See User");
			seeInfoButton.addActionListener(this);
			
			empSeeInfoTab.add(l);
			empSeeInfoTab.add(guestInfoList);
			empSeeInfoTab.add(seeInfoButton);
			
		}
		else
		{
			empSeeInfoTab.add(new JLabel("Unable to access"));
		}

		empTabbedPane.add(empSeeInfoTab,"User Info");
	}
	
	private void createEmpInfoPanel()
	{
		empInfoPanel = new JPanel(new GridLayout(2,1));
		
		JPanel userInfoMain = new JPanel(new GridLayout(8,4));
		
		user = admin.viewUser((String)guestInfoList.getSelectedItem());
		
		JLabel usernameLabel = new JLabel("Username: ");
		usernameText = new JTextField(user.username);
		usernameText.setEnabled(false);
		
		JLabel passwordLabel = new JLabel("Password: ");
		passwordText = new JTextField(user.password);
		passwordText.setEnabled(false);
		
		JLabel nameLabel = new JLabel("Name: ");
		nameText = new JTextField(user.name);
		nameText.setEnabled(false);
		
		JLabel phoneLabel = new JLabel("Phone: ");
		phoneText = new JTextField(user.phone);
		phoneText.setEnabled(false);
		
		JLabel emailLabel = new JLabel("Email: ");
		emailText = new JTextField(user.email);
		emailText.setEnabled(false);
		
		JLabel pointsLabel = new JLabel("Reward Points: ");
		pointsText = new JTextField(String.valueOf(user.curPoints));
		pointsText.setEnabled(false);
		
		JLabel statusLabel = new JLabel("Reward Level: ");
		statusText = new JTextField(user.rewardLevel);
		statusText.setEnabled(false);
		
		JLabel ccnLabel = new JLabel("CCN: ");
		ccnText = new JTextField(user.ccn.ccn);
		ccnText.setEnabled(false);
		
		JLabel ccvLabel = new JLabel("CVV: ");
		ccvText = new JTextField(user.ccn.cvv);
		ccvText.setEnabled(false);
		
		JLabel ccNameLabel = new JLabel("Name on CC: ");
		ccNameText = new JTextField(user.ccn.name);
		ccNameText.setEnabled(false);
		
		JLabel ccExpLabel = new JLabel("CC Exp Date: ");
		ccExpText = new JTextField(user.ccn.expDate.toString());
		ccExpText.setEnabled(false);
		
		JLabel street1Label = new JLabel("Street 1: ");
		street1Text = new JTextField(user.ccn.street1);
		street1Text.setEnabled(false);
		
		JLabel street2Label = new JLabel("Street 2: ");
		street2Text = new JTextField(user.ccn.street2);
		street2Text.setEnabled(false);
		
		JLabel cityLabel = new JLabel("City: ");
		cityText = new JTextField(user.ccn.city);
		cityText.setEnabled(false);
		
		JLabel stateLabel = new JLabel("State: ");
		stateText = new JTextField(user.ccn.state);
		stateText.setEnabled(false);
		
		JLabel zipLabel = new JLabel("Zip Code: ");
		zipText = new JTextField(user.ccn.zip);
		zipText.setEnabled(false);
		
		userInfoMain.add(usernameLabel);
		userInfoMain.add(usernameText);
		userInfoMain.add(ccnLabel);
		userInfoMain.add(ccnText);
		userInfoMain.add(passwordLabel);
		userInfoMain.add(passwordText);
		userInfoMain.add(ccvLabel);
		userInfoMain.add(ccvText);
		userInfoMain.add(nameLabel);
		userInfoMain.add(nameText);
		userInfoMain.add(ccNameLabel);
		userInfoMain.add(ccNameText);
		userInfoMain.add(phoneLabel);
		userInfoMain.add(phoneText);
		userInfoMain.add(ccExpLabel);
		userInfoMain.add(ccExpText);
		userInfoMain.add(emailLabel);
		userInfoMain.add(emailText);
		userInfoMain.add(zipLabel);
		userInfoMain.add(zipText);
		userInfoMain.add(pointsLabel);
		userInfoMain.add(pointsText);
		userInfoMain.add(statusLabel);
		userInfoMain.add(statusText);
		userInfoMain.add(street1Label);
		userInfoMain.add(street1Text);
		userInfoMain.add(street2Label);
		userInfoMain.add(street2Text);
		userInfoMain.add(cityLabel);
		userInfoMain.add(cityText);
		userInfoMain.add(stateLabel);
		userInfoMain.add(stateText);

		JPanel b = new JPanel(new GridLayout(1,3));
		b.add(new JPanel());
		returnButton = new JButton("Done");
		returnButton.setActionCommand("info ok");
		returnButton.addActionListener(this);
		b.add(returnButton);
		b.add(new JPanel());
		
		empInfoPanel.add(userInfoMain);
		empInfoPanel.add(b);
		
		contentPanel.add(empInfoPanel,"Emp Info Panel");
	}
	
	private void createAddMoviesTab() throws FileNotFoundException
	{
		boolean man1 = false;
		boolean man2 =  false;
		if(f.exists())
		{
			f.setWritable(true);
			f.setReadable(true);
			char[] c = new char[1000];
			BufferedReader in = new BufferedReader(new FileReader(f));
			try {
				int i = in.read(c);
				String str = new String(c);
				System.out.println(str);
				if(!str.isEmpty())
				{

					
						if(str.contains("manager1"))
						{
							man1=true;
						}
						if(str.contains("manager2"))
						{
							man2=true;
						}
					

				}
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	if(admin.type.equalsIgnoreCase("owner") || (admin.username.equalsIgnoreCase("manage1tcbt")&&man1) || (man2&&admin.username.equalsIgnoreCase("manage2tcbt")))	
	{
		JPanel lp = new JPanel(new GridLayout(1,4));
		lp.add(new JLabel("Movie"));
		lp.add(new JLabel("Theater"));
		lp.add(new JLabel("Date"));
		lp.add(new JLabel("Time"));
		
		JPanel cp = new JPanel(new GridLayout(1,4));
		
		movieNameField = new JTextField();
		cp.add(movieNameField);
		
		addMovieLocList = new JComboBox<String>();
		//TODO: Populate list w/all movie locations
		int j, size = location.locations == null ? 0 : location.locations.size();
		for(j = 0; j<size;j++){
			addMovieLocList.addItem(Integer.toString(location.locations.get(j).th_id));
		}
//		addMovieLocList.addItem("1");
//		addMovieLocList.addItem("2");
		addMovieLocList.setSelectedItem(-1);
		cp.add(addMovieLocList);
		
		model2 = new UtilDateModel();
		Date d = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		model2.setDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));	
		p2 = new Properties();
		p2.put("text.today", "Today");
		p2.put("text.month", "Month");
		p2.put("text.year", "Year");
		datePanel2 = new JDatePanelImpl(model2, p2);
		datePicker2 = new JDatePickerImpl(datePanel2,new DateLabelFormatter());
		cp.add(datePicker2);
		
		movieTimeField = new JTextField();
		cp.add(movieTimeField);
		
		JPanel bp = new JPanel(new GridLayout(1,3));
		bp.add(new JPanel());
		addMovieButton = new JButton("Add Movie");
		addMovieButton.setActionCommand("add movie");
		addMovieButton.addActionListener(this);
		addMovieButton.setEnabled(true);
		addMovieButton.setVisible(true);
		bp.add(addMovieButton);
		bp.add(new JPanel());
		
		
		empAddMoviesTab.add(lp);
		empAddMoviesTab.add(cp);
		empAddMoviesTab.add(bp);
	}
	else
	{
		empAddMoviesTab.add(new JLabel("Invalid Permissions"));
	}
		empTabbedPane.add(empAddMoviesTab,"Add Movie");
	
	}
	
	private void createRemoveMoviesTab()
	{
		JPanel lp = new JPanel(new GridLayout(1,4));
		lp.add(new JLabel("Movie"));
		lp.add(new JLabel("Theater"));
		lp.add(new JLabel("Date"));
		lp.add(new JLabel("Time"));
		
		JPanel cp = new JPanel(new GridLayout(1,4));
		
		removeMovieList = new JComboBox<String>();
		//TODO: populate removeMovieList with all currently playing movies (just title is fine)
		for (int i = 0; i<showing.schedule.size(); i++){
			removeMovieList.addItem(showing.schedule.get(i).title);
		}
		removeMovieList.setActionCommand("chose remove movie");
		removeMovieList.setSelectedIndex(-1);
		removeMovieList.addActionListener(this);
		
		removeMovieLocList = new JComboBox<String>();
		removeMovieLocList.setEnabled(false);
		removeMovieLocList.setActionCommand("chose remove location");
		removeMovieLocList.addActionListener(this);
		
		removeMovieDateList = new JComboBox<String>();
		removeMovieDateList.setEnabled(false);
		removeMovieDateList.setActionCommand("chose remove date");
		removeMovieDateList.addActionListener(this);
		
		removeMovieTimeList = new JComboBox<String>();
		removeMovieTimeList.setEnabled(false);
		removeMovieTimeList.setActionCommand("chose remove time");
		removeMovieTimeList.addActionListener(this);
		
		empTabbedPane.add(empRemoveMoviesTab, "Remove Movie");
	}
		
	//creates guest ticket tab from modified user ticket tab
	private void createGuestTicketTab()
	{
		createUserTicketTab();
		buyTicketButton.setActionCommand("buy guest ticket");
	
		guestTabbedPane.addTab("Tickets",userTicketTab);
	}
	
	//creates guest forum tab from modified user forum tab
	private void createGuestForumTab()
	{
		createUserForumTab();

		createCommentButton.setEnabled(false);
		createThreadButton.setEnabled(false);

		guestTabbedPane.addTab("Forum",userForumTab);
	}

	//creates guest info panel
	private void createGuestInfoPanel()
	{
		guestInfoPanel = new JPanel(new GridLayout(2,1));
		
		JPanel guestInfoMain = new JPanel(new GridLayout(5,4));
		JPanel guestInfoExtras = new JPanel(new GridLayout(1,3));
		
		priceConfirmText = new JTextField(ticketPriceText.getText());
		priceConfirmText.setEnabled(false);
		
		guestPurchaseButton = new JButton("Confirm Purchase");
		guestPurchaseButton.setActionCommand("confirm guest purchase");
		guestPurchaseButton.addActionListener(this);
		
		guestCancelButton = new JButton("Cancel");
		guestCancelButton.setActionCommand("cancel guest purchase");
		guestCancelButton.addActionListener(this);
		
		JLabel emailLabel = new JLabel("Email: ");
		emailText = new JTextField("");
		
		JLabel ccnLabel = new JLabel("CCN: ");
		ccnText = new JTextField("");
		
		JLabel ccvLabel = new JLabel("CCV: ");
		ccvText = new JTextField("");
		
		JLabel ccNameLabel = new JLabel("Name on CC: ");
		ccNameText = new JTextField("");
		
		JLabel ccExpLabel = new JLabel("CC Exp Date: ");
		ccExpText = new JTextField("");
		
		JLabel street1Label = new JLabel("Street 1: ");
		street1Text = new JTextField("");
		
		JLabel street2Label = new JLabel("Street 2: ");
		street2Text = new JTextField("");
		
		JLabel cityLabel = new JLabel("City: ");
		cityText = new JTextField("");
		
		JLabel stateLabel = new JLabel("State: ");
		stateText = new JTextField("");
		
		JLabel zipLabel = new JLabel("Zip Code: ");
		zipText = new JTextField("");
		
		guestInfoMain.add(ccnLabel);
		guestInfoMain.add(ccnText);
		guestInfoMain.add(ccvLabel);
		guestInfoMain.add(ccvText);
		guestInfoMain.add(ccNameLabel);
		guestInfoMain.add(ccNameText);
		guestInfoMain.add(ccExpLabel);
		guestInfoMain.add(ccExpText);
		guestInfoMain.add(zipLabel);
		guestInfoMain.add(zipText);
		guestInfoMain.add(street1Label);
		guestInfoMain.add(street1Text);
		guestInfoMain.add(street2Label);
		guestInfoMain.add(street2Text);
		guestInfoMain.add(cityLabel);
		guestInfoMain.add(cityText);
		guestInfoMain.add(stateLabel);
		guestInfoMain.add(stateText);
		guestInfoMain.add(emailLabel);
		guestInfoMain.add(emailText);

		
		guestInfoExtras.add(priceConfirmText);
		guestInfoExtras.add(guestPurchaseButton);
		guestInfoExtras.add(guestCancelButton);
		
		guestInfoMain.setVisible(true);
		guestInfoExtras.setVisible(true);
		
		guestInfoPanel.add(guestInfoMain);
		guestInfoPanel.add(guestInfoExtras);

		guestInfoPanel.setVisible(true);
		contentPanel.add(guestInfoPanel,"Guest Info");
	}
	
	//creates user ticket tab
	private void createUserTicketTab()
	{
		userTabbedPane.addTab("Tickets", userTicketTab);
		userTicketTab.setVisible(true);
		
		userTicketTab.setLayout(new GridLayout(4,3));
		
		JPanel radioPanel = new JPanel(new GridLayout(1,2));
		
		selectMovieRadioButton = new JRadioButton("Select By Movie");
		selectMovieRadioButton.setActionCommand("select by movie");
		selectMovieRadioButton.addActionListener(this);
		
		selectTheaterRadioButton = new JRadioButton("Select By Theater");
		selectTheaterRadioButton.setActionCommand("select by theater");
		selectTheaterRadioButton.addActionListener(this);
		
		ButtonGroup group = new ButtonGroup();
		group.add(selectMovieRadioButton);
		group.add(selectTheaterRadioButton);
		
		JLabel theaterLabel = new JLabel("Theaters: ",SwingConstants.CENTER);
		
		theaterList = new JComboBox<String>();
		theaterList.setActionCommand("chose theater");
		theaterList.addActionListener(this);
		
		//TESTING ONLY
		theaterList.addItem(test1);
		theaterList.addItem(test2);
		
		JLabel movieLabel = new JLabel("Movies: ",SwingConstants.CENTER);
		
		movieList = new JComboBox<String>();
		movieList.setActionCommand("chose movie");
		movieList.addActionListener(this);
		
		//TESTING ONLY
		movieList.addItem(test2);
		movieList.addItem(test1);
		
		JLabel dateLabel = new JLabel("Dates & Times: ",SwingConstants.CENTER);
		
		dateList = new JComboBox<String>();
		dateList.setActionCommand("chose date");
		dateList.addActionListener(this);
		
		//TESTING ONLY
		dateList.addItem(test3);
		
		buyTicketButton = new JButton("Buy Ticket");
		buyTicketButton.setActionCommand("buy ticket");
		buyTicketButton.addActionListener(this);
		
		String[] s = {"1","2","3","4","5","6","7","8","9"};
		JLabel ticketNumLabel = new JLabel("Number of Tickets: ");
		ticketNumList = new JComboBox<String>(s);
		ticketNumList.setActionCommand("chose num tickets");
		ticketNumList.addActionListener(this);
		JPanel ticketNumPanel = new JPanel(new GridLayout(1,2));
		ticketNumPanel.add(ticketNumLabel);
		ticketNumPanel.add(ticketNumList);
		
		ticketPriceText = new JTextField();
		ticketPriceText.setEnabled(false);
		ticketPriceText.setText("");
		
		radioPanel.add(selectMovieRadioButton);
		radioPanel.add(selectTheaterRadioButton);
		userTicketTab.add(new JPanel());
		userTicketTab.add(radioPanel);
		userTicketTab.add(new JPanel());
		userTicketTab.add(theaterLabel);
		userTicketTab.add(movieLabel);
		userTicketTab.add(dateLabel);
		userTicketTab.add(theaterList);
		userTicketTab.add(movieList);
		userTicketTab.add(dateList);
		userTicketTab.add(ticketNumPanel);
		userTicketTab.add(ticketPriceText);
		userTicketTab.add(buyTicketButton);
		
		theaterList.setEnabled(false);
		movieList.setEnabled(false);
		dateList.setEnabled(false);
		
		dateList.setSelectedIndex(-1);
		movieList.setSelectedIndex(-1);
		theaterList.setSelectedIndex(-1);
	}
	
	//creates user forum tab
	private void createUserForumTab()
	{
		JPanel forumChoicesPanel = new JPanel(new GridLayout(4,1));
		JPanel forumButtonPanel = new JPanel();
		JPanel forumPartsPanel = new JPanel(new GridLayout(1,2));
		
		commentText = new JTextArea();
		commentText.setEditable(false);
		JScrollPane scrollText = new JScrollPane(commentText);
		
		threadList = new JComboBox<String>();
		threadList.setActionCommand("thread selected");
		threadList.addActionListener(this);
		threadList.setEnabled(false);
		threadList.setSelectedIndex(-1);
		
		commentList = new JComboBox<String>();
		commentList.setActionCommand("comment selected");
		commentList.addActionListener(this);
		commentList.setEnabled(false);
		
		JScrollPane threadScroll = new JScrollPane(threadList);
		JScrollPane commentScroll = new JScrollPane(commentList);
		
		movieRadioButton = new JRadioButton("Movie Threads");
		movieRadioButton.setActionCommand("movie threads");
		movieRadioButton.addActionListener(this);
		
		starRadioButton = new JRadioButton("Star Threads");
		starRadioButton.setActionCommand("star threads");
		starRadioButton.addActionListener(this);
		
		ButtonGroup threadsGroup = new ButtonGroup();
		threadsGroup.add(movieRadioButton);
		threadsGroup.add(starRadioButton);
		
		forumChoicesPanel.add(movieRadioButton);
		forumChoicesPanel.add(starRadioButton);
		forumChoicesPanel.add(threadScroll);
		forumChoicesPanel.add(commentScroll);
		
		createThreadButton = new JButton("Create Thread");
		createThreadButton.setActionCommand("create thread");
		createThreadButton.addActionListener(this);
		
		createCommentButton = new JButton("Create Comment");
		createCommentButton.setActionCommand("create comment");
		createCommentButton.addActionListener(this);
		
		forumButtonPanel.add(createThreadButton);
		forumButtonPanel.add(createCommentButton);
		
		movieRadioButton.setVisible(true);
		starRadioButton.setVisible(true);
		commentText.setVisible(true);
		createThreadButton.setVisible(true);
		createCommentButton.setVisible(true);
		
		forumChoicesPanel.setVisible(true);
		forumButtonPanel.setVisible(true);
		
		forumPartsPanel.add(forumChoicesPanel);
		forumPartsPanel.add(scrollText);
		userForumTab.add(forumPartsPanel);
		userForumTab.add(forumButtonPanel);
		
		userTabbedPane.addTab("Forum", userForumTab);	
		userForumTab.setVisible(true);
	}
	
	//creates user info tab
	private void createUserInfoTab()
	{
		JPanel userInfoMain = new JPanel(new GridLayout(8,4));
		JPanel userInfoButton = new JPanel();
		
		updateUserButton = new JButton("Update Info");
		updateUserButton.setActionCommand("user updates info");
		updateUserButton.addActionListener(this);
		
		JLabel usernameLabel = new JLabel("Username: ");
		usernameText = new JTextField(user.username);
		usernameText.setEnabled(false);
		
		JLabel passwordLabel = new JLabel("Password: ");
		passwordText = new JTextField(user.password);
		
		JLabel nameLabel = new JLabel("Name: ");
		nameText = new JTextField(user.name);
		nameText.setEnabled(false);
		
		JLabel phoneLabel = new JLabel("Phone: ");
		phoneText = new JTextField(user.phone);
		
		JLabel emailLabel = new JLabel("Email: ");
		emailText = new JTextField(user.email);
		
		JLabel pointsLabel = new JLabel("Reward Points: ");
		pointsText = new JTextField(String.valueOf(user.curPoints));
		pointsText.setEnabled(false);
		
		JLabel statusLabel = new JLabel("Reward Level: ");
		statusText = new JTextField(user.rewardLevel);
		statusText.setEnabled(false);
		
		JLabel ccnLabel = new JLabel("CCN: ");
		ccnText = new JTextField(user.ccn.ccn);
		
		JLabel ccvLabel = new JLabel("CVV: ");
		ccvText = new JTextField(user.ccn.cvv);
		
		JLabel ccNameLabel = new JLabel("Name on CC: ");
		ccNameText = new JTextField(user.ccn.name);
		
		JLabel ccExpLabel = new JLabel("CC Exp Date: ");
		ccExpText = new JTextField(user.ccn.expDate.toString());
		
		JLabel street1Label = new JLabel("Street 1: ");
		street1Text = new JTextField(user.ccn.street1);
		
		JLabel street2Label = new JLabel("Street 2: ");
		street2Text = new JTextField(user.ccn.street2);
		
		JLabel cityLabel = new JLabel("City: ");
		cityText = new JTextField(user.ccn.city);
		
		JLabel stateLabel = new JLabel("State: ");
		stateText = new JTextField(user.ccn.state);
		
		JLabel zipLabel = new JLabel("Zip Code: ");
		zipText = new JTextField(user.ccn.zip);
		
		userInfoMain.add(usernameLabel);
		userInfoMain.add(usernameText);
		userInfoMain.add(ccnLabel);
		userInfoMain.add(ccnText);
		userInfoMain.add(passwordLabel);
		userInfoMain.add(passwordText);
		userInfoMain.add(ccvLabel);
		userInfoMain.add(ccvText);
		userInfoMain.add(nameLabel);
		userInfoMain.add(nameText);
		userInfoMain.add(ccNameLabel);
		userInfoMain.add(ccNameText);
		userInfoMain.add(phoneLabel);
		userInfoMain.add(phoneText);
		userInfoMain.add(ccExpLabel);
		userInfoMain.add(ccExpText);
		userInfoMain.add(emailLabel);
		userInfoMain.add(emailText);
		userInfoMain.add(zipLabel);
		userInfoMain.add(zipText);
		userInfoMain.add(pointsLabel);
		userInfoMain.add(pointsText);
		userInfoMain.add(statusLabel);
		userInfoMain.add(statusText);
		userInfoMain.add(street1Label);
		userInfoMain.add(street1Text);
		userInfoMain.add(street2Label);
		userInfoMain.add(street2Text);
		userInfoMain.add(cityLabel);
		userInfoMain.add(cityText);
		userInfoMain.add(stateLabel);
		userInfoMain.add(stateText);

		
		userInfoButton.add(updateUserButton,SwingConstants.CENTER);
		
		updateUserButton.setVisible(true);
		usernameLabel.setVisible(true);
		usernameText.setVisible(true);
		passwordLabel.setVisible(true);
		passwordText.setVisible(true);
		nameLabel.setVisible(true);
		nameText.setVisible(true);
		ccnLabel.setVisible(true);
		ccnText.setVisible(true);
		phoneLabel.setVisible(true);
		phoneText.setVisible(true);
		emailLabel.setVisible(true);
		emailText.setVisible(true);
		pointsLabel.setVisible(true);
		pointsText.setVisible(true);
		statusLabel.setVisible(true);
		statusText.setVisible(true);
		ccvLabel.setVisible(true);
		ccvText.setVisible(true);
		ccNameLabel.setVisible(true);
		ccNameText.setVisible(true);
		ccExpLabel.setVisible(true);
		ccExpText.setVisible(true);
		street1Label.setVisible(true);
		street1Text.setVisible(true);
		street2Label.setVisible(true);
		street2Text.setVisible(true);
		cityLabel.setVisible(true);
		cityText.setVisible(true);
		stateLabel.setVisible(true);
		stateText.setVisible(true);
		zipLabel.setVisible(true);
		zipText.setVisible(true);
		
		
		userInfoMain.setVisible(true);
		userInfoButton.setVisible(true);
		
		userInfoTab.add(userInfoMain);
		userInfoTab.add(userInfoButton);
		userTabbedPane.addTab("Info", userInfoTab);

		userInfoTab.setVisible(true);
	}
	
	//creates new thread from create thread panel
	private void createThread()
	{
		forumCreatePanel = new JPanel(new GridLayout(5,1));
		JPanel forumCreateButtonPanel = new JPanel();
		
		JLabel newLabel = new JLabel("Create a New Thread");
		newLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		createStarThreadButton = new JRadioButton("Star Thread");
		createMovieThreadButton = new JRadioButton("Movie Thread");
		ButtonGroup createGroup = new ButtonGroup();
		createGroup.add(createStarThreadButton);
		createGroup.add(createMovieThreadButton);
		createStarThreadButton.setSelected(true);
		
		forumCreateButtonPanel.add(createStarThreadButton);
		forumCreateButtonPanel.add(createMovieThreadButton);
		
		createThreadText = new JTextField("Thread Title", 50);
		createCommentText = new JTextField("Comment Text", 50);
		
		threadSubmitButton = new JButton("Submit");
		threadSubmitButton.setActionCommand("submit thread");
		threadSubmitButton.addActionListener(this);
		
		createStarThreadButton.setVisible(true);
		createMovieThreadButton.setVisible(true);
		forumCreateButtonPanel.setVisible(true);
		createThreadText.setVisible(true);
		createCommentText.setVisible(true);
		threadSubmitButton.setVisible(true);
		forumCreatePanel.setVisible(true);
		userTabbedPane.setVisible(false);
		
		forumCreatePanel.add(newLabel);
		forumCreatePanel.add(forumCreateButtonPanel);
		forumCreatePanel.add(createThreadText);
		forumCreatePanel.add(createCommentText);
		forumCreatePanel.add(threadSubmitButton);
		contentPanel.add(forumCreatePanel,"Forum Create");
		
		starRadioButton.setSelected(true);
		starRadioButton.setSelected(false);
		movieRadioButton.setSelected(false);
	}
	
	//creates new comment from create thread panel
	private void createComment()
	{		
		createThread();
	
		createStarThreadButton.setEnabled(false);
		createStarThreadButton.setSelected(starRadioButton.isSelected());
		createMovieThreadButton.setEnabled(false);
		createMovieThreadButton.setSelected(movieRadioButton.isSelected());
		
		createThreadText.setEnabled(false);
		createThreadText.setText((String) threadList.getSelectedItem()); 
		
		threadSubmitButton.setActionCommand("submit comment");
	}
	
	//handles actions on GUI
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		CardLayout layoutManager = (CardLayout) contentPanel.getLayout();
		
		if(loginPanel.isShowing()) 
		{
			if(e.getActionCommand() == "user login")
			{
				username = loginField.getText();
				password = new String(pwField.getPassword());
				
				if(user.login(username, password) == true) {
					user.getPointsInfo();
					createUserTabs();
					layoutManager.show(contentPanel, "User Tabs");
					userForumTab.setVisible(false);
					userTicketTab.setVisible(false);
				}
				else {
					JOptionPane.showMessageDialog(frame,"Invalid username/password.","Login Error",JOptionPane.ERROR_MESSAGE);
				}
			}
			else if(e.getActionCommand() == "guest login")
			{
				createGuestTabs();
				layoutManager.show(contentPanel, "Guest Tabs");
				userForumTab.setVisible(false);
			}
			else if(e.getActionCommand() == "register user")
			{
				createRegisterPanel();
				layoutManager.show(contentPanel, "Register Panel");
			}
			else if(e.getActionCommand() == "emp login")
			{
				username = loginField.getText();
				password = new String(pwField.getPassword());
				
				if(admin.login(username, password) == true)
				{
					try 
					{
						try {
							createEmpTabs();
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} 
					catch (SQLException e1) 
					{
						JOptionPane.showMessageDialog(frame,"SQL Error with DATE. Closing App.","SQL Error",JOptionPane.ERROR_MESSAGE);
						System.exit(1);
					}
					layoutManager.show(contentPanel, "Emp Tabs");
					empAddMoviesTab.setVisible(false);
					empSeeInfoTab.setVisible(false);
				}
				else 
				{
					JOptionPane.showMessageDialog(frame,"Invalid username/password.","Login Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else if(userInfoTab.isShowing())
		{
			if(e.getActionCommand() == "user updates info")
			{
				 password = passwordText.getText();
				 ccn = ccnText.getText();
				 phone = phoneText.getText();
				 email = emailText.getText();
				 String cvv = ccvText.getText();
				 String ccName = ccNameText.getText();
				 String date = ccExpText.getText();
				 String zip = zipText.getText();
				 String s1 = street1Text.getText();
				 String s2 = street2Text.getText();
				 String city = cityText.getText();
				 String state = stateText.getText();
				 
				 user.update(password, ccn, phone, email, cvv, ccName, date, s1, s2, city, state, zip);
				 JOptionPane.showMessageDialog(frame,"Update Success","User Profile",JOptionPane.PLAIN_MESSAGE);
			}	
		}
		else if(userForumTab.isShowing())
		{
			MovieForum forum = new MovieForum();
			threadList.removeActionListener(this);
			commentList.removeActionListener(this);
			if(e.getActionCommand() == "movie threads")
			{
				threadList.removeAllItems();
				threadList.setEnabled(true);
				
				ArrayList<MovieThread> movies = forum.arrangeByTitle();
				for(MovieThread thread : movies) {
					threadList.addItem(thread.movie+": "+thread.text);
				}

				commentList.removeAllItems();
				commentList.setEnabled(false);
				commentText.setText("");
				threadList.setSelectedIndex(-1);
			}
			else if(e.getActionCommand() == "star threads")
			{
				threadList.removeAllItems();
				threadList.setEnabled(true);
				
				ArrayList<MovieThread> stars = forum.arrangeByStars();
				for(MovieThread thread : stars) {
					threadList.addItem(thread.star+": "+thread.text);
				}

				commentList.removeAllItems();
				commentList.setEnabled(false);
				commentText.setText("");
				threadList.setSelectedIndex(-1);
			}
			else if(e.getActionCommand() == "thread selected" && threadList.getSelectedIndex() != -1)
			{
				commentList.setEnabled(true);
				commentList.removeAllItems();
				
				if(movieRadioButton.isSelected()) 
				{
					ArrayList<MovieThread> movies = forum.arrangeByTitle();
					for (MovieThread thread: movies) {
						String [] parts = threadList.getSelectedItem().toString().split(":");
						if (thread.movie.equals(parts[0])) {
							for (Comment comment : thread.comments) {
								commentList.addItem(comment.text);
							}
						}
					}
				}
				else if(starRadioButton.isSelected())
				{
					ArrayList<MovieThread> stars = forum.arrangeByStars();
					for (MovieThread thread: stars) {
						String [] parts = threadList.getSelectedItem().toString().split(":");
						if (thread.star.equals(parts[0])) {
							for (Comment comment : thread.comments) {
								commentList.addItem(comment.text);
							}
						}
					}
				}
				commentList.setSelectedIndex(-1);
				commentText.setText("");
			}
			else if(e.getActionCommand() == "comment selected" && commentList.getSelectedIndex() != -1)
			{
				commentText.setText((String)((JComboBox) e.getSource()).getSelectedItem());
			}
			else if(e.getActionCommand() == "create thread")
			{
				createThread();
				layoutManager.show(contentPanel, "Forum Create");
			}
			else if(e.getActionCommand() == "create comment")
			{
				if((movieRadioButton.isSelected() || starRadioButton.isSelected()) && threadList.getSelectedIndex() != -1)
				{
					createComment();
					layoutManager.show(contentPanel, "Forum Create");
				}
				else JOptionPane.showMessageDialog(frame,"You must select a thread first.","Create Comment Error",JOptionPane.ERROR_MESSAGE);
			}
			threadList.addActionListener(this);
			commentList.addActionListener(this);
		}
		else if(forumCreatePanel.isShowing())
		{
			MovieForum forum = new MovieForum();
			if(e.getActionCommand() == "submit thread")
			{
				if(createMovieThreadButton.isSelected())
				{
					forum.addMovieThread(user.username, createThreadText.getText(),createCommentText.getText());
					String msg = user.incrementPoints('t');
					if (!msg.equals("")) {
						JOptionPane.showMessageDialog(frame, msg,"Congrats!",JOptionPane.PLAIN_MESSAGE);
					}
				}
				else
				{
					forum.addStarThread(user.username, createThreadText.getText(),createCommentText.getText());
					String msg = user.incrementPoints('t');
					if (!msg.equals("")) {
						JOptionPane.showMessageDialog(frame, msg,"Congrats!",JOptionPane.PLAIN_MESSAGE);
					}
				}

				layoutManager.show(contentPanel, "User Tabs");
			}
			else if(e.getActionCommand() == "submit comment")
			{
				String []  parts = threadList.getSelectedItem().toString().split(":");
				for (MovieThread thread : forum.mf) {
					if (thread.text.equals(parts[1].trim())) {
						int num = MovieThread.movComsize + 1;
						thread.addComment(thread.id, num, user.username, createCommentText.getText());
						String msg = user.incrementPoints('c');
						if (!msg.equals("")) {
							JOptionPane.showMessageDialog(frame, msg,"Congrats!",JOptionPane.PLAIN_MESSAGE);
						}
					}
				}
				layoutManager.show(contentPanel, "User Tabs");
			}
			
			if(createMovieThreadButton.isSelected())
			{
				movieRadioButton.doClick();
			}
			else starRadioButton.doClick();
		}
		else if(userTicketTab.isShowing())
		{
			ShowingsList shows = new ShowingsList();
			shows.setTitles();
			shows.setTheaters();
			HashSet<String> titles = shows.getUniqueTitles();
			HashSet<Integer> theaters = shows.getUniqueTheaters();
			
			theaterList.removeActionListener(this);
			movieList.removeActionListener(this);
			dateList.removeActionListener(this);
			
			if(e.getActionCommand() == "select by movie")
			{
				movieList.removeAllItems();
				theaterList.removeAllItems();
				dateList.removeAllItems();
				
				theaterList.setEnabled(false);
				movieList.setEnabled(true);
				dateList.setEnabled(false);
				
				for (String title : titles) {
					movieList.addItem(title);
				}
				
				theaterList.setSelectedIndex(-1);
				movieList.setSelectedIndex(-1);
				dateList.setSelectedIndex(-1);
				
				ticketPriceText.setText("");
			}
			else if(e.getActionCommand() == "select by theater")
			{
				movieList.removeAllItems();
				theaterList.removeAllItems();
				dateList.removeAllItems();
				
				for (int theater : theaters) {
					theaterList.addItem(Integer.toString(theater));
				}
				
				movieList.setEnabled(false);
				theaterList.setEnabled(true);
				dateList.setEnabled(false);
				
				theaterList.setSelectedIndex(-1);
				movieList.setSelectedIndex(-1);
				dateList.setSelectedIndex(-1);
				
				ticketPriceText.setText("");
			}
			else if(e.getActionCommand() == "chose movie")
			{
				if(selectMovieRadioButton.isSelected())
				{
					theaterList.removeAllItems();
					
					ArrayList<Showing> copy = new ArrayList<Showing>(shows.schedule);
					
					for (Showing s : copy) {
						if (!s.title.equals(movieList.getSelectedItem())) {
							shows.schedule.remove(s);
						}
					}
					theaters = shows.getUniqueTheaters();
					for (int theater : theaters) {
						theaterList.addItem(Integer.toString(theater));
					}
					
					theaterList.setEnabled(true);
					dateList.setEnabled(false);
					
					theaterList.setSelectedIndex(-1);
					dateList.setSelectedIndex(-1);
				}
				else
				{
					dateList.removeAllItems();
					
					for (Showing s : shows.schedule) {
						if (Integer.toString(s.theater).equals(theaterList.getSelectedItem()) && s.title.equals(movieList.getSelectedItem())) {
							dateList.addItem(s.date.toString());
						}
					}
					
					
					dateList.setEnabled(true);	
					dateList.setSelectedIndex(-1);
				}
				ticketPriceText.setText("");
			}
			else if(e.getActionCommand() == "chose theater")
			{	
				if(selectTheaterRadioButton.isSelected())
				{
					movieList.removeAllItems();
					
					ArrayList<Showing> copy = new ArrayList<Showing>(shows.schedule);
					for (Showing s : copy) {
						if (!(Integer.toString(s.theater)).equals(theaterList.getSelectedItem())) {
							shows.schedule.remove(s);
						}
					}
					
					
					titles = shows.getUniqueTitles();
					for (String title : titles) {
						movieList.addItem(title);
					}
					
					movieList.setEnabled(true);
					dateList.setEnabled(false);
					
					movieList.setSelectedIndex(-1);
					dateList.setSelectedIndex(-1);
				}
				else
				{
					dateList.removeAllItems();

					for (Showing s : shows.schedule) {
						if (s.title.equals(movieList.getSelectedItem()) && Integer.toString(s.theater).equals(theaterList.getSelectedItem())) {
							dateList.addItem(s.date.toString());
						}
					}
					
				dateList.setEnabled(true);
				dateList.setSelectedIndex(-1);
				ticketPriceText.setText("");
				}
			}
				
			else if(e.getActionCommand() == "chose date")
			{
				for(Showing s : shows.schedule) {
					if (s.title.equals(movieList.getSelectedItem()) 
							&& Integer.toString(s.theater).equals(theaterList.getSelectedItem())
							&& s.date.toString().equals(dateList.getSelectedItem())) {
						selected = s;
					}
				}
				
				double price = selected.price * Double.parseDouble((String)ticketNumList.getSelectedItem());
				ticketPriceText.setText("Total Price: $" +  price);
			}
			else if(e.getActionCommand() == "chose num tickets")
			{
				for(Showing s : shows.schedule) {
					if (s.title.equals(movieList.getSelectedItem()) 
							&& Integer.toString(s.theater).equals(theaterList.getSelectedItem())
							&& s.date.toString().equals(dateList.getSelectedItem())) {
						selected = s;
					}
				}
				
				if(!ticketPriceText.getText().equals(""))
				{
					double price = selected.price * Double.parseDouble((String)ticketNumList.getSelectedItem());
					ticketPriceText.setText("Total Price: $" +  price);
				}
			}
			else if(e.getActionCommand() == "buy ticket")
			{
				for(Showing s : shows.schedule) {
					if (s.title.equals(movieList.getSelectedItem()) 
							&& Integer.toString(s.theater).equals(theaterList.getSelectedItem())
							&& s.date.toString().equals(dateList.getSelectedItem())) {
						selected = s;
					}
				}
				
				if(dateList.getSelectedIndex() != -1)
				{
					if(ticketNumList.getSelectedIndex() != -1)
					{
						user.purchaseTicket(selected.sid, Integer.parseInt(ticketNumList.getSelectedItem().toString()));
					}
					else JOptionPane.showMessageDialog(frame,"You must purchase between 1 and 9 tickets.","Buy Ticket Error",JOptionPane.ERROR_MESSAGE);			
				}
				else JOptionPane.showMessageDialog(frame,"You must select a theater, movie, and date/time.","Buy Ticket Error",JOptionPane.ERROR_MESSAGE);
			}
			else if(e.getActionCommand() == "buy guest ticket")
			{
				if(dateList.getSelectedIndex() != -1)
				{
					if(ticketNumList.getSelectedIndex() != -1)
					{	
						createGuestInfoPanel();
						layoutManager.show(contentPanel, "Guest Info");
					}
					else JOptionPane.showMessageDialog(frame,"You must purchase between 1 and 9 tickets.","Buy Ticket Error",JOptionPane.ERROR_MESSAGE);			
				}
				else JOptionPane.showMessageDialog(frame,"You must select a theater, movie, and date/time.","Buy Ticket Error",JOptionPane.ERROR_MESSAGE);
			}
			
			theaterList.addActionListener(this);
			movieList.addActionListener(this);
			dateList.addActionListener(this);
		}
		else if(guestInfoPanel.isShowing())
		{
			if(e.getActionCommand() == "cancel guest purchase")
			{
				ccnText.setText("");
				ccvText.setText("");
				ccNameText.setText("");
				zipText.setText("");
				ccExpText.setText("");
				street1Text.setText("");
				street2Text.setText("");
				cityText.setText("");
				stateText.setText("");
				emailText.setText("");
				
				JOptionPane.showMessageDialog(frame,"Your ticket purchase has been cancelled","Ticket Purchase Cancel",JOptionPane.INFORMATION_MESSAGE);
				layoutManager.show(contentPanel, "Guest Tabs");
			}
			else if(e.getActionCommand() == "confirm guest purchase")
			{
				if(!ccnText.getText().isEmpty() && !ccvText.getText().isEmpty() && !ccNameText.getText().isEmpty()  && !zipText.getText().isEmpty()  
					&& !ccExpText.getText().isEmpty()  && !street1Text.getText().isEmpty() && !street2Text.getText().isEmpty() && !cityText.getText().isEmpty()
					&& !stateText.getText().isEmpty() && !emailText.getText().isEmpty())
				{
					guest.registerCC(ccnText.getText(),ccvText.getText(),ccNameText.getText(),java.sql.Date.valueOf(ccExpText.getText()),street1Text.getText(),street2Text.getText(), cityText.getText(), stateText.getText(),zipText.getText());
					guest.registerUser(ccNameText.getText(),ccnText.getText(),emailText.getText());
					
					guest.purchaseTicket(selected.sid, Integer.parseInt(ticketNumList.getSelectedItem().toString()));
					
					JOptionPane.showMessageDialog(frame,"Your ticket purchase has been placed","Ticket Purchase Placed",JOptionPane.INFORMATION_MESSAGE);
					layoutManager.show(contentPanel,"Guest Tabs");
				}
				else JOptionPane.showMessageDialog(frame,"Must fill out all info fields","Purchase Error",JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(registerPanel.isShowing())
		{
			if(e.getActionCommand() == "create account")
			{
				if(!ccnText.getText().isEmpty() && !ccvText.getText().isEmpty() && !ccNameText.getText().isEmpty()  && !zipText.getText().isEmpty()  
					&& !ccExpText.getText().isEmpty()  && !street1Text.getText().isEmpty() && !street2Text.getText().isEmpty() && !cityText.getText().isEmpty()
					&& !stateText.getText().isEmpty() && !emailText.getText().isEmpty() && !usernameText.getText().isEmpty() && !passwordText.getText().isEmpty()
					&& !nameText.getText().isEmpty() && !phoneText.getText().isEmpty())
				{
					user.registerCC(ccnText.getText(),ccvText.getText(),ccNameText.getText(),java.sql.Date.valueOf(ccExpText.getText()),street1Text.getText(),street2Text.getText(), cityText.getText(), stateText.getText(),zipText.getText());
					user.registerUser(usernameText.getText(), passwordText.getText(), ccNameText.getText(),ccnText.getText(),phoneText.getText(),emailText.getText());
					
					JOptionPane.showMessageDialog(frame,"Your account has been created.","Account Registration",JOptionPane.INFORMATION_MESSAGE);
					layoutManager.show(contentPanel,"Login Panel");
				}
				else JOptionPane.showMessageDialog(frame,"Must fill out all info fields","Register Error",JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(empAddShiftTab.isShowing())
		{
			if(e.getActionCommand() == "chose shift emp")
			{
				int id = Integer.parseInt((String)empShiftList.getSelectedItem());
				
				typeShiftList.removeAllItems();
				
				for(int i=0;i<admin.emps.size();i++)
				{
					if(admin.emps.get(i).id == id)
					{
						if(admin.emps.get(i).janitor) typeShiftList.addItem("Janitor");
						if(admin.emps.get(i).salesrep) typeShiftList.addItem("Sales Rep");
						if(admin.emps.get(i).ticketmaster) typeShiftList.addItem("Ticketmaster");
					}
				}
				typeShiftList.setSelectedIndex(-1);
				typeShiftList.setEnabled(true);
			}
			else if(e.getActionCommand() == "add shift")
			{
				Date selectedDate = (Date) datePicker1.getModel().getValue();
				System.out.println(""+selectedDate);
				if(empShiftList.getSelectedIndex()==-1 || typeShiftList.getSelectedIndex()==-1 || theaterShiftList.getSelectedIndex()==-1
						|| timeShiftList.getSelectedIndex()==-1 || selectedDate.before(new Date()))
				{
					JOptionPane.showMessageDialog(frame,"Must fill out all info fields and select a date after today","Add Shift Error",JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					int id = Integer.parseInt((String)empShiftList.getSelectedItem());
					String job = (String) typeShiftList.getSelectedItem();
					String time = (String) timeShiftList.getSelectedItem();
					String theater = (String) theaterShiftList.getSelectedItem();
					
					int thid = Integer.parseInt((String) theaterShiftList.getSelectedItem());
					//TODO: Check if employee is being scheduled at the same time as another shift they have
					//TODO: If yes, throw JOptionPane error box up. If not, add shift to schedule in DB
					if(EmployeeList.checkDate(id, selectedDate)){
						JOptionPane.showMessageDialog(frame,"Employee is already scheduled for this time","Add Shift Error",JOptionPane.ERROR_MESSAGE);
					}else{
						EmployeeList.addSched(id, selectedDate, thid, job);
					}
				}
			}
		}
		else if(empRemoveShiftTab.isShowing())
		{
			if(e.getActionCommand() == "chose remove emp")
			{
				removeShiftList.removeAllItems();
				
				//TODO: Need to fill removeShiftList JComboBox with shifts scheduled for given employee
				int empID = Integer.parseInt((String) removeEmpList.getSelectedItem());
				ArrayList<EmpShift> empshifts = EmployeeList.getShifts(empID);
				int n;
				for(n=0; n<empshifts.size(); n++){
					int empid = empshifts.get(n).emp;
					int thid = empshifts.get(n).theater;
					Date d = empshifts.get(n).j_date;
					String t = empshifts.get(n).j_type;
					String shift = "id: "+empid+", theater: "+thid+", date: "+d+", job: "+t;
					removeShiftList.addItem(shift);
				}
				
				removeShiftList.setEnabled(true);
				removeShiftList.setSelectedIndex(-1);
			}
			else if(e.getActionCommand() == "remove shift")
			{
				if(removeEmpList.getSelectedIndex() == -1 || removeShiftList.getSelectedIndex() == -1)
				{
					JOptionPane.showMessageDialog(frame,"Must fill out all info fields","Remove Shift Error",JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					//TODO: Logic to remove selected shift from DB
					String selectedShift = (String) removeShiftList.getSelectedItem();
					int emp = Integer.parseInt(selectedShift.substring(4, selectedShift.indexOf(", t")));
					System.out.println(""+emp+".");
					int th = Integer.parseInt(selectedShift.substring(3+selectedShift.indexOf("r: "), selectedShift.indexOf(", d")));
					System.out.println(""+th+".");
					String da = selectedShift.substring(3+selectedShift.indexOf("e: "), selectedShift.indexOf(", j"));
					System.out.println(da);
					String date = da.substring(5,7) +"-"+ da.substring(8,da.length())+"-"+da.substring(0,4);
					System.out.println(date);
					
					DateFormat format = new SimpleDateFormat("MM-dd-yyyy");
					Date dat = new Date();
					try {
						dat = format.parse(date);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(frame,"SQL Error with DATE. Closing App.","SQL Error",JOptionPane.ERROR_MESSAGE);
						System.exit(1);
					}
					System.out.println(""+dat);
					
					
					String jt = selectedShift.substring(3+selectedShift.indexOf("b: "), selectedShift.length());
					System.out.println(jt+".");
					EmployeeList.delSched(emp, dat, th, jt);
				}
			}
		}
		else if(empAddMoviesTab.isShowing())
		{
			
			if(e.getActionCommand() == "add movie")
			{
				count++;
				System.out.println(""+count);
				ShowingsList shows = new ShowingsList();
				//System.out.println("add movie button was pressed");
				String tit_mov = movieNameField.getText();
				int thid = Integer.parseInt((String) addMovieLocList.getSelectedItem());
				int s_id = shows.shNum + count;
				Date sd = (Date) datePicker2.getModel().getValue();
				int m_id = movl.findMovie(tit_mov);
				//System.out.println(""+m_id);
				shows.addShowing(s_id, m_id, thid, sd);
				
			}
		}
		else if((empSeeInfoTab.isShowing()))
		{
			if(e.getActionCommand() == "Admin See User")
			{
				createEmpInfoPanel();
				layoutManager.show(contentPanel, "Emp Info Panel");
			}
		}
		
		else if((empInfoPanel.isShowing())){
			if(e.getActionCommand() == "info ok"){
				layoutManager.show(contentPanel, "Emp Tabs");
			}
		}
		else if(empPermissionsTab.isShowing())
		{
			if(!f.exists())
			{
				f = new File("C:/Users/Ayesha Ahmed/Documents/CS425/Final Project/sqlhw_final_project_ahmed_warman_caron/temp.txt");
			}
			if(e.getActionCommand() == "add permission")
			{
				try {
					BufferedWriter out = new BufferedWriter(new FileWriter(f));
					if(manager1RadioButton.isSelected())
					{
						out.write(new String("manager1"));
					}
					else if(manager2RadioButton.isSelected())
					{
						out.write(new String("manager2"));
					}
					out.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
			else if(e.getActionCommand() == "remove permissions")
			{
				f.delete();
			}
		}
		frame.pack();
	}
	
	@SuppressWarnings("serial")
	class DateLabelFormatter extends AbstractFormatter
	{
		private String datePattern = "MM-dd-yyyy";
		private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

		@Override
		public Object stringToValue(String text) throws ParseException {
			return dateFormatter.parseObject(text);
		}

		@Override
		public String valueToString(Object value) throws ParseException {
			if(value != null)
			{
				Calendar cal = (Calendar) value;
				return dateFormatter.format(cal.getTime());
			}
			else return "";
		}
		
	}
}
