import { useRef, useState, useEffect, useCallback } from "react";
import { useParams } from "react-router-dom";
import * as StompJs from "@stomp/stompjs";
import styles from "./ChatConnect.module.css";
import ChatInput from "./ChatInput";
import { useChatMessages } from "../../hooks/useChatMessages";
import { ChatList } from "./ChatMessageList";
import { useChatMessageContext } from "../../contexts/ChatMessageContext";
import { useChatRoom } from "../../contexts/ChatRoomContext";
import axios from "axios";

const ChatConnect = ({ lastMessageId }) => {
  const [chat, setChat] = useState("");
  const [chatMessages, setChatMessages] = useState([]);
  const [nowLastMessageId, setNowLastMessageId] = useState(lastMessageId);
  const client = useRef({});
  const userId = 2;
  const { roomId } = useParams();
  const realRoomId = parseInt(roomId, 10);
  const { updateRecentMessage, updateLastMessageId } = useChatRoom();
  const {
    addChatMessage,
    setRecentChatMessage: setRecentChatMessage,
    recentChatMessage: recentChatMessage,
  } = useChatMessageContext();

  const {
    data: chatMessage,
    isLoading,
    isError,
    error,
  } = useChatMessages(realRoomId);

  useEffect(() => {
    if (Array.isArray(chatMessage) && chatMessage.length > 0) {
      chatMessage.forEach((message) => {
        console.log("Message ID: ", message.id);
        console.log("message", message);
        addChatMessage(message);
        setNowLastMessageId(message.id);
        setRecentChatMessage(message);
      });
    }
  }, [chatMessages, chatMessage, addChatMessage, setRecentChatMessage]);

  useEffect(() => {
    if (!isLoading) {
      setChatMessages(chatMessage);
      console.log(chatMessages);
      if (chatMessage.length > 0) {
        setRecentChatMessage(chatMessage[chatMessage.length - 1]);
      }
    }
  }, [chatMessage, isLoading]);

  const subscribe = useCallback(() => {
    client.current.subscribe("/sub/chat/room/" + realRoomId, (message) => {
      const messageBody = JSON.parse(message.body);
      addChatMessage(messageBody);
      setChatMessages((prevMessages) => {
        const updatedMessages = Array.isArray(prevMessages)
          ? [...prevMessages, messageBody]
          : [messageBody];
        return updatedMessages;
      });
      console.log("subsub하네요");
      updateRecentMessage(realRoomId, messageBody);
      updateLastMessageId(realRoomId, messageBody.id);
      setNowLastMessageId(messageBody.id);
      console.log(messageBody.id);
      console.log(nowLastMessageId);
    });
  }, [
    lastMessageId,
    addChatMessage,
    realRoomId,
    updateRecentMessage,
    updateLastMessageId,
  ]);

  const connect = () => {
    if (client.current.connected) {
      client.current.deactivate();
    }

    client.current = new StompJs.Client({
      brokerURL: "ws://localhost:8080/ws",
      reconnectDelay: 5000,
      heartbeatIncoming: 2000,
      heartbeatOutgoing: 2000,
      onConnect: () => {
        console.log("yesyes");
        subscribe();
      },
      onError: (error) => {
        console.log(error);
      },

      connectHeaders: {
        roomId: realRoomId.toString(),
        userId: userId.toString(),
      },
    });
    client.current.activate();
  };

  const publish = (chat) => {
    //연결 끊어지면 끝
    if (!client.current.connected) return;

    //test용
    const message = {
      //TODO:나중에 header로 user정보 담아왔을때 user_id 받자받자
      user_id: 2,
      room_id: realRoomId,
      message: chat,
      messageType: "TALK",
    };

    client.current.publish({
      destination: "/pub/chat",
      body: JSON.stringify(message),
    });

    console.log(chat);
    setChat("");
    console.log("pubpub");
  };
  const disconnect = () => {
    if (client.current && client.current.connected) {
      client.current.deactivate();
    }
  };
  const handleChange = (event) => {
    //입력값 state로 변경해주기
    setChat(event.target.value);
  };

  const handleSubmit = (event, chat) => {
    //보내기 누르면 publish되도록
    event.preventDefault();
    if (!chat.trim()) return;
    console.log("보내기");
    publish(chat);
  };

  useEffect(() => {
    connect();
    console.log(realRoomId);
    return () => {
      if (client.current && client.current.connected) {
        client.current.deactivate();
      }
    };
  }, [realRoomId]);

  //roomId 변경될 때마다 구독 재설정
  useEffect(() => {
    return () => {
      if (client.current) {
        client.current.unsubscribe();
      }
    };
  }, [roomId]);

  if (isLoading) return <div>Loading messages...</div>;
  if (isError) return <div>Error loading messages: {error.message}</div>;

  return (
    <div className={styles.chatMsgFieldGlobal}>
      <ChatList chatList={chatMessages || []} userId={userId} />
      <ChatInput
        chat={chat}
        setChat={setChat}
        handleChange={handleChange}
        handleSubmit={handleSubmit}
      />
    </div>
  );
};
export default ChatConnect;
