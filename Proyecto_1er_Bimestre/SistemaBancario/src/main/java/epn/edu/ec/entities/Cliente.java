/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epn.edu.ec.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author josemiguel
 */
@Entity
@Table(name = "cliente")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
	@NamedQuery(name = "Cliente.findByIdCliente", query = "SELECT c FROM Cliente c WHERE c.idCliente = :idCliente"),
	@NamedQuery(name = "Cliente.findByCedula", query = "SELECT c FROM Cliente c WHERE c.cedula = :cedula"),
	@NamedQuery(name = "Cliente.findByNombre", query = "SELECT c FROM Cliente c WHERE c.nombre = :nombre"),
	@NamedQuery(name = "Cliente.findByTelefono", query = "SELECT c FROM Cliente c WHERE c.telefono = :telefono"),
	@NamedQuery(name = "Cliente.findByDireccion", query = "SELECT c FROM Cliente c WHERE c.direccion = :direccion"),
	@NamedQuery(name = "Cliente.findByPassword", query = "SELECT c FROM Cliente c WHERE c.password = :password")})
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cliente")
	private Integer idCliente;
	@Basic(optional = false)
    @Column(name = "cedula")
	private int cedula;
	@Basic(optional = false)
    @Column(name = "nombre")
	private String nombre;
	@Column(name = "telefono")
	private String telefono;
	@Column(name = "direccion")
	private String direccion;
	@Basic(optional = false)
    @Column(name = "password")
	private String password;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idCliente")
	private List<Transaccion> transaccionList;

	public Cliente() {
	}

	public Cliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public Cliente(Integer idCliente, int cedula, String nombre, String password) {
		this.idCliente = idCliente;
		this.cedula = cedula;
		this.nombre = nombre;
		this.password = password;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public int getCedula() {
		return cedula;
	}

	public void setCedula(int cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@XmlTransient
	public List<Transaccion> getTransaccionList() {
		return transaccionList;
	}

	public void setTransaccionList(List<Transaccion> transaccionList) {
		this.transaccionList = transaccionList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idCliente != null ? idCliente.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Cliente)) {
			return false;
		}
		Cliente other = (Cliente) object;
		if ((this.idCliente == null && other.idCliente != null) || (this.idCliente != null && !this.idCliente.equals(other.idCliente))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "epn.edu.ec.entities.Cliente[ idCliente=" + idCliente + " ]";
	}
	
}
