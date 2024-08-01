import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

public class UnitConverter1 extends JFrame {

    private JFrame frame;
    private JPanel inputPanel;
    private JPanel resultsPanel;
    private JTextField inputField;
    private JComboBox<String> unitComboBox;
    private JCheckBox modeCheckBox;

    private JLabel kmLabel, cmLabel, inLabel, yardLabel, micromLabel, mmLabel, nmLabel, mileLabel, footLabel, mLabel;


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					UnitConverter window = new UnitConverter();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
        // Run the UI in the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new UnitConverter1().setVisible(true);
        });
    }

    /**
     * Create the application.
     */
    public UnitConverter1() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Unit Converter");

        // Set the layout of the frame
        setLayout(new BorderLayout());

        // Create a panel for the input field and combo box
        inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputField = new JTextField("1000", 10);

        String[] units = {"km", "cm", "m", "mm", "microm", "nm", "mile", "yard", "foot", "inch"};
        unitComboBox = new JComboBox<>(units);

        inputPanel.add(inputField);
        inputPanel.add(unitComboBox);

        // Create a checkbox for dark and light modes
        modeCheckBox = new JCheckBox("Dark Mode");
        modeCheckBox.setBackground(Color.BLACK);
        modeCheckBox.setForeground(Color.WHITE);
        modeCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (modeCheckBox.isSelected()) {
                    setDarkMode();
                } else {
                    setLightMode();
                }
            }
        });

        inputPanel.add(modeCheckBox);
        add(inputPanel, BorderLayout.NORTH);

        // Create a panel for the conversion results
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new GridLayout(10, 2));

        kmLabel = new JLabel("0");
        mLabel = new JLabel("0");
        cmLabel = new JLabel("0");
        mmLabel = new JLabel("0");
        micromLabel = new JLabel("0");
        nmLabel = new JLabel("0");
        mileLabel = new JLabel("0");
        yardLabel = new JLabel("0");
        footLabel = new JLabel("0");
        inLabel = new JLabel("0");

        addResult(resultsPanel, "Kilometre", kmLabel);
        addResult(resultsPanel, "Meter", mLabel);
        addResult(resultsPanel, "Centimeter", cmLabel);
        addResult(resultsPanel, "Millimeter", mmLabel);
        addResult(resultsPanel, "Micrometer", micromLabel);
        addResult(resultsPanel, "Nanometer", nmLabel);
        addResult(resultsPanel, "Mile", mileLabel);
        addResult(resultsPanel, "Yard", yardLabel);
        addResult(resultsPanel, "Foot", footLabel);
        addResult(resultsPanel, "Inch", inLabel);

        add(resultsPanel, BorderLayout.CENTER);

        // Add listeners to inputField and unitComboBox
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                update();
            }
        });

        unitComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                update();
            }
        });

        // Set default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the size of the frame
        setSize(400, 400);

        // Center the frame
        setLocationRelativeTo(null);

        // Set initial mode to light
        setLightMode();
    }

    private void setDarkMode() {
        inputPanel.setBackground(Color.BLACK);
        resultsPanel.setBackground(Color.BLACK);
        inputField.setForeground(Color.WHITE);
        inputField.setBackground(Color.BLACK);
        inputField.setBorder(new LineBorder(Color.WHITE));
        unitComboBox.setForeground(Color.WHITE);
        unitComboBox.setBackground(Color.BLACK);
        unitComboBox.setBorder(new LineBorder(Color.WHITE));
        modeCheckBox.setForeground(Color.WHITE);
        modeCheckBox.setBackground(Color.BLACK);

        for (Component component : resultsPanel.getComponents()) {
            if (component instanceof JLabel) {
                component.setForeground(Color.WHITE);
                component.setBackground(Color.BLACK);
                ((JLabel) component).setBorder(new LineBorder(Color.WHITE));
            }
        }
    }

    private void setLightMode() {
        inputPanel.setBackground(Color.WHITE);
        resultsPanel.setBackground(Color.WHITE);
        inputField.setForeground(Color.BLACK);
        inputField.setBackground(Color.WHITE);
        inputField.setBorder(new LineBorder(Color.BLACK));
        unitComboBox.setForeground(Color.BLACK);
        unitComboBox.setBackground(Color.WHITE);
        unitComboBox.setBorder(new LineBorder(Color.BLACK));
        modeCheckBox.setForeground(Color.BLACK);
        modeCheckBox.setBackground(Color.WHITE);

        for (Component component : resultsPanel.getComponents()) {
            if (component instanceof JLabel) {
                component.setForeground(Color.BLACK);
                component.setBackground(Color.WHITE);
                ((JLabel) component).setBorder(new LineBorder(Color.BLACK));
            }
        }
    }

    private void addResult(JPanel panel, String unit, JLabel valueLabel) {
        JLabel unitLabel = new JLabel(unit);
        panel.add(unitLabel);
        panel.add(valueLabel);
    }

    private void update() {
        if (!inputField.getText().equals("") && unitComboBox.getSelectedItem() != null) {
            double in = Double.parseDouble(inputField.getText());
            switch (unitComboBox.getSelectedItem().toString()) {
                case "km":
                    setKm(in);
                    break;
                case "m":
                    setKm(in / 1000);
                    break;
                case "cm":
                    setKm(in / 100000);
                    break;
                case "mm":
                    setKm(in / 1000000);
                    break;
                case "microm":
                    setKm(in / 1000000000);
                    break;
                case "nm":
                    double d = 1000000 * 1000000;
                    setKm(in / d);
                    break;
                case "mile":
                    setKm(in * 1.609);
                    break;
                case "yard":
                    setKm(in / 1094);
                    break;
                case "foot":
                    setKm(in / 3281);
                    break;
                case "inch":
                    setKm(in / 39370);
                    break;
            }
        }

    }

    private void setKm(double km_in) {
        kmLabel.setText(String.valueOf(km_in));
        mLabel.setText(String.valueOf(km_in * 1000));
        cmLabel.setText(String.valueOf(km_in * 100000));
        mmLabel.setText(String.valueOf(km_in * 1000000));
        micromLabel.setText(String.valueOf(km_in * 1000000000));
        nmLabel.setText(String.valueOf(km_in * 1000000 * 1000000));
        mileLabel.setText(String.valueOf(km_in / 1.609));
        yardLabel.setText(String.valueOf(km_in * 1094));
        footLabel.setText(String.valueOf(km_in * 3281));
        inLabel.setText(String.valueOf(km_in * 39370));
    }

    class ToggleSwitchUI extends javax.swing.plaf.basic.BasicToggleButtonUI {
        private static final Color ON_COLOR = new Color(0, 200, 0);
        private static final Color OFF_COLOR = Color.GRAY;
        private static final Color KNOB_COLOR = Color.WHITE;

        @Override
        public void paint(Graphics g, JComponent c) {
            JToggleButton button = (JToggleButton) c;
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = button.getWidth();
            int height = button.getHeight();
            int arcSize = height / 2;

            if (button.isSelected()) {
                g2.setColor(ON_COLOR);
                g2.fillRoundRect(0, 0, width, height, arcSize, arcSize);
                g2.setColor(KNOB_COLOR);
                g2.fillOval(width - height, 0, height, height);
            } else {
                g2.setColor(OFF_COLOR);
                g2.fillRoundRect(0, 0, width, height, arcSize, arcSize);
                g2.setColor(KNOB_COLOR);
                g2.fillOval(0, 0, height, height);
            }

            g2.dispose();
        }

        @Override
        public Dimension getPreferredSize(JComponent c) {
            return new Dimension(50, 25);
        }
    }

}
