package utils;

import com.bitsinharmony.recognito.VoicePrint;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {

    String userName;
    Map<String,Locker> lockers = new HashMap<>();
    static Store<User> store = new Store<User>("UserData");
    Map<String, Object> dataMap = new HashMap<>();

    public User(String userName){
        this.userName =  userName;
        File file = new File(userName);
        if (!file.exists()){
            file.mkdirs();
        }
    }

    public static void addUser(String userName, User user) throws Exception {
        store.updateStore(userName, user, store.ADD);
    }

    public static void removeUser(String userName) throws Exception {
        store.updateStore(userName, null, store.REMOVE);
    }

    public void addProperty(String propertyName, Object property){
        dataMap.put(propertyName, property);
    }

    public void removeProperty(String propertyName){
        dataMap.remove(propertyName);
    }

    public Object getProperty(String propertyName){
        return dataMap.get(propertyName);
    }

    public static User getUser(String userName){
        Map<String, User> map = store.getStore();
        return map.get(userName);
    }

    public static void setUser(String userName, User user) throws Exception {
        store.updateStore(userName, user, store.ADD);
    }

    public void addLocker(String lockerName, Locker locker){
        lockers.put(lockerName, locker);
    }

    public Locker getLocker(String lockerName){
        return lockers.get(lockerName);
    }

    public HashMap<String, Locker> getLockerList(){
        return (HashMap<String, Locker>) lockers;
    }
}
