
package com.mycompany.serverproyect;
import java.util.logging.*;
import java.util.StringTokenizer;

import entities.Cliente;
import entities.TipoTransaccion;
import entities.Transaccion;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author nathy
 */
public class ServerThread extends Thread {

    Socket clientSocket;
    PrintWriter out;
    BufferedReader in;
    static String cedulaRecv = "";
    static String contraseñaRecv = "";
    static String tipoRecv = "";
    static String montoRecv = "";
    
    Sonda parametros = new Sonda();

    static String[] cadena = new String[4];
    public Transaccion ultimaTransaccion = new Transaccion();
    public Cliente cliente;

    public ServerThread(Socket sock) {
        try {
            this.clientSocket = sock;
            this.out = new PrintWriter(this.clientSocket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void enviarmensaje(String mensaje) {
        System.out.println("entro en envio mensaje:");
        this.out.println(mensaje);

    }

    public void recibemensaje() {
        
//            System.out.println(this.in.readLine());
            //LLAMAR METODO DECODIFICANDO
            System.out.println("entre a recibir:"+mensaje);
            DeCodificando(mensaje);
            transacciones();
            //ERROR NO ENCUENTRA LA ULTIMA TRANSACCION, ES VARIABLE LOCAL EN METODOS DE RETIRO Y DEPOSITO 
            //COMO OBTENGO LA ULTIMA TRANSACCION?
            
            
        
    }

    public void DeCodificando(String mensaje) {

        System.out.println("decodifique:"+mensaje);
        int n = 4;
        Logger.getLogger(ServerThread.class.getName()).log(Level.INFO, mensaje, mensaje);
        cadena = mensaje.split(",");

        cedulaRecv = cadena[0];
        System.out.println("cedula:" + cedulaRecv);
        contraseñaRecv = cadena[1];
        tipoRecv = cadena[2];
        montoRecv = cadena[3];
          
    }

    public void transacciones() {
        ClienteJpaController controladorCliente = new ClienteJpaController(Persistence.createEntityManagerFactory("com.mycompany_ServerProyect_jar_1.0-SNAPSHOTPU"));
        EntityManager em = controladorCliente.getEntityManager();
        Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, String.valueOf(Integer.parseInt(cedulaRecv)),cedulaRecv);
        List< Cliente> lstCliente = em.createNamedQuery("Cliente.findByCedula", Cliente.class).setParameter("cedula", (Integer.parseInt(cedulaRecv))).getResultList();
        Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, lstCliente.toString(),cedulaRecv);
        if (!lstCliente.isEmpty()) {
            if (lstCliente.get(0).getPassword().equals(contraseñaRecv)) {
                if ("1".equals(tipoRecv)) {
                    try {
                        //realizardeposito
                        cliente = new Cliente(lstCliente.get(0).getIdCliente());
                        cliente.setCedula(lstCliente.get(0).getCedula());
                        RealizarDeposito(cliente, new TipoTransaccion(Integer.parseInt(tipoRecv)), BigDecimal.valueOf(Double.parseDouble(montoRecv)));
                    } catch (Exception ex) {
                        Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if ("2".equals(tipoRecv)) {
                    try {
                        cliente = new Cliente(lstCliente.get(0).getIdCliente());
                        cliente.setCedula(lstCliente.get(0).getCedula());
                        RealizarRetiro(cliente, new TipoTransaccion(Integer.parseInt(tipoRecv)), BigDecimal.valueOf(Double.parseDouble(montoRecv)));
                    } catch (Exception ex) {
                        Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    //realizarconsulta
                }
            }
        }
    }

    private void RealizarDeposito(Cliente cliente, TipoTransaccion tipoTransaccion, BigDecimal monto) throws Exception {
        TransaccionJpaController controladorTransaccion = new TransaccionJpaController(Persistence.createEntityManagerFactory("com.mycompany_ServerProyect_jar_1.0-SNAPSHOTPU"));
        EntityManager em = controladorTransaccion.getEntityManager();
        //    Transaccion ultimaTransaccion = em.createNamedQuery("Transaccion.findUltimo", Transaccion.class).setParameter("cedula",lstCliente.get(0).getCedula()).setMaxResults(1).getSingleResult();
        System.out.println("entro 1");
        ultimaTransaccion = em.createNamedQuery("Transaccion.findUltimo", Transaccion.class).setParameter("cedula", cliente.getCedula()).setMaxResults(1).getSingleResult();
        Transaccion transaccion = new Transaccion();
        System.out.println("entro");
        transaccion.setFecha(Calendar.getInstance().getTime());
        transaccion.setIdCliente(cliente);
        transaccion.setIdTipo(tipoTransaccion);
        transaccion.setMonto(monto);
        transaccion.setSaldo(ultimaTransaccion.getSaldo().add(monto));
        controladorTransaccion.create(transaccion);
        enviarmensaje(transaccion.getSaldo().toString());
    }

    private void RealizarRetiro(Cliente cliente, TipoTransaccion tipoTransaccion, BigDecimal monto) throws Exception {
        TransaccionJpaController controladorTransaccion = new TransaccionJpaController(Persistence.createEntityManagerFactory("com.mycompany_ServerProyect_jar_1.0-SNAPSHOTPU"));
        EntityManager em = controladorTransaccion.getEntityManager();
        System.out.println("entro 1");
        ultimaTransaccion = em.createNamedQuery("Transaccion.findUltimo", Transaccion.class).setParameter("cedula", cliente.getCedula()).setMaxResults(1).getSingleResult();
        System.out.println("entro 2");
        Transaccion transaccion = new Transaccion();
        transaccion.setFecha(Calendar.getInstance().getTime());
        transaccion.setIdCliente(cliente);
        transaccion.setIdTipo(tipoTransaccion);
        transaccion.setMonto(monto);
        transaccion.setSaldo(ultimaTransaccion.getSaldo().subtract(monto));
        controladorTransaccion.create(transaccion);
        enviarmensaje(transaccion.getSaldo().toString());
    }
    String mensaje="";
    String mensajeSonda="";
    
    @Override
    public void run() 
    {
        try {
            while ((mensaje = in.readLine()) != null)
                {
                    System.out.println("aca toy"+mensaje);
                    if(mensaje.equals("sonda"))
                    {
                        System.out.println("paso");
                        parametros.datoSonda();
                        mensajeSonda=parametros.getI()+" "+parametros.getJ();
                        out.println(mensajeSonda);
                        System.out.println("**El balanceador envio : "+ mensaje);
                        System.out.println("--Le envio al balanceador "+mensajeSonda+" recibi : "+ mensaje);
                    }
                    else
                    {
                    recibemensaje();
                    }
                }
            }
        catch (IOException ex) {
           Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
        
        try {
            clientSocket.close();
        }
        catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    

    }
}
