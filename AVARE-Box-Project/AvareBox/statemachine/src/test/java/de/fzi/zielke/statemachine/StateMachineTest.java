package de.fzi.zielke.statemachine;

import org.junit.BeforeClass;
import org.junit.Test;

import de.fzi.zielke.statemachine.enums.CameraState;
import de.fzi.zielke.statemachine.enums.MicrophoneState;

import static org.junit.Assert.*;

/**
 * Tests if state machine methods working as expected.
 */
public class StateMachineTest {

    private static StateMachine stateMachine;

    @BeforeClass
    public static void setUpClass() throws Exception {
        stateMachine = StateMachine.getInstance();
    }

    @Test
    public void getInstance() {
        assertNotNull(StateMachine.getInstance());
    }

    @Test
    public void getAppState() {
        stateMachine.setAppState(AppState.BLOCKED);
        assertEquals(stateMachine.getAppState(), AppState.BLOCKED);
    }

    @Test
    public void setAppState() {
        stateMachine.setAppState(AppState.BLOCKED);
    }


    @Test
    public void nextCameraState() {
        assertEquals(CameraState.BLOCKED, stateMachine.getCameraState());
        stateMachine.nextCameraState();
        assertEquals(CameraState.BLACK_PICTURE, stateMachine.getCameraState());
        stateMachine.nextCameraState();
        assertEquals(CameraState.NEUTRAL_PICTURE, stateMachine.getCameraState());
        stateMachine.nextCameraState();
        assertEquals(CameraState.PIXEL_PERSONS, stateMachine.getCameraState());
        stateMachine.nextCameraState();
    }

    @Test
    public void nextMicrophoneState() {
        assertEquals(MicrophoneState.BLOCKED, stateMachine.getMicrophoneState());
        stateMachine.nextMicrophoneState();
        assertEquals(MicrophoneState.NO_SOUND, stateMachine.getMicrophoneState());
        stateMachine.nextMicrophoneState();
        assertEquals(MicrophoneState.NEUTRAL_SOUND, stateMachine.getMicrophoneState());
        stateMachine.nextMicrophoneState();
    }
}