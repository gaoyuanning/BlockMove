import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import org.python.antlr.PythonParser.return_stmt_return;


public class Block {
	int x;
	int y;
	int position;
	int type;
	Client client;
		
	public Block(Client client) {
		this.client = client;
	}
	
	public Block(Client client, int type) {
		this.type = type;
		this.client = client;
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	
	public int getType() {
		return this.type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	//图片宽度和高度为28
	public static final int imageWidth = 76;
	public static final int imageHeight = 76;
	static Toolkit toolkit = Toolkit.getDefaultToolkit();
	static Image[] blockImages = null;
	static {
		blockImages = new Image[] {
			toolkit.getImage(Block.class.getResource("images/green.png")),
			toolkit.getImage(Block.class.getResource("images/purple.png"))
		};
		
	}
		
	public void draw(Graphics g) {
		if(this.type == client.BLACK) {
			g.drawImage(blockImages[0], x, y, null);
			/*Color c = g.getColor();
			g.setColor(Color.BLACK);
			g.fillRect(x, y, imageWidth, imageHeight);
			g.setColor(c);*/
		} else if(this.type == client.WHITE) {
			g.drawImage(blockImages[1], x, y, null);
		/*	Color c = g.getColor();
			g.setColor(Color.WHITE);
			g.fillRect(x, y, imageWidth, imageHeight);
			g.setColor(c);*/
		} else if(this.type == client.SPACE){
			/*Color c = g.getColor();
			g.setColor(Color.BLUE);
			g.drawRect(x, y, imageWidth, imageHeight);
			g.setColor(c);*/
		}
	}
}
