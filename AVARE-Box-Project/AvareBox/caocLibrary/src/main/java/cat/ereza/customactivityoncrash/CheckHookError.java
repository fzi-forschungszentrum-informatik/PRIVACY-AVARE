package cat.ereza.customactivityoncrash;

/**
 * This class analyzes the uncaught exception, which is handled by the CustomActivityOnCrash class.
 */
public final class CheckHookError {

    //TODO extract String resources
    private static final String INTRO = "Ergebnis der Analyse des Fehlers:";
    private static final String YES = "Ja";
    private static final String NO = "Nein";
    private static final String MICRO = "Fehler beim Mikrofon Hook:";
    private static final String CAMERA = "Fehler beim Kamera Hook:";
    private static final String EXCEPTION = "Fehler der nicht beim Hook entstanden ist:";


    //TODO complete values
    private static final String[] nameOfHookMethodsAudio = {
            "Hook_MediaRecorder_start",
            "Hook_MediaRecorder_stop",
            "Hook_MediaRecorder_setOutPutFile"
    };

    private static final String[] nameOfHookMethodsCamera = {
            "test 1",
            "test 2"
    };

    private static final String[] nameOfPossibleExceptions = {
            "test 1",
            "test 2"
    };

    /**
     * Analyzes an input String to give a detailed output of the failure.
     *
     * @return the result
     */
    public String run(String stackTrace, String exceptionName) {
        StringBuilder sb = new StringBuilder(INTRO);
        sb.append("\n" + MICRO);
        sb.append(checkMicro(stackTrace) ? YES : NO);
        sb.append("\n" + CAMERA);
        sb.append(checkCamera(stackTrace) ? YES : NO);
        sb.append("\n" + EXCEPTION);
        sb.append(checkException(exceptionName) ? YES : NO);
        return sb.toString();
    }

    private boolean checkMicro(String stackTraceString) {
        for (String name : nameOfHookMethodsAudio) {
            if (stackTraceString.contains(name)) {
                return true;
            }
        }
        return false;
    }


    private boolean checkCamera(String stackTraceString) {
        for (String name : nameOfHookMethodsCamera) {
            if (stackTraceString.contains(name)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkException(String exceptionName) {
        for (String name : nameOfHookMethodsCamera) {
            if (exceptionName.equals(name)) {
                return true;
            }
        }
        return false;
    }
}
