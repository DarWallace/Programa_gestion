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

public class EditarMaquina implements WindowListener, ActionListener
{
	Frame ventana = new Frame("Editar Maquina");
	Label lblEleccion = new Label("Elegir una Maquina");
	Choice choiceMaquina = new Choice();
	Button bEditar = new Button("Editar");

	Dialog cambio = new Dialog(ventana, "Modificación", true);
	Label lblNombre = new Label("Maquina:");
	TextField txtNombreMaquina = new TextField(20);
	Label lblPrecio = new Label("Precio:");
	TextField txtPrecioMaquina = new TextField(20);
	Label lblSede = new Label("Sede de la Maquina:");
	Choice choiceSede = new Choice();
	Button bAceptar = new Button("Aceptar");

	Dialog feedback = new Dialog(ventana, "Mensaje", true);
	Label mensaje = new Label("cambio correcto");

	public EditarMaquina()
	{

		ventana.setLayout(new FlowLayout());
		ventana.setSize(240, 200);

		ventana.setResizable(false);
		Modelo modelo = new Modelo();
		Connection connection = modelo.conectar();
		modelo.rellenarChoiceMaquinas(connection, choiceMaquina);
		modelo.rellenarChoiceSedes(connection, choiceSede);
		modelo.desconectar(connection);
		ventana.add(choiceMaquina);

		bEditar.addActionListener(this);
		ventana.add(bEditar);
		ventana.setLocationRelativeTo(null);
		ventana.addWindowListener(this);

		cambio.setLayout(new FlowLayout());
		cambio.setSize(220, 250);
		cambio.setResizable(false);
		cambio.add(lblNombre);
		cambio.add(txtNombreMaquina);
		cambio.add(lblPrecio);
		cambio.add(txtPrecioMaquina);
		cambio.add(lblSede);
		cambio.add(choiceSede);
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
		String idMaquina = choiceMaquina.getSelectedItem().split(" - ")[0];
		if (actionEvent.getSource().equals(bEditar))
		{
			if (!choiceMaquina.getSelectedItem().equals("Seleccionar una Maquina..."))
			{

				Modelo modelo = new Modelo();
				Connection connection = modelo.conectar();
				modelo.mostrarDatosMaquinas(connection, idMaquina, txtNombreMaquina, txtPrecioMaquina, choiceSede);

				cambio.setVisible(true);

			} else
			{
				mensaje.setText("Debes elegir una Maquina");
				feedback.setVisible(true);
			}
		} else if (actionEvent.getSource().equals(bAceptar))
		{
			Modelo modelo = new Modelo();
			Connection connection = modelo.conectar();
			String idSedeFk = choiceSede.getSelectedItem().split(" - ")[0];
			if (!modelo.editarMaquina(connection, idMaquina, txtNombreMaquina.getText(), txtPrecioMaquina.getText(), idSedeFk))
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