package Interface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import mapInfo.Region;

public class windowMaker extends JFrame {

	private int DEFAULT_WIDTH;
	private int DEFAULT_HEIGHT;
	
	int screenWidth;
	int screenHeight;
	
	private JFrame optionFrame;
	private JPanel optionPanel;
	private JPanel infoPanel;
	private JButton chooseMap;
	private JButton defaultMap;
	private JButton startWalk;
	
	private Region region;
	
	public windowMaker(int w, int h) {
		
		DEFAULT_WIDTH = (w * 41) + (w/2) + 200;
		DEFAULT_HEIGHT = (h * 41) + (2*h);
		region = new Region(w, h);
		
		getScreenDimensions();
		
		infoPanel = new JPanel();
		infoPanel.setLayout(new BorderLayout());
		infoPanel.setBounds((w * 41), 0, 200, (h * 41) - (2 * h));
		
		setMenu();
	}
	
	public void getScreenDimensions() {
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		screenWidth = screenSize.width;
		screenHeight = screenSize.height;
	}
	
	public void setMenu() {
		
		optionFrame = new JFrame();
		optionPanel = new JPanel();
		int oFrameWidth = 300;
		int oFrameHeight = 100;
		int xPos = (screenWidth - oFrameWidth)/2;
		int yPos = (screenHeight - oFrameHeight)/2;	
		
		optionFrame.setBounds(xPos, yPos, oFrameWidth,oFrameHeight);
		optionFrame.getContentPane().add(optionPanel);
		
		optionPanel.setLayout(new BorderLayout());
		defaultMap = new JButton("LOAD DEFAULT MAP");
		optionPanel.add(defaultMap, BorderLayout.NORTH);
		chooseMap = new JButton("SEARCH A MAP");
		optionPanel.add(chooseMap, BorderLayout.SOUTH);
		
		optionFrame.setResizable(true);
		optionFrame.setTitle("Choose a map");
		optionFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		optionFrame.setVisible(true);
		
		chooseMap.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) { 
				choosingMap();
				optionFrame.setVisible(false);
				setMap();
			}
		});
		
		defaultMap.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) { 
				loadDefaultMap();
				optionFrame.setVisible(false);
				setMap();
			}
		});
	}

	private void choosingMap() {
		
		File file;
		FileReader fr = null;
		int answ;
		
		// Para carregar fileuivo de caracteres
		BufferedReader br = null;
		
		JFileChooser loadFrom = new JFileChooser();

		FileFilter filter = new FileNameExtensionFilter("Text file (*.txt)", "txt");
		loadFrom.addChoosableFileFilter(filter);
		loadFrom.setFileFilter(filter);

		answ = loadFrom.showOpenDialog(this);

		if(answ == JFileChooser.APPROVE_OPTION) {
		
			file = loadFrom.getSelectedFile();
			
			if (file.canRead() && file.exists()) {
				try {

					// Passando o fileuivo para o FileReader, foi necessario para poder usar no BufferedReader...
					fr = new FileReader(file);

					br = new BufferedReader(fr);
					region.loading(br);
					br.close();
				} 
				catch (IOException e) {

					System.out.println(e.getMessage());
				}
			}
			else {
				
				System.exit(1);
			}
		}
		else {
			
			System.exit(1);
		}
	}

	private void loadDefaultMap() {
		
		File file;
		FileReader fr = null;
		
		// Para carregar fileuivo de caracteres
		BufferedReader br = null;
		
		file = new File("Maps/IA_2016.2_Trabalho_1_Mapa.txt");
		
		if (file.canRead() && file.exists()) {
			try {

				// Passando o fileuivo para o FileReader, foi necessario para poder usar no BufferedReader...
				fr = new FileReader(file);

				br = new BufferedReader(fr);
				region.loading(br);
				br.close();
			} 
			catch (IOException e) {

				System.out.println(e.getMessage());
			}
		}
		else {
			
			System.exit(1);
		}
	}
	
	private void setMap() {
		
		int xPos = (screenWidth - DEFAULT_WIDTH)/2;
		int yPos = (screenHeight - DEFAULT_HEIGHT)/2;
		
		setBounds(xPos, yPos, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setResizable(true);
		setTitle("Little Red Riding Hood's Adventure");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		startWalk = new JButton("START WALKING");
		infoPanel.add(startWalk, BorderLayout.PAGE_END);
		
		startWalk.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) { 
				// TODO Here enters the path method calling
			}
		});
		
		JTextArea ta = new JTextArea();
		ta.setBounds(50, 0, 300, 300);
		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);
		ta.setEditable(false);
		ta.setText("Here we will put all the info:\n-Candies with quantity\n-Total time");
		infoPanel.add(ta);
		
		getContentPane().add(infoPanel);
		getContentPane().add(region);
		
		setVisible(false);
	}
}