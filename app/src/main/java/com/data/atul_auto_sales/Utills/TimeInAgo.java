package com.data.atul_auto_sales.Utills;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeInAgo {


    public static String getTimeAgo(String timeStamp) {


        long time_ago=stringToLong(timeStamp);
        time_ago = time_ago / 1000;
        long cur_time = (Calendar.getInstance().getTimeInMillis()) / 1000;
        long time_elapsed = cur_time - time_ago;
        long seconds = time_elapsed;
        // Seconds
        if (seconds <= 60) {
            return "Just now";
        }
        //Minutes
        else {
            int minutes = Math.round(time_elapsed / 60);

            if (minutes <= 60) {
                if (minutes == 1) {
                    return "A minute ago";
                } else {
                    return minutes + " minutes ago";
                }
            }
            //Hours
            else {
                int hours = Math.round(time_elapsed / 3600);
                if (hours <= 24) {
                    if (hours == 1) {
                        return "An hour ago";
                    } else {
                        return hours + " hrs ago";
                    }
                }
                //Days
                else {
                    int days = Math.round(time_elapsed / 86400);
                    if (days <= 7) {
                        if (days == 1) {
                            return "Yesterday";
                        } else {
                            return days + " days ago";
                        }
                    }
                    //Weeks
                    else {
                        int weeks = Math.round(time_elapsed / 604800);
                        if (weeks <= 4.3) {
                            if (weeks == 1) {
                                return "A week ago";
                            } else {
                                return weeks + " weeks ago";
                            }
                        }
                        //Months
                        else {
                            int months = Math.round(time_elapsed / 2600640);
                            if (months <= 12) {
                                if (months == 1) {
                                    return "A month ago";
                                } else {
                                    return months + " months ago";
                                }
                            }
                            //Years
                            else {
                                int years = Math.round(time_elapsed / 31207680);
                                if (years == 1) {
                                    return "One year ago";
                                } else {
                                    return years + " years ago";
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    public static long stringToLong(String today) {

        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            try {
                date = formatter.parse(today);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long dateInLong = date.getTime();

            System.out.println("date = " + date);
            System.out.println("dateInLong = " + dateInLong);

            return dateInLong;
        } catch (NullPointerException e) {
            Log.e("time parse pro....", String.valueOf(e));
        }
        return 0;
    }

}
