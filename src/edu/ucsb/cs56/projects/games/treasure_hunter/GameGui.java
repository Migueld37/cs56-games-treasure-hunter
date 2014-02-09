package edu.ucsb.cs56.projects.games.treasure_hunter;

import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.Event;
/** A viewer class to see a picture I drew with 
 *  just three simple Java graphics objects, namely
 *  Rectangle, Line2D.Double, Ellipse2D.Double
 *  
 * @author Alex Wood
 * @version for UCSB CS56, W12, 02/16/2012
 */

public class GameGui
{
    Player player;
    Player treasure;
    GameComponent component;
    
   public static void main(String[] args)
   {

       GameGui gui = new GameGui();
       gui.go();
   }

    public void go() {
      JFrame frame = new JFrame();
      
      // Set the size to whatever size you like (width, height)
      // For projects you turn in, lets not get any bigger than 640,480
      
      frame.setSize(420,450); // @@@ MODIFY THIS LINE IF YOU LIKE

      // Set your own title
      frame.setTitle("Treasure Hunter"); // @@@ MODIFY THIS LINE
      
      // Always do this so that the red X (or red circle) works
      // to close the window. 
      
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      // Instantiate your drawing as a "component"
      player = new Player(0,0,16,8,"player");
      treasure = new Player(5,5,1,0,"treasure");
      component = new GameComponent();
      component.loadPlayer(player,"player");
      component.loadPlayer(treasure,"treasure");
      component.loadMap("map.txt");
      addBindings();
      // Always add your component to the frame 
      // and then make the window visible
      
      frame.add(component);
      frame.setVisible(true);
      component.repaint();
   }
    class MoveAction extends AbstractAction {
	int startingSprite = 0;
	int x = 0;
	int y = 0;
	
    public MoveAction(int x, int y) {
		if(x == -1)
			startingSprite = 4;
		if(x == 1)
			startingSprite = 8;
		if(y == -1)
			startingSprite = 12;
		if(y == 1)
			startingSprite = 0;
		this.x = x;
		this.y = y;
    }
	public void actionPerformed(ActionEvent e) {
		player.setSprite(startingSprite);
	    component.checkMove(player.getXTile() + x, player.getYTile() + y);
	    if(player.isMovable()) {
		player.setMovable(false);
		for(int i = 0; i < 50; i++) {
		    player.moveTo(player.getXPos() + x,player.getYPos()+y);
		    if(x !=0 || y!=0)
			player.setSprite(startingSprite+i/10);
		    if(player.getCurrentSprite() >= startingSprite + 4 && (x != 0 || y != 0))
			player.setSprite(startingSprite);
		    component.updatePlayer();
		    try{ Thread.sleep(10); }
		    catch(Exception ex) {}
		}
		player.setTiles(player.getXTile() + x, player.getYTile()+y);
	    }
	    component.repaint();
	}

    }

    

    public void addBindings() {

	component.registerKeyboardAction(new MoveAction(0,-1), KeyStroke.getKeyStroke(KeyEvent.VK_UP,0), JComponent.WHEN_FOCUSED);
	component.registerKeyboardAction(new MoveAction(0,1), KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0), JComponent.WHEN_FOCUSED);
	component.registerKeyboardAction(new MoveAction(-1,0), KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0), JComponent.WHEN_FOCUSED);
	component.registerKeyboardAction(new MoveAction(1,0), KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0), JComponent.WHEN_FOCUSED);
	//component.registerKeyboardAction(new MoveAction(0,0), KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,0), JComponent.WHEN_FOCUSED);
    }
}
