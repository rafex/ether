package dev.rafex.ether.jdbc.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class Query implements IQuery {

	private final StringBuilder query;

	private Query(final Builder builder) {
		query = builder.query;
	}

	public PreparedStatement preparedStatement(final Connection connection) throws SQLException {
		LOGGER.log(Level.INFO, query.toString());
		return connection.prepareStatement(query.toString());
	}

	public PreparedStatement preparedStatementReturnGeneratedKeys(final Connection connection) throws SQLException {
		return connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
	}

	public String getString() {
		return query.toString();
	}

	public static class Builder {
		private final StringBuilder query = new StringBuilder();
		String[] columnNames;
		String header;

		public Builder columnNames(final String[] columnNames) {
			this.columnNames = columnNames;
			query.append(EMPTY_SPACE);
			if (INSERT.equals(header) || INSERT_INTO.equals(header)) {
				query.append("(");
				query.append(columns());
				query.append(")");
			} else {
				query.append(columns());
			}
			query.append(EMPTY_SPACE);
			return this;
		}

		public Builder select() {
			query.append(SELECT);
			header = SELECT;
			return this;
		}

		public Builder tableName(final String tableName) {
			if (tableName != null && !tableName.isBlank()) {
				query.append(EMPTY_SPACE);
				query.append(tableName);
				query.append(EMPTY_SPACE);
			}
			return this;
		}

		public Builder selectAllColumns(final String tableName) {
			query.append(SELECT_DEFAULT.replace("${table_name}", tableName));
			return this;
		}

		public Builder where(final String conditions) {
			if (conditions != null && !conditions.isBlank()) {
				query.append(WHERE);
				query.append(conditions);
			}
			return this;
		}

		public Builder where() {
			query.append(WHERE);
			return this;
		}

		public Builder distinct() {
			query.append(DISTINCT);
			return this;
		}

		public Builder from() {
			query.append(FROM);
			return this;
		}

		public Builder whereNot() {
			query.append(WHERE_NOT);
			return this;
		}

		public Builder not() {
			query.append(NOT);
			return this;
		}

		public Builder orderBy() {
			query.append(ORDER_BY);
			return this;
		}

		public Builder order() {
			query.append(ORDER);
			return this;
		}

		public Builder by() {
			query.append(BY);
			return this;
		}

		public Builder addText(final String text) {
			query.append(EMPTY_SPACE);
			query.append(text);
			query.append(EMPTY_SPACE);
			return this;
		}

		public Builder is() {
			query.append(IS);
			return this;
		}

		public Builder isNull() {
			query.append(IS_NULL);
			return this;
		}

		public Builder isNotNull() {
			query.append(IS_NOT_NULL);
			return this;
		}

		public Builder or() {
			query.append(OR);
			return this;
		}

		public Builder and() {
			query.append(AND);
			return this;
		}

		public Builder NULL() {
			query.append(NULL);
			return this;
		}

		public Builder asc() {
			query.append(ASC);
			return this;
		}

		public Builder desc() {
			query.append(DESC);
			return this;
		}

		public Builder limit() {
			query.append(LIMIT);
			return this;
		}

		public Builder insert() {
			query.append(INSERT);
			header = INSERT;
			return this;
		}

		public Builder insertInto() {
			query.append(INSERT_INTO);
			header = INSERT_INTO;
			return this;
		}

		public Builder into() {
			query.append(INTO);
			return this;
		}

		public Builder values() {
			query.append(VALUES);
			return this;
		}

		public Builder update() {
			query.append(UPDATE);
			return this;
		}

		public Builder set() {
			query.append(SET);
			return this;
		}

		public Builder delete() {
			query.append(DELETE);
			return this;
		}

		public Builder insertParameters(final int parameter) {
			if (parameter > 0) {
				query.append(EMPTY_SPACE);
				query.append("(");
				for (int i = 0; i < parameter; i++) {
					if (i + 1 == parameter) {
						query.append("?");
					} else {
						query.append("?");
						query.append(",");

					}
				}
				query.append(")");
				query.append(EMPTY_SPACE);
			}
			return this;
		}

		public Builder conditions(final String conditions) {
			if (conditions != null && !conditions.isBlank()) {
				query.append(EMPTY_SPACE);
				query.append(conditions);
				query.append(EMPTY_SPACE);
			}
			return this;
		}

		public Query build() {
			return new Query(this);
		}

		private String columns() {
			final StringBuilder sb = new StringBuilder();
			for (int i = 0; i < columnNames.length; i++) {
				if (i + 1 == columnNames.length) {
					sb.append(columnNames[i]);
				} else {
					sb.append(columnNames[i]);
					sb.append(", ");
				}
			}
			return sb.toString();
		}

	}

}
