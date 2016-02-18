/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epn.edu.ec.negocio;

import epn.edu.ec.controlador.ClienteJpaController;
import epn.edu.ec.entities.*;
import epn.edu.ec.vista.FrmTransaccion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

/**
 *
 * @author josemiguel
 */
public class Logging {

    public Boolean validarLogging(String cedula, String password) {

        Boolean retorno=false;
        ClienteJpaController controladorCliente = new ClienteJpaController(Persistence.createEntityManagerFactory("epn.edu.ec_SistemaBancario_jar_1.0-SNAPSHOTPU"));
        EntityManager em = controladorCliente.getEntityManager();
        List< Cliente> lstCliente = em.createNamedQuery("Cliente.findByCedula", Cliente.class).setParameter("cedula", Integer.valueOf(cedula)).getResultList();
        if (!lstCliente.isEmpty()) {
            if (lstCliente.get(0).getPassword().equals(password)) {
                JOptionPane.showMessageDialog(null, "Bienvenido " + lstCliente.get(0).getNombre(), "", JOptionPane.INFORMATION_MESSAGE);
                FrmTransaccion frmTransaccion =  new FrmTransaccion(lstCliente.get(0));
                frmTransaccion.setVisible(true);                
                retorno = true;
            } else {

                JOptionPane.showMessageDialog(null, "La contraseña es incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "El usuario no se encuentra registrado", "Error", JOptionPane.ERROR_MESSAGE);
        }
		
        
		
		

		
        return retorno;

    }
}
