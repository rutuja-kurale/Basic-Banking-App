package com.example.bankapp;

public class CustomerModel {
    String phone, name, bankBalance, sender, receiver, date, transaction_status;

    public CustomerModel() {
    }

    public CustomerModel(String phone, String name, String bankBalance) {
        this.phone = phone;
        this.name = name;
        this.bankBalance = bankBalance;
    }

    public CustomerModel(String sender, String receiver, String date,String bankBalance, String transaction_status) {
        this.sender = sender;
        this.receiver = receiver;
        this.date = date;
        this.bankBalance = bankBalance;
        this.transaction_status = transaction_status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(String bankBalance) {
        this.bankBalance = bankBalance;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransaction_status() {
        return transaction_status;
    }

    public void setTransaction_status(String transaction_status) {
        this.transaction_status = transaction_status;
    }
}
