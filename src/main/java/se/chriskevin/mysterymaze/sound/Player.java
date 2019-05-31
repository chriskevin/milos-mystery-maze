package se.chriskevin.mysterymaze.sound;

public class Player {

    /**
     * soundFileLocation stores the sounds position on the harddrive.
     */
    public static String[] soundFileLocation = new String[17];
    private SoundPlayer channel1;
    private SoundPlayer channel2;
    private SoundPlayer channel3;


    /**
     * This method initiates the array with the sounds.
     */
    public Player() {
        setSounds();
    }

    /**
     * This method represents a channel on the soundcard. This is the only
     * channel that can loop a musictrack so it is best used for
     * backgroundmusic. It can be used for a track to play ones.
     * The boolean loop sets if loop is active or not. If true the track loops
     * and if false the track only plays ones.
     * int soundFile represents the number in the initiated array of urls of
     * witch track to be played.
     * @param soundFile
     * @param loop
     */
    public void playChannel1(int soundFile, boolean loop) {
        channel1 = new SoundPlayer(soundFileLocation[soundFile], loop);
        channel1.start();
    }

    /**
     * this method stops the playiong of channel1
     */
    public void stopChannel1() {
        channel1.stopPlay();
    }

    /**
     * This method plays a music file represented by a predfined url in the
     * array soundFileLocation.
     * @param soundFile
     */
    public void playChannel2(int soundFile) {
        channel2 = new SoundPlayer(soundFileLocation[soundFile]);
        channel2.start();
    }

    /**
     * this method stops the playiong of channel2
     */
    public void stopChannel2() {
        channel2.stopPlay();
    }

    /**
     * This method plays a music file represented by a predfined url in the
     * array soundFileLocation.
     * @param soundFile
     */
    public void playChannel3(int soundFile) {
        channel3 = new SoundPlayer(soundFileLocation[soundFile]);
        channel3.start();
    }

    /**
     * this method stops the playiong of channel2
     */
    public void stopChannel3() {
        channel3.stopPlay();
    }

    /**
     * This method sets the sound locations of the sounds implemented in the
     */
    public void setSounds() {
        soundFileLocation[1] = "Ljudfiler\\BackgroundMusic\\MenuBackground.wav";
        soundFileLocation[2] = "Ljudfiler\\BackgroundMusic\\mainTheme.wav";
        soundFileLocation[3] = "Ljudfiler\\Mutant\\Mutant_Get_Egg.wav";
        soundFileLocation[4] = "Ljudfiler\\Mutant\\Mutant_Leave_Egg.wav";
        soundFileLocation[5] = "Ljudfiler\\Mutant\\Mutant_Wall_Collide.wav";
        soundFileLocation[6] = "Ljudfiler\\Mutant\\Mutant_Ball_Bite.wav";
        soundFileLocation[7] = "Ljudfiler\\Mutant\\Mutant_Mutant_Collide.wav";
        soundFileLocation[8] = "Ljudfiler\\Behemoth\\Behemoth_Growl.wav";
        soundFileLocation[9] = "Ljudfiler\\BackgroundMusic\\MenuChoice.wav";
        soundFileLocation[10] = "Ljudfiler\\Mutant\\Mutant_End_Win.wav";
        soundFileLocation[11] = "Ljudfiler\\Mutant\\Mutant_End_Loose.wav";
        soundFileLocation[12] = "Ljudfiler\\Rakh\\Rakh_Pickup_Sound.wav";
        soundFileLocation[13] = "Ljudfiler\\Mutant\\Mutant_Rakh_Pickup.wav";
        soundFileLocation[14] = "Ljudfiler\\Mutant\\Mutant_Behemoth_Clash.wav";
        soundFileLocation[15] = "Ljudfiler\\Rakh\\Rakh_Drop_Sound.wav";
    }
}