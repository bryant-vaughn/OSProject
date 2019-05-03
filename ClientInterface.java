import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.*;

class ClientInterface extends JFrame {

  int be, bea, h, verticalScrollBarMaximumValue;

  JFrame frame;

  JPanel panel, title_panel, button_panel, message_panel;

  JButton submit;
  JTextField numBees, numBears, hiveSize;
  JLabel bees, bears, hive, title;
  ImageIcon ii;
  JLabel icon, mess;
  JTextArea messages;
  JScrollPane scroll;

  public ClientInterface() {

    JFrame.setDefaultLookAndFeelDecorated(true);
    frame = new JFrame("Bears Eat Bees?");

    panel = new JPanel();
    title_panel = new JPanel();
    button_panel = new JPanel();
    message_panel = new JPanel();

    submit = new JButton("submit");
    numBees = new JTextField("enter number of Bees", 15);
    numBears = new JTextField("enter number of Bears", 15);
    hiveSize = new JTextField("enter size of Bee Hive", 15);
    bees = new JLabel("# of Bees: ");
    bears = new JLabel("# of Bears: ");
    hive = new JLabel("Size of Beehive: ");
    title = new JLabel("Bears eat Bees?");
    ii = new ImageIcon("./Bears&Bees.jpg");
    icon = new JLabel(ii);
    mess = new JLabel("");
    messages = new JTextArea(20, 50);
    messages.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
    messages.setEditable(false);
    scroll = new JScrollPane(messages);

    verticalScrollBarMaximumValue = scroll.getVerticalScrollBar().getMaximum();
    scroll.getVerticalScrollBar().addAdjustmentListener(
            e -> {
                if ((verticalScrollBarMaximumValue - e.getAdjustable().getMaximum()) == 0)
                    return;
                e.getAdjustable().setValue(e.getAdjustable().getMaximum());
                this.updateCarat(scroll.getVerticalScrollBar().getMaximum());
            });

    title.setFont(new Font("Serif", Font.BOLD, 30));
    // GridLayout
    GridLayout layout = new GridLayout(4, 1, 5, 2);

    // layout.setAutoCreateGaps(true);
    // layout.setAutoCreateContainerGaps(true);
    button_panel.setLayout(layout);

    title_panel.add(icon);
    //title_panel.add(title);
    button_panel.add(bees);
    button_panel.add(numBees);
    button_panel.add(bears);
    button_panel.add(numBears);
    button_panel.add(hive);
    button_panel.add(hiveSize);
    button_panel.add(submit);
    button_panel.add(mess);

    message_panel.add(scroll);

    panel.add(title_panel);
    panel.add(button_panel);
    panel.add(message_panel);

    submit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        try {
          int bee = Integer.parseInt(numBees.getText());
          int bear = Integer.parseInt(numBears.getText());
          int hives = Integer.parseInt(hiveSize.getText());

          be = bee;
          bea = bear;
          h = hives;

          close();

        } catch(NumberFormatException nfe) {
          mess.setText("well stupid if you dont type any numbers my bears will get you!");
        }

      }
    });

    frame.add(panel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    panel.setBorder(new EmptyBorder(20, 20, 20, 20));
    frame.pack();
    frame.setVisible(true);
  }

  public int returnBees() {
    return this.be;
  }

  public int returnBears() {
    return this.bea;
  }

  public int returnHives() {
    return this.h;
  }

  public void close() {
    dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
  }

  public void appendMessage(String m) {
    this.messages.append( m + " \n\n" );
  }

  public void updateCarat(int n) {
    this.verticalScrollBarMaximumValue = n;
  }
}
