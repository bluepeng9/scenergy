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

const ChatConnect = ({ lastMessageId, refetchChatRooms }) => {
  const [chat, setChat] = useState("");
  const [chatMessages, setChatMessages] = useState([]);
  const client = useRef({});
  const userId = 2;
  const { roomId } = useParams();
  const realRoomId = parseInt(roomId, 10);
  const { updateRecentMessage } = useChatRoom();
  const {
    addChatMessage,
    setRecentChatMessage: setRecentChatMessage,
    recentChatMessage: recentChatMessage,
  } = useChatMessageContext();

  const {
    data: loadedMessages,
    isLoading: messagesLoading,
    isError: messagesError,
  } = useChatMessages(lastMessageId);

  useEffect(() => {
    if (!messagesLoading && loadedMessages && !messagesError) {
      setChatMessages(loadedMessages);
    }
  }, [loadedMessages, messagesLoading, messagesError]);

  const subscribe = useCallback(() => {
    client.current.subscribe("/sub/chat/room/" + realRoomId, (message) => {
      const messageBody = JSON.parse(message.body);
      setChatMessages((prevMessages) => [...prevMessages, messageBody]);
      console.log("subsub하네요");
      updateRecentMessage(realRoomId, messageBody);
      console.log(messageBody.id);
      console.log("메세지 받음:", messageBody);
      console.log(recentChatMessage);
    });
  }, [realRoomId, addChatMessage, updateRecentMessage]);

  const connect = useCallback(() => {
    if (client.current.connected) {
      return;
    }

    client.current = new StompJs.Client({
      brokerURL: "ws://localhost:8080/ws",
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
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
  }, [realRoomId]);

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
      callback: function (error, messageResponse) {
        if (error) {
          console.error("메시지 전송 실패", error);
        } else {
          const receivedMessage = JSON.parse(messageResponse.body);
          addChatMessage(receivedMessage); // 실제 서버에서 생성된 ID를 포함한 메시지 객체
          updateRecentMessage(realRoomId, receivedMessage);
          refetchChatRooms();
        }
      },
    });
    setChat("");
  };
  useEffect(() => {
    return () => {
      if (client.current && client.current.connected) {
        client.current.deactivate();
      }
    };
  }, []);
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
    return () => client.current.deactivate();
  }, [realRoomId, connect]);

  if (messagesLoading) return <div>로딩중...</div>;
  if (messagesError)
    return <div>Error loading messages: {messagesError.message}</div>;

  return (
    <div className={styles.chatMsgFieldGlobal}>
      <ChatList chatList={chatMessages} userId={userId} />
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
