import javax.swing.*;

public class Main {

    public static void main(String args[]) {
        SettingsFrame settingsDialog = new SettingsFrame() {
            @Override
            public void okEvent() {
                this.setVisible(false);
                WymountSniper wymountSniper = new WymountSniper(this.getBrowser(), this.getNetID(), this.getPassword());
                try {
                    wymountSniper.snipe(this.getApartmentNumbers(), this.getRoomNumbers());
                } catch (Exception e)  {
                    JOptionPane.showMessageDialog(this, e.getMessage());
                }
                this.setVisible(true);
            }
        };
        settingsDialog.setVisible(true);
    }

}
