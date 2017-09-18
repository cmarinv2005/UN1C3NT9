/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openbravo.pos.panels;
import java.awt.*;

/**
 *
 * @author jonatan.echeverry
 */

public class Impresora {
    
private void drawString(Graphics g, String text, int x, int y) {//El drawString método no maneja líneas nuevas.
    //Asi que se debe de dividir la cadena en caracteres de nueva línea a sí mismo y trazar las líneas uno a uno con un 
    //adecuado desplazamiento vertical:
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }
/********************************************************************
*	El siguiente programa es un ejemplo bastante sencillo de 		*
*	impresión con JAVA. 											*
********************************************************************/

 
 
/********************************************************************
*	La siguiente clase llamada "Impresora", es la encargada de  	*
*	establecer la fuente con que se va a imprimir, de obtener el	*
*	trabajo de impresion, la página. En esta clase hay un método	*
*	llamado imprimir, el cual recibe una cadena y la imprime.		*
********************************************************************/
//      Font fuente = new Font("Comic Sans MS", Font.PLAIN, 10);//Se especifica la fuente de la letra y el tamaño de esta
	Font fuente = new Font("Times New Roman", Font.PLAIN, 10);//Se especifica la fuente de la letra y el tamaño de esta
        PrintJob pj;
	Graphics pagina;
 
 
	/********************************************************************
	*	A continuación el constructor de la clase. Aquí lo único que	*
	*	hago es tomar un objeto de impresion.							*
	********************************************************************/
	public Impresora(){
            pj = Toolkit.getDefaultToolkit().getPrintJob(new Frame(), "SCAT", null);
	}
 
	/********************************************************************
	*	A continuación el método "imprimir(String)", el encargado de 	*
	*	colocar en el objeto gráfico la cadena que se le pasa como 		*
	*	parámetro y se imprime.											*
	********************************************************************/
    public void imprimir(String Cadena)
	{
		//LO COLOCO EN UN try/catch PORQUE PUEDEN CANCELAR LA IMPRESION
		try
		{
                    
                        int margenIzqdo = 10; // Posición X de cada línea   
                        int margenSup = 30;   // Posición Y de la primera línea
                        int pasoLinea = 13;   // Incremento o salto entre líneas
                        
			pagina = pj.getGraphics();
			pagina.setFont(fuente);
			pagina.setColor(Color.black);
                        drawString(pagina, Cadena, 13, 13);
//			pagina.drawString(Cadena, margenIzqdo,margenSup += pasoLinea);//30 hace referencia a la distancia desde donde inicia la impresión
//                        pagina.drawString("\n", 10, 60);
			pagina.dispose();
			pj.end();
                        
		}catch(Exception e)
		{
			System.out.println("LA IMPRESION HA SIDO CANCELADA...");
		}
	}//FIN DEL PROCEDIMIENTO imprimir(String...)
 
 
}//FIN DE LA CLASE Impresora