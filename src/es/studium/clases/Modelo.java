package es.studium.clases;

import java.awt.Choice;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Modelo
{
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/empresamaquina";
	String login = "admin";
	String password = "Studium2025#";
	String sentencia = "";

	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	public Connection conectar()
	{
		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, login, password);
		} catch (ClassNotFoundException e)
		{
			return null;
		} catch (SQLException e)
		{
			return null;
		}
		return connection;
	}

	public void desconectar(Connection connection)
	{
		if (connection != null)
		{
			try
			{
				connection.close();
			} catch (SQLException e)
			{
			}
		}
	}

	public boolean comprobarCredenciales(Connection connection, String usuario, String clave)
	{
		boolean credencialesCorrectas = false;
		sentencia = "SELECT * FROM usuarios " + "WHERE nombreUsuario = '" + usuario + "' AND claveUsuario = SHA2('"
				+ clave + "', 256);";
		try
		{
			statement = connection.createStatement();
			rs = statement.executeQuery(sentencia);
			if (rs.next() == true)
			{
				credencialesCorrectas = true;
			}
		} catch (SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
		}
		return credencialesCorrectas;
	}

	public boolean altaSede(Connection conexion, String nombre, String localidad)
	{
		boolean altaCorrecta = false;
		if (!nombre.isBlank() && !localidad.isBlank())
		{
			sentencia = "INSERT INTO sedes VALUES (null,'" + nombre + "','" + localidad + "');";
			try
			{
				statement = conexion.createStatement();
				statement.executeUpdate(sentencia);
				altaCorrecta = true;
			} catch (SQLException e)
			{
				altaCorrecta = false;
			}
		}
		return altaCorrecta;
	}

	public boolean bajaSede(Connection connection, String idSede)
	{
		boolean resultado = false;
		sentencia = "DELETE FROM sedes WHERE idsede = " + idSede + ";";
		try
		{
			statement = connection.createStatement();
			statement.executeUpdate(sentencia);
			resultado = true;
		} catch (SQLException sqlex)
		{
			resultado = false;
		}
		return resultado;
	}

	public boolean editarSede(Connection conexion, String idSede, String nombre, String localidad)
	{
		boolean edicionCorrecta = false;
		if (!nombre.isBlank() && !localidad.isBlank())
		{
			sentencia = "UPDATE sedes SET nombreSede = '" + nombre + 
		            "', localidadSede = '" + localidad + 
		            "' WHERE idSede = " + idSede + ";";
			try
			{
				statement = conexion.createStatement();
				statement.executeUpdate(sentencia);
				edicionCorrecta = true;
			} catch (SQLException e)
			{
				edicionCorrecta = false;
			}
		}
		return edicionCorrecta;
	}
	

	public void rellenarChoiceSedes(Connection connection, Choice ch)
	{
		ch.removeAll();
		ch.add("Seleccionar un Sede...");
		try
		{
			statement = connection.createStatement();
			sentencia = "SELECT * FROM sedes";
			rs = statement.executeQuery(sentencia);
			while (rs.next()){
				ch.add(rs.getInt("idSede") + " - " + rs.getString("nombreSede"));
			}
		}catch (SQLException sqlex)
		{}
	}

}
