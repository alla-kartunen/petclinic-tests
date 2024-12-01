package tests.managers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseManager {

    protected enum Log {
        CREATE_OWNER, DELETE_OWNER, SELECT_OWNER,
        CREATE_PET, DELETE_PET, SELECT_PET,
        COUNT_DELETED, COUNT_SELECTED
    }

    private Logger logger = LogManager.getLogger(BaseManager.class);

    protected void log(Log log, Object object) {
        switch (log) {
            case CREATE_OWNER:
                logger.info("Create Owner in db {}", object);
                break;
            case DELETE_OWNER:
                logger.info("DELETE FROM owners WHERE {}", object);
                break;
            case SELECT_OWNER:
                logger.info("SELECT * FROM owners WHERE {}", object);
                break;
            case CREATE_PET:
                logger.info("Create Pet in db {}", object);
                break;
            case DELETE_PET:
                logger.info("DELETE FROM pets WHERE {}", object);
                break;
            case SELECT_PET:
                logger.info("SELECT * FROM pets WHERE {}", object);
                break;
            case COUNT_DELETED:
                logger.info("Deleted {} records", object);
                break;
            case COUNT_SELECTED:
                logger.info("Selected {} records", object);
                break;
        }
    }
}
