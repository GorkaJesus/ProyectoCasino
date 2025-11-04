import java.util.Random;

/**
 * Clase abstracta que representa un jugador del casino.
 * Implementa Runnable para que cada jugador se ejecute en su propio hilo.
 *
 * @author Gorka Jesús Quesada Vega
 * @version 1.0
 */
public abstract class Jugador implements Runnable {
    protected int saldo;
    protected String nombre;
    protected Banca banca;
    protected Random random;
    protected boolean enJuego;

    /**
     * Constructor que inicializa un jugador con 1000 euros de saldo inicial.
     *
     * @param nombre el nombre del jugador
     * @param banca la banca del casino a la que se conecta el jugador
     */
    public Jugador(String nombre, Banca banca){
        this.saldo = 1000;
        this.nombre = nombre;
        this.banca = banca;
        this.random = new Random();
        this.enJuego = true;
    }

    /**
     * Método principal que ejecuta el comportamiento del jugador en un hilo separado.
     * El jugador realiza apuestas continuamente mientras tenga saldo y esté activo.
     */
    @Override
    public void run(){
        System.out.println(nombre + " comienza a jugar");

        while (enJuego && saldo > 0){
            try {
                apostar();
                Thread.sleep(100 + random.nextInt(400));
            } catch (InterruptedException e) {
                System.out.println(nombre + " fue interrumpido");
                break;
            }
        }

        if (saldo <= 0){
            System.out.println(nombre + " se ha quedado sin dinero");
        } else {
            System.out.println(nombre + " deja de jugar con " + saldo + " euros");
        }
    }

    /**
     * Método abstracto que define la estrategia de apuesta del jugador.
     * Cada tipo de jugador debe implementar su propia estrategia.
     */
    public abstract void apostar();

    /**
     * Obtiene el saldo actual del jugador.
     *
     * @return el saldo actual del jugador en euros
     */
    public int getSaldo(){
        return saldo;
    }

    /**
     * Obtiene el nombre del jugador.
     *
     * @return el nombre del jugador
     */
    public String getNombre(){
        return nombre;
    }

    /**
     * Aumenta el saldo del jugador al ganar un premio.
     *
     * @param cantidad la cantidad de dinero a añadir al saldo
     */
    public void sumarSaldo(int cantidad){
        this.saldo += cantidad;
    }

    /**
     * Reduce el saldo del jugador al realizar una apuesta.
     *
     * @param cantidad la cantidad de dinero a restar del saldo
     */
    public void restarSaldo(int cantidad){
        this.saldo -= cantidad;
    }

    /**
     * Detiene la ejecución del jugador.
     * El hilo terminará en la siguiente iteración del bucle.
     */
    public void detener(){
        this.enJuego = false;
    }
}