package es.studium.clases;

import java.awt.Button;
import java.awt.Choice;
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

public class AltaMaquina implements WindowListener, ActionListener
{
	Frame ventana = new Frame("Alta Máquina");
	Label lblMaquina = new Label("Maquina:");
	TextField txtMaquina = new TextField(20);
	Label lblPrecio = new Label("Precio:");
	TextField txtPrecio = new TextField(20);
	Label lblSede = new Label("Sede:   ");
	Choice choice = new Choice();
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");
	Dialog feedback = new Dialog(ventana, "Mensaje", true);
	Label mensaje = new Label("");
	String usuario;

	public AltaMaquina(String u)
	{
		usuario=u;
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
		ventana.setSize(220, 250);
		ventana.setResizable(false);
		Modelo modelo = new Modelo();
		Connection connection = modelo.conectar();
		modelo.rellenarChoiceSedes(connection, choice);
		modelo.desconectar(connection);
		ventana.add(lblMaquina);
		ventana.add(txtMaquina);
		ventana.add(lblPrecio);
		ventana.add(txtPrecio);
		ventana.add(lblSede);
		ventana.add(choice);
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
		String idSede = choice.getSelectedItem().split(" - ")[0];
		if (actionEvent.getSource().equals(btnLimpiar))
		{
			txtMaquina.setText("");
			txtPrecio.setText("");
			txtMaquina.requestFocus();
			choice.select(0);
		} else if (actionEvent.getSource().equals(btnAceptar))
		{
			// Conectarse a la BD
			Modelo modelo = new Modelo();
			Connection connection = modelo.conectar();
			if (!choice.getSelectedItem().equals("Seleccionar una Sede..."))
			{

// Hacer el Alta
				if (!modelo.altaMaquina(connection, txtMaquina.getText(), txtPrecio.getText(), idSede, usuario))
				{
					mensaje.setText("Error en Alta");
				} else
				{
					mensaje.setText("Alta Correcta");
					txtMaquina.setText("");
					txtPrecio.setText("");
					choice.select(0);
					txtMaquina.requestFocus();
					
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
