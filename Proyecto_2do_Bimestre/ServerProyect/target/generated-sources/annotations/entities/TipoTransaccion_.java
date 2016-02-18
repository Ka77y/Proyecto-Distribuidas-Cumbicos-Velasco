package entities;

import entities.Transaccion;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-02-16T19:41:03")
@StaticMetamodel(TipoTransaccion.class)
public class TipoTransaccion_ { 

    public static volatile SingularAttribute<TipoTransaccion, Integer> idTipo;
    public static volatile SingularAttribute<TipoTransaccion, String> nombre;
    public static volatile ListAttribute<TipoTransaccion, Transaccion> transaccionList;

}