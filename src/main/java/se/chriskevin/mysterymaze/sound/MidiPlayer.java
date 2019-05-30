package se.chriskevin.mysterymaze.sound;

import javax.sound.midi.*;
import java.io.*;
import java.net.URISyntaxException;

public class MidiPlayer {

    public static void play(String filename) {
        if (!filename.endsWith(".mid")) {
            helpAndExit();
        }

        File midiFile = null;

        try {
            midiFile = new File(MidiPlayer.class.getResource(filename).toURI());
        } catch (URISyntaxException ex) {
            helpAndExit();
        }

        //File midiFile = new File(filename);

        if (!midiFile.exists() || midiFile.isDirectory() || !midiFile.canRead()) {
            helpAndExit();
        }

        // Play once
        try {
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.setSequence(MidiSystem.getSequence(midiFile));
            System.out.println(sequencer.getDeviceInfo());
            sequencer.open();
            sequencer.start();
            while (true) {
                if (sequencer.isRunning()) {
                    try {
                        Thread.sleep(1000); // Check every second
                    } catch(InterruptedException ignore) {
                        break;
                    }
                } else {
                    break;
                }
            }
            // Close the MidiDevice & free resources
            sequencer.stop();
            sequencer.close();
        } catch (MidiUnavailableException mue) {
            System.out.println("Midi device unavailable!");
        } catch (InvalidMidiDataException imde) {
            System.out.println("Invalid Midi data!");
        } catch (IOException ioe) {
            System.out.println("I/O Error!" +  ioe);
        }
    }

    /** Provides help message and exits the program */
    private static void helpAndExit() {
        System.out.println("Usage: java MidiPlayer midifile.mid");
    }
}