package friseurbarbers.mein.meinfriseur.Modules;

import java.io.Serializable;

/**
 * Created by Aarya on 2/3/2018.
 */

public class MeinFriseurModule implements Serializable {
    String id;
    String username;
    String usertype;
    String email;
    String phonenumber;
    String gender;
    String categorytype;
    String 	password;
    String salonimage;
    String salontitle;
    String result;
    String message;
    int image;

    public MeinFriseurModule(String salontitle, int image) {
        this.salontitle = salontitle;
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public MeinFriseurModule(String salontitle)
    {
        this.salontitle=salontitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getCategorytype() {
        return categorytype;
    }

    public void setCategorytype(String categorytype) {
        this.categorytype = categorytype;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalonimage() {
        return salonimage;
    }

    public void setSalonimage(String salonimage) {
        this.salonimage = salonimage;
    }

    public String getSalontitle() {
        return salontitle;
    }

    public void setSalontitle(String salontitle) {
        this.salontitle = salontitle;
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
}
