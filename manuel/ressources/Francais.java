package com.francais;

import com.hello.interfaces.Speak;

public class Francais implements Speak{

	@Override
	public void sayHello() {
		System.out.println("Bonjour");
	}
}
