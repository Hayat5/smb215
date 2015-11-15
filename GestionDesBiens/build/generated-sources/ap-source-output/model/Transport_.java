package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Personnel;
import model.Transaction;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-11-15T20:19:04")
@StaticMetamodel(Transport.class)
public class Transport_ { 

    public static volatile CollectionAttribute<Transport, Transaction> transactionCollection;
    public static volatile SingularAttribute<Transport, Integer> transportId;
    public static volatile SingularAttribute<Transport, Personnel> personnelId;
    public static volatile SingularAttribute<Transport, Date> transportDate;

}