import { useRef, useState, useEffect, useCallback } from "react";
import { useParams } from "react-router-dom";
import * as StompJs from "@stomp/stompjs";
import styles from "./ChatConnect.module.css";
import axios from "axios";
import ChatInput from "./ChatInput";
import { ChatList, ChatMessage } from "./ChatMessageList";

const ChatConnect = ({ roomId }) => {
  //채팅 목록
  const [chatList, setChatList] = useState([]);
  const [chat, setChat] = useState("");
  const maxWord = 30;

  //채팅방 식별 id url에서 가져오기
  // 백은 room_id long으로 받음 형 변환 해주기
  // const realRoom_id = parseInt(room_id, 10);
  // 나중에 roomId로 바꿔주기
  const room_id = 1;
  const chatMessage_id = 3;
  const client = useRef({});
  const userId = 2;
  const chatMsgFieldRef = useRef(null);

  const subscribe = useCallback(() => {
    client.current.subscribe("/sub/chat/room/" + room_id, (body) => {
      const messageBody = JSON.parse(body.body);
      setChatList((prevChatList) => [...prevChatList, messageBody]);
      console.log("subsub");
      // console.log(chatList);
    });
  }, [room_id]);

  const connect = useCallback(() => {
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
    });
    client.current.activate();
  }, [subscribe]);

  const publish = (chat) => {
    //연결 끊어지면 끝
    if (!client.current.connected) return;

    //test용
    const message = {
      //TODO:나중에 header로 user정보 담아왔을때 user_id 받자받자
      user_id: 2,
      room_id: 1,
      message: chat,
      messageType: "TALK",
    };

    client.current.publish({
      destination: "/pub/chat",
      body: JSON.stringify(message),
    });

    console.log(chat);
    console.log(chatList);
    setChat("");
    console.log("pubpub");
  };
  const disconnect = () => {
    client.current.deactivate();
  };
  const handleChange = (event) => {
    //입력값 state로 변경해주기
    setChat(event.target.value);
  };

  const handleSubmit = (event, chat) => {
    //보내기 누르면 publish되도록
    event.preventDefault();
    console.log("보내기");
    publish(chat);
    console.log(chatList);
  };

  useEffect(() => {
    connect();
    return () => disconnect();
  }, [connect]);

  useEffect(() => {
    const LoadPrevMessage = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8080/chatroom/load-message-room",
          {
            params: { chatMessage_id },
          },
        );
        setChatList(response.data.data.messageList);
        console.log("바로 읽어버렸쥬?");
      } catch (error) {
        console.error("읽기 실패", error);
      }
    };

    LoadPrevMessage();
    //나중에 roomId로 바꾸기
  }, [room_id]);

  useEffect(() => {
    const chatMsgElement = chatMsgFieldRef.current;

    if (chatMsgElement) {
      chatMsgElement.scrollTop = chatMsgElement.scrollHeight;
    }
    console.log(chatMsgFieldRef.current);
  }, [chatList]);

  return (
    <div className={styles.chatMsgFieldGlobal}>
      <ChatList chatList={chatList} userId={userId} />
      <ChatInput
        chat={chat}
        handleChange={handleChange}
        handleSubmit={handleSubmit}
      />
    </div>
  );
};

export default ChatConnect;
