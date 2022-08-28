package src.core.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

public class Animation extends JPanel {
    Image[] im;
    Long id;
    Object[] data;
    int time, index = 0;
    Timer timer;


    public Animation(Image[] im, Object[] data) {
        this.data = data;
        this.time = (Integer) data[7];
        this.im = im;
        this.id = (Long) data[0];

        setBackground(new Color(251, 231, 170));

        timer = new Timer(time, e -> {
            index++;
            if (index == im.length) index = 0;
            revalidate(); repaint();
        });
        timer.setInitialDelay(0);
        timer.start();
    }

    public Animation(Image[] im, int time) {
        this.data = null; this.time = 0; this.im = im;

        timer = new Timer(time, e -> {
            index++;
            if (index == im.length) {
                timer.setDelay(time);
                index = 0;
            };
            repaint();
        });
        timer.setInitialDelay(0);
    }

    public void stopTimer() {
        timer.stop();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(im[index], 0, 0, null);
    }


    public Object[] getData() { return data; }

    public Long getId() { return id; }

    public void update(Object[] data) {
        this.data = data;
        this.id = (Long) data[0];

        setBounds((Integer) data[3], (Integer) data[4], 340, 134);

        timer.setDelay((Integer) data[7]);
    }
}
