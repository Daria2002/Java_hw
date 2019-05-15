package hr.fer.zemris.java.gui.prim;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.junit.jupiter.api.Test;

class PrimTest {

	@Test
	void testPrimListModel() {
		PrimListModel plm = new PrimListModel();
		
		plm.next();
		assertEquals(1, plm.prim.get(0));
		plm.next();
		assertEquals(2, plm.prim.get(1));
		plm.next();
		assertEquals(3, plm.prim.get(2));
		plm.next();
		assertEquals(5, plm.prim.get(3));
		plm.next();
		assertEquals(7, plm.prim.get(4));
	}
	
	void GetSizeTest() {
		PrimListModel plm = new PrimListModel();
		
		plm.next();
		plm.next();
		plm.next();
		plm.next();
		plm.next();
		
		assertEquals(5, plm.getSize());
	}

	void GetElementAtIndex() {
		PrimListModel plm = new PrimListModel();
		
		plm.next();
		plm.next();
		plm.next();
		plm.next();
		plm.next();
		
		assertEquals(1, plm.getElementAt(0));
		assertEquals(2, plm.getElementAt(1));
		assertEquals(3, plm.getElementAt(2));
		assertEquals(5, plm.getElementAt(3));
		assertEquals(7, plm.getElementAt(4));
	}
}
