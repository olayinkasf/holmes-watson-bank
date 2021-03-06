/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.holmes.watson.bank.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Olayinka
 */
@Entity
@Table(catalog = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
    @NamedQuery(name = "Account.findByAccountnum", query = "SELECT a FROM Account a WHERE a.accountnum = :accountnum"),
    @NamedQuery(name = "Account.findByAccountbalance", query = "SELECT a FROM Account a WHERE a.accountbalance = :accountbalance"),
    @NamedQuery(name = "Account.findByStatus", query = "SELECT a FROM Account a WHERE a.status = :status")})
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(nullable = false, length = 30)
    private String accountnum;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal accountbalance;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Integer status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountnum", fetch = FetchType.EAGER)
    private List<Transaction> transactionList;
    @JoinColumn(name = "CLIENTID", referencedColumnName = "CLIENTID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Client clientid;

    public static final int STATUS_OPEN = 0;
    public static final int STATUS_CLOSED = 1;

    public Account() {
    }

    public Account(String accountnum) {
        this.accountnum = accountnum;
    }

    public Account(String accountnum, BigDecimal accountbalance, Integer status) {
        this.accountnum = accountnum;
        this.accountbalance = accountbalance;
        this.status = status;
    }

    public String getAccountnum() {
        return accountnum;
    }

    public void setAccountnum(String accountnum) {
        this.accountnum = accountnum;
    }

    public BigDecimal getAccountbalance() {
        return accountbalance;
    }

    public void setAccountbalance(BigDecimal accountbalance) {
        this.accountbalance = accountbalance;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @XmlTransient
    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public Client getClientid() {
        return clientid;
    }

    public void setClientid(Client clientid) {
        this.clientid = clientid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accountnum != null ? accountnum.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.accountnum == null && other.accountnum != null) || (this.accountnum != null && !this.accountnum.equals(other.accountnum))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getAccountnum() + ", MAD " + getAccountbalance().toString();
    }

}
