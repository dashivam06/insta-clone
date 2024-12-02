package com.shivam.instagram.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;


public class Time 
{
     public static String getGMT_Time(String format)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        Date date = Date.from(Instant.now());

        String dateTime = sdf.format(date);

        return dateTime;
    }


    public static long getGmtDateInMilliSec()
    {

        long milliSec = Instant.now().toEpochMilli();

        return milliSec;
        
    }
}
