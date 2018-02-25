package spaceinvaders;

import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

/**
 *
 * @author Nour Al-Hoda
 */
public class shields {
    Image shield1; 
    Image shield2;
    Image shield3;
    Image shield4; 
    Image shield;
    int count;
    int img;
    boolean vis;
    
    public shields(){
         ImageIcon i1 = new ImageIcon (getClass().getResource("/Media/shield1.png"));
         shield1= i1.getImage();
       ImageIcon i2 = new ImageIcon (getClass().getResource("/Media/shield2.png"));
    shield2= i2.getImage();
     ImageIcon i3 = new ImageIcon (getClass().getResource("/Media/shield3.png"));
     shield3= i3.getImage();
       ImageIcon i4 = new ImageIcon (getClass().getResource("/Media/shield4.png"));
     shield4= i4.getImage();
        this.shield = shield1;
        this.count = 3;
        this.img = 1;
        this.vis=true;
    }
    
    public void setCount(){
        this.count--;
        if(this.count == 0){
            this.changeImage();
            this.count = 3;
        }
    }
    
    public void changeImage(){
        this.img ++;
        switch(img){
            case 1: 
                this.shield = shield1;
                break;
            case 2:
                this.shield = shield2;
                break;
            case 3:
                this.shield = shield3;
                break;
            case 4:
                this.shield = shield4;
                break;
            case 5:
                this.vis = false;
                break;            
        }
    }
    
    public Image getImage(){
        return this.shield;
    }
    
}
