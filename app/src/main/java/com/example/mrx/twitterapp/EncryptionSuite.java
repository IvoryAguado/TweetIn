package com.example.mrx.twitterapp;

import android.support.annotation.NonNull;
import android.util.Log;

import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

/**
 * Created by MrX on 08/10/2015.
 */
public class EncryptionSuite {

    public synchronized static String decrypt(String toDecrypt) {
        try {
            toDecrypt = AESCrypt.decrypt(BuildConfig.ENCRYPTION_SEED, toDecrypt);
        } catch (@NonNull GeneralSecurityException | NullPointerException w) {
            Log.i(EncryptionSuite.class.getName(), "Failed Decryption " + toDecrypt);
            return null;
        }
        return toDecrypt;
    }

    public synchronized static String encrypt(String toEncrypt) {
        try {
            toEncrypt = AESCrypt.encrypt(BuildConfig.ENCRYPTION_SEED, toEncrypt);
        } catch (@NonNull GeneralSecurityException | NullPointerException w) {
            Log.i(EncryptionSuite.class.getName(), "Failed Encryption " + toEncrypt);
            return null;
        }
        return toEncrypt;
    }
}
