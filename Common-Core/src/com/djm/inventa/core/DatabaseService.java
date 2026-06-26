package com.djm.inventa.core;

import java.sql.Connection;

public interface DatabaseService {
    Connection getConnection();
}

