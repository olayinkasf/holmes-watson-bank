/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.holmes.watson.bank.core;

/**
 *
 * @author Olayinka
 */
public enum Operation {

    CONSULT("View your account history"),
    DEPOSIT("Deposit Money"),
    TRANSFER("Transfer Money");

    String text;

    private Operation(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
