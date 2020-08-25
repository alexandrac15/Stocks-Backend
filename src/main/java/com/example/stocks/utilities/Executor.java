package com.example.stocks.utilities;

import java.io.IOException;

public interface Executor {

    Process execute(String file) throws IOException;
}
