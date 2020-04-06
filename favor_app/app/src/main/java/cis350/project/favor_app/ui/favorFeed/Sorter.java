package cis350.project.favor_app.ui.favorFeed;

import java.util.Comparator;

public class Sorter {
    public class UrgencyComparator<Object> implements Comparator<Object> {
        @Override
        public int compare(Object favor, Object t1) {
            Favor f = (Favor) favor;
            Favor g = (Favor) t1;
            return f.furgency - g.furgency;
        }
    }
    public class UsernameComparator<Object> implements Comparator<Object> {
        @Override
        public int compare(Object favor, Object t1) {
            Favor f = (Favor) favor;
            Favor g = (Favor) t1;
            return f.fuserId.compareTo(g.fuserId);
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
