package hr.fer.zemris.java.hw11.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.w3c.dom.NameList;

public class Gui extends JFrame {
	
	List<Person> people = new ArrayList<Person>();
	JTabbedPane tabbedPane = new JTabbedPane();
	
	public Gui() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(500, 500);
		setSize(700, 500);
		setTitle("Gui jr");
		
		people = getPeopleFromFile();
		
		for(int i = 0; i < people.size(); i++) {
			System.out.println(people.get(i).getName() +  " " + people.get(i).getAge());
		}
		
		initGui();
	}
	
	private List<Person> getPeopleFromFile() {
		String fileNameAndDesktop = "/Desktop/testGUI";
		File desktopFile = new File(System.getProperty("user.home"), fileNameAndDesktop);
		
		List<String> fileText = null;
		
		try {
			fileText = Files.readAllLines(desktopFile.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Person> tempPeople = new ArrayList<Person>();
		
		// skip "ime godine", so i = 1
		for(int i = 0; i < fileText.size(); i++) {
			String[] lineInfo = fileText.get(i).trim().split("\\s+");
			
			tempPeople.add(new Person(lineInfo[0], Integer.valueOf(lineInfo[1])));
		}
		
		return tempPeople;
	}

	private void addNewPersonToFile(Person newPerson) {
		String fileNameAndDesktop = "/Desktop/testGUI";
		File desktopFile = new File(System.getProperty("user.home"), fileNameAndDesktop);
		
		String newLine = newPerson.getName() + " " + newPerson.getAge() + "\n";
		
		try {
			Files.write(Paths.get(desktopFile.getPath()), newLine.getBytes(),
					StandardOpenOption.APPEND);
			
		} catch (IOException e) {
			System.out.println("Can't add new person to file");
			e.printStackTrace();
		}
	}
	
	private void removeLineInFile(String name) {
		String fileNameAndDesktop = "/Desktop/testGUI";
		File desktopFile = new File(System.getProperty("user.home"), fileNameAndDesktop);
		
		List<String> fileText = null;
		
		try {
			fileText = Files.readAllLines(desktopFile.toPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Person> tempPeople = new ArrayList<Person>();
		
		for(int i = 0; i < fileText.size(); i++) {
			String[] lineInfo = fileText.get(i).trim().split("\\s+");
			
			if(lineInfo[0].equals(name)) {
				continue;
			}
			
			tempPeople.add(new Person(lineInfo[0], Integer.valueOf(lineInfo[1])));
		}
		
		String peopleString = "";
		
		for(int i = 0; i < tempPeople.size(); i++) {
			peopleString += tempPeople.get(i).toString();
		}
		
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(desktopFile));
			bw.write(peopleString);
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private final Action addUser = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			JFrame frame = new JFrame("Enter user info");
			frame.setSize(300, 150);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			JPanel panel = new JPanel();
			frame.add(panel);

			JLabel userLabel = new JLabel("Name");
			userLabel.setBounds(10, 10, 80, 25);
			panel.add(userLabel);
			
			JTextField userText = new JTextField(20);
			userText.setBounds(100, 10, 160, 25);
			panel.add(userText);
			
			JLabel ageLabel = new JLabel("Age");
			ageLabel.setBounds(10, 20, 80, 50);
			panel.add(ageLabel);
			
			JTextField ageText = new JTextField(3);
			ageText.setBounds(100, 20, 160, 50);
			panel.add(ageText);

			JButton save = new JButton("save user");
			
			save.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String name = userText.getText();
					Integer age = Integer.valueOf(ageText.getText());
					
					Person newPerson = new Person(name, age);
					people.add(newPerson);
					addNewPersonToFile(newPerson);
					// close window :)
					frame.dispose();
				}

			});
			
			save.setBounds(10, 80, 80, 25);
			panel.add(save);
			
			JButton cancel = new JButton("cancel");
			cancel.setBounds(180, 80, 80, 25);
			
			cancel.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.dispose();
				}
			});
			
			panel.add(cancel);
			
			frame.setVisible(true);
		}
	};
	
	private final Action removeUser = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFrame frame = new JFrame("Enter name of user to remove");
			frame.setSize(300, 150);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			JPanel panel = new JPanel();
			
			JLabel nameLabel = new JLabel("Name");
			nameLabel.setBounds(10, 10, 80, 25);
			panel.add(nameLabel);
			
			JTextField nameText = new JTextField(20);
			nameText.setBounds(100, 10, 160, 25);
			panel.add(nameText);
			
			JButton remove = new JButton("remove user");
			
			remove.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {

					for(int i = 0; i < people.size(); i++) {
						if(people.get(i).getName().equals(nameText.getText())) {
							people.remove(i);
							frame.dispose();
						}
					}
					removeLineInFile(nameText.getText());
				}

			});
			
			remove.setBounds(10, 80, 80, 25);
			panel.add(remove);
			
			JButton cancel = new JButton("cancel");
			cancel.setBounds(180, 80, 80, 25);
			
			cancel.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.dispose();
				}
			});
			
			panel.add(cancel);
			
			frame.add(panel);
			
			frame.setVisible(true);
			
		}
	};

	private final Action listNames = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFrame listNamesFrame = new JFrame("List names");
			listNamesFrame.setSize(300, 150);
			listNamesFrame.setVisible(true);
			listNamesFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			String names = "";
			
			for(int i = 0; i < people.size(); i++) {
				names += people.get(i).getName()+ " ";
			}
			
			JPanel panel = new JPanel();
			
			JLabel nameLabel = new JLabel();
			nameLabel.setText(names);
			nameLabel.setBounds(10, 10, 80, 25);
			panel.add(nameLabel);
		
			JButton ok = new JButton("OK");
			ok.setBounds(180, 80, 80, 25);
			
			ok.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					listNamesFrame.dispose();
				}
			});
			
			panel.add(ok);
			
			listNamesFrame.add(panel);
		}
	};
	
	// struktura
	private static class peop {
		int a;
		int b;
		
		public peop(int a, int b) {
			this.a = a;
			this.b = b;
		}
	}
	
	private final Action openUser = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFrame getNameToOpen = new JFrame("Enter name");
			getNameToOpen.setSize(300, 150);
			getNameToOpen.setVisible(true);
			getNameToOpen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			JPanel panel = new JPanel();
			getNameToOpen.add(panel);
			
			JLabel nameLabel = new JLabel("Name");
			nameLabel.setBounds(10, 10, 80, 25);
			panel.add(nameLabel);
			
			// Bitno je napisat 20 pa da ostane mjesta u kucici za 20 slova
			JTextField nameText = new JTextField(20);
			nameText.setBounds(100, 10, 160, 25);
			panel.add(nameText);
		
			JButton ok = new JButton("OK");
			ok.setBounds(10, 80, 80, 25);
			panel.add(ok);
			
			// open tab with table filled with users
			ok.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String givenName = nameText.getText();
					
					JPanel tabPanel = new JPanel();
					
					List<Person> tablePeople = new ArrayList<Person>();
					
					for(int i = 0; i < people.size(); i++) {
						if(people.get(i).getName().equals(givenName)) {
							tablePeople.add(people.get(i));
						}
					}
					
					String[] columnNames = {"Names", "Ages"};
					
					Object[][] data = new Object[tablePeople.size()][2];
					
					for(int i = 0; i < tablePeople.size(); i++) {
						data[i][0] = tablePeople.get(i).getName();
						data[i][1] = tablePeople.get(i).getAge();
					}
					
					JTable table = new JTable(data, columnNames);
					
					tabPanel.add(table);
					tabbedPane.addTab(givenName, tabPanel);
					
					getNameToOpen.dispose();
				}
			});
			
			JButton cancel = new JButton("cancel");
			cancel.setBounds(180, 80, 80, 25);
			cancel.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					getNameToOpen.dispose();
				}
			});
			panel.add(cancel);
			
		}
	};
	
	private final Action openAllUsers = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			Boolean tabSet = false; 
			
			List<Person> tempList = new ArrayList<Person>();
			for(int i = 0; i < people.size(); i++) {
				tempList.add(people.get(i));
			}
			
			for(int i = 0; i < tempList.size(); i++) {
				JPanel tabPanel = new JPanel();
				
				// provjeri je li ime već postoji među tabovim
				for(int j = 0; j < tabbedPane.getTabCount(); j++) {
					if(tabbedPane.getTitleAt(j).equals(tempList.get(i).getName())) {
						tabSet = true;
						break;
					}
				}
				
				if(tabSet) {
					tabSet = false;
					continue;
				}

				tabbedPane.add(tempList.get(i).getName(), tabPanel);
				
				String[] columnNames = {"Names", "Ages"};
				
				List<Person> tablePeople = new ArrayList<Person>();
				
				tablePeople.add(tempList.get(i));
				
				for(int k = i+1; k < tempList.size(); k++) {
					if(tempList.get(k).getName().equals(tempList.get(i).getName())) {
						tablePeople.add(tempList.get(k));
					}
				}
				
				for(int k = tempList.size()-1; k > i; k--) {
					if(tempList.get(k).getName().equals(tempList.get(i).getName())) {
						tempList.remove(k);
					}
				}
				
				Object[][] data = new Object[tablePeople.size()][2];
				
				for(int k = 0; k < tablePeople.size(); k++) {
					data[k][0] = tablePeople.get(k).getName();
					data[k][1] = tablePeople.get(k).getAge();
				}
				
				JTable table = new JTable(data, columnNames);
				
				tabPanel.add(table);
				tempList.remove(i);
				i--;
			}
		}
	};
	
	private void initGui() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menuUser = new JMenu("User");
		
		JMenuItem addUserItem = new JMenuItem(addUser);
		// set text sets name of menu item :)
		addUserItem.setText("Add user");
		menuUser.add(addUserItem);
		
		JMenuItem removeUserItem = new JMenuItem(removeUser);
		removeUserItem.setText("Remove user");
		menuUser.add(removeUserItem);
		
		JMenuItem openUsersItem = new JMenuItem(openUser);
		openUsersItem.setText("Open user");
		menuUser.add(openUsersItem);
		
		JMenuItem openAllUsersItem = new JMenuItem(openAllUsers);
		openAllUsersItem.setText("Open all users");
		menuUser.add(openAllUsersItem);
		
		menuBar.add(menuUser);
		
		JMenu menuInfo = new JMenu("Info");
		JMenuItem listNamesItem = new JMenuItem(listNames);
		listNamesItem.setText("List names");
		menuInfo.add(listNamesItem);
		
		menuBar.add(menuInfo);
		
		cp.add(tabbedPane);
		
		setJMenuBar(menuBar);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {new Gui().setVisible(true);});
	}
	
}
