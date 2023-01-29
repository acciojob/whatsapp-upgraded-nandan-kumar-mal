package com.driver;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WhatsappService {

    WhatsappRepository whatsappRepository = new WhatsappRepository();

    public String createUser(String name,String mobile){

        User user = new User(name,mobile);
        whatsappRepository.addUserToMap(user);
        return "SUCCESS";
    }

    public Group createGroup(List<User> users){
       return  whatsappRepository.addGroupToMap(users);
    }

    public int createMessage(String content){
        return whatsappRepository.addMessageToMap(content);
    }

    public int sendMessage(Message message,User sender,Group group){
          return whatsappRepository.sendMessageToGroup(message,sender,group);
    }

    public String changeAdmin(User approver,User user,Group group){
        return whatsappRepository.changeAdminInGroup(approver,user,group);
    }

    public int  removeUser(User user){
        return 0;

    }

    public String findMessage(Date start, Date end, int k){
        return " ";
    }
}
