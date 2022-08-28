package src.core.gui;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

public class VisualPanel extends JPanel {

    Image[] jeep      = new Image[6];

    Image[] hotDogMan = new Image[7];

    Integer maxWidth = getWidth(), maxHeight = getHeight();

    HashMap<Long, Animation> map = new HashMap<>();

    HashMap<Rectangle, JTabbedPane> mapNew = new HashMap<>();

    public VisualPanel() {
        super();
        Toolkit t = Toolkit.getDefaultToolkit();

        for (int i = 0; i < jeep.length; i++) jeep[i]           = t.getImage("assets/jeep/sprite_" + i + ".png");
        for (int i = 0; i < hotDogMan.length; i++) hotDogMan[i] = t.getImage("assets/hotDogMan/sprite_" + i + ".png");

        setBackground(new Color(251, 231, 170));
        setLayout(null);

        TableModel tableModel = GUI.tablePanel.table.getModel();
        for (int i = 0; i < tableModel.getRowCount(); i++) {

            Object[] data = new Object[13];
            for (int j = 0; j < tableModel.getColumnCount(); j++) data[j] = tableModel.getValueAt(i, j);

            addToPanel(data);

        }

    }

    public void addToPanel(Object[] data) {
        Animation animation = createAnimation(data[11].equals("null") ? hotDogMan : jeep, data);
        Rectangle rect = new Rectangle((Integer) data[3], (Integer) data[4], 340, 162);

        if (mapNew.keySet().stream().allMatch( x -> !x.intersects(rect))) {
            JTabbedPane pane = new JTabbedPane();
            pane.setBorder(BorderFactory.createMatteBorder(0,0,0,0, Color.BLACK));
            pane.addTab("" + data[1], animation);
            mapNew.put(rect, pane); pane.setBounds(rect);
            add(pane);
        } else {
            for (Rectangle key : mapNew.keySet())
                if (key.intersects(rect)) {
                    if (mapNew.get(key).getTabCount() == 0) add(mapNew.get(key));
                    mapNew.get(key).addTab("" + data[1], animation);
                    break;
                }
        }

        animation.addMouseListener(getMouseListener(animation));
        checkResizing(data);
        revalidate();
        repaint();
    }

    public Animation createAnimation(Image[] im, Object[] data) {
        return new Animation(im, data);
    }

    public void checkResizing(Object[] data) {
        maxWidth  = Math.max(maxWidth,  (Integer) data[3]);
        maxHeight = Math.max(maxHeight, (Integer) data[4]);
        if (!(getWidth() == maxWidth+200 && getHeight() == maxHeight + 200)) {
            setPreferredSize(new Dimension(maxWidth+200, maxHeight+200));
            revalidate();
            repaint();
        }
    }

    public Object[] getDataById(Object id) { return getAnimationById((Long) id).getData(); }

    public Animation getAnimationById(Long id) {
        for (JTabbedPane pane : new LinkedList<>(mapNew.values()))
            for (int i = 0; i < pane.getTabCount(); i++)
                if (((Animation) pane.getComponentAt(i)).getId().equals(id))
                    return (Animation) pane.getComponentAt(i);
        return null;
    }

    public void updateAnimation(Object[] data) {
        removeAnimation((Long) data[0]);
        addToPanel(data);

        checkResizing(data);
        revalidate();
        repaint();

    }

    public void removeAnimation(Long id) {
        new LinkedList<>(mapNew.values()).stream().forEach(x -> {
            for (int i = 0; i < x.getTabCount(); i++)
                if (((Animation) x.getComponentAt(i)).getId().equals(id) ) {
                    x.remove(i);
                    ((Animation) x.getComponentAt(i)).stopTimer();
                }


            if (x.getTabCount() == 0) remove(x);
        });

        revalidate();
        repaint();
    }

    public MouseListener getMouseListener(Animation animation) {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new UpdateFrame(animation);
            }
            public void mousePressed(MouseEvent e) {} public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {} public void mouseExited(MouseEvent e) {}
        };
    }
}
