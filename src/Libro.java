public class Libro {

        private String titulo;
        private String autor;
        private String isbn;
        private String genero;
        private int anio;

        public Libro(String titulo, String autor, String isbn, String genero, int anio) {
            this.titulo = titulo;
            this.autor = autor;
            this.isbn = isbn;
            this.genero = genero;
            this.anio = anio;
        }

        public String getTitulo() {
            return titulo;
        }

        public String getAutor() {
            return autor;
        }

        public String getIsbn() {
            return isbn;
        }

        public String getGenero() {
            return genero;
        }

        public int getAnio() {
            return anio;
        }
    }

