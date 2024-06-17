package asciiPanel;

import java.awt.*;

public class AsciiCharacterData {
	public AsciiCharacterData() {}

	public AsciiCharacterData(char character, Color foregroundColor, Color backgroundColor) {
		this.character = character;
		this.foregroundColor = foregroundColor;
		this.backgroundColor = backgroundColor;
	}

	public char character;
	public Color foregroundColor;
	public Color backgroundColor;
}
