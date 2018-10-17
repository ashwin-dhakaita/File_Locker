package utils;

import org.apache.commons.io.FileUtils;


import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Locker implements Serializable {

    private String lockerName;
    private String password;

    public Locker(String lockerName, String password){
        this.lockerName = lockerName;
        this.password = password;

        File directory = new File(lockerName);
        if (!directory.exists()){
            directory.mkdirs();
        }
    }

    private void zipDirectory() throws Exception {
        String sourceFile = lockerName;
        try {
            FileOutputStream fos = new FileOutputStream(lockerName + ".lock");
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            File fileToZip = new File(sourceFile);
            zipFile(fileToZip, fileToZip.getName(), zipOut);
            zipOut.close();
            fos.close();
        } catch (Exception e){
            throw new Exception("Problem while parsing the directory");
        }
    }

    private void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }

    private void unZipDirectory() throws IOException {

        File directory = new File(lockerName);
        if (!directory.exists()){
            directory.mkdirs();
        }
        unZipFile();
    }

    private void unZipFile() throws IOException {

        String fileZip = lockerName+".lock";
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
        ZipEntry zipEntry = zis.getNextEntry();
        while(zipEntry != null){
            String fileName = zipEntry.getName();
            File newFile = new File(lockerName+"\\"+fileName.split("/")[1]);
            FileOutputStream fos = new FileOutputStream(newFile);
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }

    public void createLocker() throws Exception {
        zipDirectory();
        Cypher.encipher(lockerName+".lock", password);
        FileUtils.forceDelete(new File(lockerName+".lock"));
        FileUtils.deleteDirectory(new File(lockerName));
    }

    public void openLocker() throws Exception {

        if (password.equals(this.password)) {
            FileUtils.forceMkdir(new File(lockerName));
            Cypher.decipher(lockerName + ".locker", password);
            unZipDirectory();
            FileUtils.forceDelete(new File(lockerName + ".lock"));
        }

    }

    public void closeLocker() throws Exception {
        createLocker();
    }

    public void addToLocker(String fileName) throws Exception {
        File file = new File(fileName);
        openLocker();
        FileUtils.copyFile(file, new File(lockerName+'\\'+file.getName()));
        closeLocker();
        FileUtils.forceDelete(file);
    }

    public void removeFromLocker(String userName, String fileName) throws Exception {
        File file = new File(userName+"//"+lockerName+"//"+fileName);
        openLocker();
        FileUtils.copyFile(new File(userName+"//"+fileName),file);
        FileUtils.forceDelete(file);
        closeLocker();
    }

    public String getPassword(){
        return password;
    }

}
