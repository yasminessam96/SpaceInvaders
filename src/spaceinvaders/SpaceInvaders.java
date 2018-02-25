 package spaceinvaders;

import java.applet.*;
import java.awt.*;
import java.awt.dnd.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;    
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import static javax.sound.sampled.Clip.LOOP_CONTINUOUSLY;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;




class gameConstants {
  
    public static final int WIDTH = 840;
    public static final int HEIGHT = 570;
    public static final int MAX_LEVELS = 10;
    public static final int RECT_WIDTH = 20;
    public static final int RECT_HEIGHT = 30;
    public static final int BLT_WIDTH = 7;
    public static final int BLT_HEIGHT = 15;
    public static final int shipWidth = 65;
    public static final int shipHeight = 35;
    public static final int shieldY = 420;
    public static final int shield1x = 60;

}

class gamePanel extends JPanel  {

    Ship ship;
    File wavFile = new File("shoot.wav");
    File enemyExplosion = new File ("enemyExplosion.wav");
    File shipExplosion = new File ("shipExplosion.wav");
    AudioClip shoot;
    AudioClip enExp;
    AudioClip shExp;
    AL a = new AL();
    Mouse m = new Mouse();
    sound s = new sound();
    sound shSound = new sound();
    sound enSound = new sound();
    Image logo; 
    Image background;
   
    private JScrollPane scroll;
    
    public shields sh1;
    public shields sh2;
    public shields sh3;
    public shields sh4;

    public ArrayList<greenEnemy>green;
    public ArrayList<purpleEnemy> purp1;
    public ArrayList<purpleEnemy>purp2;
    public ArrayList<blueEnemy>blue1;
    public ArrayList<blueEnemy>blue2;
    public ArrayList[] allEnemeies;
    public direction dir = direction.RIGHT;
    public ArrayList<enmBullet>allBlts;
    public ArrayList <Bullet> bullet = new ArrayList<Bullet>();

    public int enemyDeltaX;
    
    public FileReader fr2;
    public BufferedReader brName;
    public FileWriter fw2;
    public BufferedWriter bwName;
    public FileReader fr1;
    public BufferedReader brScore;
    public FileWriter fw1;
    public BufferedWriter bwScore;
    public static boolean newgame = false;
  
    public static enum STATE {
        game, menu, highScores, gameOver, gameWon, gameError
    };
    public static STATE state = STATE.menu;
    public Rectangle playBtn = new Rectangle(gameConstants.WIDTH / 5 + 180, 290, 100, 50);
    public Rectangle quitBtn = new Rectangle(gameConstants.WIDTH / 5 + 180, 390, 100, 50);
    public Rectangle scoresBtn = new Rectangle(gameConstants.WIDTH / 5 + 120, 490, 220, 50);
    public Rectangle backBtn = new Rectangle(40, 500, 100, 50);
    public Rectangle HallOfFameBtn = new Rectangle(40, 500, 200, 50);

    public gamePanel() {
        initComp();
    }

    public void initComp() {
        ImageIcon i3 = new ImageIcon (getClass().getResource("/Media/logo.png"));
        logo = i3.getImage();

       ImageIcon i4 = new ImageIcon (getClass().getResource("/Media/background.png"));
       background = i4.getImage();
        
        if(gamePanel.state == gamePanel.STATE.highScores)
        {scroll = new JScrollPane(this);
        this.add(scroll);}
        
        this.setPreferredSize(new Dimension(gameConstants.WIDTH,gameConstants.HEIGHT));
        s.start();
        shSound.start();
        enSound.start();
        this.addKeyListener(a);
        this.addMouseListener(m);
        
        this.setFocusable(true);
        this.setBackground(Color.black);
        this.setSize(gameConstants.WIDTH, gameConstants.HEIGHT);
        
        try {
            shExp = Applet.newAudioClip(shipExplosion.toURL());
            enExp = Applet.newAudioClip(enemyExplosion.toURL());
            shoot = Applet.newAudioClip(wavFile.toURL());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ship = new Ship();
        
        green = new ArrayList<greenEnemy>();
        purp1 = new ArrayList<purpleEnemy>();
        purp2 = new ArrayList<purpleEnemy>();
        blue1 = new ArrayList<blueEnemy>();
        blue2 = new ArrayList<blueEnemy>();
        allBlts = new ArrayList<enmBullet>();
        allEnemeies = new ArrayList[5];
        allEnemeies[0] = blue2;
        allEnemeies[1] = blue1;
        allEnemeies[2] = purp1;
        allEnemeies[3] = purp2;
        allEnemeies[4] = green;
        
        sh1 = new shields();
        sh2 = new shields();
        sh3 = new shields();
        sh4 = new shields();
        
        int nx = 0;
        int ny = 0;
        for (int i = 0; i < 5 ; i++)
        {
            ny = gameConstants.RECT_HEIGHT + i*gameConstants.RECT_HEIGHT + i*10;
            for(int j = 0 ; j < 11 ; j++)
            {
                nx = 10*gameConstants.RECT_WIDTH + j*gameConstants.RECT_WIDTH*2;
                switch (i)
                {
                    case 0 :
                        green.add(new greenEnemy(nx,ny));
                        break;
                    case 1 :
                        purp1.add(new purpleEnemy(nx,ny));
                        break;
                    case 2 :
                        purp2.add(new purpleEnemy(nx,ny));
                        break;
                    case 3 :
                        blue1.add(new blueEnemy(nx,ny));
                        break;
                    case 4 :
                        blue2.add(new blueEnemy(nx,ny));
                        break;
                }  
            } 
        }
        
        enemyDeltaX = gameConstants.RECT_WIDTH*2;
    }
    
    public void setEnemyDeltaX(int x){
        this.enemyDeltaX = x;
    }

    public void paintShip(Graphics g) {
        g.drawImage(ship.getShip(), ship.getX(), ship.getY(), this);
    }
      
    public void paintShield1(Graphics g){
        g.drawImage(sh1.getImage(),60 , gameConstants.shieldY, this);
    }
    
    public void paintShield2(Graphics g){
        g.drawImage(sh2.getImage(),270 , gameConstants.shieldY, this);
    }
    
    public void paintShield3(Graphics g){
        g.drawImage(sh3.getImage(),480 , gameConstants.shieldY, this);
    }
    
    public void paintShield4(Graphics g){
        g.drawImage(sh3.getImage(),690 , gameConstants.shieldY, this);
    }



    @Override
    public void paint(Graphics g) {
      g.drawImage(background, 0, 0, this);
       try {
            fw1 = new FileWriter("Scores.txt", true);
            bwScore = new BufferedWriter(fw1);
            fw2 = new FileWriter("Names.txt", true);
            bwName = new BufferedWriter(fw2);
            
            fr1 = new FileReader("Scores.txt");
            brScore = new BufferedReader(fr1);
            fr2 = new FileReader("Names.txt");
            brName = new BufferedReader(fr2);
            
        } catch (Exception ex) {
            Logger.getLogger(SpaceInvaders.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (state == STATE.game) {
            
            
            for (int i = 0 ; i < 11 ; i++)
        {
            if(blue2.get(i).visible)
                g.drawImage(blue2.get(i).getImg(), blue2.get(i).getX(), blue2.get(i).getY(), this);
            if(blue1.get(i).visible)
                g.drawImage(blue1.get(i).getImg(), blue1.get(i).getX(), blue1.get(i).getY(), this);
             if(purp2.get(i).visible)
                g.drawImage(purp2.get(i).getImg(), purp2.get(i).getX(), purp2.get(i).getY(), this);
            if(purp1.get(i).visible)
                g.drawImage(purp1.get(i).getImg(), purp1.get(i).getX(), purp1.get(i).getY(), this);
            if(green.get(i).visible)
                g.drawImage(green.get(i).getImg(), green.get(i).getX(), green.get(i).getY(), this);
                       
        }
            paintShip(g);
                
          
            if(sh1.vis)
            paintShield1(g);
            if(sh2.vis)
            paintShield2(g);
            if(sh3.vis)
            paintShield3(g);
            if(sh4.vis)
            paintShield4(g);
            
            for (int i = 0; i < bullet.size(); i++) {
                Bullet p = (Bullet) bullet.get(i);
                g.setColor(Color.red);
                g.fillRect(p.getX(), p.getY(), gameConstants.BLT_WIDTH, gameConstants.BLT_HEIGHT);

            }
             
            for (int j = 0 ; j < allBlts.size() ; j++)
            {
                g.setColor(Color.LIGHT_GRAY);
                if (allBlts.get(j).getVisible())
                {
                    g.fillRect(allBlts.get(j).getX(), allBlts.get(j).getY(), gameConstants.BLT_WIDTH,gameConstants.BLT_HEIGHT);
                }
            }
            

        } 
        
        else if(state == STATE.menu) {
       
            g.setColor(Color.yellow);
            g.drawImage(logo, gameConstants.WIDTH / 4, 100, this);
            Graphics2D g2 = (Graphics2D) g;
            g2.draw(playBtn);
            g2.draw(quitBtn);
            g2.draw(scoresBtn);
            Font f = new Font("Century Gothic", Font.BOLD, 30);
            g.setFont(f);
            g.drawString("PLAY", playBtn.x + 14, playBtn.y + 35);
            g.drawString("QUIT", quitBtn.x + 14, quitBtn.y + 35);
            g.drawString("HALL OF FAME", scoresBtn.x +5, scoresBtn.y + 35);
        

        }
        else if(state == STATE.highScores) {
            g.setColor(Color.yellow);
            Font f = new Font("Century Gothic", Font.BOLD, 45);
            Font f1 = new Font("Century Gothic", Font.BOLD, 20);
            Font f2= new Font("Century Gothic", Font.BOLD, 30);
            g.setFont(f);
            g.drawString("NAME",120, 80);
            g.drawString("Score",600, 80);
            Graphics2D g2 = (Graphics2D) g;
            g2.setFont(f1);
            g2.draw(backBtn);
            g.setFont(f2);
            g.drawString("MENU", backBtn.x + 12, backBtn.y + 35);
            
            int y = 120;
            try{
            while(brName.ready()){
                g.setFont(f1);
                String name = brName.readLine();
                g.drawString(name, 120, y);
                String score = brScore.readLine();
                g.drawString(score, 600, y);
                
                y+=30;
            }
        }
        catch( Exception e){
            e.getStackTrace();
        }
            
        }
        else if (state == STATE.gameOver){
        g.setColor(Color.yellow);
        Font f = new Font("Century Gothic",Font.BOLD, 50);
        g.setFont(f);
        g.drawString("GAME OVER LOSER!", 180, gameConstants.HEIGHT/2);
       
        }
        else if (state == STATE.gameWon){
        g.setColor(Color.yellow);
        Font f = new Font("Century Gothic",Font.BOLD, 50);
        g.setFont(f);
        g.drawString("WOHOO!", 260, 245);
        g.drawString("YOU SAVED THE EARTH!", 140, 305);
      
                }
         else if (state == STATE.gameError){
        g.setColor(Color.yellow);
        Font f = new Font("Century Gothic",Font.BOLD, 50);
        g.setFont(f);
        g.drawString("YOU BROKE THE GAME", 120, 245);
        g.drawString("WITH YOUR AWESOMENESS", 120,305 );
        g.drawString("REOPEN AND TRY AGAIN",120, 365);
         }
    }
   
    
   
    public int moveShip() {
        ship.setX(ship.getX() + ship.getXMoved());
        
        if (ship.getX() <= 0) {
            ship.setX(0);
        }
        if (ship.getX() >= gameConstants.WIDTH - gameConstants.shipWidth) {
            ship.setX(gameConstants.WIDTH - ship.getShip().getWidth(null));
        }
        return ship.getX();
    }
    
        
    public direction moveAliens (ArrayList enmsList,int type)
    {
        
        direction d1;
        enemies moving;
        enemies moving1;
        enemies e = null;
        switch(dir){
            case RIGHT:
                for (int i = enmsList.size()-1 ; i >= 0 ; i--)
                {
                    moving = (enemies)enmsList.get(i);
                    moving.x = moving.getX() + enemyDeltaX ;
                    switch (type)
                    {
                        case 1:
                            e = new greenEnemy(moving.x,moving.y, moving.visible);
                            break;
                        case 2:
                            e = new purpleEnemy(moving.x,moving.y, moving.visible);
                            break;
                        case 3:
                            e = new blueEnemy(moving.x,moving.y, moving.visible);
                            break;
                    }
                    enmsList.add(e);
                    enmsList.remove(i);
                }
                moving = (enemies)enmsList.get(0);
                moving1 = (enemies)enmsList.get(enmsList.size()-1);
                if (moving.getX()+enemyDeltaX >= gameConstants.WIDTH || 
                        moving1.getX()+enemyDeltaX >= gameConstants.WIDTH )
                    d1 = direction.LEFT;
                else
                    d1 = direction.RIGHT;
                return d1;
            case LEFT:
                 moving = (enemies)enmsList.get(enmsList.size()-1);
                moving1 = (enemies)enmsList.get(0);
                if (moving.getX()-enemyDeltaX <= 0 || moving1.getX()-enemyDeltaX <= 0)
                    d1 = direction.DOWN;
                else 
                    d1 = direction.LEFT;
                for (int i = enmsList.size()-1 ; i >= 0  ; i--)
                {
                    moving = (enemies)enmsList.get(i);
                    moving.x = moving.getX() - enemyDeltaX;
                    switch (type)
                    {
                        case 1:
                            e = new greenEnemy(moving.x,moving.y, moving.visible);
                            break;
                        case 2:
                            e = new purpleEnemy(moving.x,moving.y, moving.visible);
                            break;
                        case 3:
                            e = new blueEnemy(moving.x,moving.y, moving.visible);
                            break;        
                    }
                    enmsList.add(e);
                    enmsList.remove(i);
                }
               
                return d1;
            case DOWN:
                for (int i = enmsList.size()-1 ; i >=0 ; i--)
                {
                    moving = (enemies)enmsList.get(i);
                    moving.y = moving.getY() + gameConstants.RECT_HEIGHT;
                    switch (type)
                    {
                        case 1:
                            e = new greenEnemy(moving.x,moving.y,moving.visible);
                            if (e.getY() >= 420){
                                JOptionPane.showMessageDialog(null, "Game Over Loser !");
                                System.exit(0);
                            }
                            break;
                        case 2:
                            e = new purpleEnemy(moving.x,moving.y,moving.visible);
                            if (e.getY() >= 420){
                                JOptionPane.showMessageDialog(null, "Game Over Loser !");
                                System.exit(0);
                            }
                            break;
                        case 3:
                            e = new blueEnemy(moving.x,moving.y,moving.visible);
                            if (e.getY() >= 420){
                                gamePanel.state = gamePanel.STATE.gameOver;
                               // JOptionPane.showMessageDialog(null, "Game Over Loser !");
                               // System.exit(0);
                            }
                            break;        
                    }
                    
                    enmsList.add(e);
                    enmsList.remove(i);
                }
                    d1 = direction.RIGHT;
                return d1;
            default :
                dir = direction.DOWN;
                return dir;
        }
    } 

    public ArrayList getBullets() {
        return this.bullet;
    }
    
    public ArrayList getEnemy(int i) {
        return this.allEnemeies[i];
    }

    class AL extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent ke) {
            switch (ke.getKeyCode()) {
                case KeyEvent.VK_RIGHT:
                    ship.setXMoved(1);
                    break;
                case KeyEvent.VK_LEFT:
                    ship.setXMoved(-1);
                    break;
                case KeyEvent.VK_SPACE:
                    Bullet b = new Bullet(ship.getX() + ship.getShip().getWidth(null) / 2,
                            ship.getY() - ship.getShip().getHeight(null)/2);
                    bullet.add(b);
                    shoot.play();

                    break;

            }
        }

        @Override
        public void keyReleased(KeyEvent ke) {
            switch (ke.getKeyCode()) {
                case KeyEvent.VK_RIGHT:
                    ship.setXMoved(0);
                    break;
                case KeyEvent.VK_LEFT:
                    ship.setXMoved(0);
                    break;
                case KeyEvent.VK_SPACE:

                    break;

            }
        }

    }

}

class Mouse extends MouseAdapter {
  public Rectangle scoresBtn = new Rectangle(gameConstants.WIDTH / 5 + 120, 490, 220, 50);
    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (gamePanel.state == gamePanel.STATE.menu) {
            if (x >= gameConstants.WIDTH / 5 + 180 && x <= gameConstants.WIDTH / 5 + 280 && y >= 290 && y <= 340) {
                gamePanel.state = gamePanel.STATE.game;
            }
            if (x >= gameConstants.WIDTH / 5 + 180 && x <= gameConstants.WIDTH / 5 + 280 && y >= 390 && y <= 440) {
                System.exit(1);
            }
            if(x >= gameConstants.WIDTH / 5 + 120 && x <= gameConstants.WIDTH / 5 + 340 && y >= 490 && y <= 540)
       gamePanel.state = gamePanel.STATE.highScores;
                }
        if(gamePanel.state == gamePanel.STATE.highScores && x >= 40 && x <= 140 && y >= 500 && y <= 550)
        { gamePanel.state = gamePanel.STATE.menu;
        }
        if(gamePanel.state == gamePanel.STATE.gameOver && x >= 40 && x <= 240 && y >= 500 && y <= 550){
            gamePanel.state = gamePanel.STATE.highScores;
            gamePanel.newgame = true;
        }
        if(gamePanel.state == gamePanel.STATE.gameWon && x >= 40 && x <= 240 && y >= 500 && y <= 550){
            gamePanel.state = gamePanel.STATE.highScores;
            gamePanel.newgame = true;
            
        }
    }
}

class sound extends JApplet
{
    private AudioClip mcdClip;
   
    private File mario;
   
    public sound()
    {
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResource("starwars.wav"));
            clip = AudioSystem.getClip();
            clip.open(inputStream); 
            clip.start();
            clip.loop(LOOP_CONTINUOUSLY);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(sound.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(sound.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(sound.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}




public class SpaceInvaders extends JFrame implements Runnable {

    
    gamePanel gp = new gamePanel();
    private JPanel pnlInfo;
    private JLabel scrLbl;
    private JLabel lvlLbl;
    private JLabel liveslbl;
    private int lives;
    private int score;
    private int enemiesLeft;
    int DELAY;
    int BltTimer;
    boolean lvl2;
    private javax.swing.Timer timerMove;
    private javax.swing.Timer timerBlt;
    private javax.swing.Timer timerRepaint;
    private Random rand;
             
    public SpaceInvaders() {
        

        this.setTitle("Space Invaders");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(gameConstants.WIDTH, gameConstants.HEIGHT);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.add(gp);
        Container c = this.getContentPane();
        lives = 3;
        score = 0;
        enemiesLeft = 55;
        DELAY =700;
        BltTimer = 10;
        lvl2 = false;
        
        scrLbl = new JLabel("Score = " + score);
        scrLbl.setPreferredSize(new Dimension(50, 20));
        lvlLbl = new JLabel("Level = 1");
        lvlLbl.setPreferredSize(new Dimension(50, 20));
        liveslbl = new JLabel("Lives = "+ lives);
        liveslbl.setPreferredSize(new Dimension(50, 20));
        pnlInfo = new JPanel();
        pnlInfo.setLayout(new GridLayout(1,3));
        pnlInfo.add(scrLbl);
        pnlInfo.add(lvlLbl);
        pnlInfo.add(liveslbl);
        c.add(pnlInfo, BorderLayout.SOUTH);
        c.add(gp);  
       
        this.setBounds(250, 100, 840, 600);
        this.setVisible(true);
        rand = new Random();   
        
        timerRepaint = new javax.swing.Timer(2, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();                
            }
        });
        timerRepaint.start();
        moveEnemies(DELAY);
 
        timerBlt = new javax.swing.Timer(BltTimer,new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                for (int i = 0  ; i < gp.allBlts.size() ; i++)
                {
                    if (gp.allBlts.get(i).getVisible())
                    {
                        gp.allBlts.get(i).move();
                      
                        if(shieldHasCollision(gp.allBlts.get(i).getX(), gp.allBlts.get(i).getY()))
                            gp.allBlts.get(i).setVisible(false);
                        if(ShipHasCollision(gp.allBlts.get(i).getX(), gp.allBlts.get(i).getY()+gameConstants.BLT_HEIGHT)){
                            gp.allBlts.get(i).setVisible(false);
                            lives --;
                            liveslbl.setText("Lives = "+ lives);
                            if(lives == 0){
                                gamePanel.state = gamePanel.STATE.gameOver;
                               GameEnd();
                            }
                                
                        }
                        
                    }
                    else
                        gp.allBlts.remove(i);
 
                }
            }
        });
        
        this.pack();
       
                
    }
    
    public void moveEnemies(int DELAY){
           
      
       timerMove = new javax.swing.Timer(DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int vis;
                if (gamePanel.state == gamePanel.STATE.game){
                    direction d1 = gp.moveAliens(gp.purp1,2);
                    direction d2 = gp.moveAliens(gp.purp2,2);
                    direction d3 = gp.moveAliens(gp.green,1);
                    direction d4  = gp.moveAliens(gp.blue2,3);
                    direction d5 = gp.moveAliens(gp.blue1,3);
                if (d1 == d2 && d2 == d3 && d3 == d4 && d4 == d5)
                {
                    gp.dir = d1;
                }
                for (int i = 0 ; i < 11 ; i++)
                {
                    vis = visibleBlts();
                    if (i == vis)
                    {
                        if (gp.blue2.get(i).getVisible())
                            gp.allBlts.add(new enmBullet(gp.blue2.get(i).getX()+20,gp.blue2.get(i).getY()+gameConstants.RECT_HEIGHT,true));
                        else if (gp.blue1.get(i).getVisible())
                            gp.allBlts.add(new enmBullet(gp.blue1.get(i).getX()+20,gp.blue1.get(i).getY()+gameConstants.RECT_HEIGHT,true));
                        else if (gp.purp2.get(i).getVisible())
                            gp.allBlts.add(new enmBullet(gp.purp2.get(i).getX()+20,gp.purp2.get(i).getY()+gameConstants.RECT_HEIGHT,true));
                        else if (gp.purp1.get(i).getVisible())
                            gp.allBlts.add(new enmBullet(gp.purp1.get(i).getX()+20,gp.purp2.get(i).getY()+gameConstants.RECT_HEIGHT,true));
                        else if (gp.green.get(i).getVisible())
                            gp.allBlts.add(new enmBullet(gp.green.get(i).getX()+20,gp.green.get(i).getY()+gameConstants.RECT_HEIGHT,true));
                        timerBlt.start();
                    }
                } 
            }
                
            }
            
        });

        
        
        timerMove.start();
    }
    
    public boolean ShipHasCollision(int x, int y){
        
        
        if(gp.ship.getX()<= x && gp.ship.getX()+gameConstants.shipWidth >= x && y > gp.ship.getY() ){
            gp.shExp.play();
            return true;
        }
        return false;
    }
    
   public void GameEnd(){
        String name = JOptionPane.showInputDialog("Please enter your name");
        saveHighScores(name, score);
  
        
    }
    
    public boolean EnemyHasCollision(int x, int y){
        enemies en;
        ArrayList e;
       for (int i=0; i <5;i++ ){
          e = gp.getEnemy(i);
           for (int j =0; j<e.size(); j++ ){
            en =(enemies) e.get(j);
            if(en!= null && en.getVisible()){
                if(en.getX() <= x && x <= (en.getX()+2*gameConstants.RECT_WIDTH) && en.getY() >= y ){
                    en.setVisible(false);
                    gp.enExp.play();
                    enemiesLeft--;
                    if(enemiesLeft==0 && !lvl2)
                        level2();
                    if(enemiesLeft==0 && lvl2){
                        gamePanel.state = gamePanel.STATE.gameWon;
                        GameEnd();
                    }
                 
                    if(en instanceof blueEnemy)
                        score += 10;
                    else if(en instanceof purpleEnemy)
                        score += 15;
                    else if(en instanceof greenEnemy)
                        score += 25;
                    scrLbl.setText("Score = " + score);
                    return true;
            }
           }
           }
       } 
       return false;
    }
    
    public boolean shieldHasCollision(int x, int y){
        int X = gameConstants.shield1x;
        int Y = gameConstants.shieldY+10;
        for(int i =1; i<=4; i++){
            if(X<x && X+71 >x && Y<y)
            {
              switch(i){
                case 1:
                    if(gp.sh1.vis == false)
                        return false;
                    else
                        gp.sh1.setCount();
                        break;
                case 2:
                    if(gp.sh2.vis == false)
                        return false;
                    else
                        gp.sh2.setCount();
                        break;
                case 3:
                    if(gp.sh3.vis == false)
                        return false;
                    else
                        gp.sh3.setCount();
                        break;
                case 4:
                    if(gp.sh4.vis == false)
                        return false;
                    else
                        gp.sh4.setCount();
                        break;
            }
            return true;
            }
            X+=210;
        }
        return false;
    }
    
    public int visibleBlts ()
    {
        int visible;
        visible = rand.nextInt(11);
        return visible;
    }
    
    

    @Override
    public void run() {

        try {

            while (true) {
                ArrayList bullets = gp.getBullets();
                for (int i = 0; i < bullets.size(); i++) {
                    Bullet p = (Bullet) bullets.get(i);
                    if (p.getVisible()) {
                        p.move();
                        if(shieldHasCollision(p.getX(), p.getY())){
                                p.move();
                                p.setVisible(false);
                        }
                        if(EnemyHasCollision(p.getX(), p.getY()))
                            p.setVisible(false);
                        
                    } else {
                        bullets.remove(i);
                    }
                }

                gp.moveShip();
              
               Thread.sleep(5);
               
            }
        } catch (NullPointerException e) {
   
            e.printStackTrace();
        
         gamePanel.state = gamePanel.STATE.gameError;
          
        } catch (InterruptedException ex) {
            Logger.getLogger(SpaceInvaders.class.getName()).log(Level.SEVERE, null, ex);
            gamePanel.state = gamePanel.STATE.gameError;
        }
        
         }
   
    public void level2(){
        lvl2 = true;
        int d = 400;
        int nx;
        int ny;
        moveEnemies(d);
        for (int i = 0; i < 5 ; i++)
        {
            ny = gameConstants.RECT_HEIGHT + i*gameConstants.RECT_HEIGHT + i*10;
            for(int j = 0 ; j < 11 ; j++)
            {
                nx = 10*gameConstants.RECT_WIDTH + j*gameConstants.RECT_WIDTH*2;
                switch (i)
                {
                    case 0 :
                        gp.green.get(j).setX(nx);
                        gp.green.get(j).setY(ny);
                        gp.green.get(j).setVisible(true);
                        break;
                    case 1 :
                        gp.purp1.get(j).setX(nx);
                        gp.purp1.get(j).setY(ny);
                        gp.purp1.get(j).setVisible(true);
                        break;
                    case 2 :
                        gp.purp2.get(j).setX(nx);
                        gp.purp2.get(j).setY(ny);
                        gp.purp2.get(j).setVisible(true);
                        break;
                    case 3 :
                        gp.blue1.get(j).setX(nx);
                        gp.blue1.get(j).setY(ny);
                        gp.blue1.get(j).setVisible(true);
                        break;
                    case 4 :
                        gp.blue2.get(j).setX(nx);
                        gp.blue2.get(j).setY(ny);
                        gp.blue2.get(j).setVisible(true);
                        break;
                }  
            }
        }
       
        gp.dir =direction.RIGHT;
       
        lvlLbl.setText("Level 2");
        enemiesLeft=55;
    }
   
    
    public void saveHighScores(String name, int score){
        try {
            
            if(name != null){
                gp.bwName.append(name);
                gp.bwName.newLine();
                gp.bwScore.append(score+"");
                gp.bwScore.newLine();
            }
                            
            gp.bwName.close();
            gp.bwScore.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
   

    public static void main(String[] args) {
        SpaceInvaders si = new SpaceInvaders();
        si.setVisible(true);
       Thread thread = new Thread(si);
       thread.start();
       

    }
}