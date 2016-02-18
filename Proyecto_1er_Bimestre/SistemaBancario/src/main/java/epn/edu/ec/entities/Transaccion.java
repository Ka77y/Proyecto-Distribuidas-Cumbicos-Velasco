/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epn.edu.ec.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author josemiguel
 */
@Entity
@Table(name = "transaccion")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Transaccion.findAll", query = "SELECT t FROM Transaccion t"),
	@NamedQuery(name = "Transaccion.findByIdTransaccion", query = "SELECT t FROM Transaccion t WHERE t.idTransaccion = :idTransaccion"),
	@NamedQuery(name = "Transaccion.findByMonto", query = "SELECT t FROM Transaccion t WHERE t.monto = :monto"),
	@NamedQuery(name = "Transaccion.findBySaldo", query = "SELECT t FROM Transaccion t WHERE t.saldo = :saldo"),
	@NamedQuery(name = "Transaccion.findByCedula", query = "SELECT t FROM Transaccion t WHERE t.idCliente.cedula = :cedula"),
	@NamedQuery(name = "Transaccion.findUltimo", query = "SELECT t FROM Transaccion t WHERE t.idCliente.cedula = :cedula order by t.idTransaccion desc"),
	@NamedQuery(name = "Transaccion.findByFecha", query = "SELECT t FROM Transaccion t WHERE t.fecha = :fecha")})
public class Transaccion implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_transaccion")
	private Integer idTransaccion;
	// @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
	@Column(name = "monto")
	private BigDecimal monto;
	@Column(name = "saldo")
	private BigDecimal saldo;
	@Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	@JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne(optional = false)
	private Cliente idCliente;
	@JoinColumn(name = "id_tipo", referencedColumnName = "id_tipo")
    @ManyToOne(optional = false)
	private TipoTransaccion idTipo;

	public Transaccion() {
	}

	public Transaccion(Integer idTransaccion) {
		this.idTransaccion = idTransaccion;
	}

	public Integer getIdTransaccion() {
		return idTransaccion;
	}

	public void setIdTransaccion(Integer idTransaccion) {
		this.idTransaccion = idTransaccion;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Cliente getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Cliente idCliente) {
		this.idCliente = idCliente;
	}

	public TipoTransaccion getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(TipoTransaccion idTipo) {
		this.idTipo = idTipo;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idTransaccion != null ? idTransaccion.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Transaccion)) {
			return false;
		}
		Transaccion other = (Transaccion) object;
		if ((this.idTransaccion == null && other.idTransaccion != null) || (this.idTransaccion != null && !this.idTransaccion.equals(other.idTransaccion))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "epn.edu.ec.entities.Transaccion[ idTransaccion=" + idTransaccion + " ]";
	}
	
}
