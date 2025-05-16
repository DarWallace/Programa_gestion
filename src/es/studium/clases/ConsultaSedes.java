package es.studium.clases;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;

public class ConsultaSedes implements WindowListener, ActionListener
{
	Frame ventana = new Frame("Consulta de Sedes");
	TextArea txtArea = new TextArea(10,40);
	Modelo modelo = new Modelo();
	Button exportar= new Button("Exportar a PDF");
	Dialog feedback = new Dialog(ventana, "Datos exportados", true);
	Label mensaje = new Label("");
	public static final String DEST = "Sedes.pdf";
	
	public ConsultaSedes()
	{
		ventana.setLayout(new FlowLayout());

		Connection connection = modelo.conectar();
		txtArea.append(modelo.consultarSedes(connection));
		modelo.desconectar(connection);
		txtArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		ventana.add(txtArea);

		ventana.add(exportar);
		exportar.addActionListener(this);
		ventana.setLocationRelativeTo(null);
		ventana.addWindowListener(this);
		ventana.setSize(400, 250);
		ventana.setResizable(false);
		ventana.setVisible(true);
		
		feedback.setLayout(new FlowLayout());
		feedback.setSize(280, 100);
		feedback.setResizable(false);
		feedback.add(mensaje);
		feedback.setLocationRelativeTo(null);
		feedback.addWindowListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent actionEvent)
	{	
		if (actionEvent.getSource().equals(exportar))
		{
			// Conectarse a la BD
			Modelo modelo = new Modelo();
			Connection connection = modelo.conectar();
			System.out.println(txtArea.getText());
			modelo.ImprimirSedes(DEST, txtArea.getText());
			feedback.setVisible(true);
			// Desconectar
			modelo.desconectar(connection);
		}
	}
	@Override
	public void windowOpened(WindowEvent e)
	{	}
	@Override
	public void windowClosing(WindowEvent e)
	{
		if (e.getSource().equals(feedback))
		{
			feedback.setVisible(false);
		} else if (e.getSource().equals(ventana))
		{
			ventana.setVisible(false);
		}
	}
	@Override
	public void windowClosed(WindowEvent e)
	{	}
	@Override
	public void windowIconified(WindowEvent e)
	{	}
	@Override
	public void windowDeiconified(WindowEvent e)
	{	}
	@Override
	public void windowActivated(WindowEvent e)
	{	}
	@Override
	public void windowDeactivated(WindowEvent e)
	{	}

}
