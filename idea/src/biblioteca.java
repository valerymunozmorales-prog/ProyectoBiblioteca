public class biblioteca {

    private ArrayList<Libro> libros;
    private HashMap<String, ArrayList<Libro>> indicePorAutor;
    private HashSet<String> codigosRegistrados;

    public Biblioteca() {

        libros = new ArrayList<>();
        indicePorAutor = new HashMap<>();
        codigosRegistrados = new HashSet<>();
    }

}
