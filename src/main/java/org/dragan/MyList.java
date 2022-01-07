package org.dragan;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.sql.SQLException;

public class MyList extends JFrame {
    private static volatile MyList instance;
    private MyDatabase mdb;
    private PersonneTableModel model;
    private JPanel mainPanel;
    private Graphics2D graph;

    {
        setupUI();
    }

    // Singleton
    private MyList() {
        super("Examen");

        try {
            mdb = MyDatabase.getInstance();

            mdb.remplirTableau(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        MyThread thread = new MyThread(this);
        thread.start();
    }

    public MyDatabase getDatabase() {
        return mdb;
    }

    public static MyList getInstance() {
        MyList result = instance;
        if (result != null) {
            return result;
        }
        synchronized (MyList.class) {
            if (instance == null) {
                instance = new MyList();
            }
            return instance;
        }
    }

    public PersonneTableModel getModel() {
        return model;
    }

    private void setupUI() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        JScrollPane tableScrollPane = new JScrollPane();
        tableScrollPane.setAutoscrolls(false);
        tableScrollPane.setOpaque(false);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(),
                        "Liste des utilisateurs",
                        TitledBorder.DEFAULT_JUSTIFICATION,
                        TitledBorder.DEFAULT_POSITION,
                        null,
                        null
                )
        );
        JTable personneTable = new JTable();
        tableScrollPane.setViewportView(personneTable);

        var chartPanel = new JPanel();
        chartPanel.setLayout(new BorderLayout());
        chartPanel.setPreferredSize(new Dimension(800, 300));
        chartPanel.setBackground(Color.BLACK);
        mainPanel.add(chartPanel, BorderLayout.SOUTH);

        model = new PersonneTableModel();
        personneTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        personneTable.setModel(model);
        personneTable.setAutoscrolls(true);

        this.setContentPane(this.getRootComponent());
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(new Dimension(800, 600));
        this.setResizable(false);
        this.setVisible(true);

        graph = (Graphics2D) chartPanel.getGraphics();
    }

    public JComponent getRootComponent() {
        return mainPanel;
    }

    public Graphics2D getGraph() {
        return graph;
    }
}
