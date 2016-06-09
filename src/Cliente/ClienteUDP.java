
package Cliente;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
/**
 *
 * @author Stephanie Katherine Correa Alcántara C.I. 19.598.574
 */
public class ClienteUDP extends javax.swing.JFrame {

    private Conexion conexionUDP;
    private Conectado conectado;
    private String Nombre, IP, MAC;
    private int id, estado;

    public ClienteUDP() throws UnknownHostException {
        super("Cliente");
        this.estado = 0;
        initComponents();
        this.conexionUDP = new Conexion(this);
        this.conectado = new Conectado(this);
        this.main();
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void Recibir(DatagramPacket entrada) {

        String name = new String(entrada.getData());
        name = name.substring(0, entrada.getLength());
        String data[] = name.split("-");
       
        if (data[1].compareTo("Aceptado") == 0) {
            //DESCONECTO LOS HILOS DEL CLIENTE UDP
            this.setVisible(false);
            System.out.println("Cerrado");
            this.conectado.setVisible(true);
            System.out.println("ip clienteUDP "+this.getIP());
            this.conectado.getIp().setText(this.getIP());
            this.conectado.getMac().setText(this.getMAC());
            this.conectado.getIp_ser().setText(data[4]);
            this.conectado.getname().setText(data[0]);
            this.conectado.getMac_ser().setText(data[5]);
           
            
           
        } else if (data[1].compareTo("Desconectado") == 0) {

            //JOptionPane.showMessageDialog(this, "Desconectado");
            this.conectado.setVisible(false);
        } else {

            String mensaje = data[0].concat("-" + data[2]);
            conexionUDP.setIPservidor(entrada.getAddress());
            System.out.println("mensaje "+entrada.getAddress());
            this.IP = data[1];
            String msn[] = mensaje.split("-");

            int index = listaClientes.getSelectedIndex();
            if (index == -1) {
                index = 0;
            } else {
                index++;
            }

            modelo.addElement(mensaje);
        }
    }

    protected boolean alreadyInList(String ip) {
        return modelo.contains(ip);
    }

    public Conexion getConexionUDP() {
        return conexionUDP;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        listaClientes = new javax.swing.JList();
        Aceptar = new javax.swing.JButton();
        Rechazar = new javax.swing.JButton();
        cerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(10, 300, 200, 200));
        setResizable(false);

        modelo = new DefaultListModel();
        listaClientes.setModel(modelo);
        jScrollPane1.setViewportView(listaClientes);

        Aceptar.setText("Aceptar");
        Aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarActionPerformed(evt);
            }
        });

        Rechazar.setText("Rechazar");
        Rechazar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RechazarActionPerformed(evt);
            }
        });

        cerrar.setText("Reiniciar / Apagar");
        cerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(Aceptar)
                        .addGap(18, 18, 18)
                        .addComponent(Rechazar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Aceptar)
                    .addComponent(Rechazar)
                    .addComponent(cerrar))
                .addGap(36, 36, 36))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarActionPerformed

        int i;
        i = listaClientes.getSelectedIndex();
        String mensaje;
        mensaje = (String) modelo.getElementAt(i);
        String data[] = mensaje.split("-");
        String num = Integer.toString(id);
        
        String mensajedeEnvio = "Aceptar-" + Nombre + "-" + MAC + "-" + num+"-hola";
        System.out.println("envio "+mensajedeEnvio);
        this.setIP(data[1]);
        this.conexionUDP.setIPasignada(data[1]);
        try {
            conexionUDP.Enviar(mensajedeEnvio, this.getConexionUDP().getIPservidor());
            System.out.println(this.getConexionUDP().getIPservidor());
            System.out.println(mensaje);
        }catch (IOException ex) {
            Logger.getLogger(ClienteUDP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_AceptarActionPerformed

    private void RechazarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RechazarActionPerformed

        int i;
        i = listaClientes.getSelectedIndex();
        String mensaje;
        mensaje = (String) modelo.getElementAt(i);
        String data[] = mensaje.split("-");
        String num = Integer.toString(id);
        String mensajedeEnvio = "Rechazar-" + Nombre + "-" + MAC + "-" + num+"-hola";
        this.conexionUDP.setIPasignada(data[1]);
        try {
            conexionUDP.Enviar(mensajedeEnvio, this.getConexionUDP().getIPservidor());
            System.out.println(this.getConexionUDP().getIPservidor());
            System.out.println(mensaje);
            this.setVisible(false);
        }catch (IOException ex) {
            Logger.getLogger(ClienteUDP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_RechazarActionPerformed

    private void cerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cerrarActionPerformed

        this.setVisible(false);
        this.estado = 0;
    }//GEN-LAST:event_cerrarActionPerformed

    public void main() {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    setVisible(true);
                    conexionUDP.Conectar();
                } catch (UnknownHostException ex) {
                    Logger.getLogger(ClienteUDP.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ClienteUDP.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Aceptar;
    private javax.swing.JButton Rechazar;
    private javax.swing.JButton cerrar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList listaClientes;
    private DefaultListModel modelo;
    // End of variables declaration//GEN-END:variables
}
