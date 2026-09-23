import java.util.ArrayList;
import java.util.HashMap;

public class Biblioteca {

    private HashMap<String, Libro> librosPorISBN;
    private HashMap<String, ArrayList<Libro>> librosPorAutor;

    public Biblioteca() {
        librosPorISBN = new HashMap<>();
        librosPorAutor = new HashMap<>();
    }

    public boolean agregarLibro(Libro libro) {

        if (librosPorISBN.containsKey(libro.getIsbn())) {
            return false;
        }
        librosPorISBN.put(
                libro.getIsbn(), // esta es la clave
                libro);// este es el valor;

        String autor = libro.getAutor();

        if (!librosPorAutor.containsKey(autor)) {
            librosPorAutor.put(autor,
                    new ArrayList<>()
            );
        }
        librosPorAutor.get(autor).add(libro);
        return true;
    }

    public Libro buscarPorISBN(String isbn) {
        return librosPorISBN.get(isbn);
    }

    public ArrayList<Libro> buscarPorAutor(String autor) {
        return librosPorAutor.getOrDefault(
                autor,
                new ArrayList<>());
    }
    public ArrayList<Libro> obtenerTodos() {
        return new ArrayList<>(
                librosPorISBN.values());
    }
    public boolean eliminarPorISBN(String isbn) {

        Libro libro = librosPorISBN.get(isbn);

        if (libro == null) {
            return false;
        }

        librosPorISBN.remove(isbn);
        ArrayList<Libro> librosAutor =
                librosPorAutor.get(libro.getAutor());

        librosAutor.remove(libro);

        if (librosAutor.isEmpty()) {

            librosPorAutor.remove(
                    libro.getAutor()
            );
        }
        return true;
    }
}
