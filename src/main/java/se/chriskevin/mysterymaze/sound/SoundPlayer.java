package se.chriskevin.mysterymaze.sound;

/**
 * This class is complete and should not be altered.
 *
 * <p>This is the main Wavplayer class that picks up wavefiles and plays them in the soundcards
 * different channels. The code implements a loop function and a stop function. The class has three
 * different contructors taking different arguments such as if we want to loop, set a starting
 * position in the track and simply play the track ones.
 */
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundPlayer extends Thread {

  /**
   * filename = the filename to open.
   *
   * <p>loop = false; do not loop, true; loop the track.
   *
   * <p>position = position to start the track from
   *
   * <p>EXTRENAL_BUFFER_SIZE = amount of buffer, this decides the latency, less buffersize = less
   * latency
   *
   * <p>channel = the output channel where we send a byte stream to the sound card
   */
  private String filename;

  private boolean loop = false;
  private Position curPosition;
  private final int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb
  private SourceDataLine channel = null;

  /** This counstructor sets the position of the track to be played default is normal */
  public enum Position {
    LEFT,
    RIGHT,
    NORMAL
  }

  /**
   * This constructor takes a url and playes the sound located at that url
   *
   * @param wavfile
   */
  public SoundPlayer(final String wavfile) {
    filename = wavfile;
    curPosition = Position.NORMAL;
  }

  /**
   * This counstructor does something straaaaaange, noone knows, but noone will ever watch this
   * documentation so i don´t care.
   *
   * @param wavfile
   * @param p
   */
  public SoundPlayer(final String wavfile, final Position p) {
    curPosition = p;
  }

  /**
   * This constructor initiates a track to be played in a loop the boolean decides if the track will
   * loop or not. true = loop false = noloop String Wavefile brings the URL to the file to be played
   *
   * @param wavfile
   * @param loop
   */
  public SoundPlayer(final String wavfile, final boolean loop) {
    this.loop = loop;
    filename = wavfile;
    curPosition = Position.NORMAL;
  }

  /*
   * Stops the current track playing the selected file
   */
  public void stopPlay() {
    loop = false;
    channel.stop();
    channel.drain();
    channel.close();
  }

  /*
   * loops the track if loop is set to true
   */
  public void loopPlay() {
    channel.stop();
    run();
  }

  @Override

  /*
   * This threads main run function. It reads the content of the file
   * and writes it to the soundcard via a bit stream.
   */
  public void run() {
    final var soundFile = new File(filename);

    if (!soundFile.exists()) {
      System.err.println("Wave file not found: " + filename);
      return;
    }

    AudioInputStream audioInputStream = null;
    try {
      audioInputStream = AudioSystem.getAudioInputStream(soundFile);
    } catch (final UnsupportedAudioFileException | IOException e) {
      throw new RuntimeException(e);
    }

    final var format = audioInputStream.getFormat();
    final var info = new DataLine.Info(SourceDataLine.class, format);

    try {
      channel = (SourceDataLine) AudioSystem.getLine(info);
      channel.open(format);
    } catch (final Exception e) {
      throw new RuntimeException(e);
    }

    if (channel.isControlSupported(FloatControl.Type.PAN)) {
      final var pan = (FloatControl) channel.getControl(FloatControl.Type.PAN);
      if (curPosition == Position.RIGHT) {
        pan.setValue(1.0f);
      } else if (curPosition == Position.LEFT) {
        pan.setValue(-1.0f);
      }
    }

    channel.start();
    var nBytesRead = 0;
    var abData = new byte[EXTERNAL_BUFFER_SIZE];

    try {
      while (nBytesRead != -1) {
        nBytesRead = audioInputStream.read(abData, 0, abData.length);
        if (nBytesRead >= 0) {
          channel.write(abData, 0, nBytesRead);
        }
      }
    } catch (final IOException e) {
      throw new RuntimeException(e);
    } finally {
      if (loop == true) {
        loopPlay();
      } else {
        channel.drain();
        channel.close();
      }
    }
  }
}
