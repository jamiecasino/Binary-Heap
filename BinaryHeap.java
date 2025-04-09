/***************************************************************************
 * 
 *  YOUR NAME: Jamie Casino
 *  YOUR EMAIL: jamiec9981@gmail.com
 *  HACKERRANK USERNAME: jamiec9981
 * 
 ***************************************************************************/
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import static java.util.stream.Collectors.averagingDouble;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

interface IBinaryHeap {
    public void add(int item);

    public int remove();

    public int size();

    public int peek();

}

class BinaryHeap implements IBinaryHeap {
    // For help with [ArrayList], please refer to:
    // https://www.w3schools.com/java/java_arraylist.asp
    private final int INITIAL_SIZE = 10;
    private List<Integer> arrList;
    private int size;

    public BinaryHeap() {
        this.size = 0;
        this.arrList = new ArrayList<Integer>(INITIAL_SIZE);
        this.arrList.add(Integer.MAX_VALUE);
    }

    public void print() {
        String elements = "[";
        // Remember, we're not using the first element of this array list.
        for (int i = 0; i < arrList.size(); i++) {
            if (i == 0) {
                elements += "X";
            } else {
                elements += arrList.get(i);
            }
            if (i < arrList.size() - 1) {
                elements += ", ";
            }
        }
        elements += "]";
        String summary = String.format("{ size: %d, elements: %s }", this.size, elements);
        System.out.println(summary);
    }

    /***************************************************************************
     *
     * Write all your code BELOW here...
     * 
     ***************************************************************************/

    @Override
    public void add(int item) {
        arrList.add(item);
        size++;
        heapifyUp(size);
    }

    @Override
    public int remove() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty");
        }
        
        int rootValue = arrList.get(1);
        arrList.set(1, arrList.get(size));
        arrList.remove(size);
        size--;
        heapifyDown(1);
        return rootValue;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Return the value at the root element
     */
    @Override
    public int peek() {
        if (this.size == 0) {
            return -1;
        }
        return arrList.get(1);
    }


    /**
     * Use the node index to heapify up with it's parent.
     * If the node value is more than the parent value, then heapifyUp.
     */
    private void heapifyUp(int nodeIndex) {
        while (nodeIndex > 1 && arrList.get(nodeIndex) > arrList.get(getParentIndex(nodeIndex))) {
            swap(nodeIndex, getParentIndex(nodeIndex));
            nodeIndex = getParentIndex(nodeIndex);
        }
    }

    /**
     * Make sure to consider when the left/right index is out of bounds.
     * Consider cases when:
     * - Having no children - there's nothing to do.
     * - Having only left child - compare to see if the value of the parent is more
     * than the child. Swap if necessary.
     * - Having left and right child - There are 3 possible cases when heapifying
     * down:
     * - The parent's value is less than both children. Swap with the higher-valued
     * child.
     * - The parent's value is less than one of the children. Swap with that child.
     * - The parent's value is more than both of its children. Nothing to do.
     */
    private void heapifyDown(int nodeIndex) {
        while (haveLeftChild(nodeIndex)) {
            int largerChildIndex = getLeftChildIndex(nodeIndex);
            
            if (haveRightChild(nodeIndex) && arrList.get(getRightChildIndex(nodeIndex)) > arrList.get(largerChildIndex)) {
                largerChildIndex = getRightChildIndex(nodeIndex);
            }

            if (arrList.get(nodeIndex) >= arrList.get(largerChildIndex)) {
                break;
            }

            swap(nodeIndex, largerChildIndex);
            nodeIndex = largerChildIndex;
        }
    }

    private int getLeftChildIndex(int nodeIndex) {
        return 2 * nodeIndex;
    }

    private int getRightChildIndex(int nodeIndex) {
        return 2 * nodeIndex + 1;
    }

    private int getParentIndex(int nodeIndex) {
        return nodeIndex / 2;
    }

    private boolean haveLeftChild(int nodeIndex) {
        return getLeftChildIndex(nodeIndex) <= size;
    }

    private boolean haveRightChild(int nodeIndex) {
        return getRightChildIndex(nodeIndex) <= size;
    }

    private void swap(int index1, int index2) {
        int temp = arrList.get(index1);
        arrList.set(index1, arrList.get(index2));
        arrList.set(index2, temp);
    }


    /***************************************************************************
     *
     * Write all your code ABOVE here...
     * 
     ***************************************************************************/
}

interface IMaxPriorityQueue {
    public void addItem(int item);

    public int removeItem();

    public int peek();

    public int getSize();

    public boolean isEmpty();
}

class MaxPriorityQueue implements IMaxPriorityQueue {
    private BinaryHeap binaryHeap;

    public MaxPriorityQueue() {
        this.binaryHeap = new BinaryHeap();
    }

    public void print() {
        this.binaryHeap.print();
    }

    /***************************************************************************
     *
     * Implement the IMaxPriorityQueue.
     * Call the BinaryHeap operation to return the desired values.
     * 
     * Write all your code BELOW here...
     * 
     ***************************************************************************/

    @Override
    public void addItem(int item) {
        binaryHeap.add(item);
    }

    @Override
    public int removeItem() {
        return binaryHeap.remove();
    }

    @Override
    public int peek() {
        return binaryHeap.peek();
    }



    @Override
    public int getSize() {
        return binaryHeap.size();
    }

    @Override
    public boolean isEmpty() {
        return binaryHeap.size() == 0;
    }


    /***************************************************************************
     * 
     * Write all your code ABOVE here...
     * 
     ***************************************************************************/
}

class Solution {
    public static void main(String[] args) throws IOException {
        // input handling
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int operationCount = Integer.parseInt(bufferedReader.readLine().replaceAll("\\s+$", "").split("=")[1].trim());
        bufferedReader.readLine();

        MaxPriorityQueue queue = new MaxPriorityQueue();
        IntStream.range(0, operationCount).forEach(opCountItr -> {
            try {
                List<String> theInput = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split("\\("))
                        .collect(toList());
                // action
                String action = theInput.get(0);
                // args
                String argsString = theInput.get(1);
                argsString = argsString.substring(0, argsString.length() - 1);
                //
                String arg0 = null;
                if (argsString.length() > 0) {
                    List<String> argsInput = Arrays.asList(argsString.split(","));
                    arg0 = argsInput.size() > 0 ? argsInput.get(0).trim() : null;
                }
                ProcessInputs(queue, action, arg0);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        });
        bufferedReader.close();
    }

    private static void ProcessInputs(MaxPriorityQueue queue, String action, String arg0) {
        int value;
        switch (action) {
            case "addItem":
                value = Integer.parseInt(arg0);
                queue.addItem(value);
                break;

            case "removeItem":
                if(queue.isEmpty()) {
                    System.out.println("QUEUE_IS_EMPTY");
                } else {
                    int result2 = queue.removeItem();
                    System.out.println(result2);
                }
                break;

            case "peek": {
                int result3 = queue.peek();
                System.out.println(result3);
                break;
            }

            case "getSize":
                int result4 = queue.getSize();
                System.out.println(result4);
                break;

            case "isEmpty":
                boolean result5 = queue.isEmpty();
                System.out.println(result5);
                break;

            case "print":
                queue.print();
                break;
        }
    }
}