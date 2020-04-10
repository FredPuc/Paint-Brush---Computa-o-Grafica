/*
 * Classe para guardar um ponto X,Y da figura
 */
package figura;

public class Ponto {
    private int x;
    private int y;
    
    public Ponto( int x, int y ){
        this.x = x;
        this.y = y;
    }
    
    public Ponto clone(){
        return ( new Ponto(this.x, this.y) );
    }

    public String toString(){
        String result = "("+this.x+","+this.y+")";
        return result;
    }
    
    /**
     * @return the x
     */
    public int getX() { 
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }
}
