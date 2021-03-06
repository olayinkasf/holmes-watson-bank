/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.holmes.watson.bank.core;

import java.io.Serializable;

/**
 *
 * @author Olayinka
 */
public class Message implements Serializable{

    public static MessageBuilder builder(boolean status) {
        return new MessageBuilder(status);
    }

    boolean status = false;
    String message;
    String toDo;
    Object[] attachment;

    private void setStatus(boolean status) {
        this.status = status;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    private void setToDo(String toDo) {
        this.toDo = toDo;
    }

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getToDo() {
        return toDo;
    }

    public Object[] getAttachment() {
        return attachment;
    }

    private void setAttachment(Object... attachment) {
        this.attachment = attachment;
    }

    public static class MessageBuilder {

        Message message;

        public MessageBuilder(boolean status) {
            message = new Message();
            message.setStatus(status);
        }

        public MessageBuilder message(String message) {
            this.message.setMessage(message);
            return this;
        }

        public MessageBuilder toDo(String toDo) {
            this.message.setToDo(toDo);
            return this;
        }

        public MessageBuilder attachment(Object... attachment) {
            this.message.setAttachment(attachment);
            return this;
        }

        public Message build() {
            return message;
        }
    }

}
