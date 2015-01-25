/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.holmes.watson.bank.client.view;

import java.awt.CardLayout;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.event.ListDataListener;
import org.holmes.watson.bank.core.AccountListener;
import org.holmes.watson.bank.core.Operation;
import static org.holmes.watson.bank.core.Operation.*;
import org.holmes.watson.bank.core.TransactionListener;
import org.holmes.watson.bank.core.entity.Client;

/**
 *
 * @author Olayinka
 */
public class HomeView extends javax.swing.JPanel {

    public final static String TAG_NAME = "home.view";
    private final CardLayout cardLayout = new CardLayout();

    TransactionListener opsListener;
    AccountListener accountListener;
    Client client;

    public HomeView(TransactionListener opsListener, AccountListener accountListener) {
        initComponents();
        this.opsListener = opsListener;
        this.accountListener = accountListener;
        operationCards.setLayout(cardLayout);
        operationCards.add(new AccountView(), AccountView.TAG_NAME);
        operationCards.add(new DemandLoanView(), DemandLoanView.TAG_NAME);
        operationCards.add(new PayLoanView(), PayLoanView.TAG_NAME);
        operationCards.add(new TerminateLoanView(), TerminateLoanView.TAG_NAME);
        operationCards.add(new TransferView(), TransferView.TAG_NAME);
        cardLayout.show(operationCards, AccountView.TAG_NAME);
        client = ClientView.getClient();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        clientNameText = new javax.swing.JLabel();
        addressTet = new javax.swing.JLabel();
        opsDropDown = new javax.swing.JComboBox<Operation>();
        operationCards = new javax.swing.JPanel();

        setMaximumSize(new java.awt.Dimension(960, 540));
        setMinimumSize(new java.awt.Dimension(960, 540));

        clientNameText.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        clientNameText.setText("jLabel1");

        addressTet.setText("jLabel3");

        opsDropDown.setModel(new OpsDropDownModel());
        opsDropDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onOpsSelected(evt);
            }
        });

        operationCards.setMaximumSize(new java.awt.Dimension(900, 375));
        operationCards.setMinimumSize(new java.awt.Dimension(900, 375));
        operationCards.setPreferredSize(new java.awt.Dimension(900, 375));
        operationCards.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(clientNameText, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
                        .addComponent(addressTet, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(opsDropDown, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(operationCards, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(clientNameText, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(addressTet)
                .addGap(18, 18, 18)
                .addComponent(opsDropDown, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(operationCards, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void onOpsSelected(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onOpsSelected
        assert opsDropDown == (JComboBox) evt.getSource();
        Operation operation = (Operation) opsDropDown.getSelectedItem();
        switch (operation) {
            case CONSULT:
                cardLayout.show(operationCards, AccountView.TAG_NAME);
                break;
            case TRANSFER:
                cardLayout.show(operationCards, TransferView.TAG_NAME);
                break;
            case DEMAND_LOAN:
                cardLayout.show(operationCards, DemandLoanView.TAG_NAME);
                break;
            case PAY_LOAN:
                cardLayout.show(operationCards, PayLoanView.TAG_NAME);
                break;
            case TERMINATE_LOAN:
                cardLayout.show(operationCards, TerminateLoanView.TAG_NAME);
                break;
        }
    }//GEN-LAST:event_onOpsSelected


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addressTet;
    private javax.swing.JLabel clientNameText;
    private javax.swing.JPanel operationCards;
    private javax.swing.JComboBox<Operation> opsDropDown;
    // End of variables declaration//GEN-END:variables
private class OpsDropDownModel implements ComboBoxModel<Operation> {

        Operation[] operations = new Operation[]{CONSULT, TRANSFER, DEMAND_LOAN, TERMINATE_LOAN, PAY_LOAN};
        Operation selection = null;

        @Override
        public void setSelectedItem(Object anItem) {
            selection = (Operation) anItem;
        }

        @Override
        public Object getSelectedItem() {
            return selection;
        }

        @Override
        public int getSize() {
            return operations.length;
        }

        @Override
        public Operation getElementAt(int index) {
            return operations[index];
        }

        @Override
        public void addListDataListener(ListDataListener l) {

        }

        @Override
        public void removeListDataListener(ListDataListener l) {
        }
    }

}