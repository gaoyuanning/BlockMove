import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.RepaintManager;


public class Client extends Frame{
	
	public static final int GAME_WIDTH = 1000;	//1010
	public static final int GAME_HEIGHT = 625;
	public static final int gap = 1;
	
	public static final int BLACK = 0;
	public static final int WHITE = 1;
	public static final int SPACE = 2;
	
	static Toolkit toolkit = Toolkit.getDefaultToolkit();
	static Image settingImage = null;
	static {
		settingImage = toolkit.getImage(Block.class.getResource("images/setting.jpg"));
	}
	
	int blockCount = 4;
	Block[] blocks = null;
	int step = 0;
	int price = 0;
	
	static String[] resultStrings = null;
	static int steps = 0;
	
	public static void main(String[] args) {
		try {
			Start dialog = new Start();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initBlocks() {
		int i = 0;
		int x = 150;
		int y = 450;
		blocks = new Block[blockCount * 2 + 1];
		for(i = 0; i < blockCount * 2 + 1; i++) {
			blocks[i] = new Block(this);
		}
		
		for(i = 0; i < blockCount; i++) {
			blocks[i].setType(BLACK);
			blocks[i].setPosition(x, y);
			x += Block.imageWidth + gap;
		}
		for(; i < blockCount * 2; i++) {
			blocks[i].setType(WHITE);
			blocks[i].setPosition(x, y);
			x += Block.imageWidth + gap;
		}
		blocks[i].setType(SPACE);
		blocks[i].setPosition(x, y);
	}
	
	public void launchFrame() {
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setBackground(Color.CYAN);
		this.setLocation(180, 50);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setResizable(false);
		this.setVisible(true);
		PaintThread paintThread = new PaintThread();
		paintThread.start();
	}

	Image offScreenImage = null;
	public void paint(Graphics g) {
		g.drawImage(settingImage, 0, 0, null);
		Color c = g.getColor();
		g.setColor(Color.YELLOW);
		g.drawString("step: " + step, 8, 45);
		g.drawString("price: " + price, 8, 65);
		g.setColor(c);
		for(int i = 0; i < blocks.length; i++) {
			blocks[i].draw(g);
		}
	}
	
	public void update(Graphics g) {
		if(offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);	
		}
		
		Graphics graphicsOffScreen = offScreenImage.getGraphics();
		paint(graphicsOffScreen);
		
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	class PaintThread extends Thread {
		int cnt = 1;
		int prePosition = blocks.length - 1;
		
		//prePosition上一次移动完之后的空格位置7，curPosition当前要移动到空格的木块6
		public void move(int prePosition, int curPosition) {
			int horizonMove = (prePosition > curPosition) ? 4 : -4;
			int verticalMove = 4;
			int moveDist = (prePosition - curPosition) * Block.imageWidth;
			//Block tempBlock = blocks[curPosition];
			Block tempBlock = new Block(Client.this);
			tempBlock.setPosition(blocks[curPosition].getX(), blocks[curPosition].getY());
			tempBlock.setType(blocks[curPosition].type);
			
			try {
				for(int i = 0; i < Block.imageHeight; i += verticalMove) {
					blocks[curPosition].y -= verticalMove;
					repaint();
					sleep(20);
				}
				for(int i = 0; i != moveDist; i+= horizonMove) {
					blocks[curPosition].x += horizonMove;
					repaint();
					sleep(20);
				}
				
				//调整位置
				
				blocks[curPosition].x += (prePosition - curPosition) * gap;
				
				repaint();
				sleep(20);
				
				for(int i = 0; i < Block.imageHeight; i += verticalMove) {
					blocks[curPosition].y += verticalMove;
					repaint();
					sleep(30);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			blocks[prePosition] = blocks[curPosition];
			blocks[curPosition] = tempBlock;
			blocks[curPosition].type = SPACE;
		}
		
		public void run() {
			repaint();
			while(cnt < steps) {
				try {
					String singleMove = resultStrings[cnt];
					int emptyPosition = singleMove.indexOf("E");
					move(prePosition, emptyPosition);
					int moveDist = (Math.abs(prePosition - emptyPosition) > 2) ? 2 : 1 ;
					price += moveDist;
					prePosition = emptyPosition;
					repaint();
					step++;
					sleep(30);
					cnt++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				new JFrameBackground();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}



















