package gui;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import agents.BookBuyerAgent;

public class BookBuyerGui extends JFrame {

    private JTextField titleField;
    private JButton btnOK, btnCancel;

    public BookBuyerGui(BookBuyerAgent buyer) {
        super(buyer.getLocalName());


        // Creacion del panel de formulario
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.add(new JLabel("Book title:"));
        titleField = new JTextField(15);
        panel.add(titleField);
        getContentPane().add(panel, BorderLayout.CENTER);

        // Reutilización del objeto panel para modelar el panel de botones
        panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        btnOK = new JButton("Buscar");
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    String title = titleField.getText().trim();

                    buyer.updateBookTitle(title);
                    titleField.setText("");
                } catch(Exception e) {
                    JOptionPane.showMessageDialog(BookBuyerGui.this, 
                            "Invalid values",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(btnOK);

        btnCancel = new JButton("Cancelar");
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                buyer.doDelete();
            }
        });
        panel.add(btnCancel);

        getContentPane().add(panel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                buyer.doDelete();
            }
        });
        setResizable(false);
    }

    public void showNewSeller(String bookTitle,
                                 String sellerName,
                                 int price) {
        JOptionPane.showMessageDialog(this, 
                String.format("Se compró el libro \"%s\" del vendedor " +
                              "\"%s\" por el precio de $%d",
                              bookTitle,
                              sellerName,
                              price),
                "Compra realizada!",
                JOptionPane.INFORMATION_MESSAGE);
    }

	public void showGui() {
	  pack();
	  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	  int centerX = (int)screenSize.getWidth() / 2;
	  int centerY = (int)screenSize.getHeight() / 2;
	  
	  setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
	  super.setVisible(true);
	}
}
