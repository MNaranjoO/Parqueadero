import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class GestionParqueadero {

    static final Scanner SCANNER = new Scanner(System.in);

    static final int CAPACIDAD_TOTAL = 10;
    static final int TARIFA_HORA_CARRO = 4000;
    static final int TARIFA_DIA_CARRO = 30000;
    static final int TARIFA_HORA_MOTO = 2000;
    static final int TARIFA_DIA_MOTO = 15000;
    static final int UMBRAL_HORAS_DIA = 8;
    static final int HORA_APERTURA = 8;
    static final int HORA_CIERRE = 22;

    static final String TIPO_CARRO = "carro";
    static final String TIPO_MOTO = "moto";

    static final DateTimeFormatter FORMATO_FECHA_HORA =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    static String[] tiposVehiculo = new String[CAPACIDAD_TOTAL];
    static LocalDateTime[] fechasIngreso = new LocalDateTime[CAPACIDAD_TOTAL];

    static int celdasOcupadas;
    static int celdasLibres;
    static long diasACobrar;
    static long horasACobrar;
    static long recaudoCarros;
    static long recaudoMotos;

    public static void main(String[] args) {
        inicializarDatos();
        int opcion;
        do {
            mostrarMenu();
            opcion = SCANNER.nextInt();
            SCANNER.nextLine();

            switch (opcion) {
                case 1 -> registrarEntrada();
                case 2 -> System.out.println("Registro de salida (pendiente)");
                case 3 -> System.out.println("Reporte de ocupación (pendiente)");
                case 4 -> System.out.println("Reporte financiero (pendiente)");
                case 5 -> System.out.println("Hasta pronto.");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 5);

        SCANNER.close();
    }

    static void mostrarMenu() {
        System.out.println("\n=== PARQUEADERO ===");
        System.out.println("1. Registrar entrada");
        System.out.println("2. Registrar salida");
        System.out.println("3. Reporte de ocupación");
        System.out.println("4. Reporte financiero");
        System.out.println("5. Salir");
        System.out.print("Opción: ");
    }

    static void inicializarDatos() {
        for (int i = 0; i < CAPACIDAD_TOTAL; i++) {
            tiposVehiculo[i] = null;
            fechasIngreso[i] = null;
        }
        celdasOcupadas = 0;
        celdasLibres = CAPACIDAD_TOTAL;
        recaudoCarros = 0;
        recaudoMotos = 0;
    }

    static void imprimir(String mensaje) {
        System.out.println(mensaje);
    }

    static int celdaDisponible() {
        for (int i = 0; i < CAPACIDAD_TOTAL; i++) {
            if (tiposVehiculo[i] == null) {
                return i;
            }
        }
        return -1;
    }

    static void registrarEntrada() {
        int posicion = celdaDisponible();

        if (posicion == -1) {
            imprimir("El parqueadero está lleno.");
            return;
        }

        System.out.print("Tipo de vehículo (1. Carro, 2. Moto): ");
        int opcionTipo = SCANNER.nextInt();
        SCANNER.nextLine();

        if (opcionTipo != 1 && opcionTipo != 2) {
            imprimir("Tipo de vehículo no válido.");
            return;
        }
        String tipo = opcionTipo == 1 ? TIPO_CARRO : TIPO_MOTO;

        System.out.print("Fecha y hora de ingreso (yyyy-MM-dd HH:mm): ");
        LocalDateTime ingreso =
                LocalDateTime.parse(SCANNER.nextLine().trim(), FORMATO_FECHA_HORA);

        tiposVehiculo[posicion] = tipo;
        fechasIngreso[posicion] = ingreso;
        celdasOcupadas++;
        celdasLibres--;

        imprimir("Vehículo (" + tipo + ") asignado a la celda " + (posicion + 1));
    }
}