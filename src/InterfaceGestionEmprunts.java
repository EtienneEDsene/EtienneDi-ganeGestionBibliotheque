import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Comparator;
import javax.swing.table.DefaultTableModel;


public class InterfaceGestionEmprunts extends JFrame implements ActionListener {
    // D�claration des boutons
    private JButton btnEmprunter;
    private JButton btnRetourner;
    private JButton btnRechercherEmprunt;
    private JButton btnListerEmprunts;
    private JButton btnRapportStatistique;
    private JButton btnRetour;

    // Listes pour stocker les emprunts et les livres emprunt�s par chaque utilisateur
    private List<Emprunt> listeEmprunts;

    public InterfaceGestionEmprunts() {
        setTitle("Gestion des Emprunts et Retours");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Cr�ation du panneau principal avec un layout personnalis�
        JPanel mainPanel = new JPanel() {
            // Surcharge de la m�thode paintComponent pour dessiner l'image de fond
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // POUR Charger l'image de fond
                ImageIcon backgroundImage = new ImageIcon("C:/Users/LENOVO/Desktop/JavaCrud/t�l�chargement.jpg");
                // POUR Dessiner l'image de fond
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(null); // Utilisation d'un layout null pour positionner manuellement les boutons

        // Cr�ation et positionnement des boutons
        btnEmprunter = new JButton("Emprunter un livre");
        btnEmprunter.setBounds(50, 50, 200, 30); // Position et taille du bouton
        mainPanel.add(btnEmprunter); // Ajout du bouton au panneau principal

        btnRetourner = new JButton("Retourner un livre");
        btnRetourner.setBounds(50, 100, 200, 30);
        mainPanel.add(btnRetourner);

        btnRechercherEmprunt = new JButton("Rechercher un emprunt");
        btnRechercherEmprunt.setBounds(50, 150, 200, 30);
        mainPanel.add(btnRechercherEmprunt);

        btnListerEmprunts = new JButton("Lister les emprunts");
        btnListerEmprunts.setBounds(50, 200, 200, 30);
        mainPanel.add(btnListerEmprunts);

        btnRapportStatistique = new JButton("Rapport Statistique");
        btnRapportStatistique.setBounds(50, 250, 200, 30);
        mainPanel.add(btnRapportStatistique);

        btnRetour = new JButton("Retour");
        btnRetour.setBounds(50, 300, 200, 30);
        mainPanel.add(btnRetour);

        // Ajout du panneau principal � la fen�tre
        add(mainPanel);

        // Ajout des �couteurs d'�v�nements aux boutons
        btnEmprunter.addActionListener(this);
        btnRetourner.addActionListener(this);
        btnRechercherEmprunt.addActionListener(this);
        btnListerEmprunts.addActionListener(this);
        btnRapportStatistique.addActionListener(this);
        btnRetour.addActionListener(this);

        // Rendre la fen�tre visible
        setVisible(true);

        // Initialisation de la liste d'emprunts
        listeEmprunts = chargerEmprunts();
        if (listeEmprunts == null) {
            listeEmprunts = new ArrayList<>();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnEmprunter) {
            gererEmprunt();
        } else if (e.getSource() == btnRetourner) {
            gererRetour();
        } else if (e.getSource() == btnRechercherEmprunt) {
            rechercherEmprunt();
        } else if (e.getSource() == btnListerEmprunts) {
            listerEmprunts();
        } else if (e.getSource() == btnRapportStatistique) {
            genererRapportStatistique();
        } else if (e.getSource() == btnRetour) {
            sauvegarderEmprunts(listeEmprunts); // Sauvegarder la liste des emprunts
            dispose();
        }
    }
    
   
    private void genererRapportStatistique() {
        Map<String, Integer> statistiquesLivres = new HashMap<>();

        // Calculer les statistiques
        for (Emprunt emprunt : listeEmprunts) {
            String titreLivre = emprunt.getTitreLivre();
            statistiquesLivres.put(titreLivre, statistiquesLivres.getOrDefault(titreLivre, 0) + 1);
        }

        // Trier les livres en fonction du nombre d'emprunts
        List<Map.Entry<String, Integer>> listeTriee = new ArrayList<>(statistiquesLivres.entrySet());
        listeTriee.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        // Afficher le rapport
        StringBuilder rapport = new StringBuilder();
        rapport.append("Rapport Statistique sur les Livres les Plus Emprunt�s :\n\n");
        int count = 1;
        for (Map.Entry<String, Integer> entry : listeTriee) {
            rapport.append(count).append(". ").append(entry.getKey()).append(" - ").append(entry.getValue()).append(" emprunts\n");
            count++;
        }

        // Afficher le rapport dans une bo�te de dialogue
        JOptionPane.showMessageDialog(null, rapport.toString(), "Rapport Statistique", JOptionPane.INFORMATION_MESSAGE);
    }

    

    private void gererEmprunt() {
	    String nomUtilisateur = JOptionPane.showInputDialog("Entrez votre nom :");
	    if (nomUtilisateur != null && !nomUtilisateur.isEmpty()) {
	        // V�rifier le nombre d'emprunts actuels de l'utilisateur
	        int nombreEmprunts = compterEmpruntsUtilisateur(nomUtilisateur);
	        if (nombreEmprunts < 2) {
	            String cotisationStr = JOptionPane.showInputDialog("Entrez le montant de la cotisation (2000) :");
	            // V�rification si la cotisation saisie est correcte
	            if (cotisationStr != null && !cotisationStr.isEmpty()) {
	                int cotisation = Integer.parseInt(cotisationStr);
	                if (cotisation == 2000) {
	                    String titreLivre = JOptionPane.showInputDialog("Entrez le titre du livre � emprunter :");
	                    if (titreLivre != null && !titreLivre.isEmpty()) {
	                        if (utilisateurExiste(nomUtilisateur)) {
	                            if (livreExiste(titreLivre)) {
	                                List<Livres> livres = chargerLivres();
	                                for (Livres livre : livres) {
	                                    if (livre.getTitre().equalsIgnoreCase(titreLivre)) {
	                                        if (livre.getNbExemplaires() > 0) {
	                                            // Il reste des exemplaires disponibles
	                                            String dateEmprunt = LocalDate.now().toString();
	                                            ajouterEmprunt(nomUtilisateur, titreLivre, dateEmprunt);
	                                            livre.setNbExemplaires(livre.getNbExemplaires() - 1); // D�cr�menter le nombre d'exemplaires
	                                            sauvegarderLivres(livres); // Mettre � jour la liste des livres
	                                            listeEmprunts.add(new Emprunt(nomUtilisateur, titreLivre, dateEmprunt)); // Ajouter l'emprunt � la liste des emprunts
	                                            JOptionPane.showMessageDialog(null, "Livre emprunt� avec succ�s !");
	                                            listerEmprunts(); // Appeler la m�thode pour lister les emprunts apr�s chaque ajout d'emprunt
	                                        } else {
	                                            // Aucun exemplaire disponible
	                                            JOptionPane.showMessageDialog(null, "D�sol�, ce livre n'est pas disponible pour le moment.");
	                                        }
	                                        return; // Sortir de la boucle une fois que le livre est trouv�
	                                    }
	                                }
	                            } else {
	                                JOptionPane.showMessageDialog(null, "Le livre sp�cifi� n'existe pas.");
	                            }
	                        } else {
	                            JOptionPane.showMessageDialog(null, "Utilisateur non trouv�.");
	                        }
	                    } else {
	                        JOptionPane.showMessageDialog(null, "Veuillez entrer un titre de livre valide.");
	                    }
	                } else {
	                    JOptionPane.showMessageDialog(null, "Solde insuffisant. Vous devez payer une cotisation de 2000.");
	                }
	            } else {
	                JOptionPane.showMessageDialog(null, "Veuillez entrer un montant de cotisation.");
	            }
	        } else {
	            JOptionPane.showMessageDialog(null, "Vous avez d�j� emprunt� 2 livres. Vous ne pouvez pas emprunter plus.");
	        }
	    } else {
	        JOptionPane.showMessageDialog(null, "Veuillez entrer un nom d'utilisateur valide.");
	    }
	}
    
    private void listerEmprunts() {
        // Cr�ez un tableau pour stocker les donn�es de la table
        Object[][] data = new Object[listeEmprunts.size()][3];
        int i = 0;
        for (Emprunt emprunt : listeEmprunts) {
            data[i][0] = emprunt.getNomUtilisateur();
            data[i][1] = emprunt.getTitreLivre();
            data[i][2] = emprunt.getDateEmprunt();
            i++;
        }

        // Cr�ez les titres des colonnes
        String[] columnNames = {"Nom Utilisateur", "Titre Livre", "Date Emprunt"};

        // Cr�ez le mod�le de table avec les donn�es et les titres des colonnes
        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        // Cr�ez une JTable avec le mod�le de table
        JTable table = new JTable(model);

        // Ajoutez la table � une JScrollPane pour permettre le d�filement si n�cessaire
        JScrollPane scrollPane = new JScrollPane(table);

        // Cr�ez une nouvelle fen�tre pour afficher la table
        JFrame frame = new JFrame("Liste des Emprunts");
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    

	private int compterEmpruntsUtilisateur(String nomUtilisateur) {
	    int nombreEmprunts = 0;
	    for (Emprunt emprunt : listeEmprunts) {
	        if (emprunt.getNomUtilisateur().equalsIgnoreCase(nomUtilisateur)) {
	            nombreEmprunts++;
	        }
	    }
	    return nombreEmprunts;
	}


	     private void gererRetour() {
	    String nomUtilisateur = JOptionPane.showInputDialog(null, "Entrez le nom de l'utilisateur :");
	    String titreLivre = JOptionPane.showInputDialog(null, "Entrez le titre du livre � retourner :");

	    // V�rification si l'utilisateur existe
	    if (utilisateurExiste(nomUtilisateur)) {
	        // V�rification si le livre existe
	        if (livreExiste(titreLivre)) {
	            // Simulation du retour (remplacez ceci par votre propre logique)
	            for (Emprunt emprunt : listeEmprunts) {
	                if (emprunt.getNomUtilisateur().equals(nomUtilisateur) && emprunt.getTitreLivre().equals(titreLivre)) {
	                    listeEmprunts.remove(emprunt); // Retirer l'emprunt de la liste
	                    JOptionPane.showMessageDialog(null, "Livre retourn� avec succ�s !");
	                    return;
	                }
	            }
	            JOptionPane.showMessageDialog(null, "Ce livre n'a pas �t� emprunt� par cet utilisateur.");
	        } else {
	            JOptionPane.showMessageDialog(null, "Le livre n'existe pas.");
	        }
	    } else {
	        JOptionPane.showMessageDialog(null, "L'utilisateur n'existe pas.");
	    }
	}

	    private void rechercherEmprunt() {
	        // M�thode inchang�e
	    }

	    private List<Livres> chargerLivres() {
	    List<Livres> livres = null;
	    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("files/livres.ser"))) {
	        livres = (List<Livres>) ois.readObject();
	    } catch (IOException | ClassNotFoundException e) {
	        e.printStackTrace();
	        // G�rer l'exception en cons�quence, par exemple en retournant une liste vide
	    }
	    return livres; // Retourne la liste de livres charg�e, ou null en cas d'erreur
	}

	private void sauvegarderLivres(List<Livres> livres) {
	        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("files/livres.ser"))) {
	            oos.writeObject(livres);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    private void ajouterEmprunt(String nomUtilisateur, String titreLivre, String dateEmprunt) {
	        // M�thode inchang�e
	    }

	    private boolean utilisateurExiste(String nomUtilisateur) {
	    List<Utilisateur> utilisateurs = Utilisateur.getUtilisateurs();
	    for (Utilisateur utilisateur : utilisateurs) {
	        if (utilisateur.getNom().equalsIgnoreCase(nomUtilisateur)) {
	            return true;
	        }
	    }
	    return false; // Retourne false si aucun utilisateur correspondant n'est trouv�
	}

	private boolean livreExiste(String titreLivre) {
	    List<Livres> livres = chargerLivres();
	    for (Livres livre : livres) {
	        if (livre.getTitre().equalsIgnoreCase(titreLivre)) {
	            return true;
	        }
	    }
	    return false; // Retourne false si aucun livre correspondant n'est trouv�
	}


	private List<Emprunt> chargerEmprunts() {
	    List<Emprunt> emprunts = null;
	    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("files/emprunts.ser"))) {
	        emprunts = (List<Emprunt>) ois.readObject();
	    } catch (IOException | ClassNotFoundException e) {
	        e.printStackTrace();
	        // G�rer l'exception en cons�quence, par exemple en retournant une liste vide
	    }
	    return emprunts; // Retourne la liste d'emprunts charg�e, ou null en cas d'erreur
	}

	private void sauvegarderEmprunts(List<Emprunt> emprunts) {
	    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("files/emprunts.ser"))) {
	        oos.writeObject(emprunts);
	    } catch (IOException e) {
	        e.printStackTrace();
	        // G�rer l'exception en cons�quence
	    }
	}
	
	public void creerBibliothequeAvecEmprunts() {
        // Chargez les emprunts
        List<Emprunt> listeEmprunts = chargerEmprunts();
        if (listeEmprunts == null) {
            listeEmprunts = new ArrayList<>();
        }
        
        // Cr�ez une instance de la classe Bibliotheque et passez-lui la liste des emprunts
        Bibliotheque bibliotheque = new Bibliotheque(listeEmprunts);
        // Appelez la m�thode pour afficher la biblioth�que
        bibliotheque.afficherBibliotheque();
    }


	    public static void main(String[] args) {
	        InterfaceGestionEmprunts interfaceGestionEmprunts = new InterfaceGestionEmprunts();
	    interfaceGestionEmprunts.creerBibliothequeAvecEmprunts();
	        new InterfaceGestionEmprunts();
	    }
}
