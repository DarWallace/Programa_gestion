package es.studium.clases;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class MenuPrincipal extends Frame implements WindowListener, ActionListener

{
	private static final long serialVersionUID = 1L;
	// Declarar el objeto Image
	Image imagenFondo;
	// Declarar el objeto Toolkit para manejo de imágenes
	Toolkit herramienta;

	MenuBar barraMenu = new MenuBar();
	Menu mnuSedes = new Menu("Sedes");
	Menu mnuClientes = new Menu("Clientes");
	Menu mnuMaquinas = new Menu("Máquinas");
	Menu mnuRegistros = new Menu("Registros");
	Menu mnuAyuda = new Menu("Ayuda");

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

	MenuItem mniAyuda = new MenuItem("?");

	int tipo;
	String usuario;

	public MenuPrincipal(int t, String u)

	{
		usuario = u;
		tipo = t;
		try
		{

			BufferedImage icon = ImageIO.read(new File("img/logo2.jpg"));
			this.setIconImage(icon);

		} catch (Exception e)
		{
			System.out.println("error en icono");
		}
		setTitle("Menú Principal");
		setLayout(new FlowLayout());
		addWindowListener(this);
		// Establecer el método de trabajo con imágenes
		herramienta = getToolkit();
		// Especificar la ruta de la imagen
		// Si ponemos ruta OJO con los \\
		imagenFondo = herramienta.getImage("img\\imprenta2.jpg");
		setMenuBar(barraMenu);
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
		mniAyuda.addActionListener(this);
		mnuAyuda.add(mniAyuda);

		barraMenu.add(mnuSedes);
		barraMenu.add(mnuClientes);
		barraMenu.add(mnuMaquinas);
		barraMenu.add(mnuRegistros);
		barraMenu.add(mnuAyuda);
		setSize(310, 227);

		setLocationRelativeTo(null);
		setVisible(true);
	}

//	public static void main(String[] args)
//	{
//		new MenuPrincipal(1, "Juan");
//	}

	public void paint(Graphics g)
	{
		// Dibujar la imagen
		g.drawImage(imagenFondo, 7, 50, this);
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
			new ConsultaSedes(usuario);
		} else if (actionEvent.getSource().equals(mniAltaSede))
		{
			new AltaSede(usuario);
		} else if (actionEvent.getSource().equals(mniBajaSede))
		{
			new BajaSede(usuario);
		} else if (actionEvent.getSource().equals(mniEditarSede))
		{
			new EditarSede(usuario);
		}

		else if (actionEvent.getSource().equals(mniConsultaClientes))
		{
			new ConsultaClientes(usuario);
		} else if (actionEvent.getSource().equals(mniAltaCliente))
		{
			new AltaCliente(usuario);
		} else if (actionEvent.getSource().equals(mniBajaCliente))
		{
			new BajaCliente(usuario);
		} else if (actionEvent.getSource().equals(mniEditarCliente))
		{
			new EditarCliente(usuario);
		}

		else if (actionEvent.getSource().equals(mniConsultaMaquinas))
		{
			new ConsultaMaquinas(usuario);
		} else if (actionEvent.getSource().equals(mniAltaMaquina))
		{
			new AltaMaquina(usuario);
		} else if (actionEvent.getSource().equals(mniBajaMaquina))
		{
			new BajaMaquina(usuario);
		} else if (actionEvent.getSource().equals(mniEditarMaquina))
		{
			new EditarMaquina(usuario);
		}

		else if (actionEvent.getSource().equals(mniConsultaRegistros))
		{
			new ConsultaRegistros(usuario);
		} else if (actionEvent.getSource().equals(mniAltaRegistro))
		{
			new AltaRegistro(usuario);
		} else if (actionEvent.getSource().equals(mniBajaRegistro))
		{
			new BajaRegistro(usuario);
		} else if (actionEvent.getSource().equals(mniEditarRegistro))
		{
			new EditarRegistro(usuario);
		} 
		
		else if (actionEvent.getSource().equals(mniAyuda))
		{
			Modelo.ayuda();
		}

	}

}
