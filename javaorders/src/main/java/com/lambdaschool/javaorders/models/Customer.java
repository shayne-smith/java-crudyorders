package com.lambdaschool.javaorders.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long custcode;

    private String custcity;
    private String custcountry;

    @Column(nullable = false, unique = true)
    private String custname;
    private String grade;

    @Transient
    public boolean hasvalueforopeningamt = false;
    private double openingamt;

    @Transient
    public boolean hasvalueforoutstandingamt = false;
    private double outstandingamt;

    @Transient
    public boolean hasvalueforpaymentamt = false;
    private double paymentamt;
    private String phone;

    @Transient
    public boolean hasvalueforreceiveamt = false;
    private double receiveamt;
    private String workingarea;

    @ManyToOne()
    @JoinColumn(name = "agentcode", nullable = false)
    @JsonIgnoreProperties(value = "customers")
    private Agent agent;

    @OneToMany(mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnoreProperties(value = "customers")
    List<Order> orders = new ArrayList<>();

    public Customer() {
    }

    public Customer(String custcity, String custcountry, String custname, String grade, double openingamt, double outstandingamt, double paymentamt, String phone, double receiveamt, String workingarea, Agent agent) {
        this.custcity = custcity;
        this.custcountry = custcountry;
        this.custname = custname;
        this.grade = grade;
        this.openingamt = openingamt;
        this.outstandingamt = outstandingamt;
        this.paymentamt = paymentamt;
        this.phone = phone;
        this.receiveamt = receiveamt;
        this.workingarea = workingarea;
        this.agent = agent;
    }

    public long getCustcode() {
        return custcode;
    }

    public void setCustcode(long custcode) {
        this.custcode = custcode;
    }

    public String getCustcity() {
        return custcity;
    }

    public void setCustcity(String custcity) {
        this.custcity = custcity;
    }

    public String getCustcountry() {
        return custcountry;
    }

    public void setCustcountry(String custcountry) {
        this.custcountry = custcountry;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public double getOpeningamt() {
        return openingamt;
    }

    public void setOpeningamt(double openingamt) {
        this.hasvalueforopeningamt = true;
        this.openingamt = openingamt;
    }

    public double getOutstandingamt() {
        return outstandingamt;
    }

    public void setOutstandingamt(double outstandingamt) {
        this.hasvalueforoutstandingamt = true;
        this.outstandingamt = outstandingamt;
    }

    public double getPaymentamt() {
        return paymentamt;
    }

    public void setPaymentamt(double paymentamt) {
        this.hasvalueforpaymentamt = true;
        this.paymentamt = paymentamt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getReceiveamt() {
        return receiveamt;
    }

    public void setReceiveamt(double receiveamt) {
        this.hasvalueforreceiveamt = true;
        this.receiveamt = receiveamt;
    }

    public String getWorkingarea() {
        return workingarea;
    }

    public void setWorkingarea(String workingarea) {
        this.workingarea = workingarea;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "custcode=" + custcode +
                ", custcity='" + custcity + '\'' +
                ", custcountry='" + custcountry + '\'' +
                ", custname='" + custname + '\'' +
                ", grade='" + grade + '\'' +
                ", openingamt=" + openingamt +
                ", outstandingamt=" + outstandingamt +
                ", paymentamt=" + paymentamt +
                ", phone='" + phone + '\'' +
                ", receiveamt=" + receiveamt +
                ", workingarea='" + workingarea + '\'' +
                ", agent=" + agent +
                ", orders=" + orders +
                '}';
    }
}
