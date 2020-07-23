package com.dhimanstudio.the_killer_zone.model;

public class Wallet_history {
    private String amount;
    private String payment;
    private String paymentId;
    private String productinfo;
    private String txnid;

    public Wallet_history() {
    }

    public Wallet_history(String amount, String payment, String paymentId, String productinfo, String txnid) {
        this.amount = amount;
        this.payment = payment;
        this.paymentId = paymentId;
        this.productinfo = productinfo;
        this.txnid = txnid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getProductinfo() {
        return productinfo;
    }

    public void setProductinfo(String productinfo) {
        this.productinfo = productinfo;
    }

    public String getTxnid() {
        return txnid;
    }

    public void setTxnid(String txnid) {
        this.txnid = txnid;
    }
}
