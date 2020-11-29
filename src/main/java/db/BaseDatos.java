package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BaseDatos {

    private String database;
    private Connection connection;
    private Statement statement;

    public BaseDatos(String db) throws ClassNotFoundException, SQLException {
        this.database = db;
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + database);
        this.statement = connection.createStatement();
    }

    public Connection getConnection() {
        return connection;
    }
    //FUNCION VALIDAR USUARIO
    public List<Usuario> getUsuarioByName(String nombre, String password) throws SQLException {
        ResultSet rs = this.statement.executeQuery("select * from usuario where upper(nombre)='" + nombre.toUpperCase() + "' and password='" + password.toUpperCase() + "'");
        List<Usuario> usuario = new ArrayList();
        while (rs.next()) {
            Usuario temp = new Usuario();
            temp.setIdUsuario(rs.getInt("id_usuario"));
            temp.setIdUsuario(rs.getInt("nombre"));
            temp.setIdUsuario(rs.getInt("password"));
            temp.setIdUsuario(rs.getInt("rol"));
            usuario.add(temp);
        }
        return usuario;
    }

    public boolean addDoctor(String nombre, String especialidad) throws SQLException {
        String sql = "insert into doctores(nombre, especialidad) "
                + "values (?,?)";
        PreparedStatement prepStmt = this.connection.prepareStatement(sql);
        prepStmt.setString(1, nombre);
        prepStmt.setString(2, especialidad);
        return prepStmt.execute();
    }

    public boolean addPaciente(String nombre) throws SQLException {
        String sql = "insert into pacientes(nombre) "
                + "values (?)";
        PreparedStatement prepStmt = this.connection.prepareStatement(sql);
        prepStmt.setString(1, nombre);
        return prepStmt.execute();
    }
    public boolean addCita(String fecha, String hora) throws SQLException {
        String sql = "insert into citasMedicas(Fecha, Hora) "
                + "values (?,?)";
        PreparedStatement prepStmt = this.connection.prepareStatement(sql);
        prepStmt.setString(1, fecha);
        prepStmt.setString(2, hora);
        return prepStmt.execute();
    }
    public boolean addRelacionCitas(Integer idDoctor, Integer idPaciente, Integer idCita) throws SQLException {
        String sql = "insert into relacionDoctorPaciente(IdDoctor, IdPaciente, IdCita) "
                + "values (?,?,?)";
        PreparedStatement prepStmt = this.connection.prepareStatement(sql);
        prepStmt.setInt(1, idDoctor);
        prepStmt.setInt(2, idPaciente);
        prepStmt.setInt(3, idCita);
        return prepStmt.execute();
    }
}
