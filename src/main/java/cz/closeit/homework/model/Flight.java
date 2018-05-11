package cz.closeit.homework.model;


public class Flight {

    private String airportCode;
    private int delay;
    private boolean cancelled;

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
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
                "airportCode='" + airportCode + '\'' +
                ", delay=" + delay +
                ", cancelled=" + cancelled +
                '}';
    }
}
