package org.nantes.extensible.tp1.extensions;

import org.nantes.extensible.tp1.framework.AnnotationAfficheur;

public class AfficheurAnnote {
	
	@AnnotationAfficheur
	public void afficher(Object object){
		System.out.println("[Methode afficher]\n" + object);
	}
	
	@AnnotationAfficheur
	public void afficherBis(Object object){
		System.out.println("[Methode afficherBis]\n" + object);
	}	
}