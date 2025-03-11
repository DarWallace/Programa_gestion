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
import java.sql.Connection;


public class EditarCliente implements WindowListener, ActionListener
{
	Frame ventana = new Frame("Editar Cliente");
	Label lblEleccion =new Label("Elegir una Sede");
	Choice choice = new Choice();
	Button bEditar = new Button("Editar");
	
	Dialog cambio = new Dialog(ventana, "Mensaje", true);
	Label lblNombre = new Label("Cliente:");
	TextField txtNombreCliente = new TextField(20);
	Label lblTelefono = new Label("Teléfono:");
	TextField txtTelefonoCliente = new TextField(20);
	Button bAceptar = new Button("Aceptar");

	Dialog feedback = new Dialog(ventana, "Mensaje", true);
	Label mensaje = new Label("cambio correcto");


	public EditarCliente() {
		
		ventana.setLayout(new FlowLayout());
		ventana.setSize(240, 200);

		ventana.setResizable(false);
		Modelo modelo = new Modelo();
		Connection connection = modelo.conectar();
		modelo.rellenarChoiceClientes(connection, choice);
		modelo.desconectar(connection);
		ventana.add(choice);
		
		bEditar.addActionListener(this);
		ventana.add(bEditar);
		ventana.setLocationRelativeTo(null);
		ventana.addWindowListener(this);

		cambio.setLayout(new FlowLayout());
		cambio.setSize(240, 200);
		cambio.setResizable(false);
		cambio.add(lblNombre);
		cambio.add(txtNombreCliente);
		cambio.add(lblTelefono);
		cambio.add(txtTelefonoCliente);
		bAceptar.addActionListener(this);
		cambio.add(bAceptar);
		cambio.setLocationRelativeTo(null);
		cambio.addWindowListener(this);
		
		feedback.setLayout(new FlowLayout());
		feedback.setSize(500, 100);
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
		String idCliente = choice.getSelectedItem().split(" - ")[0];
		if(actionEvent.getSource().equals(bEditar)) 
		{if (!choice.getSelectedItem().equals("Seleccionar un Cliente..."))
			{
			
			Modelo modelo = new Modelo();
			Connection connection = modelo.conectar();
			modelo.mostrarDatosClientes(connection, idCliente, txtNombreCliente, txtTelefonoCliente);
			
			
			cambio.setVisible(true);
			
			}else {mensaje.setText("Debes elegir un Cliente");
			feedback.setVisible(true);}
		}
	else if (actionEvent.getSource().equals(bAceptar))
		{
		Modelo modelo = new Modelo();
		Connection connection = modelo.conectar();
		
		
		if (!modelo.editarCliente(connection, idCliente, txtNombreCliente.getText(), txtTelefonoCliente.getText()))
		{
//Mostrar feed back correcto
			mensaje.setText("Error en el cambio");
			
		}else {
			mensaje.setText("Cambio realizado");
			
			modelo.rellenarChoiceClientes(connection, choice);
			cambio.setVisible(false);
		}
		feedback.setVisible(true);
		modelo.desconectar(connection);

	}

	}
}
