package cis350.project.favor_app.ui.favorFeed;

import java.util.Comparator;

public class Sorter {
    public class UrgencyComparator<Object> implements Comparator<Object> {
        @Override
        public int compare(Object favor, Object t1) {
            Favor f = (Favor) favor;
            Favor g = (Favor) t1;
            return g.furgency - f.furgency;
        }
    }
    public class UsernameComparator<Object> implements Comparator<Object> {
        @Override
        public int compare(Object favor, Object t1) {
            Favor f = (Favor) favor;
            Favor g = (Favor) t1;
            Database db = new Database();
            User u = db.getUserFromId(f.fuserId);
            User v = db.getUserFromId(g.fuserId);
            return u.uname.compareTo(v.uname);
        }
    }
    public class DateComparator<Object> implements Comparator<Object> {
        @Override
        public int compare(Object favor, Object t1) {
            Favor f = (Favor) favor;
            Favor g = (Favor) t1;
            return -1 * f.fdate.compareTo(g.fdate);
        }
    }
}
