package gui;

import figura.reta.Bresenham;
import figura.circunferencia.Circunferencia;
import figura.recorte.CohenSutherland;
import figura.reta.DDA;
import figura.Figura;
import figura.recorte.LianBarsky;
import figura.Ponto;
import figura.recorte.Recorte;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PanelImagem extends JPanel implements MouseMotionListener, MouseListener {
    
    public static final int BRANCO = 0xffffffff;
    public static final int PRETO = 0xff000000;
    public static final int VERMELHO = 0xffff0000;
    public static final int VERDE = 0xff00ff00;
    public static final int AZUL = 0xff0000ff;

    public final int nenhumDesenho = 0;
    public final int idMover = 1;
    public final int idDDA = 2;
    public final int idBresenham = 3;
    public final int idCircunferencia = 4;
    public final int idCohenSutherland = 5;
    public final int idLianBarsky = 6;
    public final int idRotacionar = 7;
    public final int idEscala = 8;
    private final int naoClicou = 0;
    private final int clicouPrimeiroPixel = 1;
    private final int clicouArrastar = 2;
    private Recorte recorte = null;
    private Ponto janelaInicio = new Ponto(0, 0);
    private Ponto janelaFim = new Ponto(800, 600);
    private int isDesenho;
    private int estadoDesenho;
    private Ponto pontoAnterior;
    private Class<? extends Figura> figuraClicada;
    private int posListaFiguraClicada = -1;
    private BufferedImage bufferMatriz;
    private int[][] matriz;
    private int[][] copia;
    private final int width = 800;
    private final int height = 600;

    private ArrayList<Figura> listaObjetos = new ArrayList<>();

    public PanelImagem() {
        setFocusable(true);
        setDoubleBuffered(true);
        setPreferredSize(new Dimension(this.width, this.height));

        addMouseListener(this);
        addMouseMotionListener(this);

        this.matriz = new int[this.width][this.height];
        this.copia = new int[this.width][this.height];

        for (int x = 0; x < this.width; x++) {
            Arrays.fill(matriz[x], BRANCO);
            Arrays.fill(copia[x], BRANCO);
        }
        atualizaImagem();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        graphics.drawImage(bufferMatriz, null, 0, 0);
    }

    /**
     * Atualiza o buffer para redesenhar a imagem
     */
    public void atualizaImagem() {
        this.bufferMatriz = Imagem.matrixToBuffer(this.matriz);
        repaint();
    }

    /**
     * Salva a matriz desenhada na copia
     */
    public void salvaMatriz() {
        for (int i = 0; i < width; i++) {
            copia[i] = Arrays.copyOf(matriz[i], width);
        }
    }

    /**
     * Muda a matriz para a copia antiga dela
     */
    public void limpaMatriz() {
        for (int x = 0; x < width; x++) {
            matriz[x] = Arrays.copyOf(copia[x], width);
        }
    }

    /**
     * Preenche a matriz toda com zeros e depois desenha tudo na tela
     */
    public void limpaMatrizToda() {
        for (int x = 0; x < this.width; x++) {
            Arrays.fill(matriz[x], BRANCO);
            Arrays.fill(copia[x], BRANCO);
        }
        for (int i = 0; i < listaObjetos.size(); i++) {
            if (i != this.posListaFiguraClicada) {
                listaObjetos.get(i).desenha();
            }
        }
        if (this.recorte != null) {
            this.recorte.desenha();
        }
        salvaMatriz();
    }

    /**
     * desenho a ser feito
     *
     * @param id
     */
    public void setDesenho(int id) {
        this.isDesenho = id;
    }

    /**
     * Apaga o recorte que estiver na tela
     */
    public void apagaRecorte() {
        this.recorte = null;
    }

    /**
     * Verifica qual o recorte, e aplica ele nas figuras
     */
    public void aplicaRecorte() {
        
        if (this.recorte != null) {
            
            this.janelaInicio = recorte.getPontoInicio();
            this.janelaFim = recorte.getPontoFim();
            
            recorte.aplicaRecorte(listaObjetos, janelaInicio, janelaFim);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (this.estadoDesenho == this.clicouPrimeiroPixel) { // desenhar a figura
            limpaMatriz();

            switch (this.isDesenho) {
                case idDDA:
                    DDA retaDDA = (DDA) listaObjetos.get(listaObjetos.size() - 1);
                    retaDDA.setPontoFim(new Ponto(e.getX(), e.getY()));
                    retaDDA.desenha();
                    break;
                case idBresenham:
                    Bresenham retaBresenham = (Bresenham) listaObjetos.get(listaObjetos.size() - 1);
                    retaBresenham.setPontoFim(new Ponto(e.getX(), e.getY()));
                    retaBresenham.desenha();
                    break;
                case idCircunferencia:
                    Circunferencia circunferencia = (Circunferencia) listaObjetos.get(listaObjetos.size() - 1);
                    circunferencia.atualizaRaio(this.pontoAnterior.getX(), this.pontoAnterior.getY(), e.getX(), e.getY());
                    circunferencia.setPontoFim(new Ponto(e.getX(), e.getY()));
                    circunferencia.desenha();
                    break;
                case idCohenSutherland:
                    if (this.recorte != null) {
                        this.recorte.setPontoFim(new Ponto(e.getX(), e.getY()));
                        this.recorte.desenha();
                    }
                    break;
                case idLianBarsky:
                    if (this.recorte != null) {
                        this.recorte.setPontoFim(new Ponto(e.getX(), e.getY()));
                        this.recorte.desenha();
                    }
                    break;
            }

        } else if (this.estadoDesenho == this.clicouArrastar) { // arrastar figura
            limpaMatriz();

            Figura figura = listaObjetos.get(posListaFiguraClicada);

            if (figura.getClass() == Circunferencia.class) { // para o circulo

                int novoXFim = figura.getPontoInicio().getX() + (e.getX() - pontoAnterior.getX());
                int novoYFim = figura.getPontoInicio().getY() + (e.getY() - pontoAnterior.getY());

                figura.setPontoFim(new Ponto(novoXFim, novoYFim));
                figura.setPontoInicio(new Ponto(e.getX(), e.getY()));

            } else { // para as retas

                int deltaX = figura.getPontoFim().getX() - figura.getPontoInicio().getX();
                int deltaY = figura.getPontoFim().getY() - figura.getPontoInicio().getY();

                figura.setPontoInicio(new Ponto(e.getX() - deltaX / 2, e.getY() - deltaY / 2));
                figura.setPontoFim(new Ponto(figura.getPontoInicio().getX() + deltaX, figura.getPontoInicio().getY() + deltaY));
            }
            figura.desenha();
        }
        atualizaImagem();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (this.estadoDesenho == this.naoClicou) {
            this.pontoAnterior = new Ponto(e.getX(), e.getY());
            switch (this.isDesenho) {
                case idMover:
                    for (int i = 0; i < listaObjetos.size(); i++) {
                        if (listaObjetos.get(i).foiClicado(e.getX(), e.getY())) {
                            this.estadoDesenho = this.clicouArrastar;
                            this.posListaFiguraClicada = i;
                            break;
                        }
                    }
                    
                    limpaMatrizToda();

                    if (this.posListaFiguraClicada >= 0) {
                        listaObjetos.get(this.posListaFiguraClicada).desenha();
                    }

                    break;
                    
                case idDDA:
                    this.estadoDesenho = this.clicouPrimeiroPixel;
                    listaObjetos.add(new DDA(matriz, this.pontoAnterior.getX(), this.pontoAnterior.getY(), e.getX(), e.getY(), PRETO));
                    break;
                case idBresenham:
                    this.estadoDesenho = this.clicouPrimeiroPixel;
                    listaObjetos.add(new Bresenham(matriz, this.pontoAnterior.getX(), this.pontoAnterior.getY(), e.getX(), e.getY(), PRETO));
                    break;
                case idCircunferencia:
                    this.estadoDesenho = this.clicouPrimeiroPixel;
                    listaObjetos.add(new Circunferencia(matriz, this.pontoAnterior.getX(), this.pontoAnterior.getY(), e.getX(), e.getY(), PRETO));
                    break;
                case idCohenSutherland:
                    this.recorte = new CohenSutherland(matriz, this.pontoAnterior.getX(), this.pontoAnterior.getY(), Math.abs(this.pontoAnterior.getX() - e.getX()), Math.abs(this.pontoAnterior.getY() - e.getY()), AZUL);
                    this.estadoDesenho = this.clicouPrimeiroPixel;
                    break;
                case idLianBarsky:
                    this.recorte = new LianBarsky(matriz, this.pontoAnterior.getX(), this.pontoAnterior.getY(), Math.abs(this.pontoAnterior.getX() - e.getX()), Math.abs(this.pontoAnterior.getY() - e.getY()), VERMELHO);
                    this.estadoDesenho = this.clicouPrimeiroPixel;
                    break;
                case idRotacionar:
                    for (int i = 0; i < listaObjetos.size(); i++) {
                        if (listaObjetos.get(i).foiClicado(e.getX(), e.getY())) {
                            this.estadoDesenho = this.clicouArrastar;
                            this.posListaFiguraClicada = i;
                            break;
                        }
                    }
                    
                    limpaMatrizToda();

                    double angulo = 0.0;

                    if (this.posListaFiguraClicada >= 0) {
                        String message = JOptionPane.showInputDialog("Digite o valor do angulo para rotação");
                        try {
                            angulo = Double.parseDouble(message.toString());
                            System.out.println("Angulo: " + angulo);
                        } catch (Exception exc) {
                            JOptionPane.showMessageDialog(this, "");
                        }
                        System.out.println("Rotaciona em " + angulo);
                        listaObjetos.get(this.posListaFiguraClicada).rotaciona(angulo);
                    }
                    this.estadoDesenho = this.naoClicou;
                    this.posListaFiguraClicada = -1;

                    salvaMatriz();
                    atualizaImagem();

                    break;
                case idEscala:                    
                    for (int i = 0; i < listaObjetos.size(); i++) {
                        if (listaObjetos.get(i).foiClicado(e.getX(), e.getY())) {
                            this.estadoDesenho = this.clicouArrastar;
                            this.posListaFiguraClicada = i;
                            break;
                        }
                    }

                    limpaMatrizToda();
                    
                    double escala = 0.0;
                    
                    if (this.posListaFiguraClicada >= 0) {
                        String message = JOptionPane.showInputDialog("Digite o valor para escalonar");
                        try {
                            escala = Double.parseDouble(message.toString());
                            System.out.println("Escala: " + escala);
                        } catch (Exception exc) {
                            JOptionPane.showMessageDialog(this, "");
                        }
                        listaObjetos.get(this.posListaFiguraClicada).mudaEscala(escala);
                    }
                    
                    this.estadoDesenho = this.naoClicou;

                    salvaMatriz();
                    atualizaImagem();
                    
                    break;

            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        this.estadoDesenho = this.naoClicou;
        this.posListaFiguraClicada = -1;
        this.pontoAnterior = new Ponto(-1, -1);

        aplicaRecorte();

        limpaMatrizToda();
        salvaMatriz();
        atualizaImagem();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void limpa(){
        
    }

}
