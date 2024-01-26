import {useRef, useState, useEffect, useCallback} from "react";
import {useParams} from "react-router-dom";
import * as StompJs from "@stomp/stompjs";
import styles from "./ChatConnect.module.css"

const ChatConnect = () => {
    //채팅 목록
    const [chatList, setChatList] = useState([]);
    const [chat, setChat] = useState("");

    //채팅방 식별 id url에서 가져오기
    const {room_id} = useParams();
    // 백은 room_id long으로 받음 형 변환 해주기
    const realRoom_id = parseInt(room_id, 10);
    //const room_id = 1;
    const client = useRef({});

    const subscribe = useCallback(() => {
        client.current.subscribe("/sub/chat/" + realRoom_id, (body) => {
            //console.log(body);
            const json_body = JSON.parse(body.body);
            //_chat_list는 subscribe 함수 내에서만 사용되는 지역변수
            setChatList((_chat_list) => [..._chat_list, json_body]);
            console.log("subsub");
        });
    }, [realRoom_id]);

    const connect = useCallback(() => {
        client.current = new StompJs.Client({
            brokerURL: "ws://localhost:8080/ws",
            onConnect: () => {
                console.log("yesyes");
                subscribe();
            }
        });
        client.current.activate();
    }, [subscribe]);

    const publish = (chat) => {
        //연결 끊어지면 끝
        if (!client.current.connected) return;

        //test용
        const message = {
            //나중에 header로 user정보 담아왔을때 user_id 받자받자
            user_id: 1234,
            room_id: realRoom_id,
            message: chat,
        };

        client.current.publish({
            destination: "/pub/chat",
            body: JSON.stringify(message),
        });

        setChat("");
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

        publish(chat);
    };

    useEffect(() => {
        connect();

        return () => disconnect();
    }, [connect]);

    return (
        <div>
            <div>
                {chatList.map((chatMessage) => (
                    <div key={chatMessage.id}>{chatMessage.messageText}</div>
                ))}
            </div>
            <form onSubmit={(event) => handleSubmit(event, chat)}>
                <div>
                    <input
                        type={"text"}
                        name={"chatInput"}
                        onChange={handleChange}
                        value={chat}
                    />
                </div>
                <input className={styles.sendbutton} type={"submit"} value={"보내기"}/>
            </form>
        </div>
    );
}

export default ChatConnect;
