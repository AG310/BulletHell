import javafx.scene.media.AudioClip;

public class AudioPlayer {
	private AudioClip cursorHover;
	private AudioClip gameMusic;
	private AudioClip wind;
	private AudioClip death;
	private AudioClip select;
	
	public AudioPlayer() {
		cursorHover = new AudioClip(getClass().getResource("cursorHover.wav").toString());
		gameMusic = new AudioClip(getClass().getResource("gameMusic.wav").toString());
		wind = new AudioClip(getClass().getResource("wind.wav").toString());
		death = new AudioClip(getClass().getResource("death.wav").toString());
		select = new AudioClip(getClass().getResource("select.wav").toString());
		cursorHover.setRate(2);
		death.setRate(2);
	}

	public void playCursorHover() {
		cursorHover.play();
	}
	
	public void playWind() {
		wind.setCycleCount(AudioClip.INDEFINITE);
		wind.play();
	}
	
	public void stopWind() {
		if(wind.isPlaying()) {
			wind.stop();
		}
	}
	
	public void playMusic() {
		gameMusic.setCycleCount(AudioClip.INDEFINITE);
		gameMusic.play();
	}
	
	public void stopMusic() {
		if(gameMusic.isPlaying()) {
			gameMusic.stop();
		}
	}
	
	public void playDeath() {
		death.play();
	}
	
	public void playSelect() {
		select.play();
	}
}
