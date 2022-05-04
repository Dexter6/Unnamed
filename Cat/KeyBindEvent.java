package Cat;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import org.lwjgl.input.Keyboard;

/**
 * 
 * @author Unknown
 *
 */
public class KeyBindEvent extends KeyAdapter {
	private final BetterJButton jButton;

	public KeyBindEvent(BetterJButton betterJButton) {
		jButton = betterJButton;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		jButton.setKeyBind(Keyboard.getKeyIndex(String.valueOf(e.getKeyChar()).toUpperCase()));
		jButton.setText("按键: " + jButton.getKeyName());
	}
}
