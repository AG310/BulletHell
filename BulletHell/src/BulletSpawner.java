import java.util.LinkedList;
import java.util.Random;

import javafx.scene.layout.Pane;

public class BulletSpawner{
	
	private double gamePaneHeight;
	private double gamePaneWidth;
	private Pane gamePane;
	private LinkedList<Bullet> spawnedBullets;
	
	public BulletSpawner(Pane gamePane) {
		this.gamePane = gamePane;
		gamePaneHeight = gamePane.getPrefHeight();
		gamePaneWidth = gamePane.getPrefWidth();
		spawnedBullets = new LinkedList<Bullet>();
	}
	
	public LinkedList<Bullet> spawnRandomBullets(){
		spawnedBullets.clear();
		Random random = new Random();
		int numBullets = random.nextInt(25)+10;
		for(int i=0 ; i<numBullets; i++) {
			double startX = random.nextDouble()*gamePaneWidth;
			double startY = random.nextDouble()*gamePaneHeight*0.1;
			int xTrajectory = random.nextInt(2);
			if(random.nextInt(2)==1) {
				xTrajectory*=-1;
			}
			int yTrajectory = random.nextInt(3)+2;
			spawnedBullets.add(new Bullet(gamePane, startX, startY, xTrajectory, yTrajectory));
		}
		return spawnedBullets;
	}
}
