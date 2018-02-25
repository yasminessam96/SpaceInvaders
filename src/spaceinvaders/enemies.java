package spaceinvaders;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.ImageIcon;

abstract class enemies 
{
    protected int x;
    protected int y;
    protected Image img;
    protected boolean visible;
    
    public enemies (int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    public int getX ()
    {
        return x;
    }
    public int getY ()
    {
        return y;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public Image getImg ()
    {
        return img;
    }
    public boolean getVisible(){
        return this.visible;
    } 
    
    public void setVisible(boolean x){
        visible = x;
    }
}
class greenEnemy extends enemies 
{
    public greenEnemy (int x , int y)
    {
        super (x,y);
        this.visible = true;
       ImageIcon i = new ImageIcon (getClass().getResource("/Media/inv1.png"));
     img= i.getImage();
     
     
    }
    public greenEnemy (int x , int y, boolean v)
    {
        super (x,y);
        this.visible = v;
 ImageIcon i = new ImageIcon (getClass().getResource("/Media/inv1.png"));
     img= i.getImage();
     
    }
}
class purpleEnemy extends enemies 
{
    public purpleEnemy (int x , int y, boolean v)
    {
        super (x,y);
        this.visible = v;
      ImageIcon i = new ImageIcon (getClass().getResource("/Media/inv2.png"));
     img= i.getImage();
       
    }
    public purpleEnemy (int x, int y)
    {
        super (x,y);
        this.visible = true;
 ImageIcon i = new ImageIcon (getClass().getResource("/Media/inv2.png"));
     img= i.getImage();

    }
}
class blueEnemy extends enemies 
{
    public blueEnemy (int x , int y, boolean v)
    {
        super (x,y);
        this.visible = v;
          ImageIcon i = new ImageIcon (getClass().getResource("/Media/inv3.png"));
     img= i.getImage();
      
    }
    public blueEnemy (int x, int y)
    {
        super (x,y);
        this.visible = true;
        ImageIcon i = new ImageIcon (getClass().getResource("/Media/inv3.png"));
     img= i.getImage();
 
    }
}
enum direction {RIGHT, LEFT,DOWN}
class enmBullet 
{
    private int x;
    private int y;
    private int dy;
    private boolean visible;
    public enmBullet(int x, int y){
        this.x = x;
        this.y = y;
        dy = 5;
        visible = false;
    }
    public enmBullet (int x , int y , boolean v)
    {
        this.x = x;
        this.y = y;
        dy = 5;
        visible = v; 
    }
    public void move(){
        this.y += dy;
        if (this.y == 570)
            this.visible = false;
    }
       
    public int getX(){
    return this.x;
    }
    
    public int getY(){
    return this.y;
    }
    
    public void setX(int x){
        this.x = x;
    
    }
    
    public void setY(int y){
        this.y = y;
    }
    
    public void setVisible(boolean v){
        this.visible = v;
    }    
    public boolean getVisible(){
        return this.visible;
    }
}


