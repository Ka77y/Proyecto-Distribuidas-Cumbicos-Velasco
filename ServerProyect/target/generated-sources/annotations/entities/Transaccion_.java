package entities;

import entities.Cliente;
import entities.TipoTransaccion;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-01-29T08:19:36")
@StaticMetamodel(Transaccion.class)
public class Transaccion_ { 

    public static volatile SingularAttribute<Transaccion, Integer> idTransaccion;
    public static volatile SingularAttribute<Transaccion, Date> fecha;
    public static volatile SingularAttribute<Transaccion, BigDecimal> monto;
    public static volatile SingularAttribute<Transaccion, Cliente> idCliente;
    public static volatile SingularAttribute<Transaccion, TipoTransaccion> idTipo;
    public static volatile SingularAttribute<Transaccion, BigDecimal> saldo;

}