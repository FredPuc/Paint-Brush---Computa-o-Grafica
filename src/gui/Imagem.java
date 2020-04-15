/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Imagem {
    
    /**
     * Le uma imagem de um arquivo e transforma
     * para um BufferedImage
     * @param path caminho do arquivo
     * @return buffer contendo a imagem
     */
    public static BufferedImage readImageToBuffer( String path ){
        
        File inputFile = null;
        BufferedImage imagemBuffer = null;
        
        try {
            inputFile = new File(path);
            imagemBuffer = ImageIO.read(inputFile);
            
        } catch (IOException ex) {
            System.out.println("Exception: Erro ao abrir a imagem para o panel");
        }
        
        return imagemBuffer;
    }
    
    /**
     * Le a imagem de um arquivo e transforma
     * para uma matriz de pixels RGBA
     * @param path caminho do arquivo
     * @return matriz RGBA
     */
    public static int[][] readImageToMatrix( String path ){
        return bufferToMatrix( readImageToBuffer(path) );
    }
    
    /**
     * Transforma uma matriz para um BufferedImage
     * @param matriz matriz de pixels RGBA
     * @return buffer equivalente a matriz
     */
    public static BufferedImage matrixToBuffer(int[][] matriz) {

        BufferedImage image = new BufferedImage(matriz.length, matriz[0].length, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                int pixel = matriz[i][j];

                int alpha = pixel >> 24 & 0XFF;
		int red = pixel >> 16 & 0xFF;
		int green = pixel >> 8 & 0XFF;
		int blue = pixel & 0xFF;
                
                pixel = ( alpha << 24 ) + ( red << 16 ) + ( green << 8 ) + blue;
                
                image.setRGB(i, j, pixel);
            }
        }
        return image;
    }

    /**
     * Transforma um BufferedImage para uma matriz
     * de pixels RGBA
     * @param imagem buffer da imagem
     * @return matriz de pixels RGBA
     */
    public static int[][] bufferToMatrix(BufferedImage imagem) {

        int width = imagem.getWidth();
        int height = imagem.getHeight();
        int[][] matriz = new int[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                matriz[x][y] = imagem.getRGB(x, y);
            }
        }
        return matriz;
    }
}
