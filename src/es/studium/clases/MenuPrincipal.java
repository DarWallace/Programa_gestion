package es.studium.clases;

import java.awt.FlowLayout;
import java.awt.Frame;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MenuPrincipal implements WindowListener, ActionListener

{
	Frame ventana = new Frame("Menú Principal");
	MenuBar barraMenu = new MenuBar();
	Menu mnuSedes = new Menu("Sedes");
	Menu mnuClientes = new Menu("Clientes");
	Menu mnuMaquinas = new Menu("Máquinas");
	Menu mnuRegistros = new Menu("Registros");

	MenuItem mniConsultaSedes = new MenuItem("Consulta");
	MenuItem mniAltaSede = new MenuItem("Alta");
	MenuItem mniBajaSede = new MenuItem("Baja");
	MenuItem mniEditarSede = new MenuItem("Edición");

	MenuItem mniConsultaClientes = new MenuItem("Consulta");
	MenuItem mniAltaCliente = new MenuItem("Alta");
	MenuItem mniBajaCliente = new MenuItem("Baja");
	MenuItem mniEditarCliente = new MenuItem("Edición");

	MenuItem mniConsultaMaquinas = new MenuItem("Consulta");
	MenuItem mniAltaMaquina = new MenuItem("Alta");
	MenuItem mniBajaMaquina = new MenuItem("Baja");
	MenuItem mniEditarMaquina = new MenuItem("Edición");

	MenuItem mniConsultaRegistros = new MenuItem("Consulta");
	MenuItem mniAltaRegistro = new MenuItem("Alta");
	MenuItem mniBajaRegistro = new MenuItem("Baja");
	MenuItem mniEditarRegistro = new MenuItem("Edición");
	int tipo;

	public MenuPrincipal(int t)

	{
		tipo = t;
		ventana.setLayout(new FlowLayout());
		ventana.addWindowListener(this);
		ventana.setMenuBar(barraMenu);
		mniConsultaSedes.addActionListener(this);
		mniAltaSede.addActionListener(this);
		mniBajaSede.addActionListener(this);
		mniEditarSede.addActionListener(this);
		mnuSedes.add(mniAltaSede);
		if (tipo == 1)
		{
			mnuSedes.add(mniConsultaSedes);
			mnuSedes.add(mniBajaSede);
			mnuSedes.add(mniEditarSede);
		}

		mniConsultaClientes.addActionListener(this);
		mniAltaCliente.addActionListener(this);
		mniBajaCliente.addActionListener(this);
		mniEditarCliente.addActionListener(this);
		mnuClientes.add(mniAltaCliente);
		if (tipo == 1)
		{
			mnuClientes.add(mniConsultaClientes);
			mnuClientes.add(mniBajaCliente);
			mnuClientes.add(mniEditarCliente);
		}

		mniConsultaMaquinas.addActionListener(this);
		mniAltaMaquina.addActionListener(this);
		mniBajaMaquina.addActionListener(this);
		mniEditarMaquina.addActionListener(this);
		mnuMaquinas.add(mniAltaMaquina);
		if (tipo == 1)
		{
			mnuMaquinas.add(mniConsultaMaquinas);
			mnuMaquinas.add(mniBajaMaquina);
			mnuMaquinas.add(mniEditarMaquina);
		}

		mniConsultaRegistros.addActionListener(this);
		mniAltaRegistro.addActionListener(this);
		mniBajaRegistro.addActionListener(this);
		mniEditarRegistro.addActionListener(this);
		mnuRegistros.add(mniAltaRegistro);
		if (tipo == 1)
		{
			mnuRegistros.add(mniConsultaRegistros);
			mnuRegistros.add(mniBajaRegistro);
			mnuRegistros.add(mniEditarRegistro);
		}

		barraMenu.add(mnuSedes);
		barraMenu.add(mnuClientes);
		barraMenu.add(mnuMaquinas);
		barraMenu.add(mnuRegistros);
		ventana.setSize(350, 200);

		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
	}

	public static void main(String[] args)
	{
		new MenuPrincipal(1);
	}
	public void windowActivated(WindowEvent windowEvent)
	{
	}

	public void windowClosed(WindowEvent windowEvent)
	{
	}

	public void windowClosing(WindowEvent windowEvent)
	{
		System.exit(0);
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
		if (actionEvent.getSource().equals(mniConsultaSedes))
		{
			new ConsultaSedes();
		} else if (actionEvent.getSource().equals(mniAltaSede))
		{
			new AltaSede();
		} else if (actionEvent.getSource().equals(mniBajaSede))
		{
			new BajaSede();
		} else if (actionEvent.getSource().equals(mniEditarSede))
		{
			new EditarSede();
		}

		else if (actionEvent.getSource().equals(mniConsultaClientes))
		{
			new ConsultaClientes();
		} else if (actionEvent.getSource().equals(mniAltaCliente))
		{
			new AltaCliente();
		} else if (actionEvent.getSource().equals(mniBajaCliente))
		{
			new BajaCliente();
		} else if (actionEvent.getSource().equals(mniEditarCliente))
		{
			new EditarCliente();
		}
		/*
		 * else if (actionEvent.getSource().equals(mniConsultaMaquinas)) { new
		 * ConsultaMaquinas(); }else if (actionEvent.getSource().equals(mniAltaMaquina))
		 * { new AltaMaquina(); }else if
		 * (actionEvent.getSource().equals(mniBajaMaquina)) { new BajaMaquina(); }else
		 * if (actionEvent.getSource().equals(mniEditarMaquina)) { new EditarMaquina();
		 * }
		 * 
		 * else if (actionEvent.getSource().equals(mniConsultaRegistros)) { new
		 * ConsultaRegistros(); }else if
		 * (actionEvent.getSource().equals(mniAltaRegistro)) { new AltaRegistro(); }else
		 * if (actionEvent.getSource().equals(mniBajaRegistro)) { new BajaRegistro();
		 * }else if (actionEvent.getSource().equals(mniEditarRegistro)) { new
		 * EditarRegistro(); }
		 */

	}

}
