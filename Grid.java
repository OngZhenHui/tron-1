	import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;

@SuppressWarnings("serial")
public abstract class Grid extends JComponent {
	
	// the player and all other players
	addPlayer player;
	Player[] players;
	Color[] colors = {Color.CYAN, Color.PINK, Color.WHITE, Color.YELLOW,
					  Color.BLUE, Color.ORANGE, Color.RED, Color.GREEN};
	
	Random rand = new Random();

	//declare player name as blank first
	String p1_name;
	String p2_name;

	// court dimensions
	int MAPWIDTH = 500;
	int MAPHEIGHT = 500;	
	
	// initial velocity
	int VELOCITY = 3;
	
	// score and score labels
	int i = 0;
	JLabel score1;
	JLabel score2;
	
	// the game timer and speed at which tick() is called
	int interval = 20;
	Timer timer;
	boolean run = true;
	
	// constructor adds KeyListeners and initializes fields
	public Grid(JLabel sco1, JLabel sco2, int p) {
		//asks for player 1 and 2 name at the start
		p1_name = JOptionPane.showInputDialog(null, "Enter name of player 1:", "Player 1",
				JOptionPane.INFORMATION_MESSAGE);
		p2_name = JOptionPane.showInputDialog(null, "Enter name of player 2:", "Player 2",
				JOptionPane.INFORMATION_MESSAGE);

		setBackground(Color.WHITE);
		if (p > 8) { p = 8; }
		this.players = new Player[p];
		this.score1 = sco1;
		this.score2 = sco2;
		
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setFocusable(true);
		
		// timer that runs the game
		timer = new Timer(interval, new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				tick();
			}
		});
		timer.start();
		
		// player one controls
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (!player.getAlive()) {
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					player.setXVelocity(-VELOCITY);
					player.setYVelocity(0);
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					player.setXVelocity(VELOCITY);
					player.setYVelocity(0);
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					player.setYVelocity(-VELOCITY);
					player.setXVelocity(0);
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					player.setYVelocity(VELOCITY);
					player.setXVelocity(0);
				} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					player.jump();
				} else if (e.getKeyCode() == KeyEvent.VK_B) {
					player.startBoost();
				}
			}
			public void keyReleased(KeyEvent e) {
			}
		});
	}

	// returns an array of velocities and dimensions for a Player
	// ensures that the Player moves toward the center initially
	public int[] getRandomStart() {
		int[] start = new int[4];
		int xnew = 50 + rand.nextInt(400);
		int ynew = 50 + rand.nextInt(400);
		int ra = rand.nextInt(2);
		int velx = 0;
		int vely = 0;
		if (ra == 0) {
			if (xnew < 250) {
				velx = VELOCITY;
			} else {
				velx = -VELOCITY;
			}
		} else {
			if (ynew < 250) {
				vely = VELOCITY;
			} else {
				vely = -VELOCITY;
			}
		}
		start[0] = xnew;
		start[1] = ynew;
		start[2] = velx;
		start[3] = vely;
		return start;
	}
	
	// returns the velocity
	public int getVelocity() {
		return VELOCITY;
	}
	
	// moves the game by one timestamp
	abstract void tick();
	
	// initializes all new characters and restarts the timer
	abstract void reset();
	
	// changes the score being displayed
	abstract void setScore();
	
	// adds scores to high scores or sets the score after a level
	abstract void addScore();

	// draws the Player objects
   @Override
	public void paintComponent(Graphics g) {
	   super.paintComponent(g);
	   g.setColor(Color.BLACK);
	   g.fillRect(0, 0, MAPWIDTH, MAPHEIGHT);
	   for (Player p: players) {
		   if (p != null) {
			   p.draw(g);
		   }
	   }
	}
   
    // sets the dimensions of the court
   @Override
    public Dimension getPreferredSize() {
	   return new Dimension(MAPWIDTH, MAPHEIGHT);
    }
}