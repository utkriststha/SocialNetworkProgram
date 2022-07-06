package com.company;

import java.io.File;
import java.net.URISyntaxException;
import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * This program operates a data structure for a social network program in which two file are assigned.
 * One being the member list with index number and the second being the friend list linking the members.
 *
 * @author utkrist.shrestha12@gmail.com
 * @version 1.0.0
 * @since 20 May 2022
 *
 */

public class Main {
    /**
     * Stores object for graph class
     */
    public static Graph g;

    /**
     * Main method
     * @param args main method args
     */
    public static void main(String[] args) {
        System.out.println("Social Network Program");
        setUpGraph("index.txt", "friend.txt");
        startProgram();
    }

    /**
     * Module to start the program recursively.
     * Assigned case according to the menu option.
     */
    public static void startProgram() {
        int menuInput = getMenuOption();
        switch (menuInput) {
            case 1:
                System.out.println("\n**Update friends files**\n");
                Scanner sc = new Scanner(System.in);
                System.out.println("\nEnter the file name (With Out .txt)");
                System.out.print("Enter the friend file name: ");
                String friendsFile = sc.nextLine() + ".txt";
                System.out.print("Enter the index file name: ");
                String indexFile = sc.nextLine() + ".txt";

                setUpGraph(indexFile, friendsFile);
                startProgram();
                break;
            case 2:
                System.out.println("\n**Display friends**\n");
                displayFriends(g);
                startProgram();
                break;
            case 3:
                System.out.println("\n**Display friends and friends of the friends**\n");
                displayMutualFriends(g);
                startProgram();
                break;
            case 4:
                System.out.println("\n**Display common friends**\n[Two User Input]\n");
                displayCommonFriends(g);
                startProgram();
                break;
            case 5:
                System.out.println("\n**Remove member**\n");
                removeFriend(g);
                startProgram();
                break;
            case 6:
                System.out.println("\n**Display popular friends**\n");
                displayPopularFriends(g);
                startProgram();
                break;
            case 7:
                System.out.println("\n**Program Exit**\nGoodBye!!");
                System.exit(0);
        }
    }

    /**
     * Displays the menu option and get the user input
     * @return User input chosen for option selection
     */
    public static int getMenuOption() {
        System.out.println("[Please Select One]");
        System.out.println("1. Update People and Friend list\n" +
                "2. Display all the friends of a person\n" +
                "3. Display all the friends and friends of the friends of a person\n" +
                "4. Display all the common friends of two people\n" +
                "5. Delete a person from the social network\n" +
                "6. Display List of all members sorted by popularity, then by names\n" +
                "7. Exit Program");
        System.out.print("Enter a number: ");
        try {
            Scanner sc = new Scanner(System.in);
            int menuInput = sc.nextInt();
            if (!(menuInput >= 1 && menuInput <= 7)) {
                System.out.println("\nInvalid Input!!\nPlease try again.");
                startProgram();
            }
            return menuInput;
        } catch (InputMismatchException e) {
            System.out.println("\nInput must be integer!!!\nPlease try again. ");
            startProgram();
        }
        return 7;
    }

    /**
     * Method to setup graph data structure using adjacency matrix by reading .txt files.
     * @param indexFile file that contains members list (Label and Vertex)
     * @param friendsFile file that contains friend list (Edges)
     */
    public static void setUpGraph(String indexFile, String friendsFile) {
        Main myApp = new Main();

        try {
            Scanner peopleReader = new Scanner(myApp.getFile(indexFile));
            Scanner friendReader = new Scanner(myApp.getFile(friendsFile));

            int personCount = parseInt(peopleReader.next());
            int friendCount = parseInt(friendReader.next());
            int personCheck = 0;
            int friendCheck = 0;

            g = new Graph(personCount);

            try {
                //Setting up vertex and label from index file
                while (peopleReader.hasNext()) {
                    personCheck++;
                    g.setLabel(parseInt(peopleReader.next()), peopleReader.next());
                }
                //Setting up edged of friend network
                while (friendReader.hasNext()) {
                    friendCheck++;
                    int source = parseInt(friendReader.next());
                    int target = parseInt(friendReader.next());
                    g.addEdge(source, target);
                    g.addEdge(target, source);
                }

                //Checking counts
                if (friendCheck != friendCount || personCheck != personCount) {
                    throw new ArrayIndexOutOfBoundsException("Count error");
                }

            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Specified count does not match the count of data in the file");
                e.printStackTrace();
            }
            peopleReader.close();
            friendReader.close();

        } catch (Exception e) {
            System.out.println("File Not Found!!\nPlease try again.");
            startProgram();
        }

    }

    /**
     * Gets file from the given file location
     * @param filename file location
     * @return file content
     * @throws URISyntaxException throws error when the file has no correct format
     */
    public File getFile(String filename) throws URISyntaxException {
        return new File(this.getClass().getResource(filename).toURI());
    }

    /**
     * Asks user for name input checks and correct input errors
     * @return returns input name is correct format
     */
    public static String getNameInput() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the name: ");
        String inputName = sc.nextLine();
        if (inputName.isEmpty()) {
            System.out.println("No Input Found! Please enter the name to display friends: ");
            getNameInput();
        }
        return reformatString(inputName);
    }

    /**
     * Reformat the entered string
     * @param string unformatted string
     * @return returns correct format string
     */
    public static String reformatString(String string) {
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i]=='-') {
                found = false;
            }
        }
        return String.valueOf(chars);
    }

    /**
     * Displays all friends of input member name
     * @param g Graph object
     */
    public static void displayFriends(Graph g) {
        String user = getNameInput();
        try {
            int[] friends = g.neighbors(g.getVertexIndex(user));
            if (friends.length == 0) {
                System.out.println("No Friends Found!");
            }
            for (int friend : friends) System.out.println(g.getLabel(friend) + " ");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Given name does not exist");
        }
    }

    /**
     * Display all the friends and friends of the friends of a person
     * @param g Graph object
     */
    public static void displayMutualFriends(Graph g) {
        String user = getNameInput();
        try {
            int[] friends = g.neighbors(g.getVertexIndex(user));
            Set<Integer> mutual = new TreeSet<Integer>();
            for (int friend : friends) {
                mutual.add(friend);
                int[] friendOfFriend = g.neighbors(friend);
                for (int j : friendOfFriend) {
                    if (j != g.getVertexIndex(user)) {
                        mutual.add(j);
                    }
                }
            }
            for (int i : mutual) {
                System.out.println(g.getLabel(i));
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Given name does not exist");
        }
    }

    /**
     * Display all the common friends of two people
     * @param g Graph object
     */
    public static void displayCommonFriends(Graph g) {
        try {
            String user = getNameInput();
            String userTwo = getNameInput();
            int[] friends = g.neighbors(g.getVertexIndex(user));
            int[] friendsTwo = g.neighbors(g.getVertexIndex(userTwo));

            //Checking and adding all the common friends
            Set<String> commonFriend = new HashSet<>();
            for (int friend : friends) {
                for (int i : friendsTwo) {
                    if (friend == i) {
                        commonFriend.add(g.getLabel(friend) + " ");
                        break;
                    }
                }
            }

            //Displaying common friends if they have
            if (commonFriend.isEmpty()) {
                System.out.println("There is no common friend");
            } else {
                System.out.println("Common friends are: ");
                for (String i : commonFriend) {
                    System.out.println(i);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Given name does not exist");
        }

    }

    /**
     * Removes member form the social network
     * @param g Graph object
     */
    public static void removeFriend(Graph g) {
        try {
            String user = getNameInput();
            System.out.println("Are you sure you want to remove " + user + " ?  [y/n][yes/no]");
            Scanner sc = new Scanner(System.in);
            String confirm = sc.nextLine();
            reformatString(confirm);
            //Conforming deletion and deleting the member
            if(reformatString(confirm).equals("Yes")  || reformatString(confirm).equals("Y") ){
                int[] userFriend = g.neighbors(g.getVertexIndex(user));
                for (int i = 0; i < userFriend.length; i++) {
                    g.removeEdge(userFriend[i], g.getVertexIndex(user));
                    g.removeEdge(g.getVertexIndex(user), userFriend[i]);
                    
                }
            }else if(reformatString(confirm).equals("No")|| reformatString(confirm).equals("N") ){
                System.out.println("No Worries!!");
            } else{
                System.out.println("Invalid input!! Try again");
                removeFriend(g);
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Given name does not exist");
        }
    }

    /**
     * Display List of all members sorted by popularity, then by names
     * @param g Graph Object
     */
    public static void displayPopularFriends(Graph g) {

        //Adding people object to an array list
        List<People> peopleList = new ArrayList<People>();
        for (int i = 0; i < g.size(); i++) {
            int count = 0;
            for (int j = 0; j < g.neighbors(i).length; j++) {
                count++;
            }
            String name = g.getLabel(i).toString();
            peopleList.add(new People(i, name, count));

        }

        //Sorting array list according to the number of friends
        peopleList.sort(new Comparator<People>() {
            @Override
            public int compare(People o1, People o2) {
                return o2.getNumOfFriends() - o1.getNumOfFriends();
            }
        });

        //Selecting range members with same friends count and sorting according to name
        int cursorIndex = 0;
        int endIndex = 0;
        int startIndex = 0;
        while (cursorIndex < (peopleList.size() - 1)) {
            if (peopleList.get(cursorIndex).getNumOfFriends() == peopleList.get(cursorIndex + 1).getNumOfFriends()) {
                endIndex = cursorIndex + 1;
            } else {
                sortListWithInRange(peopleList, startIndex, endIndex);
                startIndex = endIndex + 1;
            }
            cursorIndex++;
            if (cursorIndex == (peopleList.size() - 1)) {
                sortListWithInRange(peopleList, startIndex, endIndex);
            }
        }

        //Output in table format
        String leftAlignFormat = "| %-7d | %-23s | %-14d |%n";
        System.out.format(" Popularity Ranking%n");
        System.out.format("+-----------------------------------+----------------+%n");
        System.out.format("| Rank    | People                  | No. of Friends |%n");
        System.out.format("+-----------------------------------+-----------------%n");
        for (int i = 0; i < peopleList.size(); i++) {
            System.out.format(leftAlignFormat, i + 1, peopleList.get(i).name, peopleList.get(i).getNumOfFriends());
        }
        System.out.format("+-----------------------------------+-----------------%n");
    }

    /**
     * Method to sort according to name in an arraylist within a range.
     * @param peopleList Arraylist of people
     * @param startIndex start index from where the sorting need to start
     * @param endIndex end index in which the sorting need to end.
     */
    public static void sortListWithInRange(List<People> peopleList, int startIndex, int endIndex) {
        peopleList.subList(startIndex, endIndex + 1).sort(new Comparator<People>() {
            @Override
            public int compare(People o1, People o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }
}
