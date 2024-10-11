import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class MiConversor {

    private JFrame frame;
    private JTextField txtInput;
    private JButton btnConvertir, btnLimpiar, btnAcercaDe;
    private JComboBox<Moneda> cmbMonedas;
    private JLabel lblResultado;

    public enum Moneda {
        PESOS_DOLAR(0.053),  // 1 peso = 0.053 dólares
        PESOS_EURO(0.045),   // 1 peso = 0.045 euros
        DOLAR_PESOS(18.87),   // 1 dólar = 18.87 pesos
        EURO_PESOS(22.29);    // 1 euro = 22.29 pesos

        private final double tasa;

        Moneda(double tasa) {
            this.tasa = tasa;
        }

        public double getTasa() {
            return tasa;
        }
    }

    public MiConversor() {
        initialize();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MiConversor window = new MiConversor();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void initialize() {
        frame = new JFrame("Conversor de Monedas");
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // Configurando el fondo
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);

        txtInput = new JTextField();
        txtInput.setBounds(10, 11, 123, 20);
        frame.getContentPane().add(txtInput);

        cmbMonedas = new JComboBox<>(Moneda.values());
        cmbMonedas.setBounds(10, 59, 123, 22);
        frame.getContentPane().add(cmbMonedas);

        btnConvertir = new JButton("Convertir");
        btnConvertir.setBounds(161, 59, 89, 23);
        btnConvertir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertir();
            }
        });
        frame.getContentPane().add(btnConvertir);

        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setBounds(261, 59, 89, 23);
        btnLimpiar.addActionListener(e -> limpiar());
        frame.getContentPane().add(btnLimpiar);

        lblResultado = new JLabel("Resultado: 00.00");
        lblResultado.setFont(new Font("Arial", Font.BOLD, 14));
        lblResultado.setBounds(161, 11, 200, 20);
        frame.getContentPane().add(lblResultado);

        btnAcercaDe = new JButton("Acerca de");
        btnAcercaDe.setBounds(10, 100, 100, 23);
        btnAcercaDe.addActionListener(e -> mostrarAcercaDe());
        frame.getContentPane().add(btnAcercaDe);
    }

    private void convertir() {
        String inputText = txtInput.getText();

        if (validar(inputText)) {
            double valorInput = Double.parseDouble(inputText);
            Moneda monedaSeleccionada = (Moneda) cmbMonedas.getSelectedItem();

            double resultado = valorInput * monedaSeleccionada.getTasa();
            lblResultado.setText("Resultado: " + redondear(resultado));
        }
    }

    private boolean validar(String texto) {
        try {
            double valor = Double.parseDouble(texto);
            if (valor <= 0) {
                throw new NumberFormatException();
            }
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame,
                    "Por favor ingrese un número válido mayor que cero.",
                    "Error de entrada",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private String redondear(double valor) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(valor);
    }

    private void limpiar() {
        txtInput.setText("");
        lblResultado.setText("Resultado: 00.00");
    }

    private void mostrarAcercaDe() {
        JOptionPane.showMessageDialog(frame,
                "Conversor de Monedas\nDesarrollado por [Tu Nombre]\nVersión: 1.0",
                "Acerca de", JOptionPane.INFORMATION_MESSAGE);
    }
}