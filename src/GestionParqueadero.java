import java.util.Scanner;

public class GestionParqueadero {

    static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = SCANNER.nextInt();
            SCANNER.nextLine();

            switch (opcion) {
                case 1 -> System.out.println("Registro de entrada (pendiente)");
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
}