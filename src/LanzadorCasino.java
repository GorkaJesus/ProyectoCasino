import java.util.ArrayList;
import java.util.List;

/**
 * Esta es la clase principal que pone en marcha todo el casino
 * Se encarga de crear todos los objetos y coordinar la simulación
 *
 * @author Gorka Jesús Quesada Vega
 * @version 1.0
 */
public class LanzadorCasino {

    /**
     * Método principal - aquí empieza todo el programa
     * @param args no usamos argumentos de entrada
     */
    public static void main(String[] args) {
        System.out.println("Empezando la simulación del casino...");
        System.out.println("======================================");

        // Primero creo la ruleta, que es lo más importante
        Ruleta ruleta = new Ruleta();

        // Luego la banca, que controla el dinero
        Banca banca = new Banca(ruleta);

        // Listas para guardar a los jugadores y sus hilos
        List<Jugador> jugadores = new ArrayList<>();
        List<Thread> hilos = new ArrayList<>();

        System.out.println("Voy a crear los jugadores...");

        // Creo 4 jugadores de cada tipo
        for (int i = 1; i <= 4; i++) {
            jugadores.add(new JugadorNumeroConcreto("Jugador Número " + i, banca));
            jugadores.add(new JugadorParImpar("Jugador Par/Impar " + i, banca));
            jugadores.add(new JugadorMartingala("Jugador Martingala " + i, banca));
        }

        // Preparo el hilo para la ruleta
        Thread hiloRuleta = new Thread(ruleta);

        // Creo un hilo para cada jugador
        for (Jugador jugador : jugadores) {
            hilos.add(new Thread(jugador));
        }

        System.out.println("Todo listo, empezando la simulación...");

        // Arranco la ruleta primero
        hiloRuleta.start();

        // Luego arranco todos los jugadores
        for (Thread hilo : hilos) {
            hilo.start();
        }

        try {
            System.out.println("La simulación va a durar 2 minutos...");
            // Espero 2 minutos (120 segundos)
            Thread.sleep(120000);

            System.out.println("Parando todo...");
            // Paro la ruleta
            ruleta.detener();

            // Paro todos los jugadores
            for (Jugador jugador : jugadores) {
                jugador.detener();
            }

            // Espero a que todo termine correctamente
            hiloRuleta.join();
            for (Thread hilo : hilos) {
                hilo.join();
            }

            // Muestro los resultados finales
            mostrarResultados(jugadores, banca);

        } catch (InterruptedException e) {
            System.out.println("Algo interrumpió la simulación");
            e.printStackTrace();
        }
    }

    /**
     * Este método muestra cómo les fue a todos al final de la simulación
     * @param jugadores lista con todos los jugadores
     * @param banca la banca con su dinero final
     */
    private static void mostrarResultados(List<Jugador> jugadores, Banca banca) {
        System.out.println("\n--- RESULTADOS FINALES ---");
        System.out.println("Dinero que le queda a la banca: " + banca.getSaldo() + "€");

        int gananciasTotales = 0;
        int jugadoresConGanancias = 0;
        int jugadoresConPerdidas = 0;

        System.out.println("\nCómo le fue a cada jugador:");
        for (Jugador jugador : jugadores) {
            // Calculo si ganó o perdió respecto a los 1000€ iniciales
            int resultado = jugador.getSaldo() - 1000;

            // Le pongo el signo + si ganó dinero
            String resultadoStr = (resultado >= 0 ? "+" : "") + resultado + "€";
            System.out.println(jugador.getNombre() + ": " + jugador.getSaldo() + "€ (" + resultadoStr + ")");

            // Cuento estadísticas
            if (resultado > 0) {
                jugadoresConGanancias++;
                gananciasTotales += resultado;
            } else if (resultado < 0) {
                jugadoresConPerdidas++;
            }
        }

        System.out.println("\n--- ESTADÍSTICAS ---");
        System.out.println("Jugadores que ganaron dinero: " + jugadoresConGanancias + " de " + jugadores.size());
        System.out.println("Jugadores que perdieron dinero: " + jugadoresConPerdidas + " de " + jugadores.size());
        System.out.println("Jugadores que se quedaron igual: " + (jugadores.size() - jugadoresConGanancias - jugadoresConPerdidas) + " de " + jugadores.size());
        System.out.println("Dinero total ganado por todos los jugadores: " + gananciasTotales + "€");
        System.out.println("Resultado final de la banca: " + (50000 - banca.getSaldo()) + "€");

        System.out.println("\n¡Ya está! Simulación terminada.");
    }
}