import Service.impl.profilesServiceImpl;
import common.AList;
import common.Graphs.DirectedGraph;
import entity.Profiles;

import java.io.*;
import java.util.Scanner;

public class Drive {
    public static void main(String[] args) throws IOException {

        profilesServiceImpl profilesService = new profilesServiceImpl();
        Profiles profileInfo = new Profiles();

        // Get the data from the database
        DirectedGraph directedGraph = profilesService.ReadGraph();
        //directedGraph.displayEdges();



        //1:Operation of login and registration
        boolean flag = true;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Enter the operation  1:Sign up   2:Login");
            int i = scanner.nextInt();

            if (i == 1) {
                try {
                    profileInfo = profilesService.reg();
                    if(profileInfo!=null){
                        flag=false;
                    }
                }catch (NullPointerException e){
                    break;
                }
            }else if (i == 2){
                try {
                    profileInfo = profilesService.login();
                    if(profileInfo!=null){
                        flag=false;
                    }
                }catch (NullPointerException e){
                    break;
                }
            }
            else {
                System.out.println("Enter the correct action");
                continue;
            }
        } while (flag) ;


        boolean isflag=true;
        do {
            System.out.println("----------1:Create personal information-------");
            System.out.println("----------2:Modify personal information-------");
            System.out.println("----------3:Find other friends----------------");
            System.out.println("----------4:Add personal friends---------------");
            System.out.println("----------5:Delete personal friends------------");
            System.out.println("--------- 6:Display of personal information----");
            System.out.println("--------- 7:see a list of their friends’ friends(Newly added features)----");
            System.out.println("------------8:Exit the program------------");

            System.out.println("Enter the action you want to perform");
            int i = scanner.nextInt();
            switch (i){
                case 1:
                    profilesService.create(profileInfo);
                    break;
                case 2:
                    profilesService.modify(profileInfo);
                    break;
                case 3:
                    profilesService.findFriends();
                    break;
                case 4:
                    profilesService.addFridends(profileInfo);
                    break;
                case 5:
                    profilesService.modifyFridends(profileInfo);
                    break;
                case 6:
                    String displace = profilesService.displace(profileInfo);
                    System.out.println(displace);
                    break;
                case 7:
                    profilesService.seeFriendslist(directedGraph);
                    break;
                case 8:
                    profilesService.signout(profileInfo);
                    isflag=false;
                    break;
            }

        } while (isflag);

    }

}

