package org.dragan;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;

public class MyForm extends JFrame {
    private JPanel mainPanel;
    private JTextField nomTextField;
    private JButton ajouterButton;
    private JButton listeButton;
    private ButtonGroup genreButtonGroup;

    {
        setupUI();
    }

    public MyForm() {
        super("Examen");

        // Lambda
        listeButton.addActionListener(e -> {
            var listeFrame = MyList.getInstance();
            if (listeFrame.isVisible()) {
                listeFrame.toFront();
                return;
            }
            listeFrame.setContentPane(listeFrame.getRootComponent());
            listeFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            listeFrame.setLocationRelativeTo(null);
            listeFrame.setSize(new Dimension(800, 600));
            listeFrame.setResizable(false);
            listeFrame.setVisible(true);
        });

        // External Class
        ajouterButton.addActionListener(new MyEvents(this));
    }

    public static void main(String[] args) {
        var mainFrame = new MyForm();
        mainFrame.setContentPane(new MyForm().getRootComponent());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setSize(400, 200);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
    }

    public JTextField getNomTextField() {
        return nomTextField;
    }

    public ButtonGroup getGenreButtonGroup() {
        return genreButtonGroup;
    }

    public JButton getAjouterButton() {
        return ajouterButton;
    }

    private void setupUI() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        // Reusable Grid Constraints
        GridBagConstraints gbc;

        // Creating the title of the form panel
        JLabel examenLabel = new JLabel();
        Font examenLabelFont = this.getFont(null, Font.BOLD, 14, examenLabel.getFont());
        if (examenLabelFont != null) examenLabel.setFont(examenLabelFont);
        examenLabel.setText("Examen");

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;

        // Adding the title to the form panel
        mainPanel.add(examenLabel, gbc);

        // Creating the form panel
        JPanel fromPanel = new JPanel();
        fromPanel.setLayout(new GridBagLayout());
        fromPanel.setFocusCycleRoot(false);
        fromPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Informations",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                null,
                null
        ));

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;

        // Adding the form panel with its constraints
        mainPanel.add(fromPanel, gbc);

        // Creating the text field
        nomTextField = new JTextField();
        nomTextField.setColumns(20);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHEAST;

        // Adding the text field with its constraints
        fromPanel.add(nomTextField, gbc);

        // Creating the "Nom" label
        JLabel nomLabel = new JLabel();
        nomLabel.setText("Nom");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        // Adding the "Nom" label with its constraints
        fromPanel.add(nomLabel, gbc);

        // Creating the "Genre" label
        final JLabel genreLabel = new JLabel();
        genreLabel.setText("Genre");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Adding the "Genre" label with its constraints
        fromPanel.add(genreLabel, gbc);

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;

        // Creating the radio buttons
        JRadioButton hommeRadioButton = new JRadioButton();
        hommeRadioButton.setSelected(true);
        hommeRadioButton.setText("Homme");
        hommeRadioButton.setActionCommand("Homme");
        radioPanel.add(hommeRadioButton);

        // Creating the radio Buttons
        JRadioButton femmeRadioButton = new JRadioButton();
        femmeRadioButton.setBorderPainted(false);
        femmeRadioButton.setText("Femme");
        femmeRadioButton.setActionCommand("Femme");
        radioPanel.add(femmeRadioButton);

        // Adding the radio panel with its constraints
        fromPanel.add(radioPanel, gbc);

        // Creating the actions panel
        JPanel actionsPanel = new JPanel();
        actionsPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;

        // Adding the action panel with its constraints
        mainPanel.add(actionsPanel, gbc);

        // Creating the button list
        JPanel actionsFlowPanel = new JPanel();
        actionsFlowPanel.setLayout(new FlowLayout(FlowLayout.TRAILING, 2, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;

        // Creating and adding the "Liste" button to the button list
        listeButton = new JButton();
        listeButton.setText("Liste");
        actionsFlowPanel.add(listeButton);

        // Creating and adding the "Ajouter" button to the button list
        ajouterButton = new JButton();
        ajouterButton.setText("Ajouter");
        actionsFlowPanel.add(ajouterButton);

        // Adding the button list with its constraints
        actionsPanel.add(actionsFlowPanel, gbc);

        // Adding the radio button to one group
        genreButtonGroup = new ButtonGroup();
        genreButtonGroup.add(hommeRadioButton);
        genreButtonGroup.add(femmeRadioButton);
    }

    // Get a font
    private Font getFont(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }

        Font font = new Font(
                resultName,
                style >= 0 ? style : currentFont.getStyle(),
                size >= 0 ? size : currentFont.getSize()
        );

        boolean isMac = System.getProperty("os.name", "")
                .toLowerCase(Locale.ENGLISH)
                .startsWith("mac");
        Font fontWithFallback = isMac ? new Font(
                font.getFamily(),
                font.getStyle(),
                font.getSize()
        ) : new StyleContext().getFont(
                font.getFamily(),
                font.getStyle(),
                font.getSize()
        );
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    public JComponent getRootComponent() {
        return mainPanel;
    }
}
