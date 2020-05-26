// EchoClient.java
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class EchoClient{
    //vari campi per gestire il file
    static final int RECORD_SIZE = 10 + 15 + 13;
    static final String file = "rubrica.dat";
    static long tanti;
    static int NRRECORD = 10;
    static RandomAccessFile inp;
    static RandomAccessFile out;
    static String vuota = "                                        ";

    public static void main(String[] args) throws IOException{
        menu(args); //richiamo il metodo per stabilire la connessione
    }
    
    static void menu(String []args)throws IOException{
        //metodo per stampare un menù di interazione con l'utente
           Scanner in = new Scanner(System.in);
        int scelta;
        System.out.println("\nMenu' principale");
        System.out.println("1) Inserimento nuovo contatto"); //gestita dal client
        System.out.println("2) Ricerca contatto"); //gestita dal server
        System.out.println("3) Visualizza rubrica"); //gestita dal client
        System.out.println("4) Uscita");

        do {
            System.out.print("Scelta: ");
            scelta = in.nextInt();
        } while (scelta < 1 || scelta > 5);

        if (scelta == 1) {
            inserimento(args);
        }
        if (scelta == 2) {
            messaggi(args,"");
        }
        if (scelta == 3) {
            tutti(args);
        }
    }
    
    static void inserimento(String []args) throws IOException {
        //metodo per l'inserimento in coda dei contatti gestito dal client
        Scanner in = new Scanner(System.in);
        cls();
        String nome, cognome, numero;

        System.out.println("AGGIUNGI UN CONTATTO");

        DataOutputStream out = null;

        try {
            out = new DataOutputStream(new FileOutputStream(file, true));

            do {
                System.out.print("Inserisci il nome: ");
                nome = in.nextLine();

            } while (nome.length() < 2);

            out.writeBytes(fissa(nome, 10));

            do {
                System.out.print("Inserisci il cognome: ");
                cognome = in.nextLine();

            } while (cognome.length() < 2);

            out.writeBytes(fissa(cognome, 15));

            do {
                System.out.print("Inserisci il numero di telefono con prefisso (13 caratteri es. +123334445566): ");
                numero = in.nextLine();

            } while (numero.length() != 13);

            out.writeBytes(fissa(numero, 13));

        } catch (IOException e) {
            //errore

        } finally {
            if (out != null) {
                out.close();       //chiusura del file dopo che abbiamo inserito le informazioni
            }
        }

        tutti(args);
    }

    static void tutti(String []args) throws IOException {
        //metodo gestito dal client che stampa tutti i contatti presi dal file binario
        Scanner in = new Scanner(System.in);
        cls();

        System.out.println("N      Nome  \tCognome\t Numero");

        inp = new RandomAccessFile(file, "r");
        long lungo = inp.length();
        NRRECORD = (int) lungo / RECORD_SIZE;

        for (int i = 0; i < NRRECORD; i++) {
            inp.seek((i) * RECORD_SIZE);
            visualizzaDato(i);
        }

        inp.close();

        int scelta;

        do {
            System.out.print("\nPremi 1 per tornare al menu', premi 2 per aggiungere un altro contatto: ");
            scelta = in.nextInt();
        } while (scelta < 1 || scelta > 2);

        cls();

        if (scelta == 1) {
            menu(args);
        }
        if (scelta == 2) {
            inserimento(args);
        }
    }

    static void visualizzaDato(int i) throws IOException {

        String nome;
        byte[] no = new byte[10];
        String cogn;
        byte[] co = new byte[15];
        String num;
        byte[] nu = new byte[20];

        inp.read(no, 0, 10);
        nome = new String(no, 0, 10);
        inp.read(co, 0, 15);
        cogn = new String(co, 0, 15);
        inp.read(nu, 0, 13);
        num = new String(nu, 0, 13);

        System.out.println(i + 1 + ")\t" + nome.trim() + "\t" + cogn.trim() + "\t" + num.trim());

    }



    static String fissa(String stringa, int num) {
        //metodo per fissare la lunghezza, del campo che si indica, all'interno del file
        stringa = stringa + vuota;
        stringa = stringa.substring(0, num);

        return stringa;
    }

    static void cls() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }
    
    static void messaggi(String[] args,String scelta)throws IOException{ 
        //metodo per la connessione
        InetAddress addr;
        Scanner inp=new Scanner(System.in);
        
        if(args.length==0)
            addr=InetAddress.getByName(null);
        else
            addr=InetAddress.getByName(args[0]);
            
        Socket socket=null;
        BufferedReader in=null, stdIn=null;
        PrintWriter out=null;
        
        try{
            socket = new Socket(addr, EchoServer.PORT);
 
             // creazione stream di input da socket
             InputStreamReader isr = new InputStreamReader( socket.getInputStream());
             in = new BufferedReader(isr);
             // creazione stream di output su socket
             OutputStreamWriter osw = new OutputStreamWriter( socket.getOutputStream());
             BufferedWriter bw = new BufferedWriter(osw);
             out = new PrintWriter(bw, true);
             // creazione stream di input da tastiera
             stdIn = new BufferedReader(new InputStreamReader(System.in));
             String userInput;
             // ciclo di lettura da tastiera, invio al server e stampa risposta
              
             //viene richiesto quale contatto si desidera cercare
            cls();
            System.out.println("Sezione ricerca contatto:");
            System.out.print("Inserisci il nome da cercare (cliccare due volte invio)");
            scelta = inp.nextLine();
            fissa(scelta, 10);
             userInput = stdIn.readLine();
             out.println(scelta);
             
             System.out.println("Echo: " + in.readLine());
             //a questo punto il server elabora il nominativo e controlla che sia presente nel file binario
             System.out.println("Controlla la risposta nel server!");
             
                    System.out.println("Cliente chiude la connection...");
        out.close();
        in.close();
        stdIn.close();
        socket.close();
     
        }
        catch (UnknownHostException e) {
         System.err.println("Non conosco l'host"+ addr);
         System.exit(1);
         } catch (IOException e) {
         System.err.println("Non è possibile fare delle operazione di I/O " + addr);
         System.exit(1);
     }
        
 ;
        
        int chose;
             
             do{
                 System.out.println("Premi 1 per andare al menu: ");
                 chose=inp.nextInt();
                }while(chose<1 || chose>1);
               
                if(chose==1)
                    menu(args);
    }
}