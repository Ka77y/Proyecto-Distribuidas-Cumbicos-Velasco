/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epn.edu.ec.entities;

import java.io.Serializable;
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
@Table(name = "tipo_transaccion")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "TipoTransaccion.findAll", query = "SELECT t FROM TipoTransaccion t"),
	@NamedQuery(name = "TipoTransaccion.findByIdTipo", query = "SELECT t FROM TipoTransaccion t WHERE t.idTipo = :idTipo"),
	@NamedQuery(name = "TipoTransaccion.findByNombre", query = "SELECT t FROM TipoTransaccion t WHERE t.nombre = :nombre")})
public class TipoTransaccion implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo")
	private Integer idTipo;
	@Basic(optional = false)
    @Column(name = "nombre")
	private String nombre;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipo")
	private List<Transaccion> transaccionList;

	public TipoTransaccion() {
	}

	public TipoTransaccion(Integer idTipo) {
		this.idTipo = idTipo;
	}

	public TipoTransaccion(Integer idTipo, String nombre) {
		this.idTipo = idTipo;
		this.nombre = nombre;
	}

	public Integer getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(Integer idTipo) {
		this.idTipo = idTipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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
		hash += (idTipo != null ? idTipo.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof TipoTransaccion)) {
			return false;
		}
		TipoTransaccion other = (TipoTransaccion) object;
		if ((this.idTipo == null && other.idTipo != null) || (this.idTipo != null && !this.idTipo.equals(other.idTipo))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "epn.edu.ec.entities.TipoTransaccion[ idTipo=" + idTipo + " ]";
	}
	
}
