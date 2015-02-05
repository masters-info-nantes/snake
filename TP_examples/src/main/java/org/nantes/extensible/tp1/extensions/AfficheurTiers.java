package org.nantes.extensible.tp1.extensions;

import org.nantes.extensible.tp1.framework.Afficheur;

public class AfficheurTiers implements Afficheur{

	public void afficherUnObjet(Object obj) {
		System.out.println(obj);
	}
}
