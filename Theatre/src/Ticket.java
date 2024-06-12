//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//Theatre Booking Management System

public class Ticket {
    private int row;
    private int seat;
    private double price;
    private Person person;

    protected Ticket(int var1, int var2, double var3, String var5, String var6, String var7) {
        this.row = var1;
        this.seat = var2;
        this.price = var3;
        this.person = new Person(var5, var6, var7);
    }

    protected void print() {
        System.out.println();
        System.out.println("Name of the person: " + this.person.getName());
        System.out.println("Surname of the person: " + this.person.getSurname());
        System.out.println("Email of the person: " + this.person.getEmail());
        System.out.println("Row number: " + this.row);
        System.out.println("Seat number: " + this.seat);
        System.out.println("Price of seat: " + this.price);
    }

    protected int getRow() {
        return this.row;
    }

    protected int getSeat() {
        return this.seat;
    }

    protected double getPrice() {
        return this.price;
    }
}
