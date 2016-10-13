package com.shriram.microservices.model.bill;

/**
 * Created by TSShriram on 13/10/2016.
 * This represents the final bill of a customer
 */
public class Bill {

    private double distanceTravelled;
    private double timeTravelled;
    private double totalAmount;

    public Bill(double distanceTravelled, double timeTravelled) {
        this.distanceTravelled = distanceTravelled;
        this.timeTravelled = timeTravelled;

        //to implement billing
        this.totalAmount = 0;
    }

    public double distanceTravelled() {
        return distanceTravelled;
    }


    public double timeTravelled() {
        return timeTravelled;
    }

    public double totalAmount() {
        return totalAmount;
    }

    public void totalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bill bill = (Bill) o;

        if (Double.compare(bill.distanceTravelled, distanceTravelled) != 0) return false;
        return Double.compare(bill.timeTravelled, timeTravelled) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(distanceTravelled);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(timeTravelled);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

}
