import java.util.*;
import java.util.stream.*;

public class Q1Test {
     public static void main(String[] args) {

          String result, traversal1, traversal2, traversal3;

          traversal1 = "1-2";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "3-2-1";
          traversal3 = "2-3-1";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "3-1-2-5-4";
          traversal2 = "2-1-4-5-3";
          traversal3 = "1-2-3-4-5";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Preorder, Traversal 2 - Postorder, Traversal 3 - Inorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "10-20-30-40-50";
          traversal2 = "20-10-30-40-50";
          traversal3 = "10-50-40-30-20";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();
     } 

     public static class Node{
          private int element;
          private Node left = null;
          private Node right = null;

          public Node(int element){
               this.element = element;
          }

          public Node getLeft(){
               return left;
          }

          public Node getRight(){
               return right;
          }

          public int getElement(){
               return element;
          }
     }

     public static Node buildTree(List<Integer> preorder){
          Node root = null;
          for (int v : preorder) {
               root = insert(root, v);
          }    
          return root;
     }

     public static Node insert(Node root, int element) {
          if (root == null){
               return new Node(element);
          }

          if (element < root.getElement()){
               root.left = insert(root.left, element);
          } else {    
               root.right = insert(root.right, element);
          }
          
          return root;
     
     }

     public static void inorderTraversal(Node node, List<Integer> out) {
          if (node == null){
               return;
          }
          inorderTraversal(node.getLeft(), out);
          out.add(node.getElement());
          inorderTraversal(node.getRight(), out);
     }

     public static void postorderTraversal(Node node, List<Integer> out) {
          if (node == null){
               return;
          }
          postorderTraversal(node.getLeft(), out);
          postorderTraversal(node.getRight(), out);
          out.add(node.getElement());
          
     }

     public static List<Integer> turnIntoList(String traversal){
          List<Integer> travList = Arrays.stream(traversal.split("-"))
                                         .map(Integer::valueOf)
                                         .collect(Collectors.toList());

          return travList;
     }

      // write your codes here
     public static String verify(String traversal1, String traversal2, String traversal3){
          List<List<Integer>> traversalList = new ArrayList<>();
          traversalList.add(turnIntoList(traversal1));
          traversalList.add(turnIntoList(traversal2));
          traversalList.add(turnIntoList(traversal3));

          String[] names = {"Traversal 1", "Traversal 2", "Traversal 3"};
          int[][] possibilities = {{0,1,2},{0,2,1},{1,0,2},{1,2,0},{2,0,1},{2,1,0}}; 
          // all possible permutations of which traversals are which
          // index 0 = pre, index 1 = inorder, index 2 = post

          for(int[] poss : possibilities){
               // use preorder to construct the BST
               List<Integer> preorder = traversalList.get(poss[0]);
               Node root = buildTree(preorder);

               List<Integer> inorder = new ArrayList<>();
               List<Integer> postorder = new ArrayList<>();
               
               inorderTraversal(root, inorder);
               postorderTraversal(root, postorder);

               if (inorder.equals(traversalList.get(poss[1])) && postorder.equals(traversalList.get(poss[2]))) {
                    String[] traversalType = new String[3];
                    traversalType[poss[0]] = "Preorder";
                    traversalType[poss[1]] = "Inorder";
                    traversalType[poss[2]] = "Postorder";
                    return names[0] + " - " + traversalType[0] + ", " + names[1] + " - " + traversalType[1] + ", "+ names[2] + " - " + traversalType[2];
               }
          }
          return "Invalid traversals";


     }


}