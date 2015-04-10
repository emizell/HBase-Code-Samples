package util;

public class Incrementor {
  
	private volatile static Incrementor instance = null;
	private Integer value = 1;
  
	protected Incrementor() {
      // Exists only to defeat instantiation.
	}
	
	public static Incrementor getInstance() {
		if (instance == null) {
			synchronized (Incrementor.class) {
				if (instance == null) {
					instance = new Incrementor();
				}
			}
			
		}
		return instance;
	}
	
	public synchronized Integer returnNextNumber() {
		return value++;
	}
}