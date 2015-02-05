package org.nantes.extensible.tp1.extensions;

public class Personne {
	private String nom;
	private String prenom;

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	@Override
	public String toString(){
		return "[nom] " + this.nom + "\n" + "[prenom] " + this.prenom;
	}
}
