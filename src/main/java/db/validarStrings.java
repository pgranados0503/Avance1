package db;

import java.util.*;

public class validarStrings {




    private Scanner string;


    public validarStrings() {
        string = new Scanner(System.in);
        string.useDelimiter("\n");
        string.useLocale(Locale.US);
    }
    public String pedirString(String mensaje) {
        System.out.println(mensaje);
        String cadena = string.next();
        return cadena;
    }

    public int pedirIntPositivo(String mensaje) {

        int num;
        do {
            try {
                System.out.println(mensaje);
                num = string.nextInt();
            } catch (InputMismatchException ex) {

                num = -1;
                string.next();
            }

            if (num < 0) {
                System.out.println("Error! Consulte al Administrador");
            }

        } while (num < 0);

        return num;
    }



}
