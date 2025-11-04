/**
 * Representa la banca del casino que gestiona todo el dinero y las apuestas.
 * Controla el flujo de dinero entre jugadores y asegura la sincronización.
 *
 * @author Gorka Jesús Quesada Vega
 * @version 1.0
 */
public class Banca {
    private int saldo;
    private Ruleta ruleta;

    /**
     * Constructor que inicializa la banca con 50,000 euros y la ruleta asociada.
     *
     * @param ruleta la ruleta del casino a la que está asociada la banca
     */
    public Banca(Ruleta ruleta) {
        this.saldo = 50000;
        this.ruleta = ruleta;
    }

    /**
     * Acepta una apuesta de un jugador si tiene suficiente saldo.
     * Este método es synchronized para evitar condiciones de carrera.
     *
     * @param jugador el jugador que realiza la apuesta
     * @param cantidad la cantidad de dinero a apostar
     * @return true si la apuesta fue aceptada, false si el jugador no tiene suficiente saldo
     */
    public synchronized boolean aceptarApuesta(Jugador jugador, int cantidad){
        if (jugador.getSaldo() >= cantidad){
            jugador.restarSaldo(cantidad);
            this.saldo += cantidad;
            System.out.println("El jugador " + jugador.getNombre() + " apuesta " + cantidad + " euros");
            return true;
        }
        return false;
    }

    /**
     * Paga un premio a un jugador si la banca tiene suficiente saldo.
     * Este método es synchronized para evitar condiciones de carrera.
     *
     * @param jugador el jugador que recibe el premio
     * @param premio la cantidad de dinero a pagar como premio
     */
    public synchronized void pagarPremio(Jugador jugador, int premio){
        if (this.saldo >= premio){
            jugador.sumarSaldo(premio);
            this.saldo -= premio;
            System.out.println(jugador.getNombre() + " gana " + premio + " euros");
        } else {
            System.out.println("La banca no tiene suficiente dinero para pagar a " + jugador.getNombre());
        }
    }

    /**
     * Obtiene el saldo actual de la banca.
     *
     * @return el saldo actual de la banca en euros
     */
    public int getSaldo() {
        return saldo;
    }

    /**
     * Obtiene la ruleta asociada a esta banca.
     *
     * @return la ruleta del casino
     */
    public Ruleta getRuleta() {
        return ruleta;
    }
}