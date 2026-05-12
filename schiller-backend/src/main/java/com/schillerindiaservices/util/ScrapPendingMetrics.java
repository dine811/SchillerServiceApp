package com.schillerindiaservices.util;

import com.schillerindiaservices.entity.ServiceMaster;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Pending-day columns from legacy ScarpListEnggController / ScarpList.jsp
 * (PFRN, OBP, URP, SCC) — same date math and suffix rules as the old servlet.
 */
public final class ScrapPendingMetrics {

    private ScrapPendingMetrics() {}

    public static String pfrn(ServiceMaster sm) {
        LocalDate sc = sm.getSerCentreReceivedDate();
        LocalDate ship = sm.getShipDtFrmSerCntr();
        LocalDate today = LocalDate.now();
        if (sc == null) {
            return "";
        }
        if (ship == null) {
            return String.valueOf(ChronoUnit.DAYS.between(sc, today));
        }
        return ChronoUnit.DAYS.between(sc, ship) + " SC";
    }

    /** Legacy OBP column reuses the same branches as PFRN with an " OB" suffix. */
    public static String obp(ServiceMaster sm) {
        LocalDate sc = sm.getSerCentreReceivedDate();
        LocalDate ship = sm.getShipDtFrmSerCntr();
        LocalDate today = LocalDate.now();
        if (sc == null) {
            return "";
        }
        if (ship == null) {
            return String.valueOf(ChronoUnit.DAYS.between(sc, today));
        }
        return ChronoUnit.DAYS.between(sc, ship) + " OB";
    }

    public static String urp(ServiceMaster sm) {
        LocalDate sc = sm.getSerCentreReceivedDate();
        LocalDate rep = sm.getRepairedBrdStkDate();
        LocalDate today = LocalDate.now();
        if (sc == null) {
            return "";
        }
        if (rep == null) {
            return String.valueOf(ChronoUnit.DAYS.between(sc, today));
        }
        return ChronoUnit.DAYS.between(sc, rep) + " UR";
    }

    public static String scc(ServiceMaster sm) {
        LocalDate sc = sm.getSerCentreReceivedDate();
        LocalDate rep = sm.getRepairedBrdStkDate();
        LocalDate today = LocalDate.now();
        if (sc == null) {
            return "";
        }
        if (rep == null) {
            return String.valueOf(ChronoUnit.DAYS.between(sc, today));
        }
        return ChronoUnit.DAYS.between(sc, rep) + " SCC";
    }
}
