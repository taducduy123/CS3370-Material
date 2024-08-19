import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

public class DemoPanel extends JPanel{
    
    // SCREEN SETTING
    final int maxCol = 15;
    final int maxRow = 10;
    final int nodeSize = 70;
    final int screenWidth = nodeSize * maxCol;
    final int screenHeight = nodeSize * maxRow;


    //NODE
    Node [][] node = new Node[maxRow][maxCol];
    Node startNode, goalNode, currentNode;
    ArrayList<Node> openList = new ArrayList<>();
    ArrayList<Node> checkedList = new ArrayList<>();


    //OTHERS
    boolean goalReached = false;

//----------------------------------------------------------------------------------
    public DemoPanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setLayout(new GridLayout(maxRow, maxCol));
        this.addKeyListener(new keyHandler(this));
        this.setFocusable(true);

        //PLACE NODES
        for(int i = 0; i < this.maxRow; i++){
            for(int j = 0; j < this.maxCol; j++){
                this.node[i][j] = new Node(i,j);
                this.add(node[i][j]);
            }
        }


        //SET START AND GOAL NODE
        setStartNode(6, 3);
        setGoalNode(3, 11);

        


        //PLACE SOLID NODES
        setSolidNode(2, 10);
        setSolidNode(3, 10);
        setSolidNode(4, 10);
        setSolidNode(5, 10);
        setSolidNode(6, 10);
        setSolidNode(7, 10);
        setSolidNode(1, 6);
        setSolidNode(2, 6);
        setSolidNode(2, 7);
        setSolidNode(2, 8);
        setSolidNode(2, 9);
        setSolidNode(7, 11);
        setSolidNode(7, 12);


        // //Test case for path not found
        // setSolidNode(3, 0);
        // setSolidNode(3, 1);
        // setSolidNode(3, 2);
        // setSolidNode(3, 3);
        // setSolidNode(3, 4);
        // setSolidNode(3, 5);
        // setSolidNode(3, 6);

        // setSolidNode(4, 0);
        // setSolidNode(5, 0);
        // setSolidNode(5, 0);
        // setSolidNode(6, 0);
        // setSolidNode(7, 0);
        // setSolidNode(8, 0);
        // setSolidNode(9, 0);
        
        // setSolidNode(9, 1);
        // setSolidNode(9, 2);
        // setSolidNode(9, 3);
        // setSolidNode(9, 4);
        // setSolidNode(9, 5);
        // setSolidNode(9, 6);

        // setSolidNode(8, 6);
        // setSolidNode(7, 6);
        // setSolidNode(6, 6);
        // setSolidNode(5, 6);
        // setSolidNode(4, 6);
        //SET COST ON NODES
        setCostOnNode();
    }



    private void setStartNode(int row, int col){
        node[row][col].setAsStart();
        startNode = node[row][col];
        currentNode = startNode;
        //Add start node to openList
        openList.add(startNode);
    }


    private void setGoalNode(int row, int col){
        node[row][col].setAsGoal();
        goalNode = node[row][col];
    }


    private void setSolidNode(int row, int col){
        node[row][col].setAsSolid();
    }


    private void setCostOnNode(){
        for(int i = 0; i < maxRow; i++){
            for(int j = 0; j < maxCol; j++){
                getCost(node[i][j]);
            }
        }
    }


    private void getCost(Node node){
        //Calculate G-cost (The distance from the start node to the current node)
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        //Calculate H-cost (The distance from the current node to the goal node)
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;
        
        //Calculate F-cost (The total cost)
        node.fCost = node.gCost + node.hCost;

        //Display the COST on Node
        if(node != startNode && node != goalNode){
            node.setText("<html>F:" + node.fCost + "<br>G:" + node.gCost + "</html>");
        }
    }


    public boolean search(){

        if(goalReached == false && openList.size() > 0){
            int row = currentNode.row;
            int col = currentNode.col;

            currentNode.setAsChecked();
            checkedList.add(currentNode);
            openList.remove(currentNode);

            //OPEN THE UP NODE
            if(row - 1 >= 0){
                openNode(node[row-1][col]);
            }
            //OPEN THE DOWN NODE
            if(row + 1 < maxRow){
                openNode(node[row+1][col]);
            }
            //OPEN THE LEFT NODE
            if(col - 1 >= 0){
                openNode(node[row][col-1]);
            }
            //OPEN THE RIGHT NODE
            if(col + 1 < maxCol){
                openNode(node[row][col+1]);
            }

            //FIND THE BEST NODE
            int bestNodeIndex = -1;
            int bestNodefCost = 999999;

            for(int i = 0; i < openList.size(); i++){

                //Check if this node's F cost is better
                if(openList.get(i).fCost < bestNodefCost){
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                }
                //If F cost is equal, check the G cost
                else if(openList.get(i).fCost == bestNodefCost){
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;

                    }
                }
            }

            //After the for loop, we get the best node which is out next step
            if(bestNodeIndex != - 1){
                System.out.println("Size of OpenList: " + openList.size());
                currentNode = openList.get(bestNodeIndex);

                if(currentNode == goalNode){
                    goalReached = true;
                    trackThePath();
                    return goalReached;
                }
            }
            else{
                System.out.println("Size of OpenList: " + openList.size());
            }  
        }
        return goalReached;
    }


    public boolean AutoSearch(){

        boolean found = false;
        while(found == false && openList.size() > 0){
            int row = currentNode.row;
            int col = currentNode.col;

            currentNode.setAsChecked();
            checkedList.add(currentNode);
            openList.remove(currentNode);

            //OPEN THE UP NODE
            if(row - 1 >= 0){
                openNode(node[row-1][col]);
            }
            //OPEN THE DOWN NODE
            if(row + 1 < maxRow){
                openNode(node[row+1][col]);
            }
            //OPEN THE LEFT NODE
            if(col - 1 >= 0){
                openNode(node[row][col-1]);
            }
            //OPEN THE RIGHT NODE
            if(col + 1 < maxCol){
                openNode(node[row][col+1]);
            }

            //FIND THE BEST NODE
            int bestNodeIndex = -1;
            int bestNodefCost = 999999;

            for(int i = 0; i < openList.size(); i++){

                //Check if this node's F cost is better
                if(openList.get(i).fCost < bestNodefCost){
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                }
                //If F cost is equal, check the G cost
                else if(openList.get(i).fCost == bestNodefCost){
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;

                    }
                }
            }

            //After the for loop, we get the best node which is out next step
            if(bestNodeIndex != - 1){
                System.out.println("Size of OpenList: " + openList.size());
                currentNode = openList.get(bestNodeIndex);

                if(currentNode == goalNode){
                    found = true;
                    trackThePath();
                    return found;
                }
            }
        }
        return found;
    }


    private void openNode(Node node){
        if(node.open == false && node.checked == false && node.solid == false){

            //If the node is not opened yet, add it to the open list
            node.setAsOpen();
            node.parent = currentNode;
            openList.add(node);
        }
    }

    
    private void trackThePath(){
        //Back track and Draw the best path
        Node current = goalNode;

        while(current != startNode){

            current = current.parent;

            if(current != startNode){
                current.setAsPath();
            }
        }
    }

}
