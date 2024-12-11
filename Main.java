interface State {
    void selectTicket();
    void insertMoney(double amount);
    void dispenseTicket();
    void cancelTransaction();
}

class TicketMachine {
    private State idleState;
    private State waitingForMoneyState;
    private State moneyReceivedState;
    private State ticketDispensedState;
    private State transactionCanceledState;

    private State currentState;
    private double currentBalance = 0;
    private final double ticketPrice = 50;

    public TicketMachine() {
        idleState = new IdleState(this);
        waitingForMoneyState = new WaitingForMoneyState(this);
        moneyReceivedState = new MoneyReceivedState(this);
        ticketDispensedState = new TicketDispensedState(this);
        transactionCanceledState = new TransactionCanceledState(this);

        currentState = idleState;
    }

    public void setState(State state) {
        currentState = state;
    }

    public void selectTicket() {
        currentState.selectTicket();
    }

    public void insertMoney(double amount) {
        currentState.insertMoney(amount);
    }

    public void dispenseTicket() {
        currentState.dispenseTicket();
    }

    public void cancelTransaction() {
        currentState.cancelTransaction();
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public State getIdleState() {
        return idleState;
    }

    public State getWaitingForMoneyState() {
        return waitingForMoneyState;
    }

    public State getMoneyReceivedState() {
        return moneyReceivedState;
    }

    public State getTicketDispensedState() {
        return ticketDispensedState;
    }

    public State getTransactionCanceledState() {
        return transactionCanceledState;
    }
}

// IdleState class
class IdleState implements State {
    private TicketMachine machine;

    public IdleState(TicketMachine machine) {
        this.machine = machine;
    }

    @Override
    public void selectTicket() {
        System.out.println("Ticket selected. Please insert money.");
        machine.setState(machine.getWaitingForMoneyState());
    }

    @Override
    public void insertMoney(double amount) {
        System.out.println("Please select a ticket first.");
    }

    @Override
    public void dispenseTicket() {
        System.out.println("No ticket selected.");
    }

    @Override
    public void cancelTransaction() {
        System.out.println("No transaction to cancel.");
    }
}

// WaitingForMoneyState class
class WaitingForMoneyState implements State {
    private TicketMachine machine;

    public WaitingForMoneyState(TicketMachine machine) {
        this.machine = machine;
    }

    @Override
    public void selectTicket() {
        System.out.println("Ticket already selected.");
    }

    @Override
    public void insertMoney(double amount) {
        machine.setCurrentBalance(machine.getCurrentBalance() + amount);
        System.out.println("Money inserted: " + amount + ". Current balance: " + machine.getCurrentBalance());
        if (machine.getCurrentBalance() >= machine.getTicketPrice()) {
            machine.setState(machine.getMoneyReceivedState());
        }
    }

    @Override
    public void dispenseTicket() {
        System.out.println("Insert enough money to purchase the ticket.");
    }

    @Override
    public void cancelTransaction() {
        System.out.println("Transaction canceled. Returning to idle state.");
        machine.setCurrentBalance(0);
        machine.setState(machine.getTransactionCanceledState());
    }
}

// MoneyReceivedState class
class MoneyReceivedState implements State {
    private TicketMachine machine;

    public MoneyReceivedState(TicketMachine machine) {
        this.machine = machine;
    }

    @Override
    public void selectTicket() {
        System.out.println("Ticket already selected and money received.");
    }

    @Override
    public void insertMoney(double amount) {
        System.out.println("Money already received.");
    }

    @Override
    public void dispenseTicket() {
        System.out.println("Dispensing ticket...");
        machine.setCurrentBalance(machine.getCurrentBalance() - machine.getTicketPrice());
        machine.setState(machine.getTicketDispensedState());
    }

    @Override
    public void cancelTransaction() {
        System.out.println("Transaction canceled. Returning to idle state.");
        machine.setCurrentBalance(0);
        machine.setState(machine.getTransactionCanceledState());
    }
}

// TicketDispensedState class
class TicketDispensedState implements State {
    private TicketMachine machine;

    public TicketDispensedState(TicketMachine machine) {
        this.machine = machine;
    }

    @Override
    public void selectTicket() {
        System.out.println("Please wait, processing your ticket.");
    }

    @Override
    public void insertMoney(double amount) {
        System.out.println("Transaction already completed.");
    }

    @Override
    public void dispenseTicket() {
        System.out.println("Ticket already dispensed.");
    }

    @Override
    public void cancelTransaction() {
        System.out.println("Cannot cancel, ticket already dispensed.");
    }
}

// TransactionCanceledState class
class TransactionCanceledState implements State {
    private TicketMachine machine;

    public TransactionCanceledState(TicketMachine machine) {
        this.machine = machine;
    }

    @Override
    public void selectTicket() {
        System.out.println("Transaction canceled. Please start a new transaction.");
    }

    @Override
    public void insertMoney(double amount) {
        System.out.println("Transaction canceled. Please start a new transaction.");
    }

    @Override
    public void dispenseTicket() {
        System.out.println("No ticket to dispense. Transaction canceled.");
    }

    @Override
    public void cancelTransaction() {
        System.out.println("Transaction already canceled.");
    }
}

public class Main {
    public static void main(String[] args) {
        TicketMachine machine = new TicketMachine();

        // Simulate the process
        machine.selectTicket();
        machine.insertMoney(30);
        machine.insertMoney(20);
        machine.dispenseTicket();

        System.out.println("Process completed.");
    }
}
