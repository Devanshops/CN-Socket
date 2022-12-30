/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Goel Devansh
 * 1910110132
 */
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;

public class server extends JPanel {

    Integer size;
    Integer X[][];
    List<List<Integer[]>> nodeList = new ArrayList<>();
    Integer A[] = { 35, 100 };
    Integer B[] = { 115, 50 };
    Integer C[] = { 195, 100 };
    Integer D[] = { 75, 180 };
    Integer E[] = { 150, 180 };

    public void showConnectedNodes() {
        for (Integer i = 0; i < size; i++) {
            System.out.println("Connection " + i + " :");
            for (Integer[] node : nodeList.get(i)) {
                System.out.println("Node : " + node[0] + " " + node[1]);
                System.out.println("Node : " + node);
            }
        }
    }

    public server() {
        super();
    }

    public void getSize(Integer s) {
        size = s;
    }

    public void getNodes(Integer X[][], Integer size) {

        System.out.println("Checking s " + X.length);
        for (Integer i = 0; i < size; i++) {
            for (Integer j = 0; j < size; j++) {
                // System.out.prIntegerln("X is " + X[i][j] + " with i=" + i + " and j=" + j);
                Integer node1[] = new Integer[2];
                Integer node2[] = new Integer[2];
                if (X[i][j] != 0) {
                    switch (i) {
                        case 0: {
                            node1 = A;
                            break;
                        }
                        case 1: {
                            node1 = B;
                            break;
                        }
                        case 2: {
                            node1 = C;
                            break;
                        }
                        case 3: {
                            node1 = D;
                            break;
                        }
                        case 4: {
                            node1 = E;
                            break;
                        }
                    }

                    switch (j) {
                        case 0: {
                            node2 = A;
                            break;
                        }
                        case 1: {
                            node2 = B;
                            break;
                        }
                        case 2: {
                            node2 = C;
                            break;
                        }
                        case 3: {
                            node2 = D;
                            break;
                        }
                        case 4: {
                            node2 = E;
                            break;
                        }
                    }
                    ArrayList<Integer[]> tempList = new ArrayList<>();
                    tempList.add(node1);
                    tempList.add(node2);
                    nodeList.add(tempList);
                }
            }
        }
    }

    public server(Integer X[][], Integer size) {
        this.X = X;
        this.size = size;
        JFrame f = new JFrame();
        server dPanel = new server();
        dPanel.getNodes(X, size);
        dPanel.getSize(size);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(dPanel);
        f.setSize(300, 240);
        f.setVisible(true);

        BufferedImage bImg = new BufferedImage(dPanel.getWidth(), dPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D cg = bImg.createGraphics();
        // dPanel.paintAll(cg);
        dPanel.paintComponent(cg);
        try {
            if (ImageIO.write(bImg, "png", new File("output_image.png"))) {
                System.out.println("-- image saved");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void drawArrow(Graphics g1, Integer x1, Integer y1, Integer x2, Integer y2) {
        Graphics2D g = (Graphics2D) g1.create();

        Integer ARR_SIZE = 4;
        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        Integer len = (int) Math.sqrt(dx * dx + dy * dy);
        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.transform(at);

        // Draw horizontal arrow starting in (0, 0)
        g.drawLine(0, 0, len, 0);

        g.fillPolygon(new int[] { len, len - ARR_SIZE, len - ARR_SIZE, len },
                new int[] { 0, -ARR_SIZE, ARR_SIZE, 0 }, 4);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.YELLOW);

        System.out.println("Size in comp " + size);
        if (size > 1) {
            g.fillOval(25, 85, 30, 30); // A
            g.fillOval(105, 35, 30, 30); // B
            if (size > 2) {
                g.fillOval(185, 85, 30, 30); // C
                if (size > 3) {
                    g.fillOval(25 + 40, 165, 30, 30);// D
                    if (size > 4) {
                        g.fillOval(105 + 40, 165, 30, 30);// E
                    }
                }
            }
        }
        g.setColor(Color.BLACK);
        if (size > 1) {
            g.drawString("A", 25 + 10, 90 + 10);
            g.drawString("B", 105 + 10, 40 + 10);
            if (size > 2) {
                g.drawString("C", 185 + 10, 90 + 10);
                if (size > 3) {
                    g.drawString("D", 25 + 40 + 10, 155 + 10);
                    if (size > 4) {
                        g.drawString("E", 105 + 40 + 10, 155 + 10);
                    }
                }
            }
        }

        for (Integer i = 0; i < nodeList.size(); i++) {
            Integer node1[] = nodeList.get(i).get(0);
            Integer node2[] = nodeList.get(i).get(1);
            drawArrow(g, node1[0] + 5, node1[1] + 5, node2[0] + 5, node2[1] + 5);
        }

        // showConnectedNodes();
    }

    public static void main(String[] args) throws Exception {
        try {
            ServerSocket serversocket = new ServerSocket(6789); // opening the server

            System.out.println("Server is running");
            while (true) {
                Socket socket = serversocket.accept(); // listen for the client
                DataInputStream ip = new DataInputStream(socket.getInputStream()); // I/O stream for communicating with
                                                                                   // the client server
                DataOutputStream op = new DataOutputStream(socket.getOutputStream());// I/O stream for communicating
                                                                                     // with the client server
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Integer size = ip.readInt();

                Integer[][] AdjMat = (Integer[][]) in.readObject();
                System.out.println("Matrix received");

                // double radius = ip.readDouble(); // receive data from the client
                // System.out.println(radius);
                //
                for (Integer i = 0; i < size; i++) {
                    for (Integer j = 0; j < size; j++)
                        System.out.print(AdjMat[i][j] + " ");
                    System.out.println("");
                }

                Integer n = ip.readInt();
                char node1 = ip.readChar();
                char node2 = ip.readChar();
                //
                System.out.println("n= " + n);
                System.out.println("node1= " + node1);
                System.out.println("node2= " + node2);

                boolean vis[] = new boolean[size];
                boolean d = DFS(AdjMat, node1 - 65, node2 - 65, vis, n);
                char response;
                if (d)
                    response = 'Y';
                else
                    response = 'N';
                System.out.println(response);
                op.writeChar(response);
                server x = new server(AdjMat, size);

                // Sending the image

                String path = "output_image.png";
                Integer bytes = 0;
                File file = new File(path);
                FileInputStream fileInputStream = new FileInputStream(file);
                op.writeLong(file.length()); // send file size
                byte[] buffer = new byte[4 * 1024];// break file into chunks
                while ((bytes = fileInputStream.read(buffer)) != -1) {
                    op.write(buffer, 0, bytes);
                    op.flush();
                }
                fileInputStream.close();
                socket.close();
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    } // main

    public static boolean DFS(Integer adj[][], Integer s, Integer d, boolean vis[], Integer n) {
        vis[s] = true;
        if (adj[s][d] != 0 && n == adj[s][d])
            return true;
        for (Integer i = 0; i < adj.length; i++) {
            if (adj[s][i] > 0 && !vis[i]) {
                if (DFS(adj, i, d, vis, n - adj[s][i]))
                    return true;
            }
        }
        vis[s] = false;
        return false;
    }
}
