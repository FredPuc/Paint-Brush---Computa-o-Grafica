/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package figura.reta;

import figura.Figura;
import figura.Ponto;

public class Bresenham extends Figura {

    public Bresenham(int[][] matriz, int xInicio, int yInicio, int xFim, int yFim, int cor) {
        super(matriz, xInicio, yInicio, xFim, yFim, cor);
    }

    @Override
    public void desenha() {

        if (this.isDentroJanela) {

            int deltaX = this.pontoFim.getX() - this.pontoInicio.getX();
            int deltaY = this.pontoFim.getY() - this.pontoInicio.getY();
            int x = this.pontoInicio.getX();
            int y = this.pontoInicio.getY();

            int xIncr, yIncr, p, c1, c2;

            colore(x, y, cor);

            if (deltaX < 0) {
                deltaX = -deltaX;
                xIncr = -1;
            } else {
                xIncr = 1;
            }

            if (deltaY < 0) {
                deltaY = -deltaY;
                yIncr = -1;
            } else {
                yIncr = 1;
            }

            if (deltaX > deltaY) { 
                p = 2 * deltaY - deltaX;
                c1 = 2 * deltaY;
                c2 = 2 * (deltaY - deltaX);

                for (int k = 1; k <= deltaX; k++) {
                    x += xIncr;
                    if (p < 0) {
                        p += c1;
                    } else {
                        p += c2;
                        y += yIncr;
                    }

                    colore(x, y, cor);
                }
            }
            else {
                p = 2 * deltaX - deltaY;
                c1 = 2 * deltaX;
                c2 = 2 * (deltaX - deltaY);

                for (int k = 1; k <= deltaY; k++) {
                    y += yIncr;
                    if (p < 0) {
                        p += c1;
                    } else {
                        p += c2;
                        x += xIncr;
                    }
                    colore(x, y, cor);
                }
            }

        }

    }

    /**
     * Verifica se essa figura foi clicada
     *
     * @param xClick
     * @param yClick
     * @return
     */
    @Override
    public boolean foiClicado(int xClick, int yClick) {

        boolean resposta = false;

        if (this.isDentroJanela) {
            
            double a = (pontoInicio.getY() - pontoFim.getY());
            double b = (pontoFim.getX() - pontoInicio.getX());
            double c = (pontoInicio.getX() * pontoFim.getY() - pontoFim.getX() * pontoInicio.getY());

            double numerador = Math.abs(a * xClick + b * yClick + c);
            double denominador = (Math.sqrt(a * a + b * b));

            resposta = (numerador / denominador) < 3.0;
        }
        
        return resposta;
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
     * Rotaciona a figura em 'angulo' graus, eu fiz alguma coisa muito errada
     * que a rotacao na o funciona, e nao sei o que e', estou a 2 semanas tentando
     * entender o que esta acontecendo
     * @param angulo
     */
    @Override
    public void rotaciona(double angulo) {

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
     * Muda a escala de um objeto. A escala tambem nao funciona de jeito
     * nenhum.
     * @param escala
     */
    @Override
    public void mudaEscala(double escala) {

        Ponto pontoAnterior = this.pontoInicio.clone();
        
        movePara( new Ponto(0,0) );
        
        int novoFimX = (int)Math.round( (double)this.pontoFim.getX() * escala );
        int novoFimY = (int)Math.round( (double)this.pontoFim.getY() * escala );
        
        this.pontoFim = new Ponto( novoFimX, novoFimY );
        
        movePara( pontoAnterior );
        
        desenha();
    }
}
