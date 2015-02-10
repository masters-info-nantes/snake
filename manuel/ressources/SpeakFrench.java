package frenchspeak;

import hello.interfaces.Speak;

public class SpeakFrench implements Speak{

	@Override
	public void sayHello() {
		System.out.println("Bonjour");
	}
}
