package entity;


import common.AList;
import common.Graphs.DirectedGraph;
import common.HashedDictionary;

import java.util.Arrays;


public class Profiles {

    //name
    private String name;

    //passward
    private String  passWard;

    //gender
    private String Gender;

    //age
    private int age;

    //brithday
    private String date;

    //personal statues
    private HashedDictionary<Object, Object> hashedDictionary;

    //fridends
    private AList<String> aList;



    public Profiles() {
    }

    public Profiles(String name, String passWard, HashedDictionary<Object, Object> hashedDictionary, AList<String> aList) {
        this.name = name;
        this.passWard = passWard;
        this.hashedDictionary = hashedDictionary;
        this.aList = aList;

        this.age=0;
        this.date=null;
        this.Gender=null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassWard() {
        return passWard;
    }

    public void setPassWard(String passWard) {
        this.passWard = passWard;
    }

    public HashedDictionary<Object, Object> getHashedDictionary() {
        return hashedDictionary;
    }

    public void setHashedDictionary(HashedDictionary<Object, Object> hashedDictionary) {
        this.hashedDictionary = hashedDictionary;
    }

    public AList<String> getaList() {
        return aList;
    }

    public void setaList(AList<String> aList) {
        this.aList = aList;
    }

    public String getStatus(String key){
        Object value = hashedDictionary.getValue(key);
        return (String) value;
    }


    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        try {
            return "Profiles{" +
                    "name='" + name + '\'' +
                    ", Gender='" + Gender + '\'' +
                    ", age=" + age +
                    ", date='" + date + '\'' +
                    ", status=" + hashedDictionary.getValue("status") +"\n"+
                    ",  " + Arrays.toString(aList.toArray()) +
                    '}';

        } catch (NullPointerException e) {

            return "Profiles{" +
                    "name='" + name + '\'' +
                    ", Gender='" + Gender + '\'' +
                    ", age=" + age +
                    ", date='" + date + '\'' +
                    ", status=" + hashedDictionary.getValue("status")+
                    ", You haven't added any friends yet."  +
                    '}';
        }
    }
}



