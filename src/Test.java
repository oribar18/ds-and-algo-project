import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;
import static java.lang.System.out;

interface Constants
{
    int SEED = 3659412;
    int TEST_SIZE = 10326;
    int UNIQUE_KEYS_NUM = TEST_SIZE;
    int MAXIMUM_INSERT_NODES = UNIQUE_KEYS_NUM;
    int MAXIMUM_INSERT_EDGES = TEST_SIZE;
    int UNIQUE_KEYS_MAXIMUM = TEST_SIZE * 100;
    int INITIAL_NUMBER_OF_NODES = TEST_SIZE / 10;
    int INITIAL_NUMBER_OF_EDGES = TEST_SIZE / 4;
    enum Operations {INSERT_NODE, DELETE_NODE, INSERT_EDGE, DELETE_EDGE, DEGREE, BFS, SCC}
}

class CurrentGraph
{
    private GraphNode[] nodes;
    private MyEdge[] edges;
    private int currentNodesIndex;
    private int currentEdgesIndex;
    public CurrentGraph()
    {
        this.nodes = new GraphNode[Constants.MAXIMUM_INSERT_NODES];
        this.edges = new MyEdge[Constants.MAXIMUM_INSERT_EDGES];
        this.currentNodesIndex = 0;
        this.currentEdgesIndex = 0;
    }
    public GraphNode getNodeByIndex(int nodeIndex)
    {
        if ((0 <= nodeIndex) && (nodeIndex <= this.currentNodesIndex))
        {
            return this.nodes[nodeIndex];
        }
        return null;
    }
    public MyEdge getEdgeByIndex(int edgeIndex)
    {
        if ((0 <= edgeIndex) && (edgeIndex <= this.currentEdgesIndex))
        {
            return this.edges[edgeIndex];
        }
        return null;
    }
    public void insertNode(GraphNode node)
    {
        this.nodes[this.currentNodesIndex] = node;
        if (this.currentNodesIndex < Constants.MAXIMUM_INSERT_NODES - 1)
        {
            this.currentNodesIndex += 1;
        }
    }
    public void insertEdge(MyEdge edge)
    {
        this.edges[this.currentEdgesIndex] = edge;
        if (this.currentEdgesIndex < Constants.MAXIMUM_INSERT_EDGES - 1)
        {
            this.currentEdgesIndex += 1;
        }
    }
    public void deleteNode(int nodeIndex)
    {
        if (this.canDeleteNode(this.nodes[nodeIndex].getKey()))
        {
            CurrentGraph.shiftArrayLeft(this.nodes, nodeIndex, this.currentNodesIndex);
            if (this.currentNodesIndex > 0)
            {
                this.currentNodesIndex -= 1;
            }
        }
    }
    public void deleteEdge(int edgeIndex)
    {
        CurrentGraph.shiftArrayLeft(this.edges, edgeIndex, this.currentEdgesIndex);
        if (this.currentEdgesIndex > 0)
        {
            this.currentEdgesIndex -= 1;
        }
    }
    public boolean canInsertEdge(int from, int to)
    /*
    traverse all current edges and check if edge from->to exists
     */
    {
        for(int i =0; i < this.currentEdgesIndex; i++ )
        {
            if ((this.edges[i].getFrom()) == from && (this.edges[i].getTo() == to))
            {
                return false;
            }
        }
        return true;
    }
    private boolean canDeleteNode(int key)
    /*
    traverse all current edges and check if a node with identifier key has no out or in edges
     */
    {
        for(int i = 0; i < this.currentEdgesIndex; i++)
        {
            if ((this.edges[i].getFrom() == key) || (this.edges[i].getTo() == key))
            {
                return false;
            }
        }
        return true;
    }
    public int getCurrentNodesIndex()
    {
        return this.currentNodesIndex;
    }
    public int getCurrentEdgesIndex()
    {
        return this.currentEdgesIndex;
    }
    private static void shiftArrayLeft(Object[] array, int startIndex, int endIndex)
    {
        if ((endIndex + 1 == array.length) || (startIndex < 0))
        {
            return;
        }
        for (int i=startIndex; i<endIndex; i++)
        {
            array[i] = array[i+1];
        }
    }
}

class MyEdge
{
    private GraphEdge graphEdge;
    private int from;
    private int to;
    public MyEdge(GraphEdge graphEdge, int from, int to)
    {
        this.graphEdge = graphEdge;
        this.from = from;
        this.to = to;
    }
    public GraphEdge getGraphEdge()
    {
        return graphEdge;
    }
    public int getFrom()
    {
        return from;
    }
    public int getTo()
    {
        return to;
    }
}

public class Test
{
    public static void main(String[] args) throws IOException
    {
        Random random = new Random();
        // fix the seed to reproduce the run
        random.setSeed(Constants.SEED);
        testDynamicGraph(random);
    }
    public static int[] createUniqueKeys(Random random)
    {
        int[] uniqueKeysArray = new int[Constants.UNIQUE_KEYS_NUM];
        int uniqueKeysArrayIndex = 0;

        // create an array to check duplicates
        int[] duplicateKeysArray = new int[Constants.UNIQUE_KEYS_MAXIMUM];
        // initialize the array
        for (int i=0; i<Constants.UNIQUE_KEYS_MAXIMUM; i++)
        {
            duplicateKeysArray[i] = 0;
        }
        // create the unique keys
        while (uniqueKeysArrayIndex < Constants.UNIQUE_KEYS_NUM)
        {
            //returns int in the range [0, Constants.UNIQUE_KEYS_MAXIMUM)
            int key = random.nextInt(Constants.UNIQUE_KEYS_MAXIMUM);
            if (duplicateKeysArray[key] == 0)
            {
                duplicateKeysArray[key] = 1;
                // save the key and make sure it is positive
                uniqueKeysArray[uniqueKeysArrayIndex] = key + 1;
                uniqueKeysArrayIndex += 1;
            }
        }
        return uniqueKeysArray;
    }
    public static void testDynamicGraph(Random random) throws IOException
    {
        DataOutputStream outStream = new DataOutputStream(out);
        int[] uniqueKeysArray = createUniqueKeys(random);
        int uniqueKeysArrayIndex = 0;

        Constants.Operations[] operationsValuesArray = Constants.Operations.values();

        DynamicGraph G = new DynamicGraph();
        CurrentGraph CG = new CurrentGraph();
        GraphNode node;
        GraphNode node1;
        GraphEdge edge;
        MyEdge myEdge;
        int randomIndex;
        int randomIndex1;

        RootedTree T;
        Constants.Operations[] operationsArray = new Constants.Operations[Constants.TEST_SIZE];
        for (int i=0; i<Constants.INITIAL_NUMBER_OF_NODES;i++)
        {
            //set the operation to insert
            operationsArray[i] = Constants.Operations.INSERT_NODE;
        }

        for (int i=Constants.INITIAL_NUMBER_OF_NODES;i<Constants.INITIAL_NUMBER_OF_NODES +
                Constants.INITIAL_NUMBER_OF_EDGES; i++)
        {
            //set the operation to insert edge
            operationsArray[i] = Constants.Operations.INSERT_EDGE;
        }
        for (int i=Constants.INITIAL_NUMBER_OF_NODES + Constants.INITIAL_NUMBER_OF_EDGES;i<Constants.TEST_SIZE;i++)
        {
            // set a random operation
            operationsArray[i] = operationsValuesArray[random.nextInt(operationsValuesArray.length)];
        }

        for (int i=0; i<Constants.TEST_SIZE;i++)
        {
            switch (operationsArray[i])
            {
                case INSERT_NODE:
                {
                    node = G.insertNode(uniqueKeysArray[uniqueKeysArrayIndex]);
                    CG.insertNode(node);
                    uniqueKeysArrayIndex += 1;
                    break;
                }
                case DELETE_NODE:
                {
                    if (CG.getCurrentNodesIndex() > 0)
                    {
                        randomIndex = random.nextInt(CG.getCurrentNodesIndex());
                        node = CG.getNodeByIndex(randomIndex);
                        G.deleteNode(node);
                        CG.deleteNode(randomIndex);
                    }
                    break;
                }
                case INSERT_EDGE:
                {
                    if (CG.getCurrentNodesIndex() > 1)
                    {
                        randomIndex = random.nextInt(CG.getCurrentNodesIndex());
                        randomIndex1 = random.nextInt(CG.getCurrentNodesIndex());
                        if (randomIndex != randomIndex1)
                        {
                            node = CG.getNodeByIndex(randomIndex);
                            node1 = CG.getNodeByIndex(randomIndex1);
                            if (CG.canInsertEdge(node.getKey(), node1.getKey()))
                            {
                                edge = G.insertEdge(node, node1);
                                CG.insertEdge(new MyEdge(edge, node.getKey(), node1.getKey()));
                            }
                        }
                    }
                    break;
                }
                case DELETE_EDGE:
                {
                    if (CG.getCurrentEdgesIndex() > 0)
                    {
                        randomIndex = random.nextInt(CG.getCurrentEdgesIndex());
                        myEdge = CG.getEdgeByIndex(randomIndex);
                        G.deleteEdge(myEdge.getGraphEdge());
                        CG.deleteEdge(randomIndex);
                    }
                    break;
                }
                case BFS:
                {
                    randomIndex = random.nextInt(CG.getCurrentNodesIndex());
                    T = G.bfs(CG.getNodeByIndex(randomIndex));
                    outStream.writeBytes("Print in layers after BFS:" + System.lineSeparator());
                    T.printByLayer(outStream);
                    outStream.writeBytes(System.lineSeparator());
                    outStream.writeBytes("Preorder print after BFS:" + System.lineSeparator());
                    T.preorderPrint(outStream);
                    outStream.writeBytes(System.lineSeparator());
                    break;
                }
                case DEGREE:
                {
                    randomIndex = random.nextInt(CG.getCurrentNodesIndex());
                    node = CG.getNodeByIndex(randomIndex);
                    randomIndex = random.nextInt(2);
                    if (randomIndex == 0)
                    {
                        outStream.writeBytes("The out degree of node " + node.getKey() +
                                " is " + node.getOutDegree() + System.lineSeparator());
                    }
                    else
                    {
                        outStream.writeBytes("The in degree of node " + node.getKey() +
                                " is " + node.getInDegree() + System.lineSeparator());
                    }
                    break;
                }
                case SCC:
                {
                    T = G.scc();
                    outStream.writeBytes("Print in layers after SCC:" + System.lineSeparator());
                    T.printByLayer(outStream);
                    outStream.writeBytes(System.lineSeparator());
                    outStream.writeBytes("Preorder print after SCC:" + System.lineSeparator());
                    T.preorderPrint(outStream);
                    outStream.writeBytes(System.lineSeparator());
                    break;
                }
            }
        }
        outStream.close();
    }
}
