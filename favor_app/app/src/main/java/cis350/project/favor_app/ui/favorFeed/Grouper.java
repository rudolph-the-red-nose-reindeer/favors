package cis350.project.favor_app.ui.favorFeed;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;

import cis350.project.favor_app.data.model.Favor;
import cis350.project.favor_app.data.model.User;

public class Grouper {
    public static LinkedHashMap<Favor, User> getFirstNFavors(int n, Comparator<Favor> c) {
        Database db = Database.getInstance();
        HashSet<Favor> allFavors = db.getAllFavors();

        Log.d("Grouper 1", "yes");

        if (allFavors == null) {
            return new LinkedHashMap<Favor, User>();
            //throw new IllegalArgumentException("all Favors is null/cannot access favors in db");
        }
        Log.d("Grouper 1", "yes");
        ArrayList<Favor> allFavorsList = new ArrayList<Favor>();
        for (Favor f : allFavors) {
            allFavorsList.add(f);
        }
        if (c != null) {
            Collections.sort(allFavorsList, c);
        } else {
            return new LinkedHashMap<Favor, User>();
        }
        Log.d("Grouper 1", "yes");
        LinkedHashMap<Favor, User> favorToUser = new LinkedHashMap<Favor, User>();
        for (Favor f : allFavorsList) {
            n = n -1;
            User u = db.getUserFromId(f.getUserId());
            if (u == null) {
                continue;
            }
            favorToUser.put(f, u);
            if (n == 0) {
                break;
            }
        }
        if (favorToUser.isEmpty()) {
            return new LinkedHashMap<Favor, User>();
            //throw new IllegalStateException("Yeah, favor to user shouldn't be empty");
        }
        return favorToUser;
    }

    public static LinkedHashMap<Favor, User> getFirstNFavorsSubmittedByUser(int n, String userId,
                                                                   Comparator<Favor> c) {
        Database db = Database.getInstance();
        HashSet<Favor> favors = db.getFavorsSubmittedByUser(userId);

        Log.d("Grouper 2", "yes");

        if (favors == null) {
            return new LinkedHashMap<Favor, User>();
            //throw new IllegalArgumentException("all Favors is null/cannot access favors in db");
        }
        Log.d("Grouper 2", "yes");
        ArrayList<Favor> allFavorsList = new ArrayList<Favor>();
        for (Favor f : favors) {
            allFavorsList.add(f);
        }
        if (c != null) {
            Collections.sort(allFavorsList, c);
        } else {
            return new LinkedHashMap<Favor, User>();
        }
        Log.d("Grouper 2", "yes");
        LinkedHashMap<Favor, User> favorToUser = new LinkedHashMap<Favor, User>();
        for (Favor f : allFavorsList) {
            n = n -1;
            User u = db.getUserFromId(f.getUserId());
            if (u == null) {
                continue;
            }
            favorToUser.put(f, u);
            if (n == 0) {
                break;
            }
        }
        if (favorToUser.isEmpty()) {
            return new LinkedHashMap<Favor, User>();
            //throw new IllegalStateException("Yeah, favor to user shouldn't be empty");
        }
        return favorToUser;
    }

    public static LinkedHashMap<Favor, User> getFirstNFavorsAcceptedByUser(int n, String userId,
                                                                           Comparator<Favor> c) {
        Database db = Database.getInstance();
        HashSet<Favor> favors = db.getFavorsAcceptedByUser(userId);

        Log.d("Grouper 2", "yes");

        if (favors == null) {
            return new LinkedHashMap<Favor, User>();
            //throw new IllegalArgumentException("all Favors is null/cannot access favors in db");
        }
        Log.d("Grouper 2", "yes");
        ArrayList<Favor> allFavorsList = new ArrayList<Favor>();
        for (Favor f : favors) {
            allFavorsList.add(f);
        }
        if (c != null) {
            Collections.sort(allFavorsList, c);
        } else {
            return new LinkedHashMap<Favor, User>();
        }
        Log.d("Grouper 2", "yes");
        LinkedHashMap<Favor, User> favorToUser = new LinkedHashMap<Favor, User>();
        for (Favor f : allFavorsList) {
            n = n -1;
            User u = db.getUserFromId(f.getUserId());
            if (u == null) {
                continue;
            }
            favorToUser.put(f, u);
            if (n == 0) {
                break;
            }
        }
        if (favorToUser.isEmpty()) {
            return new LinkedHashMap<Favor, User>();
            //throw new IllegalStateException("Yeah, favor to user shouldn't be empty");
        }
        return favorToUser;
    }
}
