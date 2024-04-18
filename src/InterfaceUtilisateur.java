
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class InterfaceUtilisateur extends JFrame implements ActionListener {
    private JButton btnAjouterUtilisateur;
    private JButton btnSupprimerUtilisateur;
    private JButton btnRechercherUtilisateur;
    private JButton btnRetourMenuPrincipal;
    private JButton btnListerUtilisateurs;
    private JButton btnRapportUtilisateursActifs;

    private Utilisateur utilisateur;

    public InterfaceUtilisateur() {
        setTitle("Menu de gestion des utilisateurs");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Initialisation des boutons
        btnAjouterUtilisateur = new JButton("Ajouter un utilisateur");
        btnSupprimerUtilisateur = new JButton("Supprimer un utilisateur");
        btnRechercherUtilisateur = new JButton("Rechercher un utilisateur");
        btnRetourMenuPrincipal = new JButton("Retour au menu principal");
        btnListerUtilisateurs = new JButton("Lister les utilisateurs");
        btnRapportUtilisateursActifs = new JButton("Rapport Utilisateurs Actifs");

        // D�finir les positions des boutons
        btnAjouterUtilisateur.setBounds(50, 50, 200, 30);
        btnSupprimerUtilisateur.setBounds(50, 100, 200, 30);
        btnRechercherUtilisateur.setBounds(50, 150, 200, 30);
        btnListerUtilisateurs.setBounds(50, 200, 200, 30);
        btnRapportUtilisateursActifs.setBounds(50, 250, 200, 30);
        btnRetourMenuPrincipal.setBounds(50, 300, 200, 30);

        // Ajout des �couteurs d'�v�nements aux boutons
        btnAjouterUtilisateur.addActionListener(this);
        btnSupprimerUtilisateur.addActionListener(this);
        btnRechercherUtilisateur.addActionListener(this);
        btnListerUtilisateurs.addActionListener(this);
        btnRetourMenuPrincipal.addActionListener(this);
        btnRapportUtilisateursActifs.addActionListener(this);

        // Ajout des boutons � la fen�tre
        add(btnAjouterUtilisateur);
        add(btnSupprimerUtilisateur);
        add(btnRechercherUtilisateur);
        add(btnListerUtilisateurs);
        add(btnRapportUtilisateursActifs);
        add(btnRetourMenuPrincipal);

        // Chargement de l'image de fond
        try {
            BufferedImage backgroundImage = ImageIO.read(new File("C:/Users/LENOVO/Desktop/JavaCrud/t�l�chargement.jpg")); // Mettez le chemin de votre image
            JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH)));
            backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
            add(backgroundLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Assurer que les composants sont superpos�s correctement
        getContentPane().setLayout(null);

        setVisible(true);

        utilisateur = new Utilisateur();
    }


    // M�thode pour d�finir le fond d'�cran et ajouter les boutons
    private void setCustomBackground() {
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Charger l'image de fond
                ImageIcon backgroundImage = new ImageIcon("C:/Users/LENOVO/Desktop/JavaCrud/t�l�chargement.jpg"); // Mettez le chemin de votre image
                // Dessiner l'image de fond
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(new GridLayout(6, 1)); // Utilisez un layout de type GridLayout avec 6 lignes et 1 colonne

        //  boutons au panneau principal
        mainPanel.add(btnAjouterUtilisateur);
        mainPanel.add(btnSupprimerUtilisateur);
        mainPanel.add(btnRechercherUtilisateur);
        mainPanel.add(btnListerUtilisateurs);
        mainPanel.add(btnRapportUtilisateursActifs);
        mainPanel.add(btnRetourMenuPrincipal);

        // D�finir le contenu de la fen�tre comme le panneau principal
        setContentPane(mainPanel);
    }

    // M�thode actionPerformed pour g�rer les �v�nements des boutons
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAjouterUtilisateur) {
            String nom = JOptionPane.showInputDialog("Donner le nom : ");
            String prenom = JOptionPane.showInputDialog("Donner le pr�nom : ");
            String dateNaissance = "";
            boolean dateValide = false;
            while (!dateValide) {
                dateNaissance = JOptionPane.showInputDialog("Donner la date de naissance (format JJ/MM/AAAA) : ");
                if (dateNaissance.matches("\\d{2}/\\d{2}/\\d{4}")) {
                    dateValide = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Le format de la date de naissance doit �tre JJ/MM/AAAA.");
                }
            }
            String tel = "";
            boolean telephoneValide = false;
            while (!telephoneValide) {
                tel = JOptionPane.showInputDialog("Donner votre num�ro de t�l�phone (format 772145963) : ");
                if (tel.matches("7[0-9]{8}")) {
                    telephoneValide = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Le num�ro de t�l�phone doit commencer par 7 et �tre suivi de 8 chiffres.");
                }
            }
            // Supprimez la demande pour le nombre d'ouvrages emprunt�s
            Utilisateur nouvelUtilisateur = new Utilisateur(nom, prenom, dateNaissance, tel, 0); // Utilisateur cr�� avec 0 ouvrages emprunt�s
            Utilisateur.ajouterUtilisateur(nouvelUtilisateur);
        } else if (e.getSource() == btnSupprimerUtilisateur) {
            String numeroIdentification = JOptionPane.showInputDialog("Entrez le num�ro d'identification de l'utilisateur � supprimer : ");
            if (numeroIdentification != null && !numeroIdentification.isEmpty()) {
                Utilisateur.supprimerUtilisateur(numeroIdentification);
            } else {
                JOptionPane.showMessageDialog(null, "Veuillez entrer un num�ro d'identification valide.");
            }
        } else if (e.getSource() == btnRechercherUtilisateur) {
            String numeroIdentification = JOptionPane.showInputDialog("Entrez le num�ro d'identification de l'utilisateur � rechercher : ");
            if (numeroIdentification != null && !numeroIdentification.isEmpty()) {
                rechercherUtilisateur(numeroIdentification);
            } else {
                JOptionPane.showMessageDialog(null, "Veuillez entrer un num�ro d'identification valide.");
            }
        } else if (e.getSource() == btnListerUtilisateurs) {
            listerUtilisateurs();
        } else if (e.getSource() == btnRapportUtilisateursActifs) {
            afficherRapportUtilisateursActifs();
        } else if (e.getSource() == btnRetourMenuPrincipal) {
            dispose();
            new Bibliotheque();
        }
    }
    
    private void afficherRapportUtilisateursActifs() {
        List<Utilisateur> utilisateurs = Utilisateur.getUtilisateurs();

        // Pour trier les utilisateurs par une autre propri�t� ici

        StringBuilder rapport = new StringBuilder("Utilisateurs les plus actifs :\n");
        for (int i = 0; i < Math.min(10, utilisateurs.size()); i++) {
            Utilisateur utilisateur = utilisateurs.get(i);
            rapport.append("Nom: ").append(utilisateur.getNom()).append(", ");
            rapport.append("Pr�nom: ").append(utilisateur.getPrenom()).append(", ");
            
            rapport.append("\n");
        }

        JOptionPane.showMessageDialog(this, rapport.toString(), "Rapport Utilisateurs Actifs", JOptionPane.INFORMATION_MESSAGE);
    }

    private void rechercherUtilisateur(String numeroIdentification) {
        Utilisateur utilisateurTrouve = null;
        for (Utilisateur user : Utilisateur.getUtilisateurs()) {
            if (user.getNumeroIdentification().equals(numeroIdentification)) {
                utilisateurTrouve = user;
                break;
            }
        }
        if (utilisateurTrouve != null) {
            JOptionPane.showMessageDialog(null, utilisateurTrouve.toString(), "Informations de l'utilisateur", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Utilisateur introuvable !");
        }
    }

    private void listerUtilisateurs() {
    List<Utilisateur> utilisateurs = Utilisateur.getUtilisateurs();
    String[][] data = new String[utilisateurs.size()][5]; // R�duisez la taille du tableau � 5 colonnes

    for (int i = 0; i < utilisateurs.size(); i++) {
        Utilisateur utilisateur = utilisateurs.get(i);
        data[i][0] = utilisateur.getNom();
        data[i][1] = utilisateur.getPrenom();
        data[i][2] = utilisateur.getDateNaissance();
        data[i][3] = utilisateur.getTel();
        data[i][4] = utilisateur.getNumeroIdentification();
    }

    String[] columnNames = {"Nom", "Pr�nom", "Date de naissance", "T�l�phone", "Num�ro d'identification"}; // Supprimez le nom de la colonne "Nombre d'ouvrages emprunt�s"

    JTable table = new JTable(data, columnNames);
    JScrollPane scrollPane = new JScrollPane(table);
    JPanel panel = new JPanel();
    panel.add(scrollPane);
    JFrame frame = new JFrame("Liste des utilisateurs");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.add(panel);
    frame.setSize(800, 400);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
}



    // M�thode principale pour tester l'interface
    public static void main(String[] args) {
        // Cr�ation de l'interface utilisateur
        SwingUtilities.invokeLater(() -> new InterfaceUtilisateur());
    }
}
