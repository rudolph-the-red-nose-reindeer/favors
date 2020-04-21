package cis350.project.favor_app.ui.favorFeed;

import java.util.Comparator;

import cis350.project.favor_app.data.database.UserDatabase;
import cis350.project.favor_app.data.model.Favor;
import cis350.project.favor_app.data.model.User;

public class Sorter {
    private static Sorter instance = null;

    public static Sorter getInstance() {
        if (instance == null) {
            instance = new Sorter();
        }
        return instance;
    }

    private class UrgencyComparator<Object> implements Comparator<Object> {
        @Override
        public int compare(Object favor, Object t1) {
            Favor f = (Favor) favor;
            Favor g = (Favor) t1;
            return g.getUrgency() - f.getUrgency();
        }
    }
    private class UsernameComparator<Object> implements Comparator<Object> {
        @Override
        public int compare(Object favor, Object t1) {
            Favor f = (Favor) favor;
            Favor g = (Favor) t1;
            UserDatabase db = UserDatabase.getInstance();
            User u = db.findUserById(f.getUserId());
            User v = db.findUserById(g.getUserId());
            return u.getUsername().compareTo(v.getUsername());
        }
    }
    private class DateComparator<Object> implements Comparator<Object> {
        @Override
        public int compare(Object favor, Object t1) {
            Favor f = (Favor) favor;
            Favor g = (Favor) t1;
            return -1 * f.getDate().compareTo(g.getDate());
        }
    }

    public Comparator getUsernameComparator() {
        return new UsernameComparator<>();
    }

    public Comparator getDateComparator() {
        return new DateComparator<>();
    }

    public Comparator getUrgencyComparator() {
        return new UrgencyComparator<>();
    }
}
