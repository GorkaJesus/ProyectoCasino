/**
 * Jugador que apuesta siempre a par o siempre a impar, decidido aleatoriamente al inicio.
 * Apuesta 10 euros por ronda y gana 20 euros si acierta.
 *
 * @author Gorka Jesús Quesada Vega
 * @version 1.0
 */
public class JugadorParImpar extends Jugador{
    private boolean apostarPar;

    /**
     * Constructor que inicializa el jugador y decide aleatoriamente si apostará a par o impar.
     *
     * @param nombre el nombre del jugador
     * @param banca la banca del casino a la que se conecta el jugador
     */
    public JugadorParImpar(String nombre, Banca banca){
        super(nombre, banca);
        this.apostarPar = random.nextBoolean();
        String estrategia = apostarPar ? "PAR" : "IMPAR";
        System.out.println(nombre + " apostará siempre a " + estrategia);
    }

    /**
     * Estrategia de apuesta: apuesta 10 euros a par o impar según su elección inicial.
     * Si acierta, gana 20 euros (el doble de lo apostado).
     * No apuesta cuando sale el 0.
     */
    @Override
    public void apostar(){
        int numeroRuleta = banca.getRuleta().getNumeroActual();

        if (numeroRuleta != -1 && numeroRuleta != 0){
            if (banca.aceptarApuesta(this, 10)){
                boolean esPar = (numeroRuleta % 2 == 0);

                boolean gano = (apostarPar && esPar) || (!apostarPar && !esPar);

                if (gano){
                    banca.pagarPremio(this, 20);
                    System.out.println(nombre + " acertó " + (esPar ? "PAR" : "IMPAR") + " y gana 20 euros");
                }
            }
        }
    }
}