/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package gerenciaPetsTelas;

/**
 *
 * @author ANDRESSA
 */
import java.sql.*;
import GerenciaPetsDados.ModuloConexao;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;


public class TelaCliente extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaCliente
     */
    public TelaCliente() {
        initComponents();
        conexao = ModuloConexao.conectar();
    }
     
    private void adicionar() {
        try {
            String sql = "insert into dbpets.clientes( nomeusuario, nomeusuariopet,foneusuario, emailusuario,endereço) VALUES ( ?, ?, ?, ?,?)";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNome.getText());
            pst.setString(2, txtNomePet.getText());
            pst.setString(3, txtTelefone.getText());
            pst.setString(4, txtEmail.getText());
            pst.setString(5, txtEndereço.getText());

            int adicionado = pst.executeUpdate(); // create

            if (adicionado > 0) {
                JOptionPane.showMessageDialog(null, "Cliente " + txtNome.getText() + " cadastrado(a) com sucesso");
                txtNome.setText(null);
                txtNomePet.setText(null);
                txtTelefone.setText(null);
                txtEmail.setText(null);
                txtEndereço.setText(null);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir cliente: " + e.getMessage());
        }
    }
    
    private void pesquisar_cliente() {
        String sql = "select idusuario as id, nomeusuario as nome, nomeusuariopet as nome_pet, foneusuario as telefone, emailusuario as email from dbpets.clientes where nomeusuario like ?";
        try{
            pst = conexao.prepareStatement(sql);
            pst.setString(1,txtPesquisa.getText() + "%" );
            rs = pst.executeQuery();
            
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao buscar cliente" + e.getMessage());
        }
    }
    
    public void setar_campos(){
        int setar = tblClientes.getSelectedRow();
        txtIdCliente.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
        txtNome.setText(tblClientes.getModel().getValueAt(setar, 1).toString());
        txtNomePet.setText(tblClientes.getModel().getValueAt(setar, 2).toString());
        txtTelefone.setText(tblClientes.getModel().getValueAt(setar, 3).toString());
        txtEmail.setText(tblClientes.getModel().getValueAt(setar, 4).toString());
        txtEndereço.setText(tblClientes.getModel().getValueAt(setar, 5).toString());
        
        btnAdicionar.enableInputMethods(false);
    }
    
     private void atualizar() {
        try {
            String sql = "update dbpets.clientes set nomeusuario=?,nomeusuariopet=?,foneusuario=?, emailusuario=?, endereço=? where idusuario=?";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNome.getText());
            pst.setString(2, txtNomePet.getText());
            pst.setString(3, txtTelefone.getText());
            pst.setString(4, txtEmail.getText());
             pst.setString(5, txtEndereço.getText());
            pst.setString(6, txtIdCliente.getText());
             

            if (txtNome.getText().isEmpty() || txtIdCliente.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios: nome, id ");
            } else {
                int adicionado = pst.executeUpdate();  //update

                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário atualizado com sucesso");
                    txtIdCliente.setText(null);
                    txtNome.setText(null);
                    txtNomePet.setText(null);
                    txtTelefone.setText(null);
                    txtEmail.setText(null);
                    txtEndereço.setText(null);
                     btnAdicionar.enableInputMethods(true);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possivel realizar a atualização" + e.getMessage());
        }
    }
     
     private void deletar() {
        Object[] options = {"Sim", "Não"};
        int deletar = JOptionPane.showOptionDialog(null, "Tem certeza que deseja deletar esse usuário?", "Atenção",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
        try {
            if (deletar == 0) { //se o usuário clicar em "sim"
                String sql = "delete from dbpets.clientes where idusuario=?";
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtIdCliente.getText());
                int excluido = pst.executeUpdate();
                if (excluido > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário excluído com sucesso");
                    txtIdCliente.setText(null);
                    txtNome.setText(null);
                    txtNomePet.setText(null);
                    txtTelefone.setText(null);
                    txtEmail.setText(null);
                    txtEndereço.setText(null);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível deletar: " + e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblNome = new javax.swing.JLabel();
        lblNomePet = new javax.swing.JLabel();
        lblTelefone = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        txtNomePet = new javax.swing.JTextField();
        txtTelefone = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        btnAdicionar = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        txtPesquisa = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        btnLupa = new javax.swing.JButton();
        lblEndereço = new javax.swing.JLabel();
        txtEndereço = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtIdCliente = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setTitle("Cliente");
        setToolTipText("");

        lblNome.setText("Nome");

        lblNomePet.setText("Nome do pet");

        lblTelefone.setText("Telefone");

        lblEmail.setText("E-mail");

        btnAdicionar.setIcon(new javax.swing.ImageIcon("C:\\Users\\ANDRESSA\\Documents\\GerenciaPets\\prjPets\\icones\\create.png")); // NOI18N
        btnAdicionar.setToolTipText("Adicionar");
        btnAdicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdicionar.setName("Adicionar"); // NOI18N
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        btnUpdate.setIcon(new javax.swing.ImageIcon("C:\\Users\\ANDRESSA\\Documents\\GerenciaPets\\prjPets\\icones\\update.png")); // NOI18N
        btnUpdate.setToolTipText("Editar");
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon("C:\\Users\\ANDRESSA\\Documents\\GerenciaPets\\prjPets\\icones\\delete.png")); // NOI18N
        btnDelete.setToolTipText("Deletar");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        txtPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesquisaActionPerformed(evt);
            }
        });
        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisaKeyReleased(evt);
            }
        });

        tblClientes = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Nome", "Nome do pet", "Telefone", "E-mail"
            }
        ));
        tblClientes.setFocusable(false);
        tblClientes.getTableHeader().setResizingAllowed(false);
        tblClientes.getTableHeader().setReorderingAllowed(false);
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        btnLupa.setIcon(new javax.swing.ImageIcon("C:\\Users\\ANDRESSA\\Documents\\GerenciaPets\\prjPets\\icones\\lupaa.png")); // NOI18N
        btnLupa.setFocusCycleRoot(true);

        lblEndereço.setText("Endereço");

        txtEndereço.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEndereçoActionPerformed(evt);
            }
        });

        jLabel1.setText("Id");

        txtIdCliente.setEnabled(false);

        jButton1.setIcon(new javax.swing.ImageIcon("C:\\Users\\ANDRESSA\\Documents\\GerenciaPets\\prjPets\\icones\\fechar (1).png")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAdicionar)
                .addGap(98, 98, 98)
                .addComponent(btnUpdate)
                .addGap(90, 90, 90)
                .addComponent(btnDelete)
                .addGap(141, 141, 141))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLupa, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(189, 189, 189)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblNomePet)
                                .addComponent(lblNome, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(lblTelefone)
                            .addComponent(lblEmail)
                            .addComponent(lblEndereço))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtEmail)
                            .addComponent(txtTelefone)
                            .addComponent(txtNomePet)
                            .addComponent(txtNome)
                            .addComponent(txtEndereço, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                            .addComponent(txtIdCliente))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(btnLupa, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNome)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNomePet)
                    .addComponent(txtNomePet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTelefone))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEndereço)
                    .addComponent(txtEndereço, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAdicionar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnDelete, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        setBounds(0, 0, 630, 503);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        // TODO add your handling code here:
        adicionar();
      
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void txtEndereçoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEndereçoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEndereçoActionPerformed

    private void txtPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisaActionPerformed
        // TODO add your handling code here:
      
    }//GEN-LAST:event_txtPesquisaActionPerformed

    private void txtPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyReleased
        // TODO add your handling code here:
        //keyreleased = enquanto estiver digitando
        pesquisar_cliente();
    }//GEN-LAST:event_txtPesquisaKeyReleased

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        // TODO add your handling code here:
        setar_campos();
    }//GEN-LAST:event_tblClientesMouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        atualizar();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        deletar();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        TelaCliente.this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnLupa;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEndereço;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblNomePet;
    private javax.swing.JLabel lblTelefone;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEndereço;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtNomePet;
    private javax.swing.JTextField txtPesquisa;
    private javax.swing.JTextField txtTelefone;
    // End of variables declaration//GEN-END:variables
}
