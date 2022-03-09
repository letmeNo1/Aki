package aki.Common;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.*;

public class TraceLog {
    private final String LogPath =System.getProperty("user.dir")+ "\\";
    private final String LogFile = LocalDate.now() + ".log";
    private static final LogManager logManager = LogManager.getLogManager();
    private static Logger InfoLog;
    private static Logger ErrLog;

    public TraceLog () {
        if (logManager.getLogger("LogInfo") == null) {
            iniInfoLog();
        } else {
            InfoLog = logManager.getLogger("LogInfo");
        }
    }


    private void iniInfoLog () {
        InfoLog = Logger.getLogger("LogInfo");
        ErrLog = Logger.getLogger("LogErr");

        try {
            FileHandler InfoFileHandler = new FileHandler(LogPath+LogFile ,true);
            InfoFileHandler.setLevel(Level.ALL);
            InfoFileHandler.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    return (new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS]").format(new Date()))+":"+record.getLevel()+":"+record.getMessage()+"\n";
                }
            });
            ErrLog.addHandler(InfoFileHandler);
            InfoLog.addHandler(InfoFileHandler);
            logManager.addLogger(InfoLog);
            logManager.addLogger(ErrLog);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logInfo (String LoggingMessage)
    {
        InfoLog.info(LoggingMessage);
    }

    public void logErr (String LoggingMessage)
    {
        ErrLog.warning(LoggingMessage);
    }

    public static void main(String[] args) {
        TraceLog log = new TraceLog();
        log.logInfo("xxafsa");
        log.logErr("xxafsa");

    }
}

