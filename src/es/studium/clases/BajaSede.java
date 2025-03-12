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
import java.sql.ResultSet;
import java.sql.Statement;

public class BajaSede implements WindowListener, ActionListener
{
	Frame ventana = new Frame("Baja Sede");
	Label nombre = new Label("Nombre:");
	Choice choice = new Choice();

	Button btnBorrar = new Button("Borrar");
	Dialog feedback = new Dialog(ventana, "Mensaje", true);
	Label msj = new Label("Borrado correcto");

	Dialog confirmacion = new Dialog(ventana, "Mensaje", true);
	Label pregunta = new Label("Estas seguro de realizar borrar la sede...");
	Label espacio = new Label("    ");
	Button btnSi = new Button(" Sí ");
	Button btnNo = new Button(" No ");
	String sentencia = "";

	public BajaSede()
	{
		ventana.setLayout(new FlowLayout());
		ventana.setSize(240, 200);
		ventana.setResizable(false);
		Modelo modelo = new Modelo();
		Connection connection = modelo.conectar();
		modelo.rellenarChoiceSedes(connection, choice);
		modelo.desconectar(connection);
		ventana.add(choice);
		ventana.add(btnBorrar);
		btnBorrar.addActionListener(this);
		ventana.setLocationRelativeTo(null);
		ventana.addWindowListener(this);
		ventana.setVisible(true);

		feedback.setLayout(new FlowLayout());
		feedback.setSize(280, 100);
		feedback.setResizable(false);
		feedback.add(msj);
		feedback.addWindowListener(this);
		feedback.setLocationRelativeTo(null);

		confirmacion.setLayout(new FlowLayout());
		confirmacion.setSize(210, 100);
		confirmacion.setResizable(false);
		confirmacion.add(pregunta);
		confirmacion.add(espacio);
		btnSi.addActionListener(this);
		btnNo.addActionListener(this);
		confirmacion.add(btnSi);
		confirmacion.add(btnNo);
		confirmacion.setLocationRelativeTo(null);
		confirmacion.addWindowListener(this);
		ventana.setVisible(true);
	}


	@Override
	public void windowOpened(WindowEvent e)
	{
	}

	@Override
	public void windowClosing(WindowEvent windowEvent)
	{
		if (windowEvent.getSource().equals(feedback))
		{
			feedback.setVisible(false);
			confirmacion.setVisible(false);
		} else if (windowEvent.getSource().equals(confirmacion))
		{
			confirmacion.setVisible(false);
		} else if (windowEvent.getSource().equals(ventana))
		{
			ventana.setVisible(false);
		}
	}

	@Override
	public void windowClosed(WindowEvent e)
	{
	}

	@Override
	public void windowIconified(WindowEvent e)
	{
	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{
	}

	@Override
	public void windowActivated(WindowEvent e)
	{
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
	}
	@Override
	public void actionPerformed(ActionEvent actionEvent)
	{
		if ((actionEvent.getSource().equals(btnBorrar))
				&& (!choice.getSelectedItem().equals("Seleccionar una Sede...")))
		{
			pregunta.setText("¿Quiere borrar " + choice.getSelectedItem() + "?");
			confirmacion.setVisible(true);
		} else if (actionEvent.getSource().equals(btnNo))
		{
			confirmacion.setVisible(false);
		} else if (actionEvent.getSource().equals(btnSi))
		{
			Modelo modelo = new Modelo();
			Connection connection = modelo.conectar();
			String idSede = choice.getSelectedItem().split(" - ")[0];
			if (!modelo.bajaSede(connection, idSede))
			{
//Mostrar feed back correcto
				msj.setText("Baja incorrecta");
			} else
			{
//Mostrar feedback ERROR
				msj.setText("Baja correcta");
				modelo.rellenarChoiceSedes(connection, choice);
			}
			feedback.setVisible(true);
			modelo.desconectar(connection);
		} else
		{
			msj.setText("Elija una Sede");
			feedback.setVisible(true);
		}
	}

}