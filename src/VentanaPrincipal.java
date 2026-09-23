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
    private JTextField txtCopias;

    private JTextField txtBuscarAutor;
    private JTable tablaLibros;
    private JButton btnAgregar;
    private JButton btnEliminar;


    public VentanaPrincipal() {

        biblioteca = new Biblioteca();

        crearVentana();
    }

    private void crearVentana() {

        setTitle("isstema de Gestion de Biblioteca");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel formulario = new JPanel(new GridLayout(3, 4, 8, 8));

        txtTitulo = new JTextField();
        txtAutor = new JTextField();
        txtISBN = new JTextField();
        txtGenero = new JTextField();
        txtAnio = new JTextField();
        txtCopias = new JTextField();


        formulario.setBorder(BorderFactory.createTitledBorder("Registrar nuevo libro"));

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

        formulario.add(new JLabel("Copias"));
        formulario.add(txtCopias);


        JPanel panelAgregar = new JPanel(new FlowLayout(FlowLayout.RIGHT));


        JPanel panelSuperior = new JPanel();

        panelSuperior.add(panelAgregar, BorderLayout.SOUTH);

        add(panelSuperior, BorderLayout.NORTH);


        JPanel panelBtnAgregar = new JPanel();
        btnAgregar = new JButton("Agregar libro");
        panelBtnAgregar.add(btnAgregar);

        tablaLibros = new JTable();

        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{
                        "Título",
                        "Autor",
                        "ISBN",
                        "Género",
                        "Año",
                        "Copias Disponbles"
                }, 0);


        tablaLibros.setModel(modelo);

        JPanel panelEliminar = new JPanel();

        txtBuscarAutor = new JTextField(20);

        JButton btnBuscar = new JButton("Buscar por autor");
        JButton btnMostrarTodos = new JButton("Mostrar todos");
        btnEliminar = new JButton("Eliminar Libro");

        panelEliminar.add(new JLabel("Autor"));
        panelEliminar.add(txtBuscarAutor);
        panelEliminar.add(btnBuscar);
        panelEliminar.add(btnMostrarTodos);
        panelEliminar.add(btnEliminar);


        JPanel superior = new JPanel(new BorderLayout());

        superior.add(panelBtnAgregar, BorderLayout.CENTER);
        superior.add(formulario, BorderLayout.NORTH);

        add(superior, BorderLayout.NORTH);

        add(new JScrollPane(tablaLibros), BorderLayout.CENTER);

        add(panelEliminar, BorderLayout.SOUTH);
        btnAgregar.addActionListener(e -> agregarLibro());
        btnMostrarTodos.addActionListener(e -> actualizarTabla());
        btnEliminar.addActionListener(e -> eliminarLibro());
        btnBuscar.addActionListener(
                e -> filtrarPorAutor()
        );
    }
    private void agregarLibro() {

        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();
        String isbn = txtISBN.getText();
        String genero = txtGenero.getText();
        String anioTexto = txtAnio.getText();
        String copiasTexto = txtCopias.getText();


        if (titulo.isEmpty() ||
                autor.isEmpty() ||
                isbn.isEmpty() ||
                genero.isEmpty() ||
                anioTexto.isEmpty() ||
                copiasTexto.isEmpty()) {

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

        int copias;

        try{
            copias = Integer.parseInt(copiasTexto);

        }catch(NumberFormatException e){

            JOptionPane.showMessageDialog(this, "Las copias deben ser un numero");
            return;
        }

        if (copias <= 0) {
            JOptionPane.showMessageDialog(this, "Debe existir al menos una copia");
            return;
        }


        Libro libro = new Libro(titulo, autor, isbn, genero, anio, copias);

        boolean agregado =
                biblioteca.agregarLibro(libro);

        if (!agregado) {
            JOptionPane.showMessageDialog(this, "Ese ISBN ya existe");

            return;
        }else{
            JOptionPane.showMessageDialog(this, "Libro agregado correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
        }

        actualizarTabla();
        limpiarCampos();
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
                    libro.getAnio(),
                    libro.getCopiasDisponibles()

            });
        }
    }
    private void eliminarLibro() {

        int fila = tablaLibros.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un libro");

            return;
        }

        String isbn = tablaLibros.getValueAt(fila, 2).toString();

        int confirmarEliminacion = JOptionPane.showConfirmDialog(
                this, "¿Estas seguro de eliminar el libro?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmarEliminacion == JOptionPane.YES_OPTION){
            biblioteca.eliminarPorISBN(isbn);
            actualizarTabla();

            JOptionPane.showMessageDialog(this, "El libro se elimino correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    private void filtrarPorAutor() {

        String autor = txtBuscarAutor.getText().trim();

        DefaultTableModel modelo = (DefaultTableModel) tablaLibros.getModel();

        modelo.setRowCount(0);

        if (autor.isEmpty()) {
            actualizarTabla();

            return;
        }


        ArrayList<Libro> resultados = biblioteca.buscarPorAutor(autor);

        for (Libro libro : resultados) {

            modelo.addRow(new Object[]{

                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getIsbn(),
                    libro.getGenero(),
                    libro.getAnio(),
                    libro.getCopiasDisponibles()

            });
        }
    }

    private void limpiarCampos() {

        txtTitulo.setText("");
        txtAutor.setText("");
        txtISBN.setText("");
        txtGenero.setText("");
        txtAnio.setText("");
        txtCopias.setText("");
    }
}
