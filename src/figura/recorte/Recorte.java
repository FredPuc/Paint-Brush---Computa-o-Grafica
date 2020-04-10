/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package figura.recorte;

import figura.Figura;
import figura.Ponto;
import java.util.ArrayList;

public abstract class Recorte extends Figura{

    public Recorte(int[][] matriz, int xInicio, int yInicio, int xFim, int yFim, int cor) {
        super(matriz, xInicio, yInicio, xFim, yFim, cor);
    }
    
    /**
     * Metodo para recortar as figuras na tela de acordo
     * com o algoritimo de recorte que a implementa
     * @param listaFiguras
     * @param pontoMin
     * @param pontoMax
     */
    public abstract void aplicaRecorte( ArrayList<Figura> listaFiguras, Ponto pontoMin, Ponto pontoMax );
    
}
