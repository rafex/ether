package dev.rafex.ether.jwt;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class JWebToken {

	Logger LOGGER = Logger.getLogger(JWebToken.class.getName());

	private static final String SECRET_KEY = "FREE_MASON"; // @TODO Add Signature here
	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
	private static final String ISSUER = "mason.metamug.net";
	private static final String JWT_HEADER = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
	private JsonObject payload = new JsonObject();
	private String signature;
	private String encodedHeader;

	private JWebToken() {
		encodedHeader = encode(new Gson().fromJson(JWT_HEADER, JsonObject.class));
	}

	public JWebToken(final JsonObject payload) {
		this(payload.get("sub").getAsString(), payload.get("aud").getAsJsonArray(), payload.get("exp").getAsLong());
	}

	public JWebToken(final String sub, final JsonArray aud, final long expires) {
		this();
		payload.addProperty("sub", sub);
		payload.add("aud", aud);
		payload.addProperty("exp", expires);
		payload.addProperty("iat", LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
		payload.addProperty("iss", ISSUER);
		payload.addProperty("jti", UUID.randomUUID().toString()); // how do we use this?
		signature = hmacSha256(encodedHeader + "." + encode(payload), SECRET_KEY);
	}

	/**
	 * For verification
	 *
	 * @param token
	 * @throws Exception
	 */
	public JWebToken(final String token) throws NoSuchAlgorithmException {
		this();
		final String[] parts = token.split("\\.");
		if (parts.length != 3) {
			throw new IllegalArgumentException("Invalid Token format");
		}
		if (encodedHeader.equals(parts[0])) {
			encodedHeader = parts[0];
		} else {
			throw new NoSuchAlgorithmException("JWT Header is Incorrect: " + parts[0]);
		}

//		payload = new JsonObject(decode(parts[1]));
		payload = new JsonParser().parse(decode(parts[1])).getAsJsonObject();
		if (payload.isJsonNull()) {
			throw new JsonIOException("Payload is Empty: ");
		}
		if (!payload.has("exp")) {
			throw new JsonSyntaxException("Payload doesn't contain expiry " + payload);
		}
		signature = parts[2];
	}

	@Override
	public String toString() {
		return encodedHeader + "." + encode(payload) + "." + signature;
	}

	public boolean isValid() {
		return payload.get("exp").getAsLong() > LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) // token not expired
				&& signature.equals(hmacSha256(encodedHeader + "." + encode(payload), SECRET_KEY)); // signature matched
	}

	public String getSubject() {
		return payload.get("sub").getAsString();
	}

	public List<String> getAudience() {
		final JsonArray aud = payload.get("aud").getAsJsonArray();
		final List<String> list = new ArrayList<>();
		for (final JsonElement jsonElement : aud) {
			list.add(jsonElement.getAsString());
		}
		return list;
	}

	private static String encode(final JsonObject obj) {
		return encode(obj.toString().getBytes(StandardCharsets.UTF_8));
	}

	private static String encode(final byte[] bytes) {
		return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
	}

	private static String decode(final String encodedString) {
		return new String(Base64.getUrlDecoder().decode(encodedString));
	}

	/**
	 * Sign with HMAC SHA256 (HS256)
	 *
	 * @param data
	 * @return
	 * @throws Exception
	 */
	private String hmacSha256(final String data, final String secret) {
		try {

			final MessageDigest digest = MessageDigest.getInstance("SHA-256");
//			final byte[] hash = secret.getBytes(StandardCharsets.UTF_8);// digest.digest(secret.getBytes(StandardCharsets.UTF_8));
			final byte[] hash = digest.digest(secret.getBytes(StandardCharsets.UTF_8));

			final Mac sha256Hmac = Mac.getInstance("HmacSHA256");
			final SecretKeySpec secretKey = new SecretKeySpec(hash, "HmacSHA256");
			sha256Hmac.init(secretKey);

			final byte[] signedBytes = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
			return encode(signedBytes);
		} catch (NoSuchAlgorithmException | InvalidKeyException ex) {
			LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
			return null;
		}
	}
}
