package com.r2c;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dushyant.xml.Database;

public class DummyConnectionManager implements AutoCloseable {
  private Connection connection;
  private Statement statement;
  private ResultSet resultSet;

  public DummyConnectionManager(Database db, String query) throws ConversionException {
    try {
      if (!(db instanceof Database)) {
        throw new ConversionException("can't find database configuration");
      }
      final String url = db.getDriver();
      final Class<?> forName = Class.forName(url);
      DriverManager.registerDriver((Driver) forName.newInstance());
      this.connection = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPassword());
      this.statement = connection.createStatement();
      this.resultSet = this.statement.executeQuery(query);
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
        | SQLException | ConversionException e) {
      throw new ConversionException(e);
    }
  }

  public Connection getConnection() {
    return connection;
  }

  public void setConnection(Connection connection) {
    this.connection = connection;
  }

  public Statement getStatement() {
    return statement;
  }

  public void setStatement(Statement statement) {
    this.statement = statement;
  }

  public ResultSet getResultSet() {
    return resultSet;
  }

  public void setResultSet(ResultSet resultSet) {
    this.resultSet = resultSet;
  }

  @Override
  public void close() throws Exception {
    if (this.resultSet != null) {
      this.resultSet.close();
    }
    if (this.statement != null) {
      this.statement.close();
    }
    if (this.connection != null) {
      this.connection.close();
    }
  }

}
