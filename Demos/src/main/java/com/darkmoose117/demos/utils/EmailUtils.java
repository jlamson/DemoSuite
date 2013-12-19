package com.darkmoose117.demos.utils;

import android.text.format.*;

import com.darkmoose117.demos.model.Email;

import java.util.Arrays;

/**
 * Created by Joshua Lamson on 12/17/13.
 */
public class EmailUtils {

    private static String[][] sPeople = {
            {"Bob"},
            {"Jim", "Alice"},
            {"Dude", "Sam", "Doug", "Jamie", "Anj"},
            {"Dude"},
            {"Sam"},
    };

    private static String[] sSubjects = {
            "Sup!",
            "Time off",
            "[project] Name of Branch",
            "[project] Really long subject, no I mean like reeeeeeally long. For realz tho, insane. lee. long.",
            "Spam Spam Spam",
    };

    private static String sBody = "Normally, both your asses would be dead as fucking fried " +
            "chicken, but you happen to pull this shit while I'm in a transitional period so I " +
            "don't wanna kill you, I wanna help you. But I can't give you this case, it don't " +
            "belong to me. Besides, I've already been through too much shit this morning over " +
            "this case to hand it over to your dumb ass.\n";

    private static int[] sNumberOfReplies = {
            1,
            3,
            17,
            2,
            1
    };

    private static byte[] sIsFavorites = {0, 1, 0, 0, 1};

    private static byte[] sHasAttachments = {1, 0, 1, 0, 1};

    private static long[] sTimes = {
            System.currentTimeMillis() - 5  * DateUtils.MINUTE_IN_MILLIS,
            System.currentTimeMillis() - 45 * DateUtils.MINUTE_IN_MILLIS,
            System.currentTimeMillis() -      DateUtils.HOUR_IN_MILLIS,
            System.currentTimeMillis() - 2  * DateUtils.DAY_IN_MILLIS
                                       - 6  * DateUtils.HOUR_IN_MILLIS,
            System.currentTimeMillis() -      DateUtils.WEEK_IN_MILLIS
    };

    public static final Email[] EMAILS = {
        new Email(Arrays.asList(sPeople[0]), sSubjects[0], sBody, sNumberOfReplies[0], sIsFavorites[0] == 1, sHasAttachments[0] == 1, sTimes[0]),
        new Email(Arrays.asList(sPeople[1]), sSubjects[1], sBody, sNumberOfReplies[1], sIsFavorites[1] == 1, sHasAttachments[1] == 1, sTimes[1]),
        new Email(Arrays.asList(sPeople[2]), sSubjects[2], sBody, sNumberOfReplies[2], sIsFavorites[2] == 1, sHasAttachments[2] == 1, sTimes[2]),
        new Email(Arrays.asList(sPeople[3]), sSubjects[3], sBody, sNumberOfReplies[3], sIsFavorites[3] == 1, sHasAttachments[3] == 1, sTimes[3]),
        new Email(Arrays.asList(sPeople[4]), sSubjects[4], sBody, sNumberOfReplies[4], sIsFavorites[4] == 1, sHasAttachments[4] == 1, sTimes[4])
    };
}
