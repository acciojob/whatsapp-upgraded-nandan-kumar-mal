package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class WhatsappRepository {

    private HashMap<String,User> userHashMap;

    private HashMap<String,Group> groupHashMap;

    private HashMap<Integer,Message> messageHashMap;

    private HashMap<String,List<Message>> userMessage;

    private HashMap<String,Integer> groupAndMessage;

    private HashMap<String,List<String>> groupAndListOfMemberMap;
    private HashMap<String,String> groupMemberMap;

    public WhatsappRepository(){
        this.userHashMap = new HashMap<>();
        this.groupHashMap = new HashMap<>();
        this.messageHashMap = new HashMap<>();
        this.groupAndListOfMemberMap = new HashMap<>();
        this.groupMemberMap = new HashMap<>();
        this.groupAndMessage = new HashMap<>();
        this.userMessage = new HashMap<>();

    }

    public void addUserToMap(User user){
        if(userHashMap.containsKey(user.getName())){
            throw new RuntimeException();
        }
        userHashMap.put(user.getName(), user);
    }

    public Group addGroupToMap(List<User> users){
        Group group;
        if(users.size()==2){
            String groupName =  users.get(1).getName();
            group = new Group(groupName,2);

        }else{
            String groupName =  "Group "+users.size();
            group = new Group(groupName,2);
        }

        group.setAdmin(users.get(0).getName());

        groupHashMap.put(group.getName(), group);

        for(User user:users){
            addGroupAndMember(group.getName(), user.getName());
        }
        return group;
    }

    public int addMessageToMap(String content){
        int msgId = messageHashMap.size()+1;
        Message message = new Message(msgId,content);
        message.setTimestamp(new Date());
        messageHashMap.put(message.getId(),message);

        return message.getId();
    }

    public void addGroupAndMember(String groupName,String memberName){

        if(userHashMap.containsKey(memberName) && groupHashMap.containsKey(groupName)){
            List<String> groupAndItsMemberList = new ArrayList<>();

            if(groupAndListOfMemberMap.containsKey(groupName)){
                groupAndItsMemberList = groupAndListOfMemberMap.get(groupName);
            }
            groupAndItsMemberList.add(memberName);
            groupMemberMap.put(memberName,groupName);


            groupAndListOfMemberMap.put(groupName,groupAndItsMemberList);
        }

    }
    public int sendMessageToGroup(Message message,User sender,Group group){
        List<Message> messageList= new ArrayList<>();
        if(!groupHashMap.containsKey(group.getName())){
            throw new RuntimeException("Group does not exist");
        }
        if(groupMemberMap.containsKey(sender.getName()) && groupMemberMap.get(sender.getName())== group.getName()){
            group.setCountOfMessage(group.getCountOfMessage()+1);
            messageHashMap.put(message.getId(),message);
            messageList=userMessage.get(sender.getName());
            userMessage.put(sender.getName(), messageList);

        }else{
            throw new RuntimeException("You are not allowed to send message");
        }

        int totalMessagesInGroup=0;

        if(groupAndMessage.containsKey(group.getName())){
            totalMessagesInGroup=groupAndMessage.get(group.getName())+1;
            groupAndMessage.put(group.getName(), totalMessagesInGroup);
        }
        return totalMessagesInGroup;

    }

    public String changeAdminInGroup(User approver,User user,Group group){

        if(groupHashMap.containsKey(group.getName())){
            if(group.getAdmin().equals(approver.getName())) {
                if (groupMemberMap.containsKey(user.getName()) && groupMemberMap.get(user.getName()).equals(group.getName())) {
                    group.setAdmin(user.getName());
                }else{
                    throw new RuntimeException("User is not a participant");
                }
            }else{
                throw new RuntimeException("Approver does not have rights");
            }
        }else{
            throw new RuntimeException("Group does not exist");
        }
        return "SUCCESS";
    }
    public int removeUser(User user){


        if(userHashMap.containsKey(user.getName())){
            if(groupMemberMap.containsKey(user.getName())){
                Group userInGroup = groupHashMap.get(groupMemberMap.get(user.getName()));
                if(userInGroup.getAdmin().equals(user.getName())){
                    throw new RuntimeException();
                }else{
                    List<String> memberList = groupAndListOfMemberMap.get(userInGroup.getName());
                    if(memberList.contains(user.getName()))
                        memberList.remove(user.getName());
                    if(groupMemberMap.containsKey(user.getName()))
                        groupMemberMap.remove(user.getName());
                    if(userMessage.containsKey(user.getName()))
                        userMessage.remove(user.getName());
                    userHashMap.remove(user.getName());
                }

            }
        }

        return 0;
    }


}
