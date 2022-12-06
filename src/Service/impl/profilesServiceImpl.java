package Service.impl;

import Service.profilesService;
import common.*;
import common.Graphs.DirectedGraph;
import entity.Profiles;
import sun.reflect.misc.FieldUtil;


import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class profilesServiceImpl implements profilesService {


    /**
     * Author：Jiahao Huang
     * Registratie
     * @return
     * @throws IOException
     */
    @Override
    public Profiles reg() throws IOException {
        Profiles profilesFinal = new Profiles();
        Scanner scanner = new Scanner(System.in);
        System.out.println(("Enter username"));
        String name = scanner.nextLine();

        System.out.println(("Enter password"));
        String passWord = scanner.nextLine();

        String path = name + ".txt";
        File file = new File(path);


        if (file.exists()) {
            System.out.println(("This account already exists, please enter your password directly to log in"));
            return null;
        } else {
            HashedDictionary<Object, Object> objectObjectHashedDictionary = new HashedDictionary<>(1);
            objectObjectHashedDictionary.add("status", "Online");
            AList<String> stringAList = new AList<>(10);
            Profiles profiles = new Profiles(name, passWord, objectObjectHashedDictionary, stringAList);
            System.out.println("Because you are a new account, we recommend that you first register your personal information simply by " +
                    "\n"+"clicking 1 so that others can see your information");

            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write(name + "\n");

            fileWriter.write(passWord + "\n");
            fileWriter.write(profiles.getStatus("status") + "\n");

            // Close the stream
            fileWriter.close();
            profilesFinal = profiles;

        }
        return profilesFinal;
    }

    /**
     * Author：Jiahao Huang
     * Login
     * @return
     * @throws IOException
     */
    @Override
    public Profiles login() throws IOException {
        Profiles profilesFinal = new Profiles();
        Scanner scanner = new Scanner(System.in);
        System.out.println(("Enter username"));
        String name = scanner.nextLine();

        System.out.println(("Enter password"));
        String passWord = scanner.nextLine();

        String path = name + ".txt";
        File file = new File(path);

        if (!file.exists() == true) {
            System.out.println(("Account does not exist, please register first"));
            return null;
        }


        // Get the account number and password to start the authentication
        FileReader fileReader = new FileReader(file);
        BufferedReader buffer = new BufferedReader(fileReader);
        String ioName = buffer.readLine();
        String ioPassword = buffer.readLine();
        String ioStates = buffer.readLine();
        String ioAge = buffer.readLine();
        String ioDate = buffer.readLine();
        String ioGender = buffer.readLine();
        String str = null;
        AList<String> stringAList = new AList<>();
        while (true) {
            str = buffer.readLine();
            if(str!=null)
                stringAList.add(str);
            else
                break;
        }
        buffer.close();


        // return the data encapsulated as an object
        if (name.equals(ioName) && passWord.equals(ioPassword))
        {

            HashedDictionary<Object, Object> objectObjectHashedDictionary = new HashedDictionary<>(1);
            objectObjectHashedDictionary.add("status", "online");


            Profiles profiles = new Profiles(ioName, ioPassword, objectObjectHashedDictionary, stringAList);
            profiles.setAge(Integer.parseInt(ioAge));
            profiles.setGender(ioGender);
            profiles.setDate(ioDate);

            profilesFinal = profiles;
            System.out.println(("Login successful"));

            profilesFinal.getAge();
            return profilesFinal;
        } else {
            System.out.println(("Account password error please try again"));
            return null;
        }
    }



    /**
     * Author：Jiahao Huang
     * create a profile
     * @param profiles
     */
    public void create(Profiles profiles) throws IOException {

        //Second login cannot be used to create personal information
        if (profiles.getAge() != 0
                && !profiles.getDate().equals(null)
                && !profiles.getGender().equals(null)) {
            System.out.println("You have already created a personal information, if you want to modify it, please press 2");
            return;
        }

        try {
            // First login
            if (profiles.getAge() == 0
                    && profiles.getDate().equals(null)
                    && profiles.getGender().equals(null)) {
            }
        } catch (NullPointerException e) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your age ");
            int i = scanner.nextInt();

            System.out.print("Enter your birthday ");
            scanner.nextLine();
            String date= scanner.nextLine();

            System.out.print("Enter your gender ");
            String gender = scanner.nextLine();

            profiles.setAge(i);
            profiles.setDate(date);
            profiles.setGender(gender);

            String path = profiles.getName() + ".txt";
            File file = new File(path);

            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write(profiles.getAge() + "\n");
            fileWriter.write(profiles.getDate() + "\n");
            fileWriter.write(profiles.getGender() + "\n");

            fileWriter.close();
        }


    }



    /**
     * Author：Peter Liu
     * modify
     * @param profiles
     */
    @Override
    public void modify(Profiles profiles) throws IOException {

        // No registration of personal information directly pressed to modify the error
        try {
            if (profiles.getAge() == 0 && profiles.getDate().equals(null) && profiles.getGender().equals(null)) {
            }
        } catch (NullPointerException e) {
            System.out.println("You have not yet created a personal information, if you want to create please press 1");
            return;
        }


        //修改
        if (profiles.getAge() != 0 && !profiles.getDate().equals(null) && !profiles.getGender().equals(null)) {



            System.out.print("1：Change your age ");
            System.out.print("2：Change your birthday ");
            System.out.print("3：Change your gender ");
            System.out.print("4：Modify your status ");
            Scanner sc = new Scanner(System.in);
            int number = sc.nextInt();
            FileUtil fileUtil = new FileUtil();

            String path = profiles.getName() + ".txt";

            switch (number){
                case 1:
                    System.out.println("Enter the age you want to change");
                    int newAge = sc.nextInt();
                    String oldAge = String.valueOf(profiles.getAge());

                    fileUtil.modifyFile(path,oldAge, String.valueOf(newAge));
                    profiles.setAge(newAge);

                    return;
                case 2:
                    System.out.println("Enter the birthday you want to change");
                    Scanner scanner = new Scanner(System.in);
                    String newDate = scanner.nextLine();
                    String oldDate = String.valueOf(profiles.getDate());

                    fileUtil.modifyFile(path,oldDate,newDate);
                    profiles.setDate(newDate);
                    return;
                case 3:
                    System.out.println("Enter the gender you want to change");
                    Scanner scanner1 = new Scanner(System.in);

                    String oldGender = String.valueOf(profiles.getDate());
                    String newGender = scanner1.nextLine();

                    fileUtil.modifyFile(path,oldGender,newGender);
                    profiles.setGender(newGender);
                    return;

                case 4:
                    //Online, offline, busy,...
                    System.out.println("1：Change the status to BUSY  2: Change the status to Online");
                    //Get the old data
                    Object status = profiles.getHashedDictionary().getValue("status");


                    //new data
                    Scanner scanner2 = new Scanner(System.in);
                    int i = scanner2.nextInt();
                    if(i==i){
                        // Modify the data at the bottom of the file
                        profiles.getHashedDictionary().add("status","busy");

                        fileUtil.modifyFile(path, (String) status,"busy");

                    }else if(i==2){

                        profiles.getHashedDictionary().add("status","Online");

                        fileUtil.modifyFile(path, (String) status,"Online");

                    }
                    return;
            }


        }

    }




    /**
     * Author：Peter Liu
     * displace
     * @param profiles
     * @return
     */
    @Override
    public String displace(Profiles profiles) {
        //profiles.toString();

        return profiles.toString();
    }


    /**
     * Author：Peter Liu
     * name
     */
    @Override
    public void findFriends() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the person you are looking for");
        String name = scanner.nextLine();

        String path = name + ".txt";
        File file = new File(path);

        if (file.exists()) {
            System.out.println("We have found "+name+" this guys");
        }
        System.out.println("no " + name + "Please re-enter");

    }



    /**
     * Author：Jiahao Huang
     * add
     */
    @Override
    public void addFridends(Profiles profiles) throws IOException {
        System.out.println("Enter the name of the friend you want to add");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();


        // determine if the friend is not in the collection can be joined normally
        AList<String> stringAList = profiles.getaList();
        if(stringAList.contains(name)){
            System.out.println("Already have this friend please enter the correct name");
            return;
        }

           stringAList.add(name);
            String path = profiles.getName() + ".txt";
            File file = new File(path);

            FileWriter fileWriter = new FileWriter(file,true);
            //修改了
            fileWriter.write(":"+name+ " "+"\n");
            fileWriter.close();


    }

    /**
     * Author：Peter Liu
     * Modify Friends
     * @param profiles
     * @throws IOException
     */
    @Override
    public void modifyFridends(Profiles profiles) throws IOException
    {
        Scanner scanner = new Scanner(System.in);

        AList<String> stringAList = profiles.getaList();

        if(stringAList.isEmpty()){
            System.out.println("Please add friends first before modifying the operation");
            return;
        }

        //Arrays.toString(aList.toArray())

        System.out.println(Arrays.toString(profiles.getaList().toArray()));
        System.out.println("Enter the name, and enter his location information:   ");
        System.out.print("Name :    ");
        String name = scanner.nextLine();
        System.out.print("position :    ");
        int posistion = scanner.nextInt();

        String path = profiles.getName() + ".txt";


        //修改了
            stringAList.remove(posistion);
            new FileUtil().removeLineFromFile(path,":"+name);

    }

    /**
     * Author：Jiahao Huang
     * Log out and set the status to offline
     */
    @Override
    public void signout(Profiles profiles) throws IOException
    {

        FileUtil fileUtil = new FileUtil();
        String Personpath = profiles.getName() + ".txt";
        Object status = profiles.getHashedDictionary().getValue("status");;
        fileUtil.modifyFile(Personpath, (String) status,"offline");


        // save a data list of all members on exit
        String pathMembers = "allMembers.txt";
        File file = new File(pathMembers);


        // Get the name
        String name = profiles.getName();
        //String path = name + ".txt";
        //File fileSec = new File(path);

        //Read the list of all members of the list has
        // not appeared each time, if it appears, it means that it is already the second login is not written
        if(!file.exists())
        {
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write(profiles.getName()+"\n");
            fileWriter.close();
        }
        else if(file.exists())// read if the user we are writing to is new
        {
            //A collection of all users
            AList<Object> objectAList = new AList<>();

            FileReader fileReader = new FileReader(pathMembers);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String str = null;
            while (true) {
                str = bufferedReader.readLine();
                //System.out.println(str);
                if(str!=null){
                    objectAList.add(str);
                }else {
                    break;
                }
            }

            if(objectAList.contains(name)){
            }else{
                FileWriter fileWriter = new FileWriter(file, true);

                fileWriter.write(profiles.getName()+"\n");
                fileWriter.close();
            }
        }
    }


    /**
     * Author：Jiahao Huang
     * @return
     * @throws IOException
     */
    public  DirectedGraph ReadGraph() throws IOException {
        String pathPerson=null;
        String path = "allMembers.txt";
        File file = new File(path);

        if(file.exists()){
            DirectedGraph<String> stringDirectedGraph = new DirectedGraph<>();

            //this to read the name of the owner
            FileReader fileReader = new FileReader(file);
            BufferedReader buffer = new BufferedReader(fileReader);

            AList<Object> MembersAList = new AList<>();
            String entry=null;

            String str=null;
            while (true) {
                str = buffer.readLine();
                if(str!=null){
                    stringDirectedGraph.addVertex(str);
                    MembersAList.add(str);
                }
                else{
                    break;
                }
            }

            for(int i=1;i<=MembersAList.getLength();i++){
                entry = (String) MembersAList.getEntry(i);
                pathPerson = entry + ".txt";
                FileReader fileReaderPerson = new FileReader(pathPerson);
                BufferedReader bufferedReader = new BufferedReader(fileReaderPerson);

                bufferedReader.readLine();
                bufferedReader.readLine();
                bufferedReader.readLine();
                bufferedReader.readLine();
                bufferedReader.readLine();
                String s = bufferedReader.readLine();
                //System.out.println(s);

                String Fridends =null;
                while (true) {
                    Fridends = bufferedReader.readLine();
                    if(Fridends!=null) {

                        String substring = Fridends.substring(1);

                        // Note that you must remove the spaces before and after here or it will always fail
                        substring=substring.trim();

                        stringDirectedGraph.addEdge(entry, substring);
                    }
                    else{
                        break;
                    }
                }
                bufferedReader.close();
                fileReaderPerson.close();
            }


            buffer.close();

            //stringDirectedGraph.displayEdges();

            //Take this name and match it with a friend inside

            return stringDirectedGraph;

        }else{
            return null;
        }

    }


    /**
     * Author：Peter Liu
     * @param directedGraph
     */
    @Override
    public void seeFriendslist(DirectedGraph directedGraph) {

        System.out.println("Please enter the name you wish to view");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();

        /*
        In the first case, the entered name has not been registered successfully
        and the user is logging in for the first time and is not up to date in our AllMembers list,
        In this case, we should prompt the user to register for more users and make sure the user has no friends.
         */

        String path = name + ".txt";
        File file = new File(path);
        File file1 = new File("allMembers.txt");


        // Success in starting the search
            directedGraph.displayEdges(name);
           System.out.println("Reminder that if not found, the user may not have added friends");



    }


}









