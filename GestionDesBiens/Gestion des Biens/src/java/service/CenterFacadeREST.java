/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.gson.Gson;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import model.Center;
import model.Location2;
import org.primefaces.json.JSONException;

/**
 *
 * @author Hayat
 */
@Stateless
@Path("model.center")
public class CenterFacadeREST extends AbstractFacade<Center> {

    @PersistenceContext(unitName = "GestionDesBiensPU")
    private EntityManager em;

    public CenterFacadeREST() {
        super(Center.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Center entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Center entity) {
   
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Center find(@PathParam("id") Integer id) {

        return super.find(id);

    }

   @GET
    @Path("lastid")
    @Produces({"application/json"})
    public List<Center> lastid() {

       return em.createQuery("SELECT c FROM Center c ORDER BY c.centerId DESC").setMaxResults(1).getResultList();


    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Center> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Center> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    public void getFile(String loc) throws JSONException {

        try {

            String content = loc;

            File file2 = new File("../applications/images/map.txt");

            // if file doesnt exists, then create it
            if (!file2.exists()) {
                file2.createNewFile();
            }

            FileWriter fw = new FileWriter(file2.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @POST
    @Path("maps")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    //public Response createDataInJSON(JSONObject data) throws JSONException {
    public void created(String data) throws JSONException {

        Gson g = new Gson();
        Location2 loc = g.fromJson(data, Location2.class);
        System.out.println(loc.getLan());
        System.out.println(loc.getLog());
        getFile(data);

    }

    public String readfile() throws FileNotFoundException, IOException {

        File file = new File("../applications/images/map.txt");
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        try {
            fis.read(data);

        } catch (IOException ex) {
            Logger.getLogger(CenterFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        fis.close();

        String str = new String(data, "UTF-8");
        return str;

    }

    @GET
    @Path("map")
    @Produces({"application/json"})
    //public Response createDataInJSON(JSONObject data) throws JSONException {
    public Response createDataInJSON() throws JSONException, IOException {
        String data = readfile();

        Gson g = new Gson();
        Location2 loc = g.fromJson(data, Location2.class);
        System.out.println(loc.getLan());
        System.out.println(loc.getLog());

        return Response.status(200).entity(loc).build();

    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
