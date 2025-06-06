package GUI;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


public class GUIclient implements ActionListener{
	private JFrame frame;
	private JTextField BusinessNameInput, BusinessNumInput, amountInput;
	private static JButton sendBtn, LogoutBtn;
	private static JLabel message;
	private Choice items;
	private static String fromUser = "";
	private static PrintWriter out = null;
	private static BufferedReader in = null;
	public static void main(String [] args)throws IOException {
		Socket BusinessSocket = null;

		try {
			BusinessSocket = new Socket("127.0.0.1", 9999);
			out = new PrintWriter(BusinessSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(BusinessSocket.getInputStream()));
			
		}catch (UnknownHostException e) {
			System.err.println("Don't know about host.");
			System.exit(1);
			} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: your host.");
			System.exit(1);
			}
		GUIclient gui = new GUIclient();
		gui.createGUI();
		
		String fromServer;
		int fromServerInt;
		
		while ((fromServer = in.readLine()) != null)
		{
			if(isNumeric(fromServer)) {
				fromServerInt = Integer.parseInt(fromServer);
				updateMessage(fromServerInt);
			}
			else {
				updateMessage(999);//Value that don't have case to return default answer of switch case 
			}
			
		}
		out.close();
		in.close();
		
		BusinessSocket.close();
	}

    public void createGUI() {
        frame = new JFrame("Ron's Shop");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        JLabel title = new JLabel("Welcome to Ron's Shop", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(title, BorderLayout.CENTER);
        frame.add(titlePanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 30, 20)); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Business Name:"), gbc);
        gbc.gridx = 1;
        BusinessNameInput = new JTextField(20);
        mainPanel.add(BusinessNameInput, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Business Number:"), gbc);
        gbc.gridx = 1;
        BusinessNumInput = new JTextField(10);
        mainPanel.add(BusinessNumInput, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Item:"), gbc);
        gbc.gridx = 1;
        items = new Choice();
        items.add("Sun glasses");
        items.add("Belt");
        items.add("Scarf");
        mainPanel.add(items, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("Amount:"), gbc);
        gbc.gridx = 1;
        amountInput = new JTextField(10);
        mainPanel.add(amountInput, gbc);

        frame.add(mainPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); // Add space at the bottom
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5)); // Reduced vertical spacing
        sendBtn = new JButton("Send");
        sendBtn.addActionListener(this);
        buttonPanel.add(sendBtn);

        LogoutBtn = new JButton("Log out");
        LogoutBtn.addActionListener(this);
        LogoutBtn.setVisible(false);
        buttonPanel.add(LogoutBtn);

        bottomPanel.add(buttonPanel, BorderLayout.CENTER);

        message = new JLabel("", JLabel.CENTER);
        message.setForeground(Color.RED);
        message.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0)); // Small space above message
        bottomPanel.add(message, BorderLayout.SOUTH);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setPreferredSize(new Dimension(600, 400));
        frame.pack();

        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == LogoutBtn) {
			fromUser = createInput(false);
			out.println(fromUser);
			frame.dispose();
		}
		if(e.getSource() == sendBtn) {
			fromUser = createInput(true);
			out.println(fromUser);
		}
	}
	
	public static void updateMessage(int code) {
		switch(code) {
			case 100:
				message.setText("Purchase successfully completed. Would you like to continue or Log-out?");
				message.setForeground(Color.GREEN);
				LogoutBtn.setVisible(true);
				break;
			case 200:
				message.setText("All fields must be completed. Please ensure no field is left blank");
				message.setForeground(Color.RED);
				LogoutBtn.setVisible(false);
				break;
			case 201:
				message.setText("Business is already exist. Business name is incorrect");
				message.setForeground(Color.RED);
				LogoutBtn.setVisible(false);
				break;
			case 202:
				message.setText("Invalid inputs. Please try again");
				message.setForeground(Color.RED);
				LogoutBtn.setVisible(false);
				break;
			default:
				message.setText("Something went wrong");
		}
	}
	
	public String createInput(boolean check) {
		if(check) {
			String str = "";
			str += BusinessNameInput.getText() + ",";
			str += BusinessNumInput.getText() + ",";
			str += items.getSelectedItem() + ",";
			str += amountInput.getText();
			str += " ";
			return str;
		}
		else {
			return "q";
		}
	}
	public static boolean isNumeric(String str) {
		 try {
			 Integer.parseInt(str);
			 return true;
		 }catch(NumberFormatException e) {
			 return false;
		 }
	}
}

