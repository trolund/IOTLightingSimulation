package server;

import java.rmi.Naming;

public class Server {

    public static String url = "rmi://localhost:3000/print-server";

    public static void main(String[] arg) throws Exception {
        java.rmi.registry.LocateRegistry.createRegistry(3000);

        IPrintServer s = new PrintServer();

        Naming.rebind(url, s);
        System.out.println("print-server registered.");
    }
}
