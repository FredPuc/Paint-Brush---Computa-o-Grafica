/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package figura;

public abstract class Figura {
    // Essa matriz e' uma referencia a matriz da classe principal
    protected int[][] matriz;
    protected Ponto pontoInicio, pontoFim;
    protected int cor;
    
    public boolean isDentroJanela;

    /**
     *
     * @param matriz
     */
    public Figura(int[][] matriz, int xInicio, int yInicio, int xFim, int yFim, int cor ) {
        this.matriz = matriz;
        this.pontoInicio = new Ponto( xInicio, yInicio );
        this.pontoFim = new Ponto( xFim, yFim );
        this.cor = cor;
        this.isDentroJanela = true;
    }

    public void colore(int x, int y, int cor) {
        
        if( ( x >= 0 && x < matriz[0].length ) && ( y >= 0 && y < matriz.length ) ){
            matriz[x][y] = cor;
        }
    }
    
    public abstract void desenha( );
    
    public abstract boolean foiClicado( int xClick, int yClick );
    
    public abstract void rotaciona( double grau );
    
    public abstract void mudaEscala( double escala );
    
    public int[][] getMatriz() {
        return this.matriz;
    }
    
     
    
    public Ponto getPontoInicio( ){
        return pontoInicio;
    }
    
    public void setPontoInicio( Ponto p ){
        this.pontoInicio = p;
    }
    
    public Ponto getPontoFim( ){
        return this.pontoFim;
    }
    
    public void setPontoFim( Ponto p ){
        this.pontoFim = p;
    }

    /**
     * @return the cor
     */
    public int getCor() {
        return cor;
    }

    /**
     * @param cor the cor to set
     */
    public void setCor(int cor) {
        this.cor = cor;
    }
}
