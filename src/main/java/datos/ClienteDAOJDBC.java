package datos;

import dominio.Cliente;
import java.sql.*;
import java.util.*;

public class ClienteDAOJDBC {

    private static final String SQL_SELECT = "SELECT id_cliente, nombre, apellido, email, telefono, saldo"
            + " FROM cliente";
    private static final String SQL_SELECT_BY_ID = "SELECT * "
            + " FROM cliente WHERE id_cliente =?";
    private static final String SQL_INSERT = "INSERT INTO cliente(nombre, apellido, email, telefono, saldo)"
            + "VALUES(?,?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE cliente "
            + "SET nombre=?, apellido=?, email =?, telefono=?, saldo=? WHERE id_cliente=?";
    private static final String SQL_DELETE = "DELETE FROM cliente WHERE id_cliente=?";

    public List<Cliente> listar() {

        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Cliente c1 = null;
        List<Cliente> clientes = new ArrayList<>();

        try {
            c = Conexion.getConnection();
            ps = c.prepareStatement(SQL_SELECT);
            rs = ps.executeQuery();
            while (rs.next()) {
                int idCliente = rs.getInt("id_cliente");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String email = rs.getString("email");
                String telefono = rs.getString("telefono");
                double saldo = rs.getDouble("saldo");

                c1 = new Cliente(idCliente, nombre, apellido, email, telefono, saldo);
                clientes.add(c1);
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            Conexion.close(c);
        }

        return clientes;

    }

    public Cliente encontrar(Cliente cliente) {

        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = Conexion.getConnection();
            ps = c.prepareStatement(SQL_SELECT_BY_ID);
            ps.setInt(1, cliente.getIdCliente());
            rs = ps.executeQuery();
            rs.absolute(1);

            String nombre = rs.getString("nombre");
            String apellido = rs.getString("apellido");
            String email = rs.getString("email");
            String telefono = rs.getString("telefono");
            double saldo = rs.getDouble("saldo");

            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setEmail(email);
            cliente.setTelefono(telefono);
            cliente.setSaldo(saldo);

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(ps);
            Conexion.close(c);
        }

        return cliente;

    }

    public int insertar(Cliente cliente) {

        Connection c = null;
        PreparedStatement ps = null;
        int rows = 0;

        try {
            c = Conexion.getConnection();
            ps = c.prepareStatement(SQL_INSERT);

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getEmail());
            ps.setString(4, cliente.getTelefono());
            ps.setDouble(5, cliente.getSaldo());

            rows = ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(ps);
            Conexion.close(c);
        }

        return rows;
    }

    public int actualizar(Cliente cliente) {

        Connection c = null;
        PreparedStatement ps = null;
        int rows = 0;

        try {
            c = Conexion.getConnection();
            ps = c.prepareStatement(SQL_UPDATE);

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getEmail());
            ps.setString(4, cliente.getTelefono());
            ps.setDouble(5, cliente.getSaldo());
            ps.setInt(6, cliente.getIdCliente());

            rows = ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(ps);
            Conexion.close(c);
        }

        return rows;

    }

    public int eliminar(Cliente cliente) {

        Connection c = null;
        PreparedStatement ps = null;
        int rows = 0;

        try {
            c = Conexion.getConnection();
            ps = c.prepareStatement(SQL_DELETE);

            ps.setInt(1, cliente.getIdCliente());

            rows = ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(ps);
            Conexion.close(c);
        }

        return rows;

    }
}
