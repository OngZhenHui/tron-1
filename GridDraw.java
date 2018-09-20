import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class GridDraw extends Grid {

	// creates the list of high scores
	Score highs = new Score("HighScores.txt");
	List<Integer> highScores = new ArrayList<Integer>();

	// create second human player
	private addPlayer player2;

	// scores of player one and two
	private int p1_score = 0;
	private int p2_score = 0;

	// outcome of the match
	private boolean p1 = false;
	private boolean p2 = false;
	private boolean tie = false;

	// constructor calls super, adds KeyListeners and get current high scores
	public GridDraw(JLabel sco1, JLabel sco2, int p) {
		super(sco1, sco2, p);


		//get high score
		this.highScores = highs.getHighScores();

		// adds KeyListeners for player two
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (!player2.getAlive()) {
				} else if (e.getKeyCode() == KeyEvent.VK_A) {
					player2.setXVelocity(-VELOCITY);
					player2.setYVelocity(0);
				} else if (e.getKeyCode() == KeyEvent.VK_D) {
					player2.setXVelocity(VELOCITY);
					player2.setYVelocity(0);
				} else if (e.getKeyCode() == KeyEvent.VK_W) {
					player2.setYVelocity(-VELOCITY);
					player2.setXVelocity(0);
				} else if (e.getKeyCode() == KeyEvent.VK_S) {
					player2.setYVelocity(VELOCITY);
					player2.setXVelocity(0);
				} else if (e.getKeyCode() == KeyEvent.VK_Q) {
					player2.jump();
				} else if (e.getKeyCode() == KeyEvent.VK_1) {
					player2.startBoost();
				}
			}
			public void keyReleased(KeyEvent e) {
			}
		});
	}

	// moves both players and checks if they crash
	void tick() {
		player.setBounds(getWidth(), getHeight());
		player.move();
		player2.setBounds(getWidth(), getHeight());
		player2.move();
		for (Player k1: players) {
			for (Player k2: players) {
				k1.crash(k1.intersects(k2));
			}
		}
		if (!player.getAlive() || !player2.getAlive()) {
			timer.stop();
			run = false;
			addScore();
		}
		setScore();
		repaint();
	}

	// restarts the score if the game is exited
	public void restartGame() {
		p1_score = 0;
		p2_score = 0;
	}

	// sets the players' scores and displays the boost left in game
	public void setScore() {
		score1.setText("   P1 Score: " + ++p1_score + "    Boost: " + player.getBoostsLeft());
		score1.repaint();

		score2.setText("   P2 Score: " + ++p2_score + "    Boost: " + player2.getBoostsLeft());
		score2.repaint();
	}

	// initializes all players and restarts the timer
	public void reset() {
		p1 = false;
		p2 = false;
		tie = false;
		int[] start1 = getRandomStart();
		player = new addPlayer
				(start1[0], start1[1], start1[2], start1[3], Color.CYAN);
		players[0] = player;
		int[] start2 = getRandomStart();
		player2 = new addPlayer
				(start2[0], start2[1], start2[2], start2[3], Color.PINK);
		players[1] = player2;
		p1_score = 0;
		p2_score = 0;
		timer.start();
		requestFocusInWindow();
	}

	// updates the scores after each round
	public void addScore() {
		if (!run) {
			if (player2.getAlive()) {
				p2 = true;
			}
			else if (player.getAlive()) {
				p1 = true;
			}
			else {
				tie = true;
			}
		}
        try {
            highs.addHighScore(p1_score + 1);
            highScores = highs.getHighScores();
            }
            catch (IOException e) {
            }
		score1.repaint();
		score2.repaint();
	}

	public JPanel getHighs() {

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6, 2));
		panel.setBackground(null);

		JLabel j0 = new JLabel("    High Scores: ");
		j0.setForeground(Color.WHITE);
		j0.setBackground(null);
		panel.add(j0);

		JLabel j100 = new JLabel("");
		j100.setBackground(null);
		panel.add(j100);

		int left = 1;
		int right = 6;
		for (int i = 0; i < 5; i++) {
			JLabel j1 = new JLabel("      " + left + ".) " + highScores.get(left - 1).toString());
			j1.setForeground(Color.WHITE);
			j1.setBackground(null);
			panel.add(j1);
			JLabel j6 = new JLabel("      " + right + ".) " + highScores.get(right - 1).toString());
			j6.setForeground(Color.WHITE);
			j6.setBackground(null);
			panel.add(j6);
			left++;
			right++;
		}
		return panel;
	}

	// draws the outcome of each match
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (p1) {
			try {
				BufferedImage picture = ImageIO.read(new File("p1_wingame.png"));
				g.drawImage
						(picture, MAPWIDTH / 2 - 140, MAPHEIGHT / 2 - 30, null);
			} catch (IOException e) {
			}
		}
		if (p2) {
			try{
				BufferedImage picture = ImageIO.read(new File("p2_wingame.png"));
				g.drawImage
						(picture, MAPWIDTH / 2 - 140, MAPHEIGHT / 2 - 30, null);
			} catch (IOException e) {
			}
		}
		if (tie) {
			try {
				BufferedImage picture = ImageIO.read(new File("game_tie.png"));
				g.drawImage
						(picture, MAPWIDTH / 2 - 140, MAPHEIGHT / 2 - 30, null);
			} catch (IOException e) {
			}
		}
	}
}