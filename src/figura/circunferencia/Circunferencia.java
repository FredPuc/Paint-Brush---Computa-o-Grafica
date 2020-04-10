/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package figura.circunferencia;

import figura.Figura;
import figura.Ponto;

public class Circunferencia extends Figura{

    private int raio;
    
    public Circunferencia(int[][] matriz, int xInicio, int yInicio, int xFim, int yFim, int cor) {
        super(matriz, xInicio, yInicio, xFim, yFim, cor );
    }
    
    /**
     * Calcula a distancia euclidiana entre 2 pontos
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return 
     */
    public int distanciaEuclidiana( int x1, int y1, int x2, int y2 ){
        int deltaXquadrado = (int)Math.pow( (x2-x1) , 2 );
        int deltaYquadrado = (int)Math.pow( (y2-y1) , 2 );
        
        return (int)( Math.sqrt( deltaXquadrado + deltaYquadrado) );
    }
    
    @Override
    public void desenha(  ){
        
        int x = 0, y = raio, p = 3 - 2 * raio;

        plotaSimetricos(x, y, this.pontoInicio.getX(), this.pontoInicio.getY(), cor);
        while (x < y) { // 2 octante
            if (p < 0) {
                p += 4 * x + 6;
            } else {
                p += 4 * (x - y) + 10;
                y--;
            }
            x++;

            plotaSimetricos(x, y, this.pontoInicio.getX(), this.pontoInicio.getY(), cor);
        }
    }

    public void plotaSimetricos(int x, int y, int xCentro, int yCentro, int cor) {
        colore(xCentro + x, yCentro + y, cor);
        colore(xCentro + x, yCentro - y, cor);
        colore(xCentro - x, yCentro + y, cor);
        colore(xCentro - x, yCentro - y, cor);

        colore(xCentro + y, yCentro + x, cor);
        colore(xCentro + y, yCentro - x, cor);
        colore(xCentro - y, yCentro + x, cor);
        colore(xCentro - y, yCentro - x, cor);
    }
    
   /**
     * Verifica se essa figura foi clicada
     * @param xClick
     * @param yClick
     * @return 
     */
    @Override
    public boolean foiClicado( int xClick, int yClick ){
        
        // calcula a distancia entre o click e o raio da circunferencia
        int distanciaClick = distanciaEuclidiana(this.pontoInicio.getX(), this.pontoInicio.getY(), xClick, yClick);
        
        if( distanciaClick >= raio-2 && distanciaClick <= raio + 2 ){
            return true;
        }else{
            return false;
        }
    }
    
    public void atualizaRaio( int x1, int y1, int x2, int y2 ){
        this.raio = distanciaEuclidiana(x1, y1, x2, y2);
    }
    
    /**
     * Rotaciona a figura em 'angulo' graus, eu consigo rotacionar a
     * circunferencia por que salvo o ponto inicial e final dela, pois
     * ela extende de figura
     * @param angulo
     */
    public void rotaciona( double angulo ){
        Ponto pontoAnterior = this.pontoInicio.clone();
        
        angulo = Math.toRadians(angulo);
        
        movePara(new Ponto(0,0));
        
        int novoFimX = (int)Math.round( ( (double)this.pontoFim.getX() * Math.cos(angulo)) - ( (double)this.pontoFim.getY() * Math.sin(angulo)) );
        int novoFimY = (int)Math.round( ( (double)this.pontoFim.getX() * Math.sin(angulo)) + ( (double)this.pontoFim.getY() * Math.cos(angulo)) );
        
        this.pontoFim = new Ponto( novoFimX, novoFimY );
        
        movePara( pontoAnterior );
        
        desenha();
    }
    
    /**
     * Move a figura para um ponto na matriz
     * @param ponto 
     */
    public void movePara( Ponto ponto ) {
        int deltaX = ponto.getX() - this.pontoInicio.getX();
        int deltaY = ponto.getY() - this.pontoInicio.getY();
        
        this.pontoInicio = new Ponto( this.pontoInicio.getX() + deltaX, this.pontoInicio.getY() + deltaY );
        this.pontoFim = new Ponto( this.pontoFim.getX() + deltaX, this.pontoFim.getY() + deltaY );
    }
    
    /**
     * Muda a escala de um objeto
     * @param escala 
     */
    @Override
    public void mudaEscala( double escala ){
        
        Ponto pontoAnterior = this.pontoInicio.clone();
        
        movePara( new Ponto(0,0) );
        
        int novoFimX = (int)Math.round( (double)this.pontoFim.getX() * escala );
        int novoFimY = (int)Math.round( (double)this.pontoFim.getY() * escala );
        
        this.pontoFim = new Ponto( novoFimX, novoFimY );
        
        movePara( pontoAnterior );
        
        atualizaRaio( this.pontoInicio.getX(), this.pontoInicio.getY(), this.pontoFim.getX(), this.pontoFim.getY() );
        
        desenha();
    }
}
