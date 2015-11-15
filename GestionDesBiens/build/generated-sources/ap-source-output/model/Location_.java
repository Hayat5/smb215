package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Center;
import model.Item;
import model.Personnel;
import model.Salle;
import model.Transaction;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-11-15T20:19:04")
@StaticMetamodel(Location.class)
public class Location_ { 

    public static volatile CollectionAttribute<Location, Transaction> transactionCollection;
    public static volatile SingularAttribute<Location, Center> centerId;
    public static volatile CollectionAttribute<Location, Item> itemCollection;
    public static volatile SingularAttribute<Location, Personnel> personnelId;
    public static volatile SingularAttribute<Location, Salle> salleId;
    public static volatile SingularAttribute<Location, Integer> locationId;
    public static volatile CollectionAttribute<Location, Transaction> transactionCollection2;
    public static volatile CollectionAttribute<Location, Transaction> transactionCollection1;

}