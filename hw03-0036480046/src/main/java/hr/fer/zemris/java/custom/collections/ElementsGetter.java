package hr.fer.zemris.java.custom.collections;

public interface ElementsGetter {
	
	abstract boolean hasNextElement();
	abstract Object getNextElement();
	
	default void processRemaining(Processor p) {
		while(hasNextElement()) {
			p.process(getNextElement());
		}
	}
}
