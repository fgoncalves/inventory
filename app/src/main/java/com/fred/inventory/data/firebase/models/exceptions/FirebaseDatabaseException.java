package com.fred.inventory.data.firebase.models.exceptions;

import com.google.firebase.database.DatabaseError;

public class FirebaseDatabaseException extends RuntimeException {
  private final DatabaseError error;

  public FirebaseDatabaseException(DatabaseError error) {
    this.error = error;
  }

  public FirebaseDatabaseException(String message, DatabaseError error) {
    super(message);
    this.error = error;
  }

  public FirebaseDatabaseException(String message, Throwable cause, DatabaseError error) {
    super(message, cause);
    this.error = error;
  }

  public FirebaseDatabaseException(Throwable cause, DatabaseError error) {
    super(cause);
    this.error = error;
  }

  public DatabaseError getError() {
    return error;
  }
}
