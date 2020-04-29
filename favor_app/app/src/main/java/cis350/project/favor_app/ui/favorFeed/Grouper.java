package cis350.project.favor_app.ui.favorFeed;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import cis350.project.favor_app.data.database.FavorDatabase;
import cis350.project.favor_app.data.database.UserDatabase;
import cis350.project.favor_app.data.model.Favor;
import cis350.project.favor_app.data.model.User;

public class Grouper {
    public static List<Favor> getFirstNFavors(int n, String compare) {
        FavorDatabase favorDB = FavorDatabase.getInstance();
        List<Favor> allFavors = favorDB.getAllFavors(compare);

        Log.d("Grouper 1", "yes");

        if (allFavors == null) {
            return new LinkedList<>();
            //throw new IllegalArgumentException("all Favors is null/cannot access favors in db");
        }
        Log.d("Grouper 1", "yes");

        List<Favor> outList = new LinkedList();

        for (Favor f : allFavors) {
            n = n -1;

            outList.add(f);

            if (n == 0) {
                break;
            }
        }
        return outList;
    }

    public static List<Favor> getFirstNFavorsSubmittedByUser(int n, String userId, String compare) {
        FavorDatabase favorDB = FavorDatabase.getInstance();
        List<Favor> allFavors = favorDB.getFavorsSubmittedByUser(userId, compare);

        Log.d("Grouper 1", "yes");

        if (allFavors == null) {
            return new LinkedList<>();
            //throw new IllegalArgumentException("all Favors is null/cannot access favors in db");
        }
        Log.d("Grouper 1", "yes");

        List<Favor> outList = new LinkedList();

        for (Favor f : allFavors) {
            n = n -1;

            outList.add(f);

            if (n == 0) {
                break;
            }
        }
        return outList;
    }

    public static List<Favor> getFirstNFavorsAcceptedByUser(int n, String userId, String compare) {
        FavorDatabase favorDB = FavorDatabase.getInstance();
        List<Favor> allFavors = favorDB.getFavorsAcceptedByUser(userId, compare);

        Log.d("Grouper 1", "yes");

        if (allFavors == null) {
            return new LinkedList<>();
            //throw new IllegalArgumentException("all Favors is null/cannot access favors in db");
        }
        Log.d("Grouper 1", "yes");

        List<Favor> outList = new LinkedList();

        for (Favor f : allFavors) {
            n = n -1;

            outList.add(f);

            if (n == 0) {
                break;
            }
        }
        return outList;
    }
}
