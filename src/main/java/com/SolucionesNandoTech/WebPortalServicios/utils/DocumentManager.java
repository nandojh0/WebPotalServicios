package com.SolucionesNandoTech.WebPortalServicios.utils;


import com.SolucionesNandoTech.WebPortalServicios.services.GetIpAddress;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DocumentManager {

    private static String OS = System.getProperty("os.name").toLowerCase();

    private static int thread = 0;
    private transient int identifier = 0;

    @Autowired
    private GetIpAddress ipAddres;

    public String getCurrentDate() {
        Date fecha = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        final String FechaActual = sdf.format(fecha);
        return FechaActual;
    }

    public String getCurrentTime() {
        final Time currentHora = new Time(new Date().getTime());
        return currentHora.toString();
    }

    private String getDocumentName(int id) {
        String path;
        String name;

        if (isUnix()) {
            path = System.getProperty("catalina.base") + File.separator + "webapps"
                    + File.separator + "log_WebPortalServicios" + File.separator;
        } else {
            path = System.getProperty("user.dir") + File.separator + "webapps"
                    + File.separator + "log_WebPortalServicios" + File.separator;
        }

        File file = new File(path);

        if (!file.exists()) {
            file.mkdirs();
        }

        switch (id) {
            case 0:
                name = path + getCurrentDate() + "_Transactions.txt";
                break;
            case 1:
                name = path + getCurrentDate() + "_Exceptions.txt";
                break;
            default:
                name = path + getCurrentDate() + "_Transactions.txt";
                break;
        }

        return name;
    }

    public void write(final int id, final String message) {
        synchronized (this) {
            String fileName = getDocumentName(id);

            try ( FileWriter fileWriter = new FileWriter(fileName, true);  BufferedWriter bufferWriter = new BufferedWriter(fileWriter);) {
                bufferWriter.write(getIdentifier() + "  Hora : " + getCurrentTime() + "  IP :  " + ipAddres.getIP() + "   " + message);
                bufferWriter.newLine();
                setPermissions(fileName);

            } catch (IOException ex) {
                Logger.getLogger(DocumentManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void setPermissions(String filePath) throws IOException {
        if (isUnix()) {
            File fileLog = new File(filePath);

            Set<PosixFilePermission> perms = new HashSet<>();
            perms.add(PosixFilePermission.OWNER_READ);
            perms.add(PosixFilePermission.OWNER_WRITE);
            perms.add(PosixFilePermission.OWNER_EXECUTE);
            perms.add(PosixFilePermission.GROUP_READ);
            perms.add(PosixFilePermission.GROUP_WRITE);
            perms.add(PosixFilePermission.GROUP_EXECUTE);
            /*
                 * Documentacion Seguridad: La linea de abajo es necesaria para
                 * que Plutón pueda acceder a los logs del desarrollo en el
                 * ambiente de producción.
             */
            perms.add(PosixFilePermission.OTHERS_READ);
            Files.setPosixFilePermissions(fileLog.toPath(), perms);
        }
    }

    public static boolean isUnix() {
        return (OS.contains("nix")
                || OS.contains("nux")
                || OS.indexOf("aix") > 0);
    }

    public int getIdentifier() {
        return identifier;
    }

    public void increaseThread() {
        if (thread == 1000) {
            thread = 0;
        } else {
            thread += 1;
        }
        this.identifier = thread;
    }

}
