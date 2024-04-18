import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Bibliotheque extends JFrame implements ActionListener {
    private JButton btnGestionLivres;
    private JButton btnGestionUtilisateurs;
    private JButton btnGestionEmprunts;
    private JButton btnQuitter;
    private JButton btnTotalLivres;
    private List<Emprunt> listeEmprunts;
    private JButton btnStatsEmprunts;

 
    

   
    
    public Bibliotheque() {
        setTitle("Bibliothèque");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        btnGestionLivres = new JButton("Gestion des Livres");
        btnGestionUtilisateurs = new JButton("Gestion des Utilisateurs");
        btnGestionEmprunts = new JButton("Gestion des Emprunts");
        btnQuitter = new JButton("Quitter");
        btnTotalLivres = new JButton("Nombre Total de Livres");
        btnStatsEmprunts = new JButton("Statistiques des Emprunts");

        btnGestionLivres.addActionListener(this);
        btnGestionUtilisateurs.addActionListener(this);
        btnGestionEmprunts.addActionListener(this);
        btnQuitter.addActionListener(this);
        btnTotalLivres.addActionListener(this);
        btnStatsEmprunts.addActionListener(this);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));
        panel.add(btnGestionLivres);
        panel.add(btnGestionUtilisateurs);
        panel.add(btnGestionEmprunts);
        panel.add(btnTotalLivres);
        panel.add(btnStatsEmprunts);
        panel.add(btnQuitter);

        add(panel);

        // Charger les emprunts depuis le fichier lors de la création de la bibliothèque
        chargerEmprunts();

        setVisible(true);
    }
    
    
    public Bibliotheque(List<Emprunt> listeEmprunts) {
        super("Bibliothèque");
        this.listeEmprunts = listeEmprunts;
       
    }
    
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnGestionLivres) {
            new InterfaceGestionLivres();
            dispose();
        } else if (e.getSource() == btnGestionUtilisateurs) {
            new InterfaceUtilisateur();
            dispose();
        } else if (e.getSource() == btnGestionEmprunts) { // Condition pour le nouveau bouton
            new InterfaceGestionEmprunts(); // Crée une nouvelle instance de l'interface de gestion des emprunts
            dispose(); // Ferme la fenêtre actuelle
        } else if (e.getSource() == btnQuitter) {
            System.exit(0);
        } else if (e.getSource() == btnStatsEmprunts) {
        afficherStatistiquesEmprunts();
        }else if (e.getSource() == btnTotalLivres) { // Si le bouton pour afficher le nombre total de livres est cliqué
            afficherNombreTotalLivres();
        } 
    }

    // Méthode pour charger les emprunts depuis un fichier
    @SuppressWarnings("unchecked")
    private void chargerEmprunts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("files/emprunts.ser"))) {
            listeEmprunts = (List<Emprunt>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            listeEmprunts = new ArrayList<>(); // Initialiser la liste si le chargement échoue
        }
    }

    
    public void ajouterLivre(Essaie livre) {
        // Ajouter le livre à la liste de livres de la bibliothèque
        // Par exemple :
        // this.listeLivres.add(livre);
    }

    // Reste du code inchangé...
      
    private void afficherStatistiquesEmprunts() {
        if (listeEmprunts == null || listeEmprunts.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Aucune donnée d'emprunt disponible.", "Statistiques des Emprunts", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Calcul des statistiques d'emprunts
        int totalEmprunts = listeEmprunts.size();

        // Affichage des statistiques
        StringBuilder statsMessage = new StringBuilder();
        statsMessage.append("Statistiques des Emprunts :\n");
        statsMessage.append("Nombre total d'emprunts : ").append(totalEmprunts).append("\n");
        JOptionPane.showMessageDialog(null, statsMessage.toString(), "Statistiques des Emprunts", JOptionPane.INFORMATION_MESSAGE);
    }

        // Fonction pour compter et afficher le nombre total de livres
    private void afficherNombreTotalLivres() {
        // Charger les livres et compter leur nombre total
        List<Livres> livres = chargerLivres();
        int totalLivres = livres.size();

        // Afficher un message avec le nombre total de livres
        JOptionPane.showMessageDialog(null, "Nombre total de livres : " + totalLivres, "Nombre Total de Livres", JOptionPane.INFORMATION_MESSAGE);
    }

    // Méthode pour charger les livres depuis un fichier
   // Méthode pour charger les livres depuis un fichier
@SuppressWarnings("unchecked") // Pour ignorer cet avertissement spécifique
private List<Livres> chargerLivres() {
    List<Livres> livres = new ArrayList<>();
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("files/livres.ser"))) {
        Object obj = ois.readObject();
        if (obj instanceof List) {
            livres = (List<Livres>) obj; // Utilisation du cast sûr avec <?>
        } else {
            System.err.println("Le fichier ne contient pas une liste de livres.");
        }
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
    }
    return livres;
}


//Méthode pour afficher la bibliothèque
public void afficherBibliotheque() {
    // Vérifier s'il y a des emprunts dans la liste
    if (listeEmprunts.isEmpty()) {
        System.out.println("La bibliothèque est vide.");
    } else {
        System.out.println("Liste des Emprunts :");
        System.out.println("---------------------");
        for (Emprunt emprunt : listeEmprunts) {
            System.out.println("Nom Utilisateur: " + emprunt.getNomUtilisateur() +
                               ", Titre Livre: " + emprunt.getTitreLivre() +
                               ", Date Emprunt: " + emprunt.getDateEmprunt());
        }
    }
}





    public static void main(String[] args) {
        new Bibliotheque();

    }
}

