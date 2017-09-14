package huffman_encoding;//This line can be taken out if errors appear

/* This was written by Ayesha Ahmed (A20344142)
 * for CS430 extra credit
 * This program will enumerate all characters.
 * This program was used to test the following sentence:
Only the fool would take trouble to verify that his sentence was composed of ten a's, three b's, four c's, four d's, forty-six e's, sixteen f's, four g's, thirteen h's, fifteen i's, two k's, nine l's, four m's, twenty-five n's, twenty-four o's, five p's, sixteen r's, forty-one s's, thirty-seven t's, ten u's, eight v's, eight w's, four x's, eleven y's, twenty-seven commas, twenty-three apostrophes, seven hyphens and, last but not least, a single!
*/

import java.util.*;

abstract class HuffmanTree implements Comparable<HuffmanTree> {
    public final int frequency; // the frequency of this tree
    public HuffmanTree(int freq) { frequency = freq; }
 
    // compares on the frequency
    public int compareTo(HuffmanTree tree) {
        return frequency - tree.frequency;
    }
}
 
class HuffmanLeaf extends HuffmanTree {
    public final char value; // the character this leaf represents
 
    public HuffmanLeaf(int freq, char val) {
        super(freq);
        value = val;
    }
}
 
class HuffmanNode extends HuffmanTree {
    public final HuffmanTree left, right; // subtrees
 
    public HuffmanNode(HuffmanTree l, HuffmanTree r) {
        super(l.frequency + r.frequency);
        left = l;
        right = r;
    }
}
 
public class HuffmanCode {
	static int chl = 0;//keeps track of child level
	static int retp = 0;//keeps track of return parent level
	static int rl = 0;//keeps track of right leaves
    // input is an array of frequencies, indexed by character code
    public static HuffmanTree buildTree(int[] charFreqs) {// This builds the tree
        //Follow pseudo-code in Introduction to Algorithms CLRS 3E page 143
        PriorityQueue<HuffmanTree> trees = new PriorityQueue<HuffmanTree>();
        // initially, we have a forest of leaves
        // one for each non-empty character
        int j=0;
        for (int i = 0; i < charFreqs.length; i++){
            if (charFreqs[i] > 0){
                trees.offer(new HuffmanLeaf(charFreqs[i], (char)i));
                j++;
            }
        }
        System.out.println("Unique Characters: "+j);
        //Now we Unionize all the leaves/nodes
        assert trees.size() > 0;
        // loop until there is only one tree left
        while (trees.size() > 1) {//run until n-1
            // two trees with least frequency
            HuffmanTree a = trees.poll();
            HuffmanTree b = trees.poll();
 
            // put into new node and re-insert into queue
            trees.offer(new HuffmanNode(a, b));
        }
        return trees.poll();
    }
 
    public static void printCodes(HuffmanTree tree, StringBuffer prefix) {//This prints the Huffman code and info
        assert tree != null;
        if (tree instanceof HuffmanLeaf) {//reached the bottom leaf
            HuffmanLeaf leaf = (HuffmanLeaf)tree;
            int depth = prefix.length();
            int total = leaf.frequency*depth;
            // print out character, frequency, depth, total, and code for this leaf (which is just the prefix)
            System.out.println("'" + leaf.value+ "'" + "\t" + leaf.frequency + "\t" + depth + "\t" + total + "\t" + prefix);
 
        } else if (tree instanceof HuffmanNode) {//reached a parent
            HuffmanNode node = (HuffmanNode)tree;
 
            // traverse left
            prefix.append('0');
            printCodes(node.left, prefix);
            prefix.deleteCharAt(prefix.length()-1);
 
            // traverse right
            prefix.append('1');
            printCodes(node.right, prefix);
            prefix.deleteCharAt(prefix.length()-1);
        }
    }
 
    public static void printTree(HuffmanTree tree, int level) {//This prints out a tree diagram horizontally in a depth first order
        assert tree != null;
        if (tree instanceof HuffmanLeaf) {//reached the bottom leaf
            HuffmanLeaf leaf = (HuffmanLeaf)tree;
            if(level!=0){
                for(int i=0;i<level-1;i++)
                    System.out.print("|\t");
                    System.out.println("|-------'"+leaf.value+"':"+leaf.frequency);
            }
        } else if (tree instanceof HuffmanNode) {//reached a parent
            HuffmanNode node = (HuffmanNode)tree;
            
            printTree(node.right, level+1);//go right until the bottom
            if(level!=0){
                for(int i=0;i<level-1;i++)
                    System.out.print("|\t");
                    System.out.println("|-------"+node.frequency);
            }
            else
                System.out.println(node.frequency);
            printTree(node.left, level+1);//go left until the bottom
        }
    }
    
    public static void printStruct(HuffmanTree tree, int level, int right) {//This prints out the data structure of the tree
    	assert tree != null;
        if (tree instanceof HuffmanLeaf) {//reached the bottom leaf
        	if(right==1 && rl>1){//print parentheses when right leaf is followed by right leaf
            	for(int i=0;i<chl-retp;i++)
            		System.out.print(")");
            	retp--;
            }
        	HuffmanLeaf leaf = (HuffmanLeaf)tree;
            System.out.print(",leaf('" + leaf.value+ "':" + leaf.frequency + ")");
            chl=level;
        } else if (tree instanceof HuffmanNode) {//reached a parent
        	rl=0;//next node is not leaf set back to zero
        	if(chl>level){//print parentheses when right child leaf is reached and level goes back to ancestor parent
            	for(int i=0;i<chl-level;i++)
            		System.out.print(")");
            	chl=0;
            }
        	if(level!=0){//if not the first root then add comma
        		System.out.print(",");
        	}       	
            HuffmanNode node = (HuffmanNode)tree;
            System.out.print("tree(root("+node.frequency+")");
            // traverse left
            if(node.left instanceof HuffmanNode){
            	retp=level;
            }
            printStruct(node.left, level+1, 0);
 
            // traverse right
            if(node.right instanceof HuffmanNode){
            	retp=level;
            }else rl++;//next node is right leaf
            printStruct(node.right, level+1, 1);
        }
    }
    
    public static void main(String[] args) {
        //custom input
        Scanner console = new Scanner(System.in);
        System.out.println("***Huffman Encoding Program***\n"
        		+ "Please enter a sentence to encode and press enter:");
        String input = console.nextLine();
        String sentence = input.toLowerCase();
        //####### Change[9/1/16]: added code to take out spaces #######
        String noSpace = "";//takes out space characters
        char[] chars = sentence.toCharArray();
        for(char c : chars){
        	if(c != ' '){
        		noSpace += c;
        	}
        }
        //System.out.println(noSpace);//If you want to see what is actually passed in
        System.out.println("Total Characters: "+sentence.length());
        System.out.println("Total Evaluated: "+noSpace.length());
        //####### End Change[9/1/16]. ####### 
        // we will assume that all our characters will have
        // code less than 512, for simplicity 2^9
        int[] charFreqs = new int[512];//I would rather like 256=2^8
        // read each character and record the frequencies
        for (char c : noSpace.toCharArray())//put sentence. to evaluate entire entry
            charFreqs[c]++;
        
        // build tree
        HuffmanTree tree = buildTree(charFreqs);
 
        // print out results
        System.out.println("\nSYMBOL\tFREQ.\tDEPTH\tTOTAL\tHUFFMAN CODE");
        printCodes(tree, new StringBuffer());
        System.out.println("\n***** TREE *****\n");
        printTree(tree, 0);
        System.out.println("\n***** TREE STRUCT *****\n");
        printStruct(tree, 0, 0);
        for(int i=0; i<chl; i++)//This puts in the final parentheses 
        	System.out.print(")");
    }
}
