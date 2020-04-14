/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package figura.recorte;

import figura.Figura;
import figura.Ponto;
import figura.reta.DDA;
import figura.reta.Bresenham;
import java.util.ArrayList;

public class CohenSutherland extends Recorte {

    Figura acima, direita, abaixo, esquerda;

    public CohenSutherland(int[][] matriz, int xInicio, int yInicio, int width, int height, int cor) {
        super(matriz, xInicio, yInicio, width, height, cor);
    }

    @Override
    public void desenha() {

        int width = Math.abs(this.pontoInicio.getX() - this.pontoFim.getX());
        int height = Math.abs(this.pontoInicio.getY() - this.pontoFim.getY());

        acima = new Bresenham(this.matriz, this.pontoInicio.getX(), this.pontoInicio.getY(), this.pontoInicio.getX() + width, this.pontoInicio.getY(), this.cor);
        direita = new Bresenham(this.matriz, this.pontoInicio.getX() + width, this.pontoInicio.getY(), this.pontoInicio.getX() + width, this.pontoInicio.getY() + height, this.cor);
        abaixo = new Bresenham(this.matriz, this.pontoInicio.getX(), this.pontoInicio.getY() + height, this.pontoInicio.getX() + width, this.pontoInicio.getY() + height, this.cor);
        esquerda = new Bresenham(this.matriz, this.pontoInicio.getX(), this.pontoInicio.getY(), this.pontoInicio.getX(), this.pontoInicio.getY() + height, this.cor);

        // desenha as retas
        acima.desenha();
        direita.desenha();
        abaixo.desenha();
        esquerda.desenha();
    }

    @Override
    public boolean foiClicado(int xClick, int yClick) {
        // Nao usado, criado pela IDE
        return false;
    }

    @Override
    public void rotaciona(double grau) {
        //Nao usado, criado pela IDE
    }

    /**
     * Metodo para obter o codigo da reta para saber a posicao dela em relação a
     * janela
     *
     * @param figura
     * @param pontoMin
     * @param pontoMax
     * @return
     */
    public int obtemCodigo( Ponto p, Ponto pontoMin, Ponto pontoMax) {
        int codigo = 0;

        
        if (p.getX() < pontoMin.getX()) {
            codigo += 1;
        }
        if ( p.getX() > pontoMax.getX()) {
            codigo += 2;
        }

        if ( p.getY() < pontoMin.getY()) {
            codigo += 4;
        }
        if ( p.getY() > pontoMax.getY()) {
            codigo += 8;
        }

        return codigo;
    }

    /**
     * Metodo para pegar o bit desejado em uma posicao
     *
     * @param codigo
     * @param pos
     * @return
     */
    public int bit(int codigo, int pos) {

        int bit = codigo << (31 - pos);
        bit = bit >>> 31;
        return bit;
    }

    /**
     * Metodo para analizar se uma reta deve ser desenhada ou nao na janela
     *
     * @param figura
     * @param pontoMin
     * @param pontoMax
     * @return
     */
    public void aceito(Figura figura, Ponto pontoMin, Ponto pontoMax) {
        boolean aceito = false;
        boolean feito = false;

        figura.isDentroJanela = true;

        int cFora;

        double xInt = 0,
                yInt = 0;

        double xMin = pontoMin.getX(),
                xMax = pontoMax.getX(),
                yMin = pontoMin.getY(),
                yMax = pontoMax.getY();

        double x1 = figura.getPontoInicio().getX(),
                x2 = figura.getPontoFim().getX(),
                y1 = figura.getPontoInicio().getY(),
                y2 = figura.getPontoFim().getY();

        while (!feito) {
            int c1 = obtemCodigo( figura.getPontoInicio(), pontoMin, pontoMax);
            int c2 = obtemCodigo( figura.getPontoFim(), pontoMin, pontoMax);

            if (c1 == 0 && c2 == 0) {
                aceito = true;
                feito = true;
            } else if ((c1 & c2) != 0) { 
                figura.isDentroJanela = false;
                feito = true;
            } else {

               
                cFora = (c1 != 0) ? c1 : c2;

                if (bit(cFora, 0) == 1) {// esq
                    xInt = xMin;
                    yInt = y1 + (y2 - y1) * (xMin - x1) / (x2 - x1);

                } else if (bit(cFora, 1) == 1) {// dir
                    xInt = xMax;
                    yInt = y1 + (y2 - y1) * (xMax - x1) / (x2 - x1);

                } else if (bit(cFora, 2) == 1) {// inferior
                    yInt = yMin;
                    xInt = x1 + (x2 - x1) * (yMin - y1) / (y2 - y1);

                } else if (bit(cFora, 3) == 1) { // superior
                    yInt = yMax;
                    xInt = x1 + (x2 - x1) * (yMax - y1) / (y2 - y1);

                }

                if (cFora == c1) {
                    x1 = xInt;
                    y1 = yInt;
                    figura.setPontoInicio( new Ponto( (int)Math.round(xInt), (int)Math.round(yInt) ) );
                } else {
                    x2 = xInt;
                    y2 = yInt;
                    figura.setPontoFim( new Ponto( (int)Math.round(xInt), (int)Math.round(yInt) ) );
                }

            }
        }
    }

    @Override
    public void aplicaRecorte(ArrayList<Figura> listaFiguras, Ponto pontoMin, Ponto pontoMax) {

        for (Figura fig : listaFiguras) {

            if (fig.getClass() == DDA.class || fig.getClass() == Bresenham.class) {
                aceito(fig, pontoMin, pontoMax);
            }

        }
    }
    
    /**
     * Muda a escala de um objeto
     * @param escala 
     */
    @Override
    public void mudaEscala( double escala ){
        
    }
}
