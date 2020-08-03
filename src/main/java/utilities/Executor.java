package utilities;

import java.io.IOException;

public interface Executor {

    Process execute(String file) throws IOException;
}
