import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game implements Runnable {
  
	// true if the instructions page is in the frame
	private boolean instruction = false;
	// true if the high scores are displayed in the frame
	private boolean scoresOn = false;
	
	public void run() {
	   
      // Top-level frame
      final JFrame frame = new JFrame("Tron");
      frame.setBackground(Color.BLACK);
      frame.setPreferredSize(new Dimension(500,560));
      frame.setLocation(400, 100);
      frame.setResizable(false);
      
      /**
       * 
       *  Main Menu
       * 
       */
      // main menu panel
      final JPanel mainMenu = new JPanel();
      mainMenu.setLayout(new BorderLayout());
      mainMenu.setBackground(Color.BLACK);

      // main menu screen image
  	 @SuppressWarnings("serial")
      final JComponent pict = new JComponent() {
    	  public void paintComponent(Graphics gc) {
    		  super.paintComponent(gc);
    		  imageStorage.draw(gc, "background.jpg", 0, 0);
    	  }
      };

      // panel for main menu buttons
      final JPanel topMenu = new JPanel();
      topMenu.setLayout(new GridLayout(1,4));
      topMenu.setBackground(null);

      // buttons for main menu
	  final JButton play = new JButton(new ImageIcon("start_button.png"));
        play.setContentAreaFilled(false);
        play.setBorderPainted(false);
	  topMenu.add(play);

	  final JButton instructions = new JButton(new ImageIcon("controls_button.png"));
        instructions.setContentAreaFilled(false);
        instructions.setBorderPainted(false);
        topMenu.add(instructions);

      final JButton scoreboard = new JButton(new ImageIcon("score_button.png"));
        scoreboard.setContentAreaFilled(false);
        scoreboard.setBorderPainted(false);
      topMenu.add(scoreboard);

	  final JButton quit = new JButton(new ImageIcon("quit_button.png"));
        quit.setContentAreaFilled(false);
        quit.setBorderPainted(false);
	  topMenu.add(quit);


	  // adds components to the main menu panel
	  mainMenu.add(pict, BorderLayout.CENTER);
	  mainMenu.add(topMenu, BorderLayout.SOUTH);

	  // adds main menu panel to the frame
	  frame.add(mainMenu);

	  // instructions image
	 @SuppressWarnings("serial")
	  final JComponent instrPict = new JComponent() {
		  public void paintComponent(Graphics gc) {
			  super.paintComponent(gc);
			  imageStorage.draw(gc, "instructions_page.png", 0, 0);
		  }
	  };


      /**
       * 
       *  Two-player Menu
       * 
       */
	  // panel that holds the buttons and labels for two-player mode
	  final JPanel inGameMenu = new JPanel();
	  inGameMenu.setLayout(new GridLayout(1,4));
	  inGameMenu.setBackground(Color.BLACK);
	  
	  // panel that holds the scores and boost for each player
	  final JPanel scoresTwo = new JPanel();
	  scoresTwo.setLayout(new GridLayout(2,1));
	  scoresTwo.setBackground(Color.BLACK);
	  
	  // the score labels for each player
	  final JLabel scoreTwo1 = new JLabel("   Player 1: 0" + "    Boost: 3");
	  scoreTwo1.setForeground(Color.WHITE);
	  scoreTwo1.setBackground(Color.BLACK);
	  scoresTwo.add(scoreTwo1);
	  final JLabel inGameScore = new JLabel("   Player 2: 0" + "    Boost: 3");
	  inGameScore.setForeground(Color.WHITE);
	  inGameScore.setBackground(Color.BLACK);
	  scoresTwo.add(inGameScore);
	  inGameMenu.add(scoresTwo);
	  
	  // the reset and main menu buttons for two-player mode
	  final JButton resetGame = new JButton(new ImageIcon("restart_button.png"));
        resetGame.setContentAreaFilled(false);
        resetGame.setBorderPainted(false);
	  inGameMenu.add(resetGame);
	  final JButton exitToMenu = new JButton(new ImageIcon("menu_button.png"));
        exitToMenu.setContentAreaFilled(false);
        exitToMenu.setBorderPainted(false);
	  inGameMenu.add(exitToMenu);
	  
	  // the two-player level
      final GridDraw levelTwoPlayer = new GridDraw(scoreTwo1, inGameScore, 2);
      levelTwoPlayer.setBorder(BorderFactory.createLineBorder(Color.WHITE));


      /**
       *						    										
       *  Adding action listeners 					
       *						  										    
       */
	  play.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              //reset instructions and highscore page
              if (instruction) {
                  mainMenu.remove(instrPict);
                  mainMenu.add(pict);
                  instructions.setIcon(new ImageIcon("controls_button.png"));
                  instruction = !instruction;
              }
              else if (scoresOn){
                  mainMenu.removeAll();
                  mainMenu.add(pict, BorderLayout.CENTER);
                  mainMenu.add(topMenu, BorderLayout.SOUTH);
                  scoreboard.setIcon(new ImageIcon("score_button.png"));
                  scoresOn = !scoresOn;
              }
              frame.remove(mainMenu);
              frame.setLayout(new BorderLayout());
              frame.add(levelTwoPlayer, BorderLayout.CENTER);
              frame.add(inGameMenu, BorderLayout.SOUTH);
              frame.update(frame.getGraphics());
              levelTwoPlayer.requestFocusInWindow();
              levelTwoPlayer.revalidate();
              levelTwoPlayer.reset();
          }
	  });

	  instructions.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e) {
			  if (instruction) {
				  mainMenu.remove(instrPict);
				  mainMenu.add(pict);
				  instructions.setIcon(new ImageIcon("controls_button.png"));
			  }
			  else if (!instruction) {
				  if (scoresOn) {
                      mainMenu.removeAll();
                      mainMenu.add(pict, BorderLayout.CENTER);
                      mainMenu.add(topMenu, BorderLayout.SOUTH);
                      scoreboard.setIcon(new ImageIcon("score_button.png"));
                      scoresOn = !scoresOn;
                  }
                  mainMenu.remove(pict);
                  mainMenu.add(instrPict);
                  instructions.setIcon(new ImageIcon("back_button.png"));
			  }
			  mainMenu.revalidate();
			  frame.repaint();
			  instruction = !instruction;
		  }
	  });
	  
	  quit.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e) {
			  System.exit(1);
		  }
	  });
	  
	  scoreboard.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e) {
			  if (scoresOn) {
				  mainMenu.removeAll();
				  mainMenu.add(pict, BorderLayout.CENTER);
				  mainMenu.add(topMenu, BorderLayout.SOUTH);
				  scoreboard.setIcon(new ImageIcon("score_button.png"));
			  } else if (!scoresOn) {
                  if (instruction) {
                      mainMenu.remove(instrPict);
                      mainMenu.add(pict);
                      instructions.setIcon(new ImageIcon("controls_button.png"));
                      instruction = !instruction;
                  }
				  mainMenu.remove(pict);
				  mainMenu.add(levelTwoPlayer.getHighs());
				  scoreboard.setIcon(new ImageIcon("back_button.png"));
			  }
			  scoresOn = !scoresOn;
			  mainMenu.revalidate();
			  frame.repaint();
		  }
	  });
	  

	  resetGame.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e) {
			  levelTwoPlayer.reset();
		  }
	  });
	  
	  exitToMenu.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e) {
			  frame.remove(levelTwoPlayer);
			  frame.remove(inGameMenu);
			  frame.add(mainMenu);
			  frame.update(frame.getGraphics());
			  mainMenu.revalidate();
			  levelTwoPlayer.restartGame();
		  }
	  });
	  

      // put the frame on the screen
      frame.pack();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);

      // start the game running
      levelTwoPlayer.reset();
   }
   
   /*
    * Get the game started!
    */
   public static void main(String[] args) {
       SwingUtilities.invokeLater(new Game());
   }
}
