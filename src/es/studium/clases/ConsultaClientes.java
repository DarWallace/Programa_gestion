package es.studium.clases;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;

public class ConsultaClientes implements WindowListener, ActionListener
{
	Frame ventana = new Frame("Consulta de Clientes");
	TextArea txtArea = new TextArea();
	Modelo modelo = new Modelo();
	Connection connection = null;
	public ConsultaClientes()
	{
		ventana.setLayout(new GridBagLayout());
// Añadircomponentes
		GridBagConstraints constraints = new GridBagConstraints();
// El áreadetextoempiezaenlacolumnacero.
		constraints.gridx = 0;
// El áreadetextoempiezaenlafilacero
		constraints.gridy = 0;
// El áreadetextoocupadoscolumnas.
		constraints.gridwidth = 2;
// El áreadetextoocupa 2 filas.
		constraints.gridheight = 2;
		constraints.fill = GridBagConstraints.BOTH;
// Lafila 0 debeestirarse, leponemosun 1.0
		constraints.weighty = 1.0;
		connection = modelo.conectar();
		txtArea.append(modelo.consultarClientes(connection));
		modelo.desconectar(connection);
		ventana.add(txtArea, constraints);
//Restauramosal valor pordefecto, para no afectar a los
//siguientescomponentes.
		constraints.weighty = 0.0;
		ventana.setLocationRelativeTo(null);
		ventana.addWindowListener(this);
		ventana.setSize(550, 250);
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

