package cis350.project.favor_app.ui.favorFeed;

import java.util.Comparator;

import cis350.project.favor_app.data.database.FavorDatabase;
import cis350.project.favor_app.data.database.UserDatabase;
import cis350.project.favor_app.data.model.Favor;
import cis350.project.favor_app.data.model.User;

public class Sorter {
    public class UrgencyComparator<Object> implements Comparator<Object> {
        @Override
        public int compare(Object favor, Object t1) {
            Favor f = (Favor) favor;
            Favor g = (Favor) t1;
            return g.getUrgency() - f.getUrgency();
        }
    }
    public class UsernameComparator<Object> implements Comparator<Object> {
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
    public class DateComparator<Object> implements Comparator<Object> {
        @Override
        public int compare(Object favor, Object t1) {
            Favor f = (Favor) favor;
            Favor g = (Favor) t1;
            return -1 * f.getDate().compareTo(g.getDate());
        }
    }
}