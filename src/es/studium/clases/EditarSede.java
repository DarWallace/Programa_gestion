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


public class EditarSede implements WindowListener, ActionListener
{
	Frame ventana = new Frame("Editar Sede");
	Label lblEleccion =new Label("Eleguir una Sede");
	Choice choice = new Choice();
	Button bAceptar = new Button("Aceptar");
	
	Dialog cambio = new Dialog(ventana, "Mensaje", true);
	Label lblNombre = new Label("Sede:");
	TextField txtNombreSede = new TextField(20);
	Label lblLocalidad = new Label("Localidad:");
	TextField txtLocalidadSede = new TextField(20);
	Button bAceptar2 = new Button("Aceptar");

	Dialog feedback = new Dialog(ventana, "Mensaje", true);
	Label mensaje = new Label("cambio correcto");


	public EditarSede() {
		
		ventana.setLayout(new FlowLayout());
		ventana.setSize(240, 200);

		ventana.setResizable(false);
		Modelo modelo = new Modelo();
		Connection connection = modelo.conectar();
		modelo.rellenarChoiceSedes(connection, choice);
		modelo.desconectar(connection);
		ventana.add(choice);
		
		bAceptar.addActionListener(this);
		ventana.add(bAceptar);
		ventana.setLocationRelativeTo(null);
		ventana.addWindowListener(this);

		cambio.setLayout(new FlowLayout());
		cambio.setSize(240, 200);
		cambio.setResizable(false);
		cambio.add(lblNombre);
		cambio.add(txtNombreSede);
		cambio.add(lblLocalidad);
		cambio.add(txtLocalidadSede);
		bAceptar2.addActionListener(this);
		cambio.add(bAceptar2);
		cambio.setLocationRelativeTo(null);
		cambio.addWindowListener(this);
		
		feedback.setLayout(new FlowLayout());
		feedback.setSize(900, 100);
		feedback.setResizable(false);
		feedback.add(mensaje);
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
		String idSede = choice.getSelectedItem().split(" - ")[0];
		if(actionEvent.getSource().equals(bAceptar)) 
		{if (!choice.getSelectedItem().equals("Seleccionar un Sede..."))
			{
			
			Modelo modelo = new Modelo();
			Connection connection = modelo.conectar();
			modelo.mostrarDatosSedes(connection, idSede, txtNombreSede, txtLocalidadSede);
			
			
			cambio.setVisible(true);
			
			}else {mensaje.setText("Debes elegir un departamento");
			feedback.setVisible(true);}
		}
	else if (actionEvent.getSource().equals(bAceptar2))
		{
		Modelo modelo = new Modelo();
		Connection connection = modelo.conectar();
		
		
		if (!modelo.editarSede(connection, idSede, txtNombreSede.getText(), txtLocalidadSede.getText()))
		{
//Mostrar feed back correcto
			mensaje.setText("Error en el cambio");
			
		}else {
			mensaje.setText("Cambio realizado");
			
			modelo.rellenarChoiceSedes(connection, choice);
			cambio.setVisible(false);
		}
		feedback.setVisible(true);
		modelo.desconectar(connection);

	}

	}
}

