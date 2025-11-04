/**
 * Jugador que utiliza la estrategia Martingala: dobla la apuesta después de cada pérdida.
 * Elige un número al azar al inicio y vuelve a la apuesta base después de ganar.
 *
 * @author Gorka Jesús Quesada Vega
 * @version 1.0
 */
public class JugadorMartingala extends Jugador{
    private int numeroElegido;
    private int apuestaActual;
    private int apuestaBase;

    /**
     * Constructor que inicializa el jugador con la estrategia Martingala.
     *
     * @param nombre el nombre del jugador
     * @param banca la banca del casino a la que se conecta el jugador
     */
    public JugadorMartingala(String nombre, Banca banca){
        super(nombre, banca);
        this.numeroElegido = 1 + random.nextInt(36);
        this.apuestaBase = 10;
        this.apuestaActual = apuestaBase;
        System.out.println(nombre + " usa Martingala y apuesta al número " + numeroElegido);
    }

    /**
     * Estrategia Martingala: dobla la apuesta después de cada pérdida.
     * Si gana, vuelve a la apuesta base. Si no puede apostar, reinicia la estrategia.
     */
    @Override
    public void apostar(){
        int numeroRuleta = banca.getRuleta().getNumeroActual();

        if (numeroRuleta != -1){
            if (banca.aceptarApuesta(this, apuestaActual)) {

                if (numeroRuleta == 0) {
                    System.out.println(nombre + " pierde " + apuestaActual + " euros por el 0");
                    apuestaActual *= 2;
                }
                else if (numeroRuleta == numeroElegido) {
                    int premio = 36 * apuestaActual;
                    banca.pagarPremio(this, premio);
                    System.out.println(nombre + " ¡acertó! Gana " + premio + " euros");
                    apuestaActual = apuestaBase;
                }
                else {
                    System.out.println(nombre + " pierde " + apuestaActual + " euros, siguiente apuesta: " + (apuestaActual * 2));
                    apuestaActual *= 2;
                }
            } else {
                apuestaActual = apuestaBase;
                System.out.println(nombre + " no puede apostar " + apuestaActual + ", vuelve a empezar con " + apuestaBase);
            }
        }
    }
}