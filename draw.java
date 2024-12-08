import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import javax.swing.*;
import java.util.ArrayList;

public class draw extends JFrame {
    ArrayList<ArrayList<Double>> points;
    private double scale = 20.0;
    private int offsetX = 0;
    private int offsetY = 0;
    private int dragStartX = 0;
    private int dragStartY = 0;

    public draw(ArrayList<ArrayList<Double>> points) {    
        this.points = points;
        setTitle("Plot of the Expression");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(Color.WHITE);

        JPanel drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawPlot(g);
            }
        };

        getContentPane().add(drawingPanel);
        setVisible(true);

        drawingPanel.setBackground(Color.WHITE);

        drawingPanel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int rotation = e.getWheelRotation();
                if (rotation < 0) {
                    scale *= 1.1; 
                } else {
                    scale /= 1.1; 
                }
                repaint();
            }
        });
        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dragStartX = e.getX();
                dragStartY = e.getY();
            }
        });
        drawingPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int deltaX = e.getX() - dragStartX;
                int deltaY = e.getY() - dragStartY;
                offsetX += deltaX;
                offsetY += deltaY;
                dragStartX = e.getX();
                dragStartY = e.getY();
                repaint();
            }
        });
    }

    private void drawPlot(Graphics g) {
        int originX = getWidth() / 2 + offsetX;
        int originY = getHeight() / 2 + offsetY;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.BLACK);
        g2d.drawLine(0, originY, getWidth(), originY);
        g2d.drawLine(originX, 0, originX, getHeight());

        g2d.setColor(Color.BLUE);
        for (int i = 0; i < points.size(); i++) {
            ArrayList<Double> point = points.get(i);

            int pixelX = originX + (int) (point.get(0) * scale); 
            int pixelY = originY - (int) (point.get(1) * scale);

            g2d.fillOval(pixelX - 3, pixelY - 3, 6, 6);

            if (i > 0) {
                ArrayList<Double> prevPoint = points.get(i - 1);
                int prevPixelX = originX + (int) (prevPoint.get(0) * scale);
                int prevPixelY = originY - (int) (prevPoint.get(1) * scale);

                g2d.draw(new Line2D.Double(prevPixelX, prevPixelY, pixelX, pixelY));
            }
        }
    }
}
