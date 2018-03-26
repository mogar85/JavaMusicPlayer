package Display;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;

import javazoom.jl.player.Player;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileInputStream;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

public class PlayerGUI {

	private JFrame frmPlzDontStop;
	private JTextField txtPathField;
	private JRadioButton rdbtnLoop;
	private File songFile;
	private File[] songFiles;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					
					PlayerGUI window = new PlayerGUI();
					window.frmPlzDontStop.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PlayerGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPlzDontStop = new JFrame();
		frmPlzDontStop.setTitle("Dont stop music");
		frmPlzDontStop.setBounds(100, 100, 348, 186);
		frmPlzDontStop.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPlzDontStop.setLocationRelativeTo(null);
		frmPlzDontStop.getContentPane().setLayout(null);
		
		ViewTwo();
	}
	
	private void ViewTwo()
	{
		// ** START/Play BUTTON ** \\
		JButton btnStart = new JButton("Start");
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnStart.setBounds(150, 48, 168, 76);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// play audio
				
				try {
					//Player p = new Player(new FileInputStream(songFile));
					Thread t = new Thread(new Runnable() {
						@Override
						public void run() {
							PlayMusicQue(songFiles, txtPathField);
						}
					});
					t.start();
				} catch (Exception e) { 
					e.printStackTrace();
				}
			}
		});
		frmPlzDontStop.getContentPane().add(btnStart);
		
		// ** Path text field ** \\
		txtPathField = new JTextField();
		txtPathField.setEditable(false);
		txtPathField.setText("Song path");
		txtPathField.setBounds(12, 13, 197, 22);
		txtPathField.setColumns(10);
		frmPlzDontStop.getContentPane().add(txtPathField);
		
		// ** OPEN BUTTON ** \\
		JButton btnOpen = new JButton("Open");
		btnOpen.setBounds(221, 12, 97, 25);
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// open file
				try {
					JFileChooser chooser = new JFileChooser("D:\\Musik");
					chooser.setMultiSelectionEnabled(true);
					chooser.setDialogTitle("Choose song to load");
					chooser.showOpenDialog(null);
					songFiles = chooser.getSelectedFiles();
					if (songFiles.length == 0)
						return;
					txtPathField.setText(songFiles[0].getName());
					System.out.println("File: " + songFiles[0].getName() + ", Selected");
				} catch(Exception e2)
				{
					e2.printStackTrace();
				}
			}
		});
		frmPlzDontStop.getContentPane().add(btnOpen);
		
		// ** LOOP BUTTON ** \\
		rdbtnLoop = new JRadioButton("Loop");
		rdbtnLoop.setBounds(15, 48, 127, 25);
		frmPlzDontStop.getContentPane().add(rdbtnLoop);
	}
	
	private void PlayMusicQue(File[] files, JTextField txtField)
	{
		int index = 0;
		try {
			
			while(index < files.length)
			{
				Player p = new Player(new FileInputStream(files[index]));
				txtField.setText(files[index].getName());
				p.play();
				index++;
			}
			if(rdbtnLoop.isSelected())
				PlayMusicQue(files, txtField);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


