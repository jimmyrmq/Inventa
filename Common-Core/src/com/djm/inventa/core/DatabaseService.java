package com.djm.inventa.core;

import java.sql.Connection;

public interface DatabaseService {
    void connect();
    Connection getConnection();
    boolean isConnected();
    void disconnect();
}

