package com.geektcp.common.core.generator;


import java.util.Objects;

/**
 * @author geektcp
 * email: geektcp@163.com
 * https://github.com/geektcp/example-id-generator
 * <p>
 * IdGenerator: thread-safe and repeat it anywhere without duplicate id
 */
public class IdGenerator {

    private static final long START_TIME = 1420041600000L;
    private static final long WORKER_ID_BITS = 5L;
    private static final long DATA_CENTER_ID_BITS = 5L;
    private static final long MAX_WORKER_ID = -1L ^ (-1L << WORKER_ID_BITS);
    private static final long MAX_DATA_CENTER_ID = -1L ^ (-1L << DATA_CENTER_ID_BITS);
    private static final long SEQUENCE_BITS = 12L;
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private static final long CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;
    private static final long SEQUENCE_MASK = -1L ^ (-1L << SEQUENCE_BITS);

    private static long SEQUENCE = 0L;
    private static long LAST_TIMESTAMP = -1L;

    private long workerId;
    private long centerId;

    private static IdGenerator instance;

    private static String SPLIT = "_";

    // private
    private IdGenerator(long workerId, long centerId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(
                    String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
        if (centerId > MAX_DATA_CENTER_ID || centerId < 0) {
            throw new IllegalArgumentException(
                    String.format("datacenter Id can't be greater than %d or less than 0", MAX_DATA_CENTER_ID));
        }
        this.workerId = workerId;
        this.centerId = centerId;
    }

    private synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < LAST_TIMESTAMP) {
            throw new RuntimeException(String.format(
                    "Clock moved backwards.  Refusing to generate id for %d milliseconds", LAST_TIMESTAMP - timestamp));
        }

        if (LAST_TIMESTAMP == timestamp) {
            SEQUENCE = (SEQUENCE + 1) & SEQUENCE_MASK;
            if (SEQUENCE == 0) {
                timestamp = tilNextMillis(LAST_TIMESTAMP);
            }
        } else {
            SEQUENCE = 0L;
        }

        LAST_TIMESTAMP = timestamp;

        return ((timestamp - START_TIME) << TIMESTAMP_LEFT_SHIFT)
                | (centerId << CENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT)
                | SEQUENCE;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }


    // for new Object
    public Long getNextId() {
        return this.nextId();
    }

    // static
    public static IdGenerator setInstance(long workerId, long centerId) {
        instance = new IdGenerator(workerId, centerId);
        return instance;
    }

    public static IdGenerator getInstance(long workerId, long centerId) {
        if (Objects.isNull(instance)) {
            instance = new IdGenerator(workerId, centerId);
        }
        return instance;
    }

    public static IdGenerator getInstance() {
        if (Objects.isNull(instance)) {
            instance = new IdGenerator(10L, 0L);
        }
        return instance;
    }

    public static void setSplit(String split) {
        SPLIT = split;
    }

    public static String getSplit(String split) {
        return SPLIT;
    }

    public static String getId(String pre) {
        return pre + SPLIT + getId();
    }

    public static Long getId(long workerId, long centerId) {
        return getInstance(workerId, centerId).nextId();
    }

    public static Long getId() {
        return getInstance().nextId();
    }

}
