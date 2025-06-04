package es.studium.clases;

import java.awt.Choice;
import java.awt.Desktop;
import java.awt.TextField;

import java.io.BufferedWriter;
import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.text.SimpleDateFormat;
import java.util.Date;


import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

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
		int tipo = -1;

		sentencia = "SELECT * FROM usuarios " + "WHERE nombreUsuario = '" + usuario + "' AND claveUsuario = SHA2('"
				+ clave + "', 256);";
		try
		{
			statement = conexion.createStatement();
			guardarLog(usuario, "Usuario registrado");
			rs = statement.executeQuery(sentencia);
			if (rs.next() == true)
			{
				tipo = rs.getInt("tipoUsuario");

			}
		} catch (SQLException e)
		{
			guardarLog(usuario, "Error de registro");
			System.out.println("Error en la sentencia SQL");
		}
		return tipo;
	}

	public boolean altaSede(Connection conexion, String nombre, String localidad, String usuario)
	{
		boolean altaCorrecta = false;
		if (!nombre.isBlank() && !localidad.isBlank())
		{
			sentencia = "INSERT INTO sedes VALUES (null,'" + nombre + "','" + localidad + "');";
			try
			{
				statement = conexion.createStatement();
				guardarLog(usuario, sentencia);
				statement.executeUpdate(sentencia);
				altaCorrecta = true;

			} catch (SQLException e)
			{
				altaCorrecta = false;
			}
		}
		return altaCorrecta;
	}
	public boolean altaMaquina(Connection conexion, String maquina, String precio, String idSede, String usuario)
	{
		boolean altaCorrecta = false;
		if (!maquina.isBlank() && !precio.isBlank())
		{

			sentencia = "INSERT INTO maquinas VALUES (null,'" + maquina + "','" + Double.parseDouble(precio) + "','"
					+ Integer.parseInt(idSede) + "');";
			try
			{
				statement = conexion.createStatement();
				guardarLog(usuario, sentencia);
				statement.executeUpdate(sentencia);
				altaCorrecta = true;
			} catch (SQLException e)
			{
				altaCorrecta = false;
				System.out.println("Error en sentencia");
			}
		}
		return altaCorrecta;
	}
	public boolean altaRegistro(Connection conexion, String idClienteFK, String fecha, String idMaquinaFK, String usuario)
	{
		boolean altaCorrecta = false;
		if (!fecha.isBlank())
		{
			sentencia = "INSERT INTO registros VALUES (null," + Integer.parseInt(idClienteFK) + ",'" + fecha + "',"
					+ Integer.parseInt(idMaquinaFK) + ");";
			try
			{
				statement = conexion.createStatement();
				guardarLog(usuario, sentencia);
				statement.executeUpdate(sentencia);
				altaCorrecta = true;
			} catch (SQLException e)
			{
				altaCorrecta = false;
				System.out.println("Error en sentencia");
				System.out.println(sentencia);
			}
		}
		return altaCorrecta;
	}
	public boolean altaCliente(Connection conexion, String nombre, String telefono, String usuario)
	{
		boolean altaCorrecta = false;
		if (!nombre.isBlank() && !telefono.isBlank())
		{
			sentencia = "INSERT INTO Clientes VALUES (null,'" + nombre + "','" + telefono + "');";
			try
			{
				statement = conexion.createStatement();
				guardarLog(usuario, sentencia);
				statement.executeUpdate(sentencia);
				altaCorrecta = true;
			} catch (SQLException e)
			{
				altaCorrecta = false;
			}
		}
		return altaCorrecta;
	}
	
	public boolean bajaSede(Connection conexion, String idSede, String usuario)
	{
		boolean resultado = false;
		sentencia = "DELETE FROM sedes WHERE idsede = " + idSede + ";";
		try
		{
			statement = conexion.createStatement();
			guardarLog(usuario, sentencia);
			statement.executeUpdate(sentencia);
			resultado = true;
		} catch (SQLException sqlex)
		{
			resultado = false;
		}
		return resultado;
	}
	public boolean bajaMaquina(Connection conexion, String idMaquina, String usuario)
	{
		boolean resultado = false;
		sentencia = "DELETE FROM maquinas WHERE idMaquina = " + idMaquina + ";";
		try
		{
			statement = conexion.createStatement();
			guardarLog(usuario, sentencia);
			statement.executeUpdate(sentencia);
			resultado = true;
		} catch (SQLException sqlex)
		{
			resultado = false;
		}
		return resultado;
	}
	public boolean bajaRegistro(Connection conexion, String idRegistro, String usuario)
	{
		boolean resultado = false;
		sentencia = "DELETE FROM registros WHERE idRegistro = " + idRegistro + ";";
		try
		{
			statement = conexion.createStatement();
			guardarLog(usuario, sentencia);
			statement.executeUpdate(sentencia);
			resultado = true;
		} catch (SQLException sqlex)
		{
			resultado = false;
		}
		return resultado;
	}
	public boolean bajaCliente(Connection conexion, String idCliente, String usuario)
	{
		boolean resultado = false;
		sentencia = "DELETE FROM clientes WHERE idCliente = " + idCliente + ";";
		try
		{
			statement = conexion.createStatement();
			guardarLog(usuario, sentencia);
			statement.executeUpdate(sentencia);
			resultado = true;
		} catch (SQLException sqlex)
		{
			resultado = false;
		}
		return resultado;
	}
	
	public boolean editarSede(Connection conexion, String idSede, String nombre, String localidad, String usuario)
	{
		boolean edicionCorrecta = false;
		if (!nombre.isBlank() && !localidad.isBlank())
		{
			sentencia = "UPDATE sedes SET nombreSede = '" + nombre + "', localidadSede = '" + localidad
					+ "' WHERE idSede = " + idSede + ";";
			try
			{
				statement = conexion.createStatement();
				guardarLog(usuario, sentencia);
				statement.executeUpdate(sentencia);
				edicionCorrecta = true;
			} catch (SQLException e)
			{
				edicionCorrecta = false;
			}
		}
		return edicionCorrecta;
	}
	public boolean editarMaquina(Connection conexion, String idMaquina, String nombre, String precio, String idSedeFk, String usuario)
	{
		boolean edicionCorrecta = false;
		if (!nombre.isBlank() && !precio.isBlank())
		{
			sentencia = "UPDATE maquinas SET nombreMaquina = '" + nombre + "', precioMaquina = '" + precio+ "', idSedeFk = '" + idSedeFk
					+ "' WHERE idMaquina = " + idMaquina + ";";
			try
			{
				statement = conexion.createStatement();
				guardarLog(usuario, sentencia);
				statement.executeUpdate(sentencia);
				edicionCorrecta = true;
			} catch (SQLException e)
			{
				edicionCorrecta = false;
			}
		}
		return edicionCorrecta;
	}
	public boolean editarRegistro(Connection conexion, String idRegistro, String idClienteFK, String fecha, String idMaquinaFK, String usuario)
	{
		boolean edicionCorrecta = false;
		if (!fecha.isBlank())
		{
			sentencia = "UPDATE registros SET idClienteFK = " + Integer.parseInt(idClienteFK) + ", fechaUso = '" + fecha+ "', idMaquinaFK = " 
					+ Integer.parseInt(idMaquinaFK)
					+ " WHERE idRegistro = " + idRegistro + ";";
			try
			{
				statement = conexion.createStatement();
				guardarLog(usuario, sentencia);
				statement.executeUpdate(sentencia);
				edicionCorrecta = true;
			} catch (SQLException e)
			{
				edicionCorrecta = false;
				System.out.println(sentencia);
			}
		}
		return edicionCorrecta;
	}
	public boolean editarCliente(Connection conexion, String idCliente, String nombre, String telefono, String usuario)
	{
		boolean edicionCorrecta = false;
		if (!nombre.isBlank() && !telefono.isBlank())
		{
			sentencia = "UPDATE clientes SET nombreCliente = '" + nombre + "', telefonoCliente = '" + telefono
					+ "' WHERE idCliente = " + idCliente + ";";
			try
			{
				statement = conexion.createStatement();
				guardarLog(usuario, sentencia);
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
		ch.add("Seleccionar una Sede...");
		try
		{
			statement = conexion.createStatement();
			sentencia = "SELECT * FROM sedes";
			rs = statement.executeQuery(sentencia);
			while (rs.next())
			{
				ch.add(rs.getInt("idSede") + " - " + rs.getString("nombreSede"));
			}
		} catch (SQLException sqlex)
		{
			System.out.println("Error sentencia SQL");
		}
	}
	public void rellenarChoiceMaquinas(Connection conexion, Choice ch)
	{
		ch.removeAll();
		ch.add("Seleccionar una Maquina...");
		try
		{
			statement = conexion.createStatement();
			sentencia = "SELECT * FROM Maquinas";
			rs = statement.executeQuery(sentencia);
			while (rs.next())
			{
				ch.add(rs.getInt("idMaquina") + " - " + rs.getString("nombreMaquina"));
			}
		} catch (SQLException sqlex)
		{
			System.out.println("Error sentencia SQL");
		}
	}
	public void rellenarChoiceRegistros(Connection conexion, Choice ch)
	{
		ch.removeAll();
		ch.add("Seleccionar un Registro...");
		try
		{
			statement = conexion.createStatement();
			sentencia = "SELECT idRegistro, date_format(fechaUso, '%d/%m/%Y') as 'fecha', nombreCliente, nombreMaquina from clientes join registros on clientes.idCliente=registros.idClienteFK join maquinas on maquinas.idMaquina=registros.idMaquinaFK order by 1;";
			rs = statement.executeQuery(sentencia);
			while (rs.next())
			{
				ch.add(rs.getInt("idRegistro") + " - " + rs.getString("fecha")+ " - " + rs.getString("nombreCliente")+ " - " + rs.getString("nombreMaquina"));
			}
		} catch (SQLException sqlex)
		{
			System.out.println("Error sentencia SQL");
		}
	}
	public void rellenarChoiceClientes(Connection conexion, Choice ch)
	{
		ch.removeAll();
		ch.add("Seleccionar un Cliente...");
		try
		{
			statement = conexion.createStatement();
			sentencia = "SELECT * FROM clientes";
			rs = statement.executeQuery(sentencia);
			while (rs.next())
			{
				ch.add(rs.getInt("idCliente") + " - " + rs.getString("nombreCliente"));
			}
		} catch (SQLException sqlex)
		{
			System.out.println("Error sentencia SQL");
		}
	}
	
	public void mostrarDatosSedes(Connection conexion, String idSede, TextField txtNombreSede,
			TextField txtLocalidadSede, String usuario)
	{


		try
		{
			statement = conexion.createStatement();
			guardarLog(usuario, sentencia);
			sentencia = "SELECT * FROM sedes WHERE idSede =" + idSede;
			rs = statement.executeQuery(sentencia);
			rs.next();
			txtNombreSede.setText(rs.getString("nombreSede"));
			txtLocalidadSede.setText(rs.getString("localidadSede"));
		} catch (SQLException sqlex)
		{
			System.out.println("Error  mostrar sentencia SQL");
		}

	}
	public void mostrarDatosMaquinas(Connection conexion, String idMaquina, TextField txtNombreMaquina,
			TextField txtPrecioMaquina, Choice choiceSede, String usuario)
	{

		try
		{
			statement = conexion.createStatement();
			sentencia = "SELECT * FROM maquinas WHERE idMaquina =" + idMaquina;
			guardarLog(usuario, sentencia);
			rs = statement.executeQuery(sentencia);
			if (rs.next())
			{
				txtNombreMaquina.setText(rs.getString("nombreMaquina"));
				txtPrecioMaquina.setText(rs.getString("precioMaquina"));
				int idSedeMaquina = rs.getInt("idSedeFK");

				// Inicializa la posición a seleccionar como no encontrada
				int indiceSeleccionar = 0;

				// Recorremos todos los ítems para encontrar el índice del idSede
				for (int i = 0; i < choiceSede.getItemCount(); i++)
				{
					String item = choiceSede.getItem(i);
					if (item.startsWith(idSedeMaquina + " -"))
					{
						indiceSeleccionar = i;
					}
				}

				// Seleccionamos el índice (aunque esté al final del bucle)
				choiceSede.select(indiceSeleccionar);
			}
			//			
		} catch (SQLException sqlex)
		{
			System.out.println("Error  mostrar sentencia SQL");
		}

	}
	public void mostrarDatosRegistros(Connection conexion, String idRegistro,
			Choice choiceDia, Choice choiceMes, Choice choiceAnio,
			Choice choiceCliente, Choice choiceMaquina, String usuario)
	{

		try
		{
			statement = conexion.createStatement();
			sentencia = "SELECT * FROM registros WHERE idRegistro =" + idRegistro;
			guardarLog(usuario, sentencia);
			rs = statement.executeQuery(sentencia);

			if (rs.next())
			{ 
				// FECHA
				String fecha = rs.getString("fechaUso"); // formato yyyy-MM-dd
				String[] partes = fecha.split("-"); // [0]=yyyy, [1]=MM, [2]=dd

				String anio = partes[0];
				String mes = partes[1];
				String dia = partes[2];

				choiceDia.select(dia);
				choiceMes.select(mes);
				choiceAnio.select(anio);

				int idClienteRegis = rs.getInt("idClienteFK");

				// Inicializa la posición a seleccionar como no encontrada
				int indiceSeleccionar = 0;

				// Recorremos todos los ítems para encontrar el índice del idSede
				for (int i = 0; i < choiceCliente.getItemCount(); i++)
				{
					String item = choiceCliente.getItem(i);
					if (item.startsWith(idClienteRegis + " -"))
					{
						indiceSeleccionar = i;
					}
				}

				// Seleccionamos el índice (aunque esté al final del bucle)
				choiceCliente.select(indiceSeleccionar);

				int idMaquinaRegis = rs.getInt("idMaquinaFK");

				// Inicializa la posición a seleccionar como no encontrada
				int indiceSeleccionar2 = 0;

				// Recorremos todos los ítems para encontrar el índice del idSede
				for (int i = 0; i < choiceMaquina.getItemCount(); i++)
				{
					String item2 = choiceMaquina.getItem(i);
					if (item2.startsWith(idMaquinaRegis + " -"))
					{
						indiceSeleccionar2 = i;
					}
				}

				// Seleccionamos el índice (aunque esté al final del bucle)
				choiceMaquina.select(indiceSeleccionar2);
			}
			//			
		} catch (SQLException sqlex)
		{
			System.out.println("Error  mostrar sentencia SQL");
			System.out.println(sentencia);
		}

	}
	public void mostrarDatosClientes(Connection conexion, String idCliente, TextField txtNombreCliente,
			TextField txtLocalidadCliente, String usuario)
	{

		try
		{
			statement = conexion.createStatement();
			sentencia = "SELECT * FROM clientes WHERE idCliente =" + idCliente;
			guardarLog(usuario, sentencia);
			rs = statement.executeQuery(sentencia);
			rs.next();
			txtNombreCliente.setText(rs.getString("nombreCliente"));
			txtLocalidadCliente.setText(rs.getString("telefonoCliente"));
		} catch (SQLException sqlex)
		{
			System.out.println("Error  mostrar sentencia SQL");
		}

	}
	
	public String consultarSedes(Connection conexion, String usuario)
	{
		{
			String contenidoTextarea = String.format("%-8s - %-18s - %-10s\n","Código","Nombre","Localidad");
			sentencia = "SELECT * FROM sedes;";
			try
			{
				statement = conexion.createStatement();
				guardarLog(usuario, sentencia);
				rs = statement.executeQuery(sentencia);
				while (rs.next())
				{
					contenidoTextarea = contenidoTextarea + String.format("%-8d - %-18s - %-10s\n",
							rs.getInt("idSede"),
							rs.getString("nombreSede"),
							rs.getString("localidadSede"));

				}

			} catch (SQLException sqlex)

			{

				System.out.println("Error en SQL");
			}
			return contenidoTextarea;
		}
	}
	public String consultarMaquinas(Connection conexion, String usuario)
	{
		{
			String contenidoTextarea = String.format("%-8s - %-18s - %-10s - %-10s\n", "Código", "Nombre", "Precio", "Sede");
			sentencia = "SELECT maquinas.idMaquina, maquinas.nombreMaquina, maquinas.precioMaquina, sedes.nombreSede "
					+ "FROM maquinas JOIN sedes ON maquinas.idSedeFK = sedes.idSede;";
			try
			{
				statement = conexion.createStatement();
				guardarLog(usuario, sentencia);
				rs = statement.executeQuery(sentencia);
				while (rs.next())
				{
					contenidoTextarea = contenidoTextarea + String.format("%-8d - %-18s - %-10s - %-10s\n",
							rs.getInt("idMaquina"),
							rs.getString("nombreMaquina"),
							rs.getString("precioMaquina") + "€",
							rs.getString("nombreSede"));
				}

			} catch (SQLException sqlex)

			{
				System.out.println("Error en SQL");
			}
			return contenidoTextarea;
		}
	}
	public String consultarRegistros(Connection conexion, String usuario)
	{
		{
			String contenidoTextarea = String.format("%-8s - %-15s - %-10s - %-18s\n", "Código", "Fecha", "Cliente", "Maquina");
			sentencia = "SELECT idRegistro, date_format(fechaUso, '%d/%m/%Y') as 'fecha', nombreCliente, nombreMaquina "
					+ "FROM clientes JOIN registros on clientes.idCliente=registros.idClienteFK"
					+ " JOIN maquinas on maquinas.idMaquina=registros.idMaquinaFK"
					+ " order by 1;";
			try
			{
				statement = conexion.createStatement();
				guardarLog(usuario, sentencia);
				rs = statement.executeQuery(sentencia);
				while (rs.next())
				{
					contenidoTextarea = contenidoTextarea + String.format("%-8d - %-15s - %-10s - %-18s\n",
							rs.getInt("idRegistro"),
							rs.getString("fecha"),
							rs.getString("nombreCliente"),
							rs.getString("nombreMaquina"));
				}

			} catch (SQLException sqlex)

			{
				System.out.println("Error en SQL");
				System.out.println(sentencia);
			}
			return contenidoTextarea;
		}
	}
	public String consultarClientes(Connection conexion, String usuario)
	{
		{
			String contenidoTextarea = String.format("%-8s - %-18s - %-10s\n","Código","Nombre","Teléfono");
			sentencia = "SELECT * FROM clientes;";
			try
			{
				statement = conexion.createStatement();
				guardarLog(usuario, sentencia);
				rs = statement.executeQuery(sentencia);
				while (rs.next())
				{
					contenidoTextarea = contenidoTextarea + String.format("%-8d - %-18s - %-10s\n",
							rs.getInt("idCliente"),
							rs.getString("nombreCliente"),
							rs.getString("telefonoCliente"));

				}

			} catch (SQLException sqlex)

			{
				System.out.println("Error en SQL");
			}
			return contenidoTextarea;
		}
	}
	
	public void ImprimirClientes(String dest, String datos, String usuario)
	{
		guardarLog(usuario, "Exportación de datos de Clientes");
		try
		{
			//Initialize PDF writer 
			PdfWriter writer = new PdfWriter(dest);
			//Initialize PDF document 
			PdfDocument pdf = new PdfDocument(writer);
			// Initialize document 
			Document document = new Document(pdf, PageSize.A4.rotate());
			document.setMargins(20, 20, 20, 20);
			PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
			PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

			Table table = new Table(UnitValue.createPercentArray(new float[] { 20, 40, 40 }))
					.useAllAvailableWidth();
			// Reading Headers
			String[] lineas = datos.split("\\n");
			for (int i = 0; i < lineas.length; i++) {
				String linea = lineas[i].trim();
				if (!linea.isEmpty()) {
					process(table, linea, (i == 0 ? bold : font), (i == 0));
				}
			}
			document.add(table);
			//Close document 
			document.close();

			Desktop.getDesktop().open(new File(dest));
		} catch (IOException ioe)
		{	}
	}
	public void ImprimirMaquinas(String dest, String datos, String usuario)
	{
		guardarLog(usuario, "Exportación de datos de Maquinas");
		try
		{
			//Initialize PDF writer 
			PdfWriter writer = new PdfWriter(dest);
			//Initialize PDF document 
			PdfDocument pdf = new PdfDocument(writer);
			// Initialize document 
			Document document = new Document(pdf, PageSize.A4.rotate());
			document.setMargins(20, 20, 20, 20);
			PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
			PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

			Table table = new Table(UnitValue.createPercentArray(new float[] { 20, 40, 30, 20 }))
					.useAllAvailableWidth();
			// Reading Headers
			String[] lineas = datos.split("\\n");
			for (int i = 0; i < lineas.length; i++) {
				String linea = lineas[i].trim();
				if (!linea.isEmpty()) {
					process(table, linea, (i == 0 ? bold : font), (i == 0));
				}
			}
			document.add(table);
			//Close document 
			document.close();

			Desktop.getDesktop().open(new File(dest));
		} catch (IOException ioe)
		{	}
	}
	public void ImprimirRegistros(String dest, String datos, String usuario)
	{
		guardarLog(usuario, "Exportación de datos de Registros");
		try
		{
			//Initialize PDF writer 
			PdfWriter writer = new PdfWriter(dest);
			//Initialize PDF document 
			PdfDocument pdf = new PdfDocument(writer);
			// Initialize document 
			Document document = new Document(pdf, PageSize.A4.rotate());
			document.setMargins(20, 20, 20, 20);
			PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
			PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

			Table table = new Table(UnitValue.createPercentArray(new float[] { 20, 40, 30, 20 }))
					.useAllAvailableWidth();
			// Reading Headers
			String[] lineas = datos.split("\\n");
			for (int i = 0; i < lineas.length; i++) {
				String linea = lineas[i].trim();
				if (!linea.isEmpty()) {
					process(table, linea, (i == 0 ? bold : font), (i == 0));
				}
			}
			document.add(table);
			//Close document 
			document.close();

			Desktop.getDesktop().open(new File(dest));
		} catch (IOException ioe)
		{	}
	}
	public void ImprimirSedes(String dest, String datos, String usuario)
	{
		guardarLog(usuario, "Exportación de datos de sedes");
		try
		{
			//Initialize PDF writer 
			PdfWriter writer = new PdfWriter(dest);
			//Initialize PDF document 
			PdfDocument pdf = new PdfDocument(writer);
			// Initialize document 
			Document document = new Document(pdf, PageSize.A4.rotate());
			document.setMargins(20, 20, 20, 20);
			PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
			PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

			Table table = new Table(UnitValue.createPercentArray(new float[] { 20, 40, 30 }))
					.useAllAvailableWidth();
			// Reading Headers
			String[] lineas = datos.split("\\n");
			for (int i = 0; i < lineas.length; i++) {
				String linea = lineas[i].trim();
				if (!linea.isEmpty()) {
					process(table, linea, (i == 0 ? bold : font), (i == 0));
				}
			}
			document.add(table);
			//Close document 
			document.close();

			Desktop.getDesktop().open(new File(dest));
		} catch (IOException ioe)
		{	}
	}

	public void process(Table table, String line, PdfFont font, boolean isHeader)
	{
		String[] tokens = line.split("\\s*-\\s*");
		for (String token : tokens) {
			if (isHeader) {
				table.addHeaderCell(new Cell().add(new Paragraph(token.trim()).setFont(font)));
			} else {
				table.addCell(new Cell().add(new Paragraph(token.trim()).setFont(font)));
			}
		}
	}

	public void guardarLog(String usuario, String mensaje) {
		//[01/04/2025][11:50:40][usuario][mensaje]
		Date ahora = new Date();
		SimpleDateFormat formateador= new SimpleDateFormat("[dd/MM/yyyy][HH:mm:ss.S]");

		try
		{
			// Destino de los datos
			FileWriter fw = new FileWriter("registro.txt", true);
			// Buffer de escritura
			BufferedWriter bw = new BufferedWriter(fw);
			// Objeto para la escritura
			PrintWriter salida = new PrintWriter(bw);
			// Guardamos la primera línea
			salida.println(formateador.format(ahora)+" ["+usuario+"] ["+mensaje+"]");
			// Cerrar el objeto salida, el objeto bw y el fw
			salida.close();
			bw.close();
			fw.close();
			System.out.println("¡Archivo creado correctamente!");
		} catch (IOException i)
		{
			System.out.println("Se produjo un error de Archivo");
		}}

	public static void ayuda()
	{
		try
		{
			ProcessBuilder pb = new ProcessBuilder("hh.exe", "AyudaGestion.chm");
			pb.start();
			System.out.println("Abriendo el archivo CHM...");
		}
		catch (IOException e)
		{
			System.err.println("Error al intentar abrir el archivo CHM: " + e.getMessage());
		}
	}


}
