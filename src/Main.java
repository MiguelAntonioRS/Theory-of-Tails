import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private JFrame frame;
    private JTextField lambdaField;
    private JTextField muField;
    private JTextField serversField;
    private JTextField capacityField; // Nuevo campo para la capacidad de la fuente
    private JTextArea resultArea;

    public Main() {
        frame = new JFrame("Teoría de Colas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 650); // Ajusta el tamaño para incluir el nuevo campo
        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel lambdaLabel = new JLabel("Lambda (λ):");
        lambdaLabel.setBounds(10, 20, 120, 25);
        panel.add(lambdaLabel);

        lambdaField = new JTextField(20);
        lambdaField.setBounds(140, 20, 165, 25);
        panel.add(lambdaField);

        JLabel muLabel = new JLabel("Mu (μ):");
        muLabel.setBounds(10, 50, 120, 25);
        panel.add(muLabel);

        muField = new JTextField(20);
        muField.setBounds(140, 50, 165, 25);
        panel.add(muField);

        JLabel serversLabel = new JLabel("Servidores (s):");
        serversLabel.setBounds(10, 80, 120, 25);
        panel.add(serversLabel);

        serversField = new JTextField(20);
        serversField.setBounds(140, 80, 165, 25);
        panel.add(serversField);

        JLabel capacityLabel = new JLabel("Fuente:");
        capacityLabel.setBounds(10, 110, 150, 25);
        panel.add(capacityLabel);

        capacityField = new JTextField(20);
        capacityField.setBounds(160, 110, 165, 25);
        panel.add(capacityField);

        JButton calculateButton = new JButton("Calcular");
        calculateButton.setBounds(10, 150, 150, 25);
        panel.add(calculateButton);

        JButton clearButton = new JButton("Limpiar");
        clearButton.setBounds(170, 150, 150, 25);
        panel.add(clearButton);

        JButton exitButton = new JButton("Salir");
        exitButton.setBounds(330,150,100,25);
        panel.add(exitButton);

        resultArea = new JTextArea();
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBounds(10, 190, 460, 390);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scrollPane);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateQueueModels();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lambdaField.setText("");
                muField.setText("");
                serversField.setText("");
                capacityField.setText(""); // Limpiar el nuevo campo
                resultArea.setText("");
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void calculateQueueModels() {
        try {
            double lambda = Double.parseDouble(lambdaField.getText());
            double mu = Double.parseDouble(muField.getText());
            int servers = Integer.parseInt(serversField.getText());
            int capacity = Integer.parseInt(capacityField.getText()); // Obtener la capacidad de la fuente

            Calculator calculator = new Calculator();
            String results = calculator.calculateAllModels(lambda, mu, servers, capacity); // Pasar la capacidad a Calculator

            resultArea.setText(results);
        } catch (NumberFormatException e) {
            resultArea.setText("Por favor, ingrese valores numéricos válidos.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }
}
