import java.awt.Color;

public class addPlayer extends Player {

	public addPlayer(int randX, int randY, int velx, int vely, Color color) {
		super(randX, randY, velx, vely, color);
	}
	

	public void addPlayers(Player[] players) {
	}

	// moves the Player based on its conditions
	public void move() {
		int a = x;
		int b = y;
		boost();
		
		if (!jump) {
			x += velocityX;
			y += velocityY;
			if (lines.size() > 1) {
				lightCycleBike l1 = lines.get(lines.size() - 2);
				lightCycleBike l2 = lines.get(lines.size() - 1);
				if (a == l1.getStartX() && 
						l1.getEndY() == l2.getStartY()) {
					lines.add(new jetWall(l1.getStartX(), l1.getStartY(),
							l2.getEndX(), l2.getEndY()));
					lines.remove(lines.size() - 2);
					lines.remove(lines.size() - 2);
				} else if (b == l1.getStartY() && 
						l1.getEndX() == l2.getStartX()) {
					lines.add(new jetWall(l1.getStartX(), l1.getStartY(),
							l2.getEndX(), l2.getEndY()));
					lines.remove(lines.size() - 2);
					lines.remove(lines.size() - 2);
				} 
			}
			lines.add(new jetWall(a, b, x, y));
		} else {
			if (velocityX > 0) {
				x += JUMPHEIGHT;
			} else if (velocityX < 0) {
				x -= JUMPHEIGHT;
			} else if (velocityY > 0) {
				y += JUMPHEIGHT;
			} else if (velocityY < 0) {
				y -= JUMPHEIGHT;
			}
			jump = false;
		}
		accelerate();
		clip();
	}
	
}