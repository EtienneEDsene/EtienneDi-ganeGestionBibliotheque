public class Roman extends Livres {
    private String genre;

    public Roman(String titre, String auteur, int anneePublication, int numeroISBN, String genre) {
        super(titre, auteur, anneePublication, numeroISBN);
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    
 // D�finir la m�thode getISBN()
    public String getISBN() {
        return getISBN();
    }

    @Override
    public String toString() {
        return "Roman{" +
                "titre='" + getTitre() + '\'' +
                ", auteur='" + getAuteur() + '\'' +
                ", ann�e de publication=" + getAnneePublication() +
                ", numeroISBN='" + getISBN() + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}
