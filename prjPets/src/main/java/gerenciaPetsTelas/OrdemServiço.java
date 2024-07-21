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
import net.proteanit.sql.DbUtils;  //usar o rs2xml
public class OrdemServiço extends javax.swing.JInternalFrame {
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    private String tipo;

    /**
     * Creates new form OrdemServiço
     */
    public OrdemServiço() {
        initComponents();
         conexao = ModuloConexao.conectar();
    }
    
    private void pesquisar_cliente(){
        String sql = "select idusuario as Id, nomeusuario as Nome, nomeusuariopet as Nome_do_pet, foneusuario as Telefone, emailusuario as Email,"
                + " endereço as Endereço from dbpets.clientes where nomeusuario like ?";
        try{
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtPesquisa.getText()+"%");
            rs = pst.executeQuery();
            
           tblClientes2.setModel(DbUtils.resultSetToTableModel(rs));
        } catch(Exception e){
            JOptionPane.showMessageDialog(null,"" );
        }
    }
     public void setar_campos(){
        int setar = tblClientes2.getSelectedRow();
        txtId.setText(tblClientes2.getModel().getValueAt(setar, 0).toString());
        
    }
     
    private void EmitirOs(){       
      
            if(!txtId.getText().isEmpty() && !txtPacotes.getText().isEmpty() && !txtDias.getText().isEmpty() && !cbSituacao.getSelectedItem().equals(" ") ){
                  try {String sql = "insert into dbpets.tbos (tipo, situacao, tipo_alimento, quantidade_pacotes, quantidade_dias,prescricao,idusuario) VALUES (?, ?, ?, ?, ?,?,?)";
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cbSituacao.getSelectedItem().toString());
            pst.setString(3, cbAlimento.getSelectedItem().toString());
            pst.setString(4, txtPacotes.getText());
            pst.setString(5, txtDias.getText());
            pst.setString(6, cbPrescricao.getSelectedItem().toString());
            pst.setString(7, txtId.getText());

            int adicionado = pst.executeUpdate(); // create

            if (adicionado > 0) {
                JOptionPane.showMessageDialog(null, "Ordem de serviço emitida com sucesso");
                cbAlimento.setSelectedIndex(-1);
                txtPacotes.setText(null);
                txtDias.setText(null);
                cbPrescricao.setSelectedIndex(-1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao emitir ordem de serviço: " + e.getMessage());
        }
            } else {
                JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios: id, quantidade de pacotes e dias");
            }
    } 
    
    private void pesquisar_os(){
        String num_os = JOptionPane.showInputDialog("Número da OS:");
        String sql = "select * from dbpets.tbos where os= "+ num_os;
        try{
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                txtNumeroOs.setText(rs.getString(1));
                txtData.setText(rs.getString(2));
                String rbtTipo = rs.getNString(3);
                if(rbtTipo.equals("Orçamento")){
                    btnOrçamento.setSelected(true);
                    tipo = "Orçamento";
                }else if(rbtTipo.equals("Ordem de serviço")){
                    btnOs.setSelected(true);
                    tipo = "Ordem de serviço";
                }
                cbSituacao.setSelectedItem(rs.getString(4));
                cbAlimento.setSelectedItem(rs.getString(5));
                txtPacotes.setText(rs.getString(6));
                txtDias.setText(rs.getString(7));
                cbPrescricao.setSelectedItem(rs.getString(8));
            } else {
                JOptionPane.showMessageDialog(null,"Número de OS não encontrada" );
            }
            
        } catch(Exception e){
            JOptionPane.showMessageDialog(null,"Número de OS não encontrada "+e.getMessage() );
        }
    }
    
private void atualizar_os() {
    if (txtPacotes.getText().isEmpty() || txtDias.getText().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios: quantidade de pacotes e dias");
        return;
    }

    String sql = "UPDATE dbpets.tbos SET tipo=?, situacao=?, tipo_alimento=?, quantidade_pacotes=?, quantidade_dias=?, prescricao=? WHERE os=?";

    try (PreparedStatement pst = conexao.prepareStatement(sql)) {
        pst.setString(1, tipo);
        pst.setString(2, cbSituacao.getSelectedItem().toString());
        pst.setString(3, cbAlimento.getSelectedItem().toString());
        pst.setString(4, txtPacotes.getText());
        pst.setString(5, txtDias.getText());
        pst.setString(6, cbPrescricao.getSelectedItem().toString());
        pst.setString(7, txtNumeroOs.getText());

        int adicionado = pst.executeUpdate();
        

        if (adicionado > 0) {
            JOptionPane.showMessageDialog(null, "Usuário atualizado com sucesso");
            txtPacotes.setText(null);
            txtDias.setText(null);
            cbSituacao.setSelectedIndex(-1);
            cbAlimento.setSelectedIndex(-1);
            cbPrescricao.setSelectedIndex(-1);
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Não foi possível realizar a atualização de OS: " + e.getMessage());
    }
}

 private void deletar_os() {
        Object[] options = {"Sim", "Não"};
        int deletar = JOptionPane.showOptionDialog(null, "Tem certeza que deseja deletar essa ordem de serviço?", "Atenção",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
        try {
            if (deletar == 0) { //se o usuário clicar em "sim"
                String sql = "delete from dbpets.tbos where os=?";
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtNumeroOs.getText());
                int excluido = pst.executeUpdate();
                if (excluido > 0) {
                    JOptionPane.showMessageDialog(null, "Ordem de serviço excluída com sucesso");
                    txtPacotes.setText(null);
            txtDias.setText(null);
            cbSituacao.setSelectedIndex(-1);
            cbAlimento.setSelectedIndex(-1);
            cbPrescricao.setSelectedIndex(-1);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível deletar essa OS: " + e.getMessage());
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNumeroOs = new javax.swing.JTextField();
        txtData = new javax.swing.JTextField();
        btnOrçamento = new javax.swing.JRadioButton();
        btnOs = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes2 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtPacotes = new javax.swing.JTextField();
        txtDias = new javax.swing.JTextField();
        btnAdicionar = new javax.swing.JButton();
        btnConsultar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnDeletar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        cbSituacao = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtPesquisa = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        cbPrescricao = new javax.swing.JComboBox<>();
        cbAlimento = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();

        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("N° OS");

        jLabel2.setText("Data");

        txtNumeroOs.setEditable(false);
        txtNumeroOs.setEnabled(false);

        txtData.setEditable(false);

        buttonGroup1.add(btnOrçamento);
        btnOrçamento.setText("Orçamento");
        btnOrçamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrçamentoActionPerformed(evt);
            }
        });

        buttonGroup1.add(btnOs);
        btnOs.setText("Ordem de serviço");
        btnOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnOrçamento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnOs)
                .addGap(49, 49, 49))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNumeroOs, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                    .addComponent(txtData))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumeroOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOrçamento)
                    .addComponent(btnOs))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        tblClientes2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Id", "Nome", "Telefone"
            }
        ));
        tblClientes2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientes2MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes2);

        jLabel5.setText("* Tipo de alimento:");

        jLabel7.setText("* Quantidade de pacotes:");

        jLabel8.setText("* Quantidade de dias:");

        jLabel9.setText("Tem prescrição?");

        btnAdicionar.setIcon(new javax.swing.ImageIcon("C:\\Users\\ANDRESSA\\Documents\\GerenciaPets\\prjPets\\icones\\create.png")); // NOI18N
        btnAdicionar.setToolTipText("Adicionar");
        btnAdicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        btnConsultar.setIcon(new javax.swing.ImageIcon("C:\\Users\\ANDRESSA\\Documents\\GerenciaPets\\prjPets\\icones\\read.png")); // NOI18N
        btnConsultar.setToolTipText("Consultar");
        btnConsultar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });

        btnEditar.setIcon(new javax.swing.ImageIcon("C:\\Users\\ANDRESSA\\Documents\\GerenciaPets\\prjPets\\icones\\update.png")); // NOI18N
        btnEditar.setToolTipText("Editar");
        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnDeletar.setIcon(new javax.swing.ImageIcon("C:\\Users\\ANDRESSA\\Documents\\GerenciaPets\\prjPets\\icones\\delete.png")); // NOI18N
        btnDeletar.setToolTipText("Deletar");
        btnDeletar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarActionPerformed(evt);
            }
        });

        jLabel10.setText("Situação");

        cbSituacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Produção", "Empacotando", "Pronto para entrega" }));

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setPreferredSize(new java.awt.Dimension(270, 119));

        jLabel3.setText("Cliente");

        txtPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisaKeyReleased(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon("C:\\Users\\ANDRESSA\\Documents\\GerenciaPets\\prjPets\\icones\\lupa (1).png")); // NOI18N

        jLabel6.setText("* Id ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(111, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtPesquisa)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        cbPrescricao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Sim", "Não" }));

        cbAlimento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Bovino", "Suíno", "Frango", "Peixe" }));

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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(106, 106, 106)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel5))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbAlimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtPacotes, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(64, 64, 64)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbPrescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDias, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(7, 7, 7)))
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(160, 160, 160)
                .addComponent(btnAdicionar)
                .addGap(18, 18, 18)
                .addComponent(btnConsultar)
                .addGap(18, 18, 18)
                .addComponent(btnEditar)
                .addGap(18, 18, 18)
                .addComponent(btnDeletar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(cbSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(cbPrescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cbAlimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addGap(18, 18, 18)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtPacotes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(txtDias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnConsultar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnEditar)
                    .addComponent(btnDeletar)
                    .addComponent(btnAdicionar))
                .addGap(50, 50, 50))
        );

        setBounds(0, 0, 635, 510);
    }// </editor-fold>//GEN-END:initComponents

    private void btnOrçamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrçamentoActionPerformed
        // TODO add your handling code here:
        tipo = "orçamento";
    }//GEN-LAST:event_btnOrçamentoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
       atualizar_os();
       
    }//GEN-LAST:event_btnEditarActionPerformed

    private void txtPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisaKeyReleased
        // TODO add your handling code here:
        pesquisar_cliente();
    }//GEN-LAST:event_txtPesquisaKeyReleased

    private void tblClientes2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientes2MouseClicked
        // TODO add your handling code here:
        setar_campos();
    }//GEN-LAST:event_tblClientes2MouseClicked

    private void btnOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsActionPerformed
        // TODO add your handling code here:
        tipo = "Ordem de serviço";
    }//GEN-LAST:event_btnOsActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        btnOrçamento.setSelected(true);
        tipo = "Orçamento";
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        // TODO add your handling code here:
        EmitirOs();
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        // TODO add your handling code here:
        pesquisar_os();
    }//GEN-LAST:event_btnConsultarActionPerformed

    private void btnDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarActionPerformed
        // TODO add your handling code here:
        deletar_os();
    }//GEN-LAST:event_btnDeletarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        OrdemServiço.this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnDeletar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JRadioButton btnOrçamento;
    private javax.swing.JRadioButton btnOs;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbAlimento;
    private javax.swing.JComboBox<String> cbPrescricao;
    private javax.swing.JComboBox<String> cbSituacao;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblClientes2;
    private javax.swing.JTextField txtData;
    private javax.swing.JTextField txtDias;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNumeroOs;
    private javax.swing.JTextField txtPacotes;
    private javax.swing.JTextField txtPesquisa;
    // End of variables declaration//GEN-END:variables
}
