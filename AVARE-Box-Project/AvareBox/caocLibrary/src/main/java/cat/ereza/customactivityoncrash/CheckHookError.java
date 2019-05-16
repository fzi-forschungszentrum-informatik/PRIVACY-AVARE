package cat.ereza.customactivityoncrash;


import de.fzi.zielke.statemachine.StateMachine;

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


    //TODO complete values
    private static final String[] nameOfHookMethodsAudio = {
            "Hook_MediaRecorder_start",
            "Hook_MediaRecorder_stop",
            "Hook_MediaRecorder_setOutPutFile",
            "Test"
    };

    private static final String[] nameOfHookMethodsCamera = {
            "test 1",
            "test 2"
    };

    private StateMachine stateMachine;

    public CheckHookError() {
        stateMachine = new StateMachine();
    }

    /**
     * Analyzes an input String to give a detailed output of the failure.
     *
     * @return the result
     */
    public String run(String stackTrace) {
        StringBuilder sb = new StringBuilder(INTRO);
        sb.append("\n" + MICRO);
        sb.append(checkMicro(stackTrace) ? YES : NO);
        sb.append("\n" + CAMERA);
        sb.append(checkCamera(stackTrace) ? YES : NO);

        return sb.toString();
    }

    private boolean checkMicro(String stackTraceString) {
        for (String name : nameOfHookMethodsAudio) {
            if (stackTraceString.contains(name)) {
                stateMachine.nextMicrophoneState();
                return true;
            }
        }
        return false;
    }


    private boolean checkCamera(String stackTraceString) {
        for (String name : nameOfHookMethodsCamera) {
            if (stackTraceString.contains(name)) {
                stateMachine.nextCameraState();
                return true;
            }
        }
        return false;
    }
}
