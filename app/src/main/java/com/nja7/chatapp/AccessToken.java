package com.nja7.chatapp;

import com.google.auth.oauth2.GoogleCredentials;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class AccessToken {
    private static final String firebaseMessagingScope = "https://www.googleapis.com/auth/firebase.messaging";

    public static String getAccessToken() {
        try {
            String serviceAccountJson = "{\n" +
                    "  \"type\": \"service_account\",\n" +
                    "  \"project_id\": \"chatapplication-114d6\",\n" +
                    "  \"private_key_id\": \"170545f166e3c3981ce05911eab1702226fce252\",\n" +
                    "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDGz9V5M+71Mp5b\\n9G956MFhsas6i79agafNPKWfFhINfowlfni5Kne9Np1ZqbfnAINPxBPlQJPVcUvs\\nwrTtQEAAS+Lt5mIixKMWJSgjyFuQhZzDG61UWIZNZOhPRGYMoyAVtxKw/rKOkdRV\\nUbkLHSl3E13N4237j4Hgs8Ig5ALbQsu4mJayH/YAULHB1olfjt7rIe2JkGGW+MLM\\neQxqh2MAGrzuXD1t73e3uE7nHzzMy8O7vRzo3pRy4vbGf90TxIKZleEbeTXCr+5q\\nqRXCzZAfJeNYiQ7gR45CnPBpNd7J3TRLxgsGPBuojW9FhSCTDoQDlIWbgQFfEyqJ\\nw7VfuPJFAgMBAAECggEACMtawxDECj6h+KlYYQklvL+SNnXGEifYF2EOZnogdE5b\\nvrDYgEBbqZgH39Z9Fw/TvyyKQMLZh0CKvj9ip3gHHLw+no1pW+Zc3HbqTqLnQf5X\\nD95ijW+ZCUVkbVU2Omrg2XUOs5ugpGPZA6koyVAR7H2D0b60t8MCWME63h+X7MD0\\nm1rdIcIJHzNeOHjsLeLvk3oxqTGMXaYPU9NJQWCroUjbOT6snsR1Uel2f5PN/OG5\\nrLveHETRuAZcB69YzcpB9zmhtDVp9ZtQTlP35QA/NAsd+KHVeNK0w6dDsRTcTmG+\\n77Er/7li8tkoIVtu1uB+hAf+SJNGALVJdXgp4SEKJQKBgQD4INIiGt0u+M3SIhUu\\n4ftnaE0gS3a0OLAuQxDxXSfPcQUMEY0PR/4csnduRnZZdE0VI/Z9PGq95GnYXfol\\nNqkq2NmgWR72N3NYVxD4TCqQY7cIgUwNyRq+z/NEa3u5sabqGvSy1gQqLQUTJYOf\\n/Tbr5YzO5Ey/ovq+DxungDQXqwKBgQDNHn003fq66Rcw4UQT3MKU4W14nGSZkf5R\\nFLMorly/+yDy/rpaFfPSBlUQOJuuNoWyhq9TGZr1ej56ggJzrduE1bMTS6iPospT\\nWT5gUGM5SEDYkSPqg7AR6SrVYFEGsiAYye5sr0HczFLZHxK0ZM1+G92m8miSRZAQ\\nZaxDGJJtzwKBgQDIbWqP1fdYW+2VkaZeynfTHbM/JPXruebGV7I15TOlIvwatYqm\\nD1c6Qwap8wziUUl6jCQtIUJvxTOibLcEnFSloaiZF6Qk6sGnWB0DFDNFkuZkXlza\\nvdl2zG8Kk2/Lna7gZ4nFc47FqrvwrVrXPyjAABpvfIZqPfIg1/9UnAB4JQKBgCD8\\nNTBKPibOkw8mOpdjGdttQPhzVe4oPxh01rbFC19IGC1bb0ZLX3E32XgJUme6ltd1\\ntz4K0ROGvANhYtmNbM64utpwgUPP7u9mW5SLeNleWCc7d+YflTHcKCo2GfGUtXqn\\nBnNt346NQQlvaMefqjgMbSGKu+xByYAJiaEkk2+bAoGAJh37Ye/ToYcXq35Ypy1v\\n5pImXWOWugHKjgtxNy2cR4pgXOzpUFn0aEu9OKjshEUGjW2i0ZRRetvjROG9jNn4\\nzcvABuAGtmx/ElnGQ1JZurUvsy8rHy2oPP2fE5DpsDsYLns01fAyp4a6BDC5Wcd3\\nyK9XT0EiTMPuAcQHvY3DvS4=\\n-----END PRIVATE KEY-----\\n\",\n" +
                    "  \"client_email\": \"firebase-adminsdk-fnvpn@chatapplication-114d6.iam.gserviceaccount.com\",\n" +
                    "  \"client_id\": \"103198708430664376760\",\n" +
                    "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
                    "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
                    "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
                    "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-fnvpn%40chatapplication-114d6.iam.gserviceaccount.com\",\n" +
                    "  \"universe_domain\": \"googleapis.com\"\n" +
                    "}\n";
            InputStream stream = new ByteArrayInputStream(serviceAccountJson.getBytes(StandardCharsets.UTF_8));
            GoogleCredentials credentials = GoogleCredentials.fromStream(stream).createScoped(Arrays.asList(firebaseMessagingScope));
            credentials.refreshIfExpired();
            return credentials.getAccessToken().getTokenValue();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
