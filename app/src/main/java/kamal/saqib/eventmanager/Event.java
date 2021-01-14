package kamal.saqib.eventmanager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;



/**
 * Created by Dell on 1/13/2018.
 */

public class Event implements Serializable{
    Employee organizer;
    ArrayList<Employee> presenter;
    String date;
    String eventname;
    String time;
    String address;



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Event(){
        this.presenter=new ArrayList<>();
    }

    public Employee getOrganizer() {
        return organizer;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public void setOrganizer(Employee organizer) {
        this.organizer = organizer;
    }

    public ArrayList<Employee> getPresenter() {
        return presenter;
    }

    public void setPresenter(ArrayList<Employee> presenter) {
        this.presenter = presenter;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
