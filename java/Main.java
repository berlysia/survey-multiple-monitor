import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.BoxLayout;
import java.util.function.Consumer;

class MyFrame extends Frame {
    public MyFrame() {
        setBounds(100, 200, 300, 400);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        refreshButtons();
    }

    void refreshButtons() {
        removeAll();

        GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for (GraphicsDevice gdev : genv.getScreenDevices()) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            pw.println(gdev.getIDstring());
            GraphicsConfiguration[] gcs = gdev.getConfigurations();
            GraphicsConfiguration gc = gcs[0];
            Rectangle bounds = gc.getBounds();
            pw.printf("x: %d, y: %d, w: %d, h: %d\n", bounds.x, bounds.y, bounds.width, bounds.height);

            Button b = new Button(sw.toString());
            b.addActionListener(event -> {
                TestFrame tf = new TestFrame(bounds);
                tf.setVisible(true);
            });
            add(b);
        }
    }
}

class TestFrame extends Frame {
    public static<T> void let(T value, Consumer<T> cons){
        cons.accept(value);
    }

    public TestFrame(Rectangle bounds) {
        setBounds(bounds);
        let(this, tf -> {
            tf.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    tf.dispose();
                }
            });
        });
        setLayout(new BorderLayout());

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        Rectangle rect = getBounds();
        pw.printf("x: %d, y: %d, w: %d, h: %d\n", rect.x, rect.y, rect.width, rect.height);
        add("Center", new Button(sw.toString()));
    }
}

class ExitAdapter extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
}

public class Main {
    public static void main(String[] args) {
       MyFrame mf = new MyFrame();
       mf.setVisible(true);
    }
}
