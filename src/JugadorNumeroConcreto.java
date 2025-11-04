/**
 * Jugador que siempre apuesta al mismo número elegido al azar al inicio.
 * Apuesta 10 euros por ronda y gana 360 euros si acierta su número.
 *
 * @author Gorka Jesús Quesada Vega
 * @version 1.0
 */
public class JugadorNumeroConcreto extends Jugador{
    private int numeroElegido;

    /**
     * Constructor que inicializa el jugador y elige un número aleatorio entre 1 y 36.
     *
     * @param nombre el nombre del jugador
     * @param banca la banca del casino a la que se conecta el jugador
     */
    public JugadorNumeroConcreto(String nombre, Banca banca){
        super(nombre, banca);
        this.numeroElegido = 1 + random.nextInt(36);
        System.out.println(nombre + " siempre apostará al número " + numeroElegido);
    }

    /**
     * Estrategia de apuesta: apuesta 10 euros a su número elegido.
     * Si acierta, gana 360 euros (36 veces lo apostado).
     * Si sale el 0, pierde su apuesta como todos los jugadores.
     */
    @Override
    public void apostar(){
        int numeroRuleta = banca.getRuleta().getNumeroActual();

        if (numeroRuleta != -1){
            if (banca.aceptarApuesta(this, 10)){
                if (numeroRuleta == 0) {
                    System.out.println(nombre + " pierde porque salió el 0");
                }
                else if (numeroRuleta == numeroElegido){
                    banca.pagarPremio(this, 360);
                    System.out.println(nombre + " ¡acertó el número! Gana 360 euros");
                }
                else {
                    System.out.println(nombre + " no acertó el número " + numeroElegido);
                }
            }
        }
    }
}