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

public class LianBarsky extends Recorte {

    Figura acima, direita, abaixo, esquerda;
    double u1, u2;

    public LianBarsky(int[][] matriz, int xInicio, int yInicio, int width, int height, int cor) {
        super(matriz, xInicio, yInicio, width, height, cor);
    }

    @Override
    public void desenha() {

        int width = Math.abs(this.pontoInicio.getX() - this.pontoFim.getX());
        int height = Math.abs(this.pontoInicio.getY() - this.pontoFim.getY());

        // Reta acima
        acima = new Bresenham(this.matriz, this.pontoInicio.getX(), this.pontoInicio.getY(), this.pontoInicio.getX() + width, this.pontoInicio.getY(), this.cor);
        // Reta direita
        direita = new Bresenham(this.matriz, this.pontoInicio.getX() + width, this.pontoInicio.getY(), this.pontoInicio.getX() + width, this.pontoInicio.getY() + height, this.cor);
        // Reta abaixo
        abaixo = new Bresenham(this.matriz, this.pontoInicio.getX(), this.pontoInicio.getY() + height, this.pontoInicio.getX() + width, this.pontoInicio.getY() + height, this.cor);
        // Reta esquerda
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
        //Nao usado, ceiado pela IDE
    }

    public boolean clipset(double p, double q) {
        double r = q / p;
        
        if (p < 0) {
            if (r > 1) {
                return false;
            } else if (r > u1) {
                u1 = r;
            }
        } else if (p > 0) {
            if (r < 0) {
                return false;
            } else if (r < u2) {
                u2 = r;
            }
        } else if (q < 0) {
            return false;
        }
        return true;
    }

    public void liang(Figura figura, Ponto pontoMin, Ponto pontoMax) {

        double x1 = figura.getPontoInicio().getX(),
                x2 = figura.getPontoFim().getX(),
                y1 = figura.getPontoInicio().getY(),
                y2 = figura.getPontoFim().getY();

        double dx = x2 - x1,
                dy = y2 - y1;
        
        this.u1 = 0.0;
        this.u2 = 1.0;

        figura.isDentroJanela = false;

        if (clipset(-dx, x1 - pontoMin.getX())) {
            if (clipset(dx, pontoMax.getX() - x1)) {
                if (clipset(-dy, y1 - pontoMin.getY())) {
                    if (clipset(dy, pontoMax.getY() - y1)) {

                        if (u2 < 1.0) {
                            x2 = x1 + dx * u2;
                            y2 = y1 + dy * u2;
                        }
                        if (u1 > 0.0) {
                            x1 = x1 + dx * u1;
                            y1 = y1 + dy * u1;
                        }

                        figura.isDentroJanela = true;
                        figura.setPontoInicio(new Ponto((int) Math.round(x1), (int) Math.round(y1)));
                        figura.setPontoFim(new Ponto((int) Math.round(x2), (int) Math.round(y2)));

                    }
                }
            }
        }

    }

    @Override
    public void aplicaRecorte(ArrayList<Figura> listaFiguras, Ponto pontoMin, Ponto pontoMax) {

        System.out.println("Implementando esse recorte");

        for (Figura fig : listaFiguras) {

            if (fig.getClass() == DDA.class || fig.getClass() == Bresenham.class) {
                liang(fig, pontoMin, pontoMax);
            }

        }
    }

    /**
     * Muda a escala de um objeto
     *
     * @param escala
     */
    @Override
    public void mudaEscala(double escala) {

    }
}
