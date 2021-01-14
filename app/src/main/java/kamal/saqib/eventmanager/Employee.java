package kamal.saqib.eventmanager;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Dell on 6/23/2017.
 */

public class Employee implements Serializable {
    String name;
    String phone;
    String email;
    String post;
    ArrayList<Event> event;
    String facebookId, twitterId;

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }



    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public ArrayList<Event> getEvent() {
        return event;
    }

    public void setEvent(ArrayList<Event> event) {
        this.event = event;
    }

    public void addEvent(Event event){
        this.event.add(event);
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(String twitterId) {
        this.twitterId = twitterId;
    }
    public Employee(){
        event=new ArrayList<>();
    }

    public Employee(String name, String phone, String email, String post){
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.facebookId = "";
        this.twitterId = "";
        this.post = post;
        this.event=new ArrayList<>();

    }


}
