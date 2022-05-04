package Cat;

import javax.swing.JButton;

import org.lwjgl.input.Keyboard;

/**
 * 
 * @author Unknown
 *
 */
public class BetterJButton extends JButton{
	private int key;
	
	public BetterJButton() {
		this("按键: NONE");
	}

	public BetterJButton(String buttpnName) {
		super(buttpnName);
	}
	
	public void setKeyBind(int newKey) {
		key = newKey;
	}
	
	public int getKey() {
		return key;
	}
	
	public String getKeyName() {
		return Keyboard.getKeyName(key);
	}
}
