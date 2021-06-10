package com.ebuy.util.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @version 1.0
 * @user lianxinzhong
 * @date 2021/1/19
 */
public class TraceIdUtil {


    private static final AtomicLong _traceSeq = new AtomicLong(0L);

    private static long genTraceId() {

        return System.currentTimeMillis() << 18 | _traceSeq.incrementAndGet() & 262143L;
    }


    public static void main(String[] args) {
        long currentTime = System.currentTimeMillis();
        System.out.println("currentTime " + currentTime);

        long param1 = currentTime << 18;
        System.out.println("param1 " + param1);

        long param2 = _traceSeq.incrementAndGet();
        System.out.println("param2 " + param2);

        long param3 = 262143L;

        System.out.println("param1|param2 " + (param1 | param2));
        System.out.println("param2&param3 " + (param2 & param3));
        System.out.println("param1|param2&param3 " + (param1 | param2 & param3));

        long finlaResult = currentTime << 18 | _traceSeq.incrementAndGet() & 262143L;
        System.out.println(finlaResult);
    }
}
