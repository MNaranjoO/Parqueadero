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
            opcion = leerEntero("Opción: ", 1, 5);

            switch (opcion) {
                case 1 -> registrarEntrada();
                case 2 -> System.out.println("Registro de salida (pendiente)");
                case 3 -> System.out.println("Reporte de ocupación (pendiente)");
                case 4 -> System.out.println("Reporte financiero (pendiente)");
                case 5 -> System.out.println("Hasta pronto.");
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

        String tipo = leerTipoVehiculo();
        LocalDateTime ingreso =
                leerFechaHora("Fecha y hora de ingreso (yyyy-MM-dd HH:mm): ");

        tiposVehiculo[posicion] = tipo;
        fechasIngreso[posicion] = ingreso;
        celdasOcupadas++;
        celdasLibres--;

        imprimir("Vehículo (" + tipo + ") asignado a la celda " + (posicion + 1));
    }

    static int leerEntero(String mensaje, int minimo, int maximo) {
        while (true) {
            System.out.print(mensaje);
            String texto = SCANNER.nextLine().trim();
            if (!texto.isEmpty() && texto.length() <= 4) {
                int valor = leerDigitos(texto, 0, texto.length());
                if (valor >= minimo && valor <= maximo) {
                    return valor;
                }
            }
            imprimir("Dato no válido. Ingrese un número entre "
                    + minimo + " y " + maximo + ".");
        }
    }

    static String leerTipoVehiculo() {
        int opcion = leerEntero("Tipo de vehículo (1. Carro, 2. Moto): ", 1, 2);
        return opcion == 1 ? TIPO_CARRO : TIPO_MOTO;
    }

    static int leerPosicion() {
        return leerEntero("Número de celda (1-" + CAPACIDAD_TOTAL + "): ",
                1, CAPACIDAD_TOTAL);
    }

    static LocalDateTime leerFechaHora(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = SCANNER.nextLine().trim();
            if (!formatoFechaHoraValido(texto)) {
                imprimir("Fecha u hora inválidas. Use yyyy-MM-dd HH:mm y el horario 08:00-22:00.");
                continue;
            }

            LocalDateTime fechaHora = LocalDateTime.parse(texto, FORMATO_FECHA_HORA);
            if (!fechaHora.format(FORMATO_FECHA_HORA).equals(texto)) {
                imprimir("La fecha no existe en el calendario.");
                continue;
            }

            return fechaHora;
        }
    }

    static boolean formatoFechaHoraValido(String texto) {
        if (texto.length() != 16
                || texto.charAt(4) != '-'
                || texto.charAt(7) != '-'
                || texto.charAt(10) != ' '
                || texto.charAt(13) != ':') {
            return false;
        }

        int anio = leerDigitos(texto, 0, 4);
        int mes = leerDigitos(texto, 5, 2);
        int dia = leerDigitos(texto, 8, 2);
        int hora = leerDigitos(texto, 11, 2);
        int minuto = leerDigitos(texto, 14, 2);

        return anio >= 1 && mes >= 1 && mes <= 12 && dia >= 1 && dia <= 31
                && hora >= HORA_APERTURA && hora <= HORA_CIERRE
                && (hora != HORA_CIERRE || minuto <= 0)
                && minuto >= 0 && minuto <= 59;
    }

    static int leerDigitos(String texto, int inicio, int cantidad) {
        int valor = 0;
        for (int i = inicio; i < inicio + cantidad; i++) {
            char caracter = texto.charAt(i);
            if (caracter < '0' || caracter > '9') {
                return -1;
            }
            valor = valor * 10 + caracter - '0';
        }
        return valor;
    }
}