import React, { useEffect, useRef } from "react";
import styles from "./ChatConnect.module.css";

const ChatList = ({ chatList, userId }) => {
  const chatMsgFieldRef = useRef(null);
  useEffect(() => {
    console.log(chatList);
    const chatMsgElement = chatMsgFieldRef.current;

    if (chatMsgElement) {
      chatMsgElement.scrollTop = chatMsgElement.scrollHeight;
    }
  }, [chatList]);

  return (
    <div className={styles.chatMsgField} ref={chatMsgFieldRef}>
      <div className={styles.chatMsg}>
        {chatList.map((chatMessage) => (
          <ChatMessage
            key={chatMessage.id}
            message={chatMessage}
            userId={userId}
          />
        ))}
      </div>
    </div>
  );
};

const ChatMessage = ({ message, userId }) => {
  function formatMessage(message) {
    const maxWord = 30;
    let formattedMessage = "";
    let currentLine = "";

    for (let i = 0; i < message.length; i++) {
      currentLine += message[i];
      if (currentLine.length >= maxWord || message[i] === " ") {
        formattedMessage += currentLine + "\n";
        currentLine = "";
      }
    }
    if (currentLine !== "") {
      formattedMessage += currentLine + "\n";
    }
    return formattedMessage;
  }

  return (
    <div
      className={
        message.senderId === userId ? styles.myMessage : styles.otherMessage
      }
    >
      <p>{formatMessage(message.messageText)}</p>
    </div>
  );
};

export { ChatList, ChatMessage };
