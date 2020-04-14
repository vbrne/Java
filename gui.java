import javax.swing.*;

//guru99.com/java-swing-gui.html#1
class gui {
  public static void main(String args[]) {
    JFrame frame = new JFrame("My First GUI");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(300,300);
    JButton button = new JButton("Press");
    frame.getContentPane().add(button);
    frame.setVisible(true);
  }
}
