
import java.util.*;
import java.io.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

class AdderImpl implements Adder
{

    public int add(int x, int y) throws RemoteException
    {
      System.out.println("Service invoked on " + x + " and " + y);
      return x + y;
    } // add

    public static void main(String args[])
    {

	     try {
         System.out.print("Installing security manager...");
         if (System.getSecurityManager() == null) {
           System.setSecurityManager(new SecurityManager());
         }
         System.out.println("done.");

         System.out.print("Creating the registry of RMI services...");
         LocateRegistry.createRegistry(1099);
         System.out.println("done.");

         System.out.print("Creating the remotely accessible adderImpl (and the stub)...");
         Adder adder = new AdderImpl();
         Adder stub = (Adder) UnicastRemoteObject.exportObject(adder, 0);
         System.out.println("done.");

         System.out.println("Registering the stub...");
         Registry registry = LocateRegistry.getRegistry();
         registry.rebind("addi", stub);
         System.out.println("done.");
       }
       catch(Exception re){
         re.printStackTrace();
       }
    }

} // AdderImpl
