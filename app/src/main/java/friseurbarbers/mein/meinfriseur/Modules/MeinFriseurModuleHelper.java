package friseurbarbers.mein.meinfriseur.Modules;

import java.io.Serializable;

/**
 * Created by Aarya on 2/3/2018.
 */

public class MeinFriseurModuleHelper implements Serializable {
    String id;
    String name;
    String email;
    String phonenumber;
    String gender;
    String photo;
    String 	password;
    String sid;
    String result;
    String message;
    String dateofbirth;

    String terminid;
    String friseurName;
    String datum;
    String zeit;
    String fname;
    String lname;
    String organization;
    String image;
    String status;
    String service;

    public MeinFriseurModuleHelper(String terminid, String friseurName, String datum, String zeit) {
        this.terminid = terminid;
        this.friseurName = friseurName;
        this.datum = datum;
        this.zeit = zeit;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getPhoto() {
        return photo;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getTerminid() {
        return terminid;
    }

    public void setTerminid(String terminid) {
        this.terminid = terminid;
    }

    public String getFriseurName() {
        return friseurName;
    }

    public void setFriseurName(String friseurName) {
        this.friseurName = friseurName;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getZeit() {
        return zeit;
    }

    public void setZeit(String zeit) {
        this.zeit = zeit;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
