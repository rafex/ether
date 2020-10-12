package dev.rafex.ether.test.jwt;

import static org.junit.jupiter.api.Assertions.fail;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dev.rafex.ether.jwt.JWebToken;

public class TestJWebToken {

	LocalDateTime ldt;
	JsonObject payload;
	JsonParser jsonParser;

	@BeforeEach
	void setUp() {
		ldt = LocalDateTime.now().plusDays(90);
		jsonParser = new JsonParser();
		final String json = "{\"sub\":\"1234\",\"aud\":[\"admin\"]," + "\"exp\":" + ldt.toEpochSecond(ZoneOffset.UTC) + "}";
		payload = jsonParser.parse(json).getAsJsonObject();
	}

	@Test
	void createJson() {

		final JsonObject jsonObject = new JsonObject();

		final String[] aud = { "admin", "user" };

		jsonObject.addProperty("sub", "jajaja");
		jsonObject.add("aud", new Gson().toJsonTree(aud));

		System.out.println(jsonObject.toString());

		final JsonElement jsonElement = jsonObject.get("aud");

		final JsonArray asJsonArray = jsonElement.getAsJsonArray();

		System.out.println(asJsonArray);
	}

	@Test
	void testWithData() {
		// generate JWT
		final long exp = LocalDateTime.now().plusDays(90).toEpochSecond(ZoneOffset.UTC);
		final String token = new JWebToken("1234", jsonParser.parse("['admin']").getAsJsonArray(), exp).toString();
		// verify and use
		JWebToken incomingToken;
		System.out.println(token);
		try {
			incomingToken = new JWebToken(token);
			if (incomingToken.isValid()) {
				Assertions.assertEquals("1234", incomingToken.getSubject());
				Assertions.assertEquals("admin", incomingToken.getAudience().get(0));
			}
		} catch (final NoSuchAlgorithmException ex) {
			fail("Invalid Token" + ex.getMessage());
		}

	}

}
