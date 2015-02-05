package org.nantes.extensible.tp1.framework;

import java.io.IOException;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class BeanLoader {
	
	private static Object createInstanceFromFile(Reader file) throws Exception {
    	Properties properties = new Properties();
    	
    	try {
			properties.load(file);
		} catch (IOException e) {
			throw new Exception("File not found: " + file);
		}
    	
    	Class<?> classe;
		try {
			classe = Class.forName(properties.getProperty("class"));
		} catch (ClassNotFoundException e) {
			throw new Exception("class parameter not found in config file");
		}
		
    	Object objet;
		try {
			objet = classe.newInstance();
		} catch (Exception e) {
			throw new Exception("Cannot instanciate objects from " + classe.getName());
		}
    	return objet;
	}
	
	public static Object loadInstanceFromFile(Reader file) throws Exception{
    	Properties properties = new Properties();
    	
		try {
			properties.load(file);
		} catch (IOException e) {
			throw new Exception("File not found: " + file);
		}
    	
    	Class<?> classe;
		try {
			classe = Class.forName(properties.getProperty("class"));
		} catch (ClassNotFoundException e) {
			throw new Exception("class parameter not found in config file");
		}
		
    	Object objet;
		try {
			objet = classe.newInstance();
		} catch (Exception e) {
			throw new Exception("Cannot instanciate objects from " + classe.getName());
		}
    	
    	@SuppressWarnings("rawtypes")
		Enumeration enumeration = properties.keys();  
    	while (enumeration.hasMoreElements()) {  
    		String key = (String) enumeration.nextElement();  
    	    
    	    if("class".equals(key)){
    	    	continue;
    	    }
    	    
    	    String val = (String) properties.get(key);
    	    String keyFirstUC = key.substring(0,1).toUpperCase() + key.substring(1);
    	    
    	    // Getter to get parameter type
    	    Method methodGet = classe.getMethod("get" + keyFirstUC);
    	    Class<?> returnType = methodGet.getReturnType(); 
    	    
    	    Method method = classe.getMethod("set" + keyFirstUC, returnType);
    	    method.invoke(objet, val);  	    
    	} 
    	
    	return objet;
	}

	public static Afficheur loadAfficheurFromFile(Reader file) throws Exception{
		Object object = BeanLoader.createInstanceFromFile(file);
    	if(!Afficheur.class.isAssignableFrom(Afficheur.class)){
        	throw new RuntimeException("Loaded class is not a subclass of Afficheur");    		
    	}
    	return (Afficheur)object;
	}

	public static List<Method> loadAnnotedMethod(Class<?> classe, Class<? extends Annotation> annotation) throws ClassNotFoundException{
		Method[] methodes = classe.getMethods();
		List<Method> methodesAnnotes = new ArrayList<Method>();
		
		for(Method method:methodes){
			if(method.isAnnotationPresent(annotation)){
				methodesAnnotes.add(method);
			}
		}
		return methodesAnnotes;
	}

	public static List<Method> loadRemoteMethod(String className, URL path, Class<? extends Annotation> annotation) throws ClassNotFoundException{
		URLClassLoader loader = new URLClassLoader(new URL[]{path});
		Class<?> classe = loader.loadClass(className); // Class.forName(className, true, loader);
		return loadAnnotedMethod(classe, annotation);
	}
}
