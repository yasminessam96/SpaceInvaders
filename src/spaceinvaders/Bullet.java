package spaceinvaders;

public class Bullet {

    private int x, y, dy;
    private boolean visible;

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
        dy = 4;
        visible = true;
    }

    public void move() {
        y -= dy;
        if (y <= -570) {
            visible = false;
        }

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

    public void setY(int y) {
        this.y = y;
    }

    public void setVisible(boolean v) {
        this.visible = v;
    }

    public boolean getVisible() {
        return this.visible;
    }

}
