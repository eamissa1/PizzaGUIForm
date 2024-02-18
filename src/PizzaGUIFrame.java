import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
public class PizzaGUIFrame extends JFrame
{
    private  JComboBox<String> sizeComboBox;
    private JRadioButton thinCrust;
    private JRadioButton regularCrust;
    private JRadioButton deepDish;
    private JCheckBox[] toppings;
    private JTextArea orderSummary;
    private JButton orderButton;
    private JButton clearButton;
    private JButton quitButton;
    private ButtonGroup crustGroup = new ButtonGroup();

    public PizzaGUIFrame()
    {
        setTitle("Pizza Order System");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crust Panel
        JPanel crustPanel = new JPanel();
        crustPanel.setBorder(BorderFactory.createTitledBorder("Crust"));
        thinCrust = new JRadioButton("Thin");
        regularCrust = new JRadioButton("Regular");
        deepDish = new JRadioButton("Deep-dish");

        crustGroup.add(thinCrust);
        crustGroup.add(regularCrust);
        crustGroup.add(deepDish);
        crustPanel.add(thinCrust);
        crustPanel.add(regularCrust);
        crustPanel.add(deepDish);

        // Size Panel
        JPanel sizePanel = new JPanel();
        sizePanel.setBorder(BorderFactory.createTitledBorder("Size"));
        String[] sizes = {"Small", "Medium", "Large", "Super"};
        sizeComboBox = new JComboBox<>(sizes);
        sizePanel.add(sizeComboBox);

        // Toppings Panel
        JPanel toppingsPanel = new JPanel();
        toppingsPanel.setLayout(new BoxLayout(toppingsPanel, BoxLayout.PAGE_AXIS));
        toppingsPanel.setBorder(BorderFactory.createTitledBorder("Toppings"));
        String[] toppingsOptions = {"Pepperoni", "Mushrooms", "Onions", "Sausage", "Bacon", "Chicken"};
        toppings = new JCheckBox[toppingsOptions.length];
        for (int i = 0; i < toppingsOptions.length; i++)
        {
            toppings[i] = new JCheckBox(toppingsOptions[i]);
            toppingsPanel.add(toppings[i]);
        }

        // Order Summary Panel
        JPanel summaryPanel = new JPanel(new BorderLayout());
        orderSummary = new JTextArea(5, 15);
        orderSummary.setEditable(false);
        Font font = new Font("Serif", Font.PLAIN, 16);
        orderSummary.setFont(font);
        JScrollPane scrollPane = new JScrollPane(orderSummary);
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Order Summary"));
        summaryPanel.add(scrollPane, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        orderButton = new JButton("Order");
        clearButton = new JButton("Clear");
        quitButton = new JButton("Quit");
        buttonsPanel.add(orderButton);
        buttonsPanel.add(clearButton);
        buttonsPanel.add(quitButton);

        // Add action listeners
        orderButton.addActionListener(new OrderListener());
        clearButton.addActionListener(e -> clearForm());
        quitButton.addActionListener(e -> exitApplication());

        // Adding Panels to Frame
        JPanel topPanel = new JPanel(new GridLayout(1, 1));
        topPanel.add(crustPanel);
        topPanel.add(sizePanel);
        topPanel.add(toppingsPanel);

        add(topPanel, BorderLayout.NORTH);
        add(summaryPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }
    private String getSelectedCrust() {
        if (thinCrust.isSelected()) {
            return "Thin";
        } else if (regularCrust.isSelected()) {
            return "Regular";
        } else if (deepDish.isSelected()) {
            return "Deep-dish";
        } else {
            return "No crust selected";
        }
    }
    private class OrderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StringBuilder summary = new StringBuilder();
            DecimalFormat df = new DecimalFormat("$0.00");
            double basePrice = 0;
            double toppingsPrice = 0;
            String size = (String) sizeComboBox.getSelectedItem();
            switch (size) {
                case "Small":
                    basePrice = 8.00;
                    break;
                case "Medium":
                    basePrice = 12.00;
                    break;
                case "Large":
                    basePrice = 16.00;
                    break;
                case "Super":
                    basePrice = 20.00;
                    break;
            }
            summary.append("===========================\n");
            summary.append(getSelectedCrust()).append(" ").append(size).append("\t").append(df.format(basePrice)).append("\n");

            for (JCheckBox topping : toppings) {
                if (topping.isSelected()) {
                    toppingsPrice += 1.00;
                    summary.append(topping.getText()).append("\t\t").append(df.format(1.00)).append("\n");
                }
            }

            double subtotal = basePrice + toppingsPrice;
            double tax = subtotal * 0.07;
            double total = subtotal + tax;

            summary.append("\nSub-total:\t\t").append(df.format(subtotal)).append("\n");
            summary.append("Tax:\t\t").append(df.format(tax)).append("\n");
            summary.append("----------------------------------------\n");
            summary.append("Total:\t\t").append(df.format(total)).append("\n");
            summary.append("===========================\n");

            orderSummary.setText(summary.toString());
        }
    }

    private void clearForm() {
        sizeComboBox.setSelectedIndex(0);
        crustGroup.clearSelection();
        for (JCheckBox topping : toppings) {
            topping.setSelected(false);
        }
        orderSummary.setText("");
    }

    private void exitApplication() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

}
