package JUnitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import bell.spellingsuggestion.main.TrieNodeImpl;

public class TestTrieNodes {

	@Test
	public void test() {
		
		System.out.println("starting test");
		ArrayList<String> strings = new ArrayList<String>();
		strings.add("first thought");
		strings.add("second");
		strings.add("third");
		
		TrieNodeImpl trieList = null;
		
		trieList = new TrieNodeImpl(strings);
		
		trieList.printTree();
		
		
		fail("Not yet implemented");
	}

}
