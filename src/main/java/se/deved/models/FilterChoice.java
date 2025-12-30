package se.deved.models;

public enum FilterChoice {
    BY_YEAR(1, "Filter by year"),
    BY_MONTH(2, "Filter by month"),
    BY_WEEK(3, "Filter by week"),
    BY_DAY(4, "Filter by day");

    private final String FilterChoice;
    private final int id;

    FilterChoice(int id, String filterChoice) {
        this.FilterChoice = filterChoice;
        this.id = id;
    }

    @Override
    public String toString() {
        return "* " + id + ". " + FilterChoice;
    }

    public int getID() {
        return id;
    }
}
