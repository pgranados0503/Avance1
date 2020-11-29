package db;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.sql.*;
import java.util.*;
public class ConsultorioAdmin {
    static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        int seleccion;
        int idDoctor;
        int idPaciente;
        int idCita;
        String user = "";
        String password = "";
        String nombreDoctor = "";
        String nombrePaciente = "";
        String fechaCita = "";
        String horaCita = "";
        String especialidadDoctor = "";
        BaseDatos persist = new BaseDatos("consultorio.db");
        validarStrings vString = new validarStrings();
        try (Scanner input = new Scanner(System.in)) {
            input.useDelimiter("\n");
            System.out.println("Ingrese su usuario y contraseña para iniciar");
            System.out.println("Usuario:");
            user = input.nextLine();
            System.out.println("Contraseña:");
            password = input.nextLine();
            List<Usuario> usuario = persist.getUsuarioByName(user, password);
            if (!usuario.isEmpty()) {
                while (true) {
                    System.out.println("\nPor favor ingrese una opción: ");
                    System.out.println("(1) Dar de alta doctores.");
                    System.out.println("(2) Dar de alta pacientes.");
                    System.out.println("(3) Crear una cita con fecha y hora.");
                    System.out.println("(4) Relacionar una cita con un doctor y un paciente.");
                    System.out.println("(0) Salir.");
                    try {
                        seleccion = input.nextInt();
                        switch (seleccion) {
                            case 0:
                                System.out.println("Saliendo..");
                                logger.info("Saliendo...");
                                return;
                            case 1:                           
                                nombreDoctor = vString.pedirString("Escribe el nombre del doctor: ");
                                especialidadDoctor = vString.pedirString("Escribe la especialidad del doctor: ");
                                persist.addDoctor(nombreDoctor, especialidadDoctor);
                                break;
                            case 2:            
                                nombrePaciente = vString.pedirString("Escribe el nombre del paciente: ");
                                persist.addPaciente(nombrePaciente);
                                break;
                            case 3:
                                fechaCita = vString.pedirString("Escribe la fecha de la cita: ");
                                horaCita = vString.pedirString("Escribe la hora de la cita: ");
                                persist.addCita(fechaCita, horaCita);
                                break;
                            case 4:
                               idDoctor = vString.pedirIntPositivo("Escribe el ID del doctor: ");
                                idPaciente = vString.pedirIntPositivo("Escribe el ID del paciente: ");
                                idCita = vString.pedirIntPositivo("Escribe el ID de la cita: ");
                                persist.addRelacionCitas(idDoctor, idPaciente,idCita);
                                break;
                            default:
                                System.err.println("Opción inválida.");
                                logger.error("Opción inválida: {}", seleccion);
                                break;
                        }
                    } catch (Exception ex) {
                        logger.error("{}: {}", ex.getClass(), ex.getMessage());
                        System.err.format("Ocurrió un error. Para más información consulta el log de la aplicación.");
                        input.next();
                    }
                }
            } else {
                System.out.println("No tiene autorización");
            }
        } catch (Exception ex) {
            logger.error("{}: {}", ex.getClass(), ex.getMessage());
            System.err.format("Ocurrió un error. Para más información consulta el log de la aplicación.");
        } finally { persist.getConnection().close(); }}}