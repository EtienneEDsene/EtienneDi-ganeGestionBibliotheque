import javax.swing.JOptionPane;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;


public class Essaie extends Livres implements Ouvrages, Serializable {
    private String sujet;

    public Essaie() {}

    public Essaie(String titre, String auteur, int anneePublication, int nbExemplaires, String sujet) {
        super(titre, auteur, anneePublication, nbExemplaires);
        this.sujet = sujet;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    @Override
    public void supprimer() {
        String sujetRecherche = JOptionPane.showInputDialog("Entrez le sujet de l'essai à supprimer :");
        supprimerParSujet(sujetRecherche);
    }

    private void supprimerParSujet(String sujetRecherche) {
        List<Livres> listeLivres = chargerLivres();
        List<Livres> essaisASupprimer = new ArrayList<>();

        for (Livres livre : listeLivres) {
            if (livre instanceof Essaie) {
                Essaie essai = (Essaie) livre;
                if (essai.getSujet().equalsIgnoreCase(sujetRecherche)) {
                    essaisASupprimer.add(essai);
                }
            }
        }

        if (!essaisASupprimer.isEmpty()) {
            listeLivres.removeAll(essaisASupprimer);
            sauvegarderLivres(listeLivres);
            JOptionPane.showMessageDialog(null, "Essai(s) supprimé(s) avec succès !");
            rafraichirTableau();
        } else {
            JOptionPane.showMessageDialog(null, "Aucun essai trouvé pour ce sujet.");
        }
    }

    @Override
    public void modifier() {
        String sujetRecherche = JOptionPane.showInputDialog("Entrez le sujet de l'essai à modifier :");
        modifierParSujet(sujetRecherche);
    }

    private void modifierParSujet(String sujetRecherche) {
        List<Livres> listeLivres = chargerLivres();
        Essaie essaiAModifier = null;

        for (Livres livre : listeLivres) {
            if (livre instanceof Essaie) {
                Essaie essai = (Essaie) livre;
                if (essai.getSujet().equalsIgnoreCase(sujetRecherche)) {
                    essaiAModifier = essai;
                    break;
                }
            }
        }

        if (essaiAModifier != null) {
            String nouveauTitre = JOptionPane.showInputDialog("Entrez le nouveau titre de l'essai :");
            String nouvelAuteur = JOptionPane.showInputDialog("Entrez le nouvel auteur de l'essai :");
            int nouvelleAnnee = Integer.parseInt(JOptionPane.showInputDialog("Entrez la nouvelle année de publication de l'essai :"));
            int nouveauNbExemplaires = Integer.parseInt(JOptionPane.showInputDialog("Entrez le nouveau nombre d'exemplaires de l'essai :"));
            String nouveauSujet = JOptionPane.showInputDialog("Entrez le nouveau sujet de l'essai :");

            essaiAModifier.setTitre(nouveauTitre);
            essaiAModifier.setAuteur(nouvelAuteur);
            essaiAModifier.setAnneePublication(nouvelleAnnee);
            essaiAModifier.setNbExemplaires(nouveauNbExemplaires);
            essaiAModifier.setSujet(nouveauSujet);

            sauvegarderLivres(listeLivres);
            JOptionPane.showMessageDialog(null, "Essai modifié avec succès !");
            rafraichirTableau();
        } else {
            JOptionPane.showMessageDialog(null, "Aucun essai trouvé pour ce sujet.");
        }
    }
    
    // Méthode pour charger la liste des livres
    private List<Livres> chargerLivres() {
        List<Livres> livres = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("livres.ser"))) {
            livres = (List<Livres>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // En cas d'erreur de lecture du fichier ou s'il n'existe pas, on retourne une liste vide
        }
        return livres;
    }

    // Méthode pour sauvegarder la liste des livres
    private void sauvegarderLivres(List<Livres> livres) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("livres.ser"))) {
            oos.writeObject(livres);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour rafraîchir le tableau
    private void rafraichirTableau() {
        // Code pour rafraîchir le tableau
    }
}
