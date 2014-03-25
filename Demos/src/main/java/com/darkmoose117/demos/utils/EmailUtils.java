package com.darkmoose117.demos.utils;

import android.text.format.*;

import com.darkmoose117.demos.model.Email;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Joshua Lamson on 12/17/13.
 */
public class EmailUtils {

    private static final String[][] sPeople = {
            {"Bob"},
            {"Jim", "Alice"},
            {"Dude", "Sam", "Doug", "Jamie", "Anj"},
            {"Dude"},
            {"Sam"},
    };

    private static final String[] sSubjects = {
            "Sup!",
            "Time off",
            "[project] Name of Branch",
            "[project] Really long subject, no I mean like reeeeeeally long. For realz tho, insane. lee. long.",
            "Spam Spam Spam",
    };

    private static final String sLongBody  = "Normally, both your asses would be dead as fucking fried " +
            "chicken, but you happen to pull this shit while I'm in a transitional period so I " +
            "don't wanna kill you, I wanna help you. But I can't give you this case, it don't " +
            "belong to me. Besides, I've already been through too much shit this morning over " +
            "this case to hand it over to your dumb ass.";
    private static String sShortBody = "How're you?";

    private static final int[] sNumberOfReplies = {
            1,
            3,
            17,
            2,
            1
    };

    private static final byte[] sIsFavorites = {0, 1, 0, 0, 1};

    private static final byte[] sHasAttachments = {1, 0, 1, 0, 1};
    
    private static final long[] sTimes = {
            System.currentTimeMillis() - 5  * DateUtils.MINUTE_IN_MILLIS,
            System.currentTimeMillis() - 20 * DateUtils.MINUTE_IN_MILLIS,
            System.currentTimeMillis() - 45 * DateUtils.MINUTE_IN_MILLIS,
            System.currentTimeMillis() -      DateUtils.HOUR_IN_MILLIS,
            System.currentTimeMillis() - 6  * DateUtils.HOUR_IN_MILLIS,
            System.currentTimeMillis() - 12 * DateUtils.HOUR_IN_MILLIS,
            System.currentTimeMillis() - 1  * DateUtils.DAY_IN_MILLIS,
            System.currentTimeMillis() - 3  * DateUtils.DAY_IN_MILLIS,
            System.currentTimeMillis() - 5  * DateUtils.DAY_IN_MILLIS,
            System.currentTimeMillis() -      DateUtils.WEEK_IN_MILLIS
    };

    private static final Email[] EMAILS = {
        new Email(Arrays.asList(sPeople[0]), sSubjects[0], sLongBody , sNumberOfReplies[0], sIsFavorites[0] == 1, sHasAttachments[0] == 1, sTimes[0]),
        new Email(Arrays.asList(sPeople[1]), sSubjects[1], sShortBody, sNumberOfReplies[1], sIsFavorites[1] == 1, sHasAttachments[1] == 1, sTimes[1]),
        new Email(Arrays.asList(sPeople[2]), sSubjects[2], sLongBody , sNumberOfReplies[2], sIsFavorites[2] == 1, sHasAttachments[2] == 1, sTimes[2]),
        new Email(Arrays.asList(sPeople[3]), sSubjects[3], sShortBody, sNumberOfReplies[3], sIsFavorites[3] == 1, sHasAttachments[3] == 1, sTimes[3]),
        new Email(Arrays.asList(sPeople[4]), sSubjects[4], sLongBody , sNumberOfReplies[4], sIsFavorites[4] == 1, sHasAttachments[4] == 1, sTimes[4]),
        new Email(Arrays.asList(sPeople[0]), sSubjects[0], sShortBody, sNumberOfReplies[0], sIsFavorites[0] == 1, sHasAttachments[0] == 1, sTimes[5]),
        new Email(Arrays.asList(sPeople[1]), sSubjects[1], sLongBody , sNumberOfReplies[1], sIsFavorites[1] == 1, sHasAttachments[1] == 1, sTimes[6]),
        new Email(Arrays.asList(sPeople[2]), sSubjects[2], sShortBody, sNumberOfReplies[2], sIsFavorites[2] == 1, sHasAttachments[2] == 1, sTimes[7]),
        new Email(Arrays.asList(sPeople[3]), sSubjects[3], sLongBody , sNumberOfReplies[3], sIsFavorites[3] == 1, sHasAttachments[3] == 1, sTimes[8]),
        new Email(Arrays.asList(sPeople[4]), sSubjects[4], sShortBody, sNumberOfReplies[4], sIsFavorites[4] == 1, sHasAttachments[4] == 1, sTimes[9])
    };

    public static List<Email> getDemoEmailList() {
        return Arrays.asList(EMAILS);
    }
}
