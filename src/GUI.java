/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GUI extends javax.swing.JFrame {

    private int x, y, final1, final2;
    private ArrayList<Node> nodes;
    private ArrayList<Arrow> arrow;
    private Shapes generalShape;
    private Stack<Shapes> redo;
    private boolean invalidArrow;
    private Node first, second;
    private String currentFisrt, currentLast;
    private int xFirst, xLast;
    private ArrayList<ArrayList<Node>> forwordPath;
    private ArrayList<ArrayList<Node>> loops;
    private ArrayList<Double> gainPath;
    private ArrayList<Double> gainLoops;
    private double[] delta;
    private double overall;
    private ArrayList<ArrayList<Node>> selfLoops;
    private ArrayList<int[]> comTLoop;
    private ArrayList<Double> gainSelfLoops;
    private String massage;
    private int highlight;
    private ImageIcon nodeIcon = createImageIcon("node.png",
            "a pretty but meaningless splat");
    ;
    private ImageIcon pathIcon = createImageIcon("arrow.png",
            "a pretty but meaningless splat");

    ;

    public GUI() {
        this.setExtendedState(GUI.MAXIMIZED_BOTH);
        initComponents();
        this.setVisible(true);
        nodes = new ArrayList<>();
        arrow = new ArrayList<>();
        redo = new Stack<>();
        invalidArrow = false;
        xFirst = this.getWidth();
        xLast = 0;
        forwordPath = new ArrayList<>();
        loops = new ArrayList<>();
        selfLoops = new ArrayList<>();
        gainSelfLoops = new ArrayList<>();
        gainLoops = new ArrayList<>();
        gainLoopList.add("Gain: ");
        loopList.add("All Loops: ");
        pathList.add("Forword Paths: ");
        gainPathList.add("Gain: ");
        deltaList.add("Delta: ");
        nonTLoopList.add("Com. nonTLoop:");

    }

    protected ImageIcon createImageIcon(String path,
            String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private void drawArrowHead(Graphics2D g2, Point tip, Point tail, Color color) {
        g2.setPaint(color);
        double dy = tip.y - tail.y;
        double dx = tip.x - tail.x;
        double theta = Math.atan2(dy, dx);
        //System.out.println("theta = " + Math.toDegrees(theta));  
        double x, y, rho = theta + Math.toRadians(40);
        for (int j = 0; j < 2; j++) {
            x = tip.x - 20 * Math.cos(rho);
            y = tip.y - 20 * Math.sin(rho);
            g2.draw(new Line2D.Double(tip.x, tip.y, x, y));
            rho = theta - Math.toRadians(40);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Panel1 = new javax.swing.JPanel()
        {
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                int minX,minY,maxX,maxY;
                Graphics2D g2d = (Graphics2D)g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.decode("#F8F8F8"));

                for ( int i=0; i< this.getWidth() ; i++ ){
                    g2d.drawLine( (this.getWidth()/90)*i, 0,(this.getWidth()/90)*i,this.getWidth());
                }
                for ( int i=0; i< this.getHeight() ; i++ ){
                    g2d.drawLine( 0, (this.getHeight()/40)*i , this.getWidth() , (this.getHeight()/40)*i );
                }

                BasicStroke s ;
                s = new BasicStroke(2);
                g2d.setStroke(s);

                if (  generalShape instanceof NodeShape){
                    g2d.setColor(Color.decode("#660099"));
                }else{
                    g2d.setColor(Color.decode("#33CC66"));
                }
                g2d.setFont(new Font("Arial", Font.BOLD, 13));
                if (generalShape != null ){
                    g2d.draw(generalShape.getShape());
                }

                for ( int i = 0 ;i< arrow.size() ; i++ ){
                    if ( arrow.get(i).isOpposite() ){
                        g2d.setColor(Color.decode("#009999"));
                    }else{
                        g2d.setColor(Color.decode("#33CC66"));
                    }
                    boolean drawed =false;
                    if ( !forwordPath.isEmpty()){
                        ArrayList<Node> hightlightPath= forwordPath.get(highlight);
                        for ( int k = 0 ; k< hightlightPath.size()-1 ; k++ ){
                            if ( arrow.get(i).getFrom().equals(hightlightPath.get(k).getName()) && arrow.get(i).getTo().equals(hightlightPath.get(k+1).getName())
                                || arrow.get(i).getFrom().equals(hightlightPath.get(k+1).getName()) && arrow.get(i).getTo().equals(hightlightPath.get(k).getName())
                            ){
                                g2d.setColor(Color.RED);
                                g2d.draw(arrow.get(i).getShape().getShape());
                                drawed =true;
                                System.out.println(arrow.get(i).getGain());
                                break;
                            }
                        }
                    }
                    if ( !drawed )
                    g2d.draw(arrow.get(i).getShape().getShape());
                }
                for ( int i = 0 ;i< arrow.size() ; i++ ){
                    int x1 = arrow.get(i).getShape().getX1();
                    int x2 = arrow.get(i).getShape().getX2();
                    int h = (int) (Math.abs(x1 - x2) / 3.5);
                    if ( arrow.get(i).getShape() instanceof Ellipse){
                        if ( arrow.get(i).isOpposite() ){
                            g2d.setColor(Color.decode("#009999"));
                            g2d.drawString(String.valueOf(arrow.get(i).getGain()),x1+((x2-x1)/2)-40,(250 - h / 2)-6);
                        }else{
                            g2d.setColor(Color.decode("#33CC66"));
                            g2d.drawString(String.valueOf(arrow.get(i).getGain()),x1+((x2-x1)/2)+20,(250 - h / 2)-6);
                        }
                        Point head = new Point((x1+x2)/2,(250 - h / 2)-2);
                        Point tail = new Point(x1, (250 - h / 2)-2);
                        drawArrowHead(g2d,head, tail, g2d.getColor());
                    }else if ( arrow.get(i).getShape() instanceof Loop)
                    g2d.drawString(String.valueOf(arrow.get(i).getGain()),x1,h+320);
                }
                g2d.setColor(Color.decode("#4a7ebb"));
                g2d.setFont(new Font("Arial", Font.BOLD, 20));
                for (int i=nodes.size()-1; i>= 0 ;i--)
                {
                    g2d.fill(nodes.get(i).getShape().getShape());
                    g2d.drawString(nodes.get(i).getName(), nodes.get(i).getShape().getX1()-10, 250+40);
                }
                if ( massage != null && !massage.isEmpty() ){
                    g2d.setFont(new Font("Arial", Font.BOLD, 100));
                    g2d.drawString(massage, 30, 250);
                    massage = null;
                }

            }
        }
        ;
        ref = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        shape5 = new javax.swing.JToggleButton();
        shape6 = new javax.swing.JToggleButton();
        compute = new javax.swing.JButton();
        loopList = new java.awt.List();
        pathList = new java.awt.List();
        gainPathList = new java.awt.List();
        deltaList = new java.awt.List();
        gainLoopList = new java.awt.List();
        jPanel2 = new javax.swing.JPanel();
        overAllGain = new javax.swing.JLabel();
        deltaLabel = new javax.swing.JLabel();
        nonTLoopList = new java.awt.List();
        MenuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        New = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(0, 153, 0));
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });

        Panel1.setBackground(new java.awt.Color(255, 255, 255));
        Panel1.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                Panel1MouseWheelMoved(evt);
            }
        });
        Panel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Panel1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Panel1MouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Panel1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Panel1MouseReleased(evt);
            }
        });
        Panel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                Panel1MouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                Panel1MouseMoved(evt);
            }
        });

        ref.setEditable(false);

        javax.swing.GroupLayout Panel1Layout = new javax.swing.GroupLayout(Panel1);
        Panel1.setLayout(Panel1Layout);
        Panel1Layout.setHorizontalGroup(
            Panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel1Layout.createSequentialGroup()
                .addComponent(ref, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1905, Short.MAX_VALUE))
        );
        Panel1Layout.setVerticalGroup(
            Panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel1Layout.createSequentialGroup()
                .addComponent(ref, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 560, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("components"));

        shape5.setBackground(new java.awt.Color(255, 255, 255));
        shape5.setIcon(nodeIcon);
        shape5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shape5ActionPerformed(evt);
            }
        });

        shape6.setBackground(new java.awt.Color(255, 255, 255));
        shape6.setIcon(pathIcon);
        shape6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shape6ActionPerformed(evt);
            }
        });

        compute.setBackground(new java.awt.Color(0, 0, 153));
        compute.setFont(new java.awt.Font("Andalus", 0, 18)); // NOI18N
        compute.setForeground(new java.awt.Color(255, 255, 255));
        compute.setText("Compute");
        compute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                computeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(shape5, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(shape6, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(compute, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(shape5, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                    .addComponent(shape6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(compute, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        loopList.setFont(new java.awt.Font("Andalus", 0, 14)); // NOI18N

        pathList.setFont(new java.awt.Font("Andalus", 0, 14)); // NOI18N
        pathList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pathListMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pathListMousePressed(evt);
            }
        });
        pathList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pathListActionPerformed(evt);
            }
        });

        gainPathList.setFont(new java.awt.Font("Andalus", 0, 14)); // NOI18N

        deltaList.setFont(new java.awt.Font("Andalus", 0, 14)); // NOI18N

        gainLoopList.setFont(new java.awt.Font("Andalus", 0, 14)); // NOI18N

        jPanel2.setBackground(Color.decode("#003366"));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Output", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(153, 153, 255)));
        jPanel2.setForeground(Color.decode("#003366"));

        overAllGain.setFont(new java.awt.Font("Andalus", 0, 24)); // NOI18N
        overAllGain.setForeground(new java.awt.Color(255, 255, 255));
        overAllGain.setText("OverAll Gain = ");

        deltaLabel.setFont(new java.awt.Font("Andalus", 0, 24)); // NOI18N
        deltaLabel.setForeground(new java.awt.Color(255, 255, 255));
        deltaLabel.setText("Delta =");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(overAllGain, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(deltaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(overAllGain)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(deltaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                .addContainerGap())
        );

        MenuBar.setBackground(Color.decode("#003366"));
        MenuBar.setForeground(new java.awt.Color(102, 255, 255));
        MenuBar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        MenuBar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                MenuBarKeyTyped(evt);
            }
        });

        jMenu1.setBackground(new java.awt.Color(204, 255, 255));
        jMenu1.setForeground(new java.awt.Color(0, 153, 255));
        jMenu1.setText("File");

        New.setForeground(new java.awt.Color(0, 153, 255));
        New.setText("New");
        New.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewActionPerformed(evt);
            }
        });
        jMenu1.add(New);

        jMenuItem3.setForeground(new java.awt.Color(0, 153, 255));
        jMenuItem3.setText("Exit");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        MenuBar.add(jMenu1);

        setJMenuBar(MenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pathList, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gainPathList, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deltaList, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loopList, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gainLoopList, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nonTLoopList, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(636, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pathList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(loopList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(gainPathList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deltaList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(gainLoopList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nonTLoopList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(613, 613, 613))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(Panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        this.getContentPane().setBackground(Color.decode("#003366"));

    }//GEN-LAST:event_formWindowOpened

    private void Panel1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel1MouseMoved
        ref.setText(String.valueOf(evt.getX() + " : " + evt.getY()));
    }//GEN-LAST:event_Panel1MouseMoved

    private void Panel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel1MousePressed
        x = evt.getX();
        y = evt.getY();
        if (shape5.isSelected()) {
            generalShape = new NodeShape(x);
            boolean ok = false;
            String d;
            do {
                d = JOptionPane.showInputDialog("Node Name: ");
                boolean repeated = false;
                for (int i = 0; i < nodes.size(); i++) {
                    if (d != null && d.equals(nodes.get(i).getName())) {
                        repeated = true;
                        break;
                    }
                }
                if (!repeated) {
                    ok = true;
                }
            } while (!ok);
            if (d != null && !d.isEmpty()) {
                if (x < xFirst) {
                    currentFisrt = d;
                    xFirst = x;
                    System.out.println("currentFisrt: " + currentFisrt);
                }
                if (x > xLast) {
                    currentLast = d;
                    xLast = x;
                    System.out.println("currentLast: " + currentLast);
                }

                Node temp = new Node(generalShape, d);
                nodes.add(temp);
                repaint();
            }
        }
        if ((new Select().isSelected2(nodes, x)) && shape6.isSelected()) {
            first = nodes.get(new Select().isSelected(nodes, x));
            generalShape = new Ellipse(x, evt.getX());
        } else {
            generalShape = null;
            invalidArrow = true;
        }
//        System.out.println((new Select().isSelected2(nodes, x, y)) + " 1");

    }//GEN-LAST:event_Panel1MousePressed

    private void Panel1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel1MouseReleased
        if (generalShape instanceof Ellipse && !invalidArrow && new Select().isSelected2(nodes, evt.getX())) {
            Arrow arrow2 = null;
            String d = JOptionPane.showInputDialog("Gain = ");
            if (d != null && !d.isEmpty()) {
                second = nodes.get(new Select().isSelected(nodes, evt.getX()));
//            System.out.println(first.getName() + " : " + second.getName());
//            System.out.println(new Select().isSelected2(nodes, evt.getX(), evt.getY()) + " 2");
                if (second.getName().equals(first.getName())) {
                    generalShape = new Loop(x, evt.getX());
                    arrow2 = new Arrow(generalShape, Double.parseDouble(d), first.getName(), second.getName());
                    ArrayList<Node> temp = new ArrayList<>();
                    temp.add(first);
                    temp.add(first);
                    selfLoops.add(temp);
                    gainSelfLoops.add(Double.parseDouble(d));
                } else {
                    generalShape.setX2(second.getShape().getX1());
                    generalShape.setX1(first.getShape().getX1());
                    arrow2 = new Arrow(generalShape, Double.parseDouble(d), first.getName(), second.getName());
                }

                for (int i = 0; i < nodes.size(); i++) {
                    if (nodes.get(i).getName().equals(first.getName())) {
                        nodes.get(i).adderEdge(Double.parseDouble(d));
                        nodes.get(i).adderNode(second);
//                        System.out.println(nodes.get(i).getName() + " :-> " + nodes.get(i).getEdges().get(nodes.get(i).getEdges().size() - 1) + " : " + nodes.get(i).getConnections().get(nodes.get(i).getConnections().size() - 1).getName());
                    }
                }
                if (first.getShape().getX1() > second.getShape().getX1()) {
                    arrow2.setOpposite(true);
                }
                arrow.add(arrow2);
                System.out.println(arrow.size());
                first = null;
                second = null;
                repaint();
            }
        } else {
            generalShape = null;
            repaint();
        }
        invalidArrow = false;
    }//GEN-LAST:event_Panel1MouseReleased

    private void Panel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel1MouseDragged
        final1 = evt.getX();
        final2 = evt.getY();
        if (shape6.isSelected() && !invalidArrow) {
            generalShape = new Ellipse(x, evt.getX());
        }
        repaint();
    }//GEN-LAST:event_Panel1MouseDragged

    private void Panel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel1MouseEntered
    }//GEN-LAST:event_Panel1MouseEntered

    private void Panel1MouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_Panel1MouseWheelMoved
    }//GEN-LAST:event_Panel1MouseWheelMoved

    private void Panel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel1MouseClicked
    }//GEN-LAST:event_Panel1MouseClicked

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
    }//GEN-LAST:event_formWindowStateChanged

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
    }//GEN-LAST:event_formWindowActivated

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void NewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewActionPerformed
        nodes = new ArrayList<>();
        arrow = new ArrayList<>();
        redo = new Stack<>();
        invalidArrow = false;
        xFirst = this.getWidth();
        xLast = 0;
        forwordPath = new ArrayList<>();
        loops = new ArrayList<>();
        selfLoops = new ArrayList<>();
        gainSelfLoops = new ArrayList<>();
        gainLoops = new ArrayList<>();
        loopList.clear();
        gainLoopList.clear();
        gainPathList.clear();
        deltaList.clear();
        nodes.clear();
        arrow.clear();
        pathList.clear();
        nonTLoopList.clear();
        gainLoopList.add("Gain: ");
        loopList.add("All Loops: ");
        pathList.add("Forword Paths: ");
        gainPathList.add("Gain: ");
        deltaList.add("Delta: ");
        nonTLoopList.add("Com. nonTLoop:");
        generalShape = null;
        repaint();
    }//GEN-LAST:event_NewActionPerformed

    private void MenuBarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenuBarKeyTyped
    }//GEN-LAST:event_MenuBarKeyTyped

    private void shape5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shape5ActionPerformed
        shape6.setSelected(false);
    }//GEN-LAST:event_shape5ActionPerformed

    private void shape6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shape6ActionPerformed
        shape5.setSelected(false);
    }//GEN-LAST:event_shape6ActionPerformed

    private void unVisited() {
        for (int i = 0; i < nodes.size(); i++) {
            nodes.get(i).setVisited(false);
        }
    }

    private void addToPathList() {
        pathList.clear();
        gainPathList.clear();
        deltaList.clear();
        pathList.add("Forword Paths: ");
        gainPathList.add("Gain: ");
        deltaList.add("Delta: ");
        for (int i = 0; i < forwordPath.size(); i++) {
            String d = "";
            for (int k = 0; k < forwordPath.get(i).size(); k++) {
                d += forwordPath.get(i).get(k).getName() + ",";
            }
            gainPathList.add((String.valueOf(gainPath.get(i))));
            deltaList.add(String.valueOf(delta[i]));
            pathList.add(d);
        }
    }

    private void addToLoopList() {
        loopList.clear();
        gainLoopList.clear();
        gainLoopList.add("Gain: ");
        loopList.add("All Loops: ");
        for (int i = 0; i < loops.size(); i++) {
            String d = "";
            for (int k = 0; k < loops.get(i).size(); k++) {
                d += loops.get(i).get(k).getName() + ",";
            }
            gainLoopList.add(String.valueOf(gainLoops.get(i)));
            loopList.add(d);
        }
    }

    private void addToNonTLoopList() {
        nonTLoopList.clear();
        nonTLoopList.add("Com. nonTLoop:");
        for (int i = 0; i < comTLoop.size(); i++) {
            String temp = "";
            for (int k = 0; k < comTLoop.get(i).length; k++) {
                temp += String.valueOf(comTLoop.get(i)[k]) + ",";
            }
            nonTLoopList.add(temp);
        }
    }

    private void computeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_computeActionPerformed

        if (!nodes.isEmpty()) {
            GraphTraversal graphTraversal = new GraphTraversal(currentFisrt, currentLast);
            for (int i = 0; i < selfLoops.size(); i++) {
                graphTraversal.addLoop(selfLoops.get(i), gainSelfLoops.get(i));
            }
            Node temp = new Node(currentFisrt);
            for (int i = 0; i < nodes.size(); i++) {
                if (nodes.get(i).getName().equals(currentFisrt)) {
                    temp = nodes.get(i);
                }
            }
            graphTraversal.caller(temp);
            overall = graphTraversal.overAllTrans();
            overAllGain.setText("OverAll Gain = " + String.valueOf(overall));
            forwordPath = graphTraversal.getForwardPath();
            loops = graphTraversal.getLoops();
            gainPath = graphTraversal.getGainForwardPath();
            gainLoops = graphTraversal.getGainLoopsarray();
            comTLoop = graphTraversal.getCombinations();
            delta = graphTraversal.getEahDelta();
            deltaLabel.setText("Delta =" + graphTraversal.delta());
            addToPathList();
            addToLoopList();
            addToNonTLoopList();
            unVisited();
        } else {
            massage = " No Nodes To Proccess !! ";
            repaint();
        }
    }//GEN-LAST:event_computeActionPerformed

    private void pathListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pathListActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pathListActionPerformed

    private void pathListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pathListMouseClicked
        if (pathList.getSelectedIndex() > 0) {
            highlight = pathList.getSelectedIndex() - 1;
        }
        repaint();
    }//GEN-LAST:event_pathListMouseClicked

    private void pathListMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pathListMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_pathListMousePressed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JMenuItem New;
    private javax.swing.JPanel Panel1;
    private javax.swing.JButton compute;
    private javax.swing.JLabel deltaLabel;
    private java.awt.List deltaList;
    private java.awt.List gainLoopList;
    private java.awt.List gainPathList;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private java.awt.List loopList;
    private java.awt.List nonTLoopList;
    private javax.swing.JLabel overAllGain;
    private java.awt.List pathList;
    private javax.swing.JTextField ref;
    private javax.swing.JToggleButton shape5;
    private javax.swing.JToggleButton shape6;
    // End of variables declaration//GEN-END:variables
}
