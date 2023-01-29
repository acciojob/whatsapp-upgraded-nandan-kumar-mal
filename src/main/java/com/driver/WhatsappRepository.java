package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class WhatsappRepository {

    private HashMap<String,User> userHashMap;
    private HashMap<Group,List<User>> groupHashMap;
    private HashMap<Group,List<Message>> groupMessageHashMap;
    private HashMap<User,List<Message>> userMessageListMap;

    private List<Message> listOfMessage;



    public WhatsappRepository(){
        this.userHashMap = new HashMap<>();
        this.groupHashMap = new HashMap<>();
        this.groupMessageHashMap = new HashMap<>();
        this.userMessageListMap = new HashMap<>();
        this.listOfMessage = new ArrayList<>();

    }

    public void addUserToMap(User user)throws Exception{

            if(userHashMap.containsKey(user.getName())){
                throw new Exception("User already exists");
            }
            userHashMap.put(user.getName(), user);

    }

    private int countOfGroup =0;
    private int countOfMessage=0;
    public Group addGroupToMap(List<User> users){
        Group group;
        if(users.size()==2){
            String groupName =  users.get(1).getName();
            group = new Group(groupName,2);
            group.setAdmin(users.get(0).getName());
            groupHashMap.put(group, users);

        }else{
            countOfGroup++;
            String groupName =  "Group "+ countOfGroup;
            group = new Group(groupName,users.size());
            group.setAdmin(users.get(0).getName());
            groupHashMap.put(group,users);
        }
        return group;
    }

    public int addMessageToMap(String content){
        int msgId = countOfMessage+1;
        Message message = new Message(msgId,content);
        message.setTimestamp(new Date());
        listOfMessage.add(message);

        return countOfMessage;
    }

    public int sendMessageToGroup(Message message,User sender,Group group){
        List<Message> messageList= new ArrayList<>();
        if(!groupHashMap.containsKey(group)){
            throw new RuntimeException("Group does not exist");
        }

        boolean isMember = false;
        for(User user:groupHashMap.get(group)){
            if(user.equals(sender)){
                isMember = true;
                break;
            }
        }

        if(!isMember)
            throw new RuntimeException("You are not allowed to send message");

        if(groupMessageHashMap.containsKey(group))
            groupMessageHashMap.get(group).add(message);
        else{
            List<Message> messagesList = new ArrayList<>();
            messagesList.add(message);
            groupMessageHashMap.put(group,messagesList);
        }

        if(userMessageListMap.containsKey(sender))
            userMessageListMap.get(sender).add(message);
        else{
            List<Message> messageList1 = new ArrayList<>();
            messageList1.add(message);
            userMessageListMap.put(sender,messageList1);
        }

        return groupMessageHashMap.get(group).size();

    }

    public String changeAdminInGroup(User approver,User user,Group group){

        if(!groupHashMap.containsKey(group))
            throw new RuntimeException("Group does not exist");

        if(!approver.equals(groupHashMap.get(group).get(0)))
            throw new RuntimeException("Approver doesnot have rights");

        boolean isParticipant = false;
        for(User user1:groupHashMap.get(group)){
            if(user1.equals(user)){
                isParticipant=true;
                break;
            }
        }
        if(!isParticipant)
            throw new RuntimeException("User is not a participant");

        User newAdmin = null;

        for(User user2: groupHashMap.get(group)){
                if(user2.equals(user)){
                    newAdmin = user2;
                }
        }
        groupHashMap.get(group).add(0,newAdmin);
        return "SUCCESS";
    }
    public int removeUser(User user){

        boolean userPresent = false;
        int groupSize = 0;
        int countOfMessage = 0;

        Group targetGroup = null;

        for(Map.Entry<Group,List<User>> entry : groupHashMap.entrySet()){

            List<User> member = entry.getValue();
            if(member.contains(user)){
                userPresent = true;
                targetGroup = entry.getKey();
                if(member.get(0).equals(user))
                    throw new RuntimeException("Cannot remove admin");
                member.remove(user);
                groupSize = member.size();

                break;

            }
        }
        if(!userPresent)
            throw new RuntimeException("User not found");

        if(userMessageListMap.containsKey(user)){
            countOfMessage = userMessageListMap.get(user).size();
            userMessageListMap.remove(user);
        }
        int overAllMessageCount = listOfMessage.size();


        return groupSize + countOfMessage + overAllMessageCount;
    }

    public String findMessage(Date start,Date end,int k){

        return "";
    }


}
