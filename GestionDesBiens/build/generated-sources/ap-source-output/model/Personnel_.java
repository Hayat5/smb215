package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Location;
import model.Transport;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-11-15T20:19:04")
@StaticMetamodel(Personnel.class)
public class Personnel_ { 

    public static volatile SingularAttribute<Personnel, String> personnelName;
    public static volatile SingularAttribute<Personnel, Integer> personnelId;
    public static volatile CollectionAttribute<Personnel, Transport> transportCollection;
    public static volatile CollectionAttribute<Personnel, Location> locationCollection;

}