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

public class TelaUsuario extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaUsuario
     */
    public TelaUsuario() {
        initComponents();
        conexao = ModuloConexao.conectar();
    }

    /*CRIAR QUERY - SETAR PARÂMETROS - EXECUTAR - TRABALHAR COM OS RESULTADOS */
    private void consultar() {
        try {
            String sql = "select * from dbpets.usuarios where idusuario=?";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtId.getText());
            rs = pst.executeQuery();

            if (rs.next()) {
                txtNome.setText(rs.getNString(2));
                txtLogin.setText(rs.getNString(3));
                txtSenha.setText(rs.getString(4));
                cboPerfil.setSelectedItem(rs.getString(5));

            } else {
                JOptionPane.showMessageDialog(null, "Este usuário não está cadastrado");
                //as linhas abaixo limpam os campos
                txtNome.setText(null);
                txtLogin.setText(null);
                txtSenha.setText(null);
                cboPerfil.setSelectedIndex(-1);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void adicionar() {
        try {
            String sql = "insert into dbpets.usuarios (idusuario, nome, login, senha, perfil) VALUES (?, ?, ?, ?, ?)";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtId.getText());
            pst.setString(2, txtNome.getText());
            pst.setString(3, txtLogin.getText());
            pst.setString(4, txtSenha.getText());
            pst.setString(5, (String) cboPerfil.getSelectedItem());

            int adicionado = pst.executeUpdate(); // create

            if (adicionado > 0) {
                JOptionPane.showMessageDialog(null, "Usuário " + txtNome.getText() + " cadastrado com sucesso");
                txtNome.setText(null);
                txtLogin.setText(null);
                txtSenha.setText(null);
                cboPerfil.setSelectedIndex(-1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir usuário: " + e.getMessage());
        }
    }

    private void atualizar() {
        try {
            String sql = "update dbpets.usuarios set nome=?,login=?,senha=?,perfil=? where idusuario=?";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNome.getText());
            pst.setString(2, txtLogin.getText());
            pst.setString(3, txtSenha.getText());
            pst.setString(4, (String) cboPerfil.getSelectedItem());
            pst.setString(5, txtId.getText());

            if (txtNome.getText().isEmpty() || txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios: nome, id");
            } else {
                int adicionado = pst.executeUpdate();  //update

                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário atualizado com sucesso");
                    txtNome.setText(null);
                    txtLogin.setText(null);
                    txtSenha.setText(null);
                    cboPerfil.setSelectedIndex(-1);
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
            if (deletar == 0) {
                String sql = "delete from dbpets.usuarios where idusuario=?";
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtId.getText());
                int excluido = pst.executeUpdate();
                if (excluido > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário excluído com sucesso");
                    txtLogin.setText(null);
                    txtNome.setText(null);
                    txtLogin.setText(null);
                    txtSenha.setText(null);
                    cboPerfil.setSelectedIndex(-1);
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

        lblId = new javax.swing.JLabel();
        lblNome = new javax.swing.JLabel();
        lblLogin = new javax.swing.JLabel();
        lblSenha = new javax.swing.JLabel();
        lblPerfil = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtNome = new javax.swing.JTextField();
        txtLogin = new javax.swing.JTextField();
        txtSenha = new javax.swing.JTextField();
        cboPerfil = new javax.swing.JComboBox<>();
        btnCreate = new javax.swing.JButton();
        btnRead = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Usuários");
        setPreferredSize(new java.awt.Dimension(451, 449));

        lblId.setText("Id");

        lblNome.setText("Nome");

        lblLogin.setText("Login");

        lblSenha.setText("Senha");

        lblPerfil.setText("Perfil");

        txtLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLoginActionPerformed(evt);
            }
        });

        txtSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSenhaActionPerformed(evt);
            }
        });

        cboPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "admin", "user", " " }));

        btnCreate.setIcon(new javax.swing.ImageIcon("C:\\Users\\ANDRESSA\\Documents\\GerenciaPets\\prjPets\\icones\\create.png")); // NOI18N
        btnCreate.setToolTipText("Adicionar");
        btnCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });

        btnRead.setIcon(new javax.swing.ImageIcon("C:\\Users\\ANDRESSA\\Documents\\GerenciaPets\\prjPets\\icones\\read.png")); // NOI18N
        btnRead.setToolTipText("Consultar");
        btnRead.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReadActionPerformed(evt);
            }
        });

        btnUpdate.setIcon(new javax.swing.ImageIcon("C:\\Users\\ANDRESSA\\Documents\\GerenciaPets\\prjPets\\icones\\update.png")); // NOI18N
        btnUpdate.setToolTipText("Atualizar");
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblPerfil)
                            .addComponent(lblSenha)
                            .addComponent(lblLogin)))
                    .addComponent(lblNome)
                    .addComponent(lblId))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtId)
                    .addComponent(txtNome)
                    .addComponent(txtLogin)
                    .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(214, 214, 214)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDelete)
                    .addComponent(btnUpdate)
                    .addComponent(btnCreate)
                    .addComponent(btnRead))
                .addContainerGap(96, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(102, 102, 102)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblId)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNome)
                            .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblLogin)
                            .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSenha)
                            .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(70, 70, 70)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPerfil)
                            .addComponent(cboPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCreate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRead)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnUpdate)
                        .addGap(18, 18, 18)
                        .addComponent(btnDelete)))
                .addContainerGap(132, Short.MAX_VALUE))
        );

        setBounds(0, 0, 630, 510);
    }// </editor-fold>//GEN-END:initComponents

    private void txtLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLoginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLoginActionPerformed

    private void txtSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSenhaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSenhaActionPerformed

    private void btnReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReadActionPerformed
        consultar();
    }//GEN-LAST:event_btnReadActionPerformed

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
        // TODO add your handling code here:
        adicionar();
    }//GEN-LAST:event_btnCreateActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        atualizar();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        deletar();
    }//GEN-LAST:event_btnDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRead;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cboPerfil;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblPerfil;
    private javax.swing.JLabel lblSenha;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtLogin;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtSenha;
    // End of variables declaration//GEN-END:variables
}
