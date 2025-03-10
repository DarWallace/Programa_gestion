package es.studium.clases;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;



public class AltaSede implements WindowListener, ActionListener
{
	Frame ventana = new Frame("Alta Sede");
	Label lblSede = new Label("Sede:");
	TextField txtSede = new TextField(20);
	Label lblLocalidad = new Label("Localidad:");
	TextField txtLocalidad = new TextField(20);
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");
	Dialog feedback = new Dialog(ventana, "Mensaje", true);
	Label mensaje = new Label("");

	public AltaSede()
	{
		ventana.setLayout(new FlowLayout());
		ventana.setSize(220, 200);
		ventana.setResizable(false);
		//ventana.setBackground(Color.pink);
		ventana.add(lblSede);
		ventana.add(txtSede);
		ventana.add(lblLocalidad);
		ventana.add(txtLocalidad);
		btnAceptar.addActionListener(this);
		ventana.add(btnAceptar);
		btnLimpiar.addActionListener(this);
		ventana.add(btnLimpiar);
		ventana.addWindowListener(this);

		feedback.setLayout(new FlowLayout());
		feedback.setSize(280, 100);
		feedback.setResizable(false);
		//feedback.setBackground(Color.pink);
		feedback.add(mensaje);
		feedback.addWindowListener(this);
		
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);

	}
	

	public void windowActivated(WindowEvent windowEvent)
	{	}
	public void windowClosed(WindowEvent windowEvent)
	{	}

	public void windowClosing(WindowEvent windowEvent)
	{
		if (windowEvent.getSource().equals(feedback))
		{
			feedback.setVisible(false);
		}
		else if (windowEvent.getSource().equals(ventana))
		{
			ventana.setVisible(false);
		}
	}

	public void windowDeactivated(WindowEvent windowEvent)
	{	}
	public void windowDeiconified(WindowEvent windowEvent)
	{	}
	public void windowIconified(WindowEvent windowEvent)
	{	}
	public void windowOpened(WindowEvent windowEvent)
	{	}
	public void actionPerformed(ActionEvent actionEvent)
	{
		if (actionEvent.getSource().equals(btnLimpiar))
		{
			txtSede.setText("");
			txtLocalidad.setText("");
			txtSede.requestFocus();
		}
		else if (actionEvent.getSource().equals(btnAceptar))
		{
// Conectarse a la BD
			Modelo modelo = new Modelo();
			Connection connection = modelo.conectar();
// Hacer el Alta
			if (!modelo.altaSede(connection, txtSede.getText(), txtLocalidad.getText()))
			{
				mensaje.setText("Error en Alta");
			}
			else
			{
				mensaje.setText("Alta Correcta");
				txtSede.setText("");
				txtLocalidad.setText("");
				txtSede.requestFocus();
			}
			feedback.setVisible(true);
// Desconectar
			modelo.desconectar(connection);
		}
	}
}

