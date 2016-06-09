package Servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Stephanie Katherine Correa Alcántara C.I. 19.598.574
 */
public class ConexionUDP{

    private DatagramSocket socketSalida = null;
    //private DatagramPacket entrada;
    private DatagramPacket salida;
    private byte buffer[];
    private int puertoSalida;
    private int cantBytes, numClientes;
    private Interfaz servidorUDP;
    private Cliente cliente[];
    private String MACcliente;

    ConexionUDP(Interfaz servidor) throws SocketException  {
       
        this.servidorUDP = servidor;
        this.cantBytes = 512;
        this.numClientes = 100;
        this.cliente = new Cliente[numClientes];
        this.socketSalida = new DatagramSocket();
        this.buffer = new byte[cantBytes];
        this.puertoSalida = 6800;
    }

    public String getMACcliente() {
        return MACcliente;
    }

    public void setMACcliente(String MACcliente) {
        this.MACcliente = MACcliente;
    }

    public Cliente[] getCliente() {
        return cliente;
    }

    public void setCliente(Cliente[] cliente) {
        this.cliente = cliente;
    }


    
    public void Conectar() throws IOException{
        
        System.out.println("Servidor disponible");
        Recibir();
        
    }

  
    public void Asignar_IP(InetAddress IP, String Nombre, String MAC, String id) throws IOException {
        int i = 0;
        String ip = servidorUDP.listaDirecciones.get(0).toString();
        this.cliente[i] = new Cliente();
        this.cliente[i].setIP(ip);
       // if(this.cliente[i].getIP())
        while (this.cliente[i] != null) {
            if (this.cliente[i].getDisponible() == 0) {
                System.out.println("cliente "+cliente[i].getIP());
                this.cliente[i].setNombre(Nombre);
                this.cliente[i].setMAC(MAC);
                this.cliente[i].setId(id);
                String mensaje = "Steph-"+"Asignacion-"+this.cliente[i].getIP()+"-"+id+"-hola";
                System.out.println(mensaje);
                this.Enviar(mensaje, IP);
                this.cliente[i].setDisponible(1);
                i++;
                break;
            }
            
        }
        servidorUDP.listaDirecciones.remove(0);
    }

    public void Buscar_IP(String mensaje) {
        int i = 0;
        while (this.cliente[i] != null) {
            if (this.cliente[i].getIP().compareTo(mensaje) == 0) {
                this.cliente[i].setDisponible(0);
                this.cliente[i].setMAC("");
                break;
            }
            i++;
        }
    }

    public int Buscar_MAC(String mensaje, int num) {
        int i = 0;
        while (this.cliente[i] != null) {
            switch (num) {
                case 1:
                    if (this.cliente[i].getMAC().compareTo(mensaje) == 0) {
                        return 0;
                    }
                    break;
                case 2:
                    if (this.cliente[i].getMAC().compareTo(mensaje) == 0) {
                        this.cliente[i].setDisponible(2);
                        return 2;
                    }
                    break;
                case 3:
                    if (this.cliente[i].getIP().compareTo(mensaje) == 0) {
                        return i;
                    }
                    break;
                case 4:
                    if (this.cliente[i].getMAC().compareTo(mensaje) == 0) {
                        this.cliente[i].setDisponible(0);
                        this.cliente[i].setMAC("");
                        this.cliente[i].setNombre("");
                        this.cliente[i].setId("");
                        this.cliente[i].setIP("");
                        return 4;
                    }
                    break;
                 case 5:
                    if (this.cliente[i].getMAC().compareTo(mensaje) == 0) {
                        return i;
                    }
                    break;
            }

            i++;
        }
        return 6;
    }

    public void Enviar(String mensaje, InetAddress ip) throws IOException  {
        mensaje.getBytes(0, mensaje.length(), buffer, 0);
        salida = new DatagramPacket(buffer, mensaje.length(), ip, puertoSalida);
        socketSalida.send(salida);
    }

    public void enviarAcceptacion(String mensaje, InetAddress ip) throws IOException {
        mensaje.getBytes(0, mensaje.length(), buffer, 0);
        salida = new DatagramPacket(buffer, mensaje.length(), ip, puertoSalida);
        socketSalida.send(salida);
    }

    public void Recibir() {
        System.out.println("Prueba de sockets UDP (servidor)");

        new Thread(new Runnable() {

            public void run() {
                DatagramSocket socketEntrada = null;
                boolean fin = false;
                System.out.print("Creando socket... ");
                try {
                    socketEntrada = new DatagramSocket(3000);
                } catch (SocketException ex) {
                    Logger.getLogger(ConexionUDP.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("ok");
                System.out.println("Recibiendo mensajes... ");
                do {
                    byte[] mensaje_bytes = new byte[256];
                    DatagramPacket paquete = new DatagramPacket(mensaje_bytes, 256);
                    try {
                        socketEntrada.receive(paquete);
                    } catch (IOException ex) {
                        Logger.getLogger(ConexionUDP.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    String mensaje = "";
                    mensaje = new String(mensaje_bytes);
                    System.out.println(paquete.getAddress() + ": " + mensaje);
                    if (mensaje.startsWith("fin")) {
                        fin = true;
                    }
                    try {
                        try {
                            servidorUDP.Recibir(paquete);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ConexionUDP.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ConexionUDP.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ConexionUDP.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   
                } while (!fin);
            }
        }).start();
    }
}
