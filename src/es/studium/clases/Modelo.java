package es.studium.clases;

import java.awt.Choice;
import java.awt.TextField;
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

	public void desconectar(Connection conexion)
	{
		if (conexion != null)
		{
			try
			{
				conexion.close();
			} catch (SQLException e)
			{
			}
		}
	}

	public int comprobarCredenciales(Connection conexion, String usuario, String clave)
	{
		int tipo=-1;
		
		sentencia = "SELECT * FROM usuarios " + "WHERE nombreUsuario = '" + usuario + "' AND claveUsuario = SHA2('"
				+ clave + "', 256);";
		try
		{
			statement = conexion.createStatement();
			rs = statement.executeQuery(sentencia);
			if (rs.next() == true)
			{
				tipo=rs.getInt("tipoUsuario");
			}
		} catch (SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
		}
		return tipo;
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

	public boolean bajaSede(Connection conexion, String idSede)
	{
		boolean resultado = false;
		sentencia = "DELETE FROM sedes WHERE idsede = " + idSede + ";";
		try
		{
			statement = conexion.createStatement();
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
	

	public void rellenarChoiceSedes(Connection conexion, Choice ch)
	{
		ch.removeAll();
		ch.add("Seleccionar un Sede...");
		try
		{
			statement = conexion.createStatement();
			sentencia = "SELECT * FROM sedes";
			rs = statement.executeQuery(sentencia);
			while (rs.next()){
				ch.add(rs.getInt("idSede") + " - " + rs.getString("nombreSede"));
			}
		}catch (SQLException sqlex)
		{
			System.out.println("Error sentencia SQL");
		}
	}
	public void mostrarDatosSedes(Connection conexion,String idSede, 
			TextField txtNombreSede, TextField txtLocalidadSede) {
		
		try
		{
			statement = conexion.createStatement();
			sentencia = "SELECT * FROM sedes WHERE idSede ="+idSede;
			rs = statement.executeQuery(sentencia);
			rs.next();
			txtNombreSede.setText(rs.getString("nombreSede"));
			txtLocalidadSede.setText(rs.getString("localidadSede"));
		}catch (SQLException sqlex)
		{
			System.out.println("Error  mostrar sentencia SQL");
		}
		
	}

	public String consultarSedes(Connection conexion)
	{
		{
			String contenidoTextarea = "Código - Nombre - Localidad\n";
			sentencia = "SELECT * FROM sedes;";
			try
			{
			statement = conexion.createStatement();
			rs = statement.executeQuery(sentencia);
			while (rs.next())
			{
			contenidoTextarea = contenidoTextarea
			+ rs.getInt("idSede")
			+ "           - "
			+ rs.getString("nombreSede")
			+ " - "
			+ rs.getString("localidadSede")
			+ "\n";
			} 

			}catch (SQLException sqlex)

			{
				System.out.println("Error en SQL");
			}
			return contenidoTextarea;
			}
	}

}
