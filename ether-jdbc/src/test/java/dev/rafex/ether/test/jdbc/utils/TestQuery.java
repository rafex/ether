package dev.rafex.ether.test.jdbc.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dev.rafex.ether.jdbc.utils.Query;

public class TestQuery {

	@Test
	void insert1() {
		final String expected = " INSERT INTO  panelx.shippers  (name, telephone, address, gps_locations_latitude, gps_locations_longitude, contact_name, contact_phone, contact_email, id_user)  VALUES  (?,?,?,?,?,?,?,?,?) ";
		final String[] columnNames = { "name", "telephone", "address", "gps_locations_latitude", "gps_locations_longitude", "contact_name", "contact_phone", "contact_email",
				"id_user" };
		final Query actual = new Query.Builder().insertInto().tableName("panelx.shippers").columnNames(columnNames).values().insertParameters(9).build();

		System.out.println("Expected: [" + expected + "]");
		System.out.println("Query   : [" + actual.getString() + "]");
		assertEquals(expected, actual.getString(), "No son iguales");
	}

	@Test
	void select1() {
		final String expected = " SELECT  id, uuid, name, business_name, email, phone, rfc, address, plan, status, contact  FROM  panelx.customers  WHERE  removed != true ";

		final String[] columnNames = { "id", "uuid", "name", "business_name", "email", "phone", "rfc", "address", "plan", "status", "contact" };
		final Query actual = new Query.Builder().select().columnNames(columnNames).from().tableName("panelx.customers").where().conditions("removed != true").build();

		System.out.println("Expected: [" + expected + "]");
		System.out.println("Query   : [" + actual.getString() + "]");
		assertEquals(expected, actual.getString(), "No son iguales");
	}
}
