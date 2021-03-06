/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.holmes.watson.bank.core;

import java.util.Random;
import org.holmes.watson.bank.core.entity.Agency;

/**
 *
 * @author Olayinka
 */
public class Utils {

    public static long nextLong(Random rng, long n) {
        // error checking and 2^x checking removed for simplicity.
        long bits, val;
        do {
            bits = (rng.nextLong() << 1) >>> 1;
            val = bits % n;
        } while (bits - val + (n - 1) < 0L);
        return val;
    }

    public static boolean isMyAgency(String accountnum) {
        return Agency.getAgency().getAgencyid().equals(accountnum.split("-")[0]);
    }

    public static String getAgencyId(String clientid) {
        return clientid.split("-")[0];
    }
}
