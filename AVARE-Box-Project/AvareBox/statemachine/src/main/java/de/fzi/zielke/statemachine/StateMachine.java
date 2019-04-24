package de.fzi.zielke.statemachine;

import de.fzi.zielke.statemachine.enums.CameraState;
import de.fzi.zielke.statemachine.enums.MicrophoneState;

/**
 * This class represents the different steps for Audio an Camera actions. The data is stored in the config.json file.
 */
public final class StateMachine {

    private MicrophoneState microphoneState;
    private CameraState cameraState;
    private JSONParser jsonParser;

    /**
     * Returns a state machine object, which is initialized with the values from the preferences.json file.
     *
     */
    public StateMachine() {
        jsonParser = new JSONParser();
        microphoneState = readMicString(jsonParser.getMicrophoneState());
        cameraState = readCameraString(jsonParser.getCameraState());
    }


    /**
     * Converts a config String to its corresponding enum value for better usability.
     *
     * @param microphoneState the extracted String from the JSON File
     * @return an enum state
     */
    private MicrophoneState readMicString(String microphoneState) {
        switch (microphoneState) {
            case "hard": return MicrophoneState.BLOCKED;
            case "softEmptyNoise": return MicrophoneState.NO_SOUND;
            case "softSignalNoise": return MicrophoneState.NEUTRAL_SOUND;
            case "enabled": return  MicrophoneState.ENABLED;
            default: return MicrophoneState.BLOCKED;
        }
    }

    /**
     * Converts a config String to its corresponding enum value for better usability.
     *
     * @param cameraState the extracted String from the JSON File
     * @return an enum state
     */
    private CameraState readCameraString(String cameraState) {
        switch (cameraState) {
            case "hard": return CameraState.BLOCKED;
            case "softBlackPicture": return CameraState.BLACK_PICTURE;
            case "softNeutralPicture": return CameraState.NEUTRAL_PICTURE;
            case "enabled": return CameraState.ENABLED ;
            case "filtered": return CameraState.PIXELED;
            default: return CameraState.BLOCKED;
        }
    }

    /**
     *
     * @return the actual microphone state.
     *
     */
    public MicrophoneState getMicrophoneState() {
        return microphoneState;
    }

    /**
     *
     * @return the actual camera state
     */
    public CameraState getCameraState() {
        return cameraState;
    }

    /**
     * Sets the next microphone intervention level.
     */
    public void nextMicrophoneState() {
        switch (getMicrophoneState()) {
            case BLOCKED:
                microphoneState = MicrophoneState.NO_SOUND;
                jsonParser.setMicrophoneState("softEmptyNoise");
                break;
            case NO_SOUND:
                microphoneState = MicrophoneState.NEUTRAL_SOUND;
                jsonParser.setMicrophoneState("softSignalNoise");
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
                jsonParser.setCameraState("softBlackPicture");
                break;
            case BLACK_PICTURE:
                cameraState = CameraState.NEUTRAL_PICTURE;
                jsonParser.setCameraState("softNeutralPicture");
                break;
        }
    }


}
