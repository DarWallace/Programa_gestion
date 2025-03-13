package es.studium.clases;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;

public class ConsultaClientes implements WindowListener, ActionListener
{
	Frame ventana = new Frame("Consulta de Clientes");
	TextArea txtArea = new TextArea(10,40);
	Button exportar= new Button("Exportar a PDF");
	Modelo modelo = new Modelo();
	
	public ConsultaClientes()
	{
		ventana.setLayout(new FlowLayout());
		Connection connection = modelo.conectar();
		txtArea.append(modelo.consultarClientes(connection));
		modelo.desconectar(connection);
		ventana.add(txtArea);

		
		ventana.add(exportar);
		exportar.addActionListener(this);
		ventana.setLocationRelativeTo(null);
		ventana.addWindowListener(this);
		ventana.setSize(400, 250);
		ventana.setResizable(false);
		ventana.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{	}
	@Override
	public void windowOpened(WindowEvent e)
	{	}
	@Override
	public void windowClosing(WindowEvent e)
	{
		ventana.setVisible(false);
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

