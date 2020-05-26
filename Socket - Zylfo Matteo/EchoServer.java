// EchoServer.java
import java.io.*;
import java.net.*;
public class EchoServer {
    //variabili per la gestione del file rubrica
    static final int RECORD_SIZE = 10 + 15 + 13;
    static final String file = "rubrica.dat";
    static long tanti;
    static int NRRECORD = 10;
    static RandomAccessFile inp;
    static RandomAccessFile out;
    static String vuota = "                                        ";
public static final int PORT = 1050; // porta al di fuori del range 1-1024 !
public static void main(String[] args) throws IOException {
    messaggi(args); //chiamo il metodo per stabilire la connessione
}
static void messaggi(String[] args)throws IOException{
 ServerSocket serverSocket = new ServerSocket(PORT);
 Socket clientSocket=null;
 BufferedReader in=null;
 PrintWriter out=null;
 try {
 // bloccante finchè non avviene una connessione
 clientSocket = serverSocket.accept();
 System.out.println("Connection accepted: \n\n"+ clientSocket);
 // creazione stream di input da clientSocket
 InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
 in = new BufferedReader(isr);
 // creazione stream di output su clientSocket
 OutputStreamWriter osw = new OutputStreamWriter(clientSocket.getOutputStream());
 BufferedWriter bw = new BufferedWriter(osw);
 out = new PrintWriter(bw, true);
 //ciclo di ricezione dal client e invio di risposta
 while (true) {
 String str = in.readLine();
 if (str.equals("END")) break;
 System.out.println("Echoing: " + str);
 out.println(str);
 //una volta ricevuta la stringa dal cliente, si procede alla ricerca chiamando il metodo ricerca e passandogli il nominativo
 ricerca(str,args);
 
  // chiusura di stream e socket
 System.out.println("Il server si chiude...");
 out.close();
 in.close();
 clientSocket.close();
 serverSocket.close();
 
 messaggi(args);
 }
 }
 catch (IOException e) {
 System.err.println("Accept fallita!");
 System.exit(1);
 }

 }
 
 static void ricerca(String scelta,String[]args) throws IOException{
     //metodo per la ricerca e stampa del nominativo, preso da un messaggio client-->server
        String nome;
        byte[] no = new byte[10];
        String cogn;
        byte[] co = new byte[15];
        String num;
        byte[] nu = new byte[20];

        inp = new RandomAccessFile(file, "r");
        long lungo = inp.length();
        NRRECORD = (int) lungo / RECORD_SIZE;
        int i = 0;

        while (i < NRRECORD) {
            inp.seek((i) * RECORD_SIZE);
            inp.read(no, 0, 10);
            nome = new String(no, 0, 10);

            if (nome.contains(scelta)) {
                inp.read(co, 0, 15);
                cogn = new String(co, 0, 15);
                inp.read(nu, 0, 13);
                num = new String(nu, 0, 13);

                System.out.println(i + 1 + ")\t" + nome.trim() + "\t" + cogn.trim() + "\t" + num.trim());
            }

            i++;

        }

        inp.close();

    }
} // EchoServer