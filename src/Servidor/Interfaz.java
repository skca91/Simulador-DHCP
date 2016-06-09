
package Servidor;


import java.io.IOException;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.net.SocketException;
import java.net.UnknownHostException;
/**
 *
 * @author Stephanie Katherine Correa Alcántara C.I. 19.598.574
 */
public class Interfaz extends javax.swing.JFrame {

    SubRedU generador;
    ArrayList listaDirecciones;
    ArrayList DireccionesUsadas;
    ArrayList DireccionesReservadas;
    DefaultListModel modeloIPs;
    DefaultListModel modeloAsignadas;
    DefaultListModel modeloSolicitudes;
    public ConexionUDP conexion;
    public Cliente cliente[];
    int numClientes;

    public Interfaz() throws UnknownHostException, SocketException {
        super("Servidor-DHCP");
        initComponents();
        this.conexion = new ConexionUDP(this);
        this.numClientes = 100;
       // this.cliente = new Cliente[numClientes];
        listaDirecciones = new ArrayList();
        DireccionesUsadas = new ArrayList();
        DireccionesReservadas = new ArrayList();
        modeloIPs = new DefaultListModel();
        modeloAsignadas = new DefaultListModel();
        Barrita.setValue(0);
    
    }

    public String RetornarIP() {
        return IP.getText();
    }

    public String RetornarMascara() {
        return Subred.getText();
    }
    public String RetornarAmbito(){
        return Ambito.getText();
    }
    
    public String RetornarMAC(){
        return MAC.getText();
    }
   
    public void ActualizarModeloAsignadas(DefaultListModel m){
        IpsAsignadas.setModel(m);
    }
    public void ActualizarModeloIps(DefaultListModel m){
        ListaIPs.setModel(m);
    }

     protected boolean alreadyInList(String ip) {
        return modeloSolicitudes.contains(ip);
    }

    public ConexionUDP getInterfaz() {
        return conexion;
    }
 
     public String DireccionDisponible() {
        String direccion = listaDirecciones.get(0).toString();
        return direccion;
    }
    public void Recibir(DatagramPacket entrada) throws IOException, InterruptedException {

        
        String name = new String(entrada.getData());
//      FORMATO DEL PAQUETE ENTRADA:
//      Descripcion-Nombre-MAC-id
        
        String data[] = name.split("-");
        int id,i=0;
        Barrita.setValue(0);
        if (data[0].compareTo("Solicitar") == 0) {
            
          JOptionPane.showMessageDialog(this, "Cliente Solicito");
             Barrita.setValue(25);
             
            if (this.conexion.Buscar_MAC(data[2], 1) != 0) {
                //SI ES DIFERENTE DE CERO QUIERE DECIR QUE NO ENCONTRO LA MAC
                String mensaje = data[0].concat("-"+data[2]);
                if (data[2].equals("") || alreadyInList(data[2])) {
                    return;
                }
                
                this.getInterfaz().setMACcliente(entrada.getAddress().toString());
                int index = listaSolicitudes.getSelectedIndex();
                if (index == -1) {
                    index = 0;
                } else {
                    index++;
                }
                
                modeloSolicitudes.addElement(mensaje);
                JOptionPane.showMessageDialog(this, "IP enviada");
                Barrita.setValue(50);
                
                this.conexion.Asignar_IP(entrada.getAddress(), data[1], data[2], data[3]);
                
            }
            //ENVIO LA DIRECCION IP AL CLIENTE
        } else if (data[0].compareTo("Aceptar") == 0) {
            int k = this.conexion.Buscar_MAC(data[2], 5);
            int index =IpsAsignadas.getSelectedIndex();
            if (index == -1) {
                index = 0;
            } else {
                index++;
            }
            JOptionPane.showMessageDialog(this, "Aceptacion de la IP");
            Barrita.setValue(75);
            String conectar = data[2]+"-"+this.conexion.getCliente()[k].getIP();
            
            System.out.println("Aceptado");
            modeloAsignadas.addElement(conectar);
            ActualizarModeloAsignadas(modeloAsignadas);
            JOptionPane.showMessageDialog(this, "Conectado");
            Barrita.setValue(100);
            this.conexion.Enviar("Steph-Aceptado-"+data[2]+"-"+data[3]+"-"+RetornarIP()+"-"+RetornarMAC(), entrada.getAddress());
            
            
        } else if (data[0].compareTo("Desconectar") == 0) {
            //System.out.println(data[0]);
            this.conexion.Buscar_MAC(this.getInterfaz().getMACcliente(), 4);
            String desconectar = data[2]+"-Desconectado";
            modeloAsignadas.addElement(desconectar);
            ActualizarModeloAsignadas(modeloAsignadas);
            Barrita.setValue(0);
            this.conexion.Enviar("Steph-Desconectado-"+data[2]+"-"+data[3]+"-hola", entrada.getAddress());
        }else if (data[0].compareTo("Rechazar") == 0){

            this.conexion.Buscar_MAC(this.getInterfaz().getMACcliente(), 4);
            String rechazar = data[2]+"-Rechazo la asignacion";
            Barrita.setValue(0);
            modeloSolicitudes.addElement(rechazar);
        }
    }

    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Ambito = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        MAC = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        IP = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        Subred = new javax.swing.JTextField();
        Generar = new javax.swing.JButton();
        Barrita = new javax.swing.JProgressBar();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ListaIPs = new javax.swing.JList();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        IpsAsignadas = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        listaSolicitudes = new javax.swing.JList();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Servidor DHCP");
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(51, 51, 51));
        setBounds(new java.awt.Rectangle(400, 90, 250, 300));
        setResizable(false);

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Información de Configuración");

        jLabel2.setText("Nombre del Servidor");

        Ambito.setEditable(false);
        Ambito.setText("Steph");
        Ambito.setName("Ambito"); // NOI18N

        jLabel3.setText("Mac");

        MAC.setEditable(false);
        MAC.setText("f4:ec:38:e1:64:ee");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(MAC, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                            .addComponent(Ambito, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE))))
                .addGap(102, 102, 102))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(Ambito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(MAC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel4.setText("IP");

        IP.setEditable(false);
        IP.setText("192.169.65.1");

        jLabel5.setText("Máscara de Subred");

        Subred.setEditable(false);
        Subred.setText("255.255.255.0");

        Generar.setText("Generar");
        Generar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenerarActionPerformed(evt);
            }
        });

        Barrita.setBackground(new java.awt.Color(255, 255, 255));
        Barrita.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Barrita.setCursor(new java.awt.Cursor(java.awt.Cursor.NW_RESIZE_CURSOR));

        jLabel8.setText("Barra de Estado Conexión");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(Barrita, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(IP, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(Subred, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(Generar)))))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(IP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Subred, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Generar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Barrita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel6.setText("IPs Disponibles");

        jScrollPane1.setViewportView(ListaIPs);

        jLabel7.setText("Ips Asignadas");

        jScrollPane2.setViewportView(IpsAsignadas);

        modeloSolicitudes = new DefaultListModel();
        listaSolicitudes.setModel(modeloSolicitudes);
        jScrollPane3.setViewportView(listaSolicitudes);

        jLabel9.setText("Solicitudes");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(54, 54, 54))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel10.setForeground(new java.awt.Color(153, 0, 51));
        jLabel10.setText("Stephanie Correa");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(161, 161, 161)
                        .addComponent(jLabel10)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void GenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenerarActionPerformed

       if (!(IP.getText().equals("")) && !(Subred.getText().equals(""))) {

            for (int i = 0; i < listaDirecciones.size(); i++) {
                listaDirecciones.remove(i);
                modeloIPs.remove(i);
            }
            for (int i = 0; i < DireccionesUsadas.size(); i++) {
                DireccionesUsadas.remove(i);
                modeloAsignadas.remove(i);
            }

            generador = new SubRedU(IP.getText(), Subred.getText());
            String[] list = generador.RetornarDirecciones();
            int j=0;
            for (int i = 0; i < list.length; i++) {
                listaDirecciones.add(list[i]);
                modeloIPs.addElement(list[i]);
                
            }
            ListaIPs.setModel(modeloIPs);
            
        }
       else{
           JOptionPane.showMessageDialog(this, "Ingrese los datos");
       }
        
    }//GEN-LAST:event_GenerarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
               Interfaz conexionUDP = null;
               //ConexionUDP conexion = null;
                try {
                    conexionUDP = new Interfaz();
                } catch (UnknownHostException ex) {
                    Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SocketException ex) {
                    Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
                }
                    conexionUDP.setVisible(true);
                try {
                    conexionUDP.getInterfaz().Conectar();
                } catch (IOException ex) {
                    Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
                }
               
                }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Ambito;
    private javax.swing.JProgressBar Barrita;
    private javax.swing.JButton Generar;
    private javax.swing.JTextField IP;
    private javax.swing.JList IpsAsignadas;
    private javax.swing.JList ListaIPs;
    private javax.swing.JTextField MAC;
    private javax.swing.JTextField Subred;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList listaSolicitudes;
    // End of variables declaration//GEN-END:variables
}
