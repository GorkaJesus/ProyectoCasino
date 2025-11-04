import java.util.Random;

/**
 * La ruleta del casino que genera números aleatorios cada 3 segundos.
 * Implementa Runnable para ejecutarse en un hilo separado.
 *
 * @author Gorka Jesús Quesada Vega
 * @version 1.0
 */
public class Ruleta implements Runnable {
    private int numeroActual;
    private boolean enJuego;
    private Random random;

    /**
     * Constructor que inicializa la ruleta con valores por defecto.
     * El número actual es -1 indicando que no ha comenzado el juego.
     */
    public Ruleta(){
        this.numeroActual = -1;
        this.enJuego = true;
        this.random = new Random();
    }

    /**
     * Método principal que ejecuta la lógica de la ruleta en un hilo separado.
     * Genera números aleatorios cada 3 segundos mientras esté activa.
     */
    @Override
    public void run() {
        System.out.println("La ruleta ha comenzado a girar");

        while (enJuego) {
            try{
                Thread.sleep(3000);
                numeroActual = random.nextInt(37);
                System.out.println("Número en juego: " + numeroActual);

            } catch (InterruptedException e) {
                System.out.println("La ruleta fue interrumpida");
                break;
            }
        }
        System.out.println("La ruleta se ha detenido");
    }

    /**
     * Obtiene el número actual que salió en la ruleta.
     *
     * @return el número actual de la ruleta, o -1 si no ha comenzado
     */
    public int getNumeroActual(){
        return numeroActual;
    }

    /**
     * Detiene la ejecución de la ruleta.
     * El hilo terminará en la siguiente iteración del bucle.
     */
    public void detener(){
        this.enJuego = false;
    }
}