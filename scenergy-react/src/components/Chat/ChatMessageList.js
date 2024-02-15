import React, { useEffect, useRef } from "react";
import styles from "./ChatMessageList.module.css";
const ChatList = ({ chatList, userId }) => {
  const chatMsgFieldRef = useRef(null);
  useEffect(() => {
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
            senderId={chatMessage.senderId}
          />
        ))}
      </div>
    </div>
  );
};

const ChatMessage = ({ message, userId, senderId }) => {
  const showUnreadCnt =
    message.senderId !== 1 &&
    message.senderId !== null &&
    message.unreadCount > 0;

  function formatMessage(message) {
    const maxWord = 30;
    let formattedMessage = "";
    let currentLine = "";
    if (message != null)
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
  const MessageContent = () => (
    <div className={styles.messageContent}>
      {showUnreadCnt && (
        <span className={styles.unreadCount}>{message.unreadCount}</span>
      )}
      <span className={styles.messageText}>
        {formatMessage(message.messageText)}
      </span>
    </div>
  );

  return (
    <div className={styles.chatMsgList}>
      <div
        className={
          senderId === 1
            ? styles.adminMessage
            : senderId === +userId
              ? styles.myMessage
              : styles.otherMessage
        }
      >
        <MessageContent />
      </div>
    </div>
  );
};

export { ChatList, ChatMessage };
