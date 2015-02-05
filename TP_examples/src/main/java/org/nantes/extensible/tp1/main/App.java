package org.nantes.extensible.tp1.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.nantes.extensible.tp1.framework.Afficheur;
import org.nantes.extensible.tp1.framework.BeanLoader;
import org.nantes.extensible.tp1.framework.AnnotationAfficheur;

public class App 
{
    public static void main( String[] args ) throws FileNotFoundException, Exception
    {	
    	TP31();
    }
    
    public static FileReader openFile(String filename) throws FileNotFoundException{
    	// return new FileReader(App.class.getResource(filename).toString());
    	// java.class.path
    	return new FileReader("src/main/resources/" + filename);
    }
    
    public static void TP1() throws FileNotFoundException, Exception{
    	Object objet = BeanLoader.loadInstanceFromFile(App.openFile("personnes.txt"));
    	Afficheur afficheur = BeanLoader.loadAfficheurFromFile(App.openFile("afficheur.txt"));
    	afficheur.afficherUnObjet(objet);
    }

    public static void TP2() throws FileNotFoundException, Exception{
    	Object objet = BeanLoader.loadInstanceFromFile(App.openFile("personnes.txt"));    	
		Class<?> classe = Class.forName("org.nantes.extensible.tp1.extensions.AfficheurAnnote");
		
    	List<Method> methodesAfficheur = BeanLoader.loadAnnotedMethod(classe, AnnotationAfficheur.class);
    	for(Method method: methodesAfficheur){
    		method.invoke(method.getClass().newInstance(), objet);
    		System.out.println();
    	}    
    }

    public static void TP31() throws ClassNotFoundException, MalformedURLException{
    	URL url = new URL("file:///Volumes/Data/TP/LogicielExtensible/TP1/tp1/target/tp1-0.0.1-SNAPSHOT.jar");
    	List<Method> methodesAfficheur = BeanLoader.loadRemoteMethod("org.nantes.extensible.tp1.extensions.AfficheurAnnote", url, AnnotationAfficheur.class);
    	for(Method method: methodesAfficheur){
    		System.out.println(method.getName());
    	}        	
    }
    
    // Not Work
    public static void TP32() throws MalformedURLException, ClassNotFoundException{
		
    	File directory = new File(/*"file://"+*/"/Volumes/Data/TP/LogicielExtensible/TP1/tp1/target/");
    	List<File> directories = new ArrayList<File>();
    	directories.add(directory);

    	while(!directories.isEmpty()){

    		directory = directories.remove(directories.size()-1);
	    	if (directory != null && directory.exists()) {

	            // Get the list of the files contained in the package
	            String[] files = directory.list();	        
	            for (int i = 0; i < files.length; i++) {
	            	File f = new File(files[i]);
	            	if(f.isDirectory()){
	            		directories.add(f);
	            		continue;
	            	}
	            	
	                // we are only interested in .class files
	                if (f.getName().endsWith(".class")) {
	                    System.out.println("ClassDiscovery: className = " + f.getName());
	                }
	                else{
	                	
	                }
	            }
	        }
    	}
    	
    	/*
    	URLClassLoader loader = new URLClassLoader(new URL[]{url});
		Class<?> classe = loader.loadClass("org.nantes.extensible.tp1.extensions.AfficheurAnnote");
		System.out.println("Bonjour "+classe.getName());
		*/
    }
}
