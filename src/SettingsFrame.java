import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class SettingsFrame extends JFrame {

    private JComboBox browserComboBox;
    private JCheckBox oneRoom;
    private JCheckBox twoRooms;
    private JCheckBox threeRooms;
    private JTextField apartmentNumbersTextField;
    private JTextField netIDTextField;
    private JPasswordField passwordTextField;

    public SettingsFrame() {
        this.initIcon();
        this.setTitle("Wymount Apartment Sniper");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        pack();
        this.setSize(this.getWidth() * 2, this.getHeight());
    }

    private void initIcon() {
        try {
            InputStream iconStream = SettingsFrame.class.getResourceAsStream("/WymountSniper.png");
            if (iconStream != null)
                this.setIconImage(ImageIO.read(iconStream));
        } catch (IOException e) {
            // let it go
        }
    }

    private void initComponents() {
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.getContentPane().add(initBrowserPanel());
        this.getContentPane().add(initRoomNumbersPanel());
        this.getContentPane().add(initApartmentNumbersPanel());
        this.getContentPane().add(initNetIDPanel());
        this.getContentPane().add(initPasswordPanel());
        this.getContentPane().add(initButtonsPanel());
    }

    private JPanel initBrowserPanel() {
        JLabel browserLabel = new JLabel("Browser:");
        String[] browsers = { "firefox", "chrome" };
        browserComboBox = new JComboBox<>(browsers);
        JPanel browserPanel = new JPanel();
        browserPanel.setLayout(new BoxLayout(browserPanel, BoxLayout.X_AXIS));
        browserPanel.add(browserLabel);
        browserPanel.add(browserComboBox);
        return browserPanel;
    }

    private JPanel initRoomNumbersPanel() {
        JLabel numberOfRoomsLabel = new JLabel("Number Of Rooms:");
        oneRoom = new JCheckBox("1");
        oneRoom.setSelected(true);
        twoRooms = new JCheckBox("2");
        twoRooms.setSelected(true);
        threeRooms = new JCheckBox("3");
        threeRooms.setSelected(true);
        JPanel roomNumbersPanel = new JPanel();
        roomNumbersPanel.add(numberOfRoomsLabel);
        roomNumbersPanel.add(oneRoom);
        roomNumbersPanel.add(twoRooms);
        roomNumbersPanel.add(threeRooms);
        return roomNumbersPanel;
    }

    private JPanel initApartmentNumbersPanel() {
        JLabel apartmentNumbersLabel = new JLabel("Apartment Numbers:");
        apartmentNumbersTextField = new JTextField();
        JButton helpButton = new JButton("?");
        String helpMessage = "When adding apartment numbers you can use specific apartment numbers like '877' or building numbers like '5A'.\n" +
                "You can add multiple apartment or building numbers that you want by separating them with commas like '877,5A,605'.\n" +
                "If you do not want to specify any building or apartment leave this field blank.";
        helpButton.addActionListener(e -> JOptionPane.showMessageDialog(null, helpMessage,  "Help", JOptionPane.QUESTION_MESSAGE));
        JPanel apartmentNumbersPanel = new JPanel();
        apartmentNumbersPanel.setLayout(new BoxLayout(apartmentNumbersPanel, BoxLayout.X_AXIS));
        apartmentNumbersPanel.add(apartmentNumbersLabel);
        apartmentNumbersPanel.add(apartmentNumbersTextField);
        apartmentNumbersPanel.add(helpButton);
        return apartmentNumbersPanel;
    }

    private JPanel initNetIDPanel() {
        JLabel netIDLabel = new JLabel("NetID:");
        netIDTextField = new JTextField();
        JPanel netIDPanel = new JPanel();
        netIDPanel.setLayout(new BoxLayout(netIDPanel, BoxLayout.X_AXIS));
        netIDPanel.add(netIDLabel);
        netIDPanel.add(netIDTextField);
        return netIDPanel;
    }

    private JPanel initPasswordPanel() {
        JLabel passwordLabel = new JLabel("Password:");
        passwordTextField = new JPasswordField();
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordTextField);
        return passwordPanel;
    }

    private JPanel initButtonsPanel() {
        JButton closeButton = new JButton("close");
        closeButton.addActionListener(e -> closeEvent());
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> okEvent());
        this.getRootPane().setDefaultButton(okButton);
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.add(closeButton);
        buttonsPanel.add(okButton);
        return buttonsPanel;
    }

    public String getBrowser() {
        return browserComboBox.getSelectedItem().toString();
    }

    public String getNetID() {
        return netIDTextField.getText();
    }

    public String getPassword() {
        return passwordTextField.getText();
    }

    public List<String> getApartmentNumbers() {
        List<String> apartmentNumbers = new ArrayList<>();
        for (String apartmentNumber : apartmentNumbersTextField.getText().split(",")) {
            if (apartmentNumber != null && !apartmentNumber.trim().isEmpty())
                apartmentNumbers.add(apartmentNumber);
        }
        return apartmentNumbers;
    }

    public boolean[] getRoomNumbers() {
        return new boolean[] { oneRoom.isSelected(), twoRooms.isSelected(), threeRooms.isSelected() };
    }

    public void okEvent() {
        this.setVisible(false);
        this.dispose();
    }

    public void closeEvent() {
        this.setVisible(false);
        this.dispose();
    }
}
