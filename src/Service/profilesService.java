package Service;

import common.Graphs.DirectedGraph;
import entity.Profiles;

import javax.naming.Name;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface profilesService {

    /**
     * Author：Jiahao Huang
     * Sign up
     * @return
     * @throws IOException
     */
    public Profiles reg() throws IOException;


    /**
     * Author：Jiahao Huang
     * //login
     * @return
     * @throws IOException
     */
    public Profiles login() throws IOException;


    /**
     * Author：Jiahao Huang
     * create the imformation
     * @param profiles
     * @throws IOException
     */
    public void create(Profiles profiles) throws IOException;


    /**
     * Author：Jiahao Huang
     * modify the personal information
     * @param profiles
     * @throws IOException
     */
    public void modify(Profiles profiles) throws IOException;



    /**
     * Author：Peter Liu
     * dispalcement all information
     * dispalcement all information
     * @param profiles
     * @return
     */
    public String displace(Profiles profiles);


    /**
     * Author：Jiahao Huang
     */
    public void findFriends();


    /**
     * Author：Peter Liu
     * add fridends
     * @param profiles
     * @throws IOException
     */
    public void addFridends(Profiles profiles) throws IOException;


    /**
     * Author：Peter Liu
     * Delete Friends
     * @param profiles
     * @throws IOException
     */
    public void modifyFridends(Profiles profiles) throws IOException;


    /**
     * Author：jiahao Huang
     * Log out and change the status to offline
     * @param profiles
     */
    public void signout (Profiles profiles) throws IOException;






    /**
     * Author：Peter Liu
     *  View friends' names
     * @param directedGraph
     */
    public void seeFriendslist(DirectedGraph directedGraph);

}
