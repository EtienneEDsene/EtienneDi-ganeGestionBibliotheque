import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InterfaceGestionLivres extends JFrame implements ActionListener {
    // Déclaration des boutons pour la gestion des livres
    private JButton btnAjouterLivre;
    private JButton btnModifierLivre;
    private JButton btnSupprimerLivre;
    private JButton btnRechercherLivre;
    private JButton btnListerLivres;
    private JButton btnRetour;
    private DefaultTableModel tableModel;
    private JComboBox<String> comboBoxType;
    private JTable table;
    private JPanel mainPanel;


    public InterfaceGestionLivres() {
    	
    	 // Initialisation du mainPanel
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
    	//pour  Initialisez comboBoxType avec les options appropriées
        comboBoxType = new JComboBox<>(new String[]{"Roman", "Essai"});
        comboBoxType.setBounds(50, 20, 200, 30); // Définissez les dimensions et la position du comboBoxType
        mainPanel.add(comboBoxType); // Ajoutez comboBoxType au panneau principal
    	table = new JTable();
        setTitle("Gestion des Livres");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Création du panneau principal avec un layout personnalisé
        JPanel mainPanel = new JPanel() {
            // Surcharge de la méthode paintComponent pour dessiner l'image de fond
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // pour Charger l'image de fond
                ImageIcon backgroundImage = new ImageIcon("C:/Users/LENOVO/Desktop/JavaCrud/téléchargement.jpg");
                // pour Dessiner l'image de fond
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(null); // Utilisation d'un layout null pour positionner manuellement les boutons

        // Création et positionnement des boutons pour la gestion des livres
        btnAjouterLivre = new JButton("Ajouter un livre");
        btnAjouterLivre.setBounds(50, 50, 200, 30); // Position et taille du bouton
        mainPanel.add(btnAjouterLivre); // Ajout du bouton au panneau principal

        btnModifierLivre = new JButton("Modifier un livre");
        btnModifierLivre.setBounds(50, 100, 200, 30);
        mainPanel.add(btnModifierLivre);

        btnSupprimerLivre = new JButton("Supprimer un livre");
        btnSupprimerLivre.setBounds(50, 150, 200, 30);
        mainPanel.add(btnSupprimerLivre);

        btnRechercherLivre = new JButton("Rechercher un livre");
        btnRechercherLivre.setBounds(50, 200, 200, 30);
        mainPanel.add(btnRechercherLivre);

        btnListerLivres = new JButton("Lister les livres");
        btnListerLivres.setBounds(50, 250, 200, 30);
        mainPanel.add(btnListerLivres);

        btnRetour = new JButton("Retour");
        btnRetour.setBounds(50, 300, 200, 30);
        mainPanel.add(btnRetour);

        // Ajout du panneau principal à la fenêtre
        add(mainPanel);

        // Ajout des action listeners pour les boutons
        btnAjouterLivre.addActionListener(this);
        btnModifierLivre.addActionListener(this);
        btnSupprimerLivre.addActionListener(this);
        btnRechercherLivre.addActionListener(this);
        btnListerLivres.addActionListener(this);
        btnRetour.addActionListener(this);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAjouterLivre) {
            ajouterLivre();
        } else if (e.getSource() == btnModifierLivre) {
            modifierLivre();
        } else if (e.getSource() == btnSupprimerLivre) {
            supprimerLivre();
        } else if (e.getSource() == btnRechercherLivre) {
            rechercherLivre();
        } else if (e.getSource() == btnListerLivres) {
            listerLivres();
        } else if (e.getSource() == btnRetour) {
            // Nouvelle fenêtre de la bibliothèque ou quelque chose d'autre à faire pour retourner
            // new Bibliotheque();
            dispose(); // Fermer la fenêtre de gestion des livres
        }
    }

    
    
    
    private void modifierLivre() {
        String isbnRecherche = JOptionPane.showInputDialog("Entrez le numéro ISBN du livre à modifier :");
        if (isbnRecherche != null && !isbnRecherche.isEmpty()) {
            List<Livres> listeLivres = chargerLivres();
            Livres livreAModifier = null;

            for (Livres livre : listeLivres) {
                if (livre.getNumeroISBN().equalsIgnoreCase(isbnRecherche)) {
                    livreAModifier = livre;
                    break; // On a trouvé le livre, pas besoin de continuer la recherche
                }
            }

            if (livreAModifier != null) {
                // Afficher les informations actuelles du livre
                JOptionPane.showMessageDialog(null, livreAModifier.toString(), "Informations actuelles du livre", JOptionPane.PLAIN_MESSAGE);

                // Demander les nouvelles informations
                String nouveauTitre = JOptionPane.showInputDialog("Entrez le nouveau titre (laissez vide pour ne pas modifier) :");
                String nouveauAuteur = JOptionPane.showInputDialog("Entrez le nouveau auteur (laissez vide pour ne pas modifier) :");
                String nouvelleAnnee = JOptionPane.showInputDialog("Entrez la nouvelle année de publication (laissez vide pour ne pas modifier) :");
                String nouveauNbExemplaires = JOptionPane.showInputDialog("Entrez le nouveau nombre d'exemplaires (laissez vide pour ne pas modifier) :");

                // Mettre à jour les informations du livre
                if (nouveauTitre != null && !nouveauTitre.isEmpty()) {
                    livreAModifier.setTitre(nouveauTitre);
                }
                if (nouveauAuteur != null && !nouveauAuteur.isEmpty()) {
                    livreAModifier.setAuteur(nouveauAuteur);
                }
                if (nouvelleAnnee != null && !nouvelleAnnee.isEmpty()) {
                    try {
                        int annee = Integer.parseInt(nouvelleAnnee);
                        livreAModifier.setAnneePublication(annee);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Année invalide !");
                    }
                }
                if (nouveauNbExemplaires != null && !nouveauNbExemplaires.isEmpty()) {
                    try {
                        int nbExemplaires = Integer.parseInt(nouveauNbExemplaires);
                        livreAModifier.setNbExemplaires(nbExemplaires);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Nombre d'exemplaires invalide !");
                    }
                }

                // Enregistrer les modifications
                sauvegarderLivres(listeLivres);
                JOptionPane.showMessageDialog(null, "Livre modifié avec succès !");
                tableModel.fireTableDataChanged(); // Mettre à jour le modèle de tableau
            } else {
                JOptionPane.showMessageDialog(null, "Aucun livre trouvé avec cet ISBN.");
            }
        }
    }

    private void ajouterLivre() {
        // Obtenir le type de livre sélectionné à partir du menu déroulant
        String typeLivre = (String) comboBoxType.getSelectedItem(); 
        
        // Logique pour ajouter un livre en fonction du type sélectionné
        JOptionPane.showMessageDialog(null, "Vous avez sélectionné : " + typeLivre);
        String titre = JOptionPane.showInputDialog("Donner le titre du livre : ");
        
        // POUR Vérifier si la saisie pour le titre est null ou vide
        if (titre == null || titre.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Veuillez entrer un titre pour le livre.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return; // Sortir de la méthode sans ajouter le livre
        }
        
        String auteur = JOptionPane.showInputDialog("Donner l'auteur du livre : ");
        String anneePublicationInput = JOptionPane.showInputDialog("Donner l'année de publication du livre : ");
        String nbExemplairesInput = JOptionPane.showInputDialog("Donner le nombre d'exemplaires du livre à ajouter : ");

        //POUR Vérifier si les autres champs obligatoires sont vides
        if (auteur.isEmpty() || anneePublicationInput.isEmpty() || nbExemplairesInput.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return; // Sortir de la méthode sans ajouter le livre
        }

        // Conversion des entrées en types appropriés
        int anneePublication = Integer.parseInt(anneePublicationInput);
        int nbExemplaires = Integer.parseInt(nbExemplairesInput);

        // Génération automatique du numéro ISBN
        String numeroISBN = generateISBN();

        // Utilisez le constructeur de Livres qui prend en compte le type
        Livres nouveauLivre = new Livres(titre, auteur, anneePublication, nbExemplaires, typeLivre);

        ajouterLivre(nouveauLivre);
    }


    private void ajouterLivre(Livres livre) {
        List<Livres> listeLivres = chargerLivres();
        listeLivres.add(livre);
        sauvegarderLivres(listeLivres);
        JOptionPane.showMessageDialog(null, "Livre ajouté avec succès !");

        // Mettre à jour le modèle de tableau avec les données mises à jour
        Object[] rowData = {livre.getTitre(), livre.getAuteur(), livre.getAnneePublication(), livre.getNbExemplaires(), livre.getNumeroISBN()};
        tableModel.addRow(rowData);

        // Rafraîchir le tableau
        table.setModel(tableModel);
    }

    private void listerLivres() {
        List<Livres> listeLivres = chargerLivres();
        String[] columnNames = {"Titre", "Auteur", "Année de publication", "Nombre d'exemplaires", "ISBN", "Type"};
        Object[][] data = new Object[listeLivres.size()][6];

        for (int i = 0; i < listeLivres.size(); i++) {
            Livres livre = listeLivres.get(i);
            data[i][0] = livre.getTitre();
            data[i][1] = livre.getAuteur();
            data[i][2] = livre.getAnneePublication();
            data[i][3] = livre.getNbExemplaires();
            data[i][4] = livre.getNumeroISBN();
            data[i][5] = livre.getType();
        }

        tableModel = new DefaultTableModel(data, columnNames); // Initialisation de tableModel
        table = new JTable(tableModel); // Création de la JTable avec le modèle

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(580, 350));

        JOptionPane.showMessageDialog(null, scrollPane, "Liste des Livres", JOptionPane.PLAIN_MESSAGE);
    }

    private List<Livres> chargerLivres() {
        List<Livres> livres = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("flies/livres.ser"))) {
            livres = (List<Livres>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // En cas d'erreur de lecture du fichier ou s'il n'existe pas, on retourne une liste vide
        }
        return livres;
    }

    private void sauvegarderLivres(List<Livres> livres) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("files/livres.ser"))) {
            oos.writeObject(livres);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateISBN() {
        Random random = new Random();
        StringBuilder isbn = new StringBuilder();
        for (int i = 0; i < 13; i++) {
            isbn.append(random.nextInt(10));
        }
        return isbn.toString();
    }

    private void rechercherLivre() {
        String choixRecherche = JOptionPane.showInputDialog("Voulez-vous rechercher par auteur (A) ou par ISBN (I) ?");
        if (choixRecherche != null && !choixRecherche.isEmpty()) {
            if (choixRecherche.equalsIgnoreCase("A")) {
                String auteurRecherche = JOptionPane.showInputDialog("Entrez le nom de l'auteur :");
                if (auteurRecherche != null && !auteurRecherche.isEmpty()) {
                    rechercherParAuteur(auteurRecherche);
                }
            } else if (choixRecherche.equalsIgnoreCase("I")) {
                String isbnRecherche = JOptionPane.showInputDialog("Entrez le numéro ISBN :");
                if (isbnRecherche != null && !isbnRecherche.isEmpty()) {
                    rechercherParISBN(isbnRecherche);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Choix invalide !");
            }
        }
    }

    private void rechercherParAuteur(String auteurRecherche) {
        List<Livres> listeLivres = chargerLivres();
        DefaultTableModel resultModel = new DefaultTableModel(new String[]{"Titre", "Auteur", "Année de publication", "Nombre d'exemplaires", "ISBN"}, 0);

        for (Livres livre : listeLivres) {
            if (livre.getAuteur().equalsIgnoreCase(auteurRecherche)) {
                resultModel.addRow(new Object[]{livre.getTitre(), livre.getAuteur(), livre.getAnneePublication(), livre.getNbExemplaires(), livre.getNumeroISBN()});
            }
        }

        if (resultModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Aucun livre trouvé pour cet auteur.");
        } else {
            JTable resultTable = new JTable(resultModel);
            JScrollPane scrollPane = new JScrollPane(resultTable);
            JOptionPane.showMessageDialog(null, scrollPane, "Résultat de la recherche", JOptionPane.PLAIN_MESSAGE);
        }
    }

    private void rechercherParISBN(String isbnRecherche) {
        List<Livres> listeLivres = chargerLivres();
        DefaultTableModel resultModel = new DefaultTableModel(new String[]{"Titre", "Auteur", "Année de publication", "Nombre d'exemplaires", "ISBN"}, 0);

        for (Livres livre : listeLivres) {
            if (livre.getNumeroISBN().equalsIgnoreCase(isbnRecherche)) {
                resultModel.addRow(new Object[]{livre.getTitre(), livre.getAuteur(), livre.getAnneePublication(), livre.getNbExemplaires(), livre.getNumeroISBN()});
                break; // On a trouvé le livre, pas besoin de continuer la recherche
            }
        }

        if (resultModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Aucun livre trouvé avec cet ISBN.");
        } else {
            JTable resultTable = new JTable(resultModel);
            JScrollPane scrollPane = new JScrollPane(resultTable);
            JOptionPane.showMessageDialog(null, scrollPane, "Résultat de la recherche", JOptionPane.PLAIN_MESSAGE);
        }
    }
    
    private void supprimerLivre() {
        String choixRecherche = JOptionPane.showInputDialog("Voulez-vous supprimer un livre par auteur (A) ou par ISBN (I) ?");
        if (choixRecherche != null && !choixRecherche.isEmpty()) {
            if (choixRecherche.equalsIgnoreCase("A")) {
                String auteurRecherche = JOptionPane.showInputDialog("Entrez le nom de l'auteur :");
                if (auteurRecherche != null && !auteurRecherche.isEmpty()) {
                    supprimerParAuteur(auteurRecherche);
                }
            } else if (choixRecherche.equalsIgnoreCase("I")) {
                String isbnRecherche = JOptionPane.showInputDialog("Entrez le numéro ISBN :");
                if (isbnRecherche != null && !isbnRecherche.isEmpty()) {
                    supprimerParISBN(isbnRecherche);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Choix invalide !");
            }
        }
    }
    
    private void supprimerParAuteur(String auteurRecherche) {
        List<Livres> listeLivres = chargerLivres();
        List<Livres> livresASupprimer = new ArrayList<>();

        for (Livres livre : listeLivres) {
            if (livre.getAuteur().equalsIgnoreCase(auteurRecherche)) {
                livresASupprimer.add(livre);
            }
        }

        if (!livresASupprimer.isEmpty()) {
            listeLivres.removeAll(livresASupprimer);
            sauvegarderLivres(listeLivres);
            JOptionPane.showMessageDialog(null, "Livre(s) supprimé(s) avec succès !");
            rafraichirTableau();
        } else {
            JOptionPane.showMessageDialog(null, "Aucun livre trouvé pour cet auteur.");
        }
    }
    
    private void supprimerParISBN(String isbnRecherche) {
        List<Livres> listeLivres = chargerLivres();
        boolean livreTrouve = false;

        for (Livres livre : listeLivres) {
            if (livre.getNumeroISBN().equalsIgnoreCase(isbnRecherche)) {
                listeLivres.remove(livre);
                livreTrouve = true;
                break; // On a trouvé le livre, pas besoin de continuer la recherche
            }
        }

        if (livreTrouve) {
            sauvegarderLivres(listeLivres);
            JOptionPane.showMessageDialog(null, "Livre supprimé avec succès !");
            rafraichirTableau();
        } else {
            JOptionPane.showMessageDialog(null, "Aucun livre trouvé avec cet ISBN.");
        }
    }

    private void rafraichirTableau() {
        tableModel.setRowCount(0); // Effacer toutes les lignes du tableau
        List<Livres> listeLivres = chargerLivres();

        for (Livres livre : listeLivres) {
            Object[] rowData = {livre.getTitre(), livre.getAuteur(), livre.getAnneePublication(), livre.getNbExemplaires(), livre.getNumeroISBN()};
            tableModel.addRow(rowData);
        }
    }


    public static void main(String[] args) {
        new InterfaceGestionLivres();
    }
}
