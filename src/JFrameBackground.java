import java.awt.FlowLayout;  
  
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;  
import javax.swing.JButton;  
import javax.swing.JFrame;  
import javax.swing.JLabel;  
import javax.swing.JPanel;  
  
public class JFrameBackground {  
  
 private JFrame frame = new JFrame("游戏结束");  
  
 private JPanel imagePanel;  
  
 private ImageIcon background;  
 
 static Toolkit toolkit = Toolkit.getDefaultToolkit();
	static Image settingImage = null;
	static {
		settingImage = toolkit.getImage(Block.class.getResource("images/gameOver.jpg"));
	}
	
 /*public static void main(String[] args) {  
  new JFrameBackground();  
 }  */
  
 public JFrameBackground() {  
  background = new ImageIcon(settingImage);// 背景图片  
  JLabel label = new JLabel(background);// 把背景图片显示在一个标签里面  
  // 把标签的大小位置设置为图片刚好填充整个面板  
  label.setBounds(0, 0, background.getIconWidth(),  
    background.getIconHeight());  
  // 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明  
  imagePanel = (JPanel) frame.getContentPane();  
  imagePanel.setOpaque(false);  
  // 内容窗格默认的布局管理器为BorderLayout  
  imagePanel.setLayout(new FlowLayout());   
  
  frame.getLayeredPane().setLayout(null);  
  // 把背景图片添加到分层窗格的最底层作为背景  
  frame.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));  
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
  frame.setSize(background.getIconWidth(), background.getIconHeight()); 
  frame.setLocation(300, 180);
  frame.setResizable(false);  
  frame.setVisible(true);
 }  
}  
