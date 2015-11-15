/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Hayat
 */
public class Location2 {
    
    private String lan;
    private String log;

    public Location2(String lan, String log) {
        this.lan = lan;
        this.log = log;
    }
    
    
    
    public Location2() {
       
    }

    public void setLan(String lan) {
        this.lan = lan;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getLan() {
        return lan;
    }

    public String getLog() {
        return log;
    }
    
    
    
    
}
