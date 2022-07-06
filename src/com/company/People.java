package com.company;

/**
 * This is a object for the member of social network
 *
 * @author utkrist.shrestha12@gmail.com
 * @version 1.0.0
 * @since 20 May 2022
 *
 */

public class People{
    /**
     * Index number of the member
     */
    public int index;
    /**
     * Name of the member
     */
    public String name;
    /**
     * Number of friends of the member
     */
    public int numOfFriends;

    /**
     * Constructor method of object people
     * @param index Index number of the member
     * @param name Name of the member
     * @param numOfFriends Number of friends of the member
     */
    public People(int index, String name, int numOfFriends) {
        this.index = index;
        this.name = name;
        this.numOfFriends = numOfFriends;
    }

    /**
     * Method to get the number of friends of the member
     * @return Number of friend
     */
    public int getNumOfFriends() {
        return numOfFriends;
    }

    /**
     * Method to set the number of friends of the member
     * @param numOfFriends Number of friend
     */
    public void setNumOfFriends(int numOfFriends) {
        this.numOfFriends = numOfFriends;
    }

    /**
     * Method to get the index number of the member
     * @return Index number
     */
    public int getIndex() {
        return index;
    }

    /**
     * Method to set the index number of the member
     * @param id Index number
     */
    public void setIndex(int id) {
        this.index = id;
    }

    /**
     * Method to get the name of the member
     * @return Member's name
     */
    public String getName() {
        return name;
    }

    /**
     * Method to get the name of the member
     * @param name Member's name
     */
    public void setName(String name) {
        this.name = name;
    }

}
