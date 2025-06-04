package es.studium.clases;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;

import javax.imageio.ImageIO;

public class AltaCliente implements WindowListener, ActionListener
{
	Frame ventana = new Frame("Alta Cliente");
	Label lblcliente = new Label("Nombre del Cliente:");
	TextField txtCliente = new TextField(20);
	Label lblTelefono = new Label("Teléfono:");
	TextField txtTelefono = new TextField(20);
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");
	Dialog feedback = new Dialog(ventana, "Mensaje", true);
	Label mensaje = new Label("");
	String usuario;

	public AltaCliente(String u)
	{
		usuario = u;
		try
		{

			BufferedImage icon = ImageIO.read(new File("img/logo2.jpg"));
			ventana.setIconImage(icon);
			feedback.setIconImage(icon);

		} catch (Exception e)
		{
			System.out.println("error en icono");
		}
		ventana.setLayout(new FlowLayout());
		ventana.setSize(220, 200);
		ventana.setResizable(false);

		ventana.add(lblcliente);
		ventana.add(txtCliente);
		ventana.add(lblTelefono);
		ventana.add(txtTelefono);
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
		if (actionEvent.getSource().equals(btnLimpiar))
		{
			txtCliente.setText("");
			txtTelefono.setText("");
			txtCliente.requestFocus();
		} else if (actionEvent.getSource().equals(btnAceptar))
		{
// Conectarse a la BD
			Modelo modelo = new Modelo();
			Connection connection = modelo.conectar();
// Hacer el Alta
			if (!modelo.altaCliente(connection, txtCliente.getText(), txtTelefono.getText(), usuario))
			{
// Mensaje de error 
				mensaje.setText("Error en Alta");
			} else
			{
// mensaje aceptación y borrado de los text
				mensaje.setText("Alta Correcta");
				txtCliente.setText("");
				txtTelefono.setText("");
				txtCliente.requestFocus();
			}
			feedback.setVisible(true);
// Desconectar
			modelo.desconectar(connection);
		}
	}
}