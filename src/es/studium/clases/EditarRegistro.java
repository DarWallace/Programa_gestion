package es.studium.clases;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditarRegistro implements WindowListener, ActionListener
{
	Frame ventana = new Frame("Editar Registro");
	Label lblEleccion = new Label("Elegir un Registro");
	Choice choiceRegistro = new Choice();
	Button bEditar = new Button("Editar");

	Dialog cambio = new Dialog(ventana, "Modificación", true);
	Label lblNombre = new Label("Registro:");
	Label lblDMY = new Label("         Dia         Mes        Año           ");
	Choice choiceDia = new Choice();
	Choice choiceMes = new Choice();
	Choice choiceAnio = new Choice();

	Label lblCliente = new Label("Cliente:");
	Choice choiceCliente = new Choice();
	Label lblMaquina = new Label("Maquina usada:");
	Choice choiceMaquina = new Choice();
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");

	Button bAceptar = new Button("Aceptar");

	Dialog feedback = new Dialog(ventana, "Mensaje", true);
	Label mensaje = new Label("cambio correcto");
	String usuario;
	Date ahora = new Date();
	SimpleDateFormat hoyDia = new SimpleDateFormat("dd");
	SimpleDateFormat hoyMes = new SimpleDateFormat("MM");
	SimpleDateFormat hoyAno = new SimpleDateFormat("yyy");

	public EditarRegistro(String u)
	{
		usuario = u;
		ventana.setLayout(new FlowLayout());
		ventana.setSize(310, 200);

		ventana.setResizable(false);
		Modelo modelo = new Modelo();
		Connection connection = modelo.conectar();
		modelo.rellenarChoiceRegistros(connection, choiceRegistro);
		modelo.rellenarChoiceMaquinas(connection, choiceMaquina);
		modelo.rellenarChoiceClientes(connection, choiceCliente);
		modelo.desconectar(connection);
		ventana.add(choiceRegistro);

		// Rellenar Choices de Fecha
		for (int i = 1; i <= 31; i++)
		{
			choiceDia.add(String.format("%02d", i));
		}
		for (int i = 1; i <= 12; i++)
		{
			choiceMes.add(String.format("%02d", i));
		}
		for (int i = 2024; i <= 2040; i++)
		{
			choiceAnio.add(String.valueOf(i));
		}

		bEditar.addActionListener(this);
		ventana.add(bEditar);
		ventana.setLocationRelativeTo(null);
		ventana.addWindowListener(this);

		cambio.setLayout(new FlowLayout());
		cambio.setSize(220, 250);
		cambio.setResizable(false);
		cambio.add(lblNombre);
		cambio.add(lblDMY);
		cambio.add(choiceDia);
		cambio.add(choiceMes);
		cambio.add(choiceAnio);

		cambio.add(lblCliente);
		cambio.add(choiceCliente);
		cambio.add(lblMaquina);
		cambio.add(choiceMaquina);
		bAceptar.addActionListener(this);
		cambio.add(bAceptar);
		cambio.setLocationRelativeTo(null);
		cambio.addWindowListener(this);

		feedback.setLayout(new FlowLayout());
		feedback.setSize(280, 100);
		feedback.setResizable(false);
		feedback.add(mensaje);
		feedback.setLocationRelativeTo(null);
		feedback.addWindowListener(this);

		ventana.setVisible(true);
	}

	public void windowActivated(WindowEvent windowEvent)
	{
	}

	public void windowClosed(WindowEvent windowEvent)
	{
	}

	public void windowClosing(WindowEvent windowEvent)
	{

		if (windowEvent.getSource().equals(feedback))

		{
			feedback.setVisible(false);
		} else if (windowEvent.getSource().equals(cambio))

		{

			cambio.setVisible(false);

		}

		else if (windowEvent.getSource().equals(ventana))
		{
			ventana.setVisible(false);
		}
	}

	public void windowDeactivated(WindowEvent windowEvent)
	{
	}

	public void windowDeiconified(WindowEvent windowEvent)
	{
	}

	public void windowIconified(WindowEvent windowEvent)
	{
	}

	public void windowOpened(WindowEvent windowEvent)
	{
	}

	public void actionPerformed(ActionEvent actionEvent)
	{
		String idClienteFK = choiceCliente.getSelectedItem().split(" - ")[0];
		String idMaquinaFK = choiceMaquina.getSelectedItem().split(" - ")[0];
		String idRegistro = choiceRegistro.getSelectedItem().split(" - ")[0];
		String dia = choiceDia.getSelectedItem();
		String mes = choiceMes.getSelectedItem();
		String anio = choiceAnio.getSelectedItem();
		String fecha = anio + "-" + mes + "-" + dia;
		if (actionEvent.getSource().equals(bEditar))
		{
			if (!choiceRegistro.getSelectedItem().equals("Seleccionar un Registro..."))
			{

				// Conectarse a la BD
				Modelo modelo = new Modelo();
				Connection connection = modelo.conectar();
				modelo.mostrarDatosRegistros(connection, idRegistro, choiceDia, choiceMes, choiceAnio, choiceCliente,
						choiceMaquina, usuario);

				cambio.setVisible(true);

			} else
			{
				mensaje.setText("Debes elegir un Registro");
				feedback.setVisible(true);
			}
		} else if (actionEvent.getSource().equals(bAceptar))
		{
			Modelo modelo = new Modelo();
			Connection connection = modelo.conectar();

			if (!modelo.editarRegistro(connection, idRegistro, idClienteFK, fecha, idMaquinaFK, usuario))
			{
//Mostrar feed back incorrecto
				mensaje.setText("Error en el cambio");

			} else
			{
// Mostrar feed back correcto
				mensaje.setText("Cambio realizado");

				modelo.rellenarChoiceMaquinas(connection, choiceMaquina);
				cambio.setVisible(false);
			}
			feedback.setVisible(true);
			modelo.desconectar(connection);

		}

	}
}
