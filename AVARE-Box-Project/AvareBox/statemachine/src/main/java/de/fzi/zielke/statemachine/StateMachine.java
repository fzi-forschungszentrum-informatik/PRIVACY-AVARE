package de.fzi.zielke.statemachine;

import android.content.SharedPreferences;

import de.fzi.zielke.statemachine.enums.AppState;
import de.fzi.zielke.statemachine.enums.CameraState;
import de.fzi.zielke.statemachine.enums.MicrophoneState;

/**
 * This class represents the different steps for Audio an Camera actions. Also the userinput is stored.
 */
public final class StateMachine {

    private AppState appState;
    private MicrophoneState microphoneState;
    private CameraState cameraState;
    private static StateMachine stateMachine;

    //no object creation allowed (Singleton)
    private StateMachine() {
        microphoneState = MicrophoneState.BLOCKED;
        cameraState = CameraState.BLOCKED;
    }

    /**
     * @return the state machine instance
     */
    public static StateMachine getInstance() {
        if (stateMachine == null) {
            stateMachine = new StateMachine();
        }
        return stateMachine;
    }

    /**
     * The permission entered by the user.
     *
     * @return BLOCKED - access denied
     *         FILTERED - intervention steps should be taken
     *         ALLOWED - camera and microphone access granted
     */
    public AppState getAppState() {
        return appState;
    }

    /**
     * Sets the user permission.
     *
     * @param appState: BLOCKED - access denied
     *      *         FILTERED - intervention steps should be taken
     *      *         ALLOWED - camera and microphone access granted
     */
    public void setAppState(AppState appState) {
        this.appState = appState;
    }

    /**
     *
     * @return the actual microphone intervention level
     *
     */
    public MicrophoneState getMicrophoneState() {
        return microphoneState;
    }

    /**
     *
     * @return the actual camera intervention level
     */
    public CameraState getCameraState() {
        return cameraState;
    }

    /**
     * Sets the next microphone intervention level.
     */
    public void nextMicrophoneState() {
        switch (microphoneState) {
            case BLOCKED:
                microphoneState = MicrophoneState.NO_SOUND;
                break;
            case NO_SOUND:
                microphoneState = MicrophoneState.NEUTRAL_SOUND;
                break;
        }
    }

    /**
     * Sets the next camera intervention level.
     */
    public void nextCameraState() {
        switch (cameraState) {
            case BLOCKED:
                cameraState = CameraState.BLACK_PICTURE;
                break;
            case BLACK_PICTURE:
                cameraState = CameraState.NEUTRAL_PICTURE;
                break;
            case NEUTRAL_PICTURE:
                cameraState = CameraState.PIXEL_PERSONS;
                break;
        }
    }


}
