package dev.rafex.ether.jdbc.utils;

import java.util.logging.Logger;

public interface IQuery {

	Logger LOGGER = Logger.getLogger(IQuery.class.getName());

	String END_OF_LINE = ";";
	String SELECT_DEFAULT = "SELECT * FROM ${table_name}";
	String SELECT_WHERE = "SELECT * FROM ${table_name} WHERE ${conditions}";
	String SELECT_WHERE_NOT = "SELECT * FROM ${table_name} WHERE NOT ${conditions}";
	String SELECT_WHERE_ORDER_BY_ASC = "SELECT * FROM ${table_name} WHERE ${conditions} ORDER BY ${columnsOrder} ASC";
	String SELECT_WHERE_ORDER_BY_DESC = "SELECT * FROM ${table_name} WHERE ${conditions} ORDER BY ${columnsOrder} DESC";
	String SELECT_COLUMNS = "SELECT ${columns} FROM ${table_name}";
	String SELECT_COLUMNS_WHERE = "SELECT ${columns} FROM ${table_name} WHERE ${conditions}";
	String SELECT_COLUMNS_WHERE_NOT = "SELECT ${columns} FROM ${table_name} WHERE NOT ${conditions}";
	String SELECT_COLUMNS_WHERE_ORDER_BY_ASC = "SELECT ${columns} FROM ${table_name} WHERE ORDER BY ${conditions} ASC";
	String SELECT_COLUMNS_WHERE_ORDER_BY_DESC = "SELECT ${columns} FROM ${table_name} WHERE ORDER BY ${conditions} DESC";

	String SELECT = " SELECT ";
	String DISTINCT = " DISTINCT ";
	String WHERE = " WHERE ";
	String WHERE_NOT = " WHERE NOT ";
	String FROM = " FROM ";
	String OR = " OR ";
	String AND = " AND ";
	String IS = " IS ";
	String IS_NULL = " IS NULL ";
	String IS_NOT_NULL = " IS NOT NULL ";
	String NOT = " NOT ";
	String NULL = " NULL ";
	String ASC = " ASC ";
	String DESC = " DESC ";
	String ORDER = " ORDER ";
	String ORDER_BY = " ORDER BY ";
	String BY = " BY ";
	String LIMIT = " LIMIT ";
	String INSERT = " INSERT ";
	String INSERT_INTO = " INSERT INTO ";
	String INTO = " INTO ";
	String VALUES = " VALUES ";
	String UPDATE = " UPDATE ";
	String SET = " SET ";
	String DELETE = " DELETE ";
	String EMPTY_SPACE = " ";

}
