package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw05.db.IFilter;
import hr.fer.zemris.java.hw05.db.QueryFilter;
import hr.fer.zemris.java.hw05.db.QueryParser;
import hr.fer.zemris.java.hw05.db.StudentDatabase;
import hr.fer.zemris.java.hw05.db.StudentRecord;


import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.ConditionalExpression;
import hr.fer.zemris.java.hw05.db.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.IComparisonOperator;

@SuppressWarnings("javadoc")
public class test {

	@Test
	public void testLikeOperator() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		
		assertEquals(oper.satisfied("Zagreb", "Aba*"), false); 
		assertEquals(oper.satisfied("AAA", "AA*AA"), false); 
		assertEquals(oper.satisfied("AAAA", "AA*AA"), true);
		assertEquals(oper.satisfied("ABCD", "*"), true);
		assertEquals(oper.satisfied("Akšamović", "*ić"), true);
		assertEquals(oper.satisfied("Marko", "M*o"), true);
	}
	
	@Test
	public void noWildCard() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(oper.satisfied("Zagreb", "Zagreb"), true);
		assertEquals(oper.satisfied("Zagreb", "Zagrebb"), false);
	}
	
	@Test
	public void tooManyWildcards() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertThrows(Exception.class, () -> oper.satisfied("asdasd", "asd**"));
	}

	@Test
	public void wildcardsInFirstString() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertThrows(Exception.class, () -> oper.satisfied("as*dasd", "asd*"));
	}
	
	@Test
	public void testLess() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		
		assertEquals(oper.satisfied("Ana", "Jasna"), true);
		assertEquals(oper.satisfied("Ana", "A"), false);
		assertEquals(oper.satisfied("Ana", "Ana"), false);
	}
	
	@Test
	public void testLessOrEquals() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		
		assertEquals(oper.satisfied("Ana", "Jasna"), true);
		assertEquals(oper.satisfied("Ana", "A"), false);
		assertEquals(oper.satisfied("Ana", "Ana"), true);
	}
	
	@Test
	public void testGreater() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		
		assertEquals(oper.satisfied("Ana", "Jasna"), false);
		assertEquals(oper.satisfied("Ana", "A"), true);
		assertEquals(oper.satisfied("Ana", "Ana"), false);
	}
	
	@Test
	public void testGreaterOrEquals() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		
		assertEquals(oper.satisfied("Ana", "Jasna"), false);
		assertEquals(oper.satisfied("Ana", "A"), true);
		assertEquals(oper.satisfied("Ana", "Ana"), true);
	}
	
	@Test
	public void testEquals() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		
		assertEquals(oper.satisfied("a", "b"), false);
		assertEquals(oper.satisfied("asd", "asd"), true);
	}
	
	@Test
	public void testNotEquals() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		
		assertEquals(oper.satisfied("a", "b"), true);
		assertEquals(oper.satisfied("asd", "asd"), false);
	}
	
	@Test
    public void testLikeExpression() {
        ConditionalExpression expr = new ConditionalExpression(
                FieldValueGetters.LAST_NAME,
                "Bos*",
                ComparisonOperators.LIKE
                );
       
        StudentRecord record = new StudentRecord("0004", "Markovic", "Marko", 5);
        boolean recordSatisfies = expr.getComparisonOperator().satisfied(
                expr.getFieldGetter().get(record),
                expr.getStringLiteral()
                );
        assertEquals(recordSatisfies, false);
       
        record = new StudentRecord("0004", "Bosanac", "Marko", 5);
        recordSatisfies = expr.getComparisonOperator().satisfied(
                expr.getFieldGetter().get(record),
                expr.getStringLiteral()
                );
        assertEquals(recordSatisfies, true);
    }
   
    @Test
    public void testInvalidLikeExpression() {
        ConditionalExpression expr = new ConditionalExpression(
                FieldValueGetters.LAST_NAME,
                "*Bo*s",
                ComparisonOperators.LIKE
                );
        StudentRecord record = new StudentRecord("0004", "Markovic", "Marko", 5);
       
        assertThrows(IllegalArgumentException.class, () -> { expr.getComparisonOperator().satisfied(
                expr.getFieldGetter().get(record),
                expr.getStringLiteral()
                );});
        
       
    }
   
    @Test
    public void testNullRecordExpression() {
        ConditionalExpression expr = new ConditionalExpression(
                FieldValueGetters.LAST_NAME,
                "Bos**",
                ComparisonOperators.LIKE
                );
       
        assertThrows(IllegalArgumentException.class, () -> {expr.getComparisonOperator().satisfied(
                expr.getFieldGetter().get(null),
                expr.getStringLiteral()
                );});
        
        
    }
   
    @Test  
    public void testLessExpression() {
        ConditionalExpression expr = new ConditionalExpression(
                FieldValueGetters.FIRST_NAME,
                "Ana",
                ComparisonOperators.LESS
                );
       
        StudentRecord record = new StudentRecord("0004", "Markovic", "Jasna", 5);
        boolean recordSatisfies = expr.getComparisonOperator().satisfied(
                expr.getFieldGetter().get(record),
                expr.getStringLiteral()
                );
        assertEquals(recordSatisfies, false);
       
        record = new StudentRecord("0004", "Markovic", "A", 5);
        recordSatisfies = expr.getComparisonOperator().satisfied(
                expr.getFieldGetter().get(record),
                expr.getStringLiteral()
                );
        assertEquals(recordSatisfies, true);
       
        record = new StudentRecord("0004", "Markovic", "Ana", 5);
        recordSatisfies = expr.getComparisonOperator().satisfied(
                expr.getFieldGetter().get(record),
                expr.getStringLiteral()
                );
        assertEquals(recordSatisfies, false);
    }
   
    @Test  
    public void testGreaterExpression() {
        ConditionalExpression expr = new ConditionalExpression(
                FieldValueGetters.LAST_NAME,
                "Ana",
                ComparisonOperators.GREATER
                );
       
        StudentRecord record = new StudentRecord("0004", "Jasna", "Jasna", 5);
        boolean recordSatisfies = expr.getComparisonOperator().satisfied(
                expr.getFieldGetter().get(record),
                expr.getStringLiteral()
                );
        assertEquals(recordSatisfies, true);
       
        record = new StudentRecord("0004", "A", "A", 5);
        recordSatisfies = expr.getComparisonOperator().satisfied(
                expr.getFieldGetter().get(record),
                expr.getStringLiteral()
                );
        assertEquals(recordSatisfies, false);
       
        record = new StudentRecord("0004", "Ana", "Ana", 5);
        recordSatisfies = expr.getComparisonOperator().satisfied(
                expr.getFieldGetter().get(record),
                expr.getStringLiteral()
                );
        assertEquals(recordSatisfies, false);
    }
   
    @Test  
    public void testEqualsExpression() {
        ConditionalExpression expr = new ConditionalExpression(
                FieldValueGetters.JMBAG,
                "0004",
                ComparisonOperators.EQUALS
                );
       
        StudentRecord record = new StudentRecord("0004", "Markovic", "Jasna", 5);
        boolean recordSatisfies = expr.getComparisonOperator().satisfied(
                expr.getFieldGetter().get(record),
                expr.getStringLiteral()
                );
        assertEquals(recordSatisfies, true);
       
        record = new StudentRecord("4", "Markovic", "A", 5);
        recordSatisfies = expr.getComparisonOperator().satisfied(
                expr.getFieldGetter().get(record),
                expr.getStringLiteral()
                );
        assertEquals(recordSatisfies, false);
    }
   
    @Test  
    public void testNotEqualsExpression() {
        ConditionalExpression expr = new ConditionalExpression(
                FieldValueGetters.JMBAG,
                "0004",
                ComparisonOperators.NOT_EQUALS
                );
       
        StudentRecord record = new StudentRecord("0004", "Markovic", "Jasna", 5);
        boolean recordSatisfies = expr.getComparisonOperator().satisfied(
                expr.getFieldGetter().get(record),
                expr.getStringLiteral()
                );
        assertEquals(recordSatisfies, false);
       
        record = new StudentRecord("4", "Markovic", "A", 5);
        recordSatisfies = expr.getComparisonOperator().satisfied(
                expr.getFieldGetter().get(record),
                expr.getStringLiteral()
                );
        assertEquals(recordSatisfies, true);
    }
    
    @Test
    public void getNullRecordName() {
        
        assertThrows(IllegalArgumentException.class, () -> {FieldValueGetters.FIRST_NAME.get(null);});
    }
   
    @Test
    public void getNullRecordLastName() {
    	 assertThrows(IllegalArgumentException.class, () -> {FieldValueGetters.LAST_NAME.get(null);});
    }
   
    @Test
    public void getNullRecordJMBAG() {
    	 assertThrows(IllegalArgumentException.class, () -> {FieldValueGetters.JMBAG.get(null);});
    }
   
    @Test
    public void getRecordAllRecordValues() {
        StudentRecord newRecord = new StudentRecord("0004", "Markovic", "Marko", 5);
       
        assertEquals(FieldValueGetters.FIRST_NAME.get(newRecord), "Marko");
        assertEquals(FieldValueGetters.LAST_NAME.get(newRecord), "Markovic");
        assertEquals(FieldValueGetters.JMBAG.get(newRecord), "0004");
        assertNotEquals(FieldValueGetters.JMBAG.get(newRecord), "4");  
    }
    
    @Test
    public void testQueryFilterOneMatch() {
        String[] ocjene = new String[] {
                new String("0000000001  Akšamović\tMarin 2"),
                new String("0000000002  Bakamović\tPetra   3"),
                new String("0000000015  Glavinić Pecotić\tKristijan   4"),
                new String("0000000028  Kosa-nović\tNenad   5"),
                new String("0000000039  Martinec\tJelena  4")
        };
       
        StudentDatabase newDb = new StudentDatabase(Arrays.asList(ocjene));
        QueryParser parser = new QueryParser(" jmbag >= \"0000000002\" AND firstName LIKE \"P*\"");
        List<StudentRecord> filteredRecords = newDb.filter(new QueryFilter(parser.getQuery()));
       
        assertEquals(filteredRecords.size(), 1);
        assertEquals(filteredRecords.get(0).getFirstName(), "Petra");
    }
 
    @Test
    public void testQueryFilterFewMatches() {
        String[] ocjene = new String[] {
        		new String("0000000001  Akšamović\tMarin   2"),
                new String("0000000002  Bakamović\tPetra   3"),
                new String("0000000015  Glavinić Pecotić\tKristijan   4"),
                new String("0000000028  Kosanović\tNenad   5"),
                new String("0000000039  Martinec\tJelena  4")
        };
       
        StudentDatabase newDb = new StudentDatabase(Arrays.asList(ocjene));
        QueryParser parser = new QueryParser(" jmbag < \"0000000050\" AND lastName LIKE \"*ić\"");
        List<StudentRecord> filteredRecords = newDb.filter(new QueryFilter(parser.getQuery()));
       
        assertEquals(filteredRecords.size(), 4);
        assertEquals(filteredRecords.get(0).getFirstName(), "Marin");
        assertEquals(filteredRecords.get(3).getFirstName(), "Nenad");
    }
   
    @Test
    public void testQueryFilterNoMatches() {
        String[] ocjene = new String[] {
                new String("0000000001  Akšamović\tMarin   2"),
                new String("0000000002  Bakamović\tPetra   3"),
                new String("0000000015  Glavinić Pecotić\tKristijan   4"),
                new String("0000000028  Kosanović\tNenad   5"),
                new String("0000000039  Martinec\tJelena  4")
        };
       
        StudentDatabase newDb = new StudentDatabase(Arrays.asList(ocjene));
        QueryParser parser = new QueryParser(" jmbag < \"0000000050\" AND lastName LIKE \"*ić\" and jmbag = \"1000000\" ");
        List<StudentRecord> filteredRecords = newDb.filter(new QueryFilter(parser.getQuery()));
       
        assertEquals(filteredRecords.size(), 0);
    }
   
    @Test
    public void testDirectQuery() {
        String[] ocjene = new String[] {
        		new String("0000000001  Akšamović\tMarin   2"),
                new String("0000000002  Bakamović\tPetra   3"),
                new String("0000000015  Glavinić Pecotić\tKristijan   4"),
                new String("0000000028  Kosanović\tNenad   5"),
                new String("0000000039  Martinec\tJelena  4")
        };
       
        StudentDatabase newDb = new StudentDatabase(Arrays.asList(ocjene));
        QueryParser parser = new QueryParser("jmbag = \"0000000015\"");
       
        if(parser.isDirectQuery()) {
            StudentRecord r = newDb.forJMBAG(parser.getQueriedJMBAG());
            assertEquals(r.getFirstName(), "Kristijan");
            assertEquals(r.getLastName(), "Glavinić Pecotić");
            return;
        }
        fail();
    }
    
    @Test
    public void nullQueryTest() {
        
        assertThrows(IllegalArgumentException.class, () -> {new QueryParser(null);});
    }
   
    @Test
    public void emptyStringTest() {
        
        assertThrows(IllegalArgumentException.class, () -> {new QueryParser("            ").getQuery();});
    }
   
    @Test
    public void incompleteQueryTooMuch() {
        
        assertThrows(IllegalArgumentException.class, () -> {new QueryParser("   jmbag=\"123\" aNd ").getQuery();});
    }
   
    @Test
    public void incompleteQueryTooFew() {
       
        assertThrows(IllegalArgumentException.class, () -> {new QueryParser("   jmbag=").getQuery();});
    }
   
    @Test
    public void incompleteStringInQueryAtEnd() {
        assertThrows(IllegalArgumentException.class, () -> {new QueryParser("  firstName=\"Banana").getQuery();});
    }
   
    @Test
    public void incompleteStringInQueryMiddle() {
        new QueryParser(" firstName<=\"Krivo anD jmbag!=\"090909\" ");
        assertThrows(IllegalArgumentException.class, () -> {new QueryParser(" firstName<=\"Krivo anD jmbag!=\"090909\" ").getQuery();});
    }
   
    @Test
    public void reverseAttributeAndLiteral() {
        assertThrows(IllegalArgumentException.class, () -> {new QueryParser(" lastName     >= \"Opet krivo\"  And \"1*23\" LIKE jmbag").getQuery();});

    }
   
    @Test
    public void lowerCaseLike() {
        try{
            new QueryParser("firstName<=\"Krivo\" anD jmbag Like \"090909\" ");
        } catch(Exception e) {
            fail();
        }
    }
   
    @Test
    public void misspelledFirstName() {
        String x = "firstname<=\"Krivo\" anD jmbag LIKE \"090909\" ";
        assertThrows(IllegalArgumentException.class, 
    			() -> {new QueryParser(x).getQuery();} );
    }
   
    @Test
    public void misspelledJmbag() {
    	assertThrows(IllegalArgumentException.class, 
    			() -> {new QueryParser("firstName<=\"Krivo\" anD jmba LIKE \"090909\" ").getQuery();} );
    }
   
    @Test
    public void wrongOperator() {
    	assertThrows(IllegalArgumentException.class, 
    			() -> {new QueryParser("firstName=<\"Krivo\" anD jmbag LIKE \"090909\" ").getQuery();});
    }
   
    @Test
    public void wrongOperatorNotEquals() {

    	assertThrows(IllegalArgumentException.class, () -> {
        	new QueryParser("firstName!=\"Krivo\" anD jmbag =!\"090909\" ").getQuery();});
    }
   
    @Test
    public void attributeEqualsAttribute() {
        
        assertThrows(IllegalArgumentException.class, () -> {
        	new QueryParser(" jmbag != firstName").getQuery();});
    }
   
    @Test
    public void literalEqualsLiteral() {
        
        assertThrows(IllegalArgumentException.class, 
        		() -> {new QueryParser(" \"jmbag\" = \"firstName\" ").getQuery();});
    }
   
    @Test
    public void tooManyWildcards1() {
        
        assertThrows(IllegalArgumentException.class, () -> {new QueryParser(" jmbag != \"******\" and firstName LIKE \"Mir*ko*\"").getQuery();});
    }
   
    @Test
    public void isDirectTest() {
        QueryParser newParser = new QueryParser(" firstName=\"Banana\"");
        assertEquals(newParser.isDirectQuery(), false);
       
        newParser = new QueryParser(" jmbag = \"123\" ");
        assertEquals(newParser.isDirectQuery(), true);
       
        newParser = new QueryParser("jmbag >= \"123\" AND firstName=\"Banana\"");
        assertEquals(newParser.isDirectQuery(), false);
    }
   
    @Test
    public void getQueriedJmbagException() {
        QueryParser newParser = new QueryParser(" firstName=\"Banana\"");
      
        assertThrows(IllegalStateException.class, () -> {newParser.getQueriedJMBAG();});
    }
   
    @Test
    public void getQueriedJmbagAndQuery() {
        QueryParser newParser = new QueryParser(" jmbag=\"Banana\"");
        assertEquals(newParser.getQueriedJMBAG(), "Banana");
        assertEquals(newParser.getQuery().size(), 1);
    }
   
    @Test
    public void getQueryMultiple() {
        QueryParser newParser = new QueryParser(" jmbag=\"Banana\" and jmbag >= \"123\" And jmbag LIKE \"090909\"");
        assertEquals(newParser.getQuery().size(), 3);
    }
   
    @Test
    public void notADirectQuery() {
        QueryParser newParser = new QueryParser("     jmbag  <= \"Maki\" ");
        assertThrows(IllegalStateException.class, () -> {newParser.getQueriedJMBAG();});
    }
   
    @Test
    public void notADirectQuery2() {
        QueryParser newParser = new QueryParser("     jmbag  like \"Maki\" ");
    
        
        assertThrows(IllegalArgumentException.class, () -> {newParser.getQuery();});
    }
    
    
    @Test
    public void nullDatabase() {
        assertThrows(IllegalArgumentException.class, () -> { new StudentDatabase(null); } );
    }
 
    @Test
    public void testForJMBAG() {
        String[] ocjene = new String[] {
                new String("0000000001  Akšamović\tMarin   2"),
                new String("0000000002  Bakamović\tPetra   3"),
                new String("0000000015  Glavinić Pecotić\tKristijan   4")
        };
       
        StudentDatabase newDB = new StudentDatabase(Arrays.asList(ocjene));
       
        StudentRecord getRecord = newDB.forJMBAG(new String("2"));
        assertEquals(getRecord, null);
       
        StudentRecord getRecord1 = newDB.forJMBAG(new String("0000000002"));
        assertEquals(getRecord1.getFirstName(), "Petra");
       
        StudentRecord getRecord2 = newDB.forJMBAG(new String("0000000015"));
        assertEquals(getRecord2.getLastName(), "Glavinić Pecotić");
       
        assertEquals(newDB.forJMBAG("00021"), null);
    }
 
    @Test
    public void testFilterRemoveAll() {
        String[] ocjene = new String[] {
                new String("0000000001  Akšamović\tMarin   2"),
                new String("0000000002  Bakamović\tPetra   3"),
                new String("0000000015  Glavinić Pecotić\tKristijan   4"),
                new String("0000000028  Kosanović\tNenad   5"),
                new String("0000000039  Martinec\tJelena  4")
        };
       
        StudentDatabase newDb = new StudentDatabase(Arrays.asList(ocjene));
        List<StudentRecord> newRecords = newDb.filter( (record) -> false );
       
        assertEquals(newRecords.size(), 0);
    }
   
    @Test
    public void testFilterRemoveNone() {
        String[] ocjene = new String[] {
                new String("0000000001  Akšamović\tMarin   2"),
                new String("0000000002  Bakamović\tPetra   3"),
                new String("0000000015  Glavinić Pecotić\tKristijan   4"),
                new String("0000000028  Kosanović\tNenad   5"),
                new String("0000000039  Martinec\tJelena  4")
        };
       
        StudentDatabase newDb = new StudentDatabase(Arrays.asList(ocjene));
        List<StudentRecord> newRecords = newDb.filter( (record) -> true );
       
        assertEquals(newRecords.size(), 5);
    }
   
    @Test
    public void invalidDatabaseEntry() {
        String[] ocjene = new String[] {
                new String("0000000001  Akšamović Marin   2   Kifla"),
        };
       
        assertThrows(IllegalArgumentException.class, () -> {new StudentDatabase(Arrays.asList(ocjene));});
    }
   
    @Test
    public void invalidDatabaseEntry2() {
        String[] ocjene = new String[] {
                new String("0000000001  ovo     nijeDobro"),
        };
       
        assertThrows(IllegalArgumentException.class, () -> {new StudentDatabase(Arrays.asList(ocjene));});
 
    }
   
    @Test
    public void gradeNotANumber() {
        String[] ocjene = new String[] {
                new String("0000000001  isto    nijeDobro Keksi"),
        };
       
        assertThrows(IllegalArgumentException.class, () -> {new StudentDatabase(Arrays.asList(ocjene));});
    }
   
    @Test
    public void spacesNotTabs() {
        String[] ocjene = new String[] {
                new String("0000000001 isto nijeDobro 1"),
        };
       
        assertThrows(IllegalArgumentException.class, () -> {new StudentDatabase(Arrays.asList(ocjene));});
    }
   
    @Test
    public void multipleNameInput() {
        String[] ocjene = new String[] {
                new String("Keksi   Ovo bi trebao\tbiti valjan upis ja mislim  1"),
        };
       
        StudentDatabase newDb = null;
        try{
            newDb = new StudentDatabase(Arrays.asList(ocjene));
       
        } catch (Exception e) {
            fail();
        }
       
        StudentRecord onlyRecord = newDb.forJMBAG("Keksi");
 
        assertEquals(onlyRecord.getLastName(), "Ovo bi trebao");
        assertEquals(onlyRecord.getFirstName(), "biti valjan upis ja mislim");
    }
    
}
