/**
 *
 * @author DEVANSH
 * 1910110132
 */

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class client {
    public static void main(String args[]) {
        double radius = 20.0;
            System.out.println("Hello");
        
        try {
            Socket clients = new Socket("localhost", 6789);
            System.out.println("Socket connected");
//
            DataInputStream ip = new DataInputStream(clients.getInputStream());
            DataOutputStream op = new DataOutputStream(clients.getOutputStream());
            ObjectOutputStream os = new ObjectOutputStream(clients.getOutputStream());
            System.out.println("client.main()");

//            ObjectInputStream is = new ObjectInputStream(clients.getInputStream());
        
            Scanner ss = new Scanner(System.in);
            Integer size;
            char node1;
            char node2;
            Integer n;
            System.out.println("Please enter size");
            size= ss.nextInt();
            op.writeInt(size);
            Integer[][] arr = new Integer[size][size];
          //  System.out.println("Enter size of matrix:");
            //size = ss.nextInt();

            System.out.println("Enter the 25 matrix elements");
            for(int i=0;i<size;i++){
                for(int j=0;j<size;j++)
                 arr[i][j] = ss.nextInt();
            }
            os.writeObject(arr);

            System.out.println("The matrix is ");
            
            for(int i=0;i<size;i++){
                for(int j=0;j<size;j++)
                    System.out.print(arr[i][j]+" ");
                System.out.println("");                  
            }
            System.out.println("Enter n");
            n = ss.nextInt();
            op.writeInt(n);

            System.out.println("Enter node1");
            node1 = ss.next().charAt(0);
            op.writeChar(node1);
            System.out.println("Enter node2");
            node2 = ss.next().charAt(0);
            
            // op.writeInt(size);
            // os.writeObject(arr);
            // op.writeInt(n);
            // op.writeChar(node1);
            op.writeChar(node2);

            op.flush();
            char result = ip.readChar();
            System.out.println(result);  // displaying the result 
            
            // saving the file as 'result.jpg' 
            FileOutputStream fo = new FileOutputStream("result.jpg");
            long filelength = ip.readLong();
            byte[] buffer = new byte[4*1024];
            int bytes = 0;
            while (filelength > 0 && (bytes = ip.read(buffer, 0, (int)Math.min(buffer.length, filelength))) != -1) {
            fo.write(buffer,0,bytes);
            filelength -= bytes;      // read upto file size
        }
            fo.close();
            System.err.println("Image saved as 'result.jpg'");
            clients.close();
            
            
            
            
        } catch (IOException e) {
            System.out.println(e);
        }
        catch (Exception ee){
            System.out.println(ee);
        }
        
        
        
    }
}
