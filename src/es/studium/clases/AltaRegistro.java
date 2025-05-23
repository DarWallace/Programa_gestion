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

public class AltaRegistro implements WindowListener, ActionListener
{
	Frame ventana = new Frame("Alta Registro");
	Label lblRegistro = new Label("Nuevo registro con Fecha:");
	
	Label lblDMY = new Label("         Dia         Mes        Año           ");
	Choice choiceDia = new Choice();
	Choice choiceMes = new Choice();
	Choice choiceAnio = new Choice();
	
	Label lblCliente = new Label("Cliente:");
	Label lblMaquina = new Label("Maquina usada:");
	
	Choice choiceCliente = new Choice();
	Choice choiceMaquina = new Choice();
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");
	Dialog feedback = new Dialog(ventana, "Mensaje", true);
	Label mensaje = new Label("");
	String usuario;
	Date ahora = new Date();
	SimpleDateFormat hoyDia= new SimpleDateFormat("dd");
	SimpleDateFormat hoyMes= new SimpleDateFormat("MM");
	SimpleDateFormat hoyAno= new SimpleDateFormat("yyy");

	public AltaRegistro(String u)
	{
		usuario=u;
		ventana.setLayout(new FlowLayout());
		ventana.setSize(220, 280);
		ventana.setResizable(false);
		Modelo modelo = new Modelo();
		Connection connection = modelo.conectar();
		modelo.rellenarChoiceClientes(connection, choiceCliente);
		modelo.rellenarChoiceMaquinas(connection, choiceMaquina);
		modelo.desconectar(connection);
		
		// Rellenar Choices de Fecha
		for (int i = 1; i <= 31; i++) {
		    choiceDia.add(String.format("%02d", i));
		}
		for (int i = 1; i <= 12; i++) {
		    choiceMes.add(String.format("%02d", i));
		}
		for (int i = 2024; i <= 2040; i++) {
		    choiceAnio.add(String.valueOf(i));
		}
		choiceDia.select(hoyDia.format(ahora));
		choiceMes.select(hoyMes.format(ahora));
		choiceAnio.select(hoyAno.format(ahora));
		ventana.add(lblRegistro);
		ventana.add(lblDMY);
		ventana.add(choiceDia);
		ventana.add(choiceMes);
		ventana.add(choiceAnio);
		ventana.add(lblCliente);
		ventana.add(choiceCliente);
		ventana.add(lblMaquina);
		ventana.add(choiceMaquina);
		btnAceptar.addActionListener(this);
		ventana.add(btnAceptar);
		btnLimpiar.addActionListener(this);
		ventana.add(btnLimpiar);
		ventana.addWindowListener(this);

		feedback.setLayout(new FlowLayout());
		feedback.setSize(280, 100);
		feedback.setResizable(false);
		feedback.add(mensaje);
		feedback.setLocationRelativeTo(null);
		feedback.addWindowListener(this);

		ventana.setLocationRelativeTo(null);
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
		} else if (windowEvent.getSource().equals(ventana))
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
		if (actionEvent.getSource().equals(btnLimpiar))
		{
			
			choiceDia.select(hoyDia.format(ahora));
			choiceMes.select(hoyMes.format(ahora));
			choiceAnio.select(hoyAno.format(ahora));
			choiceCliente.select(0);
			choiceMaquina.select(0);
		} else if (actionEvent.getSource().equals(btnAceptar))
		{
			String dia = choiceDia.getSelectedItem();
			String mes = choiceMes.getSelectedItem();
			String anio = choiceAnio.getSelectedItem();
			String fecha = anio + "-" + mes + "-" + dia;
			// Conectarse a la BD
			Modelo modelo = new Modelo();
			Connection connection = modelo.conectar();
			if (!choiceCliente.getSelectedItem().equals("Seleccionar un Cliente...") && !choiceMaquina.getSelectedItem().equals("Seleccionar una Maquina..."))
			{

// Hacer el Alta
				if (!modelo.altaRegistro(connection, idClienteFK, fecha, idMaquinaFK, usuario))
				{
					mensaje.setText("Error en Alta");
				} else
				{
					mensaje.setText("Alta Correcta");
					choiceDia.select(hoyDia.format(ahora));
					choiceMes.select(hoyMes.format(ahora));
					choiceAnio.select(hoyAno.format(ahora));
					choiceCliente.select(0);
					choiceMaquina.select(0);
				}

			} else
			{
				mensaje.setText("Error en Alta");
			}
			feedback.setVisible(true);
// Desconectar
			modelo.desconectar(connection);
		}
	}
}

