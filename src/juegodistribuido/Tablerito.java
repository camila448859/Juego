/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegodistribuido;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import juegodistribuido.Casillas;

/**
 *
 * @author Hp
 */
public class Tablerito extends JPanel {

    private String[] lenguajesDisponibles = {"melody", "kuromi", "tuzki", "huevito", "moño", "ping", "naval", "curita", "bolita", "calabaza", "zorra", "pikachu"};

    private int fila = 4;
    private int col = 6;
    private int anchoCasilla = 120;

    public boolean play = false;

    int c = 0;
    Casillas c1;
    Casillas c2;
    int aciertos = 0;

    /**
     * Constructor de clase
     */
    public Tablerito() {
        super();
        //propiedades
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setLayout(new java.awt.GridLayout(fila, col));
        Dimension d = new Dimension((anchoCasilla * col), (anchoCasilla * fila));
        setSize(d);
        setPreferredSize(d);
        //crea instancias de casillas para crear el tablero 
        int count = 0;
        for (int i = 1; i <= (fila * col); i++) {
            Casillas cas = new Casillas(String.valueOf(i));
            cas.setLenguaje(lenguajesDisponibles[count]);
            count++;
            count = (count >= lenguajesDisponibles.length) ? 0 : count++;
            cas.showLenguaje();
            cas.addMouseListener(new juegoMouseListener());
            this.add(cas);
        }
        setVisible(true);
    }

    /**
     * Inicia juegos - llena las casillas con pares de los lenguajes
     *
     * return na
     */
    public void comenzarJuego() {
        aciertos = 0;
        play = true;
        Component[] componentes = this.getComponents();
        //limpia lenguajes
        for (int i = 0; i < componentes.length; i++) {
            ((Casillas) componentes[i]).congelarLenguaje(false);
            ((Casillas) componentes[i]).ocultarLenguaje();
            ((Casillas) componentes[i]).setLenguaje("");
        }
        //coloca nuevo orden aleatorio de lenguajes
        for (int i = 0; i < componentes.length; i++) {
            int n = (int) (Math.random() * (lenguajesDisponibles.length));
            if (!existe(lenguajesDisponibles[n])) {//comprueba que bandera no este asignada mas de 2 veces                
                ((Casillas) componentes[i]).setLenguaje(lenguajesDisponibles[n]);
            } else {
                i--;
            }
        }

    }

    /**
     * Metodo que comprueba que una casilla existe
     *
     * num = nombre del objeto return Casilla NULL si no existe
     */
    private boolean existe(String lenguaje) {
        int count = 0;
        Component[] componentes = this.getComponents();
        for (int i = 0; i < componentes.length; i++) {
            if (componentes[i] instanceof Casillas) {
                if (((Casillas) componentes[i]).getSlenguaje().equals(lenguaje)) {
                    count++;
                }
            }
        }
        return (count == 2) ? true : false;
    }

    /**
     * Clase que implemenenta un MouseListener
     */
    class juegoMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

            if (play) {
                c++;//lleva la cuenta de los click realizados en las casillas            
                if (c == 1) { //primer click
                    c1 = ((Casillas) e.getSource()); //obtiene objeto
                    if (!c1.isCongelado()) {
                        c1.showLenguaje();
                        c1.setFocusable(false);
                    } else {//no toma en cuenta
                        c = 0;
                    }
                } else if (c == 2 && !c1.getName().equals(((Casillas) e.getSource()).getName())) {//segundo click
                    c2 = ((Casillas) e.getSource());
                    if (!c2.isCongelado()) {
                        c2.showLenguaje();
                        //compara imagenes
                        Animacion ani = new Animacion(c1, c2);
                        ani.execute();
                    }
                    c = 0;//contador de click a 0
                } else { //mas de 2 clic consecutivos no toma en cuenta
                    c = 0;
                }
            } else {
                System.out.println("Para jugar: presione comenzar");
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

    }

    /**
     *
     */
    class Animacion extends SwingWorker<Void, Void> {

        private Casillas casilla1;
        private Casillas casilla2;

        public Animacion(Casillas value1, Casillas value2) {
            this.casilla1 = value1;
            this.casilla2 = value2;
        }

        @Override
        protected Void doInBackground() throws Exception {
            //espera 0.5 segundo 
            Thread.sleep(500);
            if (casilla1.getSlenguaje().equals(casilla2.getSlenguaje())) {//son iguales
                casilla1.congelarLenguaje(true);
                casilla2.congelarLenguaje(true);
                aciertos++;
                if (aciertos == 12) {//win
                    JOptionPane.showMessageDialog(null, "GANASTEEE, YUPI YEI");
                }
            } else {//no son iguales
                casilla1.ocultarLenguaje();
                casilla2.ocultarLenguaje();
            }
            return null;
        }

    }
}
