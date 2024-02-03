import React from "react";
import styles from "./ChatConnect.module.css";

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

const ChatList = ({ chatList, userId }) => {
  return (
    <div className={styles.chatMsgField}>
      <div className={styles.chatMsg}>
        {chatList.map((chatMessage) => (
          <div className={styles.chatMsgList} key={chatMessage.id}>
            <div
              className={
                chatMessage.senderId === userId
                  ? styles.myMessage
                  : styles.otherMessage
              }
            >
              <p>{formatMessage(chatMessage.messageText)}</p>
              {/* <p>{chatMessage.messageText}</p> */}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

const ChatMessage = ({ message, userId }) => {
  return (
    <div
      key={message.id}
      className={
        message.senderId === userId ? styles.myMessage : styles.otherMessage
      }
    >
      <p>{formatMessage(message.messageText)}</p>
    </div>
  );
};

export { ChatList, ChatMessage };
