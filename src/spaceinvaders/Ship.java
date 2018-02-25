
package spaceinvaders;

import java.awt.*;
import javax.swing.ImageIcon;

public class Ship {

    private final int POSITION_X = 280;
    private final int POSITION_Y = 500;
    private int x, xMoved;
    private int y;
    private Image ship;
    private Image explosion;
    public boolean explode;

    public Image getShip() {
        return this.ship;
    }
    
    public Image getExplosion() {
        return this.explosion;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Ship() {
    ImageIcon i = new ImageIcon (getClass().getResource("/Media/spaceship.png"));
    ship = i.getImage();
        
         
        ImageIcon i2 = new ImageIcon (getClass().getResource("/Media/explosion.gif"));
     explosion= i2.getImage();
       
        
        explode=false;
        x = POSITION_X;
        y = POSITION_Y;

    }

    public void setImage(Image i) {
        this.ship = i;
    }
    
    

    public void setXMoved(int x) {
        this.xMoved = x;
    }

    public int getXMoved() {
        return this.xMoved;
    }
public void moveShip() {
        this.setX(this.x + this.xMoved);
        
        if (this.x <= 0) {
            this.setX(0);
        }
        if (this.getX() >= gameConstants.WIDTH - gameConstants.shipWidth) {
            this.setX(gameConstants.WIDTH - gameConstants.shipWidth);
        }
        

    }
}
