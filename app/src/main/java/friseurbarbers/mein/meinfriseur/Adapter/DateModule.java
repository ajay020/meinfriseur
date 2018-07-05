package friseurbarbers.mein.meinfriseur.Adapter;

/**
 * Created by Aarya on 2/10/2018.
 */

public class DateModule {
   int date;
    boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public DateModule(int date) {
        this.date = date;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }
}
