package views.subviews.components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Created by lwdthe1 on 12/5/16.
 */
class RoundJButton extends JButton {
    public RoundJButton() {
        this(null, null);
    }
    public RoundJButton(Icon icon) {
        this(null, icon);
    }
    public RoundJButton(String text) {
        this(text, null);
    }
    public RoundJButton(Action a) {
        this();
        setAction(a);
    }
    public RoundJButton(String text, Icon icon) {
        setModel(new DefaultButtonModel());
        init(text, icon);
        if(icon==null) {
            return;
        }
        setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
        setBackground(Color.BLACK);
        setContentAreaFilled(false);
        setFocusPainted(false);
        //setVerticalAlignment(SwingConstants.TOP);
        setAlignmentY(Component.TOP_ALIGNMENT);
        initShape();
    }
    protected Shape shape, base;
    protected void initShape() {
        if(!getBounds().equals(base)) {
            Dimension s = getPreferredSize();
            base = getBounds();
            shape = new Ellipse2D.Float(0, 0, s.width-1, s.height-1);
        }
    }
    @Override public Dimension getPreferredSize() {
        Icon icon = getIcon();
        Insets i = getInsets();
        int iw = Math.max(icon.getIconWidth(), icon.getIconHeight());
        return new Dimension(iw+i.right+i.left, iw+i.top+i.bottom);
    }
    @Override protected void paintBorder(Graphics g) {
        initShape();
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        //g2.setStroke(new BasicStroke(1.0f));
        g2.draw(shape);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_OFF);
    }
    @Override public boolean contains(int x, int y) {
        initShape();
        return shape.contains(x, y);
        //or return super.contains(x, y) && ((image.getRGB(x, y) >> 24) & 0xff) > 0;
    }
}