package utils;

import com.bitsinharmony.recognito.VoicePrint;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Store <T>{

    static String storeName = null;
    public final static int ADD = 1, REMOVE = -1;

    public Store(String storeName){

        this.storeName = storeName+".store";
        File file = new File(this.storeName);
        if (!file.exists()){
            try {
                createStore(new Hashtable<>());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void create(Map map) throws Exception {

        FileOutputStream fileOut = null;
        ObjectOutputStream objOut = null;

        try {

            fileOut = new FileOutputStream(storeName);
            objOut = new ObjectOutputStream(fileOut);
            objOut.writeObject(map);

            fileOut.close();
            objOut.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new Exception("Unable to create store");
        }
    }

    public void createStore(Map<String, T> map) throws Exception {
        create(map);
    }

    public Map<String, T> getStore(){

        Map<String, T>map = new HashMap<>();

        FileInputStream fileIn = null;
        ObjectInputStream objIn = null;

        try{
            fileIn = new FileInputStream(storeName);
            objIn = new ObjectInputStream(fileIn);
            map = (Map<String, T>) objIn.readObject();

            fileIn.close();
            objIn.close();

        } catch (FileNotFoundException e) {
            throw new Exception("Store not found");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            return map;
        }
    }

    public void updateStore(String userKey, T t, int mode) throws Exception {

        Map<String, T> map = getStore();

        if (mode == ADD) {
            map.put(userKey, t);
        } else if(mode == REMOVE){
            map.remove(userKey);
        }

        try {
            createStore(map);
        } catch (Exception e) {
            throw new Exception("Unable to update Store");
        }
    }

}