/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegodistribuido;

import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Hp
 */
public class Casillas extends JLabel{
    private int ancho=50;
    private int alto=50;
    private ImageIcon hide = new ImageIcon(getClass().getResource("/images/kitty.png"));
    private ImageIcon lenguaje;
    private String slenguaje="";
    private boolean pasmado=false;
    
     /** 
      * constructor de clase
      * name -> String El nomnbre de instancia
      */
    public Casillas( String name ){
        super();
        Dimension d = new Dimension(ancho,alto);
        setName(name);
        setSize( d );
        setPreferredSize( d );
        setText("");                
        setIcon( hide );
        setVisible(true);        
        setOpaque(true);
        setCursor(new Cursor( Cursor.HAND_CURSOR ));
    }
   
    public void showLenguaje(){
        setIcon( lenguaje );
    }
    void ocultarLenguaje(){
        if( !pasmado ){
            setIcon( hide );
        }
    }
    
    public void congelarLenguaje(boolean value){
        this.pasmado=value;
    }
    
    /**
     * Metodo que retorna el valor boolean de una casilla si este esta o no congelado
     * return boolean 
     */
    public boolean isCongelado(){
        return this.pasmado;
    }
    
    /**
     * Asigna la bandera que contendra la casilla
     * name = nombre del lenguaje
     */
    public void setLenguaje( String name ){
        this.slenguaje = name;
        if( !name.equals("") ){            
            lenguaje = new ImageIcon(getClass().getResource("/images/"+name+".png"));        
        }        
    }
    
    /**
     * Retorna el nombre de la bandera que tenga asignada la casilla, si no tiene ninguna
     * retorna una cadena vacia
     * return String 
     */
    public String getSlenguaje(){
        return slenguaje;
    }
}
