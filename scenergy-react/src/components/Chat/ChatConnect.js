import { useRef, useState, useEffect, useCallback } from "react";
import { useParams } from "react-router-dom";
import * as StompJs from "@stomp/stompjs";
import styles from "./ChatConnect.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faArrowUp } from "@fortawesome/free-solid-svg-icons";

const ChatConnect = () => {
  //채팅 목록
  const [chatList, setChatList] = useState([]);
  const [chat, setChat] = useState("");

  //채팅방 식별 id url에서 가져오기
  const { room_id } = useParams();
  // 백은 room_id long으로 받음 형 변환 해주기
  // const realRoom_id = parseInt(room_id, 10);
  const realRoom_id = 1;
  const client = useRef({});
  const userId = 1;
  const subscribe = useCallback(() => {
    client.current.subscribe("/sub/chat/room/" + realRoom_id, (body) => {
      console.log(body);
      const json_body = JSON.parse(body.body);
      //_chat_list는 subscribe 함수 내에서만 사용되는 지역변수
      setChatList((_chat_list) => [..._chat_list, json_body]);
      console.log("subsub");
      console.log(chatList);
    });
  }, [realRoom_id]);

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
      //나중에 header로 user정보 담아왔을때 user_id 받자받자
      user_id: 1,
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
    console.log(chatList);
  }, [chatList]);

  return (
    <div className={styles.ChatMsgFieldGlobal}>
      <div className={styles.ChatMsgField}>
        <div className={styles.ChatMsg}>
          {chatList.map((chatMessage) => (
              <div
                  key={chatMessage.id}
                  className={
                    chatMessage.senderId === userId
                        ? styles.myMessage
                        : styles.otherMessage
                  }
              >
                <p>{chatMessage.messageText}</p>
              </div>
          ))}
        </div>
      </div>
      <form onSubmit={(event) => handleSubmit(event, chat)}>
        <div className={styles.ChatInput}>
          <textarea
            tabIndex="0"
            rows={1}
            type={"text"}
            name={"chatInput"}
            onChange={handleChange}
            value={chat}
            placeholder="메세지 보내기"
            onKeyDown={(e) => {
              if (e.keyCode === 13) {
                e.preventDefault();
                handleSubmit(e, chat);
              }
            }}
          />
          <div className={styles.ChatSendBtn}>
            <button>
              <FontAwesomeIcon icon={faArrowUp} />
            </button>
          </div>
        </div>
      </form>
    </div>
  );
};

export default ChatConnect;
