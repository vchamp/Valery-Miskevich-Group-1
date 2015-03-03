package by.gravity.cinema.bo;

/**
 * Created by ilya.shknaj on 03.03.15.
 */
public class TicketImpl implements Ticket {

    private int series;

    private int place;

    private int price;

    public TicketImpl(int series, int place, int price) {

        this.series = series;
        this.place = place;
        this.price = price;
    }

    @Override
    public int getSeries() {

        return series;
    }

    @Override
    public int getPlace() {

        return place;
    }

    @Override
    public int getPrice() {

        return price;
    }
}
