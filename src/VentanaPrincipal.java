import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class VentanaPrincipal extends JFrame {

    private Biblioteca biblioteca;

    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtISBN;
    private JTextField txtGenero;
    private JTextField txtAnio;

    private JTextField txtBuscarAutor;

    private JTable tablaLibros;

    private JButton btnAgregar;
    private JButton btnEliminar;


    public VentanaPrincipal() {

        biblioteca = new Biblioteca();

        crearVentana();
    }

    private void crearVentana() {

        setTitle("Biblioteca");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        txtTitulo = new JTextField(10);
        txtAutor = new JTextField(10);
        txtISBN = new JTextField(10);
        txtGenero = new JTextField(10);
        txtAnio = new JTextField(10);

        btnAgregar = new JButton("Agregar");
        btnEliminar = new JButton("Eliminar");


        JPanel formulario = new JPanel();

        formulario.add(new JLabel("Título:"));
        formulario.add(txtTitulo);

        formulario.add(new JLabel("Autor:"));
        formulario.add(txtAutor);

        formulario.add(new JLabel("ISBN:"));
        formulario.add(txtISBN);

        formulario.add(new JLabel("Género:"));
        formulario.add(txtGenero);

        formulario.add(new JLabel("Año:"));
        formulario.add(txtAnio);

        formulario.add(btnAgregar);


        JPanel busqueda = new JPanel();

        busqueda.add(new JLabel("Buscar autor:"));

        txtBuscarAutor = new JTextField(20);

        busqueda.add(txtBuscarAutor);


        tablaLibros = new JTable();

        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{
                        "Título",
                        "Autor",
                        "ISBN",
                        "Género",
                        "Año"
                }, 0);

        tablaLibros.setModel(modelo);
        JPanel panelEliminar = new JPanel();

        panelEliminar.add(btnEliminar);
        JPanel superior = new JPanel(new BorderLayout());

        superior.add(formulario, BorderLayout.NORTH);

        superior.add(busqueda, BorderLayout.SOUTH);


        add(superior, BorderLayout.NORTH);

        add(new JScrollPane(tablaLibros), BorderLayout.CENTER);

        add(panelEliminar, BorderLayout.SOUTH);
        btnAgregar.addActionListener(e -> agregarLibro());
        
    }

    private void agregarLibro() {

        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();
        String isbn = txtISBN.getText();
        String genero = txtGenero.getText();
        String anioTexto = txtAnio.getText();


        if (titulo.isEmpty() ||
                autor.isEmpty() ||
                isbn.isEmpty() ||
                genero.isEmpty() ||
                anioTexto.isEmpty()) {

            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios");

            return;
        }

        int anio;

        try {

            anio = Integer.parseInt(anioTexto);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(this, "El año debe ser un número"
            );

            return;
        }
        Libro libro = new Libro(titulo, autor, isbn, genero, anio);

        boolean agregado =
                biblioteca.agregarLibro(libro);

        if (!agregado) {
            JOptionPane.showMessageDialog(this, "Ese ISBN ya existe");

            return;
        }

        actualizarTabla();
    }

    private void actualizarTabla() {

        DefaultTableModel modelo =
                (DefaultTableModel) tablaLibros.getModel();

        modelo.setRowCount(0);


        for (Libro libro : biblioteca.obtenerTodos()) {
            modelo.addRow(new Object[]{

                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getIsbn(),
                    libro.getGenero(),
                    libro.getAnio()

            });
        }
    }
}
