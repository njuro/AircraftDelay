package cz.closeit.homework;


public class Flight {

    private int delay;
    private String airportCode;
    private boolean cancelled;

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "delay=" + delay +
                ", airportCode='" + airportCode + '\'' +
                ", cancelled=" + cancelled +
                '}';
    }
}
