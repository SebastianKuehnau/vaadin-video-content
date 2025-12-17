package com.example.vaadin.views;

import com.example.vaadin.services.UserService;
import com.vaadin.collaborationengine.CollaborationMessageInput;
import com.vaadin.collaborationengine.CollaborationMessageList;
import com.vaadin.collaborationengine.UserInfo;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import java.util.UUID;

@Route("")
public class ChatView extends VerticalLayout {

  public ChatView(UserService userService) {
      var userInfo = new UserInfo(UUID.randomUUID().toString(),
              userService.getUsername());

      var messageList = new CollaborationMessageList(userInfo, "chat");
      messageList.setSizeFull();

      var messageInput = new CollaborationMessageInput(messageList);
      messageInput.setWidthFull();
      messageInput.focus();

      add(messageList, messageInput);
      setSizeFull();
  }
}
