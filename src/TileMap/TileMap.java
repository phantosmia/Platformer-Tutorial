package TileMap;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class TileMap {
	private int x;
	private int y;
	private int tileSize;
	private int[][] map;
	private int mapWidth ;
	private int mapHeight;
	private BufferedImage tileset;
	private Tile[][] tiles;
	private int minx;
	private int miny;
	private int maxx = 0;
	private int maxy = 0;
	public TileMap(String s, int tileSize){
		this.tileSize = tileSize;
		try{
			BufferedReader br = new BufferedReader(new FileReader(s));
			mapWidth = Integer.parseInt(br.readLine());
			mapHeight = Integer.parseInt(br.readLine());
			map = new int[mapHeight][mapWidth];
			minx = GamePanel.WIDTH - mapWidth * tileSize;
			miny = GamePanel.HEIGHT - mapHeight * tileSize;
			
			String delimiters = "\\s+";
			for(int row = 0; row < mapHeight; row++){
				String line = br.readLine();
				String[] tokens = line.split(delimiters);
				for(int col = 0; col < mapWidth; col++){
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public void update(){
		
	}
	public boolean isBlocked(int row, int col){
		int rc = map[row][col];
		int r = rc / tiles[0].length;
		int c = rc % tiles[0].length;
		return tiles[r][c].isBlocked();
	}
	public void draw(Graphics2D g){
		for(int row = 0; row < mapHeight; row++){
			for(int col = 0; col < mapWidth; col++){
				int rc = map[row][col];
				int r = rc / tiles[0].length;
				int c = rc % tiles[0].length;
				g.drawImage(tiles[r][c].getImage(),
						x + col * tileSize, y + row * tileSize,null);
			}
		}
	}
	public void loadTiles(String s){
		try{
			tileset = ImageIO.read(new File(s));
			int numTilesAcross = (tileset.getWidth() + 1) / (tileSize + 1);
			tiles = new Tile[2][numTilesAcross];
			BufferedImage subimage;
			for(int col = 0; col < numTilesAcross; col++){
				subimage = tileset.getSubimage(col * tileSize + col, 0, tileSize, tileSize);
				tiles[0][col] = new Tile(subimage, false);
				subimage = tileset.getSubimage(col * tileSize + col, tileSize + 1, tileSize, tileSize);
				tiles[1][col] = new Tile(subimage, true);
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void setX(int x){
		this.x = x;
		if(this.x < minx){
			this.x = minx;
		}
		if(this.x > maxx){
			this.x = maxx;
		}
	}
	public void setY(int y){
		this.y = y;
		if(this.y < miny){
			this.y = miny;
		}
		if(this.y > maxy){
			this.y = maxy;
		}
	}

	public int getTileSize(){
		return tileSize;
	}
	public int getColTile(int x){
		return x / tileSize;
	}
	public int getRowTile(int y){
		return y / tileSize;
	}
}
