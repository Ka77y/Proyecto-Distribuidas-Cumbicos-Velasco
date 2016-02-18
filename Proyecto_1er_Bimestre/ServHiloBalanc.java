
import java.io.*;
import java.net.*;
import java.util.logging.*;
import java.util.StringTokenizer;

public class ServHiloBalanc extends Thread
{
    private Socket socket;
    private int idSessio;

    static String hostName = "localhost";
    static int portNumberBank   = 6655;

    PrintWriter    outC;
    BufferedReader inC;

    public ServHiloBalanc(Socket socketHilo, int idHilo)
    {
        this.socket = socketHilo;
        this.idSessio = idHilo;

        try {
            outC  = new PrintWriter( socketHilo.getOutputStream(), true);
            inC   = new BufferedReader( new InputStreamReader(socketHilo.getInputStream()));
        }
        catch (IOException ex) {
            Logger.getLogger(ServHiloBalanc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static String [] cadena=new String [2] ;
    static String cpuS1="",cpuS2="",memoriaS1="",memoriaS2="";

    public static void DeCodificadorSonda1(String msg)
    {
        StringTokenizer tokens = new StringTokenizer(msg);
        int n=0;
        while(tokens.hasMoreTokens())
        {
            cadena[n]=tokens.nextToken();
            n++;
        }
        cpuS1=cadena[0];
        memoriaS1=cadena[1];
    }

    public static void DeCodificadorSonda2(String msg)
    {
        StringTokenizer tokens = new StringTokenizer(msg);
        int n=0;
        while(tokens.hasMoreTokens())
        {
            cadena[n]=tokens.nextToken();
            n++;
        }
        cpuS2=cadena[0];
        memoriaS2=cadena[1];
    }


    @Override
    public void run()
    {
        String mensajeCliente="",mensajeBanco="", msjSondaBancoOne="",msjSondaBancoTwo="";
        String mensajeServOne="",mensajeServTwo="";

        try {

            Socket socketServidorOne = new Socket(hostName, portNumberBank);
            Socket socketServidorTwo = new Socket(hostName, portNumberBank);
            PrintWriter    outBancoOne        = new PrintWriter(socketServidorOne.getOutputStream(), true);
            PrintWriter    outBancoTwo        = new PrintWriter(socketServidorTwo.getOutputStream(), true);

            BufferedReader inBancoOne    = new BufferedReader( new InputStreamReader(socketServidorOne.getInputStream()));
            BufferedReader stdInOne      = new BufferedReader( new InputStreamReader(System.in));


            BufferedReader inBancoTwo    = new BufferedReader( new InputStreamReader(socketServidorTwo.getInputStream()));
            BufferedReader stdInTwo      = new BufferedReader( new InputStreamReader(System.in));

            while ((mensajeCliente = inC.readLine()) != null)
            {

                System.out.println("El cliente con idSesion "+this.idSessio+" envio : "+ mensajeCliente);

                String msg="sonda";

                outBancoOne.println(msg);
                outBancoTwo.println(msg);

                msjSondaBancoOne=inBancoOne.readLine();
                DeCodificadorSonda1(msjSondaBancoOne);

                msjSondaBancoTwo=inBancoTwo.readLine();
                DeCodificadorSonda2(msjSondaBancoTwo);

                System.out.println("Recibi del servidor 1: " + msjSondaBancoOne);
                System.out.println("Recibi del servidor 2: " + msjSondaBancoTwo);
                System.out.print("Datos"+cpuS1+" "+cpuS2+" "+memoriaS1+" "+memoriaS2+"**");
                if (Double.parseDouble(cpuS1.replace(",",".")) <(1+Double.parseDouble(cpuS1.replace(",",".") )))
                //if (Integer.parseInt(memoriaS1)<(1+Integer.parseInt(memoriaS2)))
                {
                        System.out.println("enter");
                    if (Integer.parseInt(memoriaS1)<(1+Integer.parseInt(memoriaS2)))
                    {
                        System.out.println("enter 1");
                        outBancoOne.println(mensajeCliente);
                        mensajeBanco=inBancoOne.readLine();
                    }
                    else
                    {
                        System.out.println("enter 1");
                        outBancoTwo.println(mensajeCliente);
                        mensajeBanco=inBancoTwo.readLine();
                    }
                }
                /*
                if (Integer.parseInt(memoriaS1)==Integer.parseInt(memoriaS2))
                {
                    if (Integer.parseInt(cpuS1)>Integer.parseInt(cpuS2))
                    {
                        outBancoOne.println(mensajeCliente);
                        mensajeBanco=inBancoOne.readLine();
                    }
                    else
                    {
                        outBancoTwo.println(mensajeCliente);
                        mensajeBanco=inBancoTwo.readLine();
                    }
                }

                if (Integer.parseInt(cpuS1)>Integer.parseInt(cpuS2)&&(Integer.parseInt(memoriaS1)==Integer.parseInt(memoriaS2))&&(Integer.parseInt(cpuS1)==Integer.parseInt(cpuS2)))
                {
                    outBancoOne.println(mensajeCliente);
                    mensajeBanco=inBancoOne.readLine();
                }
                else
                {
                    outBancoTwo.println(mensajeCliente);
                    mensajeBanco=inBancoTwo.readLine();
                }*/

                outC.println(mensajeBanco);
                System.out.println("El banco con idSesion "+this.idSessio+" envio : "+ mensajeBanco);
            }

        }
        catch (IOException ex) {
            Logger.getLogger(ServHiloBalanc.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            socket.close();
        }
        catch (IOException ex) {
            Logger.getLogger(ServHiloBalanc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
