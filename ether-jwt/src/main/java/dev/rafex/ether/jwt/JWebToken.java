package dev.rafex.ether.jwt;

import java.util.List;

import com.google.gson.JsonObject;

public interface JWebToken {

    JsonObject getPayload();

    String getIssuer();

    String getSubject();

    List<String> getAudience();

    Long getExpiration();

    Long getNotBefore();

    Long getIssuedAt();

    String getJwtId();

    String get(String property);

    String getSignature();

    String getEncodedHeader();

    boolean isValid();

    String aJson();

}
