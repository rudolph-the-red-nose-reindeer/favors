package cis350.project.favor_app.ui.favorFeed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class Grouper {
    public static LinkedHashMap<Favor, User> getFirstNFavors(int n, Comparator<Favor> c) {
        Database db = new Database();
        HashSet<Favor> allFavors = db.getAllFavors();
        if (allFavors == null) {
            throw new IllegalArgumentException("all Favors is null/cannot access favors in db");
        }
        ArrayList<Favor> allFavorsList = new ArrayList<Favor>();
        for (Favor f : allFavors) {
            allFavorsList.add(f);
        }
        Collections.sort(allFavorsList, c);
        LinkedHashMap<Favor, User> favorToUser = new LinkedHashMap<Favor, User>();
        for (Favor f : allFavorsList) {
            n = n -1;
            User u = db.getUserFromId(f.fuserId);
            if (u == null) {
                continue;
            }
            favorToUser.put(f, u);
            if (n == 0) {
                break;
            }
        }
        if (favorToUser.isEmpty()) {
            throw new IllegalStateException("Yeah, favor to user shouldn't be empty");
        }
        return favorToUser;
    }
}
