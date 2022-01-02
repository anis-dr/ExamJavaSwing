package org.dragan;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MyEvents implements ActionListener {
    private final MyForm mf;

    public MyEvents(MyForm mf) {
        this.mf = mf;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mf.getAjouterButton()) {
            var nom = mf.getNomTextField().getText();
            var genre = mf.getGenreButtonGroup().getSelection().getActionCommand();
            var personne = new Personne(nom, genre);
            try {
                var change = MyDatabase.getInstance().ajouter(personne);
                System.out.println("Ajout de " + personne + " dans la base de données réussi");
                System.out.println("Nombre de changements : " + change);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(
                        mf,
                        ex.getMessage(),
                        "Erreur lors de l'ajout de la personne ",
                        JOptionPane.ERROR_MESSAGE
                );

                ex.printStackTrace();
            }
        }
    }
}
