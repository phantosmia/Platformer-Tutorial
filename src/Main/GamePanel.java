package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import Entity.Player;
import TileMap.TileMap;

public class GamePanel extends JPanel implements Runnable, KeyListener {
	public static final int WIDTH = 400;
	public static final int HEIGHT = 400;
	private Thread thread;
	private boolean running;
	private BufferedImage image;
	private Graphics2D g;
	private int FPS = 30;
	private int targetTime = 1000 / FPS;
	private TileMap tileMap;
	private Player player;
	public GamePanel(){
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
	}
	public void addNotify(){
		super.addNotify();
		if(thread == null){
			thread = new Thread(this);
			thread.start();
		}
		addKeyListener(this);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		init();
		long startTime;
		long urdTime;
		long waitTime;
		while(running){
			startTime = System.nanoTime();
			update();
			render();
			draw();
			urdTime = (System.nanoTime() - startTime) / 1000000;
			waitTime = targetTime - urdTime;
			try{
				Thread.sleep(waitTime);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	private void init(){
		running = true;
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D)image.getGraphics();
		tileMap = new TileMap("graphics/testmap2.txt",32);
		tileMap.loadTiles("graphics/tileset.gif");
		player = new Player(tileMap);
		player.setX(50);
		player.setY(50);
	}
	private void update(){
		tileMap.update();
		player.update();
	}
	private void render(){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		tileMap.draw(g);
		player.draw(g);
	}
	private void draw(){
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		int code = arg0.getKeyCode();
		if(code == KeyEvent.VK_LEFT){
			player.setLeft(true);
		}
		if(code == KeyEvent.VK_RIGHT){
			player.setRight(true);
		}
		if(code == KeyEvent.VK_UP){
			player.setJumping(true);
		}
		
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		int code = arg0.getKeyCode();
		if(code == KeyEvent.VK_LEFT){
			player.setLeft(false);
		}
		if(code == KeyEvent.VK_RIGHT){
			player.setRight(false);
		}
		
		if(code == KeyEvent.VK_UP){
			//player.setJumping(false);
		}
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
